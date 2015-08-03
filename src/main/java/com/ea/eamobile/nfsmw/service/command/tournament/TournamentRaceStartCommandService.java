package com.ea.eamobile.nfsmw.service.command.tournament;

import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.Tournament;
import com.ea.eamobile.nfsmw.model.TournamentGroup;
import com.ea.eamobile.nfsmw.model.TournamentOnline;
import com.ea.eamobile.nfsmw.model.Track;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestRaceStartCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestTournamentDetailCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseTournamentRaceStartCommand;
import com.ea.eamobile.nfsmw.service.command.BaseCommandService;
import com.ea.eamobile.nfsmw.service.command.racetype.RaceTypeHandler;

@Service
public class TournamentRaceStartCommandService extends BaseCommandService {

    /**
     * start request
     * 
     * @param cmd
     * @param user
     * @return
     */
    public ResponseTournamentRaceStartCommand getResponseTournamentRaceStartCommand(RequestRaceStartCommand cmd,
            User user) {
        ResponseTournamentRaceStartCommand.Builder builder = ResponseTournamentRaceStartCommand.newBuilder();
        int onlineId = cmd.getTournamentOnlineId();
        int raceType = cmd.getRaceType();
        buildTournamentRaceStartCommandFields(builder, user, onlineId, raceType);
        return builder.build();
    }

    /**
     * detail request
     * 
     * @param cmd
     * @param user
     * @return
     */
    public ResponseTournamentRaceStartCommand getResponseTournamentRaceStartCommand(RequestTournamentDetailCommand cmd,
            User user) {
        ResponseTournamentRaceStartCommand.Builder builder = ResponseTournamentRaceStartCommand.newBuilder();
        int onlineId = cmd.getTournamentOnlineId();
        int raceType = cmd.getRaceType();
        buildTournamentRaceStartCommandFields(builder, user, onlineId, raceType);
        return builder.build();
    }

    private void buildTournamentRaceStartCommandFields(ResponseTournamentRaceStartCommand.Builder builder, User user,
            int onlineId, int raceType) {
        TournamentOnline online = tournamentOnlineService.getTournamentOnline(onlineId);
        Tournament tournament = tournamentService.getTournament(online.getTournamentId());
        int groupId = tournamentGroupService.getGroupIdForUser(tournament.getId(), user.getLevel());
        TournamentGroup group = tournamentGroupService.getTournamentGroup(groupId);
        Track track = trackService.queryTrack(Integer.parseInt(group.getTrackId()));
        RaceTypeHandler handler = raceTypeHandlerFactory.create(raceType);
        builder.addAllReward(buildRewards(handler.getRewards(user), null));
        builder.setTrackName(track.getName());
        builder.addAllDetailReward(buildTourDetailRewardMessages(groupId));

    }

}
