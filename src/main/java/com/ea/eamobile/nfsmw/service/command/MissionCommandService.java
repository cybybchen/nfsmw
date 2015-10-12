package com.ea.eamobile.nfsmw.service.command;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.bean.TaskBean;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.MissionInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestMissionFinishCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestMissionRewardCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseMissionListCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseModifyUserInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.UserInfo;
import com.ea.eamobile.nfsmw.service.TaskService;
import com.ea.eamobile.nfsmw.service.UserInfoMessageService;
import com.ea.eamobile.nfsmw.service.UserService;

@Service
public class MissionCommandService {
	
	@Autowired
    private UserInfoMessageService userInfoMessageService;
	@Autowired
    private UserService userService;
	@Autowired
    private PushCommandService pushService;
	@Autowired
    private TaskService taskService;
	
	private static final Logger log = LoggerFactory.getLogger(MissionCommandService.class);
    
    public ResponseModifyUserInfoCommand modifyUserTaskFinishStatus(RequestMissionFinishCommand reqcmd, User user,
    		Commands.ResponseCommand.Builder responseBuilder) {
    	ResponseModifyUserInfoCommand.Builder builder = ResponseModifyUserInfoCommand.newBuilder();
    	UserInfo.Builder usbuilder = UserInfo.newBuilder();
        
    	int taskId = reqcmd.getId();
    	taskService.addTaskFinishStatus(user, taskId);
    	
    	userInfoMessageService.buildUserInfoMessage(usbuilder, user);
        builder.setUserinfo(usbuilder.build());
        
        return builder.build();
    }
    
    public ResponseModifyUserInfoCommand modifyUserTaskRewardStatus(RequestMissionRewardCommand reqcmd, User user,
    		Commands.ResponseCommand.Builder responseBuilder) {
    	ResponseModifyUserInfoCommand.Builder builder = ResponseModifyUserInfoCommand.newBuilder();
    	UserInfo.Builder usbuilder = UserInfo.newBuilder();
        
    	taskService.addTaskReward(user);
    	
    	userInfoMessageService.buildUserInfoMessage(usbuilder, user);
        builder.setUserinfo(usbuilder.build());
        
        return builder.build();
    }
}
