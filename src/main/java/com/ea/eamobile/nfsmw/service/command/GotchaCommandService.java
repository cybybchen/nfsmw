package com.ea.eamobile.nfsmw.service.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.constants.GotchaType;
import com.ea.eamobile.nfsmw.model.Car;
import com.ea.eamobile.nfsmw.model.GotchaFragment;
import com.ea.eamobile.nfsmw.model.GotchaRecord;
import com.ea.eamobile.nfsmw.model.Reward;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands.GotchaItemInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestGotchaCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseCommand.Builder;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseGotchaCommand;
import com.ea.eamobile.nfsmw.service.CarService;
import com.ea.eamobile.nfsmw.service.GotchaRecordService;
import com.ea.eamobile.nfsmw.service.RewardService;
import com.ea.eamobile.nfsmw.service.UserCarService;
import com.ea.eamobile.nfsmw.service.gotcha.GotchaFragmentService;
import com.ea.eamobile.nfsmw.service.gotcha.GotchaService;
import com.ea.eamobile.nfsmw.view.GotchaView;

@Service
public class GotchaCommandService extends BaseCommandService {
    private static final Logger logger = LoggerFactory.getLogger(GotchaCommandService.class);
    @Autowired
    private GotchaService gotchaService;
    @Autowired
    private GotchaFragmentService partService;
    @Autowired
    private CarService carService;
    @Autowired
    private RewardService rewardService;
    @Autowired
    private UserCarService userCarService;
    @Autowired
    private GotchaRecordService gotchaRecordService;

    /**
     * 保存最新的3条gotcha信息
     */
    private ArrayBlockingQueue<String> contentQueue = new ArrayBlockingQueue<String>(Const.GOTCHA_CONTENT_SIZE);
    private static final String SPLIT = "，";

    public ResponseGotchaCommand getResponseCommand(RequestGotchaCommand reqcmd, User user, Builder responseBuilder) {
    	logger.info(">>>>>>>>>>>>>getResponseCommand(RequestGotchaCommand");
        ResponseGotchaCommand.Builder builder = ResponseGotchaCommand.newBuilder();
        GotchaRecord gotchaRecord = new GotchaRecord();
        int level = reqcmd.getLevel();
        String carId = reqcmd.getCarId();
        String itemInfo = "fail";
        GotchaView view = gotchaService.gotcha(user, level, carId, gotchaRecord);
        builder.setGlobalMessage(itemInfo);
        if (view == null) {
        	logger.info(">>>>>>>>>>>view==null");
            return builder.build();
        }

        buildItemInfos(builder, view, gotchaRecord, user);
        logger.info(">>>>>>>>>>>buildItemInfos");
        user = view.getUser();
        // push refresh data
        pushService.pushUserInfoCommand(responseBuilder, user);
        try {
            pushService.pushUserCarInfoCommand(responseBuilder,
                    userCarService.getGarageCarListByCarId(user.getId(), carId), user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return builder.build();
    }

    private void buildItemInfos(ResponseGotchaCommand.Builder builder, GotchaView view, GotchaRecord gotchaRecord,
            User user) {
        if (view == null) {
            return;
        }
        List<GotchaItemInfo> gotchaItemList = new ArrayList<GotchaItemInfo>();
        if (view.getFragmentNum() > 0) {
            GotchaFragment part = partService.getGotchaPart(view.getFragmentId());
            if (part != null) {
                gotchaItemList.add(buildGotchaItemInfo(GotchaType.FRAGMENT, view.getFragmentNum(), part.getName()));
            }
        }
        // reward
        List<Integer> rewards = view.getRewards();
        int energy = 0;
        int money = 0;
        int gold = 0;
        if (rewards != null && rewards.size() > 0) {
            for (Integer rid : rewards) {
                Reward reward = rewardService.getReward(rid);
                if (reward == null) {
                    continue;
                }
                energy += reward.getEnergy();
                money += reward.getMoney();
                gold += reward.getGold();
            }
        }
        if (gold > 0) {
            gotchaItemList.add(buildGotchaItemInfo(GotchaType.GOLD, gold, null));
        }
        if (energy > 0) {
            gotchaItemList.add(buildGotchaItemInfo(GotchaType.ENERGY, energy, null));
        }
        if (money > 0) {
            gotchaItemList.add(buildGotchaItemInfo(GotchaType.MONEY, money, null));
        }
        gotchaRecord.setGetGasNum(energy);
        gotchaRecord.setGetGoldNum(gold);
        gotchaRecord.setGetMoneyNum(money);
        gotchaRecord.setTime((int) (System.currentTimeMillis() / 1000));
        gotchaRecordService.insert(gotchaRecord);
        String itemMessage = buildItemMessage(gotchaItemList, view);
        builder.addAllGotchaItems(gotchaItemList);
        builder.setGlobalMessage(saveAndBuildGlobalMessage(itemMessage));
        return;
    }

    /**
     * 构建用户本次gotcha物品信息
     * 
     * @param gotchaItemList
     * @param view
     * @return
     */
    private String buildItemMessage(List<GotchaItemInfo> gotchaItemList, GotchaView view) {
        StringBuilder message = new StringBuilder("恭喜").append("["+view.getUser().getName()+"]").append("获得了");
        if (view.isLuckdog()) {
            Car car = carService.getCar(view.getCarId());
            message.append(car.getName()).append(SPLIT);
        }
        if (!gotchaItemList.isEmpty()) {
            for (GotchaItemInfo info : gotchaItemList) {
                GotchaType type = GotchaType.getType(info.getType());
                // 单位是否后置
                if (type.isSuffixUnit()) {
                    message.append(info.getNumber()).append(type.getUnit());
                } else {
                    message.append(type.getUnit()).append(info.getNumber());
                }
                // 是否需要额外单位名称
                if (type.needExtraUnit()) {
                    message.append(info.getName());
                }
                message.append(SPLIT);
            }
        }
        String result = message.toString();
        if (result.endsWith(SPLIT)) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    /**
     * 构建返回的gotcha信息 如果名称是变量，不使用枚举name
     * 
     * @param gtype
     * @param num
     * @param paramName
     * @return
     */
    private GotchaItemInfo buildGotchaItemInfo(GotchaType gtype, int num, String paramName) {
        GotchaItemInfo.Builder infoBuilder = GotchaItemInfo.newBuilder();
        infoBuilder.setType(gtype.getType());
        infoBuilder.setName(paramName == null ? gtype.getName() : paramName);
        infoBuilder.setNumber(num);
        return infoBuilder.build();
    }

    /**
     * 保存最新的gotcha信息并返回信息列表字符串
     * 
     * @param itemInfo
     * @return
     */
    private String saveAndBuildGlobalMessage(String itemInfo) {
        if (contentQueue.isEmpty()) {
            boolean succ = contentQueue.offer(itemInfo);
            if(!succ){
                logger.debug("add iteminfo fail.");
            }
            return itemInfo;
        }
        if (contentQueue.size() >= Const.GOTCHA_CONTENT_SIZE) {
            contentQueue.poll();
        }
        boolean succ = contentQueue.offer(itemInfo);
        if(!succ){
            logger.debug("add iteminfo fail.");
        }
        StringBuilder message = new StringBuilder("");
        for (String str : contentQueue) {
            message.append(str).append(" ");
        }
        return message.toString().trim();
    }

}
