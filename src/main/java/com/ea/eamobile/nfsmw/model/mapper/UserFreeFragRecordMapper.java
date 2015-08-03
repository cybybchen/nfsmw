package com.ea.eamobile.nfsmw.model.mapper;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.UserFreeFragRecord;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Apr 23 17:38:22 CST 2013
 * @since 1.0
 */
public interface UserFreeFragRecordMapper {

    public UserFreeFragRecord getUserFreeFragRecord(@Param("userId") long userId, @Param("carId") String carId,
            @Param("level") int level);

    public int insert(UserFreeFragRecord userFreeFragRecord);

    public void update(UserFreeFragRecord userFreeFragRecord);

}