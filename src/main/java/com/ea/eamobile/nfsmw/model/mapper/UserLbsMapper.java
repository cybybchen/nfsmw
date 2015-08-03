package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.UserLbs;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Jan 28 16:12:11 CST 2013
 * @since 1.0
 */
public interface UserLbsMapper {

    public UserLbs getUserLbs(long userId);

    public List<UserLbs> getUserLbsList();

    public int insert(UserLbs userLbs);

    public void update(UserLbs userLbs);

    public void deleteById(int id);

}