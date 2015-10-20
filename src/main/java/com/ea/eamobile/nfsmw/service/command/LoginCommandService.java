package com.ea.eamobile.nfsmw.service.command;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.constants.ErrorConst;
import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.constants.NicknameConst;
import com.ea.eamobile.nfsmw.model.Reward;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserLbs;
import com.ea.eamobile.nfsmw.model.UserRefreshTime;
import com.ea.eamobile.nfsmw.model.UserSession;
import com.ea.eamobile.nfsmw.model.UserVersionUpdateReward;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.ErrorCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.GPSInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.HeadInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestUserInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseModifyUserInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseCommand.Builder;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseUserInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.UserInfo;
import com.ea.eamobile.nfsmw.service.DeviceService;
import com.ea.eamobile.nfsmw.service.HintsService;
import com.ea.eamobile.nfsmw.service.JsonService;
import com.ea.eamobile.nfsmw.service.RewardService;
import com.ea.eamobile.nfsmw.service.SystemConfigService;
import com.ea.eamobile.nfsmw.service.UserCarService;
import com.ea.eamobile.nfsmw.service.UserInfoMessageService;
import com.ea.eamobile.nfsmw.service.UserLbsService;
import com.ea.eamobile.nfsmw.service.UserRefreshTimeService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.service.UserSessionService;
import com.ea.eamobile.nfsmw.service.UserVersionUpdateRewardService;
import com.ea.eamobile.nfsmw.service.UserVipService;
import com.ea.eamobile.nfsmw.view.BaseView;
import com.ea.eamobile.nfsmw.view.CarView;
import com.ea.eamobile.nfsmw.view.UserView;

@Service
public class LoginCommandService {

    @Autowired
    private UserService userService;
    @Autowired
    private JsonService jsonService;
    @Autowired
    private UserSessionService sessionService;
    @Autowired
    private PushCommandService pushService;
    @Autowired
    private UserInfoMessageService userInfoMessageService;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private UserCarService userCarService;
    @Autowired
    private HintsService hintsService;
    @Autowired
    private UserLbsService userLbsService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private UserVersionUpdateRewardService userVersionUpdateRewardService;
    @Autowired
    private RewardService rewardService;
    @Autowired
    private UserRefreshTimeService userRefreshTimeService;
    @Autowired
    private UserVipService userVipService;

    private static final Logger log = LoggerFactory.getLogger(LoginCommandService.class);

    public void buildResponseUserInfo(RequestCommand request, Builder response) throws SQLException {

        RequestUserInfoCommand reqcmd = request.getLoginCommand();
        HeadInfo head = request.getHead();
        HeadInfo.Builder headBuilder = buildHeadInfo(head);
        boolean isOld = reqcmd.getIsOld();
        log.debug("isOld is {}", isOld);
        User user = new User();
        if (!isOld)
        	user = getNewUser(reqcmd, headBuilder, response);
        else
        	user = getResponseUserInfoCommand(reqcmd, headBuilder, response);
        //User user = loginUser(reqcmd, headBuilder, response, 1);
        log.info(">>>>>>>>>>>>>>>>>>>>>1");
        if(user==null){
        	log.info(">>>>>>>>>>>>>>>>>>>>>2");
            return;
        }
        
        //领取贵族奖励
        doVipReward(response, user);
        doMonthGoldCardReward(response, user);
        
        head = headBuilder.build();
        response.setHead(head);
        log.info(">>>>>>>>>>>>>>>>>>>>>3");
        UserVersionUpdateReward userVersionUpdateReward = userVersionUpdateRewardService
                .getUserVersionUpdateReward(user.getId());
        if (userVersionUpdateReward == null) {
            userVersionUpdateReward = new UserVersionUpdateReward();
            userVersionUpdateReward.setUserId(user.getId());
            userVersionUpdateReward.setVersion(head.getVersion());
            userVersionUpdateRewardService.insert(userVersionUpdateReward);
        } else if (userVersionUpdateReward.getVersion() == Const.VERSION_ONE_NUM
                && head.getVersion() == Const.VERSION_TWO_NUM) {
            userVersionUpdateReward.setVersion(Const.VERSION_TWO_NUM);
            userVersionUpdateRewardService.update(userVersionUpdateReward);
            Reward reward = rewardService.getReward(Const.USER_UPDATE_VERSION_REWARD_ID);
            if (reward != null) {
                rewardService.doRewards(user, Const.USER_UPDATE_VERSION_REWARD_ID);
                pushService.pushPopupCommand(response, reward, Match.USER_VERSION_UPDATE_POPUP, "", 0, 0);
                pushService.pushUserInfoCommand(response, user);
            }
        }
        
        setPushCommand(response, user, head.getSession(), reqcmd, head.getVersion());
    }

    private void setPushCommand(Commands.ResponseCommand.Builder responseBuilder, User user, String session,
            RequestUserInfoCommand reqcmd, int version) throws SQLException {
        if (user == null) {
            return;
        }
        // 璁剧疆鐢ㄦ埛鐨勮澶囩被鍨�
        int deviceType = deviceService.getDeviceType(reqcmd.getDeviceName());
        user.setDeviceType(deviceType);

        if (user.getTier() > 0) {
            pushService.pushDailyRaceInfoCommand(responseBuilder, user, 1, 0);
        }
        int eventOptionVersion = Integer.valueOf(systemConfigService.getEventOptionVersion());
        UserRefreshTime userRefreshTime = userRefreshTimeService.getUserRefreshTime(user.getId());
        if (userRefreshTime == null) {
            userRefreshTime = new UserRefreshTime();
            userRefreshTime.setUserId(user.getId());
            userRefreshTime.setTime((int) (System.currentTimeMillis() / 1000));
            userRefreshTimeService.insert(userRefreshTime);
        } else {
            userRefreshTime.setTime((int) (System.currentTimeMillis() / 1000));
            userRefreshTimeService.update(userRefreshTime);
        }
        // 浼燿evicename杩涘幓鐢ㄦ潵鏄剧ず椤甸潰绫诲瀷
        pushService.pushSystemCommand(responseBuilder, session, user, eventOptionVersion, version);
        pushService.pushRegistJaguarCommand(responseBuilder, user);
        pushService.pushWeiboShareLocksCommand(responseBuilder);
        pushService.pushFeedCommand(responseBuilder, user.getId());
        pushService.pushTierInfoCommand(responseBuilder, user.getId());
        List<CarView> carViewList = userCarService.getGarageCarList(user.getId());
        pushService.pushUserCarInfoCommand(responseBuilder, carViewList, user.getId());
        pushService.pushStoreDetailCommand(responseBuilder, user.getId());
        if (version == Const.VERSION_ONE_NUM) {
            pushService.pushTournamentRewardNum(responseBuilder, user);
        }
        if (reqcmd.hasGpsInfo()) {
            GPSInfo info = reqcmd.getGpsInfo();
            saveUserLbsInfo(info, user.getId(), reqcmd.getDeviceName(), reqcmd.getMac());
        }
    }

    public User getResponseUserInfoCommand(RequestUserInfoCommand reqcmd,
            com.ea.eamobile.nfsmw.protoc.Commands.HeadInfo.Builder headBuilder,
            Commands.ResponseCommand.Builder responseBuilder) {
        ResponseUserInfoCommand.Builder builder = ResponseUserInfoCommand.newBuilder();
        User user = null;
        String deviceId = reqcmd.getMac();
        String nickname = reqcmd.getDeviceName();
        String token = reqcmd.getToken();
        String session = "";
        //先确定是否新版本用户登录
//        user = getNewUser(reqcmd, headBuilder, responseBuilder);
//        if (user != null)
//        	return user;
        
        BaseView loginResult = jsonService.login(deviceId, nickname, token);
        if (loginResult instanceof UserView) {
            UserView loginUserView = (UserView) loginResult;
            loginUserView.setNickname(NicknameConst.nicknameByDeviceId(deviceId));
            UserView userView = userService.getUserView(loginUserView);
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>fillUserInfoBuilder");
            fillUserInfoBuilder(builder, userView);
            builder.setToken(userView.getToken());
            builder.setDefaultHint(hintsService.getRandomHint());
            user = userView.getUser();
            if ((user.getAccountStatus() & Const.IS_BAN) == 1) {
                ErrorCommand.Builder erBuilder = ErrorCommand.newBuilder();
                erBuilder.setCode(String.valueOf(ErrorConst.BIND_USER.getCode()));
                erBuilder.setMessage(ErrorConst.BIND_USER.getMesssage());
                responseBuilder.setErrorCommand(erBuilder.build());
                log.warn("is_ban_user , id = {}",user.getId());
                return null;
            }
            // 姣忔鐧诲綍瑕佸埛鏂皊ession
            session = sessionService.generateSession(user.getId(), userView.getToken());
            sessionService.save(user.getId(), session);
            // set to response headinfo
            headBuilder.setSession(session);
            // swrve
        } else {
            // TODO add error cmd
            log.warn("wt err return = {}",loginResult);
        }
        responseBuilder.setUserInfoCommand(builder.build());
        UserSession userSession = sessionService.getSession(session);
        if (userSession == null || sessionService.isExpired(userSession)) {
            log.debug("session error: request session is = {}", session);
            // 鍏煎绛塩lient瀹炵幇鍐嶅紑鍚�
            // return returnErrorCommand(responseBuilder,new ErrorInfo("1000","session is expired."));
        }
        return user;
    }

    private void saveUserLbsInfo(GPSInfo info, long userId, String deviceName, String mac) {
        if(info.getLatitude()==0 && info.getLongitude()==0){
            return;
        }
        UserLbs userLbs = new UserLbs();
        userLbs.setCountry(info.getCountry());
        userLbs.setLatitude(info.getLatitude());
        userLbs.setLocality(info.getLocality());
        userLbs.setLongitude(info.getLongitude());
        userLbs.setSubLocality(info.getSubLocality());
        userLbs.setSubThoroughfare(info.getSubThoroughfare());
        userLbs.setThoroughfare(info.getThoroughfare());
        userLbs.setUserId(userId);
        userLbs.setDeviceName(deviceName);
        userLbs.setMac(mac);
        userLbsService.insert(userLbs);
    }

    private void fillUserInfoBuilder(ResponseUserInfoCommand.Builder builder, UserView userView) {
        UserInfo.Builder userBuilder = UserInfo.newBuilder();
        userInfoMessageService.buildUserInfoMessage(userBuilder, userView.getUser(), userView.getAccounts(), null);
        try {
        	log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>before builder.setUserInfo");
            builder.setUserInfo(userBuilder.build());
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>after builder.setUserInfo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void fillUserInfoBuilder(ResponseUserInfoCommand.Builder builder, User user) {
//    	List<AccountInfo> accounts= new ArrayList<AccountInfo>();
        UserInfo.Builder userBuilder = UserInfo.newBuilder();
        userInfoMessageService.buildUserInfoMessage(userBuilder, user, null, null);
        try {
        	log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>before builder.setUserInfo");
            builder.setUserInfo(userBuilder.build());
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>after builder.setUserInfo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HeadInfo.Builder buildHeadInfo(HeadInfo head) {
        HeadInfo.Builder headBuilder = HeadInfo.newBuilder();
        headBuilder.setGameVersion(head.getGameVersion());
        headBuilder.setVersion(head.getVersion());
        headBuilder.setSession(head.getSession());
        headBuilder.setDatetime(System.currentTimeMillis() / 1000);
        return headBuilder;
    }
    
    /**
	 * 用户登录 获取user
	 * 
	 * @param reqcmd
	 * @param headBuilder
	 * @param responseBuilder
	 * @param gameVersionType
	 * @return
	 */
	private User loginUser(RequestUserInfoCommand reqcmd,
			com.ea.eamobile.nfsmw.protoc.Commands.HeadInfo.Builder headBuilder,
			Commands.ResponseCommand.Builder responseBuilder,
			int gameVersionType) {
		// 要返回给前端的
		ResponseUserInfoCommand.Builder builder = ResponseUserInfoCommand
				.newBuilder();

		// 设备ID
		String deviceId = reqcmd.getMac();
		// 昵称
		String nickname = reqcmd.getDeviceName();
		User u = userService.getUserByWillowtreeToken(reqcmd.getToken());
		// 用户不存在
		if (null == u) {
			userService.initUser(nickname, "", reqcmd.getToken());
			if(null == u) {
				return null;
			}
		}

//		if ((u.getAccountStatus() & Const.IS_BAN) == 1) {
//			ErrorCommand.Builder erBuilder = ErrorCommand.newBuilder();
//			erBuilder.setCode(String.valueOf(ErrorConst.BIND_USER.getCode()));
//			erBuilder.setMessage(ErrorConst.BIND_USER.getMesssage());
//			responseBuilder.setErrorCommand(erBuilder.build());
//			log.warn("is_ban_user , id = {}", u.getId());
//			retCode[0] = Const.ERR_LOGIN_BAN;
//			return null;
//		}
		// 每次登录要刷新session
		String session = sessionService.generateSession(u.getId(),
				u.getWillowtreeToken());
		sessionService.save(u.getId(), session);

		headBuilder.setSession(session);

		builder.setToken(u.getWillowtreeToken());
		builder.setDefaultHint(hintsService.getRandomHint());

		

		responseBuilder.setUserInfoCommand(builder.build());
		UserSession userSession = sessionService.getSession(session);

		if (userSession == null || sessionService.isExpired(userSession)) {
			log.debug("session error: request session is = {}", session);
			// 兼容等client实现再开启
			// return returnErrorCommand(responseBuilder,new
			// ErrorInfo("1000","session is expired."));
		}
		
		return u;
	}
	
	private User getNewUser(RequestUserInfoCommand reqcmd,
			com.ea.eamobile.nfsmw.protoc.Commands.HeadInfo.Builder headBuilder,
			Commands.ResponseCommand.Builder responseBuilder) {
		// 要返回给前端的
		ResponseUserInfoCommand.Builder builder = ResponseUserInfoCommand.newBuilder();
		// 设备ID
		String deviceId = reqcmd.getMac();
		// 昵称
		String nickname = reqcmd.getDeviceName();
		
		User u = new User();
		String token = reqcmd.getToken();
		if (token.equals("")) {
			token = sessionService.generateToken(deviceId,
					nickname);
			u = userService.initUser(null, "", token);
			if(null == u) {
				return null;
			}
			u.setIsNewUser(1);
        	u.setFansRewardLastTime(u.getCreateTime());
		} else {
			u = userService.getUserByWillowtreeToken(reqcmd.getToken());
			// 用户不存在
			if (null == u) {
				return null;
//				u = userService.initUser(nickname, "", reqcmd.getToken());
//				if(null == u) {
//					return null;
//				}
			}
		}
		// 每次登录要刷新session
		String session = sessionService.generateSession(u.getId(),
				u.getWillowtreeToken());
		sessionService.save(u.getId(), session);

		headBuilder.setSession(session);

		regainEnergy(responseBuilder, u);
		
		fillUserInfoBuilder(builder, u);
		builder.setToken(u.getWillowtreeToken());
		log.debug("u is={}", u);
		builder.setDefaultHint(hintsService.getRandomHint());

		responseBuilder.setUserInfoCommand(builder.build());
		UserSession userSession = sessionService.getSession(session);
		
		if (userSession == null || sessionService.isExpired(userSession)) {
			log.debug("session error: request session is = {}", session);
			// 兼容等client实现再开启
			// return returnErrorCommand(responseBuilder,new
			// ErrorInfo("1000","session is expired."));
		}
		
		return u;
	}
	
	private void regainEnergy(Builder responseBuilder, User user) {
//		if (user.getEnergy() < Match.ENERGY_MAX) {
    		user = userService.regainEnergy(user);
//            pushService.pushUserInfoCommand(responseBuilder, user);
//    	}
    }
	
	private void doVipReward(Builder responseBuilder, User user) {
        boolean ret = userVipService.addUserVipReward(user, responseBuilder);
        if (ret) {
        	pushService.pushPopupListCommand(responseBuilder, null, Match.SEND_VIPREWARD_POPUP, "每日登录获得[color=fbce54]10金币，$100，免费抽奖1次[/color] ", 0, 0);
        }
    }
	
	private void doMonthGoldCardReward(Builder responseBuilder, User user) {
        boolean ret = userVipService.doUserMonthGoldCardReward(user);
        if (ret) {
        	pushService.pushPopupListCommand(responseBuilder, null, Match.SEND_MONTH_GOLD_POPUP, "每日登录获得[color=fbce54]50金币[/color] ", 0, 0);
        }
    }
}
