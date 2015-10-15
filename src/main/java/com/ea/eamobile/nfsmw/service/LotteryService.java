package com.ea.eamobile.nfsmw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.bean.LotteryBean;
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
    
    public LotteryBean randomLottery(int type) {
    	List<LotteryBean> lotteryList = getLotteryList(type);
    	int totalWeight = 0;
    	for (LotteryBean lottery : lotteryList) {
    		totalWeight += lottery.getWeight();
    	}
    	Random rand = new Random();
    	int randNum = rand.nextInt(lotteryList.size());
		LotteryBean lottery = lotteryList.get(randNum);
		while (rand.nextInt(totalWeight) > lottery.getWeight()) {
			randNum = rand.nextInt(lotteryList.size());
			lottery = lotteryList.get(randNum);
		}
		
		return lottery;
    }
    
    public List<LotteryBean> randomLotteryList(int type) {
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
    		if (rand.nextInt(totalWeight) <= lottery.getWeight())
    			randomLotteryList.add(lottery);
    	}
    	
    	if (type == TYPE_RANDOM_TENTIME) {
	    	LotteryBean specialLottery = randomLottery(TYPE_RANDOM_TENTIME);
	    	randomLotteryList.add(specialLottery);
    	}
    	
    	return randomLotteryList;
    }
    
    private List<LotteryBean> parseAndSaveLotteryList(int type) {
    	List<LotteryBean> lotteryList = XmlUtil.getLotteryList(type);
        lotteryRedis.setLotteryList(lotteryList, type);
        
        return lotteryList;
    }
}