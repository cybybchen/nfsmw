package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.bean.FansRewardBean;
import com.ea.eamobile.nfsmw.model.bean.RewardBean;
import com.ea.eamobile.nfsmw.service.redis.FansRewardRedisService;
import com.ea.eamobile.nfsmw.utils.DateUtil;
import com.ea.eamobile.nfsmw.utils.XmlUtil;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 19 18:23:55 CST 2013
 * @since 1.0
 */
@Service
public class FansRewardService {
	
    @Autowired
    private FansRewardRedisService fansRewardRedis;
    @Autowired
    private RewardService rewardService;

    public FansRewardBean getFansReward(int id) {
    	FansRewardBean fansReward = fansRewardRedis.getFansReward(id);
        if (fansReward == null) {
        	parseAndSaveFansRewardList();
        	fansReward = fansRewardRedis.getFansReward(id);
        }
        return fansReward;
    }
    
    public List<FansRewardBean> getFansRewardList() {
    	List<FansRewardBean> fansRewardList = fansRewardRedis.getFansRewardList();
        if (fansRewardList == null) {
        	fansRewardList = parseAndSaveFansRewardList();
        }
        return fansRewardList;
    }
    
    public FansRewardBean addUserFansReward(User user, int fansRewardId) {
    	FansRewardBean fansReward = getFansReward(fansRewardId);
    	if (fansReward == null)
    		return null;
    	if (user.getFansRewardLastTime() == 0)
    		user.setFansRewardLastTime(user.getCreateTime());
    	int intervalSeconds = DateUtil.intervalSeconds(System.currentTimeMillis(), 
    			(long)(user.getFansRewardLastTime() * DateUtil.MILLIONSECONDS_PER_SECOND + fansReward.getCountdown() * DateUtil.MILLIONSECONDS_PER_HOUR));
    	if (intervalSeconds < 0 || (user.getFansRewardStatus() >> fansReward.getId() & 1) == 1)
    		return null;
    	
    	user.setFansRewardLastTime((int)(System.currentTimeMillis() / DateUtil.MILLIONSECONDS_PER_SECOND));
    	user.setFansRewardStatus(user.getFansRewardStatus() + (1 << fansReward.getId()));
    	
    	List<RewardBean> rewardList = fansReward.getRewardList();
    	rewardService.doRewards(user, rewardList);
    	
    	return fansReward;
    }
    
    private List<FansRewardBean> parseAndSaveFansRewardList() {
    	List<FansRewardBean> fansRewardList = XmlUtil.getFansRewardList();
        fansRewardRedis.setFansRewardList(fansRewardList);
        
        return fansRewardList;
    }
}