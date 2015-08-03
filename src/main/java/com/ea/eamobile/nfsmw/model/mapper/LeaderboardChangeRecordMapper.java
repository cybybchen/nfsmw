package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.LeaderboardChangeRecord;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Feb 27 14:34:50 CST 2013
 * @since 1.0
 */
public interface LeaderboardChangeRecordMapper {

    public LeaderboardChangeRecord getLeaderboardChangeRecord(int id);

    public List<LeaderboardChangeRecord> getLeaderboardChangeRecordList();

    public int insert(LeaderboardChangeRecord leaderboardChangeRecord);

    public void update(LeaderboardChangeRecord leaderboardChangeRecord);

    public void deleteById(int id);
    
    public LeaderboardChangeRecord getLeaderboardChangeRecordByUserIdAndRaceTime(@Param("userId")long userId,@Param("raceTime")float raceTime);
    
    public LeaderboardChangeRecord getLeaderboardChangeRecordByUserIdAndAverageSpeed(@Param("userId")long userId,@Param("averageSpeed")float averageSpeed);

}