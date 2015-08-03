package com.ea.eamobile.nfsmw.model.mapper;

import com.ea.eamobile.nfsmw.model.UserRefreshTime;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Apr 11 12:17:35 CST 2013
 * @since 1.0
 */
public interface UserRefreshTimeMapper {

    public UserRefreshTime getUserRefreshTime(long userId);

    public int insert(UserRefreshTime userRefreshTime);

    public void update(UserRefreshTime userRefreshTime);

}