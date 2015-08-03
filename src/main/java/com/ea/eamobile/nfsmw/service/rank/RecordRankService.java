package com.ea.eamobile.nfsmw.service.rank;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.CareerBestRacetimeRecord;
import com.ea.eamobile.nfsmw.model.Leaderboard;
import com.ea.eamobile.nfsmw.model.RaceMode;
import com.ea.eamobile.nfsmw.service.CareerBestRacetimeRecordService;
import com.ea.eamobile.nfsmw.service.LeaderboardService;
import com.ea.eamobile.nfsmw.service.RaceModeService;

@Service
public class RecordRankService {
    protected static final Logger log = LoggerFactory.getLogger(RecordRankService.class);
    @Autowired
    CareerBestRacetimeRecordService recordService;
    @Autowired
    RaceModeService modeService;
    @Autowired
    BaseRankUtil rankUtil;
    @Autowired
    LeaderboardService leaderboardService;
    // <modetype,<round(racetime),count>> racetime must be order
    private static Map<Integer, Map<Integer, Integer>> pool = new ConcurrentHashMap<Integer, Map<Integer, Integer>>();
    private static final int NUM = 1000000;
    // private static final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public void init() {
        int maxId = recordService.getMaxId();
        log.warn("init record rank starting...,max id = {}", maxId);
        if (maxId == 0) {
            return;
        }
        int from = 0;
        int to = NUM;
        while (true) {
            if (from >= maxId) {
                log.warn("all record data read done!!!");
                return;
            }
            log.warn("read from={}, to={}", from, to);
            List<CareerBestRacetimeRecord> list = recordService.getList(from, to);
            // build memory map
            for (CareerBestRacetimeRecord rec : list) {
                if (rec == null)
                    continue;
                Map<Integer, Integer> modeMap = pool.get(rec.getModeType());
                RaceMode mode = modeService.getModeById(rec.getModeType());
                if (mode == null)
                    continue;
                if (modeMap == null) {
                    Comparator<Integer> com = rankUtil.getComparator(mode.getRankType());
                    modeMap = new TreeMap<Integer, Integer>(com);
                    pool.put(mode.getModeType(), modeMap);
                }
                int value = 0;
                if (mode.getRankType() == Match.MODE_RANK_TYPE_BY_TIME) {
                    value = rankUtil.format(rec.getRaceTime());
                } else {
                    value = rankUtil.format(rec.getAverageSpeed());
                }
                Integer count = modeMap.get(value);
                if (count == null) {
                    count = 0;
                }
                modeMap.put(value, count + 1);
            }
            log.warn("read done. from={}, to={}, size=" + list.size(), from, to);
            from = to;
            to = from + NUM;
        }
    }

    /**
     * 2种排序的通用方法
     * 
     * @param mode
     * @param raceTime
     * @return
     */
    public int getRank(RaceMode mode, float raceTime, long userId) {
        List<Leaderboard> leaderboards = leaderboardService.getLeaderboardByMode(mode);
        if (leaderboards != null && leaderboards.size() > 0) {
            for (int i = 0; i < leaderboards.size(); i++) {
                Leaderboard leaderboard = leaderboards.get(i);
                if (leaderboard.getUserId() == userId) {
                    return -1;
                }
            }
        }
        if (mode == null || raceTime == 0) {
            return 0;
        }
        Map<Integer, Integer> modeMap = pool.get(mode.getModeType());
        int time = rankUtil.format(raceTime);
        if (modeMap == null) {
            Comparator<Integer> com = rankUtil.getComparator(mode.getRankType());
            modeMap = new TreeMap<Integer, Integer>(com);
            modeMap.put(time, 1);
            pool.put(mode.getModeType(), modeMap);
            return 1;
        }
        // 遍历有序列表查询名次
        int rank = 0;
        for (Map.Entry<Integer, Integer> entry : modeMap.entrySet()) {
            int key = entry.getKey();
            int count = entry.getValue();
            if (key == time) {
                rank += count / 2;
                break;
            }
            rank += count;
        }
        return Math.min(rank, 9999);
    }

    public void update(RaceMode mode, CareerBestRacetimeRecord oldRecord, float newTime, float newSpeed) {
        float old = 0;
        if (mode.getRankType() == Match.MODE_RANK_TYPE_BY_TIME) {
            if (oldRecord != null) {
                old = oldRecord.getRaceTime();
            }
            update(mode, old, newTime);
        } else {
            if (oldRecord != null) {
                old = oldRecord.getAverageSpeed();
            }
            update(mode, old, newSpeed);
        }
    }

    /**
     * only user update best record to call 更新要按照mode ranktype
     * 
     * @param mode
     * @param raceTime
     */
    private void update(RaceMode mode, float oldTime, float newTime) {
        if (mode == null || newTime == 0) {
            return;
        }
        Map<Integer, Integer> modeMap = pool.get(mode.getModeType());
        int oldt = rankUtil.format(oldTime);
        int newt = rankUtil.format(newTime);
        if (modeMap == null) {
            Comparator<Integer> com = rankUtil.getComparator(mode.getRankType());
            modeMap = new TreeMap<Integer, Integer>(com);
            modeMap.put(newt, 1);
            pool.put(mode.getModeType(), modeMap);
            return;
        }
        // 先找到老成绩并-1 0说明是新记录
        if (oldt != 0) {
            Integer oldTimeCount = modeMap.get(oldt);
            if (oldTimeCount == null) {
                oldTimeCount = 0;
            }
            modeMap.put(oldt, Math.max(0, oldTimeCount - 1));
        }
        // 找到新成绩并+1
        Integer newTimeCount = modeMap.get(newt);
        if (newTimeCount == null) {
            newTimeCount = 0;
        }
        modeMap.put(newt, newTimeCount + 1);
    }

}
