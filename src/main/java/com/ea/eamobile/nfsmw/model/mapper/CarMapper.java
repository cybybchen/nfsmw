package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.Car;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Nov 02 16:09:45 CST 2012
 * @since 1.0
 */
public interface CarMapper {

    

    public Car getCar(String id);
    
    public Car getCarByPrimaryId(int primary_id);

    public List<Car> getCarList();

    public int insert(Car car);

    public void update(Car car);

    public void deleteById(int id);

}