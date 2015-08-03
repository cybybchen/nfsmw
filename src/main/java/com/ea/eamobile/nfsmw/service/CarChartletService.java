package com.ea.eamobile.nfsmw.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.CarChartlet;
import com.ea.eamobile.nfsmw.model.mapper.CarChartletMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Nov 30 14:53:59 CST 2012
 * @since 1.0
 */
@Service
public class CarChartletService {
    



    @Autowired
    private CarChartletMapper carChartletMapper;

    public CarChartlet getCarChartlet(int id) {


        CarChartlet ret = (CarChartlet) InProcessCache.getInstance().get("carchartlet_getCarChartlet+"+id);

        if (ret != null) {
            return ret;
        }
        ret = carChartletMapper.getCarChartlet(id);

        InProcessCache.getInstance().set("carchartlet_getCarChartlet+"+id, ret);


        return ret;
    }

    public List<CarChartlet> getCarChartletList() {
        @SuppressWarnings("unchecked")

        List<CarChartlet> ret = (List<CarChartlet>) InProcessCache.getInstance().get("carchartlet_getcarchartletlist");

        if (ret != null) {
            return ret;
        }
        ret = carChartletMapper.getCarChartletList();
        InProcessCache.getInstance().set("carchartlet_getcarchartletlist", ret);

        return ret;
    }

    public int insert(CarChartlet carChartlet) {
        return carChartletMapper.insert(carChartlet);
    }

    public void update(CarChartlet carChartlet) {
        carChartletMapper.update(carChartlet);
    }

    public void deleteById(int id) {
        carChartletMapper.deleteById(id);
    }

    /**
     * 根据carid获取车辆所有贴图
     * @param carId
     * @return
     */
    public List<CarChartlet> getChartletsByCarId(String carId) {
        List<CarChartlet> lets = Collections.emptyList();
        List<CarChartlet> list = getCarChartletList();
        if (list != null && list.size() > 0) {
            lets = new ArrayList<CarChartlet>();
            for (CarChartlet let : list) {
                if (let.getCarId().equals(carId)) {
                    lets.add(let);
                }
            }
        }
        return lets;
    }

}