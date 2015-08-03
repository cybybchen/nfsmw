package com.ea.eamobile.nfsmw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.CarMaxSpeed;
import com.ea.eamobile.nfsmw.model.mapper.CarMaxSpeedMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Feb 22 14:39:54 CST 2013
 * @since 1.0
 */
@Service
public class CarMaxSpeedService {

    @Autowired
    private CarMaxSpeedMapper carMaxSpeedMapper;

    public CarMaxSpeed getCarMaxSpeed(String carId) {
        CarMaxSpeed ret = (CarMaxSpeed) InProcessCache.getInstance().get("getCarMaxSpeed."+carId);
        if (ret != null) {
            return ret;
        }
        ret = carMaxSpeedMapper.getCarMaxSpeed(carId);
        InProcessCache.getInstance().set("getCarMaxSpeed."+carId, ret);
        return ret;
    }

    public List<CarMaxSpeed> getCarMaxSpeedList() {
        List<CarMaxSpeed> result = carMaxSpeedMapper.getCarMaxSpeedList();
        if (result == null) {
            result = new ArrayList<CarMaxSpeed>();
        }
        return result;
    }

}