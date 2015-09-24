package com.ea.eamobile.nfsmw.service.command.racetype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.RaceMode;
import com.ea.eamobile.nfsmw.model.Reward;
import com.ea.eamobile.nfsmw.model.TournamentGhost;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserGhost;

@Service
public class SpeedRunHandler extends RaceTypeBaseService implements RaceTypeHandler {

    @Override
    public List<Reward> getRewards(User user, RaceMode mode, int gameMode) {
        // 显示位置是0的 完成和没完成的奖励
        int id1 = raceRewardService.getRewardId(user.getLevel(), gameMode, Match.RACE_TYPE_SPEED_RUN, 0, true);
        int id2 = raceRewardService
                .getRewardId(user.getLevel(), gameMode, Match.RACE_TYPE_SPEED_RUN, 0, false);
        Reward reward1 = rewardService.getReward(id1);
        Reward reward2 = rewardService.getReward(id2);
        List<Reward> rewards = new ArrayList<Reward>();
        if (reward1 != null) {
            rewards.add(reward1);
        }
        if (reward2 != null) {
            rewards.add(reward2);
        }
        return rewards;
    }

    @Override
    public List<Reward> getRewards(User user) {
        List<Reward> result = new ArrayList<Reward>();
        int finish = raceRewardService.getRewardId(user.getLevel(), Match.TOURNAMENT_MODE, Match.RACE_TYPE_SPEED_RUN,
                0, true);
        int notFinish = raceRewardService.getRewardId(user.getLevel(), Match.TOURNAMENT_MODE,
                Match.RACE_TYPE_SPEED_RUN, 0, false);
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
