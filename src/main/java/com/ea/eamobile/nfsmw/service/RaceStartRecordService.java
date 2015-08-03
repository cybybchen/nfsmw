package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.RaceStartRecord;
import com.ea.eamobile.nfsmw.model.mapper.RaceStartRecordMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 05 19:46:24 CST 2013
 * @since 1.0
 */
 @Service
public class RaceStartRecordService {

    @Autowired
    private RaceStartRecordMapper raceStartRecordMapper;
    
    public RaceStartRecord getRaceStartRecord(long userId){
        return raceStartRecordMapper.getRaceStartRecord(userId);
    }

    public List<RaceStartRecord> getRaceStartRecordList(){
        return raceStartRecordMapper.getRaceStartRecordList();
    }

    public int insert(RaceStartRecord raceStartRecord){
        return raceStartRecordMapper.insert(raceStartRecord);
    }

    public void update(RaceStartRecord raceStartRecord){
		    raceStartRecordMapper.update(raceStartRecord);    
    }

    public void deleteById(int id){
        raceStartRecordMapper.deleteById(id);
    }

}