package com.ea.eamobile.nfsmw.model.mapper;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.UserSpendActivityRecord;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Apr 18 19:32:45 CST 2013
 * @since 1.0
 */
public interface UserSpendActivityRecordMapper {

    public UserSpendActivityRecord getUserSpendActivityRecord(@Param("userId") long userId,
            @Param("spendActivityId") int spendActivityId);

    public int insert(UserSpendActivityRecord userSpendActivityRecord);

    public void update(UserSpendActivityRecord userSpendActivityRecord);

}