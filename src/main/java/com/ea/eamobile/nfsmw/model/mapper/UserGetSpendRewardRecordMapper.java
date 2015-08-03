package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.UserGetSpendRewardRecord;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Apr 18 19:32:45 CST 2013
 * @since 1.0
 */
public interface UserGetSpendRewardRecordMapper {

    public List<UserGetSpendRewardRecord> getUserGetSpendRewardRecordList(@Param("userId") long userId,
            @Param("spendActivityId") int spendActivityId);

    public UserGetSpendRewardRecord getUserGetSpendRewardRecordByUserIdAndRewardId(@Param("userId") long userId,
            @Param("spendRewardId") int spendRewardId, @Param("spendActivityId") int spendActivityId);

    public int insert(UserGetSpendRewardRecord userGetSpendRewardRecord);

}