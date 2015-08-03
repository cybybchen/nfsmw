package com.ea.eamobile.nfsmw.service.command.racetype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.RaceReward;
import com.ea.eamobile.nfsmw.model.Reward;
import com.ea.eamobile.nfsmw.service.RaceRewardService;
import com.ea.eamobile.nfsmw.service.RewardService;
import com.ea.eamobile.nfsmw.service.ghost.TourGhostPoolService;
import com.ea.eamobile.nfsmw.service.ghost.UserGhostPoolService;

@Service
public class RaceTypeBaseService {

    @Autowired
    protected RaceRewardService raceRewardService;
    @Autowired
    protected RewardService rewardService;
    @Autowired
    protected UserGhostPoolService userGhostPoolService;
    @Autowired
    protected TourGhostPoolService tourGhostPoolService;

    public List<Reward> formatRaceRewards(List<RaceReward> rrlist) {
        List<Reward> list = Collections.emptyList();
        if (rrlist != null && rrlist.size() > 0) {
            list = new ArrayList<Reward>();
            for (RaceReward rr : rrlist) {
                Reward reward = rewardService.getReward(rr.getRewardId());
                if (reward != null) {
                    list.add(reward);
                }
            }
        }
        return list;
    }
}
