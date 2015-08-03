package com.ea.eamobile.nfsmw.service.command.tournament;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.model.Advertise;
import com.ea.eamobile.nfsmw.model.Tournament;
import com.ea.eamobile.nfsmw.model.TournamentGroup;
import com.ea.eamobile.nfsmw.model.TournamentLeaderboard;
import com.ea.eamobile.nfsmw.model.TournamentOnline;
import com.ea.eamobile.nfsmw.model.TournamentUser;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestTournamentDetailCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseTournamentDetailCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.TournamentDetailMessage;
import com.ea.eamobile.nfsmw.service.AdvertiseService;
import com.ea.eamobile.nfsmw.service.CarLimitService;
import com.ea.eamobile.nfsmw.service.TournamentCarLimitService;
import com.ea.eamobile.nfsmw.service.TournamentGroupService;
import com.ea.eamobile.nfsmw.service.TournamentOnlineService;
import com.ea.eamobile.nfsmw.service.TournamentService;
import com.ea.eamobile.nfsmw.service.TournamentUserService;
import com.ea.eamobile.nfsmw.service.command.BaseCommandService;
import com.ea.eamobile.nfsmw.service.command.moderank.ModeRankTypeHandlerFactory;

/**
 * <pre>
 *     message ResponseTournamentDetailCommand {
 *     repeated TournamentDetailMessage tournamentDetail = 1;
 *     optional int32 selfRank = 2;
 *     optional string selfName = 3;
 *     optional float selfRaceTime = 4;
 *     optional int32 selfHeadIndex = 6;
 *     optional string selfHeadUrl = 7;
 *     required int32 tournamentSignUpPrice = 11;
 *     required string tournamentGroup = 12;
 *     required string tournamentDescription = 13;
 *     repeated string tournamentCarName = 14;
 *     repeated TournamentDetailRewardMessage detailReward = 15;
 *     required int32 ishasCar = 16; //0 - none;1 - has
 *     repeated string carIDs = 17;
 *     optional string hotRideCarId = 18;
 *     optional bool isNotConsumable = 19;
 *     optional string weiboContent = 20;
 *     optional string hint = 21;
 *     repeated Leaderboard friendLeaderboard = 22;
 *     optional string startContent = 23;
 *     optional string endContent = 24;
 *     optional string weiboShareContent = 25;
 * </pre>
 * 
 * @author ma.ruofei@ea.com
 * 
 */
@Service
public class TournamentDetailCommandService extends BaseCommandService {
    @Autowired
    private TournamentUserService tournamentUserService;

    @Autowired
    private TournamentService tourService;

    @Autowired
    private TournamentOnlineService onlineService;

    @Autowired
    private TournamentGroupService tournamentGroupService;

    @Autowired
    private CarLimitService carLimitService;

    @Autowired
    private TournamentCarLimitService tournamentCarLimitService;

    @Autowired
    private AdvertiseService advertiseService;

    @Autowired
    private ModeRankTypeHandlerFactory modeRankTypeHandlerFactory;

    public ResponseTournamentDetailCommand getResponseTournamentDetailCommand(RequestTournamentDetailCommand reqcmd,
            User user) throws SQLException {
        ResponseTournamentDetailCommand.Builder builder = ResponseTournamentDetailCommand.newBuilder();
        buildTournamentDetailCommandFields(builder, reqcmd, user);
        return builder.build();
    }

    private void buildTournamentDetailCommandFields(ResponseTournamentDetailCommand.Builder builder,
            RequestTournamentDetailCommand reqcmd, User user) throws SQLException {
        long userId = user.getId();
        int onlineId = reqcmd.getTournamentOnlineId();
        TournamentOnline online = onlineService.getTournamentOnline(onlineId);
        Tournament tournament = tourService.getTournament(online.getTournamentId());
        online.setTournament(tournament);
        TournamentUser tourUser = tournamentUserService.getTournamentUserByUserIdAndTOnlineId(userId, onlineId);
        int groupId = 0;
        if (tourUser != null) {
            groupId = tourUser.getGroupId();
        } else {
            groupId = tournamentGroupService.getGroupIdForUser(tournament.getId(), user.getLevel());
        }

        TournamentGroup group = tournamentGroupService.getTournamentGroup(groupId);

        int classId = tourUser == null ? tournamentUserService.getClassId(user, tournament.getId(), online.getId(),
                false) : tourUser.getClassId();

        int hasCar = 1;
        List<String> carIds = tournamentCarLimitService.getTournamentCarLimit(groupId);

        if (group.getIsProvide() == Const.PROVIDE_CAR) {
            builder.setHotRideCarId(group.getCarProvide());
        } else {
            hasCar = userCarService.hasLimitCar(userId, carIds) ? 1 : 0;
        }
        builder.addAllCarIDs(carIds);
        builder.setIshasCar(hasCar);
        builder.setIsNotConsumable(tournament.getNoConsumble() == 1);

        Advertise advertise = advertiseService.getAdvertise(tournament.getAdId());
        if (advertise != null) {
            builder.setHint(advertise.getHintString());
            builder.setWeiboContent(advertise.getAdviseString());
        }
        List<TournamentDetailMessage> detailMessages = new ArrayList<TournamentDetailMessage>();  
        boolean notInLb = buildTournamentDetailMessages(userId,onlineId, classId,tournament.getType(),detailMessages);
        if (notInLb && tourUser != null) {
            int selfrank = tourUser.getResult() != 0 ? modeRankTypeHandlerFactory.create(tournament.getType()).getRank(
                    onlineId, tourUser) : 9999;
            if (selfrank > detailMessages.size()) {
                TournamentDetailMessage.Builder tdmbuilder = TournamentDetailMessage.newBuilder();
                tdmbuilder.setHeadIndex(user.getHeadIndex());
                tdmbuilder.setHeadUrl(user.getHeadUrl());
                tdmbuilder.setName(user.getName());
                tdmbuilder.setRaceTime(modeRankTypeHandlerFactory.create(tournament.getType()).getResult(tourUser));
                tdmbuilder.setRank(selfrank);
                tdmbuilder.setUserId(user.getId());
                detailMessages.add(tdmbuilder.build());

            }
        }
        builder.setStartContent(online.getStartContent());
        builder.setEndContent(online.getEndContent());
        builder.setWeiboContent(online.getWeiboShareContent());
        builder.addAllDetailReward(buildTourDetailRewardMessages(groupId));
        builder.addAllFriendLeaderboard(buildFriendLeaderboardList(online, user));
        builder.setTournamentSignUpPrice(group.getFee());
        builder.setTournamentGroup(group.getName() + " " + classId);
        builder.addAllTournamentDetail(detailMessages);
        builder.setTournamentDescription(group.getMatchDescribe());
        builder.setTournamentOnlineId(onlineId);
        builder.setTournamentGroupId(groupId);
        builder.addAllTournamentCarName(carLimitService.getCarLimitListByGroupId(group.getId()));

    }

    /**
     * 这你妈就是个坑爹的leaderboard 非得起个二逼名
     * 返回自己不在lb的bool值
     * @param onlineId
     * @param classId
     * @param rankType
     * @return
     */
    public boolean buildTournamentDetailMessages(long userId, int onlineId, int classId, int rankType,
            List<TournamentDetailMessage> list) {
        boolean result = true;
        List<TournamentLeaderboard> leaderboards = tourLeaderboardService.getLeaderboard(onlineId, classId, rankType);
        if (leaderboards != null) {
            for (int i = 0; i < leaderboards.size(); i++) {
                TournamentLeaderboard leaderboard = leaderboards.get(i);
                if(leaderboard.getUserId()==userId){
                    result = false;
                }
                TournamentDetailMessage.Builder builder = TournamentDetailMessage.newBuilder();
                builder.setHeadIndex(leaderboard.getHeadIndex());
                builder.setHeadUrl(leaderboard.getHeadUrl());
                builder.setName(leaderboard.getUserName());
                builder.setRaceTime(leaderboard.getResult());
                builder.setRank(i + 1);
                builder.setUserId(leaderboard.getUserId());
                list.add(builder.build());
            }
        }
        return result;
    }

}
