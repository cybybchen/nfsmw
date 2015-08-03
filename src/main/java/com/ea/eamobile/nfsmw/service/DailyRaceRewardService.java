package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.DailyRaceReward;
import com.ea.eamobile.nfsmw.model.mapper.DailyRaceRewardMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Nov 20 11:13:33 CST 2012
 * @since 1.0
 */
@Service
public class DailyRaceRewardService {

    @Autowired
    private DailyRaceRewardMapper dailyRaceRewardMapper;

  
    public Integer getDailyRaceReward(int rpLevel, int duraNum, int isFinish) {
        Integer ret = (Integer) InProcessCache.getInstance().get("getDailyRaceReward."+rpLevel+"."+duraNum+"."+isFinish);
        if (ret != null) {
            return ret;
        }
        ret = dailyRaceRewardMapper.getRewardId(rpLevel, duraNum, isFinish);
        InProcessCache.getInstance().set("getDailyRaceReward."+rpLevel+"."+duraNum+"."+isFinish, ret);
        return ret;
    }

    public List<DailyRaceReward> getDailyRaceRewardList() {
        @SuppressWarnings("unchecked")
        List<DailyRaceReward> ret = (List<DailyRaceReward>) InProcessCache.getInstance().get("getDailyRaceRewardList");
        if (ret != null) {
            return ret;
        }
        ret = dailyRaceRewardMapper.getDailyRaceRewardList();
        InProcessCache.getInstance().set("getDailyRaceRewardList", ret);
        return ret;
    }

}