package com.ea.eamobile.nfsmw.service.command;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.CareerBestRacetimeRecord;
import com.ea.eamobile.nfsmw.model.RaceMode;
import com.ea.eamobile.nfsmw.model.Reward;
import com.ea.eamobile.nfsmw.model.Track;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestModeInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseModeInfoCommand;
import com.ea.eamobile.nfsmw.service.CareerBestRacetimeRecordService;
import com.ea.eamobile.nfsmw.service.RaceModeService;
import com.ea.eamobile.nfsmw.service.RewardService;
import com.ea.eamobile.nfsmw.service.TrackCarTypeService;
import com.ea.eamobile.nfsmw.service.TrackService;
import com.ea.eamobile.nfsmw.service.command.racetype.RaceTypeHandler;

/**
 * 获取Mode页面信息
 * 
 * @author ma.ruofei@ea.com
 * 
 */
@Service
public class ModeInfoCommandService extends BaseCommandService {

    @Autowired
    private RaceModeService modeService;
    @Autowired
    private RewardService rewardService;
    @Autowired
    private TrackService trackService;
    @Autowired
    private TrackCarTypeService trackCarTypeService;
    @Autowired
    private CareerBestRacetimeRecordService recordService;

    Logger log = LoggerFactory.getLogger(ModeInfoCommandService.class);

    public ResponseModeInfoCommand getResponseModeInfoCommand(RequestModeInfoCommand reqcmd, User user) {
        ResponseModeInfoCommand.Builder builder = ResponseModeInfoCommand.newBuilder();
        long userId = user.getId();
        RaceMode raceMode = modeService.getModeById(reqcmd.getModeId());
        if (raceMode == null) {
            log.error("raceModeId:" + reqcmd.getModeId());
            return null;
        }
        Track track = trackService.queryTrack(Integer.parseInt(raceMode.getTrackId()));

        CareerBestRacetimeRecord record = recordService.getCareerBestRacetimeRecord(userId, raceMode.getModeType());
        if (record != null) {
            int personalRank = recordService.getRank(raceMode, record, userId);
            if (personalRank > 0) {
                builder.setPersonalRank(personalRank);
            }
            if (raceMode.getRankType() == Match.MODE_RANK_TYPE_BY_TIME) {
                builder.setPersonalBestTime(record.getRaceTime());
            } else if (raceMode.getRankType() == Match.MODE_RANK_TYPE_BY_AVGSPEED) {
                builder.setPersonalBestTime(record.getAverageSpeed());
            }
        }
        builder.setEnergyCost(raceMode.getEnergy());

        Reward reward = rewardService.getReward(raceMode.getRewardId());
        builder.setReward(buildReward(reward, true));
        builder.setCarLimitDisplayString(track.getCarLimitDisplay());

        List<String> carIds = trackCarTypeService.getCarTypesByTrack(track.getId());
        if (carIds != null) {
            builder.addAllAcceptableCarIDs(carIds);
            builder.addAllFriendLeaderboard(getFriendLeaderboardList(raceMode, user));
            RaceTypeHandler handler = raceTypeHandlerFactory.create(reqcmd.getRaceType());
            List<Reward> rewards = handler.getRewards(user, raceMode);
            builder.addAllRewards(buildRewards(rewards, raceMode));
        }

        builder.setUserId(userId);
        return builder.build();
    }

}
