package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.CarMaxSpeed;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Feb 22 14:39:54 CST 2013
 * @since 1.0
 */
public interface CarMaxSpeedMapper {


    public CarMaxSpeed getCarMaxSpeed(String carId);

    public List<CarMaxSpeed> getCarMaxSpeedList();

}