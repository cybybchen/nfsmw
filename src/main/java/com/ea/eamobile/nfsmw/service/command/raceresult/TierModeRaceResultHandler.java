package com.ea.eamobile.nfsmw.service.command.raceresult;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.ErrorConst;
import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.Reward;
import com.ea.eamobile.nfsmw.model.TierMode;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands.ErrorCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestRaceResultCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseCommand.Builder;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseRaceResultCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.TierInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.UnlockInfoMessage;
import com.ea.eamobile.nfsmw.service.command.BaseCommandService;
import com.ea.eamobile.nfsmw.view.CarView;

@Service
public class TierModeRaceResultHandler extends BaseCommandService implements RaceResultHandler {

    @Override
    public ResponseRaceResultCommand handle(RequestRaceResultCommand reqcmd, User user, Builder responseBuilder)
            throws SQLException {
        ResponseRaceResultCommand.Builder builder = ResponseRaceResultCommand.newBuilder();
        boolean isCheatCarId = isCheatCarId(user, reqcmd);
        if (isCheatCarId) {
            ErrorCommand errorCommand = buildErrorCommand(ErrorConst.WRONG_CAR_ID);
            responseBuilder.setErrorCommand(errorCommand);
            recordCheatInfo(reqcmd, ErrorConst.WRONG_CAR_ID.getMesssage(), user);
            return builder.build();
        }
        boolean isCheatConsumble = isCheatConsumble(user, reqcmd);
        if (isCheatConsumble) {
            // ErrorCommand errorCommand = buildErrorCommand(ErrorConst.WRONG_CONSUMBLE);
            // responseBuilder.setErrorCommand(errorCommand);
            // recordCheatInfo(reqcmd, ErrorConst.WRONG_CONSUMBLE.getMesssage(), user);
            // return builder.build();
        }
        user.setIsRaceStart(0);
        int trackFinishRatio = 0;
        int modeFinishRatio = 0;
        int rewardId = 0;
        int tierStatus = 1;
        if (reqcmd.getGhosts().getSuccess()) {

            trackFinishRatio = 100;
            modeFinishRatio = 100;
            TierMode mode = tierModeModService.getTierModeByModeId(reqcmd.getModeId());
            rewardId = mode.getRewardId();
            // set tier info
            int tier = mode.getTier() + 1;
            builder.setTier(buildTierInfo(tier));
            // int originalMwNum = user.getStarNum();// 注意位置不能变化
            // update user
            Reward reward = rewardService.getReward(mode.getRewardId());
            if (reward != null) {
                user = rewardService.doRewards(user, reward.getId());
                pushService.pushPopupCommand(responseBuilder, reward, Match.TIER_POPUP, "", 0, mode.getTier());
            }
            user.setTier(tier);

            // set unlock car
            List<CarView> unlockCars = userCarService.getUnlockCarViewsByTier(user);
            if (unlockCars.size() > 0) {
                pushService.pushUserCarInfoCommand(responseBuilder, unlockCars, user.getId());
            }
            tierStatus = 3;
            UnlockInfoMessage.Builder uimbuilder = UnlockInfoMessage.newBuilder();
            uimbuilder.setTierUnlocked(tier);
            builder.setUnlockInfo(uimbuilder.build());
            pushService.pushUserInfoCommand(responseBuilder, user);
            pushService.pushDailyRaceInfoCommand(responseBuilder, user, 1, 0);
           

        }
        user.setTierStatus(tierStatus);
        userService.updateUser(user);
        builder.setRewards(buildReward(rewardService.getReward(rewardId), true));
        builder.setGainMostWantedNum(0);
        builder.setTrackFinishRatio(trackFinishRatio);
        builder.setModeFinishRatio(modeFinishRatio);

        return builder.build();
    }

    private TierInfo buildTierInfo(int tier) {
        TierInfo.Builder builder = TierInfo.newBuilder();
        builder.setTierIndex(tier);
        builder.setTierAmount(trackService.getTierCount());
        return builder.build();
    }
}
