package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.TournamentReward;
import com.ea.eamobile.nfsmw.model.mapper.TournamentRewardMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Sep 24 13:49:57 CST 2012
 * @since 1.0
 */
@Service
public class TournamentRewardService {

    @Autowired
    private TournamentRewardMapper tournamentRewardMapper;

   
    public TournamentReward getTournamentRewardByRank(int rank, int groupId) {
        TournamentReward ret = (TournamentReward) InProcessCache.getInstance().get("getTournamentRewardByRank." + rank+"."+groupId);
        if (ret != null) {
            return ret;
        }
        ret = tournamentRewardMapper.getTournamentRewardByRank(rank, groupId);
        InProcessCache.getInstance().set("getTournamentRewardByRank." + rank+"."+groupId, ret);
        return ret;
    }

    public List<TournamentReward> getTournamentRewardList() {
        return tournamentRewardMapper.getTournamentRewardList();
    }

    
    public List<TournamentReward> getTournamentRewardListByGroupId(int groupId) {
        @SuppressWarnings("unchecked")
        List<TournamentReward> ret = ( List<TournamentReward>) InProcessCache.getInstance().get("getTournamentRewardListByGroupId." +groupId);
        if (ret != null) {
            return ret;
        }
        ret = tournamentRewardMapper.getTournamentRewardListByGroupId(groupId);
        InProcessCache.getInstance().set("getTournamentRewardListByGroupId." +groupId, ret);
        return ret;
    }

    public int insert(TournamentReward tournamentReward) {
        return tournamentRewardMapper.insert(tournamentReward);
    }

    public void update(TournamentReward tournamentReward) {
        tournamentRewardMapper.update(tournamentReward);
    }

    public void deleteById(int id) {
        tournamentRewardMapper.deleteById(id);
    }

}