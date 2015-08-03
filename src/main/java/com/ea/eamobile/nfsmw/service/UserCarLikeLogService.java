package com.ea.eamobile.nfsmw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.UserCarLikeLog;
import com.ea.eamobile.nfsmw.model.mapper.UserCarLikeLogMapper;


/**
 * @author ma.ruofei
 * @version 1.0 Mon Apr 01 13:51:34 CST 2013
 * @since 1.0
 */
 @Service
public class UserCarLikeLogService {

    @Autowired
    private UserCarLikeLogMapper userCarLikeLogMapper;
    
    public UserCarLikeLog getUserCarLikeLog(long id){
        return userCarLikeLogMapper.getUserCarLikeLog(id);
    }

 

    private int insert(UserCarLikeLog userCarLikeLog){
        return userCarLikeLogMapper.insert(userCarLikeLog);
    }



    public boolean hasLog(long userId, long userCarId){
		UserCarLikeLog log = userCarLikeLogMapper.getUserCarLikeLogByUserIdAndUserCarId(userId, userCarId);
		return log != null;
    }
    
    public int insert(long userId, long userCarId,long likedUserId){
    	
    	UserCarLikeLog userCarLikeLog = new UserCarLikeLog();
    	userCarLikeLog.setUserId(userId);
    	userCarLikeLog.setUserCarId(userCarId);   	
    	userCarLikeLog.setLikedUserId(likedUserId);
    	return insert(userCarLikeLog);
    }
    
}