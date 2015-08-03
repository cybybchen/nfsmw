package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.UserCarLike;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Apr 01 13:51:32 CST 2013
 * @since 1.0
 */
public interface UserCarLikeMapper {

    public UserCarLike getUserCarLike(long userCarId);

    public List<UserCarLike> getUserCarLikeList();

    public int insert(UserCarLike userCarLike);

    public void update(UserCarLike userCarLike);

    public void deleteById(long userCarId);

}