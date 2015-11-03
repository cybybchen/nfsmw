package com.ea.eamobile.nfsmw.service.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.ErrorConst;
import com.ea.eamobile.nfsmw.constants.RewardConst;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.bean.LotteryBean;
import com.ea.eamobile.nfsmw.model.bean.RewardBean;
import com.ea.eamobile.nfsmw.model.bean.UserPropBean;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.ErrorCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestLotteryCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseLotteryCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RewardList;
import com.ea.eamobile.nfsmw.protoc.Commands.RewardN;
import com.ea.eamobile.nfsmw.service.LotteryService;
import com.ea.eamobile.nfsmw.service.PayService;
import com.ea.eamobile.nfsmw.service.RewardService;
import com.ea.eamobile.nfsmw.service.UserInfoMessageService;
import com.ea.eamobile.nfsmw.service.UserPropService;
import com.ea.eamobile.nfsmw.view.ResultInfo;

@Service
public class LotteryCommandService extends BaseCommandService {
	
	@Autowired
    private UserInfoMessageService userInfoMessageService;
	@Autowired
    private RewardService rewardService;
	@Autowired
    private LotteryService lotteryService;
	@Autowired
	private PayService payService;
	@Autowired
	private PushCommandService pushCommandService;
	@Autowired
	private UserPropService userPropService;
	
	private static final Logger log = LoggerFactory.getLogger(LotteryCommandService.class);
    
    /**
     * 抽奖
     * 
     * @param 
     * @return
     */
    public ResponseLotteryCommand getResponseLotteryCommand(RequestLotteryCommand reqcmd, User user, 
    		Commands.ResponseCommand.Builder responseBuilder) {
    	ResponseLotteryCommand.Builder builder = ResponseLotteryCommand.newBuilder();
    	List<RewardList> rewardBuilderList = new ArrayList<RewardList>();
    	int type = reqcmd.getLotteryType();
    	UserPropBean userProp = new UserPropBean();
    	 ResultInfo result = new ResultInfo(false, "", user);
    	if (type == LotteryService.TPYE_RANDOM_ONETIME) {
    		userProp = userPropService.getUserPropByPropId(user.getId(), 4);
    	}
    	if (userProp == null || userProp.getPropCount() < 1) {
	    	LotteryBean lotteryPay = new LotteryBean();
	    	lotteryPay.setPriceType(PayService.GOLD);
	    	if (user.getLotteryTimes() == 0)
	    		lotteryPay.setPrice(0);
	    	else if (type == LotteryService.TPYE_RANDOM_ONETIME)
	    		lotteryPay.setPrice(100);
	    	else if (type == LotteryService.TYPE_RANDOM_TENTIME)
	    		lotteryPay.setPrice(1000);
	    	
	    	result = payService.buy(lotteryPay, user);
    	}
    	boolean isSendCar = false;
    	if (result.isSuccess() || (userProp != null && userProp.getPropCount() > 0)) {
    		if (userProp != null && userProp.getPropCount() > 0) {
    			userProp.setPropCount(userProp.getPropCount() - 1);
    			userPropService.updateUserProp(userProp);
    		}
    		user.setLotteryTimes(user.getLotteryTimes() + 1);
	    	List<LotteryBean> lotteryList = lotteryService.randomLotteryList(type, user.getId());
	    	List<RewardBean> addRewardList = new ArrayList<RewardBean>();
	    	for (LotteryBean lottery : lotteryList) {
	    		RewardList.Builder rewardListBuilder = RewardList.newBuilder();
	    		List<RewardN> list = new ArrayList<RewardN>();
	    		List<RewardBean> rewardList = lottery.getLotteryRewardList();
	    		addRewardList.addAll(rewardList);
	    		for (RewardBean reward : rewardList) {
	    			if (reward.getRewardId() > RewardConst.TYPE_REWARD_CAR && reward.getRewardId() < RewardConst.TYPE_REWARD_FRAGMENT)
	    				isSendCar = true;
	    			if (reward.getRewardId() > RewardConst.TYPE_REWARD_FRAGMENT)
	    				isSendCar = true;
	    			log.debug("reward string is:" + reward.toJson());
	    			RewardN.Builder rewardBuilder = RewardN.newBuilder();
	    			rewardBuilder.setId(reward.getRewardId());
	    			rewardBuilder.setCount(reward.getRewardCount());
	    			list.add(rewardBuilder.build());	
	    		}
	    		rewardListBuilder.addAllRewards(list);
	    		rewardBuilderList.add(rewardListBuilder.build());
	    	}
	    	rewardService.doRewards(user, addRewardList);
    	} else {
    		ErrorCommand errorCommand = buildErrorCommand(ErrorConst.NOT_ENOUGH_GOLD);
            responseBuilder.setErrorCommand(errorCommand);
    	}
    	builder.addAllRewards(rewardBuilderList);
    	pushCommandService.pushUserInfoCommand(responseBuilder, user);
    	if (isSendCar) {
	    	try {
	    		pushCommandService.pushUserCarInfoCommand(responseBuilder, userCarService.getGarageCarList(user.getId()),
				        user.getId());
			} catch (SQLException e) {
				
			}
    	}
        return builder.build();
    }
}
