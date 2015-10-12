package com.ea.eamobile.nfsmw.service.command;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.bean.LotteryBean;
import com.ea.eamobile.nfsmw.model.bean.RewardBean;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestLotteryCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseEnergyTimeCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseLotteryCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RewardN;
import com.ea.eamobile.nfsmw.service.LotteryService;
import com.ea.eamobile.nfsmw.service.RewardService;
import com.ea.eamobile.nfsmw.service.UserInfoMessageService;
import com.ea.eamobile.nfsmw.service.UserService;

@Service
public class LotteryCommandService {
	
	@Autowired
    private UserInfoMessageService userInfoMessageService;
	@Autowired
    private UserService userService;
	@Autowired
    private RewardService rewardService;
	@Autowired
    private PushCommandService pushService;
	@Autowired
    private LotteryService lotteryService;
	
	private static final Logger log = LoggerFactory.getLogger(LotteryCommandService.class);
	
	/**
     * 获取抽奖内容
     * 
     * @param 
     * @return
     */
    public ResponseEnergyTimeCommand getEnergyTimeCommand(User user) {
        ResponseEnergyTimeCommand.Builder builder = ResponseEnergyTimeCommand.newBuilder();
        
        List<LotteryBean> lotteryList = lotteryService.getLotteryList(LotteryService.TPYE_RANDOM_ONETIME);
        List<LotteryBean> speLotteryList = lotteryService.getLotteryList(LotteryService.TYPE_RANDOM_TENTIME);
        
        return builder.build();
    }
    
    /**
     * 抽奖
     * 
     * @param 
     * @return
     */
    public ResponseLotteryCommand getResponseLotteryCommand(RequestLotteryCommand reqcmd, User user, 
    		Commands.ResponseCommand.Builder responseBuilder) {
    	ResponseLotteryCommand.Builder builder = ResponseLotteryCommand.newBuilder();
    	List<RewardN> list = new ArrayList<RewardN>();
    	int type = reqcmd.getLotteryType();
    	List<LotteryBean> lotteryList = lotteryService.randomLotteryList(type);
        
    	for (LotteryBean lottery : lotteryList) {
    		List<RewardBean> rewardList = lottery.getLotteryRewardList();
    		for (RewardBean reward : rewardList) {
    			RewardN.Builder rewardBuilder = RewardN.newBuilder();
    			rewardBuilder.setId(reward.getRewardId());
    			rewardBuilder.setCount(reward.getRewardCount());
    			list.add(rewardBuilder.build());	
    		}
    	}
    	builder.addAllRewards(list);
                
        return builder.build();
    }
}
