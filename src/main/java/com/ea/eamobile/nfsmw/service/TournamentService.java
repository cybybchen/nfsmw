package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.Tournament;
import com.ea.eamobile.nfsmw.model.mapper.TournamentMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Sep 24 13:49:57 CST 2012
 * @since 1.0
 */
@Service
public class TournamentService {

    @Autowired
    private TournamentMapper tournamentMapper;

    public Tournament getTournament(int id) {
        Tournament ret = (Tournament) InProcessCache.getInstance().get("getTournament." + id);
        if (ret != null) {
            return ret;
        }
        ret = tournamentMapper.getTournament(id);
        InProcessCache.getInstance().set("getTournament." + id, ret);
        return ret;
    }

    public List<Tournament> getTournamentList() {
        return tournamentMapper.getTournamentList();
    }

    public int insert(Tournament tournament) {
        return tournamentMapper.insert(tournament);
    }

    public void update(Tournament tournament) {
        tournamentMapper.update(tournament);
    }

    public void deleteById(int id) {
        tournamentMapper.deleteById(id);
    }

}