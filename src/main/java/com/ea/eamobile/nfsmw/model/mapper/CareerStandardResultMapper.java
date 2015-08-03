package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.CareerStandardResult;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 05 19:31:37 CST 2013
 * @since 1.0
 */
public interface CareerStandardResultMapper {


    public CareerStandardResult getCareerStandardResult(int modeType);

    public List<CareerStandardResult> getCareerStandardResultList();

    public int insert(CareerStandardResult careerStandardResult);

    public void update(CareerStandardResult careerStandardResult);

    public void deleteById(int id);

}