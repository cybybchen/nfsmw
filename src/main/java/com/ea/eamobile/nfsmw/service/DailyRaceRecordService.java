package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.DailyRaceRecord;
import com.ea.eamobile.nfsmw.model.mapper.DailyRaceRecordMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Dec 10 14:34:32 CST 2012
 * @since 1.0
 */
 @Service
public class DailyRaceRecordService {

    @Autowired
    private DailyRaceRecordMapper dailyRaceRecordMapper;
    
    public DailyRaceRecord getDailyRaceRecord(int id){
        return dailyRaceRecordMapper.getDailyRaceRecord(id);
    }
    
    public DailyRaceRecord getDailyRaceRecordByTierAndDate(int tier,long createTime){
        return dailyRaceRecordMapper.getDailyRaceRecordByTierAndDate(tier, createTime);
    }

    public List<DailyRaceRecord> getDailyRaceRecordList(){
        return dailyRaceRecordMapper.getDailyRaceRecordList();
    }

    public int insert(DailyRaceRecord dailyRaceRecord){
        return dailyRaceRecordMapper.insert(dailyRaceRecord);
    }

    public void update(DailyRaceRecord dailyRaceRecord){
		    dailyRaceRecordMapper.update(dailyRaceRecord);    
    }

    public void deleteById(int id){
        dailyRaceRecordMapper.deleteById(id);
    }

}