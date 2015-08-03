package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.TournamentOnline;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Sep 24 13:49:57 CST 2012
 * @since 1.0
 */
public interface TournamentOnlineMapper {

    public TournamentOnline getTournamentOnline(int id);

    public List<TournamentOnline> getTournamentOnlineList();

    public int insert(TournamentOnline tournamentOnline);

    public void update(TournamentOnline tournamentOnline);

    public void deleteById(int id);
    
    public List<TournamentOnline> getTournamentOnlineListByEndTime(@Param("firstTime")int firstTime,@Param("secondTime")int secondTime);

}