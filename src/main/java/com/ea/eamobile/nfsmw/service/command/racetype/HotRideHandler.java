package com.ea.eamobile.nfsmw.service.command.racetype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.RaceMode;
import com.ea.eamobile.nfsmw.model.RaceReward;
import com.ea.eamobile.nfsmw.model.Reward;
import com.ea.eamobile.nfsmw.model.TournamentGhost;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserGhost;

@Service
public class HotRideHandler extends RaceTypeBaseService implements RaceTypeHandler {

    @Override
    public List<Reward> getRewards(User user, RaceMode mode) {
        List<RaceReward> list = raceRewardService.getRaceRewards(user.getLevel(), Match.CAREER_MODE,
                Match.RACE_TYPE_HOT_RIDE, 1, 1);

        return formatRaceRewards(list);
    }

    @Override
    public List<Reward> getRewards(User user) {
        List<Reward> result = new ArrayList<Reward>();
        int finish = raceRewardService.getRewardId(user.getLevel(), Match.TOURNAMENT_MODE, Match.RACE_TYPE_HOT_RIDE, 0,
                true);
        int notFinish = raceRewardService.getRewardId(user.getLevel(), Match.TOURNAMENT_MODE, Match.RACE_TYPE_HOT_RIDE,
                0, false);
        Reward finishReward = rewardService.getReward(finish);
        Reward notFinishReward = rewardService.getReward(notFinish);

        if (finishReward != null) {
            result.add(finishReward);
        }
        if (notFinishReward != null) {
            result.add(notFinishReward);
        }
        return result;
    }

    @Override
    public List<UserGhost> getCareerGhosts(User user, RaceMode raceMode) {
        return Collections.emptyList();
    }

    @Override
    public List<TournamentGhost> getTournamentGhosts(User user, int onlineId) {
        return Collections.emptyList();
    }

}
