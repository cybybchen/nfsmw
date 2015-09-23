package com.ea.eamobile.nfsmw.service.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.chain.CoreScreen;
import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.RaceMode;
import com.ea.eamobile.nfsmw.model.Reward;
import com.ea.eamobile.nfsmw.model.TierMode;
import com.ea.eamobile.nfsmw.model.Track;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserTrack;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.CheckPointInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.Leaderboard;
import com.ea.eamobile.nfsmw.protoc.Commands.ModeInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestTrackCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseCommand.Builder;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseTrackCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.TierInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.TrackInfo;
import com.ea.eamobile.nfsmw.service.LeaderboardService;
import com.ea.eamobile.nfsmw.service.RaceModeService;
import com.ea.eamobile.nfsmw.service.RewardService;
import com.ea.eamobile.nfsmw.service.TierCarLimitService;
import com.ea.eamobile.nfsmw.service.TierModeService;
import com.ea.eamobile.nfsmw.service.TrackService;
import com.ea.eamobile.nfsmw.service.UserTrackService;

/**
 * 处理track command的逻辑类
 * 
 * @author ma.ruofei@ea.com
 * 
 */
@Service
public class TrackCommandService extends BaseCommandService {
	private static final Logger log = LoggerFactory.getLogger(TrackCommandService.class);
	
    @Autowired
    private UserTrackService userTrackService;
    @Autowired
    private RaceModeService modeService;
    @Autowired
    private TrackService trackService;
    @Autowired
    private LeaderboardService leaderboardService;
    @Autowired
    private TierModeService tierModeService;
    @Autowired
    private TierCarLimitService tierCarLimitService;
    @Autowired
    private RewardService rewardService;

    public void buildTrackCommand(RequestTrackCommand cmd, Builder response, User user) throws SQLException {
        getResponseTrackCommand(cmd, user, response);
    }

    /**
     * return response command
     * 
     * @param reqcmd
     * @return
     * @throws SQLException 
     */
    private void getResponseTrackCommand(RequestTrackCommand reqcmd, User user,
            Commands.ResponseCommand.Builder responseBuilder) throws SQLException {
        ResponseTrackCommand.Builder builder = ResponseTrackCommand.newBuilder();
        int tierId = reqcmd.getRequestTier();
        // 只有客户端不知道的情况下才重新对tier赋值，tierid！=user.gettier,因为用户可能跑低级别赛道
        if (tierId < 0 || tierId > user.getTier()) {
            TierInfo tier = getTierInfo(user);
            builder.setTier(tier);
            tierId = tier.getTierIndex();

        }

        List<TrackInfo> tracks = buildTrackInfos(tierId, user);
        if (tracks != null) {
            builder.addAllTracks(tracks);
        }
        CheckPointInfo cpi = getCheckPointInfo(tierId, user);
        if (cpi != null) {
            builder.setCheckPointInfo(cpi);
        }
        // 判断是否完成了开场动画
        hasPrologued(user);
        responseBuilder.setTrackCommand(builder.build());
    }

    /**
     * 取用户当前tier
     * 
     * @param userId
     * @return
     */
    private TierInfo getTierInfo(User user) {
        TierInfo.Builder builder = TierInfo.newBuilder();
        int tier = user != null ? user.getTier() : 0;
        builder.setTierIndex(tier);
        builder.setTierAmount(trackService.getTierCount());
        return builder.build();
    }

    /**
     * 构建赛道原则: 1.取用户当前Tier的全部赛道 2.同名赛道只发送当前和unlock的赛道 即同一名字的赛道只下发一个 3.全部完成的赛道 发送最高级别的赛道
     * 
     * @param user
     * @return
     * @throws SQLException 
     */
    private List<TrackInfo> buildTrackInfos(int tier, User user) throws SQLException {
        List<TrackInfo> list = new ArrayList<TrackInfo>();
        long userId = user.getId();
        List<Track> tierTracks = trackService.getTracksByTier(tier);
        // 以赛道Name为key的临时列表判断是否执行添加操作
        Map<String, Track> tempMap = new HashMap<String, Track>();
        TrackInfo.Builder builder = TrackInfo.newBuilder();
        log.debug("tierTracks size:" + tierTracks.size());
        for (Track track : tierTracks) {
            // 同名赛道已添加后就不再添加了
            Track addedTrack = tempMap.get(track.getName());
            if (addedTrack != null) {
                continue;
            }
            String trackId = track.getId();
            log.debug("trackId is:" + trackId);
            List<UserTrack> userTracks = userTrackService.getUserTracksByTrackId(userId, trackId);
            int finishRatio = userTrackService.calcTrackFinishRatio(track, userTracks);
            // 已完成的 并且不是最大星级的不添加
            if (finishRatio >= Const.TRACK_FINISH_RATIO && !isMaxLevelTrack(track, tierTracks)) {
                continue;
            }
            log.debug("finishRatio is:" + finishRatio);
            boolean hasTrack = (userTracks != null && userTracks.size() > 0);
            boolean unlock = hasTrack;
            boolean trackStatusUpdate = false;
            // 判断是否是解锁并保存
            if (!hasTrack && canUnlock(user, track)) {
                unlock = true;
                userTrackService.save(userId, track);
                trackStatusUpdate = true;
            }
            // 取赛道以及用户的关卡信息数据
            List<ModeInfo> modes = getModeInfoList(userId, trackId);
            constructBuilder(builder, track, finishRatio, unlock, modes, trackStatusUpdate);
            // create trackinfo
            list.add(builder.build());
            tempMap.put(track.getName(), track);
            log.debug("list size:" + list.size());
        }
        return list;
    }

    private boolean isMaxLevelTrack(Track track, List<Track> tierTracks) {
        for (Track t : tierTracks) {
            if (t.getName().equals(track.getName()) && t.getStar() > track.getStar()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 构建builder 设置
     * 
     * @param builder
     * @param track
     * @param finishRatio
     * @param unlock
     * @param userTrackRatio
     * @param modes
     */
    private void constructBuilder(TrackInfo.Builder builder, Track track, int finishRatio, boolean unlock,
            List<ModeInfo> modes, boolean trackStatusUpdate) {
        boolean isHasNewMode = false;
        builder.clear();
        builder.setTrackStatusUpdated(trackStatusUpdate);
        builder.setTrackId(track.getId());
        builder.setTrackName(track.getName());
        builder.setDifficulty(track.getStar());
        builder.setFinishRatio(finishRatio);
        builder.setUnlock(unlock);
        builder.setUserMostwantedNum(track.getUnlockStarNum());
        builder.setIsNew(finishRatio == 0);// 完成度为0
        Reward reward = rewardService.getReward(track.getRewardId());
        if (reward != null) {
            com.ea.eamobile.nfsmw.protoc.Commands.Reward.Builder rewardBuilder = com.ea.eamobile.nfsmw.protoc.Commands.Reward
                    .newBuilder();
            rewardBuilder.setDisplayStrings(reward.getDisplayName());
            rewardBuilder.setMoney(reward.getMoney());
            rewardBuilder.setRmb(reward.getGold());
            builder.setReward(rewardBuilder.build());
        }
        if (modes != null) {
            for (ModeInfo modeInfo : modes) {
                if (modeInfo.getFinishRatio() == 0) {
                    isHasNewMode = true;
                    break;
                }
            }
            builder.addAllModes(modes);
        }
        builder.setIsHasNewMode(isHasNewMode);
        builder.setCarTypeForDisplay(track.getCarLimitDisplay());
    }

    private int calcModeFinishRatio(int currValue, int modeValue) {
        return Math.min(100, Math.round(((float) currValue / modeValue) * 100));
    }

    private boolean canUnlock(User user, Track track) {
        return user.getStarNum() >= track.getUnlockStarNum();
    }

    private List<ModeInfo> getModeInfoList(long userId, String trackId) throws SQLException {
        List<ModeInfo> list = null;
        List<RaceMode> modes = modeService.getTrackModes(trackId);
        if (modes != null) {
        	log.debug("modes size is:" + modes.size());
            list = new ArrayList<ModeInfo>();
            ModeInfo.Builder builder = ModeInfo.newBuilder();
            for (RaceMode mode : modes) {
                builder.clear();
                int modeId = mode.getId();
                builder.setModeId(modeId);
                builder.setModeName(mode.getName());
                builder.setModeType(mode.getType());
                UserTrack trackMode = userTrackService.getUserModeMap(userId).get(modeId);
                int finishRatio = -1;
                if (trackMode != null) {
                    finishRatio = calcModeFinishRatio(trackMode.getValue(), mode.getValue());
                }
                builder.setFinishRatio(finishRatio);
                builder.setMostwantedNum(rewardService.getMwNumByRaceMode(mode));
                builder.setIsNew(finishRatio == 0);
                builder.setRealTrackIndex(mode.getRealTrackIndex());
                // 取排行榜列表
                List<Leaderboard> board = buildLeaderboardList(leaderboardService.getLeaderboardByMode(mode), userId);
                if (board != null)
                    builder.addAllLeaderboard(board);
                list.add(builder.build());
            }
        }
        return list;
    }

    private CheckPointInfo getCheckPointInfo(int tierId, User user) {
        CheckPointInfo.Builder builder = CheckPointInfo.newBuilder();
        TierMode tm = tierModeService.getTierMode(tierId);
        builder.setModeId(tm.getModeId());
        builder.setUnlockMostwantedNum(tm.getUnlockStarNum());
        builder.setEventName(tm.getEventName());
        builder.setStaminaCost(tm.getEnergy());
        builder.setReward(buildReward(rewardService.getReward(tm.getRewardId()),true));
        builder.setCarLimitDisplayString(tm.getDisplayString());
        builder.addAllAcceptableCarIDs(tierCarLimitService.getTierCarLimitListByTierId(tierId));
        int status = 0;
        if (user.getTier() > tierId) {
            status=2;
        } else {
            if (user.getStarNum() < tm.getUnlockStarNum() || (tm.getTier() > user.getTier())) {

                status=0;
            } else {
                status=user.getTierStatus();
                if(status==Match.TIER_FIRST_SEE){
                    user.setTierStatus(Match.TIER_IS_NEW);
                    userService.updateUser(user);
                }
            }
        }
        builder.setStatus(status);
        return builder.build();

    }

}
