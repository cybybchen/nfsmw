package com.ea.eamobile.nfsmw.service.command;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.ErrorConst;
import com.ea.eamobile.nfsmw.constants.ProfileTrackLogConst;
import com.ea.eamobile.nfsmw.model.ProfileTrackLog;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserCar;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.ProfileCarInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestProfileNextCarCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseProfileNextCarCommand;
import com.ea.eamobile.nfsmw.service.ProfileTrackLogService;
import com.ea.eamobile.nfsmw.service.UserCarService;
import com.ea.eamobile.nfsmw.view.CarView;

@Service
public class ProfileNextCarCommandService extends BaseCommandService {

    @Autowired
    private UserCarService userCarService;
    @Autowired
    private ProfileTrackLogService profileTrackLogService;

	public ResponseProfileNextCarCommand getProfileNextCarCommand(RequestProfileNextCarCommand reqcmd, User user,
            Commands.ResponseCommand.Builder responseBuilder) throws SQLException {
		
		ResponseProfileNextCarCommand.Builder builder = ResponseProfileNextCarCommand.newBuilder();
		buildProfileNextCarCommand(builder, user.getId(), reqcmd.getUserCarId(), responseBuilder);
        if (responseBuilder.hasErrorCommand()) {
            return null;
        }
		return builder.build();
	}
	
	private void buildProfileNextCarCommand(ResponseProfileNextCarCommand.Builder builder, long userId,
			long userCarId, Commands.ResponseCommand.Builder responseBuilder) throws SQLException{

		UserCar userCar = userCarService.getUserCar(userCarId);
		if (userCar == null) {
			responseErrorCommand(responseBuilder, ErrorConst.USER_CAR_NOT_EXIST);
			return ;
		}
		CarView carView = userCarService.getUserCarView(userCar.getUserId(), userCar.getCarId());
		ProfileCarInfo.Builder carInfo = buildProfileCarInfo(userId, userCar, carView);
		builder.setCarInfo(carInfo);
		
		recordProfileTrackLog(userId, userCar.getUserId(), userCar.getCarId());
	}

	/**
	 * profile统计信息
	 * @param userId
	 * @param viewedUserId
	 * @param carId
	 */
	private void recordProfileTrackLog(long userId, long viewedUserId, String carId){
		ProfileTrackLog log = new ProfileTrackLog();
		if (log != null){
			log.setUserId(userId);
			log.setViewedUserId(viewedUserId);
			log.setPageId(ProfileTrackLogConst.PAGE_ID_NEXT_CAR);
			log.setCarId(carId);
			profileTrackLogService.insert(log);
		}
	}
}
