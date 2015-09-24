package com.ea.eamobile.nfsmw.service.command.racetype;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.RaceMode;
import com.ea.eamobile.nfsmw.model.RaceReward;
import com.ea.eamobile.nfsmw.model.Reward;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.view.BaseGhost;

@Service
public class OneVsFiveHandler extends RaceTypeBaseService implements RaceTypeHandler {

    @Override
    public List<Reward> getRewards(User user, RaceMode mode, int gameMode) {

        int position = 3; // 显示前三名的奖励
        List<RaceReward> list = raceRewardService.getRaceRewards(user.getLevel(), gameMode,
                Match.RACE_TYPE_1V5, position, 1);

        return formatRaceRewards(list);
    }

    @Override
    public List<Reward> getRewards(User user) {
        List<Reward> rewards = new ArrayList<Reward>();
        for (int i = 1; i < Match.RACE_TPYE_ZERO_RACER_NUM + 1; i++) {
            int rewardId = raceRewardService.getRewardId(user.getLevel(), Match.TOURNAMENT_MODE, Match.RACE_TYPE_1V5,
                    i, true);
            Reward reward = rewardService.getReward(rewardId);
            if (reward != null) {
                rewards.add(reward);
            }
        }
        return rewards;
    }

    @Override
    public List<? extends BaseGhost> getCareerGhosts(User user, RaceMode raceMode) {
        return userGhostPoolService.getGhosts(user, raceMode, 5);
    }

    @Override
    public List<? extends BaseGhost> getTournamentGhosts(User user, int onlineId) {
        return tourGhostPoolService.getGhosts(user, onlineId, 5);
    }

}