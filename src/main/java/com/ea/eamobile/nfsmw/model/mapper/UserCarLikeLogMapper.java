package com.ea.eamobile.nfsmw.model.mapper;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.UserCarLikeLog;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Apr 01 13:51:32 CST 2013
 * @since 1.0
 */
public interface UserCarLikeLogMapper {

    public UserCarLikeLog getUserCarLikeLog(long id);

    public UserCarLikeLog getUserCarLikeLogByUserIdAndUserCarId(@Param("userId")long userId, @Param("userCarId")long userCarId);

    public int insert(UserCarLikeLog userCarLikeLog);



}