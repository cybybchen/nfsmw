package com.ea.eamobile.nfsmw.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.model.TournamentOnline;
import com.ea.eamobile.nfsmw.model.TournamentUser;
import com.ea.eamobile.nfsmw.model.mapper.TournamentOnlineMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Sep 24 13:49:57 CST 2012
 * @since 1.0
 */
@Service
public class TournamentOnlineService {

    @Autowired
    private TournamentOnlineMapper tournamentOnlineMapper;
    @Autowired
    private MemcachedClient cache;

    private void clear(int id) {
        cache.delete(CacheKey.TOURNAMENT_BYID + id);
        cache.delete(CacheKey.TOURNAMENT_LIST);
    }

    /**
     * @param id
     * @return
     */
    public TournamentOnline getTournamentOnline(int id) {
        TournamentOnline online = (TournamentOnline) cache.get(CacheKey.TOURNAMENT_BYID + id);
        if (online == null) {
            online = tournamentOnlineMapper.getTournamentOnline(id);
            cache.set(CacheKey.TOURNAMENT_BYID + id, online, MemcachedClient.HOUR);
        }
        return online;
    }

    /**
     * 已经排序好的
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<TournamentOnline> getTournamentOnlineList() {
        List<TournamentOnline> list = (List<TournamentOnline>) cache.get(CacheKey.TOURNAMENT_LIST);
        if (list == null) {
            list = tournamentOnlineMapper.getTournamentOnlineList();
            cache.set(CacheKey.TOURNAMENT_LIST, list, MemcachedClient.HOUR);
        }
        return list;
    }

    public int insert(TournamentOnline tournamentOnline) {
        clear(tournamentOnline.getId());
        return tournamentOnlineMapper.insert(tournamentOnline);
    }

    public void update(TournamentOnline tournamentOnline) {
        clear(tournamentOnline.getId());
        tournamentOnlineMapper.update(tournamentOnline);
    }

    public void deleteById(int id) {
        clear(id);
        tournamentOnlineMapper.deleteById(id);
    }

    /**
     * 判断用户是否完成了一个结束的联赛
     * 
     * @param online
     * @param tourUser
     * @return
     */
    public boolean isUserFinishedOnline(TournamentOnline online, TournamentUser tourUser) {
        if (online.getEndTime() < System.currentTimeMillis() / 1000 && tourUser != null && tourUser.getResult() > 0) {
            return true;
        }
        return false;
    }

    public Map<Integer, TournamentOnline> getTournamentOnlineMap() {
        Map<Integer, TournamentOnline> map = Collections.emptyMap();
        List<TournamentOnline> list = getTournamentOnlineList();
        if (list != null) {
            map = new HashMap<Integer, TournamentOnline>();
            for (TournamentOnline online : list) {
                map.put(online.getId(), online);
            }
        }
        return map;
    }

    /**
     * 取进行中的列表
     * 
     * @return
     */
    public List<TournamentOnline> getInProgressOnlineList() {
        List<TournamentOnline> result = Collections.emptyList();
        List<TournamentOnline> list = getTournamentOnlineList();
        if (list != null && list.size() > 0) {
            long now = System.currentTimeMillis();
            for (TournamentOnline online : list) {
                if (online.getStartTime() < now / 1000 && online.getEndTime() > now / 1000) {
                    if (result.size() == 0) {
                        result = new ArrayList<TournamentOnline>();
                    }
                    result.add(online);
                }
            }
        }
        return result;
    }

    /**
     * 取已完成的online列表
     * 
     * @param idList
     * @return
     */
    public List<TournamentOnline> getFinishedOnlineList(List<Integer> idList) {
        List<TournamentOnline> result = Collections.emptyList();
        if (idList != null && idList.size() > 0) {
            Map<Integer, TournamentOnline> map = getTournamentOnlineMap();
            long now = System.currentTimeMillis();
            for (Integer id : idList) {
                TournamentOnline online = map.get(id);
                if (online != null && online.getEndTime() <= now / 1000) { // only save second
                    if (result.size() == 0) {
                        result = new ArrayList<TournamentOnline>();
                    }
                    result.add(online);
                }
            }
        }
        return result;
    }

    public boolean isFinishOnline(int onlineId){
        List<TournamentOnline> list = getInProgressOnlineList();
        if(list==null || list.size()==0){
            return true;
        }
        for(TournamentOnline online : list){
            if(online.getId()==onlineId){
                return false;
            }
        }
        return true;
    }
    
    public List<TournamentOnline> getTournamentOnlineListByEndTime(int firstTime, int secondTime) {

        return tournamentOnlineMapper.getTournamentOnlineListByEndTime(firstTime, secondTime);

    }

}