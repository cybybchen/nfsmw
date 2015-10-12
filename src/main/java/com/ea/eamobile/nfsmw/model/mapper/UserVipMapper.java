package com.ea.eamobile.nfsmw.model.mapper;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.bean.UserVipBean;

public interface UserVipMapper {

    public UserVipBean queryById(int id);

    public void insert(UserVipBean userVip);

    public void update(UserVipBean userVip);

    public void deleteById(int id);
    
    public void deleteByUserId(long userId);

    public UserVipBean getUserVipByVipId(@Param("userId") long userId, @Param("vipId") int vipId);

    public UserVipBean getUserVips(@Param("userId") long userId);

}
