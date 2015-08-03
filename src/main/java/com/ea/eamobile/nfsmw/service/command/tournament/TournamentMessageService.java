package com.ea.eamobile.nfsmw.service.command.tournament;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.model.Reward;
import com.ea.eamobile.nfsmw.model.Tournament;
import com.ea.eamobile.nfsmw.model.TournamentGroup;
import com.ea.eamobile.nfsmw.model.TournamentLeaderboard;
import com.ea.eamobile.nfsmw.model.TournamentOnline;
import com.ea.eamobile.nfsmw.model.TournamentReward;
import com.ea.eamobile.nfsmw.model.TournamentUser;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands.TournamentMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.TournamentMessage.Builder;
import com.ea.eamobile.nfsmw.protoc.Commands.TournamentRankingListMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.TournamentRewardListMessage;
import com.ea.eamobile.nfsmw.service.RewardService;
import com.ea.eamobile.nfsmw.service.TournamentGroupService;
import com.ea.eamobile.nfsmw.service.TournamentLeaderboardService;
import com.ea.eamobile.nfsmw.service.TournamentRewardService;
import com.ea.eamobile.nfsmw.service.TournamentService;
import com.ea.eamobile.nfsmw.service.TournamentUserService;
import com.ea.eamobile.nfsmw.service.command.moderank.ModeRankTypeHandler;
import com.ea.eamobile.nfsmw.service.command.moderank.ModeRankTypeHandlerFactory;

/**
 * 构建TourMessage protobuf bean for cmd
 * 
 * @author ma.ruofei@ea.com
 * 
 */
@Service
public class TournamentMessageService {

    @Autowired
    private TournamentUserService tourUserService;
    @Autowired
    private TournamentService tournamentService;
    @Autowired
    private TournamentGroupService tourGroupService;
    @Autowired
    private RewardService rewardService;
    @Autowired
    private TournamentRewardService tourRewardService;
    @Autowired
    private TournamentLeaderboardService tourLeaderboardService;
    @Autowired
    private ModeRankTypeHandlerFactory modeRankTypeHandlerFactory;

    /**
     * 这是一个被循环调用的方法 入参online一定是进行中的
     * 
     * @param type
     * @param user
     * @param online
     * @return
     * @throws SQLException
     */
    public TournamentMessage buildTournamentMessage(int type, User user, TournamentOnline online) throws SQLException {

        boolean isHasReward = false;
        long userId = user.getId();
        Tournament tournament = tournamentService.getTournament(online.getTournamentId());
        online.setTournament(tournament);// 不想传参而已

        if (canNotJoin(tournament, user)) {
            return null;
        }
        TournamentUser tourUser = tourUserService.getTournamentUserByUserIdAndTOnlineId(userId, online.getId());
        // 为用户选组
        int groupId = tourUser == null ? tourGroupService.getGroupIdForUser(tournament.getId(), user.getLevel())
                : tourUser.getGroupId();

        boolean canHaveReward = hasReward(tourUser, online.getId(), tournament);
        if ((tourUser != null && tourUser.getIsGetReward() == 1) || !canHaveReward) {
            isHasReward = true;
        }
        TournamentGroup group = tourGroupService.getTournamentGroup(groupId);
        TournamentMessage.Builder builder = TournamentMessage.newBuilder();
        int leftTimes = tourUser == null ? tournament.getRaceNum() : tourUser.getLefttimes();
        int signUp = tourUser == null ? Const.CAN_SIGNUP_TOURNAMENT : Const.HAS_SIGNUPED_TOURNAMENT;
        if (tourUser != null
                && (tourUser.getLefttimes() == 0 && tournament.getScheduleType() == Const.CAN_REPEAT_SIGNUP)) {
            // 如果是可重复报名的且用户已经比完了一轮
            signUp = Const.CAN_SIGNUP_TOURNAMENT;
            leftTimes = tournament.getRaceNum();
        }
        // 构建Message
        builder.setSignUp(signUp);
        builder.setLeftTimes(leftTimes);
        builder.setSignUpType(group.getFeeType());
        builder.setStartTime(online.getStartTime());
        builder.setRemainTime(online.getEndTime() - System.currentTimeMillis() / 1000);
        builder.setId(String.valueOf(tournament.getId()));
        builder.setType(tournament.getScheduleType());
        builder.setOnlineId(online.getId());
        builder.setName(tournament.getName());
        builder.setEnergy(group.getUseEnergy());
        builder.setEventId(group.getModeId());
        builder.setBackgroundPictureId(tournament.getBackgroundPictureId());
        builder.setSignUpPrice(group.getFee());
        builder.setAdId(tournament.getAdId());
        // set reward
        builder.addAllRewardList(buildRewardList(groupId));
        // set rank list
        builder.addAllRankList(buildRankList(builder, online, tourUser, user));
        builder.setIsAlreadyGetReward(isHasReward);
        return builder.build();
    }

    public boolean hasReward(TournamentUser tourUser, int onlineId, Tournament tournament) {
        if (tourUser == null) {
            return false;
        }
        int rank = modeRankTypeHandlerFactory.create(tournament.getType()).getRank(onlineId, tourUser);
        TournamentReward tourReward = tourRewardService.getTournamentRewardByRank(rank, tourUser.getGroupId());
        if (tourReward != null) {
            return true;
        }
        return false;

    }

    private boolean canNotJoin(Tournament tournament, User user) {
        return tournament.getTierLimit() > user.getTier() && tournament.getLevelLimit() > user.getLevel();
    }

    @Cacheable(value = "memory", key = "'TournamentMessageService.buildRewardList.'+#groupId")
    private List<TournamentRewardListMessage> buildRewardList(int groupId) {
        List<TournamentRewardListMessage> result = new ArrayList<TournamentRewardListMessage>();
        List<TournamentReward> rewardList = tourRewardService.getTournamentRewardListByGroupId(groupId);
        if (rewardList != null) {
            for (TournamentReward tournamentReward : rewardList) {
                Reward reward = rewardService.getReward(tournamentReward.getRewardId());
                if (reward != null) {
                    TournamentRewardListMessage.Builder builder = TournamentRewardListMessage.newBuilder();
                    builder.setNum(reward.getDisplayName());
                    builder.setName(reward.getName());
                    result.add(builder.build());
                }
            }
        }
        return result;
    }

    /**
     * 取前3名 包括自己
     * 
     * @param builder
     * 
     * @param online
     * @param tourUser
     * @param user
     * @return
     */
    private List<TournamentRankingListMessage> buildRankList(Builder builder, TournamentOnline online,
            TournamentUser tourUser, User user) {
        List<TournamentRankingListMessage> result = new ArrayList<TournamentRankingListMessage>();
        int rankType = online.getTournament().getType();
        // 如果没报名 计算一个class给用户 注意此class可能存在或不存在
        int classId = tourUser == null ? tourUserService.getClassId(user, online.getTournament().getId(),
                online.getId(), false) : tourUser.getClassId();
        List<TournamentLeaderboard> leaderboards = tourLeaderboardService.getLeaderboard(online.getId(), classId,
                rankType);
        if (leaderboards != null && leaderboards.size() > 0) {
            boolean hasSelf = false;
            for (int i = 0; i < leaderboards.size(); i++) {
                if (i >= Const.SHOW_RANK_COUNT_IN_TOURMESSAGE) {
                    break;
                }
                TournamentLeaderboard leaderboard = leaderboards.get(i);
                if (leaderboard.getUserId() == user.getId()) {
                    // 自己在前3名内 设置高亮
                    builder.setRankingHighLight(i);
                    hasSelf = true;
                }
                TournamentRankingListMessage message = buildRankingListMessage(i + 1, leaderboard.getUserName());
                result.add(message);
            }
            // 如果自己不在榜内 替换老末
            if (!hasSelf && tourUser != null && tourUser.getResult() > 0) {
                tourUser.setName(user.getName());
                replaceMySelf(result, tourUser, rankType);
            }
        }
        return result;
    }

    private TournamentRankingListMessage buildRankingListMessage(int rank, String name) {
        TournamentRankingListMessage.Builder builder = TournamentRankingListMessage.newBuilder();
        builder.setRank(String.valueOf(rank));
        builder.setName(name);
        return builder.build();
    }

    private void replaceMySelf(List<TournamentRankingListMessage> list, TournamentUser tourUser, int rankType) {
        if (list.size() >= Const.SHOW_RANK_COUNT_IN_TOURMESSAGE) {
            list.remove(list.size() - 1);
        }
        // 没自己且不足3人
        ModeRankTypeHandler handler = modeRankTypeHandlerFactory.create(rankType);
        int rank = handler.getRank(tourUser.getTournamentOnlineId(), tourUser);
        TournamentRankingListMessage.Builder builder = TournamentRankingListMessage.newBuilder();
        builder.setRank(String.valueOf(rank));
        builder.setName(tourUser.getName());
        list.add(builder.build());
    }
}
