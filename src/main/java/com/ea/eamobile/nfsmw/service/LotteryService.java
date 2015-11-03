package com.ea.eamobile.nfsmw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.RewardConst;
import com.ea.eamobile.nfsmw.model.Car;
import com.ea.eamobile.nfsmw.model.GotchaCar;
import com.ea.eamobile.nfsmw.model.UserCar;
import com.ea.eamobile.nfsmw.model.bean.LotteryBean;
import com.ea.eamobile.nfsmw.model.bean.RewardBean;
import com.ea.eamobile.nfsmw.service.gotcha.GotchaCarService;
import com.ea.eamobile.nfsmw.service.redis.LotteryRedisService;
import com.ea.eamobile.nfsmw.utils.XmlUtil;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 19 18:23:55 CST 2013
 * @since 1.0
 */
@Service
public class LotteryService {

	public static final int TPYE_RANDOM_ONETIME = 0;
	public static final int TYPE_RANDOM_TENTIME = 1;
	public static final int RANDOM_COUNT_ONETIME = 1;
	public static final int RANDOM_COUNT_TENTIME = 10;
	public static final int RANDOM_SENDCAR_PERCENT_ONETIME = 1;
	public static final int RANDOM_SENDCAR_PERCENT_TENTIME = 20;
	
	@Autowired
    private UserCarService userCarService;
	@Autowired
    private CarService carService;
	@Autowired
    private GotchaCarService gotchaCarService;
    @Autowired
    private LotteryRedisService lotteryRedis;

    public LotteryBean getLottery(int id, int type) {
    	LotteryBean lottery = lotteryRedis.getLottery(id, type);
        if (lottery == null) {
        	parseAndSaveLotteryList(type);
            lottery = lotteryRedis.getLottery(id, type);
        }
        return lottery;
    }
    
    public List<LotteryBean> getLotteryList(int type) {
    	List<LotteryBean> lotteryList = lotteryRedis.getLotteryList(type);
        if (lotteryList == null || lotteryList.size() == 0) {
            lotteryList = parseAndSaveLotteryList(type);
        }
        return lotteryList;
    }
    
    public LotteryBean randomLottery(int type, long userId) {
    	List<LotteryBean> lotteryList = getLotteryList(type);
    	int totalWeight = 0;
    	for (LotteryBean lottery : lotteryList) {
    		totalWeight += lottery.getWeight();
    	}
    	Random rand = new Random();
    	int randNum = rand.nextInt(lotteryList.size());
		LotteryBean lottery = lotteryList.get(randNum);
		int circleCount = 0;
		while (circleCount < 20 && (rand.nextInt(totalWeight) > lottery.getWeight() || !isUsefulLottery(lottery, userId))) {
			randNum = rand.nextInt(lotteryList.size());
			lottery = lotteryList.get(randNum);
			circleCount++;
		}
		
		return lottery;
    }
    
    public List<LotteryBean> randomLotteryList(int type, long userId) {
    	List<LotteryBean> lotteryList = getLotteryList(TPYE_RANDOM_ONETIME);
    	int totalWeight = 0;
    	for (LotteryBean lottery : lotteryList) {
    		totalWeight += lottery.getWeight();
    	}
    	Random rand = new Random();
    	List<LotteryBean> randomLotteryList = new ArrayList<LotteryBean>();
    	int randomCount = RANDOM_COUNT_ONETIME;
    	if (type == TYPE_RANDOM_TENTIME) {
    		randomCount = RANDOM_COUNT_TENTIME;
    	}
    	while (randomLotteryList.size() < randomCount) {
    		int randNum = rand.nextInt(lotteryList.size());
    		LotteryBean lottery = lotteryList.get(randNum);
    		if (rand.nextInt(totalWeight) <= lottery.getWeight() && isUsefulLottery(lottery, userId))
    			randomLotteryList.add(lottery);
    	}
    	
    	int sendCarPercent = RANDOM_SENDCAR_PERCENT_ONETIME;
    	if (type == TYPE_RANDOM_TENTIME) {
    		sendCarPercent = RANDOM_SENDCAR_PERCENT_TENTIME;
    	}
    	
    	if (rand.nextInt(100) + 1 < sendCarPercent) {
    		LotteryBean specialLottery = randomLottery(TYPE_RANDOM_TENTIME, userId);
    		if (!isSameCarAndFragment(randomLotteryList, specialLottery))
    			randomLotteryList.add(specialLottery);
    	}
    	
    	return randomLotteryList;
    }
    
    private List<LotteryBean> parseAndSaveLotteryList(int type) {
    	List<LotteryBean> lotteryList = XmlUtil.getLotteryList(type);
        lotteryRedis.setLotteryList(lotteryList, type);
        
        return lotteryList;
    }
    
    private boolean isUsefulLottery(LotteryBean lottery, long userId) {
    	List<RewardBean> rewardList = lottery.getLotteryRewardList();
    	for (RewardBean reward : rewardList) {
    		int rewardId = reward.getRewardId();
    		if (rewardId < RewardConst.TYPE_REWARD_CAR)
    			continue;
    		
    		if (rewardId < RewardConst.TYPE_REWARD_FRAGMENT) {
    			Car car = carService.getCar(rewardId - RewardConst.TYPE_REWARD_CAR);
    			if (car != null) {
    				UserCar userCar = userCarService.getUserCarByUserIdAndCarId(userId, car.getId());
    				if (userCar != null)
    					return false;
    			}
    			
    			return true;
    		}
    		
    		GotchaCar gotchaCar = gotchaCarService.getGotchaCarById(rewardId - RewardConst.TYPE_REWARD_FRAGMENT);
    		if (gotchaCar != null) {
    			UserCar userCar = userCarService.getUserCarByUserIdAndCarId(userId, gotchaCar.getCarId());
    			if (userCar != null)
    				return false;
    			
    			return true;
    		}
    	}
    	
    	return true;
    }
    
    private boolean isSameCarAndFragment(List<LotteryBean> lotteryList, LotteryBean specialLottery) {
    	String carId = "";
    	for (RewardBean reward : specialLottery.getLotteryRewardList()) {
    		int id = reward.getRewardId() - RewardConst.TYPE_REWARD_CAR;
    		Car car = carService.getCar(id);
    		if (car == null)
    			return true;
    		
    		carId = car.getId();
    	}
    	
    	for (LotteryBean lottery : lotteryList) {
    		for (RewardBean reward : lottery.getLotteryRewardList()) {
    			if (reward.getRewardId() < RewardConst.TYPE_REWARD_FRAGMENT)
    				continue;
    			
    			GotchaCar gotchaCar = gotchaCarService.getGotchaCarById(reward.getRewardId() - RewardConst.TYPE_REWARD_FRAGMENT);
    			if (gotchaCar != null) {
    				if (gotchaCar.getCarId().equals(carId))
    					return true;
    			}
    		}
    	}
    	
    	return false;
    }
}