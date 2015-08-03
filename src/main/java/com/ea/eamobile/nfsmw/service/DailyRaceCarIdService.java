package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.DailyRaceCarId;
import com.ea.eamobile.nfsmw.model.mapper.DailyRaceCarIdMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Dec 10 14:34:32 CST 2012
 * @since 1.0
 */
 @Service
public class DailyRaceCarIdService {

    @Autowired
    private DailyRaceCarIdMapper dailyRaceCarIdMapper;
    
    public DailyRaceCarId getDailyRaceCarId(int id){
        DailyRaceCarId ret = (DailyRaceCarId) InProcessCache.getInstance().get("getDailyRaceCarId."+id);
        if (ret != null) {
            return ret;
        }
        ret = dailyRaceCarIdMapper.getDailyRaceCarId(id);
        InProcessCache.getInstance().set("getDailyRaceCarId."+id, ret);
        return ret;
    }

    public List<DailyRaceCarId> getDailyRaceCarIdList(){
        @SuppressWarnings("unchecked")
        List<DailyRaceCarId> ret = (List<DailyRaceCarId>) InProcessCache.getInstance().get("getDailyRaceCarIdList");
        if (ret != null) {
            return ret;
        }
        ret = dailyRaceCarIdMapper.getDailyRaceCarIdList();
        InProcessCache.getInstance().set("getDailyRaceCarIdList", ret);
        return ret;
    }

    public List<DailyRaceCarId> getDailyRaceCarIdListByTier(int tier){
        @SuppressWarnings("unchecked")
        List<DailyRaceCarId> ret = (List<DailyRaceCarId>) InProcessCache.getInstance().get("getDailyRaceCarIdListByTier."+tier);
        if (ret != null) {
            return ret;
        }
        ret = dailyRaceCarIdMapper.getDailyRaceCarIdListByTier(tier);
        InProcessCache.getInstance().set("getDailyRaceCarIdListByTier."+tier, ret);
        return ret;
    }

    public int insert(DailyRaceCarId dailyRaceCarId){
        return dailyRaceCarIdMapper.insert(dailyRaceCarId);
    }

    public void update(DailyRaceCarId dailyRaceCarId){
		    dailyRaceCarIdMapper.update(dailyRaceCarId);    
    }

    public void deleteById(int id){
        dailyRaceCarIdMapper.deleteById(id);
    }

}