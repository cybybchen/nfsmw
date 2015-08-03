package com.ea.eamobile.nfsmw.model.mapper;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.TournamentUser;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Sep 24 13:49:57 CST 2012
 * @since 1.0
 */
public interface TournamentUserMapper {

    public int insert(TournamentUser tournamentUser);

    public void update(TournamentUser tournamentUser);

    public void updateLeftTimes(@Param("userId") long userId, @Param("tournamentOnlineId") int tournamentOnlineId);

    public void deleteById(int id);

    public Integer getGroupSumByGroupIdAndTournamentOnlineId(@Param("groupId") int groupId,
            @Param("tournamentOnlineId") int tournamentOnlineId);

    public void deleteByUserId(long id);

    public TournamentUser getTournamentUserByUserIdAndTournamentOnlineId(@Param("userId") long userId,
            @Param("tournamentOnlineId") int tournamentOnlineId);

}