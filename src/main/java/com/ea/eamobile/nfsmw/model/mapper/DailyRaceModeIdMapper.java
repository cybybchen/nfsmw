package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.DailyRaceModeId;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Dec 10 14:34:30 CST 2012
 * @since 1.0
 */
public interface DailyRaceModeIdMapper {


    public DailyRaceModeId getDailyRaceModeId(int id);


    public List<DailyRaceModeId> getDailyRaceModeIdList();

    public int insert(DailyRaceModeId dailyRaceModeId);

    public void update(DailyRaceModeId dailyRaceModeId);

    public void deleteById(int id);

}