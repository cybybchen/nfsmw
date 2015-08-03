package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.LeaderboardChangeRecord;
import com.ea.eamobile.nfsmw.model.mapper.LeaderboardChangeRecordMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Feb 27 14:34:50 CST 2013
 * @since 1.0
 */
@Service
public class LeaderboardChangeRecordService {

    @Autowired
    private LeaderboardChangeRecordMapper leaderboardChangeRecordMapper;

    public LeaderboardChangeRecord getLeaderboardChangeRecord(int id) {
        return leaderboardChangeRecordMapper.getLeaderboardChangeRecord(id);
    }

    public LeaderboardChangeRecord getLeaderboardChangeRecordByUserIdAndRaceTime(long userId, float raceTime) {
        return leaderboardChangeRecordMapper.getLeaderboardChangeRecordByUserIdAndRaceTime(userId, raceTime);
    }

    public LeaderboardChangeRecord getLeaderboardChangeRecordByUserIdAndAverageSpeed(long userId, float averageSpeed) {
        return leaderboardChangeRecordMapper.getLeaderboardChangeRecordByUserIdAndAverageSpeed(userId, averageSpeed);
    }

    public List<LeaderboardChangeRecord> getLeaderboardChangeRecordList() {
        return leaderboardChangeRecordMapper.getLeaderboardChangeRecordList();
    }

    public int insert(LeaderboardChangeRecord leaderboardChangeRecord) {
        return leaderboardChangeRecordMapper.insert(leaderboardChangeRecord);
    }

    public void update(LeaderboardChangeRecord leaderboardChangeRecord) {
        leaderboardChangeRecordMapper.update(leaderboardChangeRecord);
    }

    public void deleteById(int id) {
        leaderboardChangeRecordMapper.deleteById(id);
    }

}