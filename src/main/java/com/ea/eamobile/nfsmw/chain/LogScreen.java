package com.ea.eamobile.nfsmw.chain;

import org.springframework.beans.factory.annotation.Autowired;

import com.ea.eamobile.nfsmw.model.RequestLog;
import com.ea.eamobile.nfsmw.model.User;
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
import com.ea.eamobile.nfsmw.protoc.Commands.RequestFixCarLimitCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestFleetDoubleCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestFleetEndCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestFleetRaceCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestFleetRaceRefreshCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestFleetRankRewardCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestFleetStartCommand;
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
import com.ea.eamobile.nfsmw.protoc.Commands.RequestProfileCarCommand;
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
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseCommand.Builder;
import com.ea.eamobile.nfsmw.service.RequestLogService;

public class LogScreen extends RequestScreen {

    @Autowired
    private RequestLogService requestLogService;

    @Override
    protected boolean handleLoginCommand(RequestCommand request, Builder responseBuilder) {
        requestLogService.insert(new RequestLog(0, "Login", request.getLoginCommand().toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTrackCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "Track", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRaceResultCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "RaceResult", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "Tournament", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRegistJaguarCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "RegistJaguar", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentRewardDetailCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "TournamentRewardDetail", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentDetailCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "TournamentDetail", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestModeInfoCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "Ghost", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestResourceCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "Resource", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentSignUpCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "TournamentSignUp", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRaceStartCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "RaceStart", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBindingStartCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "BindingStart", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBindingResultCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "BindingResult", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBindingConfirmCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "BindingConfirm", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestGarageCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "Garage", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBuyCarCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "BuyCar", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestUpgradeSlotCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "UpgradeSlot", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestUseChartletCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "UseChartlet", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBuyItemCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "BuyItem", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestStoreDetailCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "StoreDetail", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestGetRewardCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "GetRward", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestModifyUserInfoCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "ModifyUserInfo", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestIapCheckCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "IapCheck", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRpLeaderboardCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "RpLeaderboard", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestProfileUserDataCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "ProfileUserData", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestProfileLikeCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "ProfileLike", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestProfileReportCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "ProfileReport", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestProfileVSCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "ProfileVS", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRecordUserRaceActionCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "RecordUserRaceAction", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestProfileNextCarCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "ProfileNextCar", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTutorialRewardCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "TutorialReward", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestGhostRecordCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "GhostRecord", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestChallengeMathInfoCommand cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "ChallengeMathInfo", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestCommand cmd, Builder responseBuilder, User user) {

        requestLogService.insert(new RequestLog(user.getId(), "CheatInfo", cmd.getCheatInfoCommand().toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestSystemCommand cmd, Builder responseBuilder, User user) {

        requestLogService.insert(new RequestLog(user.getId(), "System", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestGotchaCommand cmd, Builder responseBuilder, User user) {

        requestLogService.insert(new RequestLog(user.getId(), "Gotcha", cmd.toString()));
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentNum cmd, Builder responseBuilder, User user) {
        requestLogService.insert(new RequestLog(user.getId(), "TournamentNum", cmd.toString()));
        return true;
    }

	@Override
	protected boolean handleCommand(RequestBindingTokenCommand cmd,
			Builder responseBuilder, User user) {
		// TODO Auto-generated method stub
		requestLogService.insert(new RequestLog(user.getId(), "BindingToken", cmd.toString()));
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

	@Override
	protected boolean handleCommand(RequestFleetRaceCommand cmd, Builder responseBuilder, User user) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean handleCommand(RequestFleetStartCommand cmd, Builder responseBuilder, User user) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean handleCommand(RequestFleetEndCommand cmd, Builder responseBuilder, User user) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean handleCommand(RequestFleetDoubleCommand cmd, Builder responseBuilder, User user) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean handleCommand(RequestFixCarLimitCommand cmd, Builder responseBuilder, User user) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean handleCommand(RequestProfileCarCommand cmd, Builder responseBuilder, User user) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean handleCommand(RequestFleetRankRewardCommand cmd, Builder responseBuilder, User user) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean handleCommand(RequestFleetRaceRefreshCommand cmd, Builder responseBuilder, User user) {
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
