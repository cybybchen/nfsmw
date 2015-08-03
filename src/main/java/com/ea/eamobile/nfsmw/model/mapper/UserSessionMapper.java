package com.ea.eamobile.nfsmw.model.mapper;

import com.ea.eamobile.nfsmw.model.UserSession;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Oct 25 17:34:05 CST 2012
 * @since 1.0
 */
public interface UserSessionMapper {

    public UserSession getUserSession(long userId);

    public int insertOrUpdate(UserSession userSession);

    public void delete(long userId);

    public UserSession getSession(String session);

}