package com.ea.eamobile.nfsmw.service.command;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.ProfileComparisonType;
import com.ea.eamobile.nfsmw.constants.ProfileTrackLogConst;
import com.ea.eamobile.nfsmw.model.ProfileComparison;
import com.ea.eamobile.nfsmw.model.ProfileTrackLog;
import com.ea.eamobile.nfsmw.model.Tournament;
import com.ea.eamobile.nfsmw.model.TournamentOnline;
import com.ea.eamobile.nfsmw.model.TournamentUser;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserRaceAction;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestProfileVSCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseProfileVSCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.UserVSProfile;
import com.ea.eamobile.nfsmw.service.ProfileComparisonService;
import com.ea.eamobile.nfsmw.service.ProfileTrackLogService;
import com.ea.eamobile.nfsmw.service.TournamentLeaderboardService;
import com.ea.eamobile.nfsmw.service.TournamentOnlineService;
import com.ea.eamobile.nfsmw.service.TournamentService;
import com.ea.eamobile.nfsmw.service.TournamentUserService;
import com.ea.eamobile.nfsmw.service.UserRaceActionService;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

@Service
public class ProfileVSCommandService {
	
	@Autowired
	private UserRaceActionService userRaceActionService;
    @Autowired
    private TournamentService tourService;
    @Autowired
    private TournamentUserService tourUserService;
    @Autowired
    private TournamentOnlineService onlineService;
    @Autowired
    private TournamentLeaderboardService tourLeaderboardService;
    @Autowired
    private MemcachedClient cache;
    @Autowired
    private ProfileComparisonService profileComparisonService;
    @Autowired
    private ProfileTrackLogService profileTrackLogService;
	
	public ResponseProfileVSCommand getProfileVSCommand(RequestProfileVSCommand reqcmd, User user,
            Commands.ResponseCommand.Builder responseBuilder) throws SQLException {

		ResponseProfileVSCommand.Builder builder = ResponseProfileVSCommand.newBuilder();
		
		buildProfileVSCommand(builder, reqcmd.getProfileId(), user.getId());
		
		return builder.build();
	}
		
	private void buildProfileVSCommand(ResponseProfileVSCommand.Builder builder,
			long profileId, long userId) throws SQLException {
		
        updateTournamentChampionCount(userId);
        updateTournamentChampionCount(profileId);
		List<UserRaceAction> userRaceActionList = userRaceActionService.getUserRaceActionListByUserId(userId);
		List<UserRaceAction> profileRaceActionList = userRaceActionService.getUserRaceActionListByUserId(profileId);
		List<ProfileComparison> profileComparisonList = profileComparisonService.getProfileComparisonList();
		
		if (profileComparisonList != null){
			for (ProfileComparison profileComparison : profileComparisonList) {
				UserVSProfile.Builder itemBuild = UserVSProfile.newBuilder();
				itemBuild.setTitleStr(profileComparison.getTitle());
				itemBuild.setSubTitleStr(profileComparison.getSubTitle());
				// value type:
				// 0: normal	""
				// 1: distance	"米／码／公里／英里"
				// 2: count		"个"
				// 3: car		"辆"
				// 4: score		"分"
				// 5: times		"次"
				// 6: points	"点"
				int valueType = profileComparison.getValueType();
				String unitsStr = "";
				itemBuild.setUnitsStr(unitsStr);
				itemBuild.setValueType(valueType);
				itemBuild.setHasProgressBar(profileComparison.getHasProgressBar());
				int valueId = profileComparison.getId();
				int value = getValue(userId, valueId, userRaceActionList);
				itemBuild.setPlayerValue(value);
				value = getValue(profileId, valueId, profileRaceActionList);
				itemBuild.setGhostValue(value);
				builder.addVsData(itemBuild);
			}
		}
		
		recordProfileTrackLog(userId, profileId);
	}

    /**
     * 更新Tournament冠军次数到UserRaceAction表，每天只更新一次
     * 
     * @param userId
     * @return
     */
	private void updateTournamentChampionCount(long userId) throws SQLException {
		int count = 0;
		Integer championCount = (Integer) cache.get(CacheKey.TOURNAMENT_CHAMPION_COUNT_UPDATE_TIME + userId);
		// Tournament冠军次数每小时只更新一次
		if (championCount == null){
			// 计算Tournament冠军次数
			List<Integer> idList = tourUserService.getTournamentUser(userId);
			List<TournamentOnline> finishedOnlineList = onlineService.getFinishedOnlineList(idList);
			for (TournamentOnline tournamentOnline : finishedOnlineList) {
				TournamentUser tourUser = tourUserService.getTournamentUserByUserIdAndTOnlineId(userId, tournamentOnline.getId());
				Tournament tour = tourService.getTournament(tournamentOnline.getTournamentId());
				if (tour != null) {
				int rankType = tour.getType();
				int rank = tourLeaderboardService.getRankFromLeaderboard(tourUser, rankType);
				if (rank == 1)// 1：第一名
					count++;
				}
			}
			// 存储Tournament冠军次数到UserRaceAction表
	        int valueId = ProfileComparisonType.TOURNAMENT_CHAMPION.getIndex();
	        UserRaceAction userRaceAction = userRaceActionService.getUserRaceActionByUserIdAndValueId(userId, valueId);
	        if (userRaceAction != null) {
	        	userRaceAction.setValue(count);
	        	userRaceActionService.update(userRaceAction);
	        } else {
	            userRaceActionService.insert(userId, valueId, count);
	        }
	        // 纪录Tournament冠军次数，1小时后失效
	        championCount = count;
			cache.set(CacheKey.TOURNAMENT_CHAMPION_COUNT_UPDATE_TIME + userId, championCount, MemcachedClient.HOUR);
		}
	}

    /**
     * 根据字段userId和valueId获取UserRaceAction表中的值
     * 
     * @param userId
     * @param valueId: 数据类型id
     * @param userRaceActionList: 数据库中已有数据列表
     * @return value: 返回UserRaceAction中的值，没有则返回0
     */
	private int getValue(long userId, int valueId, List<UserRaceAction> userRaceActionList) throws SQLException	{
		int value = 0;
		if (userRaceActionList != null){
			for (UserRaceAction userRaceAction : userRaceActionList){
				if (userRaceAction.getValueId() == valueId){
					value = userRaceAction.getValue();
					break;
				}
			}
		}
		return value;
	}

	/**
	 * profile统计信息
	 * @param userId
	 * @param viewedUserId
	 */
	private void recordProfileTrackLog(long userId, long viewedUserId){
		ProfileTrackLog log = new ProfileTrackLog();
		if (log != null){
			log.setUserId(userId);
			log.setViewedUserId(viewedUserId);
			log.setPageId(ProfileTrackLogConst.PAGE_ID_COMPARISON);
			profileTrackLogService.insert(log);
		}
	}
	
}
