package com.ea.eamobile.nfsmw.service.command.tournament;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.constants.ErrorConst;
import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.ModeStandardResult;
import com.ea.eamobile.nfsmw.model.Tournament;
import com.ea.eamobile.nfsmw.model.TournamentGhost;
import com.ea.eamobile.nfsmw.model.TournamentGhostMod;
import com.ea.eamobile.nfsmw.model.TournamentGroup;
import com.ea.eamobile.nfsmw.model.TournamentLeaderboard;
import com.ea.eamobile.nfsmw.model.TournamentOnline;
import com.ea.eamobile.nfsmw.model.TournamentUser;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.ErrorCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.GhostInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.Leaderboard;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestRaceResultCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseTournamentRaceResultCommand;
import com.ea.eamobile.nfsmw.service.RaceRewardService;
import com.ea.eamobile.nfsmw.service.RewardService;
import com.ea.eamobile.nfsmw.service.TournamentGhostModService;
import com.ea.eamobile.nfsmw.service.TournamentGhostService;
import com.ea.eamobile.nfsmw.service.TournamentGroupService;
import com.ea.eamobile.nfsmw.service.TournamentLeaderboardService;
import com.ea.eamobile.nfsmw.service.TournamentOnlineService;
import com.ea.eamobile.nfsmw.service.TournamentService;
import com.ea.eamobile.nfsmw.service.TournamentUserService;
import com.ea.eamobile.nfsmw.service.command.BaseCommandService;
import com.ea.eamobile.nfsmw.service.command.PushCommandService;
import com.ea.eamobile.nfsmw.service.command.moderank.ModeRankTypeHandler;
import com.ea.eamobile.nfsmw.service.command.moderank.ModeRankTypeHandlerFactory;
import com.ea.eamobile.nfsmw.service.rank.TourRecordRankService;

@Service
public class TournamentRaceResultCommandService extends BaseCommandService {
    Logger log = LoggerFactory.getLogger(TournamentRaceResultCommandService.class);
    @Autowired
    private TournamentGhostService tourGhostService;
    @Autowired
    private TournamentUserService tourUserService;
    @Autowired
    private TournamentGhostModService tourGhostModService;
    @Autowired
    private TournamentOnlineService onlineService;
    @Autowired
    private TournamentService tournamentService;
    @Autowired
    private TournamentLeaderboardService tourLeaderboardService;
    @Autowired
    private PushCommandService pushService;
    @Autowired
    private RaceRewardService raceRewardService;
    @Autowired
    private RewardService rewardService;
    @Autowired
    private ModeRankTypeHandlerFactory rankTypeHandlerFactory;
    @Autowired
    private TourRecordRankService rankService;
    @Autowired
    private TournamentGroupService tournamentGroupService;

    /**
     * 获取联赛比赛结果Command
     * 
     * @param reqcmd
     * @param user
     * @param responseBuilder
     * @return
     * @throws SQLException
     */
    public ResponseTournamentRaceResultCommand getResponseTournamentRaceResultCommand(RequestRaceResultCommand reqcmd,
            User user, Commands.ResponseCommand.Builder responseBuilder) throws SQLException {
        ResponseTournamentRaceResultCommand.Builder builder = ResponseTournamentRaceResultCommand.newBuilder();
        setTournamentRaceResultCommandFields(builder, reqcmd, user, responseBuilder);
        if (responseBuilder.hasErrorCommand()) {
            return null;
        }
        return builder.build();
    }

    private void setTournamentRaceResultCommandFields(ResponseTournamentRaceResultCommand.Builder builder,
            RequestRaceResultCommand reqcmd, User user, Commands.ResponseCommand.Builder responseBuilder)
            throws SQLException {
        boolean isCheatCarId = isCheatCarId(user, reqcmd);
        if (isCheatCarId) {
            log.error(user.getId() + "Tournament " + "wrong car" + reqcmd.toString());
            ErrorCommand errorCommand = buildErrorCommand(ErrorConst.WRONG_CAR_ID);
            responseBuilder.setErrorCommand(errorCommand);
            recordCheatInfo(reqcmd, ErrorConst.WRONG_CAR_ID.getMesssage(), user);
            return;
        }
        boolean isCheatConsumble = isCheatConsumble(user, reqcmd);
        if (isCheatConsumble) {
            log.error(user.getId() + "Tournament " + "wrong consumble" + reqcmd.toString());
            // ErrorCommand errorCommand = buildErrorCommand(ErrorConst.WRONG_CONSUMBLE);
            // responseBuilder.setErrorCommand(errorCommand);
            // recordCheatInfo(reqcmd, ErrorConst.WRONG_CONSUMBLE.getMesssage(), user);
            // return;
        }

        long userId = user.getId();
        int onlineId = reqcmd.getModeId();
        int currentTime = (int) (System.currentTimeMillis() / 1000);
        boolean isTournamentFinish = false;
        user.setIsRaceStart(0);
        TournamentUser tourUser = tourUserService.getTournamentUserByUserIdAndTOnlineId(userId, reqcmd.getModeId());
        TournamentOnline online = onlineService.getTournamentOnline(onlineId);
        Tournament tournament = tournamentService.getTournament(online.getTournamentId());
        boolean isBeyondYellowLine = isBeyondYellowLine(reqcmd, userId, tourUser.getGroupId());
        if (isBeyondYellowLine) {
            TournamentGroup tournamentGroup = tournamentGroupService.getTournamentGroup(tourUser.getGroupId());
            ModeStandardResult modeStandardResult = modeStandardResultService.getModeStandardResult(
                    tournamentGroup.getModeId(), reqcmd.getGhosts().getCarID());
            float changedRaceTime = changedRaceTime(modeStandardResult, reqcmd);
            RequestRaceResultCommand.Builder raceResultCommandBuilder = RequestRaceResultCommand.newBuilder(reqcmd);
            GhostInfo.Builder ghosBuilder = GhostInfo.newBuilder(reqcmd.getGhosts());
            ghosBuilder.setRaceTime(changedRaceTime);
            raceResultCommandBuilder.setGhosts(ghosBuilder.build());
            reqcmd = raceResultCommandBuilder.build();
        }
        if ((currentTime) > online.getEndTime()) {
            isTournamentFinish = true;
        }
        online.setTournament(tournament);

        int originalRpNum = user.getRpNum();
        // 更新EOL User改变
        updateCareerUserEol(user, reqcmd);
        if (reqcmd.getGhosts().getRaceResultState() < Match.REACH_FINISH && !isTournamentFinish
                && reqcmd.getGhosts().getRaceTime() > 30) {
            // 保存ghost信息
            if ((user.getAccountStatus() & Const.IS_NOGHOST) == 0) {
                saveTournamentGhostInfo(reqcmd.getGhosts(), onlineId, user, tourUser);
            }
            // 更新排行榜
            if ((user.getAccountStatus() & Const.IS_NORECORD) == 0) {
                updateLeaderboard(user, tourUser, reqcmd.getGhosts(), online);
            }
        }
        boolean finish = reqcmd.getGhosts().getRaceResultState() < Match.REACH_FINISH;
        if (reqcmd.getRaceType() == Match.RACE_TYPE_SPEED_RUN
                && reqcmd.getGhosts().getRaceResultState() == Match.RACE_FAILTURE && !isTournamentFinish) {

            finish = false;

        }

        // 取得比赛后奖励
        int raceRewardId = -1;
        if (reqcmd.getGhosts().getRaceResultState() < Match.REACH_FINISH && !isTournamentFinish) {
            raceRewardId = raceRewardService.getRewardId(user.getLevel(), reqcmd.getGameMode(), reqcmd.getRaceType(),
                    reqcmd.getGhosts().getPosition(), finish);
        }
        user = rewardService.doRewards(user, raceRewardId);
        // 更新tourUser的成绩记录
        boolean isNewRecord = false;
        float raceTime = 0;
        float speed = 0;
        if (reqcmd.getGhosts().getRaceResultState() < Match.REACH_FINISH && !isTournamentFinish) {
            raceTime = reqcmd.getGhosts().getRaceTime();
            speed = reqcmd.getGhosts().getAverageSpd();
        }
        boolean isFirstRaceUser = tourUser.getResult() == 0;
        ModeRankTypeHandler handler = rankTypeHandlerFactory.create(tournament.getType());
        if ((isFirstRaceUser || handler.isFaster(raceTime, speed, tourUser.getResult(), tourUser.getAverageSpeed()))
                && reqcmd.getGhosts().getRaceResultState() < Match.REACH_FINISH && !isTournamentFinish
                && (user.getAccountStatus() & Const.IS_NORECORD) == 0) {
            isNewRecord = true;
        }
        TournamentUser oldUser = tourUser.clone();// 获取一个未更新的tourUser
        if (isNewRecord) {
            tourUser.setResult(raceTime);
            tourUser.setAverageSpeed(speed);
            // update rank
            if (isFirstRaceUser) {
                rankService.addRank(tourUser, tournament.getType());
            } else {
                rankService.update(oldUser, raceTime, speed);
            }
            tourUserService.update(tourUser);
            cache.delete(CacheKey.USER_FRIEND_TOUR_LB + tourUser.getUserId() + "_" + tourUser.getTournamentOnlineId());
        }
        builder.setIsNewRecode(isNewRecord);
        int currentRank = handler.getRank(onlineId, tourUser);// 总是要返回当前排名
        if (currentRank == Match.NO_RANK) {
            builder.setUpRank(0);
            builder.setSelfName("");
            builder.setSelfRank(0);
        } else {
            builder.setUpRank(isFirstRaceUser ? 0 : handler.getRank(onlineId, oldUser) - currentRank);
            builder.setSelfRank(currentRank);
            builder.setSelfName(tourUser.getName());

        }
        builder.setTournamentOnlineId(onlineId);
        builder.setSelfRaceResult(handler.getResult(tourUser));

        // fuck you 9999
        // 取排行榜
        List<Leaderboard> leaderboardList = buildLeaderboardList(
                tourLeaderboardService.getLeaderboard(onlineId, tourUser.getClassId(), tournament.getType()), userId);
        builder.addAllBoard(leaderboardList);
        builder.addAllFriendLeaderboard(buildFriendLeaderboardList(online, user));

        builder.setRewards(buildReward(rewardService.getReward(raceRewardId), false));

        builder.setTournamentRemainTime(online.getEndTime() - currentTime);
        builder.setTournamentLeftTimes(tourUser.getLefttimes());
        builder.addAllRpmessages(buildRpMessages(originalRpNum, user, responseBuilder));
        builder.setIsTournamentOnlineFinish(isTournamentFinish);
        pushService.pushUserInfoCommand(responseBuilder, user);
        pushService.pushTournamentCommand(responseBuilder, userId);// TODO fuck
        pushService.pushTournamentDetailCommand(responseBuilder, userId, onlineId, tournament.getType());// TODO fuck
    }

    private void updateLeaderboard(User user, TournamentUser tourUser, GhostInfo ghost, TournamentOnline online) {
        TournamentLeaderboard leaderboard = new TournamentLeaderboard();
        leaderboard.setClassId(tourUser.getClassId());
        leaderboard.setRaceTime(ghost.getRaceTime());
        leaderboard.setTournamentOnlineId(online.getId());
        leaderboard.setUserId(tourUser.getUserId());
        leaderboard.setAverageSpeed(ghost.getAverageSpd());
        ModeRankTypeHandler handler = rankTypeHandlerFactory.create(online.getTournament().getType());
        leaderboard.setResult(handler.getGhostResult(ghost));
        leaderboard.setUserName(user.getName());
        leaderboard.setHeadIndex(user.getHeadIndex());
        leaderboard.setHeadUrl(user.getHeadUrl() != null ? user.getHeadUrl() : "");
        tourLeaderboardService.updateLeaderboard(leaderboard, online);
    }

    private void saveTournamentGhostInfo(GhostInfo ghostInfo, int onlineId, User user, TournamentUser tourUser) {
        TournamentGhost ghost = tourGhostService.getTournamentGhost(onlineId, user.getId());
        boolean isNew = ghost == null;
        float raceTime = ghostInfo.getRaceTime();
        if (isNew) {
            ghost = new TournamentGhost();
        } else {
            raceTime = Math.min(raceTime, ghost.getRaceTime());
        }
        ghost.setCarColorIndex(ghostInfo.getCarColorIndex());
        ghost.setCarId(ghostInfo.getCarID());
        ghost.setEventName(ghostInfo.getRaceEventName());
        ghost.setTournamentOnlineId(onlineId);
        ghost.setRaceTime(raceTime);
        ghost.setUserId(user.getId());
        ghost.setEol(user.getEol());
        ghost.setAverageSpeed(ghostInfo.getAverageSpd());
        ghost.setCarScore(ghostInfo.getCarScore());
        if (tourUser != null) {
            ghost.setClassId(tourUser.getClassId());
        }
        if (isNew) {
            tourGhostService.insert(ghost);
        } else {
            tourGhostService.update(ghost);
        }
        // 更新ghost mod
        tourGhostModService.deleteById(ghost.getId());
        int count = ghostInfo.getCarModTypeCount();
        if (count > 0) {
            List<TournamentGhostMod> modList = new ArrayList<TournamentGhostMod>();
            for (int i = 0; i < count; i++) {
                TournamentGhostMod mod = new TournamentGhostMod();
                mod.setTournamentGhostId(ghost.getId());
                mod.setModeType(ghostInfo.getCarModType(i));
                mod.setModeValue(ghostInfo.getCarModValue(i));
                mod.setModeId(ghostInfo.getCarModId(i));
                mod.setModeLevel(ghostInfo.getCarModLevel(i));
                modList.add(mod);
            }
            tourGhostModService.insertBatch(modList);
        }
    }

}
