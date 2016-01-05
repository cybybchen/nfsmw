package com.ea.eamobile.nfsmw.chain;

import org.springframework.beans.factory.annotation.Autowired;

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
import com.ea.eamobile.nfsmw.service.ResponseLogService;

public class ResponseScreen extends RequestScreen {

    @Autowired
    private ResponseLogService responseLogService;

    @Override
    protected boolean handleLoginCommand(RequestCommand request, Builder responseBuilder) {
        responseLogService.insert(0, "Login", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTrackCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "Track", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRaceResultCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "RaceResult", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "Tournament", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRegistJaguarCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "RegistJaguar", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentRewardDetailCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "TournamentRewardDetail", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentDetailCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "TournamentDetail", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestModeInfoCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "Ghost", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestResourceCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "Resource", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentSignUpCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "TournamentSignUp", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRaceStartCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "RaceStart", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBindingStartCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "BindingStart", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBindingResultCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "BindingResult", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBindingConfirmCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "BindingConfirm", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestGarageCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "Garage", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBuyCarCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "BuyCar", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestUpgradeSlotCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "UpgradeSlot", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestUseChartletCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "UseChartlet", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBuyItemCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "BuyItem", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestStoreDetailCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "StoreDetail", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestGetRewardCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "GetRward", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestModifyUserInfoCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "ModifyUserInfo", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestIapCheckCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "IapCheck", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRpLeaderboardCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "RpLeaderboard", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestProfileUserDataCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "ProfileUserData", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRecordUserRaceActionCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "RecordUserRaceAction", responseBuilder.build().toString());
        return true;
    }
    
    @Override
    protected boolean handleCommand(RequestProfileLikeCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "ProfileLike", responseBuilder.build().toString());
        return true;
    }
    @Override
    protected boolean handleCommand(RequestProfileNextCarCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "ProfileNextCar", responseBuilder.build().toString());

        return true;
    }



  
    @Override
    protected boolean handleCommand(RequestProfileReportCommand cmd, Builder responseBuilder, User user) {

        responseLogService.insert(user.getId(), "ProfileReport", responseBuilder.build().toString());
        return true;
    }

    @Override  
    protected boolean handleCommand(RequestProfileVSCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "ProfileVS", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTutorialRewardCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "TutorialReward", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestGhostRecordCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "GhostRecord", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestChallengeMathInfoCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "ChallengeMathInfo", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestCommand cmd, Builder responseBuilder, User user) {
        responseLogService.insert(user.getId(), "CheatInfo", responseBuilder.build().toString());
        return true;
    }

    @Override
    protected boolean handleCommand(RequestSystemCommand cmd, Builder responseBuilder, User user) {

        return true;
    }

    @Override
    protected boolean handleCommand(RequestGotchaCommand cmd, Builder responseBuilder, User user) {
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
		responseLogService.insert(user.getId(), "BindingToken", responseBuilder.build().toString());
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
