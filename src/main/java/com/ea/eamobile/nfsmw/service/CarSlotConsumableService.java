package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.CarSlotConsumable;
import com.ea.eamobile.nfsmw.model.mapper.CarSlotConsumableMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Nov 02 16:09:46 CST 2012
 * @since 1.0
 */
@Service
public class CarSlotConsumableService {

    @Autowired
    private CarSlotConsumableMapper carSlotConsumableMapper;

    public List<CarSlotConsumable> getCarSlotConsumableList() {
        return carSlotConsumableMapper.getCarSlotConsumableList();
    }

    public List<CarSlotConsumable> getCarSlotConsumableListBySlotId(int slotId) {
        @SuppressWarnings("unchecked")
        List<CarSlotConsumable> ret = (List<CarSlotConsumable>) InProcessCache.getInstance().get(
                "getCarSlotConsumableListBySlotId." + slotId);
        if (ret != null) {
            return ret;
        }
        ret = carSlotConsumableMapper.getCarSlotConsumableListBySlotId(slotId);
        InProcessCache.getInstance().set("getCarSlotConsumableListBySlotId." + slotId, ret);
        return ret;
    }

    public CarSlotConsumable getCarSlotConsumableBySlotIdAndConType(int slotId, int conType) {
        CarSlotConsumable ret = (CarSlotConsumable) InProcessCache.getInstance().get(
                "getCarSlotConsumableBySlotIdAndConType." + slotId + "." + conType);
        if (ret != null) {
            return ret;
        }
        ret = carSlotConsumableMapper.getCarSlotConsumableListBySlotIdAndConType(slotId, conType);
        InProcessCache.getInstance().set("getCarSlotConsumableBySlotIdAndConType." + slotId + "." + conType, ret);
        return ret;
    }

    public int insert(CarSlotConsumable carSlotConsumable) {
        return carSlotConsumableMapper.insert(carSlotConsumable);
    }

    public void update(CarSlotConsumable carSlotConsumable) {
        carSlotConsumableMapper.update(carSlotConsumable);
    }

    public void deleteById(int id) {
        carSlotConsumableMapper.deleteById(id);
    }

}