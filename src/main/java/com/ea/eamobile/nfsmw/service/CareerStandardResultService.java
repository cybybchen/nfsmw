package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.CareerStandardResult;
import com.ea.eamobile.nfsmw.model.mapper.CareerStandardResultMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 05 19:31:37 CST 2013
 * @since 1.0
 */
@Service
public class CareerStandardResultService {

    @Autowired
    private CareerStandardResultMapper careerStandardResultMapper;

    public CareerStandardResult getCareerStandardResult(int modeType) {
        CareerStandardResult ret = (CareerStandardResult) InProcessCache.getInstance().get("getCareerStandardResult."+modeType);
        if (ret != null) {
            return ret;
        }
        ret = careerStandardResultMapper.getCareerStandardResult(modeType);
        InProcessCache.getInstance().set("getCareerStandardResult."+modeType, ret);
        return ret;
    }

    public List<CareerStandardResult> getCareerStandardResultList() {
        @SuppressWarnings("unchecked")
        List<CareerStandardResult> ret = (List<CareerStandardResult>) InProcessCache.getInstance().get("getCareerStandardResultList");
        if (ret != null) {
            return ret;
        }
        ret = careerStandardResultMapper.getCareerStandardResultList();
        InProcessCache.getInstance().set("getCareerStandardResultList", ret);
        return ret;
    }

    public int insert(CareerStandardResult careerStandardResult) {
        return careerStandardResultMapper.insert(careerStandardResult);
    }

    public void update(CareerStandardResult careerStandardResult) {
        careerStandardResultMapper.update(careerStandardResult);
    }

    public void deleteById(int id) {
        careerStandardResultMapper.deleteById(id);
    }

}