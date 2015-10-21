package com.ea.eamobile.nfsmw.service.command;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.bean.FansRewardBean;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestFansRewardCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseModifyUserInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.UserInfo;
import com.ea.eamobile.nfsmw.service.FansRewardService;
import com.ea.eamobile.nfsmw.service.RewardService;
import com.ea.eamobile.nfsmw.service.UserCarService;
import com.ea.eamobile.nfsmw.service.UserInfoMessageService;

@Service
public class FansRewardCommandService {
	
	@Autowired
    private UserInfoMessageService userInfoMessageService;
	@Autowired
    private RewardService rewardService;
	@Autowired
    private PushCommandService pushService;
	@Autowired
    private FansRewardService fansRewardService;
	@Autowired
    private UserCarService userCarService;
	
	private static final Logger log = LoggerFactory.getLogger(FansRewardCommandService.class);
    
    public ResponseModifyUserInfoCommand getFansRewardCommand(RequestFansRewardCommand reqcmd, User user,
    		Commands.ResponseCommand.Builder responseBuilder) {
    	ResponseModifyUserInfoCommand.Builder builder = ResponseModifyUserInfoCommand.newBuilder();
    	UserInfo.Builder usbuilder = UserInfo.newBuilder();
        
    	int fansRewardId = reqcmd.getId();
    	FansRewardBean fansReward = fansRewardService.addUserFansReward(user, fansRewardId);
    	
    	userInfoMessageService.buildUserInfoMessage(usbuilder, user);
        builder.setUserinfo(usbuilder.build());
        if (fansReward != null)
        	pushService.pushPopupCommand(responseBuilder, null, Match.SEND_FANSREWARD_POPUP, "[color=fbce54]" + fansReward.getName() + "[/color]！", 0, 0);
       
        try {
			pushService.pushUserCarInfoCommand(responseBuilder, userCarService.getGarageCarList(user.getId()),
			        user.getId());
		} catch (SQLException e) {
			
		}
        return builder.build();
    }
}
