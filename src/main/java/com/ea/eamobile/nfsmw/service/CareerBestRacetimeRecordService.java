package com.ea.eamobile.nfsmw.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.CareerBestRacetimeRecord;
import com.ea.eamobile.nfsmw.model.RaceMode;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.handler.UserBestRaceTimeRecordListHandler;
import com.ea.eamobile.nfsmw.model.mapper.CareerBestRacetimeRecordMapper;
import com.ea.eamobile.nfsmw.service.command.moderank.ModeRankTypeHandler;
import com.ea.eamobile.nfsmw.service.command.moderank.ModeRankTypeHandlerFactory;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;
import com.ea.eamobile.nfsmw.service.rank.RecordRankService;
import com.ea.eamobile.nfsmw.utils.RWSplit;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Nov 05 15:58:43 CST 2012
 * @since 1.0 注意：此记录表中的modeid实际上为modeType
 */
@Service
public class CareerBestRacetimeRecordService {

    @Autowired
    private CareerBestRacetimeRecordMapper recordMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ModeRankTypeHandlerFactory rankTypeHandlerFactory;
    @Autowired
    private MemcachedClient cache;
    @Autowired
    RecordRankService rankService;

    private String buildRecordKey(long userId, int modeType) {
        return CacheKey.USER_CAREER_RECORD + userId + "_" + modeType;
    }

    public CareerBestRacetimeRecord getCareerBestRacetimeRecord(long userId, int modeType) {
        CareerBestRacetimeRecord record = (CareerBestRacetimeRecord) cache.get(buildRecordKey(userId, modeType));
        if (record == null) {
            record = recordMapper.getCareerBestRacetimeRecordByModeIdAndUserId(userId, modeType);
            cache.set(buildRecordKey(userId, modeType), record, MemcachedClient.HOUR);
        }
        return record;
    }

    public int insert(CareerBestRacetimeRecord record) {
        cache.delete(buildRecordKey(record.getUserId(), record.getModeType()));
        return recordMapper.insert(record);
    }

    public void update(CareerBestRacetimeRecord record) {
        cache.delete(buildRecordKey(record.getUserId(), record.getModeType()));
        recordMapper.update(record);
    }

    /**
     * 取批量用户的记录列表
     * 
     * @param tokens
     * @param mode
     * @return
     */
    public List<CareerBestRacetimeRecord> getUserRecordList(List<String> tokens, RaceMode mode) {
        List<CareerBestRacetimeRecord> result = Collections.emptyList();
        if (tokens != null && tokens.size() > 0) {
            result = new ArrayList<CareerBestRacetimeRecord>();
            // TODO
            List<User> friends = userService.getUsersByTokens(tokens.toArray(new String[tokens.size()]));
            // 取好友的最佳记录列表
            for (User friend : friends) {
                if (friend != null) {
                    CareerBestRacetimeRecord record = getCareerBestRacetimeRecord(friend.getId(), mode.getModeType());
                    if (record != null) {
                        record.setUser(friend);// 用户信息set进去方便使用
                        result.add(record);
                    }
                }
            }
            // 排序
            ModeRankTypeHandler handler = rankTypeHandlerFactory.create(mode.getRankType());
            handler.sortRecord(result);
        }
        return result;
    }

    public List<CareerBestRacetimeRecord> getTopTenByAverageSpeed(int modeType) {
        return recordMapper.getTopTenByAverageSpeed(modeType);
    }

    public List<CareerBestRacetimeRecord> getTopTenByRaceTime(int modeType) {
        return recordMapper.getTopTenByRaceTime(modeType);
    }

    public void deleteByUserId(long id) {
        // first clear cache
        List<CareerBestRacetimeRecord> list = recordMapper.getByUserId(id);
        if (list != null && list.size() > 0) {
            for (CareerBestRacetimeRecord record : list) {
                cache.delete(buildRecordKey(id, record.getModeType()));
            }
        }
        recordMapper.deleteByUserId(id);
    }

    /**
     * 获取实时排名 TODO cache
     * 
     * @param mode
     * @param raceTime
     * @param userId
     * @param averageSpeed
     * @return
     */
    public int getRank(RaceMode mode, CareerBestRacetimeRecord record, long userId) {
        if (record == null) {
            return 0;
        }
        int result = 0;
        switch (mode.getRankType()) {
        case Match.MODE_RANK_TYPE_BY_TIME:
            // result = recordMapper.getRank(mode.getModeType(), record.getRaceTime(), userId);
            result = rankService.getRank(mode, record.getRaceTime(), userId);
            break;
        case Match.MODE_RANK_TYPE_BY_AVGSPEED:
            // result = recordMapper.getRankBySpeed(mode.getModeType(), record.getAverageSpeed(), userId);
            result = rankService.getRank(mode, record.getAverageSpeed(), userId);
            break;
        default:
            break;
        }

        return result + 1;
    }

    public List<CareerBestRacetimeRecord> getList(int from, int to) {
        try {
            QueryRunner run = new QueryRunner(RWSplit.getInstance().getBackupReadDataSource());
            return run.query("select id,mode_type,race_time,average_speed from career_best_racetime_record"
                    + " where id >= ? and id< ?", new UserBestRaceTimeRecordListHandler(), from, to);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public int getMaxId() {
        Integer id = recordMapper.getMaxId();
        if (id == null) {
            id = 0;
        }
        return id;
    }
}