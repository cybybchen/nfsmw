package com.ea.eamobile.nfsmw.service.dao;

import org.springframework.stereotype.Component;

import com.ea.eamobile.nfsmw.model.UserSession;
import com.ea.eamobile.nfsmw.service.dao.helper.UserSessionDaoHelper;

@Component("userSessionDao")
public class UserSessionDao {

	private static UserSessionDaoHelper userSessionDaoHelper = new UserSessionDaoHelper();
	
    public UserSession getUserSession(long userId) {
    	return userSessionDaoHelper.getUserSession(userId);
    }

    public int insertOrUpdate(UserSession userSession) {
    	return userSessionDaoHelper.insertOrUpdate(userSession);
    }

    public void delete(long userId) {
    	userSessionDaoHelper.delete(userId);
    }

    public UserSession getSession(String session) {
    	return (UserSession) userSessionDaoHelper.getSingleByField("session", session);
    }
}
