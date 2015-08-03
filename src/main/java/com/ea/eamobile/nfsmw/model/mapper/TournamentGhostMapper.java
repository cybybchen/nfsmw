package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.TournamentGhost;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Sep 26 15:43:32 CST 2012
 * @since 1.0
 */
public interface TournamentGhostMapper {

    public TournamentGhost getTournamentGhostByTournamentOnlineIdAndUserId(int tournamentOnlineId,long userId);

    public List<TournamentGhost> getTournamentGhostList(int onlineId);

    public int insert(TournamentGhost tournamentGhost);

    public void update(TournamentGhost tournamentGhost);

    public void deleteById(int id);
    
}