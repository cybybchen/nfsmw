package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.TournamentGroup;
import com.ea.eamobile.nfsmw.model.mapper.TournamentGroupMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Sep 24 13:49:57 CST 2012
 * @since 1.0
 */
@Service
public class TournamentGroupService {

    @Autowired
    private TournamentGroupMapper tournamentGroupMapper;

    public TournamentGroup getTournamentGroup(int id) {
        TournamentGroup ret = (TournamentGroup) InProcessCache.getInstance().get("getTournamentGroup" + id);
        if (ret != null) {
            return ret;
        }
        ret = tournamentGroupMapper.getTournamentGroup(id);
        InProcessCache.getInstance().set("getTournamentGroup" + id, ret);
        return ret;
    }

    public TournamentGroup getTournamentGroupByModeId(int modeId) {
        TournamentGroup ret = (TournamentGroup) InProcessCache.getInstance().get("getTournamentGroupByModeId" + modeId);
        if (ret != null) {
            return ret;
        }
        ret = tournamentGroupMapper.getTournamentGroupByModeId(modeId);
        InProcessCache.getInstance().set("getTournamentGroupByModeId" + modeId, ret);
        return ret;
    }

    public List<TournamentGroup> getTournamentGroupListByTid(int tid) {
        @SuppressWarnings("unchecked")
        List<TournamentGroup> ret = (List<TournamentGroup>) InProcessCache.getInstance().get("getTournamentGroupListByTid" + tid);
        if (ret != null) {
            return ret;
        }
        ret = tournamentGroupMapper.getTournamentGroupListByTid(tid);
        InProcessCache.getInstance().set("getTournamentGroupListByTid" + tid, ret);
        return ret;
    }

    /**
     * 为用户选组 字典数据无需变更
     * 
     * @param tournamentId
     * @param userLevel
     * @return
     */

    public int getGroupIdForUser(int tournamentId, int userLevel) {
        List<TournamentGroup> list = getTournamentGroupListByTid(tournamentId);
        if (list != null && list.size() > 0) {
            for (TournamentGroup group : list) {
                if (group.getRpDownLevel() <= userLevel && group.getRpUpLevel() >= userLevel) {
                    return group.getId();
                }
            }
        }
        return 0;
    }

}