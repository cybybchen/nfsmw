package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.ProfileComparisonType;
import com.ea.eamobile.nfsmw.model.RaceMode;
import com.ea.eamobile.nfsmw.model.Reward;
import com.ea.eamobile.nfsmw.model.RpLevel;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.mapper.RewardMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Sep 24 18:44:41 CST 2012
 * @since 1.0
 */
@Service
public class RewardService {

    @Autowired
    private RewardMapper rewardMapper;
    @Autowired
    private UserService userService;

    @Autowired
    private RpLevelService rpLevelService;

	@Autowired
	private UserRaceActionService userRaceActionService;
    @Autowired
    private MemcachedClient cache;

    public Reward getReward(int id) {
        Reward ret = (Reward) InProcessCache.getInstance().get("getReward." + id);
        if (ret != null) {
            return ret;
        }
        ret = rewardMapper.getReward(id);
        InProcessCache.getInstance().set("getReward." + id, ret);
        return ret;
    }

    public List<Reward> getRewardList() {
        return rewardMapper.getRewardList();
    }

    public int insert(Reward reward) {
        return rewardMapper.insert(reward);
    }

    public void update(Reward reward) {
        rewardMapper.update(reward);
    }

    public void deleteById(int id) {
        rewardMapper.deleteById(id);
    }

    public void updateUserReward(long userId, int rewardId) {
        User user = userService.getUser(userId);
        updateUserReward(user, rewardId);
    }

    public User updateUserReward(User user, int rewardId) {
        return doRewards(user, rewardId);
    }

    /**
     * 进行奖励 可批量
     * 
     * @param user
     * @param rewardIds
     * @return
     */
    public User doRewards(User user, int... rewardIds) {
        if (user == null || rewardIds == null || rewardIds.length == 0) {
            return user;
        }
        int rpNum = 0; // 注意此处不能+ user.getRpNum()，后面需要判断
        int mwNum = user.getStarNum();
        int money = user.getMoney();
        int gold = user.getGold();
        int energy = user.getEnergy();
        for (int i = 0; i < rewardIds.length; i++) {
            Reward reward = getReward(rewardIds[i]);
            if (reward == null)
                continue;
            rpNum += reward.getRpNum();
            mwNum += reward.getMostwantedNum();
            money += reward.getMoney();
            gold += reward.getGold();
            energy += reward.getEnergy();
        }
        // 有经验值时才进行升级奖励判断
        if (rpNum > 0) {
            int level = rpLevelService.getLevelByRpNum(user.getRpNum() + rpNum);
            if (level > user.getLevel()) {
                for (int i = user.getLevel() + 1; i <= level; i++) {
                    RpLevel rpLevel = rpLevelService.getLevel(i);
                    Reward reward = getReward(rpLevel.getRewardId());
                    rpNum += reward.getRpNum();
                    mwNum += reward.getMostwantedNum();
                    money += reward.getMoney();
                    gold += reward.getGold();
                    energy += reward.getEnergy();
                }

            }
            user.setLevel(level);
        }

        user.setRpNum(user.getRpNum() + rpNum);
        user.setStarNum(mwNum);
        user.setMoney(money);
        user.setGold(gold);
        user.setEnergy(energy);
        userService.updateUser(user);
        
        // update rpNum in user_race_action
        int valueId = ProfileComparisonType.RP_NUM.getIndex();
        long userId = user.getId();
        cache.delete(CacheKey.USER_RACE_ACTION + userId + "_" + valueId);
        rpNum = user.getRpNum();
        userRaceActionService.refreshDataAndCache(userId, valueId, rpNum);
        
        return user;
    }

    public User doRewards(User user, List<Integer> rewardIds) {
        if (user == null || rewardIds == null || rewardIds.size() == 0) {
            return user;
        }
        int rpNum = 0; // 注意此处不能+ user.getRpNum()，后面需要判断
        int mwNum = user.getStarNum();
        int money = user.getMoney();
        int gold = user.getGold();
        int energy = user.getEnergy();
        for (int i = 0; i < rewardIds.size(); i++) {
            Reward reward = getReward(rewardIds.get(i));
            if (reward == null)
                continue;
            rpNum += reward.getRpNum();
            mwNum += reward.getMostwantedNum();
            money += reward.getMoney();
            gold += reward.getGold();
            energy += reward.getEnergy();
        }
        // 有经验值时才进行升级奖励判断
        if (rpNum > 0) {
            int level = rpLevelService.getLevelByRpNum(user.getRpNum() + rpNum);
            if (level > user.getLevel()) {
                for (int i = user.getLevel() + 1; i <= level; i++) {
                    RpLevel rpLevel = rpLevelService.getLevel(i);
                    Reward reward = getReward(rpLevel.getRewardId());
                    rpNum += reward.getRpNum();
                    mwNum += reward.getMostwantedNum();
                    money += reward.getMoney();
                    gold += reward.getGold();
                    energy += reward.getEnergy();
                }

            }
            user.setLevel(level);
        }

        user.setRpNum(user.getRpNum() + rpNum);
        user.setStarNum(mwNum);
        user.setMoney(money);
        user.setGold(gold);
        user.setEnergy(energy);
        userService.updateUser(user);

        // update rpNum in user_race_action
        int valueId = ProfileComparisonType.RP_NUM.getIndex();
        long userId = user.getId();
        cache.delete(CacheKey.USER_RACE_ACTION + userId + "_" + valueId);
        rpNum = user.getRpNum();
        userRaceActionService.refreshDataAndCache(userId, valueId, rpNum);
        
        return user;
    }

    public int getMwNumByRaceMode(RaceMode mode) {
        Reward reward = getReward(mode.getRewardId());
        return reward != null ? reward.getMostwantedNum() : 0;
    }

}