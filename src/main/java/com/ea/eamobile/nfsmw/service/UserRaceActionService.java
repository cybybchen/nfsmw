package com.ea.eamobile.nfsmw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.ProfileComparisonType;
import com.ea.eamobile.nfsmw.model.UserRaceAction;
import com.ea.eamobile.nfsmw.model.mapper.UserRaceActionMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Apr 01 13:51:34 CST 2013
 * @since 1.0
 */
 @Service
public class UserRaceActionService {

    @Autowired
    private UserRaceActionMapper userRaceActionMapper;
    @Autowired
    private MemcachedClient cache;
    
    public UserRaceAction getUserRaceAction(long id){
        return userRaceActionMapper.getUserRaceAction(id);
    }

    public UserRaceAction getUserRaceActionByUserIdAndValueId(long userId, int valueId){
    	UserRaceAction userRaceAction = (UserRaceAction) cache.get(CacheKey.USER_RACE_ACTION + userId + "_" + valueId);
    	if (userRaceAction == null){
    		userRaceAction = userRaceActionMapper.getUserRaceActionByUserIdAndValueId(userId, valueId);
    		if (userRaceAction != null){
    			cache.set(CacheKey.USER_RACE_ACTION + userId + "_" + valueId, userRaceAction);
    		}
    	}
        return userRaceAction;
    }
    
    public List<UserRaceAction> getUserRaceActionListByUserId(long userId){
    	//return userRaceActionMapper.getUserRaceActionListByUserId(userId);
    	List<UserRaceAction> userRaceActionList = new ArrayList<UserRaceAction>();
    	for(ProfileComparisonType en : ProfileComparisonType.values()){
    		UserRaceAction userRaceAction = getUserRaceActionByUserIdAndValueId(userId, en.getIndex());
    		if (userRaceAction != null){
    			userRaceActionList.add(userRaceAction);
    		}
    	}
    	return userRaceActionList;
    }

    public int insert(UserRaceAction userRaceAction){
        return userRaceActionMapper.insert(userRaceAction);
    }

    public void update(UserRaceAction userRaceAction){
    	cache.delete(CacheKey.USER_RACE_ACTION + userRaceAction.getUserId() + "_" + userRaceAction.getValueId());
		userRaceActionMapper.update(userRaceAction);    
    }

    public void deleteById(long id){
    	UserRaceAction userRaceAction = getUserRaceAction(id);
    	cache.delete(CacheKey.USER_RACE_ACTION + userRaceAction.getUserId() + "_" + userRaceAction.getValueId());
        userRaceActionMapper.deleteById(id);
    }
    
    public int insert(long userId, int valueId, int value){
    	UserRaceAction userRaceAction = new UserRaceAction();
    	userRaceAction.setUserId(userId);
    	userRaceAction.setValueId(valueId);
    	userRaceAction.setValue(value);
    	return insert(userRaceAction);
    }
    
    public void refreshDataAndCache(long userId, int valueId, int value){
        UserRaceAction userRaceAction = getUserRaceActionByUserIdAndValueId(userId, valueId);
    	if (userRaceAction != null){// user_race_action 表中有数据，updte
        	userRaceAction.setValue(value);
        	update(userRaceAction);
    	} else {
        	userRaceAction = new UserRaceAction();
        	userRaceAction.setUserId(userId);
        	userRaceAction.setValueId(valueId);
        	userRaceAction.setValue(value);
    		insert(userRaceAction);
    	}
        cache.set(CacheKey.USER_RACE_ACTION + userId + "_" + valueId, userRaceAction);
    }

}