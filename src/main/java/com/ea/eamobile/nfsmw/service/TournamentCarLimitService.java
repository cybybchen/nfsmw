package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.TournamentCarLimit;
import com.ea.eamobile.nfsmw.model.mapper.TournamentCarLimitMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Nov 20 11:13:33 CST 2012
 * @since 1.0
 */
@Service
public class TournamentCarLimitService {

    @Autowired
    private TournamentCarLimitMapper tournamentCarLimitMapper;

    public List<String> getTournamentCarLimit(int groupId) {
        @SuppressWarnings("unchecked")
        List<String> ret = (List<String>) InProcessCache.getInstance().get("getTournamentCarLimit" + groupId);
        if (ret != null) {
            return ret;
        }
        ret = tournamentCarLimitMapper.getTournamentCarLimitByGroupId(groupId);
        InProcessCache.getInstance().set("getTournamentCarLimit" + groupId, ret);
        return ret;
    }

    public List<TournamentCarLimit> getTournamentCarLimitList() {
        return tournamentCarLimitMapper.getTournamentCarLimitList();
    }

}