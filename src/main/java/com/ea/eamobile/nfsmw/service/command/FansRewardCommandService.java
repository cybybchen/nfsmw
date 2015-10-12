package com.ea.eamobile.nfsmw.service.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestFansRewardCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseFansRewardTimeCommand;
import com.ea.eamobile.nfsmw.service.FansRewardService;
import com.ea.eamobile.nfsmw.service.RewardService;
import com.ea.eamobile.nfsmw.service.UserInfoMessageService;
import com.ea.eamobile.nfsmw.service.UserPropService;
import com.ea.eamobile.nfsmw.service.UserService;

@Service
public class FansRewardCommandService {
	
	@Autowired
    private UserInfoMessageService userInfoMessageService;
	@Autowired
    private UserService userService;
	@Autowired
    private RewardService rewardService;
	@Autowired
    private PushCommandService pushService;
	@Autowired
    private UserPropService userPropService;
	@Autowired
    private FansRewardService fansRewardService;
	
	private static final Logger log = LoggerFactory.getLogger(FansRewardCommandService.class);
    
    public ResponseFansRewardTimeCommand getFansRewardTimeCommand(RequestFansRewardCommand reqcmd, User user,
    		Commands.ResponseCommand.Builder responseBuilder) {
    	ResponseFansRewardTimeCommand.Builder builder = ResponseFansRewardTimeCommand.newBuilder();
    	
        
    	int fansRewardId = reqcmd.getId();
    	fansRewardService.addUserFansReward(user, fansRewardId);
    	
               
        return builder.build();
    }
}
