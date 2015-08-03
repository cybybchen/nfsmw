package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.CarExt;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 19 18:23:55 CST 2013
 * @since 1.0
 */
public interface CarExtMapper {

    public CarExt getCarExt(String carId);

    public List<CarExt> getCarExtList();

    public void update(CarExt carExt);

}