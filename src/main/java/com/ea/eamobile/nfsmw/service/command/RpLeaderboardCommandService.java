package com.ea.eamobile.nfsmw.service.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.model.ComparatorRpLeaderboard;
import com.ea.eamobile.nfsmw.model.RpLeaderBoard;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestRpLeaderboardCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseRpLeaderboardCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseRpLeaderboardCommand.Builder;
import com.ea.eamobile.nfsmw.protoc.Commands.RpLeaderboardMessage;
import com.ea.eamobile.nfsmw.service.JsonService;
import com.ea.eamobile.nfsmw.service.RpLeaderBoardService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;
import com.ea.eamobile.nfsmw.utils.DateUtil;

@Service
public class RpLeaderboardCommandService {
	private static final Logger log = LoggerFactory.getLogger(RpLeaderboardCommandService.class);
	
    @Autowired
    private UserService userService;
    @Autowired
    private RpLeaderBoardService rpLeaderBoardService;
    @Autowired
    protected JsonService jsonService;
    @Autowired
    private MemcachedClient cache;

    public ResponseRpLeaderboardCommand getResponseRpLeaderboardCommand(RequestRpLeaderboardCommand reqcmd, User user,
            Commands.ResponseCommand.Builder responseBuilder) {
        ResponseRpLeaderboardCommand.Builder builder = ResponseRpLeaderboardCommand.newBuilder();
        long userId = user.getId();
        int selfRank = 0;
        RpLeaderBoard self = buildRpLeaderboard(user);
        setSelfFields(builder, self);
        List<RpLeaderBoard> list = new ArrayList<RpLeaderBoard>();
        List<RpLeaderBoard> resultList = new ArrayList<RpLeaderBoard>();
        if (reqcmd.getType() == Const.RPLEADERBOARD_ALL) {
            list = rpLeaderBoardService.getLeaderboardList();
            for (RpLeaderBoard rpLeaderBoard : list) {
                if (rpLeaderBoard.getUserId() != user.getId()) {
                    resultList.add(rpLeaderBoard);
                }
            }
            resultList.add(self);
            list = resultList;
            if (list.size() > 50) {
                list = list.subList(0, 50);
            }
            if (user.getRpNum() == 0) {
                selfRank = 100;
            } else {
                selfRank = rpLeaderBoardService.getUserRank(user);
            }
        } else if (reqcmd.getType() == Const.RPLEADERBOARD_FRIEAND) {
            list = getFriendRpLeaderboard(user);
        }

        // 构建leaderboard message
        buildRpLeaderboardCommand(builder, list, userId, reqcmd.hasHasProfileUI());
        // 如果自己在50名开外 设置下rank
        log.debug("selfrank={}, builder selfRank={}", selfRank, builder.getSelfRank());
        if (selfRank > 0) {
            builder.setSelfRank(selfRank);
        }
        builder.setType(reqcmd.getType());
        return builder.build();
    }

    private void setSelfFields(Builder builder, RpLeaderBoard self) {
        builder.setSelfHeadIndex(self.getHeadIndex());
        builder.setSelfHeadUrl(self.getHeadUrl());
        builder.setSelfName(self.getName());
        builder.setSelfRpLevel(self.getRpLevel());
        builder.setSelfRpNum(self.getRpNum());
    }

    private RpLeaderBoard buildRpLeaderboard(User user) {
        RpLeaderBoard board = new RpLeaderBoard();
        board.setHeadIndex(user.getHeadIndex());
        board.setHeadUrl(user.getHeadUrl());
        board.setMostWantedNum(user.getStarNum());
        board.setName(user.getName());
        board.setRpLevel(user.getLevel());
        board.setRpNum(user.getRpNum());
        board.setUserId(user.getId());
        return board;
    }

    @SuppressWarnings("unchecked")
    private List<RpLeaderBoard> getFriendRpLeaderboard(User user) {
        List<RpLeaderBoard> result = (List<RpLeaderBoard>) cache.get(CacheKey.FIREND_LEADERBOARD + user.getId());
        if (result == null) {
            result = new ArrayList<RpLeaderBoard>();
            if (user.getCertType() == Const.CERT_TYPE_WEIBO) {
                List<String> tokens = jsonService.getSinaFreindUserList(user.getWillowtreeToken());
                List<User> friends = userService.getUsersByTokens(tokens.toArray(new String[tokens.size()]));
                for (User friend : friends) {
                    if (friend != null) {
                        result.add(buildRpLeaderboard(friend));
                    }
                }
                if (result.size() > 0) {
                    Collections.sort(result, new ComparatorRpLeaderboard());
                    cache.set(CacheKey.FIREND_LEADERBOARD + user.getId(), result, MemcachedClient.HOUR);
                }
            } else {
                result.add(buildRpLeaderboard(user));
            }
        }
        return result;
    }

    private void buildRpLeaderboardCommand(ResponseRpLeaderboardCommand.Builder builder,
            List<RpLeaderBoard> rpLeaderBoardList, long userId, boolean hasProfileUI) {
        List<RpLeaderboardMessage> list = new ArrayList<Commands.RpLeaderboardMessage>();
        if (rpLeaderBoardList != null && rpLeaderBoardList.size() > 0) {
            for (int i = 0; i < rpLeaderBoardList.size(); i++) {
                RpLeaderboardMessage.Builder msgBuilder = RpLeaderboardMessage.newBuilder();
                RpLeaderBoard rpLeaderBoard = rpLeaderBoardList.get(i);
                msgBuilder.setHeadIndex(rpLeaderBoard.getHeadIndex());
                msgBuilder.setHeadUrl(rpLeaderBoard.getHeadUrl());
                msgBuilder.setName(rpLeaderBoard.getName());
                msgBuilder.setRank(i + 1);
                msgBuilder.setRpLevel(rpLeaderBoard.getRpLevel());
                msgBuilder.setRpNum(rpLeaderBoard.getRpNum());
                if (hasProfileUI)
                    msgBuilder.setUserId(rpLeaderBoard.getUserId());//

                list.add(msgBuilder.build());
                if (userId == rpLeaderBoard.getUserId()) {
                    builder.setSelfRank(i + 1);
                }
            }
        }
        builder.addAllRpLeaderboard(list);

    }
}
