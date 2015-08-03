package com.ea.eamobile.nfsmw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.UserFreeFragRecord;
import com.ea.eamobile.nfsmw.model.mapper.UserFreeFragRecordMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Apr 23 17:38:22 CST 2013
 * @since 1.0
 */

@Service
public class UserFreeFragRecordService {

    @Autowired
    private UserFreeFragRecordMapper userFreeFragRecordMapper;

    public UserFreeFragRecord getUserFreeFragRecord(long userId, String carId, int level) {
        return userFreeFragRecordMapper.getUserFreeFragRecord(userId, carId, level);
    }

    public int insert(UserFreeFragRecord userFreeFragRecord) {
        return userFreeFragRecordMapper.insert(userFreeFragRecord);
    }

    public void update(UserFreeFragRecord userFreeFragRecord) {
        userFreeFragRecordMapper.update(userFreeFragRecord);

    }

}