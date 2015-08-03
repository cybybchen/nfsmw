package com.ea.eamobile.nfsmw.service;

import java.util.List;
import com.ea.eamobile.nfsmw.model.UserGetSpendRewardRecord;
import com.ea.eamobile.nfsmw.model.mapper.UserGetSpendRewardRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Apr 18 19:32:45 CST 2013
 * @since 1.0
 */
@Service
public class UserGetSpendRewardRecordService {

    @Autowired
    private UserGetSpendRewardRecordMapper userGetSpendRewardRecordMapper;

    public List<UserGetSpendRewardRecord> getUserGetSpendRewardRecordList(long userId, int spendActivityId) {
        return userGetSpendRewardRecordMapper.getUserGetSpendRewardRecordList(userId, spendActivityId);
    }

    public int insert(UserGetSpendRewardRecord userGetSpendRewardRecord) {
        return userGetSpendRewardRecordMapper.insert(userGetSpendRewardRecord);
    }

    public UserGetSpendRewardRecord getUserGetSpendRewardRecordByUserIdAndRewardId(long userId, int spendRewardId,
            int spendActivityId) {
        return userGetSpendRewardRecordMapper.getUserGetSpendRewardRecordByUserIdAndRewardId(userId, spendRewardId,
                spendActivityId);
    }

}