package com.ea.eamobile.nfsmw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.UserSpendActivityRecord;
import com.ea.eamobile.nfsmw.model.mapper.UserSpendActivityRecordMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Apr 18 19:32:45 CST 2013
 * @since 1.0
 */
@Service
public class UserSpendActivityRecordService {

    @Autowired
    private UserSpendActivityRecordMapper userSpendActivityRecordMapper;

    public UserSpendActivityRecord getUserSpendActivityRecord(long userId, int spendActivityId) {
        return userSpendActivityRecordMapper.getUserSpendActivityRecord(userId, spendActivityId);
    }

    public int insert(UserSpendActivityRecord userSpendActivityRecord) {
        return userSpendActivityRecordMapper.insert(userSpendActivityRecord);
    }

    public void update(UserSpendActivityRecord userSpendActivityRecord) {
        userSpendActivityRecordMapper.update(userSpendActivityRecord);
    }

}