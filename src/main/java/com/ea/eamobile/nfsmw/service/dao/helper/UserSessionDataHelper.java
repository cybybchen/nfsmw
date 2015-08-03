package com.ea.eamobile.nfsmw.service.dao.helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.ea.eamobile.nfsmw.model.UserSession;
import com.ea.eamobile.nfsmw.service.dao.helper.util.GeneralDataHelper;

public class UserSessionDataHelper extends UserSession implements GeneralDataHelper{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserSessionDataHelper(){
		
	}
	
	public UserSessionDataHelper(UserSession data) {
		this.userId = data.getUserId();
		this.session = data.getSession();
		this.upTime = data.getUpTime();
		
		setMarks(data.getMarks());
	}

	@Override
	public void parseDbData(ResultSet rs) throws SQLException {
		this.userId = rs.getLong("user_id");
		this.session = rs.getString("session");
		this.upTime = rs.getTimestamp("up_time");
	}

	@Override
	public Map<String, String> getDataMap() {
		return demark();
	}

	@Override
	public void addDefaultDataMap(Map<String, String> dataMap) {
		return;
	}

	@Override
	public void deleteIgnoreDataMap(Map<String, String> dataMap) {
		if (dataMap.containsKey(USER_ID)) {
			dataMap.remove(USER_ID);
		}
	}

}
