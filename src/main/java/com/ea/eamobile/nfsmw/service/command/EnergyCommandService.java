package com.ea.eamobile.nfsmw.service.command;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.Reward;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestCollectEnergyCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestEnergyTimeCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseEnergyTimeCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseModifyUserInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.UserInfo;
import com.ea.eamobile.nfsmw.service.RewardService;
import com.ea.eamobile.nfsmw.service.UserInfoMessageService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.utils.DateUtil;

@Service
public class EnergyCommandService {
	
	@Autowired
    private UserInfoMessageService userInfoMessageService;
	@Autowired
    private UserService userService;
	@Autowired
    private RewardService rewardService;
	@Autowired
    private PushCommandService pushService;
	
	private static final Logger log = LoggerFactory.getLogger(EnergyCommandService.class);
	
	/**
     * 获取领取油的时间
     * 
     * @param 
     * @return
     */
    public ResponseEnergyTimeCommand getEnergyTimeCommand(User user) {
        ResponseEnergyTimeCommand.Builder builder = ResponseEnergyTimeCommand.newBuilder();
        Map<String, Integer> timeMap = DateUtil.getEnergyTimeMap(user.getLastSendEnergyDate() * 1000L);
        builder.setStarttime(timeMap.get(DateUtil.STARTTIME_STRING));
        builder.setEndtime(timeMap.get(DateUtil.ENDTIME_STRING));
        builder.setEnergy(Match.SEND_ENERGY_AMOUNT);
        return builder.build();
    }
    
    /**
     * 用户领取油，不能超过上限
     * 
     * @param 
     * @return
     */
    public ResponseModifyUserInfoCommand getModifyUserInfoCommand(Commands.ResponseCommand.Builder responseBuilder, User user) {
    	ResponseModifyUserInfoCommand.Builder builder = ResponseModifyUserInfoCommand.newBuilder();
        UserInfo.Builder usbuilder = UserInfo.newBuilder();
        
        boolean canGetSendEnergy = userService.canGetSendEnergy(user);
        userInfoMessageService.buildUserInfoMessage(usbuilder, user);
        builder.setUserinfo(usbuilder.build());
        
        if (canGetSendEnergy) {
            pushService.pushPopupCommand(responseBuilder, null, Match.SEND_ENERGY_POPUP, "", 0,
                    0);
        } else {
        	pushService.pushPopupCommand(responseBuilder, null, Match.SEND_ENERGY_FAILED_POPUP, "", 0,
                    0);
        }
               
        return builder.build();
    }
}
