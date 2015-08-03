package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.UserReportLog;
import com.ea.eamobile.nfsmw.model.mapper.UserReportLogMapper;
import com.ea.eamobile.nfsmw.utils.DateUtil;


/**
 * @author ma.ruofei
 * @version 1.0 Mon Apr 01 13:51:34 CST 2013
 * @since 1.0
 */
 @Service
public class UserReportLogService {

    @Autowired
    private UserReportLogMapper userReportLogMapper;
    
    public UserReportLog getUserReportLog(long id){
        return userReportLogMapper.getUserReportLog(id);
    }

    public List<UserReportLog> getUserReportLogList(){
        return userReportLogMapper.getUserReportLogList();
    }

    public int insert(UserReportLog userReportLog){
        return userReportLogMapper.insert(userReportLog);
    }

    public void update(UserReportLog userReportLog){
		    userReportLogMapper.update(userReportLog);    
    }

    public void deleteById(long id){
        userReportLogMapper.deleteById(id);
    }
    
    public int insert(long userId, long reportedId){
    	
		UserReportLog userReportLog = new UserReportLog();
		userReportLog.setUserId(userId);
		userReportLog.setReportedId(reportedId);
		int date = DateUtil.getDateId();
		userReportLog.setReportDate(date);
		
		return insert(userReportLog);
    }

    public boolean hasLog(long userId, long reportedId){
    	int reportDate = DateUtil.getDateId();
    	UserReportLog log = userReportLogMapper.getUserReportLogByIdAndData(userId, reportedId, reportDate);
    	return log != null;
    }
}