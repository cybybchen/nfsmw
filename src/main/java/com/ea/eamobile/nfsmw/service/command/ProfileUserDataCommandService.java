package com.ea.eamobile.nfsmw.service.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.ErrorConst;
import com.ea.eamobile.nfsmw.constants.ProfileComparisonType;
import com.ea.eamobile.nfsmw.constants.ProfileTrackLogConst;
import com.ea.eamobile.nfsmw.model.Car;
import com.ea.eamobile.nfsmw.model.ProfileTrackLog;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserCar;
import com.ea.eamobile.nfsmw.model.UserRaceAction;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.ProfileCarInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestProfileUserDataCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseProfileUserDataCommand;
import com.ea.eamobile.nfsmw.service.CarService;
import com.ea.eamobile.nfsmw.service.ProfileTrackLogService;
import com.ea.eamobile.nfsmw.service.UserCarService;
import com.ea.eamobile.nfsmw.service.UserRaceActionService;
import com.ea.eamobile.nfsmw.service.UserReportLogService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.view.CarView;
@Service
public class ProfileUserDataCommandService extends BaseCommandService{
    @Autowired
    private UserService userService;
    @Autowired
    private UserCarService userCarService;
    @Autowired
    private UserReportLogService userReportLogService;
	@Autowired
	private UserRaceActionService userRaceActionService;
	@Autowired
	private CarService carService;
	@Autowired
	private ProfileTrackLogService profileTrackLogService;
	
	public ResponseProfileUserDataCommand getProfileUserDataCommand(RequestProfileUserDataCommand reqcmd, User user,
            Commands.ResponseCommand.Builder responseBuilder) throws SQLException {
		
		ResponseProfileUserDataCommand.Builder builder = ResponseProfileUserDataCommand.newBuilder();
		
		buildProfileUserDataCommand(builder, reqcmd, user, responseBuilder);
        if (responseBuilder.hasErrorCommand()) {
            return null;
        }
		
		return builder.build();
	}

	private void buildProfileUserDataCommand(ResponseProfileUserDataCommand.Builder builder,
			RequestProfileUserDataCommand reqcmd, User user, Commands.ResponseCommand.Builder responseBuilder) throws SQLException
	{
		long profileId = reqcmd.getProfileId();
		User profileUser = userService.getUser(profileId);
		if (profileUser == null) {
			responseErrorCommand(responseBuilder, ErrorConst.USER_NOT_EXIST);
			return ;
		}
		
		builder.setNickname(profileUser.getName());
		builder.setHeadUrl(profileUser.getHeadUrl());
		builder.setHeadIndex(profileUser.getHeadIndex());
		builder.setRpLevel(profileUser.getLevel());
		builder.setRpExp(profileUser.getRpNum());
		
		freshRpNumInUserRaceAction(profileUser);
		freshRpNumInUserRaceAction(user);
		
		builder.setMostwantedNum(profileUser.getStarNum());
		
		CarView carView = userCarService.getUserBestCarView(profileId);
		if (carView == null) {
			responseErrorCommand(responseBuilder, ErrorConst.USER_CAR_NOT_EXIST);
			return ;
		}
		String carId = carView.getCarId();
		long userId = user.getId();
		UserCar userCar = userCarService.getUserCarByUserIdAndCarId(profileId, carId);
		
		ProfileCarInfo.Builder carInfo;
		if (userCar == null) {// if userCar == null: player don't unlock any car
			carInfo = buildProfileCarInfo();
		} else {
			carInfo = buildProfileCarInfo(userId, userCar, carView);
		}
		builder.setCarInfo(carInfo);
		
		builder.setCanReport(!userReportLogService.hasLog(userId, profileId));
		
		//List<UserCar> ownedCarList = userCarService.getUserCarOwnedList(profileId);
		Map<String, UserCar> userCarMap = userCarService.getUserCarMap(profileId);
		int carCount = 0;
		List<String> ownedCarIdList = new ArrayList<String>();
		List<Long> ownedUserCarIdList = new ArrayList<Long>();
		List<Car> carList=carService.getCarList();
		for(Car car:carList){
			if(userCarMap.get(car.getId())!=null){
				carCount++;
				ownedCarIdList.add(car.getId());
				ownedUserCarIdList.add(userCarMap.get(car.getId()).getId());
			}
		}
		builder.setCarNum(carCount);
		// UserRaceAction 数据刷新预处理：GARAGE_SCORE
		freshGarageScoreInUserRaceAction(userId);
		int totalScore = freshGarageScoreInUserRaceAction(profileId);
		builder.setGarageScore(totalScore);
		builder.addAllOwnedCarId(ownedCarIdList);
		builder.addAllOwnedUserCarId(ownedUserCarIdList);
		
		recordProfileTrackLog(reqcmd, userId, carId);
	}

    /**
     * UserRaceAction 数据刷新预处理：rpNum
     * 
     * @param user
     * @return
     */
	private int freshRpNumInUserRaceAction(User user){
		int rpNum = 0;
		long userId = user.getId();
        int valueId = ProfileComparisonType.RP_NUM.getIndex();
        UserRaceAction userRaceAction = (UserRaceAction) cache.get(CacheKey.USER_RACE_ACTION + userId + "_" + valueId);
        if (userRaceAction != null) {
        	rpNum = userRaceAction.getValue(); 
        } else {
        	rpNum = user.getRpNum();
        	userRaceActionService.refreshDataAndCache(userId, valueId, rpNum);
        }
        return rpNum;
	}

    /**
     * UserRaceAction 数据刷新预处理：GARAGE_SCORE
     * 
     * @param userId
     * @return
     */
	private int freshGarageScoreInUserRaceAction(long userId){
		int totalScore = 0;
        int valueId = ProfileComparisonType.GARAGE_SCORE.getIndex();
        UserRaceAction userRaceAction = (UserRaceAction) cache.get(CacheKey.USER_RACE_ACTION + userId + "_" + valueId);
        if (userRaceAction != null) {
        	totalScore = userRaceAction.getValue();
        } else {// 更新数据库，更新memcache，因为新建的表在车库信息未更新前各值均为0
        	totalScore = userCarService.getUserCarOwnedTotalScore(userId);
        	userRaceActionService.refreshDataAndCache(userId, valueId, totalScore);
        }
        return totalScore;
	}
	
	/**
	 * profile统计信息
	 * @param reqcmd
	 * @param userId
	 * @param carId
	 */
	private void recordProfileTrackLog(RequestProfileUserDataCommand reqcmd,
			long userId, String carId){
		ProfileTrackLog log = new ProfileTrackLog();
		if (log != null){
			log.setUserId(userId);
			log.setViewedUserId(reqcmd.getProfileId());
			log.setPageId(ProfileTrackLogConst.PAGE_ID_OVERVIEW);
			log.setSourceId(reqcmd.getSourceTypeId());
			log.setTournamentOnlineId(reqcmd.getTournamentOnlineId());
			log.setTournamentGroupId(reqcmd.getTournamentGroupId());
			log.setCarId(carId);
			profileTrackLogService.insert(log);
		}
	}
}
