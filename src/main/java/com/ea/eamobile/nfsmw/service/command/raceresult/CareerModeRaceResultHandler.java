package com.ea.eamobile.nfsmw.service.command.raceresult;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.constants.ErrorConst;
import com.ea.eamobile.nfsmw.constants.FinishRatioConst;
import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.Car;
import com.ea.eamobile.nfsmw.model.CareerBestRacetimeRecord;
import com.ea.eamobile.nfsmw.model.CareerGhost;
import com.ea.eamobile.nfsmw.model.CareerGhostMod;
import com.ea.eamobile.nfsmw.model.ModeStandardResult;
import com.ea.eamobile.nfsmw.model.RaceMode;
import com.ea.eamobile.nfsmw.model.RaceModeUnlock;
import com.ea.eamobile.nfsmw.model.Reward;
import com.ea.eamobile.nfsmw.model.Track;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserTrack;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.ErrorCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.GhostInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.Leaderboard;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestRaceResultCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseCommand.Builder;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseRaceResultCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.UnlockInfoMessage;
import com.ea.eamobile.nfsmw.service.CareerGhostModService;
import com.ea.eamobile.nfsmw.service.CareerGhostService;
import com.ea.eamobile.nfsmw.service.command.BaseCommandService;
import com.ea.eamobile.nfsmw.service.command.moderank.ModeRankTypeHandler;
import com.ea.eamobile.nfsmw.service.rank.RecordRankService;
import com.ea.eamobile.nfsmw.view.CarView;

@Service
public class CareerModeRaceResultHandler extends BaseCommandService implements RaceResultHandler {

    @Autowired
    RecordRankService rankService;
    @Autowired
    private CareerGhostService careerGhostService;
    @Autowired
    private CareerGhostModService careerGhostModService;
    Logger log = LoggerFactory.getLogger(CareerModeRaceResultHandler.class);

    @Override
    public ResponseRaceResultCommand handle(RequestRaceResultCommand reqcmd, User user, Builder responseBuilder) {
        ResponseRaceResultCommand.Builder builder = ResponseRaceResultCommand.newBuilder();
        try {
            setRaceResultCommandFields(builder, reqcmd, user, responseBuilder);
        } catch (SQLException e) {
            return null;
        }
        if (responseBuilder.hasErrorCommand()) {
            return null;
        }
        return builder.build();
    }

    private void setRaceResultCommandFields(ResponseRaceResultCommand.Builder builder, RequestRaceResultCommand reqcmd,
            User user, Commands.ResponseCommand.Builder responseBuilder) throws SQLException {
        boolean isCheatCarId = isCheatCarId(user, reqcmd);
        boolean isCheatConsumble = isCheatConsumble(user, reqcmd);
        if (isCheatCarId) {
            log.error(user.getId() + "career" + "wrong car" + reqcmd.toString());
            ErrorCommand errorCommand = buildErrorCommand(ErrorConst.WRONG_CAR_ID);
            responseBuilder.setErrorCommand(errorCommand);
            recordCheatInfo(reqcmd, ErrorConst.WRONG_CAR_ID.getMesssage(), user);
            return;
        }
        if (isCheatConsumble) {
            log.error(user.getId() + "career" + "wrong consumble" + reqcmd.toString());
        }
        String trackId = reqcmd.getTrackId();
        int modeId = reqcmd.getModeId();
        float raceTime = reqcmd.getGhosts().getRaceTime();

        RaceMode mode = modeService.getModeById(modeId);
        boolean isSpeedRun = mode != null ? mode.getRankType() == Match.MODE_RANK_TYPE_BY_AVGSPEED : false;
        Track track = trackService.queryTrack(Integer.parseInt(trackId));
        if (user == null || mode == null || track == null) {
            return;
        }
        user.setIsRaceStart(0);
        int originalRpNum = user.getRpNum();
        long userId = user.getId();
        int modeType = mode.getModeType();
        // 更新用户EOL
        boolean isBeyondYellowLine = isBeyondYellowLine(reqcmd, userId, 0);
        if (isBeyondYellowLine) {
            ModeStandardResult modeStandardResult = modeStandardResultService.getModeStandardResult(modeId, reqcmd
                    .getGhosts().getCarID());
            float changedRaceTime = changedRaceTime(modeStandardResult, reqcmd);
            raceTime = changedRaceTime;
            RequestRaceResultCommand.Builder raceResultCommandBuilder = RequestRaceResultCommand.newBuilder(reqcmd);
            GhostInfo.Builder ghosBuilder = GhostInfo.newBuilder(reqcmd.getGhosts());
            ghosBuilder.setRaceTime(changedRaceTime);
            if (isSpeedRun) {

                float speedRun = changedSpeedRun(modeStandardResult, reqcmd);
                ghosBuilder.setAverageSpd(speedRun);
            }
            raceResultCommandBuilder.setGhosts(ghosBuilder.build());
            reqcmd = raceResultCommandBuilder.build();
        }
        updateCareerUserEol(user, reqcmd);
        GhostInfo ghost = reqcmd.getGhosts();
        int rank = ghost.getPosition();
        if (reqcmd.getRaceType() == Match.RACE_TYPE_SPEED_RUN) {
            rank = reqcmd.getGhosts().getSuccess() ? 1 : 2;
        }
        boolean finish = ghost.getRaceResultState() < 3;
        if (reqcmd.getRaceType() == Match.RACE_TYPE_SPEED_RUN
                && reqcmd.getGhosts().getRaceResultState() == Match.RACE_FAILTURE) {
            finish = false;
        }
        boolean canInLeaderboard = canInLeaderboard(reqcmd, modeType, mode.getRankType());
        int raceRewardId = raceRewardService.getRewardId(user.getLevel(), reqcmd.getGameMode(), reqcmd.getRaceType(),
                reqcmd.getGhosts().getPosition(), finish);
        if (ghost.getRaceResultState() < Match.REACH_FINISH && ghost.getRaceTime() > 30) {
            // 保存ghost
            if ((user.getAccountStatus() & Const.IS_NOGHOST) == 0) {
                saveCareerGhostInfo(ghost, modeType, user);
            }
            // 更新排行榜
            if ((user.getAccountStatus() & Const.IS_NORECORD) == 0 && canInLeaderboard) {
                updateLeaderboard(user, ghost, mode);
            }
        }
        UnlockInfoMessage.Builder unlockBuilder = UnlockInfoMessage.newBuilder();
        UserTrack userTrack = userTrackService.getUserTrackByMode(userId, mode);
        int finishRatioType = getFinishRatioType(reqcmd, user);
        int modeFinishRatio = userTrackService.calcModeFinishRatio(mode, userTrack, rank, userId, finish,
                finishRatioType);
        int trackFinishRatio = userTrackService.calcTrackFinishRatio(track, userId);
        // 判断mode是否完成
        int gainMwNum = modeFinishRatio >= Const.TRACK_FINISH_RATIO ? rewardService.getMwNumByRaceMode(mode) : 0;
        int trackRewardId = 0;
        int modeRewardId = 0;
        if (gainMwNum > 0) {
            // 只有mwNum有变化时才有可能解锁
            int originalMwNum = user.getStarNum();
            int currentMwNum = user.getStarNum();
            if (userTrack.getIsFinish() == 0) {
                userTrack.setIsFinish(1);
                currentMwNum = user.getStarNum() + gainMwNum;
                userTrackService.updateUserTrack(userTrack);
                modeRewardId = mode.getRewardId();
                if (trackFinishRatio == Const.TRACK_FINISH_RATIO) {
                    trackRewardId = track.getRewardId();
                    Reward reward = rewardService.getReward(trackRewardId);
                    if (reward != null) {
                        pushService.pushPopupCommand(responseBuilder, reward, Match.TRACK_POPUP,
                                track.getDisplayString(), 0, 0);
                    }
                }
            }
            // 构建解锁车辆
            List<CarView> unlockCars = userCarService.getUnlockCarViews(user, originalMwNum, currentMwNum);
            if (unlockCars.size() > 0) {
                List<String> unlockCarIds = new ArrayList<String>();
                for (CarView cv : unlockCars) {
                    unlockCarIds.add(cv.getCarId());
                }
                unlockBuilder.addAllUnlockedCar(unlockCarIds);
                pushService.pushUserCarInfoCommand(responseBuilder, unlockCars, user.getId());

            }
        }
        // 做3个奖励 比赛奖励一定给
        user = rewardService.doRewards(user, raceRewardId, modeRewardId, trackRewardId);
        // 判断并添加解锁的mode
        unlockModes(builder, user, trackId, modeId, unlockBuilder);
        // 更新最好成绩记录
        if ((user.getAccountStatus() & Const.IS_NORECORD) == 0) {
            updateRaceTimeRecord(builder, reqcmd, raceTime, mode, user, ghost, canInLeaderboard);
        }
        // 取2个排行榜 有性能问题
        builder.addAllLeaderBoard(buildLeaderboardList(leaderboardService.getLeaderboardByMode(mode), userId));
        List<Leaderboard> friendList = getFriendLeaderboardList(mode, user);
        builder.addAllFriendLeaderboard(friendList);
        builder.addAllRpmessages(buildRpMessages(originalRpNum, user, responseBuilder));
        // builder赋值
        builder.setUnlockInfo(unlockBuilder.build());
        builder.setRewards(buildReward(rewardService.getReward(raceRewardId), false));
        builder.setGainMostWantedNum(gainMwNum);
        builder.setTrackFinishRatio(trackFinishRatio);
        builder.setModeFinishRatio(modeFinishRatio);
        // 推送更新用户
        pushService.pushUserInfoCommand(responseBuilder, user);
    }

    private void updateRaceTimeRecord(ResponseRaceResultCommand.Builder builder, RequestRaceResultCommand reqcmd,
            float raceTime, RaceMode mode, User user, GhostInfo ghost, boolean canInLeaderboard) {
        int modeType = mode.getModeType();
        long userId = user.getId();
        boolean isNewRecord = false;
        CareerBestRacetimeRecord careerBestRacetimeRecord = new CareerBestRacetimeRecord();
        careerBestRacetimeRecord.setAverageSpeed(reqcmd.getGhosts().getAverageSpd());
        careerBestRacetimeRecord.setRaceTime(raceTime);
        int tempRank = raceTimeRecordService.getRank(mode, careerBestRacetimeRecord, userId);
        if (tempRank <= Const.LEADERBOARD_NUM && (!canInLeaderboard) && tempRank != 0) {
            return;
        }
        CareerBestRacetimeRecord originalRecord = raceTimeRecordService.getCareerBestRacetimeRecord(userId, modeType);

        if (ghost.getRaceResultState() < Match.REACH_FINISH && ghost.getRaceTime() > 30) {
            // 和旧数据比较看是否是新记录
            isNewRecord = isNewCareerRecord(originalRecord, userId, mode, raceTime, reqcmd.getGhosts().getAverageSpd());
        }
        if (isNewRecord) {
            cache.delete(CacheKey.USER_FRIEND_RACE_LB + userId + "_" + mode.getId());
        }
        builder.setIsNewRecord(isNewRecord);
        int rank = 0;
        int bestRank = 0;
        float bestRaceTime = 0;
        CareerBestRacetimeRecord record = raceTimeRecordService.getCareerBestRacetimeRecord(userId, modeType);
        if (record != null) {
            int oldRank = raceTimeRecordService.getRank(mode, originalRecord, userId);
            int newRank = oldRank;
            if (ghost.getRaceResultState() < Match.REACH_FINISH) {
                newRank = raceTimeRecordService.getRank(mode, record, userId);
            }
            rank = oldRank - newRank;
            if (oldRank == Match.NO_RANK) {
                bestRank = newRank;
            } else {
                bestRank = Math.min(oldRank, newRank);
            }
            if (mode.getRankType() == Match.MODE_RANK_TYPE_BY_TIME) {
                bestRaceTime = originalRecord != null ? (Math.min(record.getRaceTime(), originalRecord.getRaceTime()))
                        : record.getRaceTime();
            } else if (mode.getRankType() == Match.MODE_RANK_TYPE_BY_AVGSPEED) {
                bestRaceTime = originalRecord != null ? (Math.max(record.getAverageSpeed(),
                        originalRecord.getAverageSpeed())) : record.getAverageSpeed();
            }
        }
        // record==null说明从来没成功跑完过并且本次也没跑完
        if (rank > 9999) {
            rank = 9999;
        }
        if (bestRank > 9999) {
            bestRank = 9999;
        }
        if (bestRank <= 50 && (user.getAccountStatus() & Const.IS_GHOSTRECORD) == 0) {
            int accountStatus = user.getAccountStatus() | Const.IS_GHOSTRECORD;
            user.setAccountStatus(accountStatus);
            userService.updateUser(user);
        }
        List<com.ea.eamobile.nfsmw.model.Leaderboard> leaderboards = leaderboardService.getLeaderboardByMode(mode);
        if (leaderboards != null && leaderboards.size() > 0)
            for (int i = 0; i < leaderboards.size(); i++) {
                com.ea.eamobile.nfsmw.model.Leaderboard leaderboard = leaderboards.get(i);
                if (leaderboard.getUserId() == user.getId()) {
                    bestRank = i + 1;
                    bestRaceTime = leaderboard.getResult();
                    break;
                }
            }
        builder.setNewRank(rank);
        builder.setBestRank(bestRank);
        builder.setPersonalBestTime(bestRaceTime);

    }

    private void updateLeaderboard(User user, GhostInfo ghost, RaceMode mode) {
        com.ea.eamobile.nfsmw.model.Leaderboard leaderboard = new com.ea.eamobile.nfsmw.model.Leaderboard();
        leaderboard.setModeType(mode.getModeType());
        ModeRankTypeHandler handler = rankTypeHandlerFactory.create(mode);
        leaderboard.setResult(handler.getGhostResult(ghost));
        leaderboard.setUserId(user.getId());
        leaderboard.setUserName(user.getName());
        leaderboard.setHeadIndex(user.getHeadIndex());
        leaderboard.setHeadUrl(user.getHeadUrl() != null ? user.getHeadUrl() : "");
        leaderboardService.updateLeaderboard(leaderboard, mode);
    }

    private void unlockModes(ResponseRaceResultCommand.Builder builder, User user, String trackId, int modeId,
            UnlockInfoMessage.Builder uimbuilder) throws SQLException {
        List<Integer> unlockModes = new ArrayList<Integer>();
        int starNum = user.getStarNum();
        int tier = user.getTier();
        long userId = user.getId();
        // 取赛道列表

        List<Track> trackList = trackService.getTracksByTier(tier);
        Map<String, Track> tempMap = new HashMap<String, Track>();
        for (Track t : trackList) {
            Track addedTrack = tempMap.get(t.getName());
            if (addedTrack != null) {
                continue;
            }
            List<UserTrack> userTracks = userTrackService.getUserTracksByTrackId(userId, t.getId());
            int finishRatio = userTrackService.calcTrackFinishRatio(t, userTracks);
            // 已完成的 并且不是最大星级的不添加
            if (finishRatio >= Const.TRACK_FINISH_RATIO && !isMaxLevelTrack(t, trackList)) {
                continue;
            }
            // 根据用户星星和考级解锁 并且没被添加的
            boolean has = userTracks != null && userTracks.size() > 0;
            if (starNum >= t.getUnlockStarNum() && tier >= t.getUnlockTier() && !has) {
                uimbuilder.setIsTrackUnlocked(true);
                break;
            }
            tempMap.put(t.getName(), t);

        }

        // 取要解锁的mode 解锁条件: 赛道的完成度（不会解锁本赛道以外的关卡）
        List<RaceModeUnlock> unlocks = raceModeUnlockService.getRaceModeUnlockListByTrackId(trackId);
        if (unlocks != null) {
            int trackFinishRatioValue = userTrackService.getFinishRatioByTrack(userId, trackId);
            Map<Integer, UserTrack> userModes = userTrackService.getUserModeMap(userId);
            for (RaceModeUnlock unlock : unlocks) {
                // 判断大于解锁完成度 且用户没添加
                if (trackFinishRatioValue >= unlock.getUnlockValue() && userModes.get(unlock.getModeId()) == null) {
                    unlockModes.add(unlock.getModeId());
                    userTrackService.save(userId, trackId, unlock.getModeId(), 0);
                    uimbuilder.setIsModeUnlocked(true);
                }
            }
        }
        if (unlockModes.size() > 0)
            builder.addAllUnlockModes(unlockModes);
    }

    private boolean isMaxLevelTrack(Track track, List<Track> tierTracks) {
        for (Track t : tierTracks) {
            if (t.getName().equals(track.getName()) && t.getStar() > track.getStar()) {
                return false;
            }
        }
        return true;
    }

    private void saveCareerGhostInfo(GhostInfo ghostInfo, int modeType, User user) {
        CareerGhost ghost = careerGhostService.getCareerGhostByUserIdAndModeType(user.getId(), modeType);
        boolean isNew = ghost == null;
        float raceTime = ghostInfo.getRaceTime();
        if (isNew) {
            ghost = new CareerGhost();
        } else if (ghostInfo.getPosition() == 1) {
            raceTime = (float) (raceTime * 0.3 + ghost.getRaceTime() * 0.7);
        } else {
            raceTime = (float) (raceTime * 0.4 + ghost.getRaceTime() * 0.6);
        }
        BigDecimal racebd = new BigDecimal(raceTime);
        raceTime = racebd.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        ghost.setRaceTime(raceTime);
        ghost.setCarColorIndex(ghostInfo.getCarColorIndex());
        ghost.setCarId(ghostInfo.getCarID());
        ghost.setEventName(ghostInfo.getRaceEventName());
        ghost.setModeType(modeType);
        ghost.setUserId(user.getId());
        ghost.setAverageSpeed(ghostInfo.getAverageSpd());
        ghost.setCarScore(ghostInfo.getCarScore());
        int isGoldCar = 0;
        Car car = carService.getCar(ghostInfo.getCarID());
        if (car != null && car.getPriceType() == Const.GOLD) {
            isGoldCar = 1;
        }
        ghost.setIsGoldCar(isGoldCar);
        int isHasConsumble = 0;
        int count = ghostInfo.getCarModTypeCount();
        for (int i = 0; i < count; i++) {
            if (ghostInfo.getCarModValue(i) > 0) {
                isHasConsumble = 1;
            }
        }
        ghost.setIsHasConsumble(isHasConsumble);
        if (isNew) {
            careerGhostService.insert(ghost);
        } else {
            careerGhostService.update(ghost);
        }
        List<CareerGhostMod> mods = new ArrayList<CareerGhostMod>();

        if (count > 0) {
            for (int i = 0; i < count; i++) {
                CareerGhostMod gm = new CareerGhostMod();
                gm.setGhostId(ghost.getId());
                gm.setModeType(ghostInfo.getCarModType(i));
                gm.setModeValue(ghostInfo.getCarModValue(i));
                gm.setModeLevel(ghostInfo.getCarModLevel(i));
                gm.setModeId(ghostInfo.getCarModId(i));
                mods.add(gm);
            }

        }
        // 更新ghost mod
        careerGhostModService.deleteById(ghost.getId());
        careerGhostModService.insertBatch(mods);
    }

    private boolean isNewCareerRecord(CareerBestRacetimeRecord record, long userId, RaceMode mode, float raceTime,
            float speed) {
        rankService.update(mode, record, raceTime, speed);

        if (record == null) {
            record = new CareerBestRacetimeRecord();
            record.setModeType(mode.getModeType());
            record.setRaceTime(raceTime);
            record.setAverageSpeed(speed);
            record.setUserId(userId);
            raceTimeRecordService.insert(record);
            return true;
        }
        ModeRankTypeHandler handler = rankTypeHandlerFactory.create(mode);
        if (handler.isFaster(raceTime, speed, record)) {
            // 避免入参被改变
            CareerBestRacetimeRecord clone = record.clone();
            clone.setRaceTime(raceTime);
            clone.setAverageSpeed(speed);
            raceTimeRecordService.update(clone);

            return true;
        }
        return false;
    }

    private int getFinishRatioType(RequestRaceResultCommand reqcmd, User user) {
        int result = FinishRatioConst.FINISH_RATIO_NO_ADDITION;
        String carId = reqcmd.getGhosts().getCarID();
        Car car = carService.getCar(carId);
        if (car != null && car.getPriceType() == Const.GOLD_TYPE) {
            result = FinishRatioConst.FINISH_RATIO_GOLD_CAR_TYPE;

        }
        return result;
    }

}
