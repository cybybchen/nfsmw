package com.ea.eamobile.nfsmw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.CheatRecord;
import com.ea.eamobile.nfsmw.model.mapper.CheatRecordMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Feb 25 14:27:47 CST 2013
 * @since 1.0
 */
 @Service
public class CheatRecordService {

    @Autowired
    private CheatRecordMapper cheatRecordMapper;
    

    public int insert(CheatRecord cheatRecord){
        return cheatRecordMapper.insert(cheatRecord);
    }


}