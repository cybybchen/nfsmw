package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.CarChartlet;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Nov 30 14:53:59 CST 2012
 * @since 1.0
 */
public interface CarChartletMapper {

  
    public CarChartlet getCarChartlet(int id);


    public List<CarChartlet> getCarChartletList();

    public int insert(CarChartlet carChartlet);

    public void update(CarChartlet carChartlet);

    public void deleteById(int id);

}