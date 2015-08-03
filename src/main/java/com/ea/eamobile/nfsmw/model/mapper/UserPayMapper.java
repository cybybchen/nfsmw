package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.UserPay;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Mar 27 10:01:11 CST 2013
 * @since 1.0
 */
public interface UserPayMapper {

    public UserPay getUserPay(long userId);

    public List<UserPay> getUserPayList();

    public int insert(UserPay userPay);

    public void deleteById(long userId);

}