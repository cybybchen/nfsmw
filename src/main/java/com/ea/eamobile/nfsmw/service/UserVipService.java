package com.ea.eamobile.nfsmw.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.RewardConst;
import com.ea.eamobile.nfsmw.model.RaceMode;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserTrack;
import com.ea.eamobile.nfsmw.model.bean.RechargeDataBean;
import com.ea.eamobile.nfsmw.model.bean.RewardBean;
import com.ea.eamobile.nfsmw.model.bean.UserVipBean;
import com.ea.eamobile.nfsmw.model.handler.UserTrackListHandler;
import com.ea.eamobile.nfsmw.model.mapper.RewardMapper;
import com.ea.eamobile.nfsmw.model.mapper.UserTrackMapper;
import com.ea.eamobile.nfsmw.utils.CommonUtil;
import com.ea.eamobile.nfsmw.utils.DateUtil;
import com.ea.eamobile.nfsmw.utils.RWSplit;

@Service
public class UserVipService {

    @Autowired
    private UserTrackMapper userTrackMapper;
    @Autowired
    private RewardService rewardService;
    @Autowired
    private FinishRatioService ratioService;
    @Autowired
    private FinishRatioAdditionService finishRatioAdditionService;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private RechargeDataService rechargeDataService;

    public void insertUserTrack(UserTrack userTrack) {
        userTrackMapper.insert(userTrack);
    }

    public void updateUserTrack(UserTrack userTrack) {
        userTrackMapper.update(userTrack);
    }

    public void delete(int id) {
        userTrackMapper.deleteById(id);
    }

    public void deleteByUserId(long userId) {
        userTrackMapper.deleteByUserId(userId);
    }

    public UserTrack queryUserTrack(int id) {
        return userTrackMapper.queryById(id);
    }

    public UserTrack getUserTrackByMode(long userId, RaceMode mode) {
        if (mode == null)
            return null;
        return userTrackMapper.getUserTrackByMode(userId, mode.getId());
    }

    // TODO cache
    public List<UserTrack> getUserTracksByTrackId(long userId, String trackId) {
        return userTrackMapper.getUserTracksByTrackId(userId, trackId);
    }

    public List<UserTrack> getUserTracks(long userId) throws SQLException {
        QueryRunner run = new QueryRunner(RWSplit.getInstance().getReadDataSource());
        return run.query("SELECT * FROM user_track WHERE user_id = ?", new UserTrackListHandler(), userId);
    }

    public boolean addUserVipReward(User user) {
    	if (!CommonUtil.isNextDay(user.getVipLastRewardTime()) || !CommonUtil.isTimeExpried(user.getVipEndTime()))
    		return false;
    	user.setVipLastRewardTime(CommonUtil.getCurrentTimeStr(DateUtil.DEFAULT_DATETIME_FORMAT));
    	RechargeDataBean rechargeData = rechargeDataService.getRechargeDataByVipId(1);
    	List<RewardBean> rewardList = rechargeData.getRewardList();
    	rewardService.doRewards(user, rewardList);
    	
    	return true;
    }
    
    public boolean doUserMonthGoldCardReward(User user) {
    	if (!CommonUtil.isNextDay(user.getMonthGoldCardLastRewardTime()) || !CommonUtil.isTimeExpried(user.getMonthGoldCardEndTime()))
    		return false;
    	
    	user.setMonthGoldCardLastRewardTime(CommonUtil.getCurrentTimeStr(DateUtil.DEFAULT_DATETIME_FORMAT));
    	RechargeDataBean rechargeData = rechargeDataService.getRechargeDataById(RewardConst.PACKAGE_GOLDCARD_MONTH_ID);
    	List<RewardBean> rewardList = rechargeData.getRewardList();
    	rewardService.doRewards(user, rewardList);
    	return true;
    }
}
