package com.ea.eamobile.nfsmw.service.command;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestProfileReportCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseProfileReportCommand;
import com.ea.eamobile.nfsmw.service.UserReportLogService;
import com.ea.eamobile.nfsmw.service.UserReportService;

@Service
public class ProfileReportCommandService {
	
	@Autowired
	private UserReportService userReportService;
	@Autowired
	private UserReportLogService userReportLogService;
	
	public ResponseProfileReportCommand getProfileReportCommand(RequestProfileReportCommand reqcmd,
			User user,
            Commands.ResponseCommand.Builder responseBuilder) throws SQLException {
		
		ResponseProfileReportCommand.Builder builder = ResponseProfileReportCommand.newBuilder();
		
		buildProfileReportCommand(builder, reqcmd.getProfileId(), user.getId());
		
		return builder.build();
	}
	
	private void buildProfileReportCommand(ResponseProfileReportCommand.Builder builder,
			long reportedId, long userId) throws SQLException {
		
		boolean hasReported = userReportLogService.hasLog(userId, reportedId);
		
		if (!hasReported) {
			
			userReportLogService.insert(userId, reportedId);
			
			userReportService.add(userId, reportedId);
		}
	}

}
