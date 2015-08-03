package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.DailyRaceRecord;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Dec 10 14:34:30 CST 2012
 * @since 1.0
 */
public interface DailyRaceRecordMapper {

    public DailyRaceRecord getDailyRaceRecord(int id);

    public DailyRaceRecord getDailyRaceRecordByTierAndDate(@Param("tier")int tier,@Param("createTime")long createTime);
    
    public List<DailyRaceRecord> getDailyRaceRecordList();

    public int insert(DailyRaceRecord dailyRaceRecord);

    public void update(DailyRaceRecord dailyRaceRecord);

    public void deleteById(int id);

}