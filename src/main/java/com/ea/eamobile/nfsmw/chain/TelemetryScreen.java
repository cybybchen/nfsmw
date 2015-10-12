package com.ea.eamobile.nfsmw.chain;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.action.NfsThreadLocal;
import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.CarSlot;
import com.ea.eamobile.nfsmw.model.CarSlotConsumable;
import com.ea.eamobile.nfsmw.model.Leaderboard;
import com.ea.eamobile.nfsmw.model.LeaderboardChangeRecord;
import com.ea.eamobile.nfsmw.model.RaceMode;
import com.ea.eamobile.nfsmw.model.SpendActivity;
import com.ea.eamobile.nfsmw.model.SpendActivityReward;
import com.ea.eamobile.nfsmw.model.SpendReward;
import com.ea.eamobile.nfsmw.model.TelemetryRecord;
import com.ea.eamobile.nfsmw.model.Tournament;
import com.ea.eamobile.nfsmw.model.TournamentLeaderboard;
import com.ea.eamobile.nfsmw.model.TournamentLeaderboardChangeRecord;
import com.ea.eamobile.nfsmw.model.TournamentOnline;
import com.ea.eamobile.nfsmw.model.TournamentUser;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserDailyRace;
import com.ea.eamobile.nfsmw.model.UserGetSpendRewardRecord;
import com.ea.eamobile.nfsmw.model.UserSession;
import com.ea.eamobile.nfsmw.model.UserSpendActivityRecord;
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
import com.ea.eamobile.nfsmw.service.CarSlotConsumableService;
import com.ea.eamobile.nfsmw.service.CarSlotService;
import com.ea.eamobile.nfsmw.service.LeaderboardChangeRecordService;
import com.ea.eamobile.nfsmw.service.LeaderboardService;
import com.ea.eamobile.nfsmw.service.RaceModeService;
import com.ea.eamobile.nfsmw.service.SpendActivityRewardService;
import com.ea.eamobile.nfsmw.service.SpendActivityService;
import com.ea.eamobile.nfsmw.service.SpendRewardService;
import com.ea.eamobile.nfsmw.service.TournamentLeaderboardChangeRecordService;
import com.ea.eamobile.nfsmw.service.TournamentLeaderboardService;
import com.ea.eamobile.nfsmw.service.TournamentOnlineService;
import com.ea.eamobile.nfsmw.service.TournamentService;
import com.ea.eamobile.nfsmw.service.TournamentUserService;
import com.ea.eamobile.nfsmw.service.UserDailyRaceService;
import com.ea.eamobile.nfsmw.service.UserGetSpendRewardRecordService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.service.UserSessionService;
import com.ea.eamobile.nfsmw.service.UserSpendActivityRecordService;

@Service
public class TelemetryScreen extends RequestScreen {
    Logger log = LoggerFactory.getLogger("TelemetryLog");
    @Autowired
    private CarSlotService carSlotService;
    @Autowired
    private CarSlotConsumableService carSlotConsumableService;
    @Autowired
    private TournamentOnlineService tournamentOnlineService;
    @Autowired
    private TournamentUserService tournamentUserService;
    @Autowired
    private UserDailyRaceService userDailyRaceService;
    @Autowired
    private UserSessionService userSessionService;
    @Autowired
    private LeaderboardChangeRecordService leaderboardChangeRecordService;
    @Autowired
    private TournamentLeaderboardChangeRecordService tournamentLeaderboardChangeRecordService;
    @Autowired
    private LeaderboardService leaderboardService;
    @Autowired
    private TournamentLeaderboardService tournamentLeaderboardService;
    @Autowired
    private RaceModeService raceModeService;
    @Autowired
    private TournamentService tournamentService;
    @Autowired
    private UserService userService;
    @Autowired
    private SpendActivityService spendActivityService;
    @Autowired
    private UserSpendActivityRecordService userSpendActivityRecordService;
    @Autowired
    private SpendActivityRewardService spendActivityRewardService;
    @Autowired
    private SpendRewardService spendRewardService;
    @Autowired
    private UserGetSpendRewardRecordService userGetSpendRewardRecordService;

    @Override
    protected boolean handleLoginCommand(RequestCommand request, Builder responseBuilder) {
        TelemetryRecord telemetryRecord = new TelemetryRecord(null);
        telemetryRecord.setRequestName("Login");
        recordTelemetry(responseBuilder, null, telemetryRecord);
        return true;
    }

    /**
     * 填充delta信息
     * 
     * @param rec
     * @param user
     */
    private void fillSpendActivity(boolean isShowNotification, TelemetryRecord rec, User user, Builder responseBuilder) {
        User old = NfsThreadLocal.getUser();
        if (user == null || old == null) {
            return;
        }

        rec.setDeltaEnergy(user.getEnergy() - old.getEnergy());
        rec.setDeltaGold(user.getGold() - old.getGold());
        rec.setDeltaMoney(user.getMoney() - old.getMoney());
        rec.setDeltaRpNum(user.getRpNum() - old.getRpNum());
        rec.setDeltaMwNum(user.getStarNum() - old.getStarNum());
        int spendGold = old.getGold() - user.getGold();
        if (spendGold < 0) {
            return;
        }
        SpendActivity spendActivity = spendActivityService
                .getSpendActivityByTime((int) (System.currentTimeMillis() / 1000));
        if (spendActivity == null) {
            return;
        }

        UserSpendActivityRecord userSpendActivityRecord = userSpendActivityRecordService.getUserSpendActivityRecord(
                user.getId(), spendActivity.getId());
        if (userSpendActivityRecord == null) {
            userSpendActivityRecord = new UserSpendActivityRecord();
            userSpendActivityRecord.setSpendActivityId(spendActivity.getId());
            userSpendActivityRecord.setUserId(user.getId());
            userSpendActivityRecord.setSpendGoldNum(spendGold);
            userSpendActivityRecordService.insert(userSpendActivityRecord);
        } else {
            if (spendGold > 0) {
                userSpendActivityRecord.setSpendGoldNum(userSpendActivityRecord.getSpendGoldNum() + spendGold);
            }
            userSpendActivityRecordService.update(userSpendActivityRecord);
        }
        if (!isShowNotification || responseBuilder.build().hasUserInfoCommand()
                || (!responseBuilder.build().hasModifyUserInfoCommand())) {
            return;
        }
        List<SpendActivityReward> spendActivityRewardList = spendActivityRewardService
                .getSpendActivityRewardList(spendActivity.getId());
        if (spendActivityRewardList == null) {
            return;
        }
        List<SpendReward> getSpendRewardList = new ArrayList<SpendReward>();
        for (SpendActivityReward spendActivityReward : spendActivityRewardList) {
            SpendReward spendReward = spendRewardService.getSpendReward(spendActivityReward.getSpendRewardId());
            if (spendReward == null) {
                continue;
            }
            if (userSpendActivityRecord.getSpendGoldNum() >= spendReward.getGoldAmount()) {
                getSpendRewardList.add(spendReward);
            }
        }
        boolean getNewSpendReward = false;
        int addMoney = 0;
        int addGold = 0;
        int addEnergy = 0;
        for (SpendReward spendReward : getSpendRewardList) {
            UserGetSpendRewardRecord userGetSpendRewardRecord = userGetSpendRewardRecordService
                    .getUserGetSpendRewardRecordByUserIdAndRewardId(user.getId(), spendReward.getId(),
                            spendActivity.getId());
            if (userGetSpendRewardRecord != null) {
                continue;
            }
            userGetSpendRewardRecord = new UserGetSpendRewardRecord();
            userGetSpendRewardRecord.setSpendActivityId(spendActivity.getId());
            userGetSpendRewardRecord.setSpendRewardId(spendReward.getId());
            userGetSpendRewardRecord.setUserId(user.getId());
            userGetSpendRewardRecordService.insert(userGetSpendRewardRecord);
            user.setEnergy(user.getEnergy() + spendReward.getAddEnergy());
            user.setMoney(user.getMoney() + spendReward.getAddMoney());
            user.setGold(user.getGold() + spendReward.getAddGold());
            userService.updateUser(user);
            addMoney = addMoney + spendReward.getAddMoney();
            addGold = addGold + spendReward.getAddGold();
            addEnergy = addEnergy + spendReward.getAddEnergy();
            getNewSpendReward = true;
        }
        if (getNewSpendReward) {
            rec.setDeltaEnergy(user.getEnergy() - old.getEnergy());
            rec.setDeltaGold(user.getGold() - old.getGold());
            rec.setDeltaMoney(user.getMoney() - old.getMoney());
            setModifyUserAndNotification(user, responseBuilder, addMoney, addGold, addEnergy,
                    userSpendActivityRecord.getSpendGoldNum());
        }

    }

    private void setModifyUserAndNotification(User user, Builder responseBuilder, int addMoney, int addGold,
            int addEnergy, int spendGold) {
        ResponseNotificationCommand.Builder rnbuilder = ResponseNotificationCommand.newBuilder();
        ResponseCommand responseCommand = responseBuilder.build();
        rnbuilder.setContent("目前您累计花费金币" + spendGold + "恭喜您已经获得：汽油" + addEnergy + ",游戏币" + addMoney);
        rnbuilder.setDuration(5.0f);
        rnbuilder.setIconId(0);

        responseBuilder.setNotificationCommand(rnbuilder.build());
        if (!responseBuilder.hasModifyUserInfoCommand()) {
            return;
        }
        ResponseModifyUserInfoCommand userInfoCmd = responseCommand.getModifyUserInfoCommand();
        ResponseModifyUserInfoCommand.Builder ruiBuilder = ResponseModifyUserInfoCommand.newBuilder(userInfoCmd);
        UserInfo.Builder uiBuilder = UserInfo.newBuilder(userInfoCmd.getUserinfo());
        uiBuilder.setRmb(user.getGold());
        uiBuilder.setCurrency(user.getMoney());
        uiBuilder.setEnergy(user.getEnergy());
        ruiBuilder.setUserinfo(uiBuilder.build());
        responseBuilder.setModifyUserInfoCommand(ruiBuilder.build());
    }

    @Override
    protected boolean handleCommand(RequestTrackCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("Track");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRaceResultCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("RaceResult");
        telemetryRecord.setRank(cmd.getGhosts().getPosition());
        telemetryRecord.setRaceTime(cmd.getGhosts().getRaceTime());
        telemetryRecord.setAverAgeSpeed(cmd.getGhosts().getAverageSpd());
        telemetryRecord.setRaceResultStatus(cmd.getGhosts().getRaceResultState());
        telemetryRecord.setModeId(cmd.getModeId());
        telemetryRecord.setGameMode(cmd.getGameMode());
        telemetryRecord.setCarId(cmd.getGhosts().getCarID());
        for (int i = 0; i < cmd.getGhosts().getCarModIdCount(); i++) {
            CarSlotConsumable consumable = carSlotConsumableService.getCarSlotConsumableBySlotIdAndConType(cmd
                    .getGhosts().getCarModId(i), cmd.getGhosts().getCarModType(i));

            if (consumable == null) {
                continue;
            }
            if (i == 0) {
                telemetryRecord.setConsumble1Id(consumable.getSlotId() + "-" + consumable.getConType());
            }
            if (i == 1) {
                telemetryRecord.setConsumble2Id(consumable.getSlotId() + "-" + consumable.getConType());
            }
            if (i == 2) {
                telemetryRecord.setConsumble3Id(consumable.getSlotId() + "-" + consumable.getConType());
            }
        }

        if (cmd.getGameMode() == Match.EVERYDAY_RACE_MODE) {
            telemetryRecord.setDailyRaceResult(cmd.getGhosts().getSuccess() ? 1 : 0);
            UserDailyRace userDailyRace = userDailyRaceService.getUserDailyRace(user.getId());
            if (userDailyRace != null) {
                telemetryRecord.setDailyRaceDuraNum(userDailyRace.getDuraDayNum());
            }

        }
        handleLeaderboardChangeRecord(cmd, user);
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    private void handleLeaderboardChangeRecord(RequestRaceResultCommand cmd, User user) {
        user = userService.getUser(user.getId());
        if (cmd.getGameMode() != Match.TOURNAMENT_MODE && cmd.getGameMode() != Match.CAREER_MODE) {
            return;
        }
        float raceTime = cmd.getGhosts().getRaceTime();
        float averageSpeed = cmd.getGhosts().getAverageSpd();
        int modeId = cmd.getModeId();
        if (cmd.getGameMode() == Match.CAREER_MODE) {
            RaceMode raceMode = raceModeService.getModeById(modeId);
            if (raceMode == null) {
                return;
            }
            Leaderboard leaderboard = leaderboardService.getLeaderboardByModeIdAndUserId(raceMode.getModeType(),
                    user.getId());
            if (leaderboard == null) {
                return;
            }
            if (raceMode.getRankType() == Match.MODE_RANK_TYPE_BY_TIME && raceTime == leaderboard.getResult()) {
                recordLeaderboardChange(cmd, user);
            }
            if (raceMode.getRankType() == Match.MODE_RANK_TYPE_BY_AVGSPEED && averageSpeed == leaderboard.getResult()) {
                recordLeaderboardChange(cmd, user);
            }
        }
        if (cmd.getGameMode() == Match.TOURNAMENT_MODE) {
            TournamentUser tournamentUser = null;
            try {
                tournamentUser = tournamentUserService.getTournamentUserByUserIdAndTOnlineId(user.getId(), modeId);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (tournamentUser == null) {
                return;
            }
            TournamentOnline tournamentOnline = tournamentOnlineService.getTournamentOnline(modeId);
            if (tournamentOnline == null) {
                return;
            }
            Tournament tournament = tournamentService.getTournament(tournamentOnline.getTournamentId());
            if (tournament == null) {
                return;
            }
            TournamentLeaderboard tournamentLeaderboard = tournamentLeaderboardService
                    .getTournamentLeaderboardByUserId(modeId, tournamentUser.getClassId(), user.getId());
            if (tournamentLeaderboard == null) {
                return;
            }
            if (tournament.getType() == Match.MODE_RANK_TYPE_BY_TIME && raceTime == tournamentLeaderboard.getResult()) {
                recordTournamentLeaderboardChange(cmd, user, tournamentUser);
            }
            if (tournament.getType() == Match.MODE_RANK_TYPE_BY_AVGSPEED
                    && averageSpeed == tournamentLeaderboard.getResult()) {
                recordTournamentLeaderboardChange(cmd, user, tournamentUser);
            }

        }
    }

    private void recordLeaderboardChange(RequestRaceResultCommand cmd, User user) {

        LeaderboardChangeRecord leaderboardChangeRecord = new LeaderboardChangeRecord();
        leaderboardChangeRecord.setAverageSpeed(cmd.getGhosts().getAverageSpd());
        leaderboardChangeRecord.setCarId(cmd.getGhosts().getCarID());
        leaderboardChangeRecord.setModeId(cmd.getModeId());
        leaderboardChangeRecord.setRaceTime(cmd.getGhosts().getRaceTime());
        leaderboardChangeRecord.setUserId(user.getId());
        String consumble1Id = "";
        String consumble2Id = "";
        String consumble3Id = "";
        for (int i = 0; i < cmd.getGhosts().getCarModIdCount(); i++) {
            CarSlotConsumable consumable = carSlotConsumableService.getCarSlotConsumableBySlotIdAndConType(cmd
                    .getGhosts().getCarModId(i), cmd.getGhosts().getCarModType(i));

            if (consumable == null) {
                continue;
            }
            if (i == 0) {
                consumble1Id = consumable.getSlotId() + "-" + consumable.getConType();
            }
            if (i == 1) {
                consumble2Id = consumable.getSlotId() + "-" + consumable.getConType();
            }
            if (i == 2) {
                consumble3Id = consumable.getSlotId() + "-" + consumable.getConType();
            }
        }
        leaderboardChangeRecord.setFirstConsumbleId(consumble1Id);
        leaderboardChangeRecord.setSecondConsumbleId(consumble2Id);
        leaderboardChangeRecord.setThirdConsumbleId(consumble3Id);
        leaderboardChangeRecordService.insert(leaderboardChangeRecord);

    }

    private void recordTournamentLeaderboardChange(RequestRaceResultCommand cmd, User user,
            TournamentUser tournamentUser) {
        TournamentLeaderboardChangeRecord tournamentLeaderboardChangeRecord = new TournamentLeaderboardChangeRecord();
        tournamentLeaderboardChangeRecord.setAverageSpeed(cmd.getGhosts().getAverageSpd());
        tournamentLeaderboardChangeRecord.setCarId(cmd.getGhosts().getCarID());
        tournamentLeaderboardChangeRecord.setTournamentOnlineId(cmd.getModeId());
        tournamentLeaderboardChangeRecord.setRaceTime(cmd.getGhosts().getRaceTime());
        tournamentLeaderboardChangeRecord.setUserId(user.getId());
        tournamentLeaderboardChangeRecord.setGroupId(tournamentUser.getGroupId());
        tournamentLeaderboardChangeRecord.setClassId(tournamentUser.getClassId());
        String consumble1Id = "";
        String consumble2Id = "";
        String consumble3Id = "";
        for (int i = 0; i < cmd.getGhosts().getCarModIdCount(); i++) {
            CarSlotConsumable consumable = carSlotConsumableService.getCarSlotConsumableBySlotIdAndConType(cmd
                    .getGhosts().getCarModId(i), cmd.getGhosts().getCarModType(i));

            if (consumable == null) {
                continue;
            }
            if (i == 0) {
                consumble1Id = consumable.getSlotId() + "-" + consumable.getConType();
            }
            if (i == 1) {
                consumble2Id = consumable.getSlotId() + "-" + consumable.getConType();
            }
            if (i == 2) {
                consumble3Id = consumable.getSlotId() + "-" + consumable.getConType();
            }
        }
        tournamentLeaderboardChangeRecord.setFirstConsumbleId(consumble1Id);
        tournamentLeaderboardChangeRecord.setSecondConsumbleId(consumble2Id);
        tournamentLeaderboardChangeRecord.setThirdConsumbleId(consumble3Id);
        tournamentLeaderboardChangeRecordService.insert(tournamentLeaderboardChangeRecord);

    }

    @Override
    protected boolean handleCommand(RequestTournamentCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);

        telemetryRecord.setRequestName("Tournament");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRegistJaguarCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("RegistJaguar");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentRewardDetailCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("TournamentRewardDetail");

        // responseTournamentRewardCommand responseTournamentRewardCommand = responseCommand.getRewardCommand();
        if (responseBuilder.build().getRewardCommand().getIsGet()) {
            telemetryRecord.setTournamentOnlineId(cmd.getTournamentOnlineId());
            TournamentUser tournamentUser = null;
            try {
                tournamentUser = tournamentUserService.getTournamentUserByUserIdAndTOnlineId(user.getId(),
                        cmd.getTournamentOnlineId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (tournamentUser != null) {
                telemetryRecord.setGroupId(tournamentUser.getGroupId());
                telemetryRecord.setClassId(tournamentUser.getClassId());
                telemetryRecord.setTournamentRank(responseBuilder.build().getRewardCommand().getRank());
            }
            telemetryRecord.setTournamentOnlineId(cmd.getTournamentOnlineId());

        }
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentDetailCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("TournamentDetail");
        telemetryRecord.setTournamentOnlineId(cmd.getTournamentOnlineId());
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestModeInfoCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("Ghost");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestResourceCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(false, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("Resource");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentSignUpCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("TournamentSignUp");
        if (responseBuilder.build().getTournamentSignUpCommand().getResult() == 1) {
            telemetryRecord.setTournamentOnlineId(cmd.getTournamentOnlineId());
            TournamentUser tournamentUser = null;
            try {
                tournamentUser = tournamentUserService.getTournamentUserByUserIdAndTOnlineId(user.getId(),
                        cmd.getTournamentOnlineId());
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (tournamentUser != null) {
                telemetryRecord.setGroupId(tournamentUser.getGroupId());
                telemetryRecord.setClassId(tournamentUser.getClassId());
            }
        }
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRaceStartCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(false, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("RaceStart");
        telemetryRecord.setModeId(cmd.getModeId());
        telemetryRecord.setGameMode(cmd.getGameMode());
        String carId = telemetryRecord.getCarId();
        for (int i = 0; i < cmd.getCarModSlotIDCount(); i++) {
            CarSlotConsumable consumable = carSlotConsumableService.getCarSlotConsumableBySlotIdAndConType(
                    cmd.getCarModSlotID(i), cmd.getCarModType(i));

            if (consumable == null) {
                continue;
            }
            if (carId.equals("")) {
                CarSlot carSlot = carSlotService.getCarSlot(consumable.getSlotId());
                if (carSlot == null) {
                    continue;
                }
                carId = carSlot.getCarId();
            }
            if (i == 0) {
                telemetryRecord.setConsumble1Id(consumable.getSlotId() + "-" + consumable.getConType());
            }
            if (i == 1) {
                telemetryRecord.setConsumble2Id(consumable.getSlotId() + "-" + consumable.getConType());
            }
            if (i == 2) {
                telemetryRecord.setConsumble3Id(consumable.getSlotId() + "-" + consumable.getConType());
            }
        }
        telemetryRecord.setCarId(carId);
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBindingStartCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("BindingStart");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBindingResultCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("BindingResult");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBindingConfirmCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("BindingConfirm");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestGarageCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("Garage");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBuyCarCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("BuyCar");
        telemetryRecord.setCarId(cmd.getCarId());
        recordTelemetry(responseBuilder, user, telemetryRecord);

        return true;
    }

    @Override
    protected boolean handleCommand(RequestUpgradeSlotCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("UpgradeSlot");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestUseChartletCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("UseChartlet");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBuyItemCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("BuyItem");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestStoreDetailCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("StoreDetail");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestGetRewardCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("GetRward");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestModifyUserInfoCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("ModifyUserInfo");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestIapCheckCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("IapCheck");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRpLeaderboardCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("RpLeaderboard");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestProfileUserDataCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("ProfileUserData");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestProfileLikeCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("ProfileLike");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestProfileReportCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("ProfileReport");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestProfileVSCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("ProfileVS");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRecordUserRaceActionCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam

        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("RecordUserRaceAction");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestProfileNextCarCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("ProfileNextCar");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTutorialRewardCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("TutorialReward");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestGhostRecordCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("GhostRecord");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestChallengeMathInfoCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("ChallengeMatchInfo");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    private void recordTelemetry(Builder response, User user, TelemetryRecord telemetryRecord) {

        ResponseCommand responseCommand = response.build();
        if (user != null) {
            telemetryRecord.setUserId(user.getId());
        } else {
            String session = responseCommand.getHead().getSession();
            UserSession userSession = userSessionService.getSession(session);
            if (userSession != null) {
                telemetryRecord.setUserId(userSession.getUserId());

            }

        }
        if (responseCommand.hasBindingResultCommand() && (!responseCommand.hasBindingConfirmCommand())) {
            telemetryRecord.setBindingWeiBo(1);
        }
        if (responseCommand.hasBindingConfirmCommand()) {
            telemetryRecord.setBindingWeiBo(1);
        }
        telemetryRecord.setVersion(response.getHead().getVersion());
        recordTelemetry(telemetryRecord);

    }

    private void recordTelemetry(TelemetryRecord telemetryRecord) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String time = sdf.format(new Date(System.currentTimeMillis()));
        String loginfo = telemetryRecord.getAverAgeSpeed() + "\t" + telemetryRecord.getBindingWeiBo() + "\t"
                + telemetryRecord.getCarId() + "\t" + telemetryRecord.getClassId() + "\t"
                + telemetryRecord.getConsumble1Id() + "\t" + telemetryRecord.getConsumble2Id() + "\t"
                + telemetryRecord.getConsumble3Id() + "\t" + telemetryRecord.getDailyRaceDuraNum() + "\t"
                + telemetryRecord.getDailyRaceResult() + "\t" + telemetryRecord.getDeltaEnergy() + "\t"
                + telemetryRecord.getDeltaGold() + "\t" + telemetryRecord.getDeltaMoney() + "\t"
                + telemetryRecord.getDeltaMwNum() + "\t" + telemetryRecord.getDeltaRpNum() + "\t"
                + telemetryRecord.getEnergy() + "\t" + telemetryRecord.getGameMode() + "\t" + telemetryRecord.getGold()
                + "\t" + telemetryRecord.getGroupId() + "\t" + telemetryRecord.getHeadIndex() + "\t"
                + telemetryRecord.getHeadUrl() + "\t" + telemetryRecord.getModeId() + "\t" + telemetryRecord.getMwNum()
                + "\t" + telemetryRecord.getMoney() + "\t" + telemetryRecord.getNickName() + "\t"
                + telemetryRecord.getRaceTime() + "\t" + telemetryRecord.getRank() + "\t"
                + telemetryRecord.getRequestName() + "\t" + telemetryRecord.getRpNum() + "\t"
                + telemetryRecord.getTier() + "\t" + telemetryRecord.getTournamentOnlineId() + "\t"
                + telemetryRecord.getTournamentRank() + "\t" + telemetryRecord.getUserId() + "\t" + time + "\t"
                + telemetryRecord.getRaceResultStatus() + "\t" + telemetryRecord.getVersion();

        log.info(loginfo);
    }

    @Override
    protected boolean handleCommand(RequestGotchaCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("GotCha");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestSystemCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("System");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestCommand cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("CheatInfo");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentNum cmd, Builder responseBuilder, User user) {
        user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("Tournament");
        recordTelemetry(responseBuilder, user, telemetryRecord);
        return true;
    }

	@Override
	protected boolean handleCommand(RequestBindingTokenCommand cmd,
			Builder responseBuilder, User user) {
		user = userService.getUser(user.getId());
        TelemetryRecord telemetryRecord = new TelemetryRecord(user);
        // add deltaparam
        fillSpendActivity(true, telemetryRecord, user, responseBuilder);
        telemetryRecord.setRequestName("BindingToken");
        recordTelemetry(responseBuilder, user, telemetryRecord);
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
