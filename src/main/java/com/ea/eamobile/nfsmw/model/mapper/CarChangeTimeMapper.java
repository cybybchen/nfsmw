package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;
import com.ea.eamobile.nfsmw.model.CarChangeTime;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Apr 11 12:17:35 CST 2013
 * @since 1.0
 */
public interface CarChangeTimeMapper {

    public List<CarChangeTime> getCarChangeTimeListByTime(int time);

    public int insert(CarChangeTime carChangeTime);

    public List<CarChangeTime> getCarChangeTimeList();

}