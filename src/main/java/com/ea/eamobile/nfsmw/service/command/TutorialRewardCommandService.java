package com.ea.eamobile.nfsmw.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.model.Reward;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestTutorialRewardCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseTutorialRewardCommand;
import com.ea.eamobile.nfsmw.service.RewardService;
import com.ea.eamobile.nfsmw.service.UserService;

@Service
public class TutorialRewardCommandService {

    @Autowired
    private UserService userService;
    @Autowired
    private RewardService rewardService;
    @Autowired
    private PushCommandService pushService;

    public ResponseTutorialRewardCommand getResponseTutorialRewardCommand(RequestTutorialRewardCommand reqcmd,
            User user, Commands.ResponseCommand.Builder responseBuilder) {
        ResponseTutorialRewardCommand.Builder builder = ResponseTutorialRewardCommand.newBuilder();
        // User user = userService.getUser(userId);

        boolean finished = reqcmd.getFinishedAllTutorial();
        boolean success = false;
        if (finished) {
            if (user.getIsGetTutorialReward() == 0) {
                int rewardId = Const.TUTORIAL_REWARDID;
                Reward reward = rewardService.getReward(rewardId);
                if (reward != null) {
                    user = rewardService.doRewards(user, rewardId);
                    com.ea.eamobile.nfsmw.protoc.Commands.Reward.Builder rewardBuilder = com.ea.eamobile.nfsmw.protoc.Commands.Reward
                            .newBuilder();
                    rewardBuilder.setDisplayStrings(reward.getDisplayName());
                    rewardBuilder.setMoney(reward.getMoney());
                    rewardBuilder.setRmb(reward.getGold());
                    builder.setReward(rewardBuilder.build());
                }
                user.setIsGetTutorialReward(1);
                userService.updateUser(user);
                success = true;
                pushService.pushUserInfoCommand(responseBuilder, user);
            }
        }
        builder.setSuccess(success);
        return builder.build();
    }

}
