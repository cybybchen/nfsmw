package com.ea.eamobile.nfsmw.service.command;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.Reward;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBindingConfirmCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBindingInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBindingResultCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBindingStartCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBindingTokenCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseBindingConfirmCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseBindingInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseBindingResultCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseBindingStartCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseBindingTokenCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.UserInfo;
import com.ea.eamobile.nfsmw.service.CareerBestRacetimeRecordService;
import com.ea.eamobile.nfsmw.service.JsonService;
import com.ea.eamobile.nfsmw.service.LeaderboardService;
import com.ea.eamobile.nfsmw.service.RewardService;
import com.ea.eamobile.nfsmw.service.TournamentLeaderboardService;
import com.ea.eamobile.nfsmw.service.TournamentUserService;
import com.ea.eamobile.nfsmw.service.UserInfoMessageService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;
import com.ea.eamobile.nfsmw.view.BaseView;
import com.ea.eamobile.nfsmw.view.BindingResultView;
import com.ea.eamobile.nfsmw.view.BindingUrl;

@Service
public class BindingCommandService {

    private static final Logger log = LoggerFactory.getLogger(BindingCommandService.class);

    @Autowired
    private JsonService jsonService;

    @Autowired
    private UserService userService;

    @Autowired
    private PushCommandService pushService;

    @Autowired
    private UserInfoMessageService userInfoMessageService;

    @Autowired
    private RewardService rewardService;

    @Autowired
    private LeaderboardService leaderboardService;
    @Autowired
    private CareerBestRacetimeRecordService careerBestRacetimeRecordService;
    @Autowired
    private TournamentLeaderboardService tournamentLeaderboardService;
    @Autowired
    private TournamentUserService tournamentUserService;
    @Autowired
    private MemcachedClient cache;

    /**
     * 绑定开始 返回登录url
     * 
     * @param reqcmd
     * @return
     */
    public ResponseBindingStartCommand getBindingStartCommand(RequestBindingStartCommand reqcmd,
            User user, Commands.ResponseCommand.Builder responseBuilder) {
        if (user.getIsOldUser() == 0) {
            user.setIsOldUser(1);
            userService.updateUser(user);
            pushService.pushUserInfoCommand(responseBuilder, user);
        }
        ResponseBindingStartCommand.Builder builder = ResponseBindingStartCommand.newBuilder();
        String token = reqcmd.getToken();
        BindingUrl bindingUrl = jsonService.getBindingUrl(token);
        builder.setAuthUrl(bindingUrl.getAuthUrl());
        log.info("The binding auth url: "+ bindingUrl.getAuthUrl());
        builder.setCloseUrl(bindingUrl.getCloseUrl());
        log.info("The binding auth url: "+ bindingUrl.getCloseUrl());
        return builder.build();
    }

    /**
     * 绑定结果返回
     * 
     * @param reqcmd
     * @return
     */
    public ResponseBindingResultCommand getBindingResultCommand(RequestBindingResultCommand reqcmd,
            Commands.ResponseCommand.Builder responseBuilder) {
        String token = reqcmd.getToken();
        ResponseBindingResultCommand.Builder builder = ResponseBindingResultCommand.newBuilder();
        BaseView result = jsonService.getBindingResult(token);
        if(result!=null && (result instanceof BindingResultView)){
            BindingResultView bindingResult = (BindingResultView) result;
            bindingResult.setOriginalToken(token);
            builder.setIsBinding(bindingResult.isBinding());
            UserInfo userInfo = buildUserInfo(bindingResult, responseBuilder);
            builder.setUserInfo(userInfo);
            if (!bindingResult.isNeedConfirm()) {
                builder.setBindingConfirmCommand(buildConfirmCommand(bindingResult.getReturnToken()));
                //清除token缓存 fix绑定后暂时无法发微博
                cache.delete(CacheKey.USER_WEIBO_INFO + token);
            }
        } else {
            // TODO return err cmd
            builder.setIsBinding(false);
        }
        return builder.build();
    }
    private UserInfo buildUserInfo(BindingResultView bindingResult,
            Commands.ResponseCommand.Builder responseBuilder) {
        UserInfo.Builder builder = UserInfo.newBuilder();
        String returnToken = bindingResult.getReturnToken();
        String originalToken = bindingResult.getOriginalToken();
        String headUrl = bindingResult.getHeadUrl();
        
        User user = userService.getUserByWillowtreeToken(originalToken);
        User newUser = userService.getUserByWillowtreeToken(returnToken);
        log.info("build user info : otoken={},return token={}",originalToken,returnToken);
        log.info("ouser={},newuser={}",user,newUser);
        // 使用一个新的微博账号绑定设备
        if (user.getCertType()==Const.CERT_TYPE_DEVICE) {
            //此处要兼容旧版本token变化情况
            if(newUser==null || originalToken.equals(returnToken)){
                user.setWillowtreeToken(returnToken);
                //update cert type
                user.setCertType(Const.CERT_TYPE_WEIBO);
                userService.updateUser(user);
            }
        }
        //切换用户
        if(newUser!=null && newUser.getId()!=user.getId()){
            user = newUser;
        }
        if (StringUtils.isNotBlank(headUrl)) {
            user.setHeadUrl(headUrl);
            user.setHeadIndex(-1);
            userService.updateUser(user);
            userService.clearCacheUser(user.getId());
        }
        if (user.getIsRewardedBind() == 0) {
            Reward bindReward = rewardService.getReward(Const.BIND_REWARDID);
            if (bindReward != null) {
                user.setIsRewardedBind(1);
                user = rewardService.doRewards(user, Const.BIND_REWARDID);
                pushService.pushPopupCommand(responseBuilder, bindReward, Match.BIND_POPUP, "", 0,
                        0);
            }
        }
        userService.regainEnergy(user);
        userInfoMessageService.buildUserInfoMessage(builder, user, bindingResult.getAccounts(),
                null);

        return builder.build();
    }

    private ResponseBindingConfirmCommand buildConfirmCommand(String returnToken) {
        ResponseBindingConfirmCommand.Builder builder = ResponseBindingConfirmCommand.newBuilder();
        builder.setToken(returnToken);
        return builder.build();
    }

    /**
     * 绑定确认 返回更新后的token
     * 
     * @param reqcmd
     * @return
     */
    public ResponseBindingConfirmCommand getBindingConfirmCommand(
            RequestBindingConfirmCommand reqcmd) {
        ResponseBindingConfirmCommand.Builder builder = ResponseBindingConfirmCommand.newBuilder();
        String originalToken = reqcmd.getToken();
        boolean isOverride = reqcmd.getIsOverride();
        String returnToken = jsonService.getBindingConfirm(originalToken, isOverride);
        builder.setToken(returnToken);
        /***
         * <pre>
         * 删除无用的设备用户，要满足条件：
         * 1.老用户是设备用户
         * 2.返回的微博用户是老用户
         * （设备用户成为了废用户）
         * </pre>
         */
        if (isOverride) {
            //清除token缓存 fix绑定后暂时无法发微博
            cache.delete(CacheKey.USER_WEIBO_INFO + originalToken);
            try {
                clearUserInfo(originalToken, returnToken);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return builder.build();
    }

    public ResponseBindingInfoCommand getBindingInfoCommand(RequestBindingInfoCommand reqcmd,
            Commands.ResponseCommand.Builder responseBuilder) {
        String uid = reqcmd.getUid();
        String accessToken = reqcmd.getAccessToken();
        ResponseBindingInfoCommand.Builder builder = ResponseBindingInfoCommand.newBuilder();
        User user = userService.getUserByUid(uid);
        if(user != null) {
            BindingResultView bindingResult = new BindingResultView();
            bindingResult.setBinding(true);
            bindingResult.setOriginalToken(accessToken);
            bindingResult.setReturnToken(user.getWillowtreeToken());
            builder.setIsBinding(bindingResult.isBinding());
            UserInfo userInfo = buildUserInfoOfBindingInfo(uid, responseBuilder);
            builder.setUserInfo(userInfo);
            builder.setBindingConfirmCommand(buildConfirmCommand(bindingResult.getReturnToken()));
        } else {
            // TODO return err cmd
        	builder.setBindingConfirmCommand(buildConfirmCommand(accessToken));
            builder.setIsBinding(true);
        }
        return builder.build();
    }
    
    private UserInfo buildUserInfoOfBindingInfo(String uid,
            Commands.ResponseCommand.Builder responseBuilder) {
        UserInfo.Builder builder = UserInfo.newBuilder();
        
        User user = userService.getUserByUid(uid);
        log.info("build user info : uid={}",uid);
        log.info("user={}",user);

        userService.regainEnergy(user);
        userInfoMessageService.buildUserInfoMessage(builder, user, null,
                null);

        return builder.build();
    }
    
    /**
     * 新版本绑定确认 返回更新后的token
     * 
     * @param reqcmd
     * @return
     */
    public ResponseBindingTokenCommand getBindingTokenCommand(
    		RequestBindingTokenCommand reqcmd, Commands.ResponseCommand.Builder responseBuilder) {
    	ResponseBindingTokenCommand.Builder builder = ResponseBindingTokenCommand.newBuilder();
        String newToken = reqcmd.getAccessToken();
        String originalToken = reqcmd.getToken();
        String uid = reqcmd.getUid();
        String nickName = reqcmd.getNickname();
        boolean isOverride = reqcmd.getIsOverride();
        /***
         * <pre>
         * 删除无用的设备用户，要满足条件：
         * 1.老用户是设备用户
         * 2.返回的微博用户是老用户
         * （设备用户成为了废用户）
         * </pre>
         */
        //清除token缓存 fix绑定后暂时无法发微博
        cache.delete(CacheKey.USER_WEIBO_INFO + originalToken);
        try {
        	String returnToken = updateUserInfo(originalToken, newToken, uid, nickName, isOverride, responseBuilder);
        	builder.setToken(returnToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.build();
    }
    
    /**
     * 清除originalToken的用户
     * 
     * @param originalToken
     * @param returnToken
     */
    private void clearUserInfo(String originalToken, String returnToken) {
        if (StringUtils.isBlank(originalToken) || StringUtils.isBlank(returnToken)) {
            return;
        }
        if (originalToken.equals(returnToken)) {
            return;
        }
        //returntoken是新用户 返回
        User returnUser = userService.getUserByWillowtreeToken(returnToken);
        if (returnUser == null) {
            return;
        }
        //original token 不是设备用户 返回
        User user = userService.getUserByWillowtreeToken(originalToken);
        if (user == null) {
            return;
        }
        if (user.getCertType() != Const.CERT_TYPE_DEVICE) {
            return;
        }
        //开始删除数据 避免出错暂时不清理 rename
        user.setName("DELETE_" + user.getId());
        userService.updateUser(user);
        long userId = user.getId();
        leaderboardService.deleteByUserId(userId);
        careerBestRacetimeRecordService.deleteByUserId(userId);
        tournamentLeaderboardService.deleteByUserId(userId);
        tournamentUserService.deleteByUserId(userId);
        //clear cache
        userService.clearCacheUser(user.getId());
    }

    /**
     * 清除originalToken的用户
     * 
     * @param originalToken
     * @param newToken
     */
    private String updateUserInfo(String originalToken, String accessToken, String uid, String nickName, boolean isOverride,
    		Commands.ResponseCommand.Builder responseBuilder) {
        if (StringUtils.isBlank(originalToken) || StringUtils.isBlank(accessToken)) {
            return originalToken;
        }
        if (originalToken.equals(accessToken)) {
            return originalToken;
        }
        
        //returntoken是新用户 返回
        User returnUser = userService.getUserByWillowtreeToken(originalToken);
        if (returnUser == null) {
            return originalToken;
        }
       
        User user = userService.getUserByUid(uid);
        if (user == null) {
            user = userService.getUserByWillowtreeToken(originalToken);
            if (user == null) {
                return originalToken;
            }
            user.setAccessToken(accessToken);
            if (!nickName.trim().equals(""))
            	user.setName(nickName);
            user.setUid(uid);
            user.setCertType(Const.CERT_TYPE_WEIBO);
//            if (user.getCertType() != Const.CERT_TYPE_DEVICE) {
//                return;
//            }
            //开始删除数据 避免出错暂时不清理 rename
//            user.setName("DELETE_" + user.getId());
            userService.updateUser(user);
//            long userId = user.getId();
//            leaderboardService.deleteByUserId(userId);
//            careerBestRacetimeRecordService.deleteByUserId(userId);
//            tournamentLeaderboardService.deleteByUserId(userId);
//            tournamentUserService.deleteByUserId(userId);
            //clear cache
            userService.clearCacheUser(user.getId());
        } else if (isOverride){
        	User deleteUser = userService.getUserByWillowtreeToken(originalToken);
            if (deleteUser != null) {
            	//开始删除数据 避免出错暂时不清理 rename
            	deleteUser.setName("DELETE_" + deleteUser.getId());
                userService.updateUser(deleteUser);
                long userId = deleteUser.getId();
                leaderboardService.deleteByUserId(userId);
                careerBestRacetimeRecordService.deleteByUserId(userId);
                tournamentLeaderboardService.deleteByUserId(userId);
                tournamentUserService.deleteByUserId(userId);
                //clear cache
                userService.clearCacheUser(deleteUser.getId());
            }
        } else {
        	user.setUid("");
        	user.setAccessToken("");
        	//开始删除数据 避免出错暂时不清理 rename
            user.setName("DELETE_" + user.getId());
            userService.updateUser(user);
            long userId = user.getId();
            leaderboardService.deleteByUserId(userId);
            careerBestRacetimeRecordService.deleteByUserId(userId);
            tournamentLeaderboardService.deleteByUserId(userId);
            tournamentUserService.deleteByUserId(userId);
            //clear cache
            userService.clearCacheUser(user.getId());
            
            user = userService.getUserByWillowtreeToken(originalToken);
            user.setAccessToken(accessToken);
            if (!nickName.trim().equals(""))
            	user.setName(nickName);
            user.setUid(uid);
            user.setCertType(Const.CERT_TYPE_WEIBO);
            userService.updateUser(user);
            //clear cache
            userService.clearCacheUser(user.getId());
        }

     
        if (user.getIsRewardedBind() == 0) {
            Reward bindReward = rewardService.getReward(Const.BIND_REWARDID);
            if (bindReward != null) {
                user.setIsRewardedBind(1);
                user = rewardService.doRewards(user, Const.BIND_REWARDID);
                pushService.pushPopupCommand(responseBuilder, bindReward, Match.BIND_POPUP, "", 0,
                        0);
            }
        }
        
        return user.getWillowtreeToken();
    }

    
}
