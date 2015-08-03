package com.ea.eamobile.nfsmw.service.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CarConst;
import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.constants.CtaContentConst;
import com.ea.eamobile.nfsmw.constants.IapConst;
import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.ComparatorTournamentOnline;
import com.ea.eamobile.nfsmw.model.DailyRaceCarId;
import com.ea.eamobile.nfsmw.model.DailyRaceModeId;
import com.ea.eamobile.nfsmw.model.DailyRaceRecord;
import com.ea.eamobile.nfsmw.model.FeedContent;
import com.ea.eamobile.nfsmw.model.Purchase;
import com.ea.eamobile.nfsmw.model.SystemConfig;
import com.ea.eamobile.nfsmw.model.Tournament;
import com.ea.eamobile.nfsmw.model.TournamentGroup;
import com.ea.eamobile.nfsmw.model.TournamentLeaderboard;
import com.ea.eamobile.nfsmw.model.TournamentOnline;
import com.ea.eamobile.nfsmw.model.TournamentReward;
import com.ea.eamobile.nfsmw.model.TournamentUser;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserDailyRace;
import com.ea.eamobile.nfsmw.protoc.Commands.CarData;
import com.ea.eamobile.nfsmw.protoc.Commands.EventOptionMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.ItemMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseChallengeMatchInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseCommand.Builder;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseFeedCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseGarageCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseModifyUserInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseNotificationCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponsePopupCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseRegistJaguarCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseStoreDetailCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseSystemCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseTournamentCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseTournamentDetailCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseTournamentRewardNumCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseTrackCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseWeiboShareLocksCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.Reward;
import com.ea.eamobile.nfsmw.protoc.Commands.TierInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.TournamentDetailMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.TournamentDetailRewardMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.TournamentMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.UserInfo;
import com.ea.eamobile.nfsmw.service.CarDataMessageService;
import com.ea.eamobile.nfsmw.service.CarLimitService;
import com.ea.eamobile.nfsmw.service.CtaContentService;
import com.ea.eamobile.nfsmw.service.DailyRaceCarIdService;
import com.ea.eamobile.nfsmw.service.DailyRaceModeIdService;
import com.ea.eamobile.nfsmw.service.DailyRaceRecordService;
import com.ea.eamobile.nfsmw.service.DailyRaceRewardService;
import com.ea.eamobile.nfsmw.service.EventOptionMessageService;
import com.ea.eamobile.nfsmw.service.FeedContentService;
import com.ea.eamobile.nfsmw.service.NewsService;
import com.ea.eamobile.nfsmw.service.PurchaseService;
import com.ea.eamobile.nfsmw.service.RewardService;
import com.ea.eamobile.nfsmw.service.StoreMessageService;
import com.ea.eamobile.nfsmw.service.SystemConfigService;
import com.ea.eamobile.nfsmw.service.TournamentCarLimitService;
import com.ea.eamobile.nfsmw.service.TournamentGroupService;
import com.ea.eamobile.nfsmw.service.TournamentLeaderboardService;
import com.ea.eamobile.nfsmw.service.TournamentOnlineService;
import com.ea.eamobile.nfsmw.service.TournamentRewardService;
import com.ea.eamobile.nfsmw.service.TournamentService;
import com.ea.eamobile.nfsmw.service.TournamentUserService;
import com.ea.eamobile.nfsmw.service.TrackService;
import com.ea.eamobile.nfsmw.service.UserCarService;
import com.ea.eamobile.nfsmw.service.UserDailyRaceService;
import com.ea.eamobile.nfsmw.service.UserInfoMessageService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.service.command.moderank.ModeRankTypeHandlerFactory;
import com.ea.eamobile.nfsmw.service.command.tournament.TournamentMessageService;
import com.ea.eamobile.nfsmw.utils.ConfigUtil;
import com.ea.eamobile.nfsmw.utils.DateUtil;
import com.ea.eamobile.nfsmw.utils.NumberUtil;
import com.ea.eamobile.nfsmw.view.CarView;

/**
 * 推送需要更新信息的cmd给前端
 * 
 * @author ma.ruofei@ea.com
 * 
 */
@Service
public class PushCommandService {

    @Autowired
    private UserService userService;

    @Autowired
    private TrackService trackService;

    @Autowired
    private RewardService rewardService;

    @Autowired
    private UserCarService userCarService;
    @Autowired
    private DailyRaceRewardService dailyRaceRewardService;
    @Autowired
    private UserDailyRaceService userDailyRaceService;
    @Autowired
    private SystemConfigService configService;
    @Autowired
    private TournamentMessageService tourMessageService;
    @Autowired
    private TournamentUserService tourUserService;
    @Autowired
    private TournamentRewardService tourRewardService;
    @Autowired
    private TournamentLeaderboardService tourLeaderboardService;
    @Autowired
    private DailyRaceRecordService dailyRaceRecordService;
    @Autowired
    private DailyRaceCarIdService dailyRaceCarIdService;
    @Autowired
    private DailyRaceModeIdService dailyRaceModeIdService;
    @Autowired
    private TournamentOnlineService tournamentOnlineService;
    @Autowired
    private TournamentService tournamentService;
    @Autowired
    private TournamentGroupService tournamentGroupService;
    @Autowired
    private CarLimitService carLimitService;
    @Autowired
    private TournamentCarLimitService tournamentCarLimitService;
    @Autowired
    private StoreMessageService storeMessageService;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private EventOptionMessageService eventOptionMessageService;
    @Autowired
    private UserInfoMessageService userInfoMessageService;
    @Autowired
    private ModeRankTypeHandlerFactory modeRankTypeHandlerFactory;
    @Autowired
    private CtaContentService ctaContentService;
    @Autowired
    private FeedContentService feedContentService;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private CarDataMessageService carDataMessageService;
    @Autowired
    private NewsService newsService;

    private ResponseModifyUserInfoCommand getResponseUserInfoCommand(User user) {
        ResponseModifyUserInfoCommand.Builder builder = ResponseModifyUserInfoCommand.newBuilder();

        UserInfo.Builder usbuilder = UserInfo.newBuilder();
        // usbuilder.setUserId(user.getId());
        userInfoMessageService.buildUserInfoMessage(usbuilder, user);

        builder.setUserinfo(usbuilder.build());
        return builder.build();
    }

    /**
     * 添加用户信息cmd返回给前端刷新用
     * 
     * @param responseBuilder
     * @param user
     */
    public void pushUserInfoCommand(Builder responseBuilder, User user) {
        ResponseModifyUserInfoCommand cmd = getResponseUserInfoCommand(user);
        responseBuilder.setModifyUserInfoCommand(cmd);
    }

    public void pushUserInfoAndFeedCommand(Builder responseBuilder, User user) {
        ResponseModifyUserInfoCommand cmd = getResponseUserInfoCommand(user);
        responseBuilder.setModifyUserInfoCommand(cmd);
        pushFeedCommand(responseBuilder, user.getId());
    }

    public void pushUserCarInfoCommand(Builder responseBuilder, List<CarView> carViewList, long userId)
            throws SQLException {
        ResponseGarageCommand cmd = getResponseUserCarInfoCommand(carViewList, userId);
        responseBuilder.setGarageCommand(cmd);
    }

    public void pushTournamentCommand(Builder responseBuilder, long userId) throws SQLException {
        ResponseTournamentCommand cmd = getResponseTournamentCommand(userId);
        responseBuilder.setTournamentCommand(cmd);
    }

    public void pushWeiboShareLocksCommand(Builder responseBuilder) {
        ResponseWeiboShareLocksCommand cmd = getResponseWeiboShareLocksCommand();
        responseBuilder.setWeiboShareCommand(cmd);
    }

    public void pushTournamentDetailCommand(Builder responseBuilder, long userId, int tournamentOnlineId, int rankType)
            throws SQLException {
        ResponseTournamentDetailCommand cmd = getResponseTournamentDetailCommand(tournamentOnlineId, userId, rankType);
        responseBuilder.setTournamentDetailCommand(cmd);
    }

    public void pushStoreDetailCommand(Builder responseBuilder, long userId) {
        ResponseStoreDetailCommand cmd = getResponseStoreDetailCommand(userId);
        responseBuilder.setStoreDetailCommand(cmd);
    }

    public void pushSystemCommand(Builder responseBuilder, String session, User user, int eventOptionVersion,
            int version) {
        ResponseSystemCommand cmd = getResponseSystemCommand(session, user, eventOptionVersion, version);
        responseBuilder.setSystemCommand(cmd);
    }

    public void pushRegistJaguarCommand(Builder responseBuilder, User user) {
        ResponseRegistJaguarCommand cmd = getResponseRegistJaguarCommand(user);
        responseBuilder.setRegistJaguar(cmd);
    }

    public void pushTierInfoCommand(Builder responseBuilder, long userId) {
        ResponseTrackCommand cmd = getResponseTrackCommand(userId);
        responseBuilder.setTrackCommand(cmd);
    }

    private ResponseTrackCommand getResponseTrackCommand(long userId) {
        ResponseTrackCommand.Builder rtcbuilder = ResponseTrackCommand.newBuilder();
        rtcbuilder.setTier(getTierInfo(userId));
        return rtcbuilder.build();
    }

    public void pushFeedCommand(Builder responseBuilder, long userId) {
        ResponseFeedCommand cmd = getResponseFeedCommand(userId);
        responseBuilder.setFeedCommand(cmd);
    }

    public void pushPopupCommand(Builder responseBuilder, com.ea.eamobile.nfsmw.model.Reward reward, int type,
            String content, int days, int tier) {
        ResponsePopupCommand cmd = getResponsePopupCommand(reward, type, content, days, tier);
        responseBuilder.setPopupCommand(cmd);
    }

    public void pushNotificationCommand(Builder responseBuilder, List<CarView> carViewList) {
        ResponseNotificationCommand cmd = getResponseNotificationCommand(carViewList);
        responseBuilder.setNotificationCommand(cmd);
    }

    public void pushTournamentRewardNum(Builder responseBuilder, User user) throws SQLException {
        ResponseTournamentRewardNumCommand cmd = getResponseTournamentRewardNumCommand(user);
        responseBuilder.setTournamentRewardNumCommand(cmd);
    }

    private ResponseTournamentRewardNumCommand getResponseTournamentRewardNumCommand(User user) throws SQLException {
        ResponseTournamentRewardNumCommand.Builder trnBuilder = ResponseTournamentRewardNumCommand.newBuilder();
        List<Integer> idList = tourUserService.getTournamentUser(user.getId());

        List<TournamentOnline> onlineFinishList = tournamentOnlineService.getFinishedOnlineList(idList);

        int num = 0;
        for (TournamentOnline to : onlineFinishList) {
            Tournament tournament = tournamentService.getTournament(to.getTournamentId());
            if (tournament == null) {
                continue;
            }
            TournamentUser tu = tourUserService.getTournamentUserByUserIdAndTOnlineId(user.getId(), to.getId());
            if (tu != null && tu.getIsGetReward() == Const.NOT_HAVE_GET_REWARD
                    && tourMessageService.hasReward(tu, to.getId(), tournament)) {
                num = num + 1;
            }
        }
        if (num > 10) {
            num = 10;
        }
        List<Integer> endTimeList = new ArrayList<Integer>();
        List<TournamentOnline> onlineList = tournamentOnlineService.getInProgressOnlineList();
        for (TournamentOnline to : onlineList) {
            int time = (int) (to.getEndTime() - System.currentTimeMillis() / 1000);
            if (time > 0) {
                endTimeList.add(time);
            }
        }

        trnBuilder.setRewardNum(num);
        trnBuilder.addAllEndTime(endTimeList);
        return trnBuilder.build();
    }

    private ResponseNotificationCommand getResponseNotificationCommand(List<CarView> carViewList) {
        ResponseNotificationCommand.Builder rnbuilder = ResponseNotificationCommand.newBuilder();
        String carId = "";
        StringBuffer buf = new StringBuffer();
        for (CarView carView : carViewList) {
            buf.append(carView.getCarId() + " ");

        }
        carId = buf.toString();
        rnbuilder.setContent("you have been buyed " + carId);
        rnbuilder.setDuration(3.0f);
        rnbuilder.setIconId(0);
        return rnbuilder.build();
    }

    private ResponsePopupCommand getResponsePopupCommand(com.ea.eamobile.nfsmw.model.Reward reward, int type,
            String content, int days, int tier) {
        ResponsePopupCommand.Builder rpbuilder = ResponsePopupCommand.newBuilder();

        if (type == Match.BIND_POPUP) {
            rpbuilder.setHeading(Const.GET_REWARD);
            rpbuilder.setPopScreen(1);
            rpbuilder.setText(ctaContentService.getCtaContent(CtaContentConst.BIND_REWARD).getContent()
                    .replace(CtaContentConst.REWARD_DISPLAYNAME_REPLACE, reward.getDisplayName()));
        } else if (type == Match.EVERYDAYRACE_POPUP) {
            rpbuilder.setHeading(Const.GET_REWARD);
            rpbuilder.setPopScreen(1);
            rpbuilder.setText(ctaContentService.getCtaContent(CtaContentConst.EVERYDAYRACE_POPUP).getContent()
                    .replace(CtaContentConst.DAYS_NUM_REPLACE, String.valueOf(days))
                    .replace(CtaContentConst.REWARD_DISPLAYNAME_REPLACE, reward.getDisplayName()));
        } else if (type == Match.TRACK_POPUP) {
            rpbuilder.setHeading(Const.GET_REWARD);
            rpbuilder.setPopScreen(2);
            rpbuilder.setText(ctaContentService.getCtaContent(CtaContentConst.TRACK_COMPLETE_POPUP).getContent()
                    .replace(CtaContentConst.TRACK_NAME_REPLACE, content)
                    .replace(CtaContentConst.REWARD_DISPLAYNAME_REPLACE, reward.getDisplayName()));
        } else if (type == Match.TIER_POPUP) {
            int nextTier = tier + 1;
            rpbuilder.setHeading(Const.GET_REWARD);
            rpbuilder.setPopScreen(2);
            rpbuilder.setText(ctaContentService.getCtaContent(CtaContentConst.TIER_COMPLETE_POPUP).getContent()
                    .replace(CtaContentConst.TIER_REPLACE, String.valueOf(tier))
                    .replace(CtaContentConst.NEXT_TIER_REPLACE, String.valueOf(nextTier))
                    .replace(CtaContentConst.REWARD_DISPLAYNAME_REPLACE, reward.getDisplayName()));
        } else if (type == Match.USER_VERSION_UPDATE_POPUP) {
            rpbuilder.setHeading(Const.GET_REWARD);
            rpbuilder.setPopScreen(1);
            rpbuilder.setText(reward.getDisplayName());
        }
        return rpbuilder.build();
    }

    private ResponseFeedCommand getResponseFeedCommand(long userId) {
        ResponseFeedCommand.Builder rfbuilder = ResponseFeedCommand.newBuilder();
        List<String> feedList = new ArrayList<String>();
        List<FeedContent> feedContents = feedContentService.getFeedContentList();
        for (FeedContent feedContent : feedContents) {
            feedList.add(feedContent.getContent());
        }
        // feedList.add("捷豹锦标赛开始啦！");
        // feedList.add("欢迎来到极品飞车的世界里");
        // feedList.add("3. However, a senior administration official said it was up to Republican leaders not to stand in the way of an agreement.");
        // feedList.add("4. Earlier this month the US National Highway Traffic Safety Administration said Toyota had agreed to pay $17m for allegedly failing to report a safety fault this year in two Lexus models in a timely manner. Other recalls have involved faulty window switches, fuel leaks and, most recently, steering wheels and water pumps. The company's reputation was badly tarnished by the repeated recalls and it lost its place as the world's biggest carmaker in 2011.However, the Japanese firm said earlier on Wednesday that it anticipated a 22% increase in group sales for 2012, reaching 9.7 million vehicles globally, and returning it to the position of biggest car manufacturer.");
        rfbuilder.addAllFeedContent(feedList);
        return rfbuilder.build();
    }

    private ResponseWeiboShareLocksCommand getResponseWeiboShareLocksCommand() {
        ResponseWeiboShareLocksCommand.Builder rwslbuilder = ResponseWeiboShareLocksCommand.newBuilder();

        rwslbuilder.setIsBindingWeiboShareOpen(systemConfigService
                .getWeiBoShareById(Const.BINDING_WEIBO_SHARE_OPEN_SYSTEMCONFIG_ID) == 1);
        rwslbuilder.setIsBuyCarShareOpen(systemConfigService
                .getWeiBoShareById(Const.BUY_CAR_SHARE_OPEN_SYSTEMCONFIG_ID) == 1);
        rwslbuilder.setIsCarUnlockInfoShareOpen(systemConfigService
                .getWeiBoShareById(Const.CAR_UNLOCK_INFO_SHARE_OPEN_SYSTEMCONFIG_ID) == 1);
        rwslbuilder
                .setIsJaguarShareOpen(systemConfigService.getWeiBoShareById(Const.JAGUAR_SHARE_OPEN_SYSTEMCONFIG_ID) == 1);
        rwslbuilder.setIsRaceRewardShareOpen(systemConfigService
                .getWeiBoShareById(Const.RACE_REWARD_SHARE_OPEN_SYSTEMCONFIG_ID) == 1);
        rwslbuilder.setIsSpeedWallShareOpen(systemConfigService
                .getWeiBoShareById(Const.SPEED_WALL_SHARE_OPEN_SYSTEMCONFIG_ID) == 1);
        rwslbuilder.setIsTournamentRewardShareOpen(systemConfigService
                .getWeiBoShareById(Const.TOURNAMENT_REWARD_SHARE_OPEN_SYSTEMCONFIG_ID) == 1);
        rwslbuilder.setIsTrackUnlockInfoShareOpen(systemConfigService
                .getWeiBoShareById(Const.TRACK_UNLOCK_INFO_SHARE_OPEN_SYSTEMCONFIG_ID) == 1);
        rwslbuilder.setIsUpdateConsumableShareOpen(systemConfigService
                .getWeiBoShareById(Const.UPDATE_CONSUMBLE_SHARE_OPEN_SYSTEMCONFIG_ID) == 1);
        return rwslbuilder.build();
    }

    private ResponseRegistJaguarCommand getResponseRegistJaguarCommand(User user) {
        ResponseRegistJaguarCommand.Builder rrjbuilder = ResponseRegistJaguarCommand.newBuilder();
        rrjbuilder.setLevelRaceCanUse(user.getIsWriteJaguar() == 1);
        return rrjbuilder.build();
    }

    private TierInfo getTierInfo(long userId) {
        TierInfo.Builder builder = TierInfo.newBuilder();
        User user = userService.getUser(userId);
        int tier = user != null ? user.getTier() : 0;
        int tierCount = trackService.getTierCount();
        builder.setTierIndex(tier);
        builder.setTierAmount(tierCount);
        // builder.addAllNewbieTierCheckList(getNewbieTierCheckList(userId));
        return builder.build();
    }

    private ResponseSystemCommand getResponseSystemCommand(String session, User user, int eventOptionVersion,
            int version) {
        ResponseSystemCommand.Builder rsbuilder = ResponseSystemCommand.newBuilder();
        List<EventOptionMessage> eventOptionMessageList = eventOptionMessageService.getEventOptionMessageList();
        rsbuilder.addAllEventOption(eventOptionMessageList);
        rsbuilder.setEnergyRecoveringStartTime(15 * 60 * 60);
        rsbuilder.setEnergyRecoveringPeriod(24 * 60 * 60);
        rsbuilder.setEnergyRecoveringNum(120);
        // rsbuilder.setEnergyRecoveringStartTime(0);
        // rsbuilder.setEnergyRecoveringPeriod(60);
        // rsbuilder.setEnergyRecoveringNum(1);
        rsbuilder.setEnergyMaxNum(Match.ENERGY_MAX);
        rsbuilder.addAllCtaParam(systemConfigService.getCtaParam());
        rsbuilder.addAllSpeedFactor(systemConfigService.getSpeedFactor());

        // MOD page config
        boolean modPopup = false;
        if ((user.getAccountStatus() & Const.IS_SHOWMOD) != 0) {
            modPopup = true;
        }
        SystemConfig config = configService.getSystemConfig(Const.MOD_CONFIG);
        if (config != null) {
            String status = config.getValue();
            if (status.equals(Const.MOD_OPEN) || status.equals(Const.MOD_POPUP)) {
                rsbuilder.setMessageUrl(ConfigUtil.NFSMW_URL + "/nfsmw/news?session=" + session + "&display="
                        + user.getDeviceType());
            }
            if (status.equals(Const.MOD_POPUP)) {
                modPopup = true;
            }
        }
        rsbuilder.setPopMOD(modPopup);
        rsbuilder.setNewMOD(newsService.getMaxTime());

        // add help page link
        String code = "1";// define help code
        rsbuilder.setIPSPFailedMessageUrl(ConfigUtil.NFSMW_URL + "/nfsmw/help?userId=" + user.getId() + "#" + code);
        int rentId = Const.RENT_JAGUAR_IPAD;
        int getId = Const.GET_JAGUAR_IPAD;
        int moreId = Const.JAGUAR_MORE_IPAD;
        if (user.getDeviceType() == Const.DEVICE_IS_IPHONE5) {
            rentId = Const.RENT_JAGUAR_IPHONE5;
            getId = Const.GET_JAGUAR_IPHONE5;
            moreId = Const.JAGUAR_MORE_IPHONE;
        } else if (user.getDeviceType() == Const.DEVICE_IS_IPHONE) {
            rentId = Const.RENT_JAGUAR_IPHONE;
            getId = Const.GET_JAGUAR_IPHONE;
            moreId = Const.JAGUAR_MORE_IPHONE;
        }
        String jaguarBaseUrl = ConfigUtil.NFSMW_URL + "/nfsmw/url?id=";
        rsbuilder.setRentJaguarUrl(jaguarBaseUrl + rentId);
        rsbuilder.setGetJaguarUrl(jaguarBaseUrl + getId);
        rsbuilder.setJaguarInfoUrl(jaguarBaseUrl + moreId);
        rsbuilder.addAllServers(ConfigUtil.servers);
        rsbuilder.setIpspPurchaseItemCallBackType(IapConst.IPSP_NEW_TYPE);
        return rsbuilder.build();
    }

    private ResponseStoreDetailCommand getResponseStoreDetailCommand(long userId) {
        ResponseStoreDetailCommand.Builder builder = ResponseStoreDetailCommand.newBuilder();
        List<ItemMessage> itemList = new ArrayList<ItemMessage>();
        List<Purchase> cacheList = purchaseService.getPurchaseList();
        List<Purchase> purchaseList = new ArrayList<Purchase>();
        purchaseList.addAll(cacheList);
        if (purchaseList != null) {
            for (Purchase purchase : purchaseList) {
                int currentTime = (int) (System.currentTimeMillis() / 1000);
                if ((currentTime < purchase.getLimitTime()) || purchase.getLimitTime() == CarConst.NO_TIME_LIMIT) {
                    ItemMessage.Builder itembuilder = ItemMessage.newBuilder();
                    if (purchase.getBuyCount() == -1) {
                        itembuilder.setBuyCount(purchase.getBuyCount());
                    } else {
                        if (purchase.getBuyCount() - storeMessageService.getBuyedNum(userId, purchase.getId()) == 0) {
                            continue;
                        }
                        itembuilder.setBuyCount(purchase.getBuyCount()
                                - storeMessageService.getBuyedNum(userId, purchase.getId()));
                    }
                    if (purchase.getLimitTime() == CarConst.NO_TIME_LIMIT) {
                        itembuilder.setBuyTimeLimit(-1);
                    } else {
                        itembuilder.setBuyTimeLimit(purchase.getLimitTime() - currentTime);
                    }
                    itembuilder.setDiscount(purchase.getIsDiscount() == 1);
                    itembuilder.setDiscountNum(purchase.getDiscountNum());
                    itembuilder.setDiscountPrice(purchase.getPrice());
                    itembuilder.setItemId(purchase.getId());
                    itembuilder.setItemType(purchase.getItemType());
                    itembuilder.setName(purchase.getName());
                    itembuilder.setPrice(purchase.getDisplayPrice());
                    itembuilder.setPriceType(purchase.getPriceType());
                    itembuilder.setSellFlag(purchase.getIsBestSell() * 4 + purchase.getIsHot() * 2
                            + purchase.getIsNew() * 1);
                    itemList.add(itembuilder.build());
                }
            }
        }
        builder.addAllItems(itemList);
        return builder.build();

    }

    private ResponseTournamentDetailCommand getResponseTournamentDetailCommand(int tournamentOnlineId, long userId,
            int rankType) throws SQLException {
        ResponseTournamentDetailCommand.Builder builder = ResponseTournamentDetailCommand.newBuilder();
        TournamentOnline to = tournamentOnlineService.getTournamentOnline(tournamentOnlineId);
        User u = userService.getUser(userId);
        if (to == null || u == null) {
            return null;
        }
        Tournament t = tournamentService.getTournament(to.getTournamentId());
        if (t == null) {
            return null;
        }
        TournamentUser tu = tourUserService.getTournamentUserByUserIdAndTOnlineId(userId, tournamentOnlineId);
        if (tu == null) {
            return null;
        }
        int groupId = tournamentGroupService.getGroupIdForUser(t.getId(), u.getLevel());
        TournamentGroup tg = tournamentGroupService.getTournamentGroup(groupId);
        if (tg == null) {
            return null;
        }
        // List<TournamentDetailRewardMessage> tdrm = tournamentDetailMessageService
        // .getTournamentDetailRewardMessageList(tg.getId());
        List<TournamentDetailMessage> ldms = Collections.emptyList();
        int classId = 0;

        classId = tu.getClassId();
        // get user rank info
        int selfrank = 9999;
        if (tu.getResult() != 0) {

            selfrank = modeRankTypeHandlerFactory.create(t.getType()).getRank(tournamentOnlineId, tu);
        }
        ldms = buildTournamentDetailMessages(tournamentOnlineId, classId, rankType);// ////////////
        if (selfrank > 10) {

            TournamentDetailMessage.Builder tdmbuilder = TournamentDetailMessage.newBuilder();
            tdmbuilder.setHeadIndex(u.getHeadIndex());
            tdmbuilder.setHeadUrl(u.getHeadUrl());
            tdmbuilder.setName(u.getName());
            if (rankType == 0) {
                tdmbuilder.setRaceTime(tu.getResult());
            } else if (rankType == 1) {
                tdmbuilder.setRaceTime(tu.getAverageSpeed());
            }
            tdmbuilder.setRank(selfrank);
            ldms.add(tdmbuilder.build());

        }
        if (tg.getIsProvide() == 1) {
            builder.setHotRideCarId(tg.getCarProvide());
            builder.setIshasCar(1);
        } else if (tg.getIsProvide() == 0) {
            List<String> carIds = tournamentCarLimitService.getTournamentCarLimit(groupId);
            builder.addAllCarIDs(carIds);
            builder.setIshasCar(userCarService.hasLimitCar(userId, carIds) ? 1 : 0);// /////////////////
        }
        if (t.getNoConsumble() == 0) {
            builder.setIsNotConsumable(false);
        } else if (t.getNoConsumble() == 1) {
            builder.setIsNotConsumable(true);
        }
        builder.addAllDetailReward(buildTourDetailRewardMessages(groupId));// ////////
        builder.setTournamentSignUpPrice(tg.getFee());
        builder.setTournamentGroup(tg.getName() + " " + classId);
        builder.addAllTournamentDetail(ldms);
        builder.setTournamentDescription(tg.getMatchDescribe());
        builder.addAllTournamentCarName(carLimitService.getCarLimitListByGroupId(tg.getId()));
        return builder.build();
    }

    private List<TournamentDetailRewardMessage> buildTourDetailRewardMessages(int tourGroupId) {
        List<TournamentDetailRewardMessage> result = Collections.emptyList();
        List<TournamentReward> tourRewards = tourRewardService.getTournamentRewardListByGroupId(tourGroupId);
        if (tourRewards != null && tourRewards.size() > 0) {
            result = new ArrayList<TournamentDetailRewardMessage>();
            for (TournamentReward tourReward : tourRewards) {
                TournamentDetailRewardMessage.Builder builder = TournamentDetailRewardMessage.newBuilder();
                com.ea.eamobile.nfsmw.model.Reward reward = rewardService.getReward(tourReward.getRewardId());
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

    public List<TournamentDetailMessage> buildTournamentDetailMessages(int onlineId, int classId, int rankType) {
        List<TournamentDetailMessage> result = new ArrayList<TournamentDetailMessage>();
        List<TournamentLeaderboard> leaderboards = tourLeaderboardService.getLeaderboard(onlineId, classId, rankType);
        if (leaderboards != null) {
            for (int i = 0; i < leaderboards.size(); i++) {
                TournamentLeaderboard leaderboard = leaderboards.get(i);
                TournamentDetailMessage.Builder builder = TournamentDetailMessage.newBuilder();
                builder.setHeadIndex(leaderboard.getHeadIndex());
                builder.setHeadUrl(leaderboard.getHeadUrl());
                builder.setName(leaderboard.getUserName());
                builder.setRaceTime(leaderboard.getResult());
                builder.setRank(i + 1);
                result.add(builder.build());
            }
        }
        return result;
    }

    private ResponseTournamentCommand getResponseTournamentCommand(long userId) throws SQLException {
        User user = userService.getUser(userId);
        ResponseTournamentCommand.Builder builder = ResponseTournamentCommand.newBuilder();
        List<TournamentOnline> tos = tournamentOnlineService.getInProgressOnlineList();
        tos = filterCanShowOnlineList(tos, user);
        if (tos.size() == 0) {
            // no more tournament online
            return null;
        }
        ComparatorTournamentOnline ct = new ComparatorTournamentOnline();
        Collections.sort(tos, ct);

        for (int i = 0; i < tos.size(); i++) {
            TournamentMessage tm = tourMessageService.buildTournamentMessage(0, user, tos.get(i));
            if (tm != null) {
                builder.addTournament(tm);
            }
        }
        builder.setType(0);
        return builder.build();
    }

    private List<TournamentOnline> filterCanShowOnlineList(List<TournamentOnline> tournamentOnlines, User user) {
        List<TournamentOnline> result = new ArrayList<TournamentOnline>();
        for (TournamentOnline tournamentOnline : tournamentOnlines) {
            Tournament t = tournamentService.getTournament(tournamentOnline.getTournamentId());
            if (t == null) {
                continue;
            }
            if (user.getTier() >= t.getTierLimit()) {
                result.add(tournamentOnline);
            }
        }
        return result;
    }

    public void pushDailyRaceInfoCommand(Builder resposeBuilder, User user, int isFinish, int modeId) {
        DailyRaceRecord dailyRaceRecord = new DailyRaceRecord();
        Date nowTime = new Date();
        if (modeId == 0) {
            DailyRaceRecord originDailyRaceRecord = dailyRaceRecordService.getDailyRaceRecordByTierAndDate(
                    user.getTier(), DateUtil.setToDayStartTime(nowTime).getTime() / 1000);
            if (originDailyRaceRecord != null) {
                dailyRaceRecord = originDailyRaceRecord;
            } else {
                int carIndex = 0;
                int modeIndex = 0;
                String carId = "";
                String dispalyName = "";
                List<DailyRaceCarId> dailyRaceCarIds = dailyRaceCarIdService
                        .getDailyRaceCarIdListByTier(user.getTier());
                List<DailyRaceModeId> dayRaceModeIds = dailyRaceModeIdService.getDailyRaceModeIdList();
                DailyRaceCarId dailyRaceCarId = NumberUtil.randomList(dailyRaceCarIds);
                DailyRaceModeId dailyRaceModeId = NumberUtil.randomList(dayRaceModeIds);
                if (dailyRaceCarId != null && dailyRaceModeId != null) {
                    carIndex = dailyRaceCarId.getId();
                    modeIndex = dailyRaceModeId.getId();
                    carId = dailyRaceCarId.getCarId();
                    dispalyName = dailyRaceCarId.getCarDisplayName();
                }
                dailyRaceRecord.setCarIndex(carIndex);
                dailyRaceRecord.setCreateTime(DateUtil.setToDayStartTime(nowTime).getTime() / 1000);
                dailyRaceRecord.setTier(user.getTier());
                dailyRaceRecord.setModeIndex(modeIndex);
                dailyRaceRecord.setCarId(carId);
                dailyRaceRecord.setDisplayName(dispalyName);
                dailyRaceRecordService.insert(dailyRaceRecord);
            }
        }
        dailyRaceRecord = dailyRaceRecordService.getDailyRaceRecordByTierAndDate(user.getTier(), DateUtil
                .setToDayStartTime(nowTime).getTime() / 1000);

        ResponseChallengeMatchInfoCommand cmd = getDailyRaceInfoCommand(user, dailyRaceRecord, isFinish);
        resposeBuilder.setChallengeMatchInfoCommand(cmd);

    }

    private ResponseChallengeMatchInfoCommand getDailyRaceInfoCommand(User user, DailyRaceRecord dailyRaceRecord,
            int isFinish) {
        ResponseChallengeMatchInfoCommand.Builder builder = ResponseChallengeMatchInfoCommand.newBuilder();
        Date nowTime = new Date();
        UserDailyRace userDailyRace = userDailyRaceService.getUserDailyRace(user.getId());
        if (userDailyRace == null) {
            userDailyRace = new UserDailyRace();
            userDailyRace.setDuraDayNum(0);
            userDailyRace.setLeftTimes(1);
            userDailyRace.setLastMatchDate((int) (System.currentTimeMillis() / 1000));
            userDailyRace.setUserId(user.getId());
            userDailyRaceService.insert(userDailyRace);
        }
        int challengeDay = 1;
        int leftTimes = 1;
        int leftSeconds = (int) (Const.ONE_DAY_SECONDS + (DateUtil.setToDayStartTime(nowTime).getTime() - nowTime
                .getTime()) / 1000);
        long realLastMatchDate = userDailyRace.getLastMatchDate();
        // today have been matched
        if (DateUtil.intervalDays(nowTime, new Date(realLastMatchDate * 1000l)) == 0) {
            leftTimes = userDailyRace.getLeftTimes();
            challengeDay = userDailyRace.getDuraDayNum();

            challengeDay = challengeDay + 1;
            if (challengeDay > 7) {
                challengeDay = challengeDay - 7;
            }

        } else if (DateUtil.intervalDays(nowTime, new Date(realLastMatchDate * 1000l)) == 1) {
            challengeDay = userDailyRace.getDuraDayNum() + 1;
            userDailyRace.setLeftTimes(1);
            userDailyRaceService.update(userDailyRace);
            if (challengeDay > 7) {
                challengeDay = challengeDay - 7;
            }
        } else {
            userDailyRace.setLeftTimes(1);
            userDailyRaceService.update(userDailyRace);
        }
        int nowRewardId = dailyRaceRewardService.getDailyRaceReward(user.getLevel(), challengeDay, isFinish);
        // int nextDuraNum=challengeDay+1;
        // if(nextDuraNum>7){
        // nextDuraNum=nextDuraNum-7;
        // }
        // int nextRewardId=dailyRaceRewardService.getDailyRaceReward(user.getLevel(), nextDuraNum, 1);
        // DailyRaceCarId dailyRaceCarId = dailyRaceCarIdService.getDailyRaceCarId(dailyRaceRecord
        // .getCarIndex());
        DailyRaceModeId dailyRaceModeId = dailyRaceModeIdService.getDailyRaceModeId(dailyRaceRecord.getModeIndex());

        builder.setReward(getRewardById(nowRewardId));
        // builder.setNextReward(getRewardById(nextRewardId));
        builder.setCarId(dailyRaceRecord.getCarId());
        builder.setModeId(dailyRaceModeId.getModeId());
        builder.setPassTime(dailyRaceModeId.getPassTime());
        builder.setEventName(dailyRaceModeId.getModeName());
        builder.setRemainTimes(leftTimes);
        builder.setChallengeDays(challengeDay);
        builder.setCarLimitDisplayString(dailyRaceRecord.getDisplayName());
        builder.setExpireSeconds(leftSeconds);

        return builder.build();
    }

    private Reward getRewardById(int rewardId) {
        Reward.Builder builder = Reward.newBuilder();
        com.ea.eamobile.nfsmw.model.Reward reward = rewardService.getReward(rewardId);
        if (reward == null) {
            return null;
        }
        builder.setDisplayStrings(reward.getDisplayName());
        builder.setMoney(reward.getMoney());
        builder.setMostwantedNum(reward.getMostwantedNum());
        builder.setRmb(reward.getGold());
        builder.setRpNum(reward.getRpNum());
        return builder.build();
    }

    private ResponseGarageCommand getResponseUserCarInfoCommand(List<CarView> carViewList, long userId)
            throws SQLException {
        ResponseGarageCommand.Builder rgcbuilder = ResponseGarageCommand.newBuilder();

        List<CarData> list = new ArrayList<CarData>();
        for (CarView view : carViewList) {
            CarData carInfo = carDataMessageService.buildCarData(view, userId);
            list.add(carInfo);
        }
        rgcbuilder.addAllCarDatas(list);
        return rgcbuilder.build();

    }

}
