package com.ea.eamobile.nfsmw.service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.constants.FinishRatioConst;
import com.ea.eamobile.nfsmw.model.FinishRatioAddition;
import com.ea.eamobile.nfsmw.model.RaceMode;
import com.ea.eamobile.nfsmw.model.Track;
import com.ea.eamobile.nfsmw.model.UserTrack;
import com.ea.eamobile.nfsmw.model.handler.UserTrackListHandler;
import com.ea.eamobile.nfsmw.model.mapper.UserTrackMapper;
import com.ea.eamobile.nfsmw.utils.DateUtil;
import com.ea.eamobile.nfsmw.utils.RWSplit;

@Service
public class UserTrackService {

    @Autowired
    private UserTrackMapper userTrackMapper;
    @Autowired
    private FinishRatioService ratioService;
    @Autowired
    private FinishRatioAdditionService finishRatioAdditionService;
    @Autowired
    private SystemConfigService systemConfigService;

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

    public int calcTrackFinishRatio(Track track, long userId) {
        List<UserTrack> userTracks = getUserTracksByTrackId(userId, track.getId());
        return calcTrackFinishRatio(track, userTracks);
    }

    /**
     * 计算赛道完成度% = 所有赛道下用户的mode的完成度总和/mode字典表value和
     * 
     * @param dictTrack
     * @param tracks
     * @return
     */
    public int calcTrackFinishRatio(Track dictTrack, List<UserTrack> tracks) {
        if (tracks == null || dictTrack == null) {
            return 0;
        }
        int userTrackTotal = 0; // 分子
        for (UserTrack track : tracks) {
            userTrackTotal += track.getValue();
        }
        float ratio = ((float) userTrackTotal / dictTrack.getCompleteStarNum()) * 100;
        return Math.min(100, (int) Math.floor(ratio));
    }

    /**
     * 计算关卡的完成度% == 玩家已有/关卡配置
     * 
     * @param modeId
     * @param rank
     * @param userId
     * @return
     */
    public int calcModeFinishRatio(RaceMode mode, UserTrack track, int rank, long userId, boolean finish, int type) {
        // 此处mode和usertrack不可能为空 用户比赛前就能新增赛道
        // 取用户当前关卡完成度数
        int currentRatio = track.getValue();
        // 根据本次比赛计算新增的完成度
        int ratio = 0;
        if (finish) {
            ratio = ratioService.getFinishRatioValue(mode.getFinishRatioType(), rank);
        }
		// 计算完成率 保存 并返回
		if (type != FinishRatioConst.FINISH_RATIO_NO_ADDITION) {
			ratio = getChangedRatio(mode.getId(), ratio, type);
		} else {
			// *****职业生涯赛完成度翻倍程序开始*****
			// 获取当前时间
			Date now = new Date();

			// 获取职业生涯赛关卡完成度翻倍开始时间
			Date startTime = DateUtil.getDateTime(systemConfigService
					.getSystemConfig(Const.MODE_FINISH_RATIO_START_TIME)
					.getValue());

			// 获取职业生涯赛关卡完成度翻倍结束时间
			Date endTime = DateUtil.getDateTime(systemConfigService
					.getSystemConfig(Const.MODE_FINISH_RATIO_END_TIME)
					.getValue());

			// 获取职业生涯赛关卡完成度翻倍次数
			int addTimes = Integer.parseInt(systemConfigService
					.getSystemConfig(Const.MODE_FINISH_RATIO_ADD_TIMES)
					.getValue());
			
			// 如果在活动有效期，则翻倍
			if (now.after(startTime) && now.before(endTime)) {
				ratio = ratio * addTimes;
			}
			// *****职业生涯赛完成度翻倍程序结束*****
		}
        track.setValue(Math.min(mode.getValue(), (currentRatio + ratio)));
        if (ratio > 0) {
            updateUserTrack(track);
        }
        int result = Math.min(100, (int) Math.floor(((float) (currentRatio + ratio) / mode.getValue()) * 100));
        return result;
    }

    private int getChangedRatio(int modeId, int ratio, int type) {
        int result = ratio;
        FinishRatioAddition finishRatioAddition = finishRatioAdditionService.getFinishRatioAddition(modeId, type);
        if (finishRatioAddition == null) {
            return ratio;
        }
        result = (int) (ratio * finishRatioAddition.getAddTimes());

        return result;
    }

    /**
     * 取当前赛道用户的完成度数量
     * 
     * @param userId
     * @param trackId
     * @return
     */
    public int getFinishRatioByTrack(long userId, String trackId) {
        int total = 0;
        List<UserTrack> list = getUserTracksByTrackId(userId, trackId);
        if (list != null) {
            for (UserTrack track : list) {
                total += track.getValue();
            }
        }
        return total;
    }

    /**
     * 判断当前赛道是否已经完成
     * 
     * @param USER_ID
     * @param trackId
     * @return
     */
    public boolean isFinishTrack(int userRatio, int trackTotalRatio) {
        return userRatio >= trackTotalRatio;
    }

    // TODO cache
    public Map<Integer, UserTrack> getUserModeMap(long userId) throws SQLException {
        List<UserTrack> list = getUserTracks(userId);
        if (list != null) {
            Map<Integer, UserTrack> map = new HashMap<Integer, UserTrack>();
            for (UserTrack track : list) {
                map.put(track.getModeId(), track);
            }
            return map;
        }
        return Collections.emptyMap();
    }

    /**
     * 保存解锁赛道
     * 
     * @param id
     * @param id2
     * @param firstMode
     */
    public void save(long userId, String trackId, int firstMode, int value) {
        UserTrack track = buildTrack(trackId, userId, firstMode, value);
        insertUserTrack(track);
    }

    public void save(long userId, Track track) {
        UserTrack userTrack = buildTrack(track.getId(), userId, track.getFirstMode(), 0);
        insertUserTrack(userTrack);
    }

    private UserTrack buildTrack(String trackId, long userId, int firstMode, int value) {
        UserTrack track = new UserTrack();
        track.setUserId(userId);
        track.setTrackId(trackId);
        track.setModeId(firstMode);
        track.setValue(value);
        return track;
    }

}
