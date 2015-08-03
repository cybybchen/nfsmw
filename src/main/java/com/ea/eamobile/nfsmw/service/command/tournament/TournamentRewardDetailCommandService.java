package com.ea.eamobile.nfsmw.service.command.tournament;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.Reward;
import com.ea.eamobile.nfsmw.model.Tournament;
import com.ea.eamobile.nfsmw.model.TournamentLeaderboard;
import com.ea.eamobile.nfsmw.model.TournamentOnline;
import com.ea.eamobile.nfsmw.model.TournamentReward;
import com.ea.eamobile.nfsmw.model.TournamentUser;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestTournamentRewardDetailCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseTournamentRewardDetailCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.TournamentRewardDetailMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.responseTournamentRewardCommand;
import com.ea.eamobile.nfsmw.service.RewardService;
import com.ea.eamobile.nfsmw.service.TournamentGroupService;
import com.ea.eamobile.nfsmw.service.TournamentLeaderboardService;
import com.ea.eamobile.nfsmw.service.TournamentOnlineService;
import com.ea.eamobile.nfsmw.service.TournamentRewardService;
import com.ea.eamobile.nfsmw.service.TournamentService;
import com.ea.eamobile.nfsmw.service.TournamentUserService;
import com.ea.eamobile.nfsmw.service.command.BaseCommandService;
import com.ea.eamobile.nfsmw.service.command.PushCommandService;
import com.ea.eamobile.nfsmw.service.command.moderank.ModeRankTypeHandlerFactory;

@Service
public class TournamentRewardDetailCommandService extends BaseCommandService {

    @Autowired
    private TournamentUserService tourUserService;
    @Autowired
    private TournamentService tournamentService;
    @Autowired
    private TournamentOnlineService onlineService;
    @Autowired
    private TournamentGroupService tournamentGroupService;
    @Autowired
    private RewardService rewardService;
    @Autowired
    private TournamentRewardService tourRewardService;
    @Autowired
    private TournamentLeaderboardService tournamentLeaderboardService;
    @Autowired
    private PushCommandService pushService;
    @Autowired
    private ModeRankTypeHandlerFactory modeRankTypeHandlerFactory;

    /**
     * 
     * 
     * 比赛结束后显示的奖励
     * 
     * @param reqcmd
     * @param user
     * @param responseBuilder
     * @return
     * @throws SQLException
     */
    public responseTournamentRewardCommand getResponseTournamentRewardCommand(
            RequestTournamentRewardDetailCommand reqcmd, User user, Commands.ResponseCommand.Builder responseBuilder)
            throws SQLException {
        responseTournamentRewardCommand.Builder builder = responseTournamentRewardCommand.newBuilder();
        int onlineId = reqcmd.getTournamentOnlineId();
        long userId = user.getId();
        int rank = 0;
        TournamentOnline online = onlineService.getTournamentOnline(onlineId);
        Tournament tournament = tournamentService.getTournament(online.getTournamentId());
        TournamentUser tourUser = tourUserService.getTournamentUserByUserIdAndTOnlineIdFromMaster(userId, onlineId);
        // 注意：此方法只有在用户参加并完成了一个结束的联赛才返回 上层需要判断
        if (!onlineService.isUserFinishedOnline(online, tourUser)) {
            return null;
        }
        boolean isGot = false;
        if (tourUser != null && tourUser.getResult() != 0) {

            rank = modeRankTypeHandlerFactory.create(tournament.getType()).getRank(onlineId, tourUser);
            TournamentReward tourReward = tourRewardService.getTournamentRewardByRank(rank, tourUser.getGroupId());
            if (tourReward != null) {
                // 设置奖励显示项
                Reward reward = rewardService.getReward(tourReward.getRewardId());
                if (reward != null) {
                    builder.setRmb(String.valueOf(reward.getGold()));
                    builder.setGoldIconId(Match.RMB_ICON_ID);
                    builder.setMoney(String.valueOf(reward.getMoney()));
                    builder.setMoneyIconId(Match.MONEY_ICON_ID);
                    builder.setDisplayName(reward.getDisplayName());
                }
                // 给用户发奖励
                if (tourUser.getIsGetReward() == 0 && tourUser.getResult() > 0) {
                    isGot = true;
                    user = rewardService.updateUserReward(user, tourReward.getRewardId());
                    pushService.pushUserInfoCommand(responseBuilder, user);
                }
            }
            tourUser.setIsGetReward(1);
            tourUserService.update(tourUser);
        }

        builder.setIsGet(isGot);
        builder.setRank(rank);
        return builder.build();
    }

    /**
     * 联赛detail页面显示的奖励列表
     * 
     * @param reqcmd
     * @param user
     * @return
     * @throws SQLException
     */
    public ResponseTournamentRewardDetailCommand getResponseTournamentRewardDetailCommand(
            RequestTournamentRewardDetailCommand reqcmd, User user) throws SQLException {
        ResponseTournamentRewardDetailCommand.Builder builder = ResponseTournamentRewardDetailCommand.newBuilder();
        buildTournamentRewardDetailCommandFields(builder, reqcmd, user);
        return builder.build();
    }

    private void buildTournamentRewardDetailCommandFields(ResponseTournamentRewardDetailCommand.Builder builder,
            RequestTournamentRewardDetailCommand reqcmd, User user) throws SQLException {

        int onlineId = reqcmd.getTournamentOnlineId();
        long userId = user.getId();
        TournamentOnline online = onlineService.getTournamentOnline(onlineId);
        Tournament tournament = tournamentService.getTournament(online.getTournamentId());
        online.setTournament(tournament);
        TournamentUser tourUser = tourUserService.getTournamentUserByUserIdAndTOnlineId(userId, onlineId);

        int groupId = 0;
        int classId = 0;
        if (tourUser == null) {
            groupId = tournamentGroupService.getGroupIdForUser(tournament.getId(), user.getLevel());
            classId = tourUserService.getClassId(user, tournament.getId(), online.getId(), false);
        } else {
            groupId = tourUser.getGroupId();
            classId = tourUser.getClassId();
        }
        // TournamentGroup group = tournamentGroupService.getTournamentGroup(groupId);
        builder.setTournamentName(tournament.getName());
        builder.setInfo(online.getInfo());
        // 构建RewardDetailMessage list
        List<TournamentRewardDetailMessage> rewardDetailMessages = new ArrayList<TournamentRewardDetailMessage>();
        List<TournamentLeaderboard> leaderboards = tournamentLeaderboardService.getLeaderboard(onlineId, classId,
                tournament.getType());
        if (leaderboards != null && leaderboards.size() > 0) {
            for (int i = 0; i < leaderboards.size(); i++) {
                TournamentLeaderboard board = leaderboards.get(i);
                TournamentRewardDetailMessage.Builder detailBuilder = TournamentRewardDetailMessage.newBuilder();
                detailBuilder.setHeadIndex(board.getHeadIndex());
                detailBuilder.setHeadUrl(board.getHeadUrl());
                detailBuilder.setName(board.getUserName());
                detailBuilder.setRank(i + 1);
                detailBuilder.setRaceTime(board.getResult());
                detailBuilder.setReward(getTourRewardString(i + 1, groupId));
                rewardDetailMessages.add(detailBuilder.build());

            }
        }
        // 构建自己的信息
        if (tourUser != null && tourUser.getResult() != 0) {
            int selfrank = modeRankTypeHandlerFactory.create(tournament.getType()).getRank(onlineId, tourUser);
            builder.setSelfHeadIndex(user.getHeadIndex());
            builder.setSelfHeadUrl(user.getHeadUrl());
            builder.setSelfName(user.getName());
            builder.setSelfRank(selfrank);
            builder.setSelfRaceTime(modeRankTypeHandlerFactory.create(tournament.getType()).getResult(tourUser));
            builder.setSelfReward(getTourRewardString(selfrank, tourUser.getGroupId()));
        }
        builder.addAllRewardDetail(rewardDetailMessages);
        builder.addAllFriendLeaderboard(buildFriendLeaderboardList(online, user));
    }

    private String getTourRewardString(int rank, int groupId) {
        String rewardString = "";
        TournamentReward tourReward = tourRewardService.getTournamentRewardByRank(rank, groupId);
        if (tourReward != null) {
            Reward reward = rewardService.getReward(tourReward.getRewardId());
            if (reward != null) {
                rewardString = "Cash:" + reward.getMoney();
            }
        }
        return rewardString;
    }

}
