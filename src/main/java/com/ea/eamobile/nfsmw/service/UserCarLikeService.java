package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.model.UserCarLike;
import com.ea.eamobile.nfsmw.model.mapper.UserCarLikeMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Apr 01 13:51:34 CST 2013
 * @since 1.0
 */
 @Service
public class UserCarLikeService {

    @Autowired
    private UserCarLikeMapper userCarLikeMapper;
    @Autowired
    private MemcachedClient cache;
    
    public UserCarLike getUserCarLike(long userCarId){
        return userCarLikeMapper.getUserCarLike(userCarId);
    }

    public List<UserCarLike> getUserCarLikeList(){
        return userCarLikeMapper.getUserCarLikeList();
    }

    public int insert(UserCarLike userCarLike){
    	cache.delete(CacheKey.USER_CAR_LIKE_COUNT + userCarLike.getUserCarId());
        return userCarLikeMapper.insert(userCarLike);
    }

    public void update(UserCarLike userCarLike){
    	cache.set(CacheKey.USER_CAR_LIKE_COUNT + userCarLike.getUserCarId(), userCarLike.getCount());
    	userCarLikeMapper.update(userCarLike);
    }

    public void deleteById(long userCarId){
    	cache.delete(CacheKey.USER_CAR_LIKE_COUNT + userCarId);
        userCarLikeMapper.deleteById(userCarId);
    }
    
    public int getLikeCountByUserCarId(long userCarId){
    	
    	Integer count = (Integer) cache.get(CacheKey.USER_CAR_LIKE_COUNT + userCarId);
    	if (count == null){
    		UserCarLike userCarLike = userCarLikeMapper.getUserCarLike(userCarId);
        	if (userCarLike != null){
        		count = userCarLike.getCount();
        	} else {
        		count = 0;
        	}
        	cache.set(CacheKey.USER_CAR_LIKE_COUNT + userCarId, count);
    	}
    	return count;
    }
    
    public int insert(long userCarId){
    	UserCarLike userCarLike = new UserCarLike();
    	userCarLike.setUserCarId(userCarId);
    	userCarLike.setCount(1);

    	return insert(userCarLike);
    }

}