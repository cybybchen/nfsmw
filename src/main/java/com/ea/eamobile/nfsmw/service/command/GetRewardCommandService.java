package com.ea.eamobile.nfsmw.service.command;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.TournamentReward;
import com.ea.eamobile.nfsmw.model.TournamentUser;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestGetRewardCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseGetRewardCommand;
import com.ea.eamobile.nfsmw.service.RewardService;
import com.ea.eamobile.nfsmw.service.TournamentRewardService;
import com.ea.eamobile.nfsmw.service.TournamentUserService;
import com.ea.eamobile.nfsmw.service.UserService;

@Service
public class GetRewardCommandService {

    @Autowired
    private UserService userService;

    @Autowired
    private RewardService rewardService;

    @Autowired
    private TournamentUserService tournamentUserService;

    @Autowired
    private TournamentRewardService tournamentRewardService;
    
    @Autowired
    private PushCommandService pushService;

    public ResponseGetRewardCommand getResponseGetRewardCommand(RequestGetRewardCommand reqcmd, User user,Commands.ResponseCommand.Builder responseBuilder) throws SQLException {
        ResponseGetRewardCommand.Builder builder = ResponseGetRewardCommand.newBuilder();
        long userId = user.getId();
        buildGetRewardCommandFields(builder, reqcmd, userId, responseBuilder);

        return builder.build();
    }

    private void buildGetRewardCommandFields(ResponseGetRewardCommand.Builder builder, RequestGetRewardCommand reqcmd,
            long userId,Commands.ResponseCommand.Builder responseBuilder) throws SQLException {
       
        User user = userService.getUser(userId);
        int tournamentOnlineId = reqcmd.getTournamentOnlineId();
        TournamentUser tournamentUser = tournamentUserService.getTournamentUserByUserIdAndTOnlineId(userId,
                tournamentOnlineId);
        if (user == null || tournamentUser == null) {
            return;
        }
        userService.regainEnergy(user);
        if (tournamentUser.getResult() > 0) {
            if (tournamentUser.getIsGetReward() == 1) {
                builder.setSuccess(0);
                builder.setErrorMessage("have been got reward");
            } else {
                TournamentReward tournamentReward = tournamentRewardService.getTournamentRewardByRank(9,tournamentUser.getGroupId());
                if (tournamentReward == null) {
                    builder.setSuccess(0);
                    builder.setErrorMessage("you didn't have reward");
                } else {
                    rewardService.updateUserReward(userId, tournamentReward.getRewardId());
                    builder.setSuccess(1);
                    tournamentUser.setIsGetReward(1);
                    tournamentUserService.update(tournamentUser);
                }

            }
        }
        
        User changedUser=userService.getUser(userId);
        pushService.pushUserInfoCommand(responseBuilder, changedUser);

    }

}
