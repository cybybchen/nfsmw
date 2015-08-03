package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.TournamentGhostMod;
import com.ea.eamobile.nfsmw.model.mapper.TournamentGhostModMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Sep 26 15:43:32 CST 2012
 * @since 1.0
 */
 @Service
public class TournamentGhostModService {

    @Autowired
    private TournamentGhostModMapper tournamentGhostModMapper;
    
    public List<TournamentGhostMod> getTournamentGhostMod(long tournamentGhostId){
        return tournamentGhostModMapper.getTournamentGhostModByTournamentGhostid(tournamentGhostId);
    }

    public List<TournamentGhostMod> getTournamentGhostModList(){
        return tournamentGhostModMapper.getTournamentGhostModList();
    }

    public int insert(TournamentGhostMod tournamentGhostMod){
        return tournamentGhostModMapper.insert(tournamentGhostMod);
    }

    public void update(TournamentGhostMod tournamentGhostMod){
		    tournamentGhostModMapper.update(tournamentGhostMod);    
    }

    public void deleteById(long tournamentGhostId){
        tournamentGhostModMapper.deleteById(tournamentGhostId);
    }

    public void insertBatch(List<TournamentGhostMod> modList) {
        tournamentGhostModMapper.insertBatch(modList);
    }

}