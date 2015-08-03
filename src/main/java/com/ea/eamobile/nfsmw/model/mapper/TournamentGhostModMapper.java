package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.TournamentGhostMod;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Sep 26 15:43:32 CST 2012
 * @since 1.0
 */
public interface TournamentGhostModMapper {

    public List<TournamentGhostMod> getTournamentGhostModByTournamentGhostid(long tournamentGhostId);

    public List<TournamentGhostMod> getTournamentGhostModList();

    public int insert(TournamentGhostMod tournamentGhostMod);

    public void update(TournamentGhostMod tournamentGhostMod);

    public void deleteById(long tournamentGhostId);

    public void insertBatch(List<TournamentGhostMod> modList);

}