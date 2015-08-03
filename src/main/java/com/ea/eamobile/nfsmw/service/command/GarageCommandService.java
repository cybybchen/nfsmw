package com.ea.eamobile.nfsmw.service.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.ProfileComparisonType;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserRaceAction;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.CarData;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBuyCarCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestGarageCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestUpgradeSlotCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestUseChartletCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseBuyCarCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseGarageCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseUpgradeSlotCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseUseChartletCommand;
import com.ea.eamobile.nfsmw.service.CarDataMessageService;
import com.ea.eamobile.nfsmw.service.CarSlotConsumableService;
import com.ea.eamobile.nfsmw.service.CarSlotService;
import com.ea.eamobile.nfsmw.service.UserCarService;
import com.ea.eamobile.nfsmw.service.UserCarSlotService;
import com.ea.eamobile.nfsmw.service.UserChartletService;
import com.ea.eamobile.nfsmw.service.UserRaceActionService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.view.CarView;
import com.ea.eamobile.nfsmw.view.ResultInfo;

/**
 * 车库功能command 1.车辆列表 2.查看车辆详细信息 3.购买车辆 4.插槽解锁/升级 5.使用/购买贴图
 * 
 * @author ma.ruofei@ea.com
 * 
 */
@Service
public class GarageCommandService {

    private static final Logger log = LoggerFactory.getLogger(GarageCommandService.class);

    @Autowired
    private UserCarService userCarService;
    @Autowired
    private UserChartletService userChartletService;
    @Autowired
    private UserCarSlotService userSlotService;
    @Autowired
    private CarSlotService slotService;
    @Autowired
    private CarSlotConsumableService slotConsumbleService;
    @Autowired
    private UserService userService;
    @Autowired
    private PushCommandService pushService;
    @Autowired
    private CarDataMessageService carDataMessageService;
	@Autowired
	private UserRaceActionService userRaceActionService;

    /**
     * 获取车库
     * 
     * @param user
     *            .getId()
     * @param reqcmd
     * @return
     */
    public ResponseGarageCommand getGarageCommand(User user, RequestGarageCommand reqcmd) throws SQLException {
        ResponseGarageCommand.Builder builder = ResponseGarageCommand.newBuilder();
        List<CarData> carInfos = getCarDatas(user.getId());
        builder.addAllCarDatas(carInfos);
        return builder.build();
    }

    private List<CarData> getCarDatas(long userId) throws SQLException {
        // 获取用户车库车辆试图的列表 要取全部车不可能为空
        List<CarView> viewList = userCarService.getGarageCarList(userId);
        log.debug("get user car view list,userid={},list = {}", userId, viewList);
        // 封装成要返回的CarData
        List<CarData> list = new ArrayList<CarData>();
        for (CarView view : viewList) {
            CarData carInfo = carDataMessageService.buildCarData(view, userId);
            list.add(carInfo);
        }
        return list;
    }

    /**
     * 买车
     * 
     * @param userId
     * @param reqcmd
     * @return
     */
    public ResponseBuyCarCommand getBuyCarCommand(User user, RequestBuyCarCommand reqcmd,
            Commands.ResponseCommand.Builder responseBuilder) throws SQLException {
        long userId = user.getId();
        ResponseBuyCarCommand.Builder builder = ResponseBuyCarCommand.newBuilder();
        String carId = reqcmd.getCarId();

        ResultInfo result = userCarService.buyCar(userId, carId, false);
        builder.setSuccess(result.isSuccess());
        builder.setMessage(result.getMessage());
        if (result.isSuccess()) {
            user = userService.getUser(userId);
            pushService.pushUserInfoCommand(responseBuilder, user);
            pushService.pushUserCarInfoCommand(responseBuilder, userCarService.getGarageCarListByCarId(userId, carId),
                    userId);
            // pushService.pushNotificationCommand(responseBuilder, userCarService.getGarageCarListByCarId(userId,
            // carId));
            // profile: clear cache
            userCarService.clearUserBestCar(userId);
            // refresh garage score
            updateGarageScore(userId);
        }
        // if(reqcmd.getCarId().equals(CarConst.TIER_ZERO_CAR)){
        // pushService.pushTierInfoCommand(responseBuilder, userId);
        // }
        return builder.build();
    }

    
//    public ResponseSendCar getSendCarCommand(User user, RequestSendCar reqcmd, Commands.ResponseCommand.Builder responseBuilder) throws SQLException {
//    	long userId = user.getId();
//        ResponseSendCar.Builder builder = ResponseSendCar.newBuilder();
//        String carId = reqcmd.getCarId();
//
//        ResultInfo result = userCarService.buyCar(userId, carId, false);
//        builder.setSuccess(result.isSuccess());
//        builder.setMessage(result.getMessage());
//        if (result.isSuccess()) {
//            user = userService.getUser(userId);
//            pushService.pushUserInfoCommand(responseBuilder, user);
//            pushService.pushUserCarInfoCommand(responseBuilder, userCarService.getGarageCarListByCarId(userId, carId),
//                    userId);
//            // pushService.pushNotificationCommand(responseBuilder, userCarService.getGarageCarListByCarId(userId,
//            // carId));
//            // profile: clear cache
//            userCarService.clearUserBestCar(userId);
//            // refresh garage score
//            updateGarageScore(userId);
//        }
//        // if(reqcmd.getCarId().equals(CarConst.TIER_ZERO_CAR)){
//        // pushService.pushTierInfoCommand(responseBuilder, userId);
//        // }
//        return builder.build();
//    }
    /**
     * 开启升级插槽
     * 
     * @param userId
     * @param reqcmd
     * @return
     */
    public ResponseUpgradeSlotCommand getUpgradeSlotCommand(User user, RequestUpgradeSlotCommand reqcmd,
            Commands.ResponseCommand.Builder responseBuilder) throws SQLException {
        long userId = user.getId();
        ResponseUpgradeSlotCommand.Builder builder = ResponseUpgradeSlotCommand.newBuilder();
        ResultInfo result = userSlotService.upgrade(userId, reqcmd.getSlotId(), reqcmd.getUserCarId());
        builder.setSuccess(result.isSuccess());
        builder.setMessage(result.getMessage());

        builder.setRemainTime(0);

        User changedUser = userService.getUser(userId);
        pushService.pushUserInfoCommand(responseBuilder, changedUser);
        pushService.pushUserCarInfoCommand(responseBuilder,
                userCarService.getGarageCarListByCarId(userId, reqcmd.getUserCarId()), userId);

        // refresh garage score
        updateGarageScore(userId);
        
        return builder.build();
    }

    /**
     * 使用贴图
     * 
     * @param userId
     * @param reqcmd
     * @return
     */
    public ResponseUseChartletCommand getUseChartletCommand(User user, RequestUseChartletCommand reqcmd,
            Commands.ResponseCommand.Builder responseBuilder) throws SQLException {
        long userId = user.getId();
        ResponseUseChartletCommand.Builder builder = ResponseUseChartletCommand.newBuilder();
        ResultInfo result = userChartletService.useChartlet(userId, reqcmd.getChartletId(), reqcmd.getCarId());
        builder.setSuccess(result.isSuccess());
        builder.setMessage(result.getMessage());
        // refresh this car data TODO pushService
        CarView carView = userCarService.getUserCarView(userId, reqcmd.getCarId());
        CarData carData = carDataMessageService.buildCarData(carView, userId);
        ResponseGarageCommand.Builder rgcbuilder = ResponseGarageCommand.newBuilder();
        rgcbuilder.addCarDatas(carData);
        responseBuilder.setGarageCommand(rgcbuilder.build());

        if (result.isSuccess()) {
            user = userService.getUser(userId);
            pushService.pushUserInfoCommand(responseBuilder, user);

            // refresh garage score
            updateGarageScore(userId);
        }
        return builder.build();
    }

    /**
     * 更新车库总评分，购买车辆、插槽解锁/升级、使用/购买贴图时更新，更新存入UserRaceAction表
     * 
     * @param userId
     * @return
     */
    private void updateGarageScore(long userId) {
        int score = userCarService.getUserCarOwnedTotalScore(userId);
        int valueId = ProfileComparisonType.GARAGE_SCORE.getIndex();
        UserRaceAction userRaceAction = userRaceActionService.getUserRaceActionByUserIdAndValueId(userId, valueId);
        if (userRaceAction != null) {
        	userRaceAction.setValue(score);
        	userRaceActionService.update(userRaceAction);
        } else {
            userRaceActionService.insert(userId, valueId, score);
        }
    }
}
