package com.ea.eamobile.nfsmw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.CheatUserInfoRecord;
import com.ea.eamobile.nfsmw.model.mapper.CheatUserInfoRecordMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Tue May 28 17:32:10 CST 2013
 * @since 1.0
 */
 @Service
public class CheatUserInfoRecordService {

    @Autowired
    private CheatUserInfoRecordMapper cheatUserInfoRecordMapper;
    
    
    public int insert(CheatUserInfoRecord cheatUserInfoRecord){
        return cheatUserInfoRecordMapper.insert(cheatUserInfoRecord);
    }


}