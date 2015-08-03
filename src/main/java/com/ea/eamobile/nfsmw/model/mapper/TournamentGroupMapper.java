package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.TournamentGroup;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Sep 24 13:49:57 CST 2012
 * @since 1.0
 */
public interface TournamentGroupMapper {

    public TournamentGroup getTournamentGroup(int id);

    public TournamentGroup getTournamentGroupByModeId(int modeId);

    public List<TournamentGroup> getTournamentGroupListByTid(int tournamentId);

    public Integer getTournamentGroupByUser(@Param("tournamentId") int tournamentId, @Param("userLevel") int userLevel);

}