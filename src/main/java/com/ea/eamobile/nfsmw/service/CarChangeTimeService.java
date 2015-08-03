package com.ea.eamobile.nfsmw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.model.CarChangeTime;
import com.ea.eamobile.nfsmw.model.mapper.CarChangeTimeMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Apr 11 12:17:35 CST 2013
 * @since 1.0
 */
@Service
public class CarChangeTimeService {

    @Autowired
    private CarChangeTimeMapper carChangeTimeMapper;
    @Autowired
    private MemcachedClient cache;

    public List<CarChangeTime> getCarChangeTimeListByTime(int time) {
        List<CarChangeTime> result = new ArrayList<CarChangeTime>();
        List<CarChangeTime> carChangeTimes = getCarChangeTimeList();
        for (CarChangeTime carChangeTime : carChangeTimes) {
            if (time < carChangeTime.getTime()) {
                result.add(carChangeTime);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<CarChangeTime> getCarChangeTimeList() {
        List<CarChangeTime> list = (List<CarChangeTime>) cache.get(CacheKey.CAR_CHANGE_TIME_LIST);
        if (list == null) {
            list = carChangeTimeMapper.getCarChangeTimeList();
            cache.set(CacheKey.CAR_CHANGE_TIME_LIST, list, MemcachedClient.HOUR);
        }
        return list;
    }

    public int insert(CarChangeTime carChangeTime) {
        cache.delete(CacheKey.CAR_CHANGE_TIME_LIST);
        return carChangeTimeMapper.insert(carChangeTime);
    }

}