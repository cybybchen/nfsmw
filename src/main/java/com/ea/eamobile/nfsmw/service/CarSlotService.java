package com.ea.eamobile.nfsmw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.constants.CarConst;
import com.ea.eamobile.nfsmw.model.CarSlot;
import com.ea.eamobile.nfsmw.model.mapper.CarSlotMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Nov 02 16:09:46 CST 2012
 * @since 1.0
 */
@Service
public class CarSlotService {

    @Autowired
    private CarSlotMapper carSlotMapper;

    public CarSlot getCarSlot(int id) {
        CarSlot slot = (CarSlot) InProcessCache.getInstance().get("getCarSlot."+id);
        if(slot==null){
            slot = carSlotMapper.getCarSlot(id);
            InProcessCache.getInstance().set("getCarSlot."+id,slot);
        }
        return slot;
    }

    public CarSlot getFirstCarSlot(String carId) {
        CarSlot ret = (CarSlot) InProcessCache.getInstance().get("getFirstCarSlot." + carId);
        if (ret != null) {
            return ret;
        }
        ret = carSlotMapper.getFirstCarSlot(carId);
        InProcessCache.getInstance().set("getFirstCarSlot." + carId, ret);
        return ret;
    }

    public List<CarSlot> getCarSlotList() {
        @SuppressWarnings("unchecked")
        List<CarSlot> ret = (List<CarSlot>) InProcessCache.getInstance().get("getCarSlotList");
        if (ret != null) {
            return ret;
        }
        ret = carSlotMapper.getCarSlotList();
        InProcessCache.getInstance().set("getCarSlotList", ret);
        return ret;
    }

    public int insert(CarSlot carSlot) {
        return carSlotMapper.insert(carSlot);
    }

    public void update(CarSlot carSlot) {
        carSlotMapper.update(carSlot);
    }

    public void deleteById(int id) {
        carSlotMapper.deleteById(id);
    }

    public List<CarSlot> getCarSlotListByCarId(String carId) {
        List<CarSlot> result = new ArrayList<CarSlot>();
        List<CarSlot> slots = getCarSlotList();
        for (CarSlot slot : slots) {
            if (slot.getCarId().equals(carId)) {
                result.add(slot);
            }
        }
        return result;
    }

    public CarSlot getNextSlot(CarSlot carSlot) {
        CarSlot next = null;
        if (carSlot == null || carSlot.getLevel() >= CarConst.SLOT_MAX_LEVEL) {
            return next;
        }
        List<CarSlot> slots = getCarSlotListByCarId(carSlot.getCarId());
        for (CarSlot slot : slots) {
            if (slot.getSn() == carSlot.getSn() && slot.getLevel() == carSlot.getLevel() + 1) {
                next = slot;
                break;
            }
        }
        return next;
    }

}