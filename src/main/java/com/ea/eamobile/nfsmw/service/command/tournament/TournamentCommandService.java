package com.ea.eamobile.nfsmw.service.command.tournament;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.model.ComparatorTournamentOnline;
import com.ea.eamobile.nfsmw.model.ComparatorTournamentOnlineByEndTime;
import com.ea.eamobile.nfsmw.model.Tournament;
import com.ea.eamobile.nfsmw.model.TournamentOnline;
import com.ea.eamobile.nfsmw.model.TournamentUser;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestTournamentCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseTournamentCommand;
import com.ea.eamobile.nfsmw.service.TournamentOnlineService;
import com.ea.eamobile.nfsmw.service.TournamentUserService;
import com.ea.eamobile.nfsmw.service.command.BaseCommandService;

@Service
public class TournamentCommandService extends BaseCommandService {

    @Autowired
    private TournamentUserService tourUserService;
    @Autowired
    private TournamentOnlineService onlineService;

    public ResponseTournamentCommand getResponseTournamentCommand(RequestTournamentCommand reqcmd, User user,
            Commands.ResponseCommand.Builder responseBuilder) throws SQLException {
        ResponseTournamentCommand.Builder builder = ResponseTournamentCommand.newBuilder();
        buildTournamentCommandFields(builder, reqcmd, user, responseBuilder);
        return builder.build();
    }

    /**
     * 构建一个TourOnline的列表
     * 
     * @param builder
     * @param reqcmd
     * @param user2
     * @param responseBuilder
     * @throws SQLException
     */
    private void buildTournamentCommandFields(ResponseTournamentCommand.Builder builder,
            RequestTournamentCommand reqcmd, User user, Commands.ResponseCommand.Builder responseBuilder)
            throws SQLException {
        int type = reqcmd.getType();
        builder.setType(type);
        // 是否看过开场
        hasPrologued(user);
        // 获取online列表
        List<TournamentOnline> onlineList = getOnlineList(type, user);
        // 构建messagelist
        builder.addAllTournament(buildTourMessages(onlineList, type, user));
    }

    private List<TournamentOnline> getOnlineList(int type, User user) throws SQLException {
        List<TournamentOnline> onlineList = Collections.emptyList();
        switch (type) {
        case Const.SHOW_IN_PROGRESS_TOURNAMENT:
            onlineList = onlineService.getInProgressOnlineList();
            onlineList = filterCanShowOnlineList(onlineList, user);
            ComparatorTournamentOnline ct = new ComparatorTournamentOnline();
            Collections.sort(onlineList, ct);
            break;
        case Const.SHOW_USER_FINISH_TOURNAMENT:
            List<Integer> idList = tourUserService.getTournamentUser(user.getId());
            List<TournamentOnline> tempOnlineList = onlineService.getFinishedOnlineList(idList);
            onlineList = filterOnlineList(tempOnlineList, user.getId());
            break;
        default:
            break;
        }
        return onlineList;
    }

    private List<TournamentOnline> filterCanShowOnlineList(List<TournamentOnline> tournamentOnlines, User user) {
        List<TournamentOnline> result = new ArrayList<TournamentOnline>();
        for (TournamentOnline tournamentOnline : tournamentOnlines) {
            Tournament t = tournamentService.getTournament(tournamentOnline.getTournamentId());
            if (t == null) {
                continue;
            }
            if (user.getTier() >= t.getTierLimit()) {
                result.add(tournamentOnline);
            }
        }
        return result;
    }

    private List<TournamentOnline> filterOnlineList(List<TournamentOnline> tournamentOnlineList, long userId)
            throws SQLException {
        List<TournamentOnline> result = Collections.emptyList();
        List<TournamentOnline> getRewardList = new ArrayList<TournamentOnline>();
        List<TournamentOnline> notGetRewardList = new ArrayList<TournamentOnline>();
        for (TournamentOnline tournamentOnline : tournamentOnlineList) {
            TournamentUser tournamentUser = tourUserService.getTournamentUserByUserIdAndTOnlineId(userId,
                    tournamentOnline.getId());
            if (tournamentUser == null)
                continue;
            if (tournamentOnline.getId() == 650)
                continue;
            if (tournamentUser.getIsGetReward() == Const.NOT_HAVE_GET_REWARD) {
                notGetRewardList.add(tournamentOnline);
                if (notGetRewardList.size() == 10) {
                    break;
                }
            } else {
                getRewardList.add(tournamentOnline);
            }
        }
        ComparatorTournamentOnlineByEndTime cl = new ComparatorTournamentOnlineByEndTime();
        Collections.sort(notGetRewardList, cl);
        Collections.sort(getRewardList, cl);
        List<TournamentOnline> temp = new ArrayList<TournamentOnline>();
        temp.addAll(notGetRewardList);
        temp.addAll(getRewardList);
        if (temp != null && temp.size() > 10) {
            result = temp.subList(0, 10);
        } else {
            result = temp;
        }
        return result;
    }
}
