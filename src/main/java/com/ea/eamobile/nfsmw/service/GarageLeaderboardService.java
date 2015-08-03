package com.ea.eamobile.nfsmw.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.model.CarSlot;
import com.ea.eamobile.nfsmw.model.GarageLeaderboard;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserCar;
import com.ea.eamobile.nfsmw.model.UserCarSlot;
import com.ea.eamobile.nfsmw.model.UserLbs;
import com.ea.eamobile.nfsmw.model.mapper.GarageLeaderboardMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;
import com.ea.eamobile.nfsmw.servlet.BootstrapServlet;
import com.ea.eamobile.nfsmw.utils.ConfigUtil;
import com.ea.eamobile.nfsmw.utils.JsonUtil;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 05 16:27:05 CST 2013
 * @since 1.0
 */
@Service
public class GarageLeaderboardService {

    @Autowired
    private GarageLeaderboardMapper garageLeaderboardMapper;
    @Autowired
    private UserCarService userCarService;
    @Autowired
    UserCarSlotService userSlotService;
    @Autowired
    CarSlotService slotService;
    @Autowired
    UserService userService;
    @Autowired
    JsonService jsonService;
    @Autowired
    MemcachedClient cache;
    @Autowired
    private UserLbsService lbsService;
    @Autowired
    private UserFilterService filterService;
    
    private static final Logger log = LoggerFactory.getLogger(GarageLeaderboardService.class);
    // <uid,glb>
    private static Map<Long, GarageLeaderboard> pool = new ConcurrentHashMap<Long, GarageLeaderboard>();
    // <user car id , merge slot>
    private static Map<Long, UserCarSlot> slotPool = new ConcurrentHashMap<Long, UserCarSlot>();
    private static int limit = 500000; // 每次从db取数据的限制
    private static int NUM = 2000; // 排行榜数量
    private static List<GarageLeaderboard> lblist = new ArrayList<GarageLeaderboard>();

    /**
     * <pre>
     * 1.读取db数据 先读slot 完全完成后读car
     * 2.pool填充,carpool填充时加入slot积分
     * 3.遍历pool，插入num数量的对象到新的list 
     * 4.排序
     * 5.插入db
     * java -Djava.ext.dirs=./lib/ -cp ./classes/  com.ea.eamobile.nfsmw.service.GarageLeaderboardService
     * </pre>
     */
    public static void main(String[] args) {
        BootstrapServlet.setRunning(false);
        // local debug use
        // ApplicationContext ctx = new
        // ClassPathXmlApplicationContext("file:src/main/webapp/WEB-INF/nfsmw-servlet.xml");
        String url = args[0];
        String debug = args[1];
        boolean isDebug = false;
        if (debug != null && debug.equals("debug")) {
            isDebug = true;
        }
        ApplicationContext ctx = new FileSystemXmlApplicationContext("file:" + url + "/nfsmw-servlet.xml");
        GarageLeaderboardService service = ctx.getBean("garageLeaderboardService", GarageLeaderboardService.class);
        service.execute(isDebug);
        System.exit(0);
    }

    public void execute(boolean isDebug) {
        // 1.先填充 slotpool
        initSlotPool();
        // 2.填充car pool
        initCarPool();
        // 3.插入list并排序
        sortList();
        // 4.add userinfo
        buildUserInfo(isDebug);
        // 5.save db
        save();
        // clear
        pool.clear();
        slotPool.clear();
        lblist.clear();
    }

    private void initSlotPool() {
        int fromId = userSlotService.getFirstId();
        long start = System.currentTimeMillis();
        log.warn("start fill slotpool......");
        int maxId = userSlotService.getMaxId();
        boolean isf = true;
        while (true) {
            int to = fromId + limit;
            if ((!isf) && (to > maxId)) {
                break;
            }
            isf = false;
            List<UserCarSlot> slots = userSlotService.getSlotListLimit(fromId, to);
            if (slots == null || slots.size() == 0) {
                fromId = to;
                continue;
            }
            fillSlotPool(slots);
            log.warn("fill done , from = {},to={}", fromId, to);
            UserCarSlot last = slots.get(slots.size() - 1);
            fromId = last.getId();
        }
        long end = System.currentTimeMillis();
        log.warn("finish fill slotpool! pool size = {} , cost time = {}", slotPool.size(), end - start);
    }

    private void initCarPool() {
        long fromId = userCarService.getFirstId();
        long start = System.currentTimeMillis();
        log.warn("start fill carpool......");
        int maxId = userCarService.getMaxId();
        boolean isf = true;
        while (true) {
            long to = fromId + limit;
            if ((!isf) && (to > maxId)) {
                break;
            }
            isf = false;
            List<UserCar> userCars = userCarService.getUserCarList(fromId, to);
            if (userCars == null || userCars.size() == 0) {
                fromId = to;
                continue;
            }
            fillPool(userCars);
            log.warn("fill done,from = {},to={}", fromId, to);
            UserCar last = userCars.get(userCars.size() - 1);
            fromId = last.getId();
        }
        long end = System.currentTimeMillis();
        log.warn("finish fill carpool! pool size = {} , cost time = {}", pool.size(), end - start);
    }

    private void fillPool(List<UserCar> userCars) {
        if (userCars != null && userCars.size() > 0) {
            for (UserCar ucar : userCars) {
                long userId = ucar.getUserId();
                GarageLeaderboard lb = pool.get(userId);
                if (lb == null) {
                    lb = new GarageLeaderboard();
                    lb.setUserId(userId);
                    lb.setCarNum(1);
                    lb.setCarTotalScore(ucar.getScore());
                    pool.put(userId, lb);
                } else {
                    lb.setCarNum(lb.getCarNum() + 1);
                    lb.setCarTotalScore(lb.getCarTotalScore() + ucar.getScore());
                }
                // get this car's slot score
                UserCarSlot slot = slotPool.get(ucar.getId());
                if (slot != null) {
                    lb.setCarTotalScore(lb.getCarTotalScore() + slot.getScore());
                    slotPool.remove(ucar.getId());
                }
            }
        }
    }

    private void fillSlotPool(List<UserCarSlot> slots) {
        if (slots != null && slots.size() > 0) {
            for (UserCarSlot userSlot : slots) {
                long userCarId = userSlot.getUserCarId();
                int score = calcSlotScore(userSlot);
                if (score == 0) {
                    continue;
                }
                UserCarSlot slot = slotPool.get(userCarId);
                if (slot == null) {
                    userSlot.setScore(score);
                    slotPool.put(userCarId, userSlot);
                } else {
                    slot.setScore(slot.getScore() + score);
                }
            }
        }
    }

    private int calcSlotScore(UserCarSlot userSlot) {
        CarSlot slot = slotService.getCarSlot(userSlot.getSlotId());
        if (slot != null) {
            return slot.getScore();
        }
        return 0;
    }

    private void sortList() {
        log.warn("start sort list...");
        ScoreComparator com = new ScoreComparator();
        long start = System.currentTimeMillis();
        HeapSort hs = new HeapSort();
        GarageLeaderboard[] arr = pool.values().toArray(new GarageLeaderboard[pool.values().size()]);
        lblist = hs.heapSort(arr, NUM, com);
        long end = System.currentTimeMillis();
        log.warn("finish sort list,size={},cost time={}", lblist.size(), end - start);
    }

    private static class ScoreComparator implements Comparator<GarageLeaderboard>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(GarageLeaderboard lb1, GarageLeaderboard lb2) {
            return lb1.getCarTotalScore() - lb2.getCarTotalScore();
        }
    }

    private void buildUserInfo(boolean isDebug) {
        log.warn("start build user info...");
        if (lblist.size() == 0) {
            log.warn("lblist null , return.");
            return;
        }
        long start = System.currentTimeMillis();
        Iterator<GarageLeaderboard> it = lblist.iterator();
        int rank = 1;
        while (it.hasNext()) {
            GarageLeaderboard lb = it.next();
            long userId = lb.getUserId();
            if(filterService.needFilterFromGlb(userId)){
                it.remove();
                continue;
            }
            if (!isDebug && (userId <= 10000 || userId == 3804756 || userId == 12268 || userId == 226863)) {
                it.remove();
                continue;
            }
            User user = userService.getUserFromDb(userId);
            boolean delete = user == null || ((user.getAccountStatus() & Const.IS_BAN) == 1);
            if (delete) {
                it.remove();
            } else {
                lb.setName(user.getName());
                if (user.getHeadIndex() >= 0) {
                    lb.setHeadUrl(ConfigUtil.SERVER_URL + "/nfsmw/images/head/" + user.getHeadIndex() + ".jpg");
                } else {
                    lb.setHeadUrl(user.getHeadUrl());
                }
                lb.setRank(rank);
                rank++;
                // add other param
                lb.setRpLevel(user.getLevel());
                lb.setRpNum(user.getRpNum());
                lb.setMwNum(user.getStarNum());
                lb.setTier(user.getTier());
                UserLbs lbs = lbsService.getUserLbs(userId);
                if (lbs != null) {
                    lb.setLatitude(lbs.getLatitude());
                    lb.setLongitude(lbs.getLongitude());
                    lb.setZone(lbs.getLocality());
                }
            }
        }
        long end = System.currentTimeMillis();
        log.warn("finish user info,size={},cost time={}", lblist.size(), end - start);
    }

    private void save() {
        if (lblist == null || lblist.size() == 0) {
            log.warn("list null ,not save");
            return;
        }
        long start = System.currentTimeMillis();
        deleteAll();
        insertBatch(lblist);
        long end = System.currentTimeMillis();
        log.warn("finish save to db,cost time={}", end - start);
    }

    private void insertBatch(List<GarageLeaderboard> list) {
        garageLeaderboardMapper.insertBatch(list);
    }

    private void deleteAll() {
        garageLeaderboardMapper.deleteAll();
    }

    @SuppressWarnings("unchecked")
    public List<GarageLeaderboard> getList() {
        List<GarageLeaderboard> list = (List<GarageLeaderboard>) cache.get(CacheKey.GARAGE_LEADERBOARD);
        if (list == null) {
            list = garageLeaderboardMapper.getList();
            cache.set(CacheKey.GARAGE_LEADERBOARD, list, MemcachedClient.HOUR);
        }
        return list;
    }

    public String getJson() {
        List<GarageLeaderboard> list = getList();
        return JsonUtil.toJson(list);
    }

    public List<GarageLeaderboard> getToptenList() {
        List<GarageLeaderboard> list = getList();
        if (CollectionUtils.isNotEmpty(list)) {
            int size = list.size() >= 10 ? 10 : list.size();
            return list.subList(0, size);
        }
        return Collections.emptyList();
    }

    public GarageLeaderboard getSelf(long userId) {
        GarageLeaderboard self = (GarageLeaderboard) cache.get(CacheKey.GARAGE_LEADERBOARD_USER + userId);
        if (self == null) {
            List<GarageLeaderboard> list = getList();
            if (CollectionUtils.isNotEmpty(list)) {
                for (GarageLeaderboard gb : list) {
                    if (gb.getUserId() == userId) {
                        cache.set(CacheKey.GARAGE_LEADERBOARD_USER + userId, gb, MemcachedClient.HOUR);
                        return gb;
                    }
                }
            }
            User user = userService.getUser(userId);
            if (user != null) {
                self = new GarageLeaderboard();
                self.setName(user.getName());
                if (user.getHeadIndex() >= 0) {
                    self.setHeadUrl(ConfigUtil.SERVER_URL + "/nfsmw/images/head/" + user.getHeadIndex() + ".jpg");
                } else {
                    self.setHeadUrl(user.getHeadUrl());
                }
                List<UserCar> carList = userCarService.getUserCarList(userId);
                int carNum = 0;
                int score = 0;
                if (carList != null) {
                    carNum = carList.size();
                    for (UserCar car : carList) {
                        score += car.getScore();
                        score += userCarService.getCarSlotScore(car);
                    }
                }
                self.setCarNum(carNum);
                self.setCarTotalScore(score);
                cache.set(CacheKey.GARAGE_LEADERBOARD_USER + userId, self, MemcachedClient.HOUR);
            }
        }
        return self;
    }

    private static class HeapSort {
        /**
         * 堆排序 取最大的returnNum数
         * 
         * @param arr
         * @param com
         * @param returnNum
         * @param returnList
         */
        public <T> List<T> heapSort(T[] arr, int returnNum, Comparator<? super T> com) {
            List<T> returnList = new ArrayList<T>();
            if (returnNum > arr.length) {
                returnNum = arr.length;
            }
            buildHeap(arr, com);
            for (int i = arr.length - 1; i >= 0; i--) {
                if (arr.length > returnNum && i < arr.length - returnNum) {
                    break;
                }
                swap(arr, 0, i);
                returnList.add(arr[i]);
                maxHeapify(arr, 0, com, i - 1);
            }
            return returnList;
        }

        private <T> void maxHeapify(T[] arr, int i, Comparator<? super T> com, int size) {
            if (i >= size) {
                return;
            }
            int l = 2 * i + 1;
            int r = 2 * i + 2;
            int target = i;
            if (l < size && com.compare(arr[l], arr[i]) > 0) {
                target = l;
            }
            if (r < size && com.compare(arr[r], arr[target]) > 0) {
                target = r;
            }
            if (i != target) {
                swap(arr, i, target);
                maxHeapify(arr, target, com, size);
            }
        }

        private void swap(Object[] arr, int i, int j) {
            if (i == j)
                return;
            Object tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }

        private <T> void buildHeap(T[] arr, Comparator<? super T> com) {
            for (int i = arr.length / 2; i >= 0; i--) {
                maxHeapify(arr, i, com, arr.length);
            }
        }
        /**
         * public static void main(String[] args) { int length = 5; Integer[] temp = new Integer[length]; Random random
         * = new Random(System.currentTimeMillis()); for (int i = 0; i < length; i++) { temp[i] =
         * Math.abs(random.nextInt() % 50); } for (int i : temp) { System.out.print(i + " "); }
         * System.out.println(Arrays.toString(temp)); HeapSort heapSort = new HeapSort(); List<Integer> list =
         * heapSort.heapSort(temp,22, new Comparator<Integer>() {
         * 
         * @Override public int compare(Integer o1, Integer o2) { return o1 - o2; } }); System.out.println(list); }
         */

    }
}