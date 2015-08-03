package com.ea.eamobile.nfsmw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.RaceReward;
import com.ea.eamobile.nfsmw.model.mapper.RaceRewardMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Oct 18 14:56:40 CST 2012
 * @since 1.0
 */
@Service
public class RaceRewardService {

    @Autowired
    private RaceRewardMapper raceRewardMapper;

    public int getRewardId(int rpLevel, int gameMode, int raceType, int position, boolean success) {
        int isFinish = success ? 1 : 0;
        List<RaceReward> rewards = getRaceRewards();
        for (RaceReward reward : rewards) {
            if (rpLevel == reward.getRpLevel() && gameMode == reward.getGameMode() && raceType == reward.getRaceType()
                    && position == reward.getPosition() && isFinish == reward.getIsFinish()) {
                return reward.getRewardId();
            }
        }
        return 0;
    }

    public List<RaceReward> getRaceRewards() {
        @SuppressWarnings("unchecked")
        List<RaceReward> ret = (List<RaceReward>) InProcessCache.getInstance().get("race_reward_reward_list");
        if (ret != null) {
            return ret;
        }
        ret = raceRewardMapper.getRaceRewards();
        InProcessCache.getInstance().set("race_reward_reward_list", ret);
        return ret;
    }

    /**
     * 根据排名取一个有序的列表
     * 
     * @param rpLevel
     * @param gameMode
     * @param raceType
     * @param position
     * @param isFinish
     * @return
     */
    public List<RaceReward> getRaceRewards(int rpLevel, int gameMode, int raceType, int position, int isFinish) {
        List<RaceReward> result = new ArrayList<RaceReward>();
        List<RaceReward> rewards = getRaceRewards();// 此处已经排序过了
        for (RaceReward reward : rewards) {
            if (rpLevel == reward.getRpLevel() && gameMode == reward.getGameMode() && raceType == reward.getRaceType()
                    && position >= reward.getPosition() && isFinish == reward.getIsFinish()) {
                result.add(reward);
            }
        }
        return result;
    }

}