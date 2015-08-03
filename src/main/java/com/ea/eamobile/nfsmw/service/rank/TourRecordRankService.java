package com.ea.eamobile.nfsmw.service.rank;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.Tournament;
import com.ea.eamobile.nfsmw.model.TournamentOnline;
import com.ea.eamobile.nfsmw.model.TournamentUser;
import com.ea.eamobile.nfsmw.service.TournamentLeaderboardService;
import com.ea.eamobile.nfsmw.service.TournamentOnlineService;
import com.ea.eamobile.nfsmw.service.TournamentService;
import com.ea.eamobile.nfsmw.service.TournamentUserService;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

@Service
public class TourRecordRankService {

    @Autowired
    private TournamentOnlineService onlineService;
    @Autowired
    private TournamentUserService tuserService;
    @Autowired
    private TournamentService tourService;
    @Autowired
    private BaseRankUtil rankUtil;
    @Autowired
    private TournamentLeaderboardService lbService;
    @Autowired
    private MemcachedClient cache;

    protected static final Logger log = LoggerFactory.getLogger(TourRecordRankService.class);

    private static final int MIN_RANK = 9999;

    // <onlineid-classid,Map<round(racetime),count>> racetime must be order
    private static Map<String, Map<Float, Integer>> pool = new ConcurrentHashMap<String, Map<Float, Integer>>();

    public void init() {
        List<TournamentOnline> onlines = onlineService.getInProgressOnlineList();
        if (onlines != null && onlines.size() > 0) {
            for (TournamentOnline online : onlines) {
                log.warn("read tour user starting,onlineid= {}", online.getId());
                BuildPoolThread thread = new BuildPoolThread();
                thread.setOnlineId(online.getId());
                thread.start();
            }
        }
    }

    private class BuildPoolThread extends Thread {
        private int onlineId;

        public void setOnlineId(int onlineId) {
            this.onlineId = onlineId;
        }

        @Override
        public void run() {
            TournamentOnline online = onlineService.getTournamentOnline(onlineId);
            if (online == null) {
                return;
            }
            Tournament tour = tourService.getTournament(online.getTournamentId());
            if (tour == null) {
                return;
            }
            List<TournamentUser> list = null;
            try {
                list = tuserService.getTourUserList(onlineId);
            } catch (SQLException e) {
                log.error("read tour user list err", e);
            }
            if (list == null || list.size() == 0) {
                return;
            }
            for (TournamentUser user : list) {
                addRank(user, tour.getType());
            }
            log.warn("read tour user record done,onlineid= {}", onlineId);
        }
    }

    private String buildKey(int onlineId, int classId) {
        return onlineId + "_" + classId;
    }

    public int getRank(TournamentUser tuser, int rankType) {
        int rank = lbService.getRankFromLeaderboard(tuser, rankType);
        // 1.用户在lb里面
        if (rank > 0) {
            return rank;
        }
        if (tuser == null || tuser.getResult() == 0) {
            return MIN_RANK;
        }
        int onlineId = tuser.getTournamentOnlineId();
        int classId = tuser.getClassId();
        // 2. 如果online结束了 从db查然后保存到cache
        if (onlineService.isFinishOnline(onlineId)) {
            return getCacheRank(tuser, rankType, onlineId, MemcachedClient.DAY * 10);
        }
        // 3. 没结束的 从内存计算获取
        String key = buildKey(onlineId, classId);
        Map<Float, Integer> map = pool.get(key);
        if (map == null) {
            // 原则上不会空，20130521更新发现加载线程在tomcat启动后完成 导致map空
            return getCacheRank(tuser, rankType, onlineId, MemcachedClient.MIN * 10);
        }
        float time = getValue(onlineId, tuser.getResult(), tuser.getAverageSpeed());
        // 遍历有序列表查询名次
        for (Map.Entry<Float, Integer> entry : map.entrySet()) {
            float racetime = entry.getKey();
            int count = entry.getValue();
            if (racetime == time) {
                rank += 1;
                break;
            }
            rank += count;
            if (rank >= MIN_RANK) {
                return MIN_RANK;
            }
        }
        return rank;
    }

    private int getCacheRank(TournamentUser tuser, int rankType, int onlineId, int cacheTime) {
        String rankKey = buildRankKey(onlineId, tuser.getUserId());
        Integer cacheRank = (Integer) cache.get(rankKey);
        if (cacheRank == null) {
            cacheRank = tuserService.getRank(tuser, rankType);
            cache.set(rankKey, cacheRank, cacheTime);
        }
        return cacheRank;
    }

    /**
     * 添加排名到内存
     * 
     * @param user
     * @param rankType
     * @return
     */
    public void addRank(TournamentUser user, int rankType) {
        String key = buildKey(user.getTournamentOnlineId(), user.getClassId());
        float value = rankType == Match.MODE_RANK_TYPE_BY_TIME ? user.getResult() : user.getAverageSpeed();
        if (value == 0) {
            return;
        }
        Map<Float, Integer> map = pool.get(key);
        if (map == null) {
            Comparator<Float> com = rankUtil.getFComparator(rankType);
            map = new TreeMap<Float, Integer>(com);
            pool.put(key, map);
            map.put(value, 1);
        } else {
            Integer count = map.get(value);
            map.put(value, count != null ? count + 1 : 1);
        }
    }

    public void update(TournamentUser oldUser, float racetime, float speed) {
        if (oldUser == null) {
            return;
        }
        int onlineId = oldUser.getTournamentOnlineId();
        float oldt = getValue(onlineId, oldUser.getResult(), oldUser.getAverageSpeed());
        float newt = getValue(onlineId, racetime, speed);
        if (newt == 0)
            return;
        String key = buildKey(oldUser.getTournamentOnlineId(), oldUser.getClassId());
        Map<Float, Integer> map = pool.get(key);
        if (map == null) {
            return;
        }
        // 先找到老成绩并-1 0说明是新记录
        if (oldt != 0) {
            Integer oldTimeCount = map.get(oldt);
            if (oldTimeCount != null) {
                map.put(oldt, Math.max(0, oldTimeCount - 1));
            }
        }
        // 找到新成绩并+1
        Integer newTimeCount = map.get(newt);
        map.put(newt, newTimeCount == null ? 1 : newTimeCount + 1);
    }

    private float getValue(int onlineId, float racetime, float speed) {
        TournamentOnline online = onlineService.getTournamentOnline(onlineId);
        if (online == null) {
            return 0;
        }
        Tournament tour = tourService.getTournament(online.getTournamentId());
        if (tour == null) {
            return 0;
        }
        float value = 0;
        if (tour.getType() == Match.MODE_RANK_TYPE_BY_TIME) {
            value = racetime;
        } else {
            value = speed;
        }
        return value;
    }

    private String buildRankKey(int onlineId, long userId) {
        return CacheKey.TOUR_USER_RANK + onlineId + "_" + userId;
    }
}
