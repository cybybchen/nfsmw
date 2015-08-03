package com.ea.eamobile.nfsmw.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.model.CareerGhost;
import com.ea.eamobile.nfsmw.model.handler.IntListHandler;
import com.ea.eamobile.nfsmw.model.mapper.CareerGhostMapper;
import com.ea.eamobile.nfsmw.utils.NumberUtil;
import com.ea.eamobile.nfsmw.utils.RWSplit;
import com.ea.eamobile.nfsmw.view.CareerGhostListView;

/**
 * @author ma.ruofei
 * @version 1.0 Mon May 20 14:47:25 CST 2013
 * @since 1.0
 */
@Service
public class CareerGhostService {

    protected static final Logger log = LoggerFactory.getLogger(CareerGhostService.class);
    @Autowired
    private CareerGhostMapper careerGhostMapper;
    @Autowired
    private RaceModeService raceModeService;

    private static Map<String, CareerGhostListView> pool = new ConcurrentHashMap<String, CareerGhostListView>();

    private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    private static final int expiredTime = 60 * 60 * 1000;

    private static final int notSearch = 0;

    private static final int isTheMaxTime = 1;

    private static final int isNotMaxTime = 2;

    private static final int isTheMinTime = 1;

    private static final int isNotMinTime = 2;

    public CareerGhost getCareerGhost(int id) {
        return careerGhostMapper.getCareerGhost(id);
    }

    public List<CareerGhost> getCareerGhostList() {
        return careerGhostMapper.getCareerGhostList();
    }

    public int insert(CareerGhost careerGhost) {
        return careerGhostMapper.insert(careerGhost);
    }

    public void update(CareerGhost careerGhost) {
        careerGhostMapper.update(careerGhost);
    }

    public void deleteById(int id) {
        careerGhostMapper.deleteById(id);
    }

    public CareerGhost getCareerGhostByUserIdAndModeType(long userId, int modeType) {
        return careerGhostMapper.getCareerGhostByUserIdAndModeType(userId, modeType);
    }

    public CareerGhost getCareerGhostByRaceTimeFromPool(float raceTime, int modeType) {
        String mapKey = buildMapKey(modeType, raceTime);
        CareerGhostListView careerGhostListView = getCareerGhostListViewFromPool(mapKey);
        if (careerGhostListView == null) {
            careerGhostListView = getCareerGhostListViewByRaceTime(raceTime, modeType, false, 0);

        }
        Integer id = NumberUtil.randomList(careerGhostListView.getCareerIdList());
        if (id == null) {
            id = 0;
        }
        CareerGhost careerGhost = getCareerGhost(id);
        return careerGhost;
    }

    private CareerGhostListView getCareerGhostListViewFromPool(String key) {
        CareerGhostListView careerGhostListView = pool.get(key);
        if (careerGhostListView != null
                && (System.currentTimeMillis() - careerGhostListView.getCreateTime() > expiredTime)) {
            careerGhostListView = null;
            pool.remove(key);
        }
        return careerGhostListView;
    }

    public CareerGhost getCareerGhostFromTimeRange(float maxRaceTime, float minRaceTime, int modeType,
            int searchDirection) {
        if (minRaceTime >= maxRaceTime) {
            return null;
        }
        BigDecimal maxracebd = new BigDecimal(maxRaceTime);
        maxRaceTime = maxracebd.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        BigDecimal minracebd = new BigDecimal(minRaceTime);
        minRaceTime = minracebd.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        CareerGhost result = null;
        List<CareerGhostListView> careerGhostListViews = new ArrayList<CareerGhostListView>();
        Integer allGhostNum = 0;
        for (float time = minRaceTime; time < maxRaceTime; time = time + 0.1f) {
            String mapKey = buildMapKey(modeType, time);
            CareerGhostListView careerGhostListView = getCareerGhostListViewFromPool(mapKey);
            if (careerGhostListView == null) {
                careerGhostListView = getCareerGhostListViewByRaceTime(time, modeType, false, 0);
            }
            careerGhostListViews.add(careerGhostListView);
            allGhostNum = allGhostNum + careerGhostListView.getGhostNum();

        }
        if (allGhostNum == 0 && searchDirection == Const.CHECK_IS_LEAST_RACETIME) {
            for (float time = minRaceTime; time >= 0; time = (float) (time - 0.1)) {
                String mapKey = buildMapKey(modeType, time);
                CareerGhostListView careerGhostListView = getCareerGhostListViewFromPool(mapKey);
                if (careerGhostListView == null || careerGhostListView.getIsLeastRaceTime() == notSearch) {
                    careerGhostListView = getCareerGhostListViewByRaceTime(time, modeType, true, searchDirection);
                }
                careerGhostListViews.add(careerGhostListView);
                allGhostNum = allGhostNum + careerGhostListView.getGhostNum();

                if (allGhostNum > 0 || careerGhostListView.getIsLeastRaceTime() == isTheMinTime) {
                    Integer id = NumberUtil.randomList(careerGhostListView.getCareerIdList());
                    if (id == null) {
                        id = 0;
                    }
                    result = careerGhostMapper.getCareerGhost(id);
                    return result;
                }
            }
        } else if (allGhostNum == 0 && searchDirection == Const.CHECK_IS_MOST_RACETIME) {
            for (float time = maxRaceTime; time <= Const.MAX_RACETIME; time = time + 0.1f) {
                String mapKey = buildMapKey(modeType, time);
                CareerGhostListView careerGhostListView = getCareerGhostListViewFromPool(mapKey);
                if (careerGhostListView == null || careerGhostListView.getIsMostRaceTime() == notSearch) {
                    careerGhostListView = getCareerGhostListViewByRaceTime(time, modeType, true, searchDirection);
                }
                careerGhostListViews.add(careerGhostListView);
                allGhostNum = allGhostNum + careerGhostListView.getGhostNum();

                if (allGhostNum > 0 || careerGhostListView.getIsMostRaceTime() == isTheMaxTime) {
                    Integer id = NumberUtil.randomList(careerGhostListView.getCareerIdList());
                    if (id == null) {
                        id = 0;
                    }
                    result = careerGhostMapper.getCareerGhost(id);
                    return result;
                }
            }
        }
        if (allGhostNum == 0) {
            return result;
        }
        int randomNum = NumberUtil.randomNumber(allGhostNum);
        int allSize = 0;
        for (int i = 0; i < careerGhostListViews.size(); i++) {
            CareerGhostListView careerGhostListView = careerGhostListViews.get(i);
            allSize = allSize + careerGhostListView.getGhostNum();
            if (allSize >= randomNum) {
                Integer id = NumberUtil.randomList(careerGhostListView.getCareerIdList());
                if (id == null) {
                    id = 0;
                }
                result = careerGhostMapper.getCareerGhost(id);
                return result;
            }
        }
        return result;
    }

    private String buildMapKey(int modeType, float time) {
        BigDecimal racebd = new BigDecimal(time);
        time = racebd.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        return String.valueOf(modeType) + "-" + String.valueOf(time);
    }

    private List<Integer> getCareerGhostListByRaceTimeAndModeType(float raceTime, int modeType) {
        try {
            QueryRunner run = new QueryRunner(RWSplit.getInstance().getBackupReadDataSource());
            return run.query("SELECT id from career_ghost where race_time=? and mode_type=? "
                    + "order by id desc limit 100", new IntListHandler(), raceTime, modeType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private CareerGhostListView getCareerGhostListViewByRaceTime(float raceTime, int modeType, boolean needCheckBorder,
            int checkStatus) {
        String mapKey = buildMapKey(modeType, raceTime);
        BigDecimal racebd = new BigDecimal(raceTime);
        raceTime = racebd.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        CareerGhostListView careerGhostListView = new CareerGhostListView();
        int ghostListNum = careerGhostMapper.getCareerGhostNumByRaceTime(raceTime, modeType);
        List<Integer> careerGhostIds = getCareerGhostListByRaceTimeAndModeType(raceTime, modeType);
        if (careerGhostIds == null) {
            careerGhostIds = new ArrayList<Integer>();
        }
        careerGhostListView.setGhostNum(ghostListNum);
        careerGhostListView.setCareerIdList(careerGhostIds);
        careerGhostListView.setCreateTime(System.currentTimeMillis());
        if (needCheckBorder && checkStatus == Const.CHECK_IS_LEAST_RACETIME) {
            int status = isTheMinTime;
            if (careerGhostMapper.getLessRaceTimeCareerGhostNumByRaceTime(raceTime, modeType) > 0) {
                status = isNotMinTime;
            }
            careerGhostListView.setIsLeastRaceTime(status);
        }
        if (needCheckBorder && checkStatus == Const.CHECK_IS_MOST_RACETIME) {
            int status = isTheMaxTime;
            if (careerGhostMapper.getMoreRaceTimeCareerGhostNumByRaceTime(raceTime, modeType) > 0) {
                status = isNotMaxTime;
            }
            careerGhostListView.setIsMostRaceTime(status);
        }
        pool.put(mapKey, careerGhostListView);
        return careerGhostListView;
    }

    public void initPool() {
        log.warn("fill career ghost pool start....");
        List<Integer> modeTypeList = raceModeService.getFirstModeType();
        for (Integer modeType : modeTypeList) {
            FillGhostThread thread = new FillGhostThread(modeType);
            threadPool.execute(thread);
        }
        threadPool.shutdown();
    }

    private class FillGhostThread extends Thread {

        private int modeType;

        public FillGhostThread(Integer modeType) {
            this.modeType = modeType;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            Float minRaceTime = careerGhostMapper.getCareerGhostMinRaceTimeByModeType(modeType);
            if (minRaceTime == null) {
                return;
            }
            Float maxRaceTime = careerGhostMapper.getCareerGhostMaxRaceTimeByModeType(modeType);
            if (maxRaceTime == null) {
                return;
            }
            if (minRaceTime > Const.MAX_RACETIME) {
                return;
            }
            if (maxRaceTime > Const.MAX_RACETIME) {
                maxRaceTime = Const.MAX_RACETIME;
            }
            for (float time = minRaceTime; time < maxRaceTime; time = time + 0.1f) {
                String mapKey = buildMapKey(modeType, time);
                CareerGhostListView careerGhostListView = getCareerGhostListViewFromPool(mapKey);
                if (careerGhostListView == null) {
                    careerGhostListView = getCareerGhostListViewByRaceTime(time, modeType, false, 0);
                }
            }
            log.warn("modeType:" + modeType + " minRaceTime:" + minRaceTime + " maxRaceTime:" + maxRaceTime
                    + " costTime:" + (System.currentTimeMillis()-startTime));
        }

    }
}