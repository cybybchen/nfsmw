package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.UserCarFragment;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Apr 08 14:28:18 CST 2013
 * @since 1.0
 */
public interface UserCarFragmentMapper {

    public UserCarFragment getUserCarFragment(@Param("userId") long userId,@Param("fragmentId") int fragmentId);

    public List<UserCarFragment> getUserCarFragmentList(long userId);

    public int insert(UserCarFragment userCarFragment);

    public void update(UserCarFragment userCarFragment);

    public void deleteById(int id);

}