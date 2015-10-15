package com.ea.eamobile.nfsmw.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.Car;
import com.ea.eamobile.nfsmw.model.CarExt;
import com.ea.eamobile.nfsmw.model.mapper.CarMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Nov 02 16:09:46 CST 2012
 * @since 1.0
 */
@Service
public class CarService {

    @Autowired
    private CarMapper carMapper;
    @Autowired
    private CarExtService carExtService;

    public Car getCar(String id) {
        Car ret = (Car) InProcessCache.getInstance().get("car_getCar_new_" + id);

        if (ret != null) {
            return ret;
        }
        ret = carMapper.getCar(id);
        InProcessCache.getInstance().set("car_getCar_new_" + id, ret);
        CarExt carExt = carExtService.getCarExt(id);
        if (carExt != null) {
            ret.setEndTime(carExt.getEndTime());
            ret.setStartTime(carExt.getStartTime());
            ret.setPrice(carExt.getPrice());
            ret.setPriceType(carExt.getPriceType());
        }
        return ret;
    }
    
    public Car getCar(int primary_id) {
        Car ret = (Car) InProcessCache.getInstance().get("car_getCar_new_" + primary_id);

        if (ret != null) {
            return ret;
        }
        ret = carMapper.getCarByPrimaryId(primary_id);
        InProcessCache.getInstance().set("car_getCar_new_" + primary_id, ret);
        CarExt carExt = carExtService.getCarExt(ret.getId());
        if (carExt != null) {
            ret.setEndTime(carExt.getEndTime());
            ret.setStartTime(carExt.getStartTime());
            ret.setPrice(carExt.getPrice());
            ret.setPriceType(carExt.getPriceType());
        }
        return ret;
    }

    public List<Car> getCarList() {
        @SuppressWarnings("unchecked")
        List<Car> ret = (List<Car>) InProcessCache.getInstance().get("car_carlist_new");
        if (ret != null) {
            return ret;
        }
        ret = carMapper.getCarList();
        List<Car> result = new ArrayList<Car>();
        for (Car car : ret) {
            CarExt carExt = carExtService.getCarExt(car.getId());
            if (carExt != null) {
                car.setEndTime(carExt.getEndTime());
                car.setStartTime(carExt.getStartTime());
                car.setPrice(carExt.getPrice());
                car.setPriceType(carExt.getPriceType());
            }
            result.add(car);
        }
        InProcessCache.getInstance().set("car_carlist_new", result);
        return result;
    }

    public int insert(Car car) {
        InProcessCache.getInstance().expire("car_carlist_new");
        return carMapper.insert(car);
    }

    public void update(Car car) {
        carMapper.update(car);
    }

    public void deleteById(int id) {
        carMapper.deleteById(id);
    }

    /**
     * 获取当前tier可解锁车辆列表 注：解锁车辆必须同时满足tier+ [min,maxmwNum] 说明：min=0适用于tier有变化的解锁，min>0适用于mw有变化的解锁
     * 
     * @param tier
     * @param mwNum
     * @return
     */
    public List<Car> getUnlockCars(int tier, int maxMwNum, int minMxNum) {
        List<Car> result = Collections.emptyList();
        List<Car> list = getCarList();
        for (Car car : list) {
            if (car.getTier() <= tier && maxMwNum >= car.getUnlockMwNum() && minMxNum < car.getUnlockMwNum()) {
                if (result.size() == 0) {
                    result = new ArrayList<Car>();
                }
                result.add(car);
            }
        }
        return result;
    }

    public List<Car> getUnlockCarsByTier(int tier, int userMwnum) {
        List<Car> result = Collections.emptyList();
        List<Car> list = getCarList();
        for (Car car : list) {
            if (car.getTier() == tier && userMwnum >= car.getUnlockMwNum()) {
                if (result.size() == 0) {
                    result = new ArrayList<Car>();
                }
                result.add(car);
            }
        }
        return result;
    }

}