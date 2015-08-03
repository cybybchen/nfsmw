package com.ea.eamobile.nfsmw.model.mapper;

import java.util.LinkedList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.TournamentLeaderboard;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Nov 06 15:20:44 CST 2012
 * @since 1.0
 */
public interface TournamentLeaderboardMapper {

    public LinkedList<TournamentLeaderboard> getLeaderboard(@Param("tournamentOnlineId") int tournamentOnlineId,
            @Param("classId") int classId);

    public int insert(TournamentLeaderboard tournamentLeaderboard);

    public void update(TournamentLeaderboard tournamentLeaderboard);

    public TournamentLeaderboard getLeaderboardByUserId(@Param("tournamentOnlineId") int tournamentOnlineId,
            @Param("classId") int classId, @Param("userId") long userId);

    public void deleteByUserId(long id);

    public List<TournamentLeaderboard> getSpeedRankNumberOneList(int tournamentOnlineId);

    public List<TournamentLeaderboard> getRankNumberOneList(int tournamentOnlineId);

}