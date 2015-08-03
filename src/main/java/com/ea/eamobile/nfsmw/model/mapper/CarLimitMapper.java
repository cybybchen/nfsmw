package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.CarLimit;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Nov 02 15:12:07 CST 2012
 * @since 1.0
 */
public interface CarLimitMapper {


    public CarLimit getCarLimit(int id);


    public List<CarLimit> getCarLimitList();

    public List<String> getCarLimitListByGroupId(int tournamentGroupId);

    public int insert(CarLimit carLimit);

    public void update(CarLimit carLimit);

    public void deleteById(int id);

}