package com.ea.eamobile.nfsmw.service.command.raceresult;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.Reward;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserDailyRace;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestRaceResultCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseCommand.Builder;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseRaceResultCommand;
import com.ea.eamobile.nfsmw.service.command.BaseCommandService;
import com.ea.eamobile.nfsmw.utils.DateUtil;

@Service
public class DailyModeRaceResultHandler extends BaseCommandService implements RaceResultHandler {

    @Override
    public ResponseRaceResultCommand handle(RequestRaceResultCommand reqcmd, User user, Builder responseBuilder) {
        ResponseRaceResultCommand.Builder builder = ResponseRaceResultCommand.newBuilder();
        long userId = user.getId();
        Date nowTime = new Date();
        builder.setTrackFinishRatio(1);
        builder.setModeFinishRatio(1);
        builder.setGainMostWantedNum(0);
        UserDailyRace race = userDailyRaceService.getUserDailyRace(userId);
        int duraDays = race.getDuraDayNum();
        // 计算连续比赛天数 周循环
        long realLastMatchDate = race.getLastMatchDate();
        if (DateUtil.intervalDays(nowTime, new Date(realLastMatchDate * 1000l)) == 1) {
            duraDays += 1;
            if (duraDays > 7) {
                duraDays -= 7;
            }
        } else {
            duraDays = 1;
        }
        // 构建奖励
        int isFinish = reqcmd.getGhosts().getSuccess() ? 1 : 0;
        int nowRewardId = dailyRaceRewardService.getDailyRaceReward(user.getLevel(), duraDays, isFinish);
        Reward reward = rewardService.getReward(nowRewardId);
        if (race.getLeftTimes() > 0 && reward != null) {
            race.setLastMatchDate((int) (System.currentTimeMillis() / 1000));
            race.setLeftTimes(0);
            race.setDuraDayNum(duraDays);
            // 比赛了才更新
            userDailyRaceService.update(race);
            user = rewardService.doRewards(user, nowRewardId);
        }

        builder.setRewards(buildReward(rewardService.getReward(nowRewardId), true));
        pushService.pushDailyRaceInfoCommand(responseBuilder, user, isFinish, reqcmd.getModeId());
        pushService.pushUserInfoCommand(responseBuilder, user);
        pushService.pushPopupCommand(responseBuilder, reward, Match.EVERYDAYRACE_POPUP, "", duraDays, 0);
        return builder.build();
    }

}
