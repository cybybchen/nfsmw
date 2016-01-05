package com.ea.eamobile.nfsmw.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.IapConst;
import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.constants.ProfileComparisonType;
import com.ea.eamobile.nfsmw.constants.RewardConst;
import com.ea.eamobile.nfsmw.model.GotchaFragment;
import com.ea.eamobile.nfsmw.model.RaceMode;
import com.ea.eamobile.nfsmw.model.Reward;
import com.ea.eamobile.nfsmw.model.RpLevel;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserCarFragment;
import com.ea.eamobile.nfsmw.model.bean.RewardBean;
import com.ea.eamobile.nfsmw.model.mapper.RewardMapper;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseCommand.Builder;
import com.ea.eamobile.nfsmw.service.command.PushCommandService;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;
import com.ea.eamobile.nfsmw.service.gotcha.GotchaCarService;
import com.ea.eamobile.nfsmw.service.gotcha.GotchaFragmentService;
import com.ea.eamobile.nfsmw.view.ResultInfo;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Sep 24 18:44:41 CST 2012
 * @since 1.0
 */
@Service
public class RewardService {
	private static final Logger log = LoggerFactory.getLogger(RewardService.class);
	
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
    @Autowired
    private UserPropService userPropService;
    @Autowired
    private UserCarService userCarService;
    @Autowired
    private PushCommandService pushService;
    @Autowired
    private GotchaCarService gotchaCarService;
    @Autowired
    private UserCarFragmentService userCarFragmentService;
    @Autowired
    private CarService carService;

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
    
    public int getGoldByRaceMode(RaceMode mode) {
        Reward reward = getReward(mode.getRewardId());
        return reward != null ? reward.getGold() : 0;
    }
    
    public void doRewardList(User user, List<RewardBean> rewardList) {
    	doRewards(user, rewardList, null);
    }
    
    public void doRewards(User user, List<RewardBean> rewardList, Builder responseBuilder) {
    	int money = user.getMoney();
    	int gold = user.getGold();
    	long userId = user.getId();
    	int energy = user.getEnergy();
    	int rpnum = 0;
    	int rpexpweek = user.getRpExpWeek();
    	
    	for (RewardBean reward : rewardList) {
    		int rewardId = reward.getRewardId();
    		int rewardCount = reward.getRewardCount();
    		switch (rewardId) {
    			case RewardConst.TYPE_REWARD_ERENGY:
    				energy += rewardCount;
    				break;
    			case RewardConst.TYPE_REWARD_GOLD:
    				gold += rewardCount;
    				break;
    			case RewardConst.TYPE_REWARD_MONEY:
    				money += rewardCount;
    				break;
    			case RewardConst.TYPE_REWARD_RPEXPWEEK:
    				rpexpweek+=rewardCount;
    				rpnum += rewardCount;
    				break;
    			case RewardConst.TYPE_REWARD_RPNUM:
    				rpnum += rewardCount;
    				break;
    			default :
    				int fragmentId = rewardId - RewardConst.TYPE_REWARD_FRAGMENT;
    				int propId = rewardId - RewardConst.TYPE_REWARD_PROP;
    				int carId = rewardId - RewardConst.TYPE_REWARD_CAR;
    				if (fragmentId > 0) {
    		            if ( gotchaCarService.getGotchaCarById(fragmentId) == null) {
    		                break;
    		            }
    		            UserCarFragment userCarFragment = userCarFragmentService.getUserCarFragment(user.getId(), fragmentId);
    		            if (userCarFragment == null) {
    		                userCarFragment = new UserCarFragment();
    		                userCarFragment.setCount(0);
    		                userCarFragment.setFragmentId(fragmentId);
    		                userCarFragment.setUserId(user.getId());
    		                userCarFragmentService.insert(userCarFragment);
    		            }
    		            log.debug("addFragment " + fragmentId +" : "+rewardCount);
    		            userCarFragment.setCount(userCarFragment.getCount() + rewardCount);
    		            userCarFragmentService.update(userCarFragment);
    				} else if (carId > 0) {
//    					String carIdStr = RewardConst.REWARD_CAR_MAP.get(carId);
    					String carIdStr = carService.getCar(carId).getId();
    					log.debug("carId is:" + carIdStr);
    					if (carIdStr != null) {
    			    		ResultInfo result = userCarService.sendCar(user.getId(), carIdStr);
    			    		if (result.isSuccess() && responseBuilder != null) {
    			    			log.debug("send car success,carId is {}", carId);
    			    			try {
									pushService.pushUserCarInfoCommand(responseBuilder, userCarService.getGarageCarList(userId),
									        userId);
								} catch (SQLException e) {
									
								}
    			    			pushService.pushPopupListCommand(responseBuilder, null, Match.SEND_CAR_POPUP, IapConst.SENDCAR_CNNAME_MAP.get(carIdStr), 0,
    			                        0);
    			    		}
    			    	}
    				} else if (propId > 0) {
    					userPropService.addUserProp(user.getId(), propId, rewardCount);
    				}
    				break;
    		}
    	}
    	
    	if (rpnum > 0) {
            int level = rpLevelService.getLevelByRpNum(user.getRpNum() + rpnum);
            if (level > user.getLevel()) {
                for (int i = user.getLevel() + 1; i <= level; i++) {
                    RpLevel rpLevel = rpLevelService.getLevel(i);
                    Reward reward = getReward(rpLevel.getRewardId());
                    rpnum += reward.getRpNum();
                }
            }
        	user.setRpNum(user.getRpNum() + rpnum);
            user.setLevel(level);
    	}
    	
    	user.setEnergy(Math.min(energy, Match.ENERGY_BUY_MAX));
    	user.setGold(gold);
    	user.setMoney(money);
    	user.setRpExpWeek(rpexpweek);
    	userService.updateUser(user);
    }

}
