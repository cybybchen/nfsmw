package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.model.TournamentGhost;
import com.ea.eamobile.nfsmw.model.mapper.TournamentGhostMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Sep 26 15:43:32 CST 2012
 * @since 1.0
 */
@Service
public class TournamentGhostService {

    @Autowired
    private TournamentGhostMapper tournamentGhostMapper;
    @Autowired
    private MemcachedClient cache;

    public TournamentGhost getTournamentGhost(int tournamentOnlineId, long userId) {
        return tournamentGhostMapper.getTournamentGhostByTournamentOnlineIdAndUserId(tournamentOnlineId, userId);
    }

    /**
     * 为房间匹配用 不需要受update影响
     * 
     * @param onlineId
     * @param userId
     * @return
     */
    public TournamentGhost getGhost(int onlineId, long userId) {
        String key = buildKey(onlineId, userId);
        TournamentGhost ghost = (TournamentGhost) cache.get(key);
        if (ghost == null) {
            ghost = getTournamentGhost(onlineId, userId);
            cache.set(key, ghost, MemcachedClient.HOUR);
        }
        return ghost;
    }

    private String buildKey(int onlineId, long userId) {
        return CacheKey.TOUR_GHOST + onlineId + "_" + userId;
    }

    /**
     * 从db取10条数据 首次房间匹配不到时使用
     * 
     * @param onlineId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<TournamentGhost> getTournamentGhostList(int onlineId) {
        List<TournamentGhost> list = (List<TournamentGhost>) cache.get(CacheKey.TOUR_GHOST_LIST + onlineId);
        if (list == null || list.size() < 5) {
            list = tournamentGhostMapper.getTournamentGhostList(onlineId);
            cache.set(CacheKey.TOUR_GHOST_LIST + onlineId, list, MemcachedClient.DAY);
        }
        return list;
    }

    public int insert(TournamentGhost tournamentGhost) {
        return tournamentGhostMapper.insert(tournamentGhost);
    }

    public void update(TournamentGhost tournamentGhost) {
        tournamentGhostMapper.update(tournamentGhost);
    }

    public void deleteById(int id) {
        tournamentGhostMapper.deleteById(id);
    }

}