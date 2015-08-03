package com.ea.eamobile.nfsmw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.RaceModeUnlock;
import com.ea.eamobile.nfsmw.model.mapper.RaceModeUnlockMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Sep 25 16:33:42 CST 2012
 * @since 1.0
 */
@Service
public class RaceModeUnlockService {

    @Autowired
    private RaceModeUnlockMapper raceModeUnlockMapper;

    public RaceModeUnlock getRaceModeUnlock(int id) {
        return raceModeUnlockMapper.getRaceModeUnlock(id);
    }

    public List<RaceModeUnlock> getRaceModeUnlockList() {
        @SuppressWarnings("unchecked")
        List<RaceModeUnlock> ret = (List<RaceModeUnlock>) InProcessCache.getInstance().get("getRaceModeUnlockList");
        if (ret != null) {
            return ret;
        }
        ret = raceModeUnlockMapper.getRaceModeUnlockList();
        InProcessCache.getInstance().set("getRaceModeUnlockList", ret);
        return ret;
    }

    /**
     * 根据赛道取可解锁的mode
     * 
     * @param trackId
     * @return
     */
    public List<RaceModeUnlock> getRaceModeUnlockListByTrackId(String trackId) {
        List<RaceModeUnlock> list = getRaceModeUnlockList();
        List<RaceModeUnlock> result = new ArrayList<RaceModeUnlock>();
        for (RaceModeUnlock unlock : list) {
            if (unlock.getTrackId().equals(trackId)) {
                result.add(unlock);
            }
        }
        return result;
    }

    public int insert(RaceModeUnlock raceModeUnlock) {
        return raceModeUnlockMapper.insert(raceModeUnlock);
    }

    public void update(RaceModeUnlock raceModeUnlock) {
        raceModeUnlockMapper.update(raceModeUnlock);
    }

    public void deleteById(int id) {
        raceModeUnlockMapper.deleteById(id);
    }

}