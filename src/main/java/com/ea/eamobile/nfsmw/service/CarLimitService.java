package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.CarLimit;
import com.ea.eamobile.nfsmw.model.mapper.CarLimitMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Nov 02 15:12:07 CST 2012
 * @since 1.0
 */
@Service
public class CarLimitService {

    @Autowired
    private CarLimitMapper carLimitMapper;

    public CarLimit getCarLimit(int id) {
        CarLimit ret = (CarLimit) InProcessCache.getInstance().get("getCarLimit."+id);
        if (ret != null) {
            return ret;
        }
        ret = carLimitMapper.getCarLimit(id);
        InProcessCache.getInstance().set("getCarLimit."+id, ret);
        return ret;
    }

    public List<CarLimit> getCarLimitList() {
        return carLimitMapper.getCarLimitList();
    }

    public List<String> getCarLimitListByGroupId(int groupId) {
        @SuppressWarnings("unchecked")
        List<String> ret = (List<String>) InProcessCache.getInstance().get("getCarLimitListByGroupId."+groupId);
        if (ret != null) {
            return ret;
        }
        ret = carLimitMapper.getCarLimitListByGroupId(groupId);
        InProcessCache.getInstance().set("getCarLimitListByGroupId."+groupId, ret);
        return ret;
    }

    public int insert(CarLimit carLimitDescribe) {
        return carLimitMapper.insert(carLimitDescribe);
    }

    public void update(CarLimit carLimitDescribe) {
        carLimitMapper.update(carLimitDescribe);
    }

    public void deleteById(int id) {
        carLimitMapper.deleteById(id);
    }

}