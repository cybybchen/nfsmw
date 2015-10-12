package com.ea.eamobile.nfsmw.service.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.bean.UserPropBean;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestPropPurchaseCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseModifyUserInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.UserInfo;
import com.ea.eamobile.nfsmw.service.RewardService;
import com.ea.eamobile.nfsmw.service.UserInfoMessageService;
import com.ea.eamobile.nfsmw.service.UserPropService;
import com.ea.eamobile.nfsmw.service.UserService;

@Service
public class PropCommandService {
	
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
	
	private static final Logger log = LoggerFactory.getLogger(PropCommandService.class);
    
    /**
     * 用户领取油，不能超过上限
     * 
     * @param 
     * @return
     */
    public ResponseModifyUserInfoCommand getPropPurchaseCommand(RequestPropPurchaseCommand reqcmd, User user,
    		Commands.ResponseCommand.Builder responseBuilder) {
    	ResponseModifyUserInfoCommand.Builder builder = ResponseModifyUserInfoCommand.newBuilder();
    	UserInfo.Builder usbuilder = UserInfo.newBuilder();
        
        int propId = reqcmd.getId();
        UserPropBean userProp = userPropService.buyProp(user.getId(), propId);
        userInfoMessageService.buildUserInfoMessage(usbuilder, user, userProp);
        builder.setUserinfo(usbuilder.build());
               
        return builder.build();
    }
}
