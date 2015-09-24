package com.ea.eamobile.nfsmw.service.command;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.bean.LotteryBean;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseEnergyTimeCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseModifyUserInfoCommand;
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
    public ResponseModifyUserInfoCommand getModifyUserInfoCommand(Commands.ResponseCommand.Builder responseBuilder, User user) {
    	ResponseModifyUserInfoCommand.Builder builder = ResponseModifyUserInfoCommand.newBuilder();
        
    	int type = 0;
    	List<LotteryBean> lotteryList = lotteryService.randomLotteryList(type);
        
        
        
        
               
        return builder.build();
    }
}
