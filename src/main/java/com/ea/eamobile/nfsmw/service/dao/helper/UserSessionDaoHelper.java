package com.ea.eamobile.nfsmw.service.dao.helper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.model.UserSession;
import com.ea.eamobile.nfsmw.service.dao.helper.util.GeneralDaoDbHelper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.GeneralDaoDbMemcachedHelper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.GeneralDaoMemcachedHelper;

public class UserSessionDaoHelper extends GeneralDaoDbMemcachedHelper<UserSessionDataHelper> {
	
	private static final UserSessionDaoDbHelper dbHelper = new UserSessionDaoDbHelper();
	private static final UserSessionDaoMemcachedHelper cacheHelper = new UserSessionDaoMemcachedHelper();

	public UserSessionDaoHelper() {
		super(dbHelper, cacheHelper);
	}
	
	public UserSessionDataHelper getSingleByField(String field, String value) {
		return dbHelper.getSingleByField(field, value);
	}
	
	public UserSession getUserSession(long userId) {
		String sql = "select * from user_session where user_id="+userId;
		return (UserSession)dbHelper.query(sql, new ResultSetHandler<UserSession>(){
			@Override
			public UserSession handle(ResultSet rs) throws SQLException {
				if (rs.next()){
					UserSession us = new UserSession();
					us.setUserId(rs.getLong("user_id"));
					us.setSession(rs.getString("session"));
					us.setUpTime(rs.getTimestamp("up_time"));
					return us;
				}
				return null;
			}
			
		});
	}

	public int insertOrUpdate(UserSession userSession) {
		String sql = "insert into user_session (user_id, session) values ("
				+ userSession.getUserId() + ", \"" + userSession.getSession()
				+ "\") on duplicate key update session=\""
				+ userSession.getSession() + "\"";
		update(sql, userSession.getSession());
		return (int) userSession.getUserId();
	}
	
	protected static class UserSessionDaoDbHelper extends GeneralDaoDbHelper<UserSessionDataHelper> {

		protected UserSessionDaoDbHelper() {
			super("user_session", "user_session", "session");
		}
		@Override
		protected UserSessionDataHelper create() {
			return new UserSessionDataHelper();
		}
		
	}
	
	protected static class UserSessionDaoMemcachedHelper extends GeneralDaoMemcachedHelper<UserSessionDataHelper> {

		@Override
		protected String getPrefix() {
			return CacheKey.USER_SESSION;
		}
		
	}

}
