package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.model.RpLevel;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands.AccountInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.RPMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.UserInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.UserWeiboInfo;
import com.ea.eamobile.nfsmw.view.BaseView;
import com.ea.eamobile.nfsmw.view.UserView;

@Service
public class UserInfoMessageService {

    @Autowired
    private RpLevelService levelService;
    @Autowired
    private JsonService jsonService;

    public void buildUserInfoMessage(UserInfo.Builder builder, User user, List<AccountInfo> accounts,
            UserWeiboInfo weiboInfo) {
        builder.setHeadUrl(user.getHeadUrl());
        builder.setHeadIndex(user.getHeadIndex());
        builder.setMostwantedNum(user.getStarNum());
        builder.setTier(user.getTier());
        builder.setRpLevel(user.getLevel());
        builder.setRpExp(user.getRpNum());
        builder.setEnergy(user.getEnergy());
        builder.setNickname(user.getName());
        builder.setAccessToken(user.getAccessToken());
        builder.setUid(user.getUid());
        builder.setTutorialRewardIsGiven(user.getIsGetTutorialReward() == 1);
        builder.setIsNameChanged(user.getIsNicknameChanged());
        try {
            if (weiboInfo == null && user.getCertType() == Const.CERT_TYPE_WEIBO) {
                weiboInfo = jsonService.getWeiboTokenInfo(user.getWillowtreeToken());
            }
            if (weiboInfo != null) {
                builder.setWeiboInfo(weiboInfo);
            }
        } catch (Exception e) {

        }
        // TODO 此处取willow的钱还是 nfs还是hais的钱？
        builder.setCurrency(user.getMoney());
        builder.setRmb(user.getGold());
        RpLevel currentLevel = levelService.getLevel(user.getLevel());
        int exp = currentLevel != null ? user.getRpNum() - currentLevel.getRpNum() : user.getRpNum();
        builder.setRpExp(exp);
        RpLevel nextLevel = levelService.getLevel(user.getLevel() + 1);
        if (currentLevel != null) {
            int nextExp = nextLevel != null ? nextLevel.getRpNum() - currentLevel.getRpNum() : currentLevel.getRpNum();
            builder.setExpMax(nextExp);
        }
        builder.setFinishedFirstRace(user.getIsOldUser() == 1);
        builder.setRpMessage(buildRpMessage(user.getLevel(), user.getRpNum()));
        // add accounts info
        if (accounts != null) {
            builder.addAllAccountInfos(accounts);
        }
    }

    /**
     * 这个方法非常频繁
     * 
     * @param builder
     * @param user
     */
    public void buildUserInfoMessage(UserInfo.Builder builder, User user) {
    	buildUserInfoMessage(builder, user, null, null);
//        BaseView view = jsonService.getUser(user.getWillowtreeToken());
//        List<AccountInfo> accounts = null;
//        if (view instanceof UserView) {
//            UserView userView = (UserView) view;
//            accounts = userView.getAccounts();
//            buildUserInfoMessage(builder, user, accounts, userView.getWeiboInfo());
//        }
    }

    private RPMessage buildRpMessage(int level, int rpNum) {
        RPMessage.Builder builder = RPMessage.newBuilder();
        RpLevel current = levelService.getLevel(level);
        int maxExp = rpNum;
        RpLevel next = levelService.getLevel(current.getLevel() + 1);
        if (next != null) {
            maxExp = next.getRpNum();
        }

        builder.setPercentage(1);

        builder.setIconname(current.getIconId());
        builder.setLevel(level);
        builder.setTitle(current.getName());
        builder.setCurrentExp(rpNum);
        builder.setMinExp(current.getRpNum());
        builder.setMaxExp(maxExp);
        return builder.build();
    }
}
