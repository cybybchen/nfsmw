package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.Leaderboard;

public interface LeaderboardMapper {

    public void insert(Leaderboard leaderboard);

    public void update(Leaderboard leaderboard);

    public List<Leaderboard> getLeaderboardByMode(int modeType);
    
    public Leaderboard getLeaderboardByModeIdAndUserId(@Param("modeType") int modeType,@Param("userId") long userId);
    
    public List<Leaderboard> getLeaderboardByUserId(long userId);
    
    public void deleteByUserId(long userId);
    
    public void deleteByModeType(int modeType);
    

}
