package com.ea.eamobile.nfsmw.service.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.CarSlotConsumable;
import com.ea.eamobile.nfsmw.model.Merchandise;
import com.ea.eamobile.nfsmw.model.RaceMode;
import com.ea.eamobile.nfsmw.model.RaceStartRecord;
import com.ea.eamobile.nfsmw.model.TierMode;
import com.ea.eamobile.nfsmw.model.TournamentGroup;
import com.ea.eamobile.nfsmw.model.TournamentUser;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestRaceStartCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseRaceStartCommand;
import com.ea.eamobile.nfsmw.service.CarSlotConsumableService;
import com.ea.eamobile.nfsmw.service.CareerBestRacetimeRecordService;
import com.ea.eamobile.nfsmw.service.HintsService;
import com.ea.eamobile.nfsmw.service.PayService;
import com.ea.eamobile.nfsmw.service.RaceModeService;
import com.ea.eamobile.nfsmw.service.RaceStartRecordService;
import com.ea.eamobile.nfsmw.service.TierModeService;
import com.ea.eamobile.nfsmw.service.TournamentGroupService;
import com.ea.eamobile.nfsmw.service.TournamentUserService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.view.ResultInfo;

@Service
public class RaceStartCommandService {

    private static final Logger logger = LoggerFactory.getLogger(RaceStartCommandService.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RaceModeService raceModeService;
    @Autowired
    private TierModeService tierModeService;
    @Autowired
    private TournamentGroupService tournamentGroupService;
    @Autowired
    private TournamentUserService tournamentUserService;
    @Autowired
    private PushCommandService pushService;
    @Autowired
    private CarSlotConsumableService carSlotConsumbleService;
    @Autowired
    private PayService payService;
    @Autowired
    private HintsService hintsService;
    @Autowired
    private RaceStartRecordService raceStartRecordService;
    @Autowired
    private CareerBestRacetimeRecordService careerBestRacetimeRecordService;

    private static final int NOT_ENOUGH_ENERGY = 1;
    private static final int NOT_ENOUGH_MONEY = 2;

    public ResponseRaceStartCommand getResponseRaceStartCommand(RequestRaceStartCommand reqcmd, User user,
            Commands.ResponseCommand.Builder responseBuilder) throws SQLException {
        ResponseRaceStartCommand.Builder builder = ResponseRaceStartCommand.newBuilder();
        user = userService.regainEnergy(user);
        builderRacetartCommandFields(builder, reqcmd, user, responseBuilder);
        ResponseRaceStartCommand responseRaceStartCommand = builder.build();
        if (reqcmd.getGameMode() == Match.TOURNAMENT_MODE && responseRaceStartCommand.getApproved()) {
            tournamentUserService.updateLeftTimes(user.getId(), reqcmd.getTournamentOnlineId());
        }
        return responseRaceStartCommand;
    }

    private void builderRacetartCommandFields(ResponseRaceStartCommand.Builder builder, RequestRaceStartCommand reqcmd,
            User user, Commands.ResponseCommand.Builder responseBuilder) throws SQLException {
        int consumeEnergy = getConsumeEnergy(reqcmd, user);
        if (consumeEnergy == -1) {
            builder.setApproved(false);
            return;
        }
        builderRaceResultBuilder(builder, consumeEnergy, user, responseBuilder, reqcmd);

    }

    private int getConsumeEnergy(RequestRaceStartCommand reqcmd, User user) throws SQLException {
        int consumeEnergy = 0;
        if (reqcmd.getGameMode() == Match.CAREER_MODE) {
            RaceMode raceMode = raceModeService.getModeById(reqcmd.getModeId());
            consumeEnergy = raceMode.getEnergy();
        } else if (reqcmd.getGameMode() == Match.TOURNAMENT_MODE) {
            TournamentGroup tournamentGroup = tournamentGroupService.getTournamentGroupByModeId(reqcmd.getModeId());
            TournamentUser tournamentUser = tournamentUserService.getTournamentUserByUserIdAndTOnlineId(user.getId(),
                    reqcmd.getTournamentOnlineId());
            if (tournamentUser == null) {
                logger.warn("Unknown TournamentUser user_id={} tournament_online_id={}", user.getId(),
                        reqcmd.getTournamentOnlineId());
                return 0;
            }
            if (tournamentUser.getLefttimes() >= 1) {
                consumeEnergy = tournamentGroup.getUseEnergy();
            } else {
                consumeEnergy = -1;
            }
        } else if (reqcmd.getGameMode() == Match.TIER_MODE) {
            TierMode tierMode = tierModeService.getTierModeByModeId(reqcmd.getModeId());
            consumeEnergy = tierMode.getEnergy();
        }
        return consumeEnergy;
    }

    private boolean builderRaceResultBuilder(ResponseRaceStartCommand.Builder builder, int consumeEnergy, User user,
            Commands.ResponseCommand.Builder responseBuilder, RequestRaceStartCommand reqcmd) {
        boolean result = false;
        String hint = "";
        if (reqcmd.getModeId() == Const.JAGUAR_MODE_ID) {
            hint = hintsService.getJaguarRandomHint();
        } else {
            hint = hintsService.getRandomHint();
        }
        builder.setHint(hint);
        builder.setApproved(false);
        int samplePeriod = getSamplePeriod(user);
        if (reqcmd.getModeId() == Const.JAGUAR_MODE_ID && reqcmd.getGameMode() == Match.TOURNAMENT_MODE) {
            samplePeriod = 500;
        }
        builder.setSamplePeriod(samplePeriod);
        if (consumeEnergy > user.getEnergy()) {
            builder.setStatus(NOT_ENOUGH_ENERGY);
            return result;
        }
        List<Merchandise> items = getConsumables(reqcmd);
        ResultInfo payResult = payService.buy(items, user);
        if (payResult.isSuccess()) {

            user = payResult.getUser();
            user.setEnergy(user.getEnergy() - consumeEnergy);
            user.setIsRaceStart(1);
            userService.updateUser(user);
            builder.setApproved(true);
            pushService.pushUserInfoCommand(responseBuilder, user);
            result = true;
            saveRaceStartInfo(user.getId(), reqcmd);
        } else {
            builder.setStatus(NOT_ENOUGH_MONEY);
        }
        return result;
    }

    private int getSamplePeriod(User user) {
        int result = 0;
        if ((user.getAccountStatus() & Const.IS_GHOSTRECORD) != 0) {
            result = 500;
        }
        return result;

    }

    private void saveRaceStartInfo(long userId, RequestRaceStartCommand cmd) {
        RaceStartRecord raceStartRecord = new RaceStartRecord();
        raceStartRecord.setUserId(userId);
        String firstConsumbleId = "";
        String secondConsumbleId = "";
        String thirdConsumbleId = "";
        for (int i = 0; i < cmd.getCarModSlotIDCount(); i++) {
            CarSlotConsumable consumable = carSlotConsumbleService.getCarSlotConsumableBySlotIdAndConType(
                    cmd.getCarModSlotID(i), cmd.getCarModType(i));

            if (consumable == null) {
                continue;
            }
            if (i == 0) {
                firstConsumbleId = consumable.getSlotId() + "-" + consumable.getConType();
            }
            if (i == 1) {
                secondConsumbleId = consumable.getSlotId() + "-" + consumable.getConType();
            }
            if (i == 2) {
                thirdConsumbleId = consumable.getSlotId() + "-" + consumable.getConType();
            }
        }
        raceStartRecord.setFirstConsumebleId(firstConsumbleId);
        raceStartRecord.setSecondConsumebleId(secondConsumbleId);
        raceStartRecord.setThirdConsumebleId(thirdConsumbleId);
        raceStartRecordService.insert(raceStartRecord);
    }

    private List<Merchandise> getConsumables(RequestRaceStartCommand reqcmd) {
        List<Merchandise> items = Collections.emptyList();
        if (reqcmd.getCarModSlotIDCount() == 0) {
            return items;
        }
        items = new ArrayList<Merchandise>();
        for (int i = 0; i < reqcmd.getCarModSlotIDCount(); i++) {
            CarSlotConsumable consumable = carSlotConsumbleService.getCarSlotConsumableBySlotIdAndConType(
                    reqcmd.getCarModSlotID(i), reqcmd.getCarModType(i));
            if (consumable != null) {

                items.add(consumable);
            }
        }
        return items;
    }

}
