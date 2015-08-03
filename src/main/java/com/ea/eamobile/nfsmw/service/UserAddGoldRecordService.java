package com.ea.eamobile.nfsmw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.UserAddGoldRecord;
import com.ea.eamobile.nfsmw.model.mapper.UserAddGoldRecordMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 05 16:01:12 CST 2013
 * @since 1.0
 */
@Service
public class UserAddGoldRecordService {

    @Autowired
    private UserAddGoldRecordMapper userAddGoldRecordMapper;

    public int insert(UserAddGoldRecord userAddGoldRecord) {
        return userAddGoldRecordMapper.insert(userAddGoldRecord);
    }

}