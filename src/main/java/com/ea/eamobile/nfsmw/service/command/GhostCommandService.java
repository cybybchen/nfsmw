package com.ea.eamobile.nfsmw.service.command;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.CarSlotConsumable;
import com.ea.eamobile.nfsmw.model.CareerGhost;
import com.ea.eamobile.nfsmw.model.CareerGhostMatchRecord;
import com.ea.eamobile.nfsmw.model.TournamentGhost;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands.GhostInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.RacerInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestModeInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseRacerForGhostCommand;
import com.ea.eamobile.nfsmw.service.CarSlotConsumableService;
import com.ea.eamobile.nfsmw.service.CareerGhostMatchRecordService;
import com.ea.eamobile.nfsmw.service.CareerGhostModService;
import com.ea.eamobile.nfsmw.service.RaceModeService;
import com.ea.eamobile.nfsmw.service.TournamentGhostModService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.service.command.racetype.RaceTypeHandler;
import com.ea.eamobile.nfsmw.service.command.racetype.RaceTypeHandlerFactory;
import com.ea.eamobile.nfsmw.view.BaseGhost;
import com.ea.eamobile.nfsmw.view.BaseGhostMod;
import com.ea.eamobile.nfsmw.view.CacheUser;

@Service
public class GhostCommandService {

    @Autowired
    private TournamentGhostModService tournamentGhostModService;
    @Autowired
    private RaceTypeHandlerFactory raceTypeHandlerFactory;
    @Autowired
    private RaceModeService modeService;
    @Autowired
    private UserService userService;
    @Autowired
    private CareerGhostModService ghostModService;
    @Autowired
    private CarSlotConsumableService slotConsumbleService;
    @Autowired
    private CareerGhostMatchRecordService careerGhostMatchRecordService;

    public ResponseRacerForGhostCommand getResponseGhostCommand(RequestModeInfoCommand reqcmd, User user) {
        ResponseRacerForGhostCommand.Builder builder = ResponseRacerForGhostCommand.newBuilder();
        int gameMode = reqcmd.getGameMode();
        int raceType = reqcmd.getRaceType();
        int time = (int) (System.currentTimeMillis() / 1000);
        List<? extends BaseGhost> ghosts = null;
        RaceTypeHandler handler = raceTypeHandlerFactory.create(raceType);
        if (gameMode == Match.CAREER_MODE || gameMode == Match.GOLD_MODE) {
            ghosts = handler.getCareerGhosts(user, modeService.getModeById(reqcmd.getModeId()));
        } else if (gameMode == Match.TOURNAMENT_MODE) {
            ghosts = handler.getTournamentGhosts(user, reqcmd.getModeId());// 实际上是onlineid
        }
        
        // 构建RacerInfo列表
        List<RacerInfo> racerInfos = new ArrayList<RacerInfo>();
        if (ghosts != null && ghosts.size() > 0) {
            for (BaseGhost ghost : ghosts) {
                if (ghost.getUserId() == user.getId()) {
                    continue;
                }
                RacerInfo racer = buildRacerInfo(ghost, time, user.getId());
                if (racer != null) {
                    racerInfos.add(racer);
                }
            }
        }
        builder.addAllRacers(racerInfos);
        return builder.build();
    }

    private RacerInfo buildRacerInfo(BaseGhost ghost, int time, long userId) {
        RacerInfo racerInfo = null;
        CacheUser cacheUser = userService.getCacheUser(ghost.getUserId());
        if (cacheUser != null) {
            RacerInfo.Builder racerBuilder = RacerInfo.newBuilder();
            racerBuilder.setGhost(buildGhostInfo(ghost, time, userId));
            racerBuilder.setRpLv(cacheUser.getLevel());
            racerBuilder.setHeadIndex(cacheUser.getHeadIndex());
            racerBuilder.setHeadUrl(cacheUser.getHeadUrl());
            racerBuilder.setName(cacheUser.getName());
            racerInfo = racerBuilder.build();
        }
        return racerInfo;
    }

    public GhostInfo buildGhostInfo(BaseGhost ghost, int time, long userId) {
        GhostInfo.Builder builder = GhostInfo.newBuilder();
        builder.setCarColorIndex(ghost.getCarColorIndex());
        builder.setCarID(ghost.getCarId());
        builder.setRaceEventName(ghost.getEventName());
        builder.setRaceTime(ghost.getRaceTime());
        builder.setAverageSpd(ghost.getAverageSpeed());
        builder.setPosition(1);
        builder.setSuccess(false);
        builder.setCarScore(ghost.getCarScore());
        List<? extends BaseGhostMod> mods = null;
        if (ghost instanceof CareerGhost) {
            builder.setModeId(((CareerGhost) ghost).getModeType());
            ghost.setModeId(((CareerGhost) ghost).getModeType());
            mods = ghostModService.getGhostModByGhostId(ghost.getId());
            buildGhostMod(mods, builder);
            if (mods != null) {
                recordGhostMatch(ghost, time, mods, userId);
            }
        } else if (ghost instanceof TournamentGhost) {
            mods = tournamentGhostModService.getTournamentGhostMod(ghost.getId());
            builder.setModeId(ghost.getTournamentOnlineId());
            buildGhostMod(mods, builder);
        }
        return builder.build();
    }

    private void buildGhostMod(List<? extends BaseGhostMod> ghostMods, GhostInfo.Builder ghostBuilder) {
        if (ghostMods == null) {
            return;
        }
        List<Integer> modTypeList = new ArrayList<Integer>();
        List<Float> modValueList = new ArrayList<Float>();
        List<Integer> modLevelList = new ArrayList<Integer>();
        List<Integer> modIdList = new ArrayList<Integer>();
        for (BaseGhostMod mod : ghostMods) {
            modTypeList.add(mod.getModeType());
            modValueList.add(mod.getModeValue());
            modLevelList.add(mod.getModeLevel());
            modIdList.add(mod.getModeId());
        }
        ghostBuilder.addAllCarModType(modTypeList);
        ghostBuilder.addAllCarModValue(modValueList);
        ghostBuilder.addAllCarModId(modIdList);
        ghostBuilder.addAllCarModLevel(modLevelList);

    }

    private void recordGhostMatch(BaseGhost ghost, int time, List<? extends BaseGhostMod> mods, long userId) {
        CareerGhostMatchRecord careerGhostMatchRecord = new CareerGhostMatchRecord();
        careerGhostMatchRecord.setCarColorIndex(ghost.getCarColorIndex());
        careerGhostMatchRecord.setCarId(ghost.getCarId());
        careerGhostMatchRecord.setModeId(ghost.getModeId());
        careerGhostMatchRecord.setOpponentUserId(ghost.getUserId());
        careerGhostMatchRecord.setOwnerUserId(userId);
        careerGhostMatchRecord.setRaceTime(ghost.getRaceTime());
        careerGhostMatchRecord.setTime(time);
        String firstConsumble = "";
        String secondConsumble = "";
        String thirdConsumble = "";
        for (int i = 0; i < mods.size(); i++) {
            CarSlotConsumable consumable = slotConsumbleService.getCarSlotConsumableBySlotIdAndConType(mods.get(i)
                    .getModeId(), mods.get(i).getModeType());

            if (consumable == null) {
                continue;
            }
            if (i == 0) {
                firstConsumble = consumable.getSlotId() + "-" + consumable.getConType();
            }
            if (i == 1) {
                secondConsumble = consumable.getSlotId() + "-" + consumable.getConType();
            }
            if (i == 2) {
                thirdConsumble = consumable.getSlotId() + "-" + consumable.getConType();
            }
        }
        careerGhostMatchRecord.setFirstConsumbleId(firstConsumble);
        careerGhostMatchRecord.setSecondConsumbleId(secondConsumble);
        careerGhostMatchRecord.setThirdConsumbleId(thirdConsumble);
        careerGhostMatchRecordService.insert(careerGhostMatchRecord);

    }

}
