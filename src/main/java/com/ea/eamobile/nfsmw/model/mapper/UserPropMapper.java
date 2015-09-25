package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.bean.UserPropBean;

public interface UserPropMapper {

    public UserPropBean queryById(int id);

    public void insert(UserPropBean userProp);

    public void update(UserPropBean userProp);

    public void deleteById(int id);
    
    public void deleteByUserId(long userId);

    public UserPropBean getUserPropByPropId(@Param("userId") long userId, @Param("propId") int propId);

    public List<UserPropBean> getUserProps(@Param("userId") long userId);

}
