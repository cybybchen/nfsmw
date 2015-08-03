package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.model.CarExt;
import com.ea.eamobile.nfsmw.model.mapper.CarExtMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 19 18:23:55 CST 2013
 * @since 1.0
 */
@Service
public class CarExtService {

    @Autowired
    private CarExtMapper carExtMapper;
    @Autowired
    private MemcachedClient cache;

    private void clear(String id) {
        cache.delete(CacheKey.CAR_EXT_BYID + id);
        cache.delete(CacheKey.CAR_EXT_LIST);
    }

    public CarExt getCarExt(String carId) {
        CarExt carExt = (CarExt) cache.get(CacheKey.CAR_EXT_BYID + carId);
        if (carExt == null) {
            carExt = carExtMapper.getCarExt(carId);
            cache.set(CacheKey.CAR_EXT_BYID + carId, carExt, MemcachedClient.HOUR);
        }
        return carExt;
    }

    @SuppressWarnings("unchecked")
    public List<CarExt> getCarExtList() {
        List<CarExt> list = (List<CarExt>) cache.get(CacheKey.CAR_EXT_LIST);
        if (list == null) {
            list = carExtMapper.getCarExtList();
            cache.set(CacheKey.CAR_EXT_LIST, list, MemcachedClient.HOUR);
        }
        return list;

    }

    public void update(CarExt carExt) {
        clear(carExt.getCarId());
        carExtMapper.update(carExt);
    }

}