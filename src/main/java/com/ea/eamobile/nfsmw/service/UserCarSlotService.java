package com.ea.eamobile.nfsmw.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.CarConst;
import com.ea.eamobile.nfsmw.constants.CtaContentConst;
import com.ea.eamobile.nfsmw.model.CarSlot;
import com.ea.eamobile.nfsmw.model.UserCar;
import com.ea.eamobile.nfsmw.model.UserCarSlot;
import com.ea.eamobile.nfsmw.model.handler.Inthandler;
import com.ea.eamobile.nfsmw.model.handler.UserCarSlotListHandler;
import com.ea.eamobile.nfsmw.model.mapper.UserCarSlotMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;
import com.ea.eamobile.nfsmw.utils.RWSplit;
import com.ea.eamobile.nfsmw.view.CarSlotView;
import com.ea.eamobile.nfsmw.view.ResultInfo;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Nov 02 16:09:46 CST 2012
 * @since 1.0
 */
@Service
public class UserCarSlotService {
    
    private static final Logger log = LoggerFactory.getLogger(UserCarSlotService.class);

    @Autowired
    private UserCarSlotMapper userCarSlotMapper;
    @Autowired
    private CarSlotService slotService;
    @Autowired
    private PayService payService;
    @Autowired
    private UserCarService userCarService;
    @Autowired
    private CtaContentService ctaContentService;
    @Autowired
    private MemcachedClient cache;

    private void clear(long userCarId) {
        cache.delete(CacheKey.USER_CAR_SLOTS + userCarId);
    }

    /**
     * 根据用户的car唯一id 取此车对应的插槽
     * @param userCarId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<UserCarSlot> getSlotListByUserCarId(long userCarId) {
        List<UserCarSlot> list = (List<UserCarSlot>) cache.get(CacheKey.USER_CAR_SLOTS + userCarId);
        if(list==null){
            list = userCarSlotMapper.getSlotListByUserCarId(userCarId);
            if(list==null){
                //空也缓存一个 避免总是查db
                list = new ArrayList<UserCarSlot>();
            }
            cache.set(CacheKey.USER_CAR_SLOTS + userCarId, list,MemcachedClient.DAY);
        }
        return list;
    }

    public int insert(UserCarSlot userCarSlot) {
        clear(userCarSlot.getUserCarId());
        return userCarSlotMapper.insert(userCarSlot);
    }

    public void update(UserCarSlot userCarSlot) {
        clear(userCarSlot.getUserCarId());
        userCarSlotMapper.update(userCarSlot);
    }

    /**
     * only test clear data use
     * @param id
     */
    public void deleteById(int id) {
        UserCarSlot slot = userCarSlotMapper.getUserCarSlot(id);
        if(slot!=null){
            clear(slot.getUserCarId());
        }
        userCarSlotMapper.deleteById(id);
    }

    /**
     * 开启或升级插槽
     * 
     * @param userId
     * @param slotId
     * @return
     */
    public ResultInfo upgrade(long userId, int slotId, String carId) {
        ResultInfo result = null;
        CarSlot currentCarSlot = slotService.getCarSlot(slotId);
        if (currentCarSlot == null) {
            return new ResultInfo(false, ctaContentService.getCtaContent(CtaContentConst.SLOT_IS_NOT_EXIST)
                    .getContent());
        }
        if (currentCarSlot.getLevel() >= CarConst.SLOT_MAX_LEVEL) {
            return new ResultInfo(false, ctaContentService.getCtaContent(CtaContentConst.SLOT_IS_MAX_LEVEL)
                    .getContent());
        }
        long userCarId = 0;
        UserCar userCar = userCarService.getUserCarByUserIdAndCarId(userId, carId);
        if (userCar != null) {
            userCarId = userCar.getId();
        }
        // 判断车辆和插槽是否匹配
        if (!isSameCar(currentCarSlot.getCarId(), userCarId)) {
            return new ResultInfo(false, ctaContentService.getCtaContent(CtaContentConst.SLOT_IS_UNFIT_CAR)
                    .getContent());
        }
        UserCarSlot userSlot = getUserSlot(userCarId, slotId);
        boolean isUpgrade = false;
        // 空是开启 否则是升级
        if (userSlot == null) {
            // 判断是否跳过了前面的升级
            if (currentCarSlot.getLevel() != 1) {
                return new ResultInfo(false, ctaContentService.getCtaContent(CtaContentConst.SLOT_UP_LEVEL_IS_LOCK)
                        .getContent());
            }
            // 判断是否已经升级过了
            if (hasUpgraded(userCarId, currentCarSlot)) {
                return new ResultInfo(false, ctaContentService.getCtaContent(CtaContentConst.SLOT_ALREADY_HAVE)
                        .getContent());
            }
        } else {
            currentCarSlot = slotService.getNextSlot(currentCarSlot);
            isUpgrade = true;
        }
        result = payService.buy(currentCarSlot, userId);
        if (result.isSuccess()) {
            if (isUpgrade) {
                userSlot.setSlotId(currentCarSlot.getId());
                userSlot.setCreateTime((int) (System.currentTimeMillis() / 1000));
                update(userSlot);
            } else {
                userSlot = buildUserSlot(currentCarSlot, userCarId);
                insert(userSlot);
            }
        }
        return result;
    }

    private boolean hasUpgraded(long userCarId, CarSlot currentCarSlot) {
        List<UserCarSlot> slots = getSlotListByUserCarId(userCarId);
        for (UserCarSlot userSlot : slots) {
            CarSlot slot = slotService.getCarSlot(userSlot.getSlotId());
            if (slot.getSn() == currentCarSlot.getSn() && slot.getLevel() > currentCarSlot.getLevel()) {
                return true;
            }
        }
        return false;
    }

    private boolean isSameCar(String carId, long userCarId) {
        return userCarService.isTheSameCar(carId, userCarId);
    }

    private UserCarSlot getUserSlot(long userCarId, int slotId) {
        List<UserCarSlot> list = getSlotListByUserCarId(userCarId);
        for (UserCarSlot slot : list) {
            if (slot.getSlotId() == slotId) {
                return slot;
            }
        }
        return null;
    }

    private UserCarSlot buildUserSlot(CarSlot carSlot, long userCarId) {
        UserCarSlot userSlot = new UserCarSlot();
        userSlot.setSlotId(carSlot.getId());
        userSlot.setCreateTime((int) (System.currentTimeMillis() / 1000));
        userSlot.setUserCarId(userCarId);
        return userSlot;
    }

    public void initLevelOneCarSlot(UserCar userCar) {
        if(userCar==null){
            return;
        }
        CarSlot firstCarsolt = slotService.getFirstCarSlot(userCar.getCarId());

        UserCarSlot userSlot = new UserCarSlot();
        userSlot.setConsumableId(1);
        userSlot.setCreateTime((int) (System.currentTimeMillis() / 1000));
        userSlot.setSlotId(firstCarsolt.getId());
        userSlot.setUserCarId(userCar.getId());
        insert(userSlot);
        return;
    }

    // TODO cache
    /**
     * 取用户车辆的插槽信息 只需要按组别显示最高级，以及加成数值、描述
     * 
     * @param userCar
     * @return
     */
    public List<CarSlotView> getUserCarSlotViewList(UserCar userCar) {
        List<CarSlotView> result = new ArrayList<CarSlotView>();
        // 取车辆的插槽列表字典数据
        List<CarSlot> carSlots = slotService.getCarSlotListByCarId(userCar.getCarId());
        Map<Integer, CarSlotView> carSlotMap = new HashMap<Integer, CarSlotView>();
        // TODO refactor
        for (CarSlot slot : carSlots) {
            UserCarSlot userSlot = getUserSlot(userCar.getId(), slot.getId());
            if (userSlot == null && slot.getLevel() != 1) {
                // 用户没有说明没开启 直接插入最低等级的
                continue;
            }

            CarSlotView view = buildCarSlotView(slot, userSlot);

            CarSlotView mapView = carSlotMap.get(slot.getSn());
            if (mapView == null) {
                carSlotMap.put(slot.getSn(), view);
            } else {
                if (view.getLevel() > mapView.getLevel()) {
                    carSlotMap.put(slot.getSn(), view);
                }
            }

        }
        result.addAll(carSlotMap.values());
        if (result.size() == 3 && result.get(1).getStatus() == CarConst.SLOT_CLOSE) {
            result.get(2).setStatus(CarConst.SLOT_LOCKED);
            result.get(2).setDescription(CarConst.SLOT_CAN_NOT_UNLOCK);
        }
        return result;
    }

    private CarSlotView buildCarSlotView(CarSlot slot, UserCarSlot userSlot) {
        CarSlotView view = new CarSlotView();
        view.setSlotId(slot.getId());
        view.setLevel(slot.getLevel());
        int remainTime = 0;
        if (userSlot != null) {
            remainTime = (int) (System.currentTimeMillis() / 1000 - userSlot.getCreateTime());
        }
        view.setDescription(slot.getDescription());
        view.setRemainTime(remainTime);
        int status = getSlotStatus(slot, userSlot);
        view.setStatus(status);
        if (status == CarConst.SLOT_CLOSE) {
            view.setDescription(CarConst.SLOT_CAN_UNLOCK);
        }
        if (slot.getLevel() == 1 && status == CarConst.SLOT_CLOSE) {
            view.setNextAddScore(slot.getScore());
        } else {
            view.setNextAddScore(slot.getNextAddScore());
        }
        view.setScore(slot.getScore());
        return view;
    }

    /**
     * 取插槽的显示状态
     * 
     * @param slot
     * @return
     */
    private int getSlotStatus(CarSlot slot, UserCarSlot userSlot) {
        if (userSlot == null) {
            return CarConst.SLOT_CLOSE;
        } else {
            return CarConst.SLOT_UPGRADE;
        }
    }

    public List<UserCarSlot> getSlotListLimit(int from, int limit) {
        try {
            QueryRunner run = new QueryRunner(RWSplit.getInstance().getBackupReadDataSource());
            return run.query("select id,user_car_id,slot_id from user_car_slot where id >= ? and id< ?",
                    new UserCarSlotListHandler(), from,limit);
        } catch (SQLException e) {
            log.error("read user car slot db err",e);
        }
        return Collections.emptyList();
    }
    
    public int getMaxId(){
        try {
            QueryRunner run = new QueryRunner(RWSplit.getInstance().getBackupReadDataSource());
            Integer max = run.query("select max(id) from user_car_slot",
                    new Inthandler());
            if(max!=null){
                return max;
            }
        } catch (SQLException e) {
            log.error("read user car slot db err",e);
        }
        return 0;
    }
    public int getFirstId(){
        try {
            QueryRunner run = new QueryRunner(RWSplit.getInstance().getBackupReadDataSource());
            Integer max = run.query("select id from user_car_slot limit 1",
                    new Inthandler());
            if(max!=null){
                return max;
            }
        } catch (SQLException e) {
            log.error("read user car slot db err",e);
        }
        return 0;
    }

}