package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.DailyRaceModeId;
import com.ea.eamobile.nfsmw.model.mapper.DailyRaceModeIdMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Dec 10 14:34:32 CST 2012
 * @since 1.0
 */
@Service
public class DailyRaceModeIdService {

    @Autowired
    private DailyRaceModeIdMapper dailyRaceModeIdMapper;

    public DailyRaceModeId getDailyRaceModeId(int id) {
        DailyRaceModeId ret = (DailyRaceModeId) InProcessCache.getInstance().get("getDailyRaceModeId." + id);
        if (ret != null) {
            return ret;
        }
        ret = dailyRaceModeIdMapper.getDailyRaceModeId(id);
        InProcessCache.getInstance().set("getDailyRaceModeId." + id, ret);
        return ret;
    }

    public List<DailyRaceModeId> getDailyRaceModeIdList() {
        @SuppressWarnings("unchecked")
        List<DailyRaceModeId> ret = (List<DailyRaceModeId>) InProcessCache.getInstance().get("getDailyRaceModeIdList");
        if (ret != null) {
            return ret;
        }
        ret = dailyRaceModeIdMapper.getDailyRaceModeIdList();
        InProcessCache.getInstance().set("getDailyRaceModeIdList", ret);
        return ret;
    }

    public int insert(DailyRaceModeId dailyRaceModeId) {
        return dailyRaceModeIdMapper.insert(dailyRaceModeId);
    }

    public void update(DailyRaceModeId dailyRaceModeId) {
        dailyRaceModeIdMapper.update(dailyRaceModeId);
    }

    public void deleteById(int id) {
        dailyRaceModeIdMapper.deleteById(id);
    }

}