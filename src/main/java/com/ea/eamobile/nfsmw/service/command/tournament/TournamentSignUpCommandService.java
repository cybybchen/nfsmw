package com.ea.eamobile.nfsmw.service.command.tournament;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.model.Merchandise;
import com.ea.eamobile.nfsmw.model.Tournament;
import com.ea.eamobile.nfsmw.model.TournamentGroup;
import com.ea.eamobile.nfsmw.model.TournamentOnline;
import com.ea.eamobile.nfsmw.model.TournamentUser;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestTournamentSignUpCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseTournamentSignUpCommand;
import com.ea.eamobile.nfsmw.service.PayService;
import com.ea.eamobile.nfsmw.service.command.BaseCommandService;
import com.ea.eamobile.nfsmw.view.ResultInfo;

@Service
public class TournamentSignUpCommandService extends BaseCommandService {

    @Autowired
    private PayService payService;

    public ResponseTournamentSignUpCommand getResponseTournamentSignUpCommand(RequestTournamentSignUpCommand reqcmd,
            User user, Commands.ResponseCommand.Builder responseBuilder) throws SQLException {
        ResponseTournamentSignUpCommand.Builder builder = ResponseTournamentSignUpCommand.newBuilder();
        buildTournamentSignUpCommandFields(builder, reqcmd, user, responseBuilder);
        return builder.build();
    }

    private void buildTournamentSignUpCommandFields(ResponseTournamentSignUpCommand.Builder builder,
            RequestTournamentSignUpCommand reqcmd, User user, Commands.ResponseCommand.Builder responseBuilder) throws SQLException {
        long userId = user.getId();
        int onlineId = reqcmd.getTournamentOnlineId();
        TournamentUser tourUser = tourUserService.getTournamentUserByUserIdAndTOnlineId(userId, onlineId);
        TournamentOnline online = tournamentOnlineService.getTournamentOnline(onlineId);
        Tournament tournament = tournamentService.getTournament(online.getTournamentId());
        online.setTournament(tournament);
        int signUpResult = 0; // 定义报名结果
        if (canSignUp(tournament, tourUser, online)) {
            int groupId = tourUser != null ? tourUser.getGroupId() : tournamentGroupService.getGroupIdForUser(
                    tournament.getId(), user.getLevel());
            TournamentGroup group = tournamentGroupService.getTournamentGroup(groupId);
            // 构建报名费并消费
            Merchandise fee = buildMerchandise(group.getFee(), group.getFeeType());
            ResultInfo payResult = payService.buy(fee, userId);
            if (payResult.isSuccess()) {
                // 报名成功
                signUpResult = 1;
                user = payResult.getUser();
                if (tourUser == null) {
                    int classId = tourUserService.getClassId(user, tournament.getId(), online.getId(),true);
                    tourUser = tourUserService.buildUser(user.getName(), userId, onlineId, groupId, classId,
                            tournament.getRaceNum());
                    tourUserService.insert(tourUser);
                } else {
                    tourUser.setLefttimes(tournament.getRaceNum());
                    tourUserService.update(tourUser);
                }
                pushService.pushUserInfoCommand(responseBuilder, user);
                pushService.pushTournamentCommand(responseBuilder, userId);
            }
        }
        builder.setResult(signUpResult);
    }

    private boolean canSignUp(Tournament tournament, TournamentUser tourUser, TournamentOnline online) {
        if ((online.getEndTime() ) < System.currentTimeMillis()/1000) {
            return false;
        }
        if (tourUser == null
                || (tourUser.getLefttimes() == 0 && tournament.getScheduleType() == Const.CAN_REPEAT_SIGNUP)) {
            return true;
        }
        return false;
    }

}
