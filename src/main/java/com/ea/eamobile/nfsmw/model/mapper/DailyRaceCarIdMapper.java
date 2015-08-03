package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.DailyRaceCarId;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Dec 10 14:34:30 CST 2012
 * @since 1.0
 */
public interface DailyRaceCarIdMapper {


    public DailyRaceCarId getDailyRaceCarId(int id);

    public List<DailyRaceCarId> getDailyRaceCarIdList();

    public List<DailyRaceCarId> getDailyRaceCarIdListByTier(int tier);

    public int insert(DailyRaceCarId dailyRaceCarId);

    public void update(DailyRaceCarId dailyRaceCarId);

    public void deleteById(int id);

}