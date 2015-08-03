package com.ea.eamobile.nfsmw.service.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.CarConst;
import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.constants.ErrorConst;
import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.Car;
import com.ea.eamobile.nfsmw.model.CarChartlet;
import com.ea.eamobile.nfsmw.model.CarSlotConsumable;
import com.ea.eamobile.nfsmw.model.CareerBestRacetimeRecord;
import com.ea.eamobile.nfsmw.model.CheatRecord;
import com.ea.eamobile.nfsmw.model.GotchaCar;
import com.ea.eamobile.nfsmw.model.Merchandise;
import com.ea.eamobile.nfsmw.model.ModeStandardResult;
import com.ea.eamobile.nfsmw.model.RaceMode;
import com.ea.eamobile.nfsmw.model.RaceStartRecord;
import com.ea.eamobile.nfsmw.model.Reward;
import com.ea.eamobile.nfsmw.model.RpLevel;
import com.ea.eamobile.nfsmw.model.TournamentGroup;
import com.ea.eamobile.nfsmw.model.TournamentOnline;
import com.ea.eamobile.nfsmw.model.TournamentReward;
import com.ea.eamobile.nfsmw.model.TournamentUser;
import com.ea.eamobile.nfsmw.model.Track;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserCar;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.ErrorCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.Leaderboard;
import com.ea.eamobile.nfsmw.protoc.Commands.ProfileCarInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.RPMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestRaceResultCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseNotificationCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.TournamentDetailRewardMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.TournamentMessage;
import com.ea.eamobile.nfsmw.service.CarLimitService;
import com.ea.eamobile.nfsmw.service.CarService;
import com.ea.eamobile.nfsmw.service.CarSlotConsumableService;
import com.ea.eamobile.nfsmw.service.CarSlotService;
import com.ea.eamobile.nfsmw.service.CareerBestRacetimeRecordService;
import com.ea.eamobile.nfsmw.service.CareerStandardResultService;
import com.ea.eamobile.nfsmw.service.CheatRecordService;
import com.ea.eamobile.nfsmw.service.DailyRaceCarIdService;
import com.ea.eamobile.nfsmw.service.DailyRaceModeIdService;
import com.ea.eamobile.nfsmw.service.DailyRaceRecordService;
import com.ea.eamobile.nfsmw.service.DailyRaceRewardService;
import com.ea.eamobile.nfsmw.service.EventOptionMessageService;
import com.ea.eamobile.nfsmw.service.FinishRatioService;
import com.ea.eamobile.nfsmw.service.JsonService;
import com.ea.eamobile.nfsmw.service.LeaderboardService;
import com.ea.eamobile.nfsmw.service.ModeStandardResultService;
import com.ea.eamobile.nfsmw.service.PurchaseService;
import com.ea.eamobile.nfsmw.service.RaceModeService;
import com.ea.eamobile.nfsmw.service.RaceModeUnlockService;
import com.ea.eamobile.nfsmw.service.RaceResultFilterService;
import com.ea.eamobile.nfsmw.service.RaceRewardService;
import com.ea.eamobile.nfsmw.service.RaceStartRecordService;
import com.ea.eamobile.nfsmw.service.RewardService;
import com.ea.eamobile.nfsmw.service.RpLevelService;
import com.ea.eamobile.nfsmw.service.StoreMessageService;
import com.ea.eamobile.nfsmw.service.TierModeService;
import com.ea.eamobile.nfsmw.service.TournamentCarLimitService;
import com.ea.eamobile.nfsmw.service.TournamentGroupService;
import com.ea.eamobile.nfsmw.service.TournamentLeaderboardService;
import com.ea.eamobile.nfsmw.service.TournamentOnlineService;
import com.ea.eamobile.nfsmw.service.TournamentRewardService;
import com.ea.eamobile.nfsmw.service.TournamentService;
import com.ea.eamobile.nfsmw.service.TournamentUserService;
import com.ea.eamobile.nfsmw.service.TrackService;
import com.ea.eamobile.nfsmw.service.UserCarLikeLogService;
import com.ea.eamobile.nfsmw.service.UserCarLikeService;
import com.ea.eamobile.nfsmw.service.UserCarService;
import com.ea.eamobile.nfsmw.service.UserChartletService;
import com.ea.eamobile.nfsmw.service.UserDailyRaceService;
import com.ea.eamobile.nfsmw.service.UserGhostModService;
import com.ea.eamobile.nfsmw.service.UserGhostService;
import com.ea.eamobile.nfsmw.service.UserInfoMessageService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.service.UserTrackService;
import com.ea.eamobile.nfsmw.service.command.moderank.ModeRankTypeHandler;
import com.ea.eamobile.nfsmw.service.command.moderank.ModeRankTypeHandlerFactory;
import com.ea.eamobile.nfsmw.service.command.racetype.RaceTypeHandlerFactory;
import com.ea.eamobile.nfsmw.service.command.tournament.TournamentMessageService;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;
import com.ea.eamobile.nfsmw.service.ghost.TourGhostPoolService;
import com.ea.eamobile.nfsmw.service.ghost.UserGhostPoolService;
import com.ea.eamobile.nfsmw.service.gotcha.GotchaCarService;
import com.ea.eamobile.nfsmw.utils.ConfigUtil;
import com.ea.eamobile.nfsmw.utils.NumberUtil;
import com.ea.eamobile.nfsmw.utils.StringUtil;
import com.ea.eamobile.nfsmw.view.AbstractLeaderboard;
import com.ea.eamobile.nfsmw.view.CarChartletView;
import com.ea.eamobile.nfsmw.view.CarSlotView;
import com.ea.eamobile.nfsmw.view.CarView;
import com.ea.eamobile.nfsmw.view.MerchandiseImpl;

@Service
public class BaseCommandService {
    @Autowired
    protected UserGhostService ghostService;
    @Autowired
    protected UserGhostModService ghostModService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected UserTrackService userTrackService;
    @Autowired
    protected RaceModeService modeService;
    @Autowired
    protected RaceModeUnlockService raceModeUnlockService;
    @Autowired
    protected TrackService trackService;
    @Autowired
    protected TierModeService tierModeModService;
    @Autowired
    protected RpLevelService rpLevelService;
    @Autowired
    protected RaceRewardService raceRewardService;
    @Autowired
    protected RewardService rewardService;
    @Autowired
    protected LeaderboardService leaderboardService;
    @Autowired
    protected CareerBestRacetimeRecordService raceTimeRecordService;
    @Autowired
    protected UserCarService userCarService;
    @Autowired
    protected DailyRaceRewardService dailyRaceRewardService;
    @Autowired
    protected UserDailyRaceService userDailyRaceService;
    @Autowired
    protected ModeRankTypeHandlerFactory rankTypeHandlerFactory;
    @Autowired
    protected JsonService jsonService;
    @Autowired
    protected FinishRatioService ratioService;
    @Autowired
    protected RaceTypeHandlerFactory raceTypeHandlerFactory;
    @Autowired
    protected TournamentMessageService tourMessageService;
    @Autowired
    protected TournamentUserService tourUserService;
    @Autowired
    protected TournamentRewardService tourRewardService;
    @Autowired
    protected TournamentLeaderboardService tourLeaderboardService;
    @Autowired
    protected DailyRaceRecordService dailyRaceRecordService;
    @Autowired
    protected DailyRaceCarIdService dailyRaceCarIdService;
    @Autowired
    protected DailyRaceModeIdService dailyRaceModeIdService;
    @Autowired
    protected CarSlotService slotService;
    @Autowired
    protected CarSlotConsumableService slotConsumbleService;
    @Autowired
    protected TournamentOnlineService tournamentOnlineService;
    @Autowired
    protected TournamentService tournamentService;
    @Autowired
    protected TournamentGroupService tournamentGroupService;
    @Autowired
    protected CarLimitService carLimitDescribeService;
    @Autowired
    protected TournamentCarLimitService tournamentCarLimitService;
    @Autowired
    protected StoreMessageService storeMessageService;
    @Autowired
    protected PurchaseService purchaseService;
    @Autowired
    protected EventOptionMessageService eventOptionMessageService;
    @Autowired
    protected UserInfoMessageService userInfoMessageService;
    @Autowired
    protected UserChartletService userChartletService;
    @Autowired
    protected PushCommandService pushService;
    @Autowired
    protected RaceResultFilterService raceResultFilterService;
    @Autowired
    protected CheatRecordService cheatRecordService;
    @Autowired
    protected RaceStartRecordService raceStartRecordService;
    @Autowired
    protected CarSlotConsumableService carSlotConsumbleService;
    @Autowired
    protected CarService carService;
    @Autowired
    protected UserGhostPoolService userGhostPoolService;
    @Autowired
    protected TourGhostPoolService tourGhostPoolService;
    @Autowired
    protected MemcachedClient cache;
    @Autowired
    protected GotchaCarService gotchaCarService;
    @Autowired
    protected UserCarLikeService userCarLikeService;
    @Autowired
    protected UserCarLikeLogService userCarLikeLogService;
    @Autowired
    protected CareerStandardResultService careerStandardResultService;
    @Autowired
    protected ModeStandardResultService modeStandardResultService;

    /**
     * 构建reward显示
     * 
     * @param reward
     * @return
     */
    public com.ea.eamobile.nfsmw.protoc.Commands.Reward buildReward(Reward reward, boolean showDisplay) {
        Commands.Reward.Builder builder = Commands.Reward.newBuilder();
        if (reward != null) {
            builder.setRmb(reward.getGold());
            builder.setMoney(reward.getMoney());
            builder.setMostwantedNum(reward.getMostwantedNum());
            builder.setRpNum(reward.getRpNum());
            if (showDisplay) {
                builder.setDisplayStrings(reward.getDisplayName());
            }
        } else {
            builder.setRmb(0);
            builder.setMoney(0);
            builder.setMostwantedNum(0);
            builder.setRpNum(0);
            if (showDisplay) {
                builder.setDisplayStrings("");
            }
        }
        return builder.build();
    }

    public ResponseNotificationCommand buildResponseNotification(String message, float duraTime) {
        ResponseNotificationCommand.Builder rnbuilder = ResponseNotificationCommand.newBuilder();
        rnbuilder.setContent(message);
        rnbuilder.setDuration(duraTime);
        rnbuilder.setIconId(0);
        return rnbuilder.build();
    }

    public List<com.ea.eamobile.nfsmw.protoc.Commands.Reward> buildRewards(List<Reward> rewards, RaceMode mode) {
        List<com.ea.eamobile.nfsmw.protoc.Commands.Reward> list = Collections.emptyList();
        if (rewards != null && rewards.size() > 0) {
            list = new ArrayList<com.ea.eamobile.nfsmw.protoc.Commands.Reward>();
            Commands.Reward.Builder builder = Commands.Reward.newBuilder();
            // 注意reward list must be sort by rank
            for (int i = 0; i < rewards.size(); i++) {
                Reward reward = rewards.get(i);
                builder.setRmb(reward.getGold());
                builder.setMoney(reward.getMoney());
                builder.setMostwantedNum(reward.getMostwantedNum());
                builder.setRpNum(reward.getRpNum());
                builder.setDisplayStrings(reward.getDisplayName());
                if (mode != null) {
                    int finishRatio = ratioService.getFinishRatioPercentValue(mode, i + 1);
                    builder.setFinishRatio(finishRatio);
                }
                list.add(builder.build());
            }
        }
        return list;
    }

    /**
     * 构建rp信息
     * 
     * @param originalRpNum
     * @param user
     * @param responseBuilder
     * @return
     */
    public List<RPMessage> buildRpMessages(int originalRpNum, User user,
            Commands.ResponseCommand.Builder responseBuilder) {
        List<RPMessage> result = Collections.emptyList();
        int rpNum = user.getRpNum();
        if (originalRpNum == rpNum) {
            return result;
        }
        result = new ArrayList<RPMessage>();
        int originalLevel = rpLevelService.getLevelByRpNum(originalRpNum);
        int currentLevel = rpLevelService.getLevelByRpNum(user.getRpNum());
        // 构建当前经验进度信息 升不升级都要传2条记录用以进度动画显示 无需判断了
        RPMessage originalMessage = buildRpMessage(originalLevel, originalRpNum);
        result.add(originalMessage);
        RPMessage currentMessage = buildRpMessage(currentLevel, rpNum);
        result.add(currentMessage);
        if (currentLevel > originalLevel) {
            pushService.pushDailyRaceInfoCommand(responseBuilder, user, 1, 0);
        }
        return result;
    }

    private RPMessage buildRpMessage(int level, int rpNum) {
        RPMessage.Builder builder = RPMessage.newBuilder();
        RpLevel current = rpLevelService.getLevel(level);
        int maxExp = rpNum;
        RpLevel next = rpLevelService.getLevel(current.getLevel() + 1);
        if (next != null) {
            maxExp = next.getRpNum();
        }
        if (level < Match.MAX_LEVEL) {

            float percent = ((float) rpNum - current.getRpNum()) / (next.getRpNum() - current.getRpNum());
            builder.setPercentage(percent);
        } else {
            builder.setPercentage(1);
        }
        builder.setIconname(current.getIconId());
        builder.setLevel(level);
        builder.setTitle(current.getName());
        builder.setCurrentExp(rpNum);
        builder.setMinExp(current.getRpNum());
        builder.setMaxExp(maxExp);
        return builder.build();
    }

    /**
     * 计算并更新EOL
     * 
     * @param user
     * @param reqcmd
     */
    public void updateCareerUserEol(User user, RequestRaceResultCommand reqcmd) {
        int position = reqcmd.getGhosts().getPosition(); // 名次
        boolean isWin = reqcmd.getGhosts().getSuccess() || position == 1;
        boolean lastIsWin = user.getLastWinOrNot() == 1;
        // 设置用户胜负场次信息
        user.setLastWinOrNot(isWin ? 1 : 0);
        if (isWin) {
            user.setDuraMatchNum(lastIsWin ? (user.getDuraMatchNum() + 1) : 1);
            user.setMaxMatchWinNum(Math.max(user.getDuraMatchNum(), user.getMaxMatchWinNum()));
        } else {
            user.setDuraMatchNum(lastIsWin ? 1 : (user.getDuraMatchNum() + 1));
            user.setMaxMatchLoseNum(Math.max(user.getDuraMatchNum(), user.getDuraMatchNum()));
        }
        // 计算EOL
        float exceptPer = 0;
        float winPer = 0;
        if (reqcmd.getRaceType() == Match.RACE_TYPE_1V5) {
            winPer = Match.ONE_VS_FIVE_MAP.get(reqcmd.getRank());
        } else if (reqcmd.getRaceType() == Match.RACE_TYPE_1V1) {
            winPer = Match.ONE_VS_ONE_MAP.get(reqcmd.getRank());
        }
        if (reqcmd.hasGhostAvergeEol()) {
            exceptPer = (float) (1 / (double) (1 + Math.pow(10, (user.getEol() - reqcmd.getGhostAvergeEol())
                    / (double) 400)));
        }
        int changeEol = (int) (16 * (winPer - exceptPer));
        user.setEol(user.getEol() + changeEol);
        // update user
        userService.updateUser(user);
    }

    @SuppressWarnings("unchecked")
    public List<com.ea.eamobile.nfsmw.protoc.Commands.Leaderboard> getFriendLeaderboardList(RaceMode mode, User user) {
        long userId = user.getId();
        List<Leaderboard> list = (List<Leaderboard>) cache.get(CacheKey.USER_FRIEND_RACE_LB + userId + "_"
                + mode.getId());
        if (list == null || list.size() == 0) {
            ModeRankTypeHandler handler = rankTypeHandlerFactory.create(mode);
            list = new ArrayList<com.ea.eamobile.nfsmw.protoc.Commands.Leaderboard>();
            if (user.getCertType() == Const.CERT_TYPE_WEIBO) {
                List<String> friendTokens = jsonService.getSinaFreindUserList(user.getWillowtreeToken());
                List<CareerBestRacetimeRecord> recordList = raceTimeRecordService.getUserRecordList(friendTokens, mode);
                if (recordList != null && recordList.size() > 0) {
                    // 构建leaderboard
                    for (CareerBestRacetimeRecord record : recordList) {
                        float result = handler.getRecord(record);
                        if ((record.getUser().getAccountStatus() & Const.IS_NORECORD) == Const.IS_NORECORD) {
                            continue;
                        }
                        list.add(buildLeaderboard(record.getUser(), result, userId));
                    }
                }
                cache.set(CacheKey.USER_FRIEND_RACE_LB + userId + "_" + mode.getId(), list, MemcachedClient.HOUR);
            } else {
                CareerBestRacetimeRecord record = raceTimeRecordService.getCareerBestRacetimeRecord(userId,
                        mode.getModeType());
                if (record != null) {
                    float result = handler.getRecord(record);
                    list.add(buildLeaderboard(user, result, userId));
                }
            }
        }
        return list;
    }

    protected com.ea.eamobile.nfsmw.protoc.Commands.Leaderboard buildLeaderboard(User user, float result, long userId) {
        com.ea.eamobile.nfsmw.protoc.Commands.Leaderboard.Builder builder = com.ea.eamobile.nfsmw.protoc.Commands.Leaderboard
                .newBuilder();
        builder.setName(user.getName());
        builder.setHeadIndex(user.getHeadIndex());
        builder.setHeadUrl(user.getHeadUrl());
        builder.setRaceResult(result);
        builder.setIsMyself(userId == user.getId());
        builder.setUserId(user.getId());
        return builder.build();
    }

    public List<com.ea.eamobile.nfsmw.protoc.Commands.Leaderboard> buildLeaderboardList(
            List<? extends AbstractLeaderboard> topTenList, long userId) {
        List<com.ea.eamobile.nfsmw.protoc.Commands.Leaderboard> result = Collections.emptyList();
        if (topTenList.size() > 0) {
            result = new ArrayList<com.ea.eamobile.nfsmw.protoc.Commands.Leaderboard>();
            for (AbstractLeaderboard board : topTenList) {
                com.ea.eamobile.nfsmw.protoc.Commands.Leaderboard.Builder builder = com.ea.eamobile.nfsmw.protoc.Commands.Leaderboard
                        .newBuilder();
                builder.setName(board.getUserName());
                builder.setHeadIndex(board.getHeadIndex());
                builder.setHeadUrl(board.getHeadUrl() != null ? board.getHeadUrl() : "");
                builder.setRaceResult(board.getResult());
                builder.setIsMyself(userId == board.getUserId());
                builder.setUserId(board.getUserId());
                result.add(builder.build());
            }
        }
        return result;
    }

    public boolean isCheatCarId(User user, RequestRaceResultCommand reqcmd) throws SQLException {
        return raceResultFilterService.isCheatCarId(reqcmd.getModeId(), reqcmd.getGhosts().getCarID(),
                reqcmd.getGameMode(), user.getId());
    }

    public boolean canInLeaderboard(RequestRaceResultCommand reqcmd, int modeType, int rankType) {
        // TODO
        // CareerStandardResult careerStandardResult = careerStandardResultService.getCareerStandardResult(modeType);
        // if (careerStandardResult != null) {
        // if (rankType == Match.MODE_RANK_TYPE_BY_AVGSPEED
        // && reqcmd.getGhosts().getAverageSpd() < careerStandardResult.getAverageSpeed()) {
        // return true;
        // }
        // if (rankType == Match.MODE_RANK_TYPE_BY_TIME
        // && reqcmd.getGhosts().getRaceTime() > careerStandardResult.getRaceTime()) {
        // return true;
        // }
        //
        // }
        if (reqcmd.getGameMode() == Match.CAREER_MODE) {
            int modeId = reqcmd.getModeId();
            RaceMode raceMode = modeService.getModeById(modeId);
            if (raceMode == null) {
                return false;
            }
            Track track = trackService.queryTrack(Integer.parseInt(raceMode.getTrackId()));
            if (track != null && track.getTier() > 2) {
                return true;
            }
        }

        String CarId = reqcmd.getGhosts().getCarID();
        Car car = carService.getCar(CarId);
        if (car != null && car.getPriceType() == Const.GOLD_TYPE) {
            return true;
        }
        for (int i = 0; i < reqcmd.getGhosts().getCarModIdCount(); i++) {
            CarSlotConsumable consumable = carSlotConsumbleService.getCarSlotConsumableBySlotIdAndConType(reqcmd
                    .getGhosts().getCarModId(i), reqcmd.getGhosts().getCarModType(i));

            if (consumable != null && consumable.getPriceType() == Const.GOLD_TYPE) {
                return true;

            }
        }
        return false;
    }

    public boolean isCheatConsumble(User user, RequestRaceResultCommand reqcmd) {
        // return false;
        RaceStartRecord raceStartRecord = raceStartRecordService.getRaceStartRecord(user.getId());
        if (raceStartRecord == null) {

            return true;
        }
        String firstConsumble = "";
        String secondConsumble = "";
        String thirdConsumble = "";
        for (int i = 0; i < reqcmd.getGhosts().getCarModIdCount(); i++) {
            CarSlotConsumable consumable = carSlotConsumbleService.getCarSlotConsumableBySlotIdAndConType(reqcmd
                    .getGhosts().getCarModId(i), reqcmd.getGhosts().getCarModType(i));

            if (consumable == null) {
                continue;
            }
            if (i == 0) {
                firstConsumble = consumable.getSlotId() + "-" + consumable.getConType();
            }
            if (i == 1) {
                secondConsumble = consumable.getSlotId() + "-" + consumable.getConType();
            }
            if (i == 2) {
                thirdConsumble = consumable.getSlotId() + "-" + consumable.getConType();
            }
        }
        ArrayList<String> raceStartString = new ArrayList<String>();
        ArrayList<String> raceResultString = new ArrayList<String>();
        raceStartString.add(raceStartRecord.getFirstConsumebleId());
        raceStartString.add(raceStartRecord.getSecondConsumebleId());
        raceStartString.add(raceStartRecord.getThirdConsumebleId());
        raceResultString.add(firstConsumble);
        raceResultString.add(secondConsumble);
        raceResultString.add(thirdConsumble);
        raceResultString = StringUtil.sortString(raceResultString);
        raceStartString = StringUtil.sortString(raceStartString);
        if (raceResultString.size() == raceStartString.size()) {
            for (int i = 0; i < raceResultString.size(); i++) {
                if (!(raceResultString.get(i).equals(raceStartString.get(i)))) {
                    return true;
                }
            }
        }
        // 0306 client bug have been fixed
        // if (raceStartRecord.getFirstConsumebleId().equals(firstConsumble)
        // && raceStartRecord.getSecondConsumebleId().equals(secondConsumble)
        // && raceStartRecord.getThirdConsumebleId().equals(thirdConsumble)) {
        // return false;
        // }
        return false;
    }

    public ErrorCommand buildErrorCommand(ErrorConst errorConst) {
        ErrorCommand.Builder erBuilder = ErrorCommand.newBuilder();
        erBuilder.setCode(String.valueOf(errorConst.getCode()));
        erBuilder.setMessage(errorConst.getMesssage());
        return erBuilder.build();
    }

    public void recordCheatInfo(RequestRaceResultCommand cmd, String reason, User user) {
        CheatRecord cheatRecord = new CheatRecord();
        cheatRecord.setCarId(cmd.getGhosts().getCarID());
        cheatRecord.setIsRaceStart(user.getIsRaceStart());
        cheatRecord.setModeId(cmd.getModeId());
        cheatRecord.setRaceTime(cmd.getGhosts().getRaceTime());
        cheatRecord.setGameMode(cmd.getGameMode());
        cheatRecord.setReason(reason);
        cheatRecord.setUserId(user.getId());
        cheatRecordService.insert(cheatRecord);
    }

    

    /**
     * 是否看过了开场动画
     * 
     * @param user
     */
    protected void hasPrologued(User user) {
        if (user != null && user.getIsOldUser() != Const.HAS_PROLOGUED) {
            user.setIsOldUser(Const.HAS_PROLOGUED);
            userService.updateUser(user);
        }
    }

    protected List<TournamentMessage> buildTourMessages(List<TournamentOnline> onlineList, int type, User user)
            throws SQLException {
        List<TournamentMessage> list = Collections.emptyList();
        if (onlineList != null && onlineList.size() > 0) {
            list = new ArrayList<TournamentMessage>();
            for (TournamentOnline online : onlineList) {
                TournamentMessage message = tourMessageService.buildTournamentMessage(type, user, online);
                if (message != null) {
                    list.add(message);
                }
            }
        }
        return list;
    }

    protected List<TournamentDetailRewardMessage> buildTourDetailRewardMessages(int tourGroupId) {
        List<TournamentDetailRewardMessage> result = Collections.emptyList();
        List<TournamentReward> tourRewards = tourRewardService.getTournamentRewardListByGroupId(tourGroupId);
        if (tourRewards != null && tourRewards.size() > 0) {
            result = new ArrayList<TournamentDetailRewardMessage>();
            for (TournamentReward tourReward : tourRewards) {
                TournamentDetailRewardMessage.Builder builder = TournamentDetailRewardMessage.newBuilder();
                Reward reward = rewardService.getReward(tourReward.getRewardId());
                if (reward != null) {
                    builder.setRmb(String.valueOf(reward.getGold()));
                    builder.setMoney(String.valueOf(reward.getMoney()));
                    builder.setTitle(reward.getName() + ": ");
                    builder.setDisplayName(reward.getDisplayName());
                    result.add(builder.build());
                }
            }
        }
        return result;
    }

    /**
     * 构建好友排行榜
     * 
     * @param online
     * @param user
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    protected List<Leaderboard> buildFriendLeaderboardList(TournamentOnline online, User user) throws SQLException {
        long userId = user.getId();
        List<Leaderboard> list = (List<Leaderboard>) cache.get(CacheKey.USER_FRIEND_TOUR_LB + userId + "_"
                + online.getId());
        if (list == null || list.size() == 0) {
            list = new ArrayList<Leaderboard>();
            List<TournamentUser> tourUsers = new ArrayList<TournamentUser>();
            if (user.getCertType() == Const.CERT_TYPE_WEIBO) {
                List<String> friendTokens = jsonService.getSinaFreindUserList(user.getWillowtreeToken());
                // 构建tourUser列表
                List<User> friends = userService
                        .getUsersByTokens(friendTokens.toArray(new String[friendTokens.size()]));
                for (User friend : friends) {
                    TournamentUser tourUser = tourUserService.getTournamentUser(friend, online.getId());
                    if (tourUser != null && tourUser.getResult() > 0
                            && ((friend.getAccountStatus() & Const.IS_NORECORD) == 0)) {
                        tourUser.setUser(friend);// 后面用
                        tourUsers.add(tourUser);
                    }
                }
                Collections.sort(tourUsers, new ComparatorTourUser(online.getTournament().getType()));
                if (tourUsers != null && tourUsers.size() > 0) {
                    for (TournamentUser tourUser : tourUsers) {
                        if (online.getTournament().getType() == Match.MODE_RANK_TYPE_BY_AVGSPEED) {
                            list.add(buildLeaderboard(tourUser.getUser(), tourUser.getAverageSpeed(), userId));
                        } else if (online.getTournament().getType() == Match.MODE_RANK_TYPE_BY_TIME) {
                            list.add(buildLeaderboard(tourUser.getUser(), tourUser.getResult(), userId));
                        }
                    }
                }
                cache.set(CacheKey.USER_FRIEND_TOUR_LB + userId + "_" + online.getId(), list, MemcachedClient.HOUR);
            } else {
                TournamentUser tourUser = tourUserService.getTournamentUser(user, online.getId());
                if (tourUser != null) {
                    tourUsers.add(tourUser);
                    if (online.getTournament().getType() == Match.MODE_RANK_TYPE_BY_AVGSPEED) {
                        list.add(buildLeaderboard(tourUser.getUser(), tourUser.getAverageSpeed(), userId));
                    } else if (online.getTournament().getType() == Match.MODE_RANK_TYPE_BY_TIME) {
                        list.add(buildLeaderboard(tourUser.getUser(), tourUser.getResult(), userId));
                    }
                }
            }
        }
        return list;
    }

    class ComparatorTourUser implements Comparator<TournamentUser> {
        private int rankType;

        @Override
        public int compare(TournamentUser user1, TournamentUser user2) {
            ModeRankTypeHandler handler = rankTypeHandlerFactory.create(rankType);
            float diff = 0;
            if (rankType == Match.MODE_RANK_TYPE_BY_TIME) {
                diff = handler.diffResult(user1.getResult(), user2.getResult());
            } else if (rankType == Match.MODE_RANK_TYPE_BY_AVGSPEED) {
                diff = handler.diffResult(user1.getAverageSpeed(), user2.getAverageSpeed());
            }
            if (diff > 0) {
                return 1;
            } else if (diff < 0) {
                return -1;
            } else {
                return 0;
            }
        }

        public ComparatorTourUser(int rankType) {
            this.rankType = rankType;
        }
    }

    protected Merchandise buildMerchandise(int price, int priceType) {
        return new MerchandiseImpl(price, priceType);
    }

    protected boolean hasGotchaCar(List<CarView> unlockCars) {
        boolean result = false;
        for (CarView carView : unlockCars) {
            GotchaCar gotchaCar = gotchaCarService.getGotchaCar(carView.getCarId());
            if (gotchaCar != null) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 按price获取最贵汽车贴图
     * 
     * @param carId
     * @param userId
     * @return paintIndex：返回玩家已拥有的最贵贴图Index
     * @throws SQLException
     */
    protected int getBestPaintJobIndex(String carId, long userId) throws SQLException {
        int paintIndex = 0;
        List<CarChartletView> list = userChartletService.getChartletViewList(carId, userId);
        if (list != null && list.size() > 0) {
            int i = 0;
            int maxPrice = 0;
            for (CarChartletView view : list) {
                CarChartlet clet = view.getChartlet();
                if (view.isOwned() && maxPrice <= clet.getPrice()) {
                    maxPrice = clet.getPrice();
                    paintIndex = i;
                }
                i++;
            }
        }
        return paintIndex;
    }

    /**
     * 计算汽车贴图总评分
     * 
     * @param carId
     * @param userId
     * @return score：返回玩家已拥有的贴图总评分
     * @throws SQLException
     */
    protected int getPaintJobScore(String carId, long userId) throws SQLException {
        int score = 0;
        List<CarChartletView> list = userChartletService.getChartletViewList(carId, userId);
        if (list != null && list.size() > 0) {
            for (CarChartletView view : list) {
                CarChartlet clet = view.getChartlet();
                if (view.isOwned()) {
                    score += clet.getScore();
                }
            }
        }
        return score;
    }

    /**
     * 获取汽车插槽简易数据，只存储可用插槽的等级
     * 
     * @param slots
     *            : list of CarSlotView
     * @return slotList: list of simple slot info
     */
    protected List<Integer> getSimpleSlotInfo(List<CarSlotView> slots) {
        List<Integer> slotList = new ArrayList<Integer>();
        if (slots != null) {
            for (CarSlotView slot : slots) {
                if (slot.getStatus() == 1) {// Enabled
                    slotList.add(slot.getLevel());
                }
            }
        }
        return slotList;
    }

    /**
     * 根据userCarId创建ProfileCarInfo
     * 
     * @param userId
     *            : 赞车的人
     * @param userCar
     *            : 被赞的车
     * @param carView
     *            : 被赞的车
     * @return ProfileCarInfo builder，如果根据userCarId找不到userCar则返回null
     * @throws SQLException
     */
    protected ProfileCarInfo.Builder buildProfileCarInfo(long userId, UserCar userCar, CarView carView)
            throws SQLException {
        ProfileCarInfo.Builder carInfo = ProfileCarInfo.newBuilder();

        long userCarId = userCar.getId();
        carInfo.setUserCarId(userCarId);
        carInfo.setCarId(userCar.getCarId());
        carInfo.addAllSlotLevel(getSimpleSlotInfo(carView.getSlots()));
        int paintIndex = getBestPaintJobIndex(userCar.getCarId(), userCar.getUserId());
        carInfo.setCarPaintJobIndex(paintIndex);
        carInfo.setCarScore(getCarRatingScore(carView, userCar));
        long likeNum = userCarLikeService.getLikeCountByUserCarId(userCarId);
        carInfo.setLikeNum(likeNum);
        boolean canLike = !userCarLikeLogService.hasLog(userId, userCarId);
        carInfo.setCanLike(canLike);

        return carInfo;
    }

    /**
     * 创建ProfileCarInfo对象，使用空值填充
     * 
     * @param userCar
     * @param carView
     * @return ProfileCarInfo builder，如果根据userCarId找不到userCar则返回null
     * @throws SQLException
     */
    protected ProfileCarInfo.Builder buildProfileCarInfo() {
        ProfileCarInfo.Builder carInfo = ProfileCarInfo.newBuilder();
        carInfo.setUserCarId(0);
        carInfo.setCarId("dodge_challenger_srt8_392_2011_desc");
        carInfo.addAllSlotLevel(getSimpleSlotInfo(null));
        carInfo.setCarPaintJobIndex(0);
        carInfo.setCarScore(0);
        carInfo.setLikeNum(0);
        carInfo.setCanLike(false);

        return carInfo;
    }

    /**
     * 计算car评分
     * 
     * @param carView
     * @param userCar
     * @return
     * @throws SQLException
     */
    protected int getCarRatingScore(CarView carView, UserCar userCar) throws SQLException {
        int score = 0;
        score = carView.getScore();
        // 计算插槽
        score += userCarService.getCarSlotScore(userCar);
        // 计算喷图
        score += getPaintJobScore(userCar.getCarId(), userCar.getUserId());
        return score;
    }

    /**
     * 返回指定错误信息
     * 
     * @param responseBuilder
     * @param errorConst
     *            ：错误类型
     * @return
     */
    protected void responseErrorCommand(Commands.ResponseCommand.Builder responseBuilder, ErrorConst errorConst) {
        ErrorCommand errorCommand = buildErrorCommand(errorConst);
        responseBuilder.setErrorCommand(errorCommand);
    }

    protected boolean isBeyondYellowLine(RequestRaceResultCommand reqcmd, long userId, int groupId) throws SQLException {
        boolean result = false;
        int modeId = reqcmd.getModeId();
        if (reqcmd.getGameMode() == Match.TOURNAMENT_MODE) {
            TournamentGroup tournamentGroup = tournamentGroupService.getTournamentGroup(groupId);

            if (tournamentGroup == null) {
                return false;
            }
            modeId = tournamentGroup.getModeId();

        }
        ModeStandardResult modeStandardResult = modeStandardResultService.getModeStandardResult(modeId, reqcmd
                .getGhosts().getCarID());
        if (modeStandardResult == null) {
            return false;
        }
        float standardTime = modeStandardResult.getRaceTime();
        float standardSpeed = modeStandardResult.getAverageSpeed();
        for (int i = 0; i < reqcmd.getGhosts().getCarModIdCount(); i++) {
            CarSlotConsumable consumable = carSlotConsumbleService.getCarSlotConsumableBySlotIdAndConType(reqcmd
                    .getGhosts().getCarModId(i), reqcmd.getGhosts().getCarModType(i));

            if (consumable == null) {
                continue;
            }
            if (consumable.getConType() == CarConst.POWER_PACK_CONSUMBLE) {
                standardSpeed = standardSpeed + modeStandardResult.getPowerPackAddSpeed();
                standardTime = standardTime + modeStandardResult.getPowerPackAddTime();
            }
            if (consumable.getConType() == CarConst.NITROUS_BURN_CONSUMBLE) {
                standardSpeed = standardSpeed + modeStandardResult.getNitrousBurnAddSpeed();
                standardTime = standardTime + modeStandardResult.getNitrousBurnAddTime();
            }
        }
        if (reqcmd.getGhosts().getAverageSpd() > 0 && reqcmd.getGhosts().getAverageSpd() > standardSpeed) {
            return true;
        }
        if (reqcmd.getGhosts().getRaceTime() < standardTime) {
            return true;
        }
        return result;

    }

    protected float changedRaceTime(ModeStandardResult modeStandardResult, RequestRaceResultCommand reqcmd) {
        float standardTime = modeStandardResult.getRaceTime();
        for (int i = 0; i < reqcmd.getGhosts().getCarModIdCount(); i++) {
            CarSlotConsumable consumable = carSlotConsumbleService.getCarSlotConsumableBySlotIdAndConType(reqcmd
                    .getGhosts().getCarModId(i), reqcmd.getGhosts().getCarModType(i));
            if (consumable == null) {
                continue;
            }
            if (consumable.getConType() == CarConst.POWER_PACK_CONSUMBLE) {
                standardTime = standardTime + modeStandardResult.getPowerPackAddTime();
            }
            if (consumable.getConType() == CarConst.NITROUS_BURN_CONSUMBLE) {
                standardTime = standardTime + modeStandardResult.getNitrousBurnAddTime();
            }
        }
        return (float) (standardTime + Integer.parseInt(ConfigUtil.RACE_ADD_TIME) + NumberUtil.randomDouble());
    }

    protected float changedSpeedRun(ModeStandardResult modeStandardResult, RequestRaceResultCommand reqcmd) {
        float standardSpeed = modeStandardResult.getAverageSpeed();
        for (int i = 0; i < reqcmd.getGhosts().getCarModIdCount(); i++) {
            CarSlotConsumable consumable = carSlotConsumbleService.getCarSlotConsumableBySlotIdAndConType(reqcmd
                    .getGhosts().getCarModId(i), reqcmd.getGhosts().getCarModType(i));
            if (consumable == null) {
                continue;
            }
            if (consumable.getConType() == CarConst.POWER_PACK_CONSUMBLE) {
                standardSpeed = standardSpeed + modeStandardResult.getPowerPackAddSpeed();
            }
            if (consumable.getConType() == CarConst.NITROUS_BURN_CONSUMBLE) {
                standardSpeed = standardSpeed + modeStandardResult.getNitrousBurnAddSpeed();
            }
        }
        return (float) (standardSpeed - (Integer.parseInt(ConfigUtil.RACE_ADD_SPEED) + NumberUtil.randomRange(0, 3)));
    }
}
