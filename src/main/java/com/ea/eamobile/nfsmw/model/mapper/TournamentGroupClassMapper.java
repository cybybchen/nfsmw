package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.TournamentGroupClass;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Jan 24 17:21:02 CST 2013
 * @since 1.0
 */
public interface TournamentGroupClassMapper {

    public TournamentGroupClass getTournamentGroupClass(int id);

    public List<TournamentGroupClass> getTournamentGroupClassList();

    public int insert(TournamentGroupClass tournamentGroupClass);

    public void update(TournamentGroupClass tournamentGroupClass);

    public Integer getMaxTournamentGroupClass(@Param("tournamentOnlineId") int tournamentOnlineId,
            @Param("groupId") int groupId);

    public List<Integer> getTournamentGroupClassListByOnlineId(int tournamentOnlineId);

}