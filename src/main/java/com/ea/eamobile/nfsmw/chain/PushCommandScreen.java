package com.ea.eamobile.nfsmw.chain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.model.CarChangeTime;
import com.ea.eamobile.nfsmw.model.GotchaCar;
import com.ea.eamobile.nfsmw.model.GotchaExpense;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserRefreshTime;
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
import com.ea.eamobile.nfsmw.service.CarChangeTimeService;
import com.ea.eamobile.nfsmw.service.CarSlotConsumableService;
import com.ea.eamobile.nfsmw.service.CarSlotService;
import com.ea.eamobile.nfsmw.service.UserCarService;
import com.ea.eamobile.nfsmw.service.UserChartletService;
import com.ea.eamobile.nfsmw.service.UserRefreshTimeService;
import com.ea.eamobile.nfsmw.service.command.PushCommandService;
import com.ea.eamobile.nfsmw.service.gotcha.GotchaCarService;
import com.ea.eamobile.nfsmw.service.gotcha.GotchaExpenseService;
import com.ea.eamobile.nfsmw.utils.DateUtil;
import com.ea.eamobile.nfsmw.view.CarView;

public class PushCommandScreen extends RequestScreen {

    @Autowired
    private CarChangeTimeService carChangeTimeService;
    @Autowired
    private UserRefreshTimeService userRefreshTimeService;
    @Autowired
    private CarSlotService slotService;
    @Autowired
    private CarSlotConsumableService slotConsumbleService;
    @Autowired
    private UserChartletService userChartletService;
    @Autowired
    private PushCommandService pushCommandService;
    @Autowired
    private UserCarService userCarService;
    @Autowired
    private GotchaExpenseService gotchaExpenseService;
    @Autowired
    private GotchaCarService gotchaCarService;

    @Override
    protected boolean handleLoginCommand(RequestCommand request, Builder responseBuilder) {
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTrackCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRaceResultCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRegistJaguarCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentRewardDetailCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentDetailCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestModeInfoCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestResourceCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentSignUpCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRaceStartCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBindingStartCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBindingResultCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBindingConfirmCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestGarageCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBuyCarCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestUpgradeSlotCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestUseChartletCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBuyItemCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestStoreDetailCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestGetRewardCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestModifyUserInfoCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestIapCheckCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRpLeaderboardCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestProfileUserDataCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestProfileLikeCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestProfileReportCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestProfileVSCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRecordUserRaceActionCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestProfileNextCarCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTutorialRewardCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestGhostRecordCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestChallengeMathInfoCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestCommand cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    private void updateRefeshTime(Builder responseBuilder, User user) throws SQLException {
        UserRefreshTime userRefreshTime = userRefreshTimeService.getUserRefreshTime(user.getId());
        if (userRefreshTime == null) {
            return;
        }
        List<String> refreshCarList = new ArrayList<String>();
        List<CarChangeTime> carList = carChangeTimeService.getCarChangeTimeListByTime(userRefreshTime.getTime());
        Date lastUseTime = new Date(userRefreshTime.getTime() * 1000L - Const.DAILY_GOTCHA_FREE_TIME_HOUR_SECONDS
                * 1000l);

        Date currentTime = new Date(System.currentTimeMillis() - Const.DAILY_GOTCHA_FREE_TIME_HOUR_SECONDS * 1000l);
        int days = DateUtil.intervalDays(currentTime, lastUseTime);
        if (days > 0) {
            List<Integer> gotchaIds = new ArrayList<Integer>();
            List<GotchaExpense> gotchaExpenses = gotchaExpenseService.getAllGotchaExpenseList();
            if (gotchaExpenses != null) {
                for (GotchaExpense gotchaExpense : gotchaExpenses) {
                    if (gotchaExpense.getDailyFreeTimes() > 0) {
                        gotchaIds.add(gotchaExpense.getGotchaId());
                    }
                }
            }
            if (gotchaIds.size() > 0) {
                for (int id : gotchaIds) {
                    GotchaCar gotchaCar = gotchaCarService.getGotchaCarById(id);
                    if (gotchaCar == null) {
                        continue;
                    }
                    if (refreshCarList.contains(gotchaCar.getCarId())) {
                        continue;
                    }
                    refreshCarList.add(gotchaCar.getCarId());
                }
            }
        }
        List<CarView> result = new ArrayList<CarView>();
        for (CarChangeTime carChangeTime : carList) {
            if (refreshCarList.contains(carChangeTime.getCarId())) {
                continue;
            }
            refreshCarList.add(carChangeTime.getCarId());
        }
        for (String carId : refreshCarList) {
            CarView carView = userCarService.getUserCarView(user.getId(), carId);
            if (carView != null) {
                result.add(carView);
            }
        }
        if (result.size() > 0) {
            pushCommandService.pushUserCarInfoCommand(responseBuilder, result, user.getId());
        }
        userRefreshTime.setTime((int) (System.currentTimeMillis() / 1000));
        userRefreshTimeService.update(userRefreshTime);
    }

    @Override
    protected boolean handleCommand(RequestSystemCommand cmd, Builder responseBuilder, User user) {
        // TODO Auto-generated method stub
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestGotchaCommand cmd, Builder responseBuilder, User user) {
        // TODO Auto-generated method stub
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentNum cmd, Builder responseBuilder, User user) {
        try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

	@Override
	protected boolean handleCommand(RequestBindingTokenCommand cmd,
			Builder responseBuilder, User user) {
		try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
	}

	@Override
	protected boolean handleCommand(RequestBindingInfoCommand cmd,
			Builder responseBuilder, User user) {
		try {
            updateRefeshTime(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
	
//	@Override
//	protected boolean handleCommand(RequestSendCar cmd,
//			Builder responseBuilder, User user) {
//		// TODO Auto-generated method stub
//		return false;
//	}
}
