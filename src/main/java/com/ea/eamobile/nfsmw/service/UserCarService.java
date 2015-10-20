package com.ea.eamobile.nfsmw.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.CarConst;
import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.constants.CtaContentConst;
import com.ea.eamobile.nfsmw.model.BuyCarRecord;
import com.ea.eamobile.nfsmw.model.Car;
import com.ea.eamobile.nfsmw.model.CarChartlet;
import com.ea.eamobile.nfsmw.model.CarExt;
import com.ea.eamobile.nfsmw.model.GotchaCar;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserCar;
import com.ea.eamobile.nfsmw.model.UserCarFragment;
import com.ea.eamobile.nfsmw.model.UserChartlet;
import com.ea.eamobile.nfsmw.model.handler.Inthandler;
import com.ea.eamobile.nfsmw.model.handler.UserCarListHandler;
import com.ea.eamobile.nfsmw.model.mapper.UserCarMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;
import com.ea.eamobile.nfsmw.service.gotcha.GotchaCarService;
import com.ea.eamobile.nfsmw.utils.DateUtil;
import com.ea.eamobile.nfsmw.utils.RWSplit;
import com.ea.eamobile.nfsmw.view.BuyCayUserFragView;
import com.ea.eamobile.nfsmw.view.CarSlotView;
import com.ea.eamobile.nfsmw.view.CarView;
import com.ea.eamobile.nfsmw.view.ResultInfo;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Nov 02 16:09:46 CST 2012
 * @since 1.0
 */
@Service
public class UserCarService {

    private static final Logger log = LoggerFactory.getLogger(UserCarService.class);

    @Autowired
    private UserCarMapper userCarMapper;
    @Autowired
    private CarService carService;
    @Autowired
    private PayService payService;
    @Autowired
    private UserCarSlotService userCarSlotService;
    @Autowired
    private UserService userService;
    @Autowired
    private CtaContentService ctaContentService;
    @Autowired
    private MemcachedClient cache;
    @Autowired
    private CarExtService carExtService;
    @Autowired
    private GotchaCarService gotchaCarService;
    @Autowired
    private UserCarFragmentService userCarFragmentService;
    @Autowired
    private BuyCarRecordService buyCarRecordService;
	@Autowired
	private SystemConfigService systemConfigService;
	@Autowired
	private UserChartletService userChartletService;
    @Autowired
    private CarChartletService chartletService;

    /**
     * 取用户车 id= user car only id
     * 
     * @param id
     * @return
     */
    public UserCar getUserCar(long id) {
        return userCarMapper.getUserCar(id);
    }

    public UserCar getUserCarByUserIdAndCarId(long userId, String carId) {
        return userCarMapper.getUserCarByUserIdAndCarId(userId, carId);
    }

    public List<UserCar> getUserCarList(long userId) {
        return userCarMapper.getUserCarList(userId);
    }

    public int insert(UserCar userCar) {
        clear(userCar.getUserId());
        return userCarMapper.insert(userCar);
    }

    public void update(UserCar userCar) {
        clear(userCar.getUserId());
        userCarMapper.update(userCar);
    }

    private void clear(long userId) {
        cache.delete(CacheKey.USER_CAR_MAP + userId);
    }

    public CarView getUserBestCarView(long userId) {
        String carId = (String) cache.get(CacheKey.USER_BEST_CAR + userId);
        if (carId == null) {
            int score = 0;
            List<UserCar> ucList = getUserCarList(userId);
            User u = userService.getUser(userId);
            for (UserCar ucTmp : ucList) {
                Car car = carService.getCar(ucTmp.getCarId());
                if (isUserCarOwned(car, ucTmp, u) && ucTmp.getScore() > score) {
                    score = ucTmp.getScore();
                    carId = ucTmp.getCarId();
                }
            }
            cache.set(CacheKey.USER_BEST_CAR + userId, carId, MemcachedClient.HOUR);
        }
        if (carId == null) {
            carId = "dodge_challenger_srt8_392_2011_desc";
        }
        return getUserCarView(userId, carId);
    }

    private boolean isUserCarOwned(Car car, UserCar userCar, User u) {
        boolean isOwned = false;

        int currentTime = (int) (System.currentTimeMillis() / 1000);

        boolean isLock = isCarLock(u, car);

        int status = getCarStatus(isLock, userCar, car, currentTime);
        isOwned = (status == CarConst.OWN);
        if (userCar != null && car.getId().equals(CarConst.FREE_CAR)) {
            isOwned = true;
        }
        return isOwned;
    }

    public List<UserCar> getUserCarOwnedList(long userId) {

        List<UserCar> result = new ArrayList<UserCar>();
        List<UserCar> ucList = getUserCarList(userId);
        User u = userService.getUser(userId);
        for (UserCar ucTmp : ucList) {
            Car car = carService.getCar(ucTmp.getCarId());
            if (isUserCarOwned(car, ucTmp, u)) {
                result.add(ucTmp);
            }
        }
        return result;
    }

    public int getUserCarOwnedCount(long userId) {

        int carCount = 0;
        List<UserCar> ucList = getUserCarList(userId);
        User u = userService.getUser(userId);
        for (UserCar ucTmp : ucList) {
            Car car = carService.getCar(ucTmp.getCarId());
            if (isUserCarOwned(car, ucTmp, u)) {
                carCount++;
            }
        }
        return carCount;
    }

    public int getUserCarOwnedTotalScore(long userId) {

        int totalScore = 0;
        List<UserCar> ucList = getUserCarList(userId);
        User u = userService.getUser(userId);
        for (UserCar ucTmp : ucList) {
            Car car = carService.getCar(ucTmp.getCarId());
            if (isUserCarOwned(car, ucTmp, u)) {
                totalScore += ucTmp.getScore() + getCarSlotScore(ucTmp);
            }
        }
        return totalScore;
    }

    public int getCarSlotScore(UserCar userCar) {
        int score = 0;
        List<CarSlotView> slots = userCarSlotService.getUserCarSlotViewList(userCar);
        if (slots != null) {
            for (CarSlotView slot : slots) {
                if (slot.getStatus() == 1) {// Enabled
                    score += slot.getScore();
                }
            }
        }
        return score;
    }

    public void clearUserBestCar(long userId) {
        cache.delete(CacheKey.USER_BEST_CAR + userId);
    }

    /**
     * 获取用户车辆Map<carId,usercar>
     * 
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, UserCar> getUserCarMap(long userId) {
        Map<String, UserCar> map = (Map<String, UserCar>) cache.get(CacheKey.USER_CAR_MAP + userId);
        if (map == null) {
            map = new HashMap<String, UserCar>();
            List<UserCar> list = getUserCarList(userId);
            if (list != null && list.size() > 0) {
                for (UserCar car : list) {
                    if (map.get(car.getCarId()) == null) {
                        map.put(car.getCarId(), car);
                    }
                }
            }
            cache.set(CacheKey.USER_CAR_MAP + userId, map, MemcachedClient.HOUR);
        }
        return map;
    }

    public List<CarView> getGarageCarList(long userId) {
        User u = userService.getUser(userId);
        List<CarView> result = new ArrayList<CarView>();
        // 获取全部车辆信息
        List<Car> cars = carService.getCarList();
        // 获取用户车辆信息 一个用户只能拥有一个型号的车 无需使用list
        Map<String, UserCar> userCars = getUserCarMap(userId);
        for (Car car : cars) {
            CarView carView = buildCarView(car, userCars.get(car.getId()), u, true);
            if (carView != null) {
                result.add(carView);
            }
        }
        return result;
    }

    public List<CarView> getGarageCarListByCarId(long userId, String CarId) {
        User u = userService.getUser(userId);
        List<CarView> result = new ArrayList<CarView>();

        Car car = carService.getCar(CarId);
        Map<String, UserCar> userCars = getUserCarMap(userId);
        if (car != null) {
            CarView carView = buildCarView(car, userCars.get(CarId), u, true);
            if (carView != null) {
                result.add(carView);
            }
        }

        return result;
    }

    public CarView getUserCarView(long userId, String CarId) {
        CarView carView = null;
        User u = userService.getUser(userId);
        Car car = carService.getCar(CarId);
        Map<String, UserCar> userCars = getUserCarMap(userId);
        if (car != null) {
            carView = buildCarView(car, userCars.get(CarId), u, true);
        }
        return carView;
    }

    /**
     * 根据字段car和usercar封装成carview
     * 
     * @param car
     * @param userCar
     * @return
     */
    private CarView buildCarView(Car car, UserCar userCar, User u, boolean checkUnlock) {
    	if (userCar == null && car.getId().equals("jaguar_cx16_concept_2012_desc"))
    		return null;
        int currentTime = (int) (System.currentTimeMillis() / 1000);
        CarExt carExt = carExtService.getCarExt(car.getId());
        if (carExt != null) {
            car.setEndTime(carExt.getEndTime());
            car.setStartTime(carExt.getStartTime());
            car.setPrice(carExt.getPrice());
            car.setPriceType(carExt.getPriceType());
            car.setVisible(carExt.getVisible());
            if (carExt.getVisible() == 1) {
                return null;
            }
        }
        CarView view = new CarView();
        view.setCarId(car.getId());
        view.setType(car.getType());
        view.setTier(car.getTier());
        view.setPrice(car.getPrice());
        view.setScore(car.getScore());
        view.setChartletId(userCar != null ? userCar.getChartletId() : 0);
        view.setUnlockMwNum(car.getUnlockMwNum());
        view.setPriceType(car.getPriceType());
        view.setSellFlag(car.getIsBestSell() * 4 + car.getIsHot() * 2 + car.getIsNew() * 1);
        view.setIsSpecialCar(car.getIsSpecialCar() == CarConst.IS_SPECIAL_CAR);
        boolean isLock = checkUnlock ? isCarLock(u, car) : false;

        view.setLock(isLock);
        int status = getCarStatus(isLock, userCar, car, currentTime);
        view.setStatus(status);
        if (userCar != null && car.getId().equals(CarConst.FREE_CAR)) {
            view.setLock(false);
            view.setStatus(CarConst.OWN);
        }
        if (userCar != null) {
            view.setLock(false);
            view.setStatus(CarConst.OWN);
        }
        // 运输中的车辆状态进行处理
        if (status == CarConst.TRANSPORTING) {
            int intervalTime = currentTime - userCar.getCreateTime();
            if (intervalTime >= car.getTransportTime()) {
                status = CarConst.OWN;
                userCar.setStatus(status);
                update(userCar);
            }
        }

        view.setRemainTime(car.getEndTime() - currentTime);
        // 获取车辆插槽信息
        if (userCar != null) {
            List<CarSlotView> slots = userCarSlotService.getUserCarSlotViewList(userCar);
            view.setSlots(slots);
        }
        return view;
    }
    
    public ResultInfo sendCar(long userId, String carId) {
    	ResultInfo result = new ResultInfo(false, "");
    	UserCar userCar = getUserCarByUserIdAndCarId(userId, carId);
    	
    	if (userCar != null) {
    		result.setMessage(ctaContentService.getCtaContent(CtaContentConst.HAVE_THE_CAR).getContent());
            return result;
    	}
    	
    	Car car = carService.getCar(carId);
    	car.setPrice(0);
    	result = payService.buy(car, userId);
        if (result.isSuccess()) {
            // insert car for user
            userCar = buildUserCar(userId, car);
            try {
                insert(userCar);
            } catch (Exception e) {
            }
            userCarSlotService.initLevelOneCarSlot(userCar);
            log.debug("send car success,carid={},userid={}", carId, userId);
        }

        return result;
    }
    
    public ResultInfo buyCar(long userId, String carId, boolean isFree) {
        BuyCarRecord buyCarRecord = new BuyCarRecord();
        ResultInfo result = new ResultInfo(false, "");
        UserCar userCar = getUserCarByUserIdAndCarId(userId, carId);
        boolean isFragCar = false;
        // 判断是否已购买
        if (userCar != null) {
            result.setMessage(ctaContentService.getCtaContent(CtaContentConst.HAVE_THE_CAR).getContent());
            return result;
        }
        Date now = new Date();
        Car car = carService.getCar(carId);
        if (car.getVisible() == 1 || car.getStartTime() > now.getTime() / 1000
                || (car.getEndTime() < now.getTime() / 1000 && car.getEndTime() > CarConst.NO_TIME_LIMIT)) {
            result.setMessage(ctaContentService.getCtaContent(CtaContentConst.CAN_NOT_BUY_CAR).getContent());
            return result;

        }
        BuyCayUserFragView buyCayUserFragView = new BuyCayUserFragView();
        if (isFree) {
            car.setPrice(0);
        } else {
            buyCayUserFragView = getHasFragPrice(userId, car, buyCarRecord);

            int hasFragPrice = buyCayUserFragView.getPrice();

            if (hasFragPrice < car.getPrice()) {
                isFragCar = true;
            }
            car.setPrice(hasFragPrice);
        }
        result = payService.buy(car, userId);
        if (result.isSuccess()) {
            // insert car for user

            userCar = buildUserCar(userId, car);
            try {
                insert(userCar);
            } catch (Exception e) {
            }
            userCarSlotService.initLevelOneCarSlot(userCar);
			// 添加买车送贴图活动,仅当在活动期间的金币车才有送所有贴图活动
			if(car.getPriceType() == 1 && DateUtil.isActivity(systemConfigService.getSystemConfig(Const.BUY_CAR_SEND_CHARTLET_START).getValue(), systemConfigService.getSystemConfig(Const.BUY_CAR_SEND_CHARTLET_END).getValue())){
				List<CarChartlet> chartletList = chartletService.getChartletsByCarId(car.getId());
				if(null != chartletList && !chartletList.isEmpty()){
					UserChartlet userChartlet = null;
					for(CarChartlet carChartlet : chartletList){
						userChartlet = new UserChartlet();
	                    userChartlet.setUserId(userId);
	                    userChartlet.setChartletId(carChartlet.getId());
	                    userChartlet.setIsOwned(1);
	                    userChartlet.setRentTime(new Timestamp(System.currentTimeMillis()));
						userChartletService.insert(userChartlet);
					}
					cache.delete(CacheKey.USER_CAR_CHARTLET_VIEW_LIST + userId + "_" + carId);
				}
			}
            if (isFragCar) {
                result.setMessage("购买剩余碎片成功，恭喜你获得车辆！");
                userCarFragmentService.update(buyCayUserFragView.getUserCarFragment());
                buyCarRecord.setCarId(carId);
                buyCarRecord.setSpendGoldNum(car.getPrice());
                buyCarRecord.setUserId(userId);
                buyCarRecordService.insert(buyCarRecord);

            }

            log.debug("buy car success,carid={},userid={}", carId, userId);
        }

        return result;
    }

    private BuyCayUserFragView getHasFragPrice(long userId, Car car, BuyCarRecord buyCarRecord) {
        BuyCayUserFragView buyCayUserFragView = new BuyCayUserFragView();
        int price = car.getPrice();
        GotchaCar gotchaCar = gotchaCarService.getGotchaCar(car.getId());
        if (gotchaCar == null) {
            buyCayUserFragView.setPrice(price);
            return buyCayUserFragView;
        }
        UserCarFragment userCarFragment = userCarFragmentService.getUserCarFragment(userId, gotchaCar.getPartId());
        if (userCarFragment == null || userCarFragment.getCount() == 0) {
            buyCayUserFragView.setPrice(price);
            return buyCayUserFragView;
        }
        buyCarRecord.setFragNum(userCarFragment.getCount());
        float count = Math.min(userCarFragment.getCount(), gotchaCar.getPartNum());
        price = (int) (car.getPrice() * (1 - Math.pow((count / gotchaCar.getPartNum()), 2)));
        buyCayUserFragView.setPrice(price);
        userCarFragment.setCount(Math.max(userCarFragment.getCount() - gotchaCar.getPartNum(), 0));
        buyCayUserFragView.setUserCarFragment(userCarFragment);
        return buyCayUserFragView;
    }

    private UserCar buildUserCar(long userId, Car car) {
        UserCar userCar = new UserCar();
        userCar.setCarId(car.getId());
        userCar.setUserId(userId);
        userCar.setScore(car.getScore());
        userCar.setStatus(car.needTransport() ? CarConst.TRANSPORTING : CarConst.OWN);
        userCar.setChartletId(0);
        userCar.setCreateTime((int) (System.currentTimeMillis() / 1000));
        return userCar;
    }

    public void save(long userId, String carId) {
        Car car = carService.getCar(carId);
        UserCar userCar = getUserCarByUserIdAndCarId(userId, carId);
        if (car != null && userCar == null) {
            UserCar ucar = buildUserCar(userId, car);
            insert(ucar);
            userCarSlotService.initLevelOneCarSlot(ucar);
        }
    }

    public boolean isTheSameCar(String carId, long userCarId) {
        UserCar userCar = getUserCar(userCarId);
        return userCar != null && userCar.getCarId().equals(carId);
    }

    /**
     * 判断是否拥有赛道限制的车辆
     * 
     * @param userId
     * @param carIds
     * @return
     */
    public boolean hasLimitCar(long userId, List<String> carIds) {
        Map<String, UserCar> userCars = getUserCarMap(userId);
        if (userCars == null || userCars.size() == 0) {
            return false;
        }
        for (String carId : carIds) {
            if (userCars.containsKey(carId)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCarLock(User user, Car car) {
        boolean result = true;
        if (user.getStarNum() >= car.getUnlockMwNum()) {
            result = false;
        }
        return result;

    }

    private int getCarStatus(boolean islock, UserCar userCar, Car car, int currentTime) {
        int result = CarConst.LOCK;
        if (!islock) {
            if (userCar != null) {
                result = CarConst.OWN;
            } else {
                if (car.getEndTime() == CarConst.NO_TIME_LIMIT && car.getStartTime() == CarConst.NO_TIME_LIMIT) {
                    result = CarConst.UNLOCK;
                } else if (currentTime > car.getStartTime() && currentTime < car.getEndTime()) {
                    result = CarConst.TIME_LIMITED_AVAILABLE;
                } else {
                    result = CarConst.TIME_LIMITED;
                }
            }
        }
        return result;
    }

    /**
     * 获取用户当前tier的解锁车辆
     * 
     * @param user
     * @param currentMwNum
     * @return
     */
    public List<CarView> getUnlockCarViews(User user, int originalMwNum, int currentMwNum) {
        List<CarView> result = Collections.emptyList();
        List<Car> cars = carService.getUnlockCars(user.getTier(), currentMwNum, originalMwNum);
        if (cars.size() > 0) {
            Map<String, UserCar> userCars = getUserCarMap(user.getId());
            result = new ArrayList<CarView>();
            for (Car car : cars) {
                // 排除掉已有的
                UserCar userCar = userCars.get(car.getId());
                if (userCar != null)
                    continue;
                CarView carView = buildCarView(car, null, user, false);
                if (carView != null) {
                    result.add(carView);
                }
            }
        }
        return result;
    }

    public List<CarView> getUnlockCarViewsByTier(User user) {
        List<CarView> result = Collections.emptyList();
        List<Car> cars = carService.getUnlockCarsByTier(user.getTier(), user.getStarNum());
        if (cars.size() > 0) {
            Map<String, UserCar> userCars = getUserCarMap(user.getId());
            result = new ArrayList<CarView>();
            for (Car car : cars) {
                // 排除掉已有的
                UserCar userCar = userCars.get(car.getId());
                if (userCar != null)
                    continue;
                CarView carView = buildCarView(car, null, user, false);
                if (carView != null) {
                    result.add(carView);
                }
            }
        }
        return result;
    }

    /**
     * only test action use
     * 
     * @param id
     */
    public void deleteById(long id) {
        UserCar car = getUserCar(id);
        if (car != null) {
            clear(car.getUserId());
        }
        userCarMapper.deleteById(id);
    }

    public List<UserCar> getUserCarList(long from, long to) {
        try {
            QueryRunner run = new QueryRunner(RWSplit.getInstance().getBackupReadDataSource());
            return run.query("select id,user_id,score from user_car where id >= ? and id< ?",
                    new UserCarListHandler(), from, to);
        } catch (SQLException e) {
            log.error("read user car db err", e);
        }
        return Collections.emptyList();
    }
    
    public int getMaxId(){
        try {
            QueryRunner run = new QueryRunner(RWSplit.getInstance().getBackupReadDataSource());
            Integer max = run.query("select max(id) from user_car",
                    new Inthandler());
            if(max!=null){
                return max;
            }
        } catch (SQLException e) {
            log.error("read user car slot db err",e);
        }
        return 0;
    }
    public int getFirstId(){
        try {
            QueryRunner run = new QueryRunner(RWSplit.getInstance().getBackupReadDataSource());
            Integer max = run.query("select id from user_car limit 1",
                    new Inthandler());
            if(max!=null){
                return max;
            }
        } catch (SQLException e) {
            log.error("read user car slot db err",e);
        }
        return 0;
    }
}