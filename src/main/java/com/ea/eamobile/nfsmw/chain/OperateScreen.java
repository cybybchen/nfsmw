package com.ea.eamobile.nfsmw.chain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.constants.ProfileComparisonType;
import com.ea.eamobile.nfsmw.model.Car;
import com.ea.eamobile.nfsmw.model.OperateActivity;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands.RPMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBindingConfirmCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBindingInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBindingResultCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBindingStartCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBindingTokenCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBuyCarCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBuyItemCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestChallengeMathInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestCollectEnergyCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestEnergyTimeCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestFansRewardCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestGarageCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestGetRewardCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestGhostRecordCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestGotchaCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestIapCheckCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestLotteryCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestMissionFinishCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestMissionRewardCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestModeInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestModifyUserInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestProfileLikeCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestProfileNextCarCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestProfileReportCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestProfileUserDataCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestProfileVSCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestPropPurchaseCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestRaceResultCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestRaceStartCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestRecordUserRaceActionCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestRegistJaguarCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestResourceCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestRpLeaderboardCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestStoreDetailCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestSystemCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestTournamentCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestTournamentDetailCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestTournamentNum;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestTournamentRewardDetailCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestTournamentSignUpCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestTrackCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestTutorialRewardCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestUpgradeSlotCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestUseChartletCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseCommand.Builder;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseModifyUserInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseNotificationCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.UserInfo;
import com.ea.eamobile.nfsmw.service.CarChartletService;
import com.ea.eamobile.nfsmw.service.CarService;
import com.ea.eamobile.nfsmw.service.CtaContentService;
import com.ea.eamobile.nfsmw.service.OperateActivityService;
import com.ea.eamobile.nfsmw.service.UserExpenseRecService;
import com.ea.eamobile.nfsmw.service.UserInfoMessageService;
import com.ea.eamobile.nfsmw.service.UserRaceActionService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;
import com.ea.eamobile.nfsmw.utils.ConfigUtil;
import com.ea.eamobile.nfsmw.utils.DateUtil;
import com.ea.eamobile.nfsmw.utils.NumberUtil;

public class OperateScreen extends RequestScreen {

    @Autowired
    private UserService userService;
    @Autowired
    private UserExpenseRecService userExpenseRecService;
    @Autowired
    UserInfoMessageService userInfoMessageService;

    @Autowired
    private CtaContentService ctaContentService;
    @Autowired
    private CarChartletService chartletService;
    @Autowired
    private CarService carService;
    @Autowired
    private OperateActivityService operateActivityService;

    @Autowired
    private UserRaceActionService userRaceActionService;
    @Autowired
    private MemcachedClient cache;

    Logger log = LoggerFactory.getLogger(OperateScreen.class);

    @Override
    protected boolean handleLoginCommand(RequestCommand request, Builder responseBuilder) {

        return true;

    }

    @Override
    protected boolean handleCommand(RequestTrackCommand cmd, Builder responseBuilder, User user) {
        int canSeeNotification = NumberUtil.randomNumber(2);
        long currentTime = System.currentTimeMillis();
        if ((canSeeNotification == 1 && currentTime > DateUtil.getDateTime(ConfigUtil.ACT_START).getTime() && currentTime < DateUtil
                .getDateTime(ConfigUtil.ACT_END).getTime()) || user.getId() < 10000) {
            ResponseNotificationCommand.Builder rnbuilder = ResponseNotificationCommand.newBuilder();
            rnbuilder.setContent(ConfigUtil.IAP_ACTIVITY_CONTENT);
            rnbuilder.setDuration(5.0f);
            rnbuilder.setIconId(0);
            responseBuilder.setNotificationCommand(rnbuilder.build());
        }
        return true;

    }

    @Override
    protected boolean handleCommand(RequestRaceResultCommand cmd, Builder responseBuilder, User user) {

        boolean canUserTrackDouble = showDoubleUserTrack(cmd);
        if (canUserTrackDouble) {
            ResponseNotificationCommand.Builder rnbuilder = ResponseNotificationCommand.newBuilder();
            rnbuilder.setContent("恭喜用金币车获得双倍关卡完成度！");
            rnbuilder.setDuration(5.0f);
            rnbuilder.setIconId(0);
            responseBuilder.setNotificationCommand(rnbuilder.build());
        }
        updateUserInfoByOperateActivity(cmd, responseBuilder, user);

        // warn:rpmessage =userInfoCmd.getUserinfo().getRpMessage().getCurrentExp()
        // boolean canRpDouble = canRpDouble(cmd, user, responseBuilder);
        // if (canRpDouble) {
        // addRpNum = responseBuilder.build().getRaceResultCommand().getRewards().getRpNum();
        // ResponseModifyUserInfoCommand userInfoCmd = responseBuilder.build().getModifyUserInfoCommand();
        // ResponseModifyUserInfoCommand.Builder ruiBuilder = ResponseModifyUserInfoCommand.newBuilder(userInfoCmd);
        // UserInfo.Builder uiBuilder = UserInfo.newBuilder(userInfoCmd.getUserinfo());
        // uiBuilder.setRpExp((userInfoCmd.getUserinfo().getRpExp() + addRpNum));
        // RPMessage.Builder rpBuilder = RPMessage.newBuilder(userInfoCmd.getUserinfo().getRpMessage());
        // rpBuilder.setCurrentExp(userInfoCmd.getUserinfo().getRpMessage().getCurrentExp() + addRpNum);
        // uiBuilder.setRpMessage(rpBuilder.build());
        // user.setRpNum(user.getRpNum() + addRpNum);
        // userService.updateUser(user);
        // ruiBuilder.setUserinfo(uiBuilder.build());
        // responseBuilder.setModifyUserInfoCommand(ruiBuilder.build());
        // }

        return true;
    }

    private void updateUserInfoByOperateActivity(RequestRaceResultCommand cmd, Builder responseBuilder, User user) {

        if (cmd.getGameMode() == Match.EVERYDAY_RACE_MODE || cmd.getGameMode() == Match.TIER_MODE) {
            return;
        }
        int time = (int) (System.currentTimeMillis() / 1000);
        ResponseCommand responseCommand = responseBuilder.build();
        if (!responseCommand.hasModifyUserInfoCommand()) {
            return;
        }

        OperateActivity operateActivity = operateActivityService.getOperateActivityByTime(time);
        if (operateActivity == null) {
            return;
        }
        int addRpNum = 0;
        int addMoney = 0;
        int addGold = 0;
        if (cmd.getGameMode() == Match.CAREER_MODE) {
            addRpNum = (int) (responseCommand.getRaceResultCommand().getRewards().getRpNum() * (operateActivity
                    .getCareerRpTimes() - 1));
            addMoney = (int) (responseCommand.getRaceResultCommand().getRewards().getMoney() * (operateActivity
                    .getCareerMoneyTimes() - 1));
            addGold = (int) (responseCommand.getRaceResultCommand().getRewards().getRmb() * (operateActivity
                    .getCareerGoldTimes() - 1));
        } else if (cmd.getGameMode() == Match.TOURNAMENT_MODE) {
            addRpNum = (int) (responseCommand.getTournamentRaceReault().getRewards().getRpNum() * (operateActivity
                    .getTournamentRpTimes() - 1));
            addMoney = (int) (responseCommand.getTournamentRaceReault().getRewards().getMoney() * (operateActivity
                    .getTournamentMoneyTimes() - 1));
            addGold = (int) (responseCommand.getTournamentRaceReault().getRewards().getRmb() * (operateActivity
                    .getTournamentGoldTimes() - 1));
        }
        ResponseModifyUserInfoCommand userInfoCmd = responseCommand.getModifyUserInfoCommand();
        ResponseModifyUserInfoCommand.Builder ruiBuilder = ResponseModifyUserInfoCommand.newBuilder(userInfoCmd);
        UserInfo.Builder uiBuilder = UserInfo.newBuilder(userInfoCmd.getUserinfo());
        uiBuilder.setRpExp((userInfoCmd.getUserinfo().getRpMessage().getCurrentExp() + addRpNum));
        uiBuilder.setRmb(userInfoCmd.getUserinfo().getRmb() + addGold);
        uiBuilder.setCurrency(userInfoCmd.getUserinfo().getCurrency() + addMoney);
        RPMessage.Builder rpBuilder = RPMessage.newBuilder(userInfoCmd.getUserinfo().getRpMessage());

        rpBuilder.setCurrentExp(userInfoCmd.getUserinfo().getRpMessage().getCurrentExp() + addRpNum);
        uiBuilder.setRpMessage(rpBuilder.build());
        user.setRpNum(user.getRpNum() + addRpNum);
        user.setMoney(user.getMoney() + addMoney);
        user.setGold(user.getGold() + addGold);
        userService.updateUser(user);
        ruiBuilder.setUserinfo(uiBuilder.build());
        responseBuilder.setModifyUserInfoCommand(ruiBuilder.build());

        // update rpNum in user_race_action
        int valueId = ProfileComparisonType.RP_NUM.getIndex();
        long userId = user.getId();
        cache.delete(CacheKey.USER_RACE_ACTION + userId + "_" + valueId);
        int rpNum = user.getRpNum();
        userRaceActionService.refreshDataAndCache(userId, valueId, rpNum);
    }

    private boolean showDoubleUserTrack(RequestRaceResultCommand cmd) {
        boolean result = false;
        String carId = cmd.getGhosts().getCarID();
        Car car = carService.getCar(carId);
        if (car == null) {
            return false;
        }
        if (cmd.getGameMode() == Match.CAREER_MODE && car.getPriceType() == Const.GOLD) {
            result = true;
        }
        return result;
    }

    // private boolean canRpDouble(RequestRaceResultCommand cmd, User user, Builder responseBuilder) {
    // boolean result = false;
    // long startTime = DateUtil.getDateTime("2013-03-15 19:00:00").getTime();
    // long endTime = DateUtil.getDateTime("2013-03-18 12:00:00").getTime();
    // if (cmd.getGameMode() != Match.CAREER_MODE || !responseBuilder.build().hasRaceResultCommand()) {
    // return false;
    // }
    // if (System.currentTimeMillis() > startTime && System.currentTimeMillis() < endTime) {
    // result = true;
    // }
    // if (user.getId() < 10000) {
    // result = true;
    // }
    // return result;
    // }

    @Override
    protected boolean handleCommand(RequestTournamentCommand cmd, Builder responseBuilder, User user) {
        long currentTime = System.currentTimeMillis();
        int canSeeNotification = NumberUtil.randomNumber(2);
        if ((canSeeNotification == 1 && currentTime > DateUtil.getDateTime(ConfigUtil.ACT_START).getTime() && currentTime < DateUtil
                .getDateTime(ConfigUtil.ACT_END).getTime()) || user.getId() < 10000) {
            ResponseNotificationCommand.Builder rnbuilder = ResponseNotificationCommand.newBuilder();
            rnbuilder.setContent(ConfigUtil.IAP_ACTIVITY_CONTENT);
            rnbuilder.setDuration(5.0f);
            rnbuilder.setIconId(0);
            responseBuilder.setNotificationCommand(rnbuilder.build());
        }
        return true;

    }

    @Override
    protected boolean handleCommand(RequestRegistJaguarCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestTournamentRewardDetailCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestTournamentDetailCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestModeInfoCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestResourceCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestTournamentSignUpCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestRaceStartCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestBindingStartCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestBindingResultCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestBindingConfirmCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestGarageCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestBuyCarCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestUpgradeSlotCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestUseChartletCommand cmd, Builder responseBuilder, User user) {
        // ResponseModifyUserInfoCommand userInfoCmd = responseBuilder.build().getModifyUserInfoCommand();
        // ResponseUseChartletCommand useChartletCommand = responseBuilder.getUseChartletCommand();
        // if (System.currentTimeMillis() < 1362978000000l
        // && useChartletCommand.getSuccess()
        // && useChartletCommand.getMessage().equals(
        // ctaContentService.getCtaContent(CtaContentConst.BUY_SUCCESS).getContent())) {
        // int chartletId = cmd.getChartletId();
        // CarChartlet chartlet = chartletService.getCarChartlet(chartletId);
        // if (chartlet.getPriceType() != Const.GOLD) {
        // return true;
        // }
        // int addEnergy = chartlet.getPrice();
        // ResponseModifyUserInfoCommand.Builder ruiBuilder = ResponseModifyUserInfoCommand.newBuilder(userInfoCmd);
        // UserInfo.Builder uiBuilder = UserInfo.newBuilder(userInfoCmd.getUserinfo());
        // int energy = userInfoCmd.getUserinfo().getEnergy() + addEnergy;
        // // if (energy >= Match.ENERGY_MAX) {
        // // energy = Match.ENERGY_MAX;
        // // }
        // // int sendEnergy = energy - userInfoCmd.getUserinfo().getEnergy();
        // ResponseNotificationCommand.Builder rnbuilder = ResponseNotificationCommand.newBuilder();
        // rnbuilder.setContent("恭喜您额外获得" + addEnergy + "点汽油。");
        // rnbuilder.setDuration(3.0f);
        // rnbuilder.setIconId(0);
        // responseBuilder.setNotificationCommand(rnbuilder.build());
        // uiBuilder.setEnergy(energy);
        // user.setEnergy(energy);
        // user.setGold(userInfoCmd.getUserinfo().getRmb());
        // userService.updateUser(user);
        // ruiBuilder.setUserinfo(uiBuilder.build());
        // responseBuilder.setModifyUserInfoCommand(ruiBuilder.build());
        //
        // }

        return true;

    }

    @Override
    protected boolean handleCommand(RequestBuyItemCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestStoreDetailCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestGetRewardCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestModifyUserInfoCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestIapCheckCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestRpLeaderboardCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestProfileUserDataCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestProfileLikeCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestProfileReportCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestProfileVSCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestRecordUserRaceActionCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestProfileNextCarCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestTutorialRewardCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestGhostRecordCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestChallengeMathInfoCommand cmd, Builder responseBuilder, User user) {
        return true;

    }

    @Override
    protected boolean handleCommand(RequestCommand cmd, Builder responseBuilder, User user) {
        return true;
    }

    @Override
    protected boolean handleCommand(RequestGotchaCommand cmd, Builder responseBuilder, User user) {
        return true;
    }

    @Override
    protected boolean handleCommand(RequestSystemCommand cmd, Builder responseBuilder, User user) {
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentNum cmd, Builder responseBuilder, User user) {
        // TODO Auto-generated method stub
        return true;
    }

	@Override
	protected boolean handleCommand(RequestBindingTokenCommand cmd,
			Builder responseBuilder, User user) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean handleCommand(RequestBindingInfoCommand cmd,
			Builder responseBuilder, User user) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean handleCommand(RequestEnergyTimeCommand cmd,
			Builder responseBuilder, User user) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean handleCommand(RequestCollectEnergyCommand cmd,
			Builder responseBuilder, User user) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean handleCommand(RequestPropPurchaseCommand cmd,
			Builder responseBuilder, User user) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean handleCommand(RequestFansRewardCommand cmd,
			Builder responseBuilder, User user) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean handleCommand(RequestMissionRewardCommand cmd,
			Builder responseBuilder, User user) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean handleCommand(RequestMissionFinishCommand cmd,
			Builder responseBuilder, User user) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean handleCommand(RequestLotteryCommand cmd,
			Builder responseBuilder, User user) {
		// TODO Auto-generated method stub
		return true;
	}
	
//	@Override
//	protected boolean handleCommand(RequestSendCar cmd,
//			Builder responseBuilder, User user) {
//		// TODO Auto-generated method stub
//		return false;
//	}
}
