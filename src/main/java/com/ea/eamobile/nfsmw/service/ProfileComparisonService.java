package com.ea.eamobile.nfsmw.service;

import java.util.List;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.ProfileComparison;
import com.ea.eamobile.nfsmw.model.mapper.ProfileComparisonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ma.ruofei
 * @version 1.0 Mon May 06 16:32:08 CST 2013
 * @since 1.0
 */
 @Service
public class ProfileComparisonService {

    @Autowired
    private ProfileComparisonMapper profileComparisonMapper;
    
    private final static String CACHE_HEAD = "profile_comparison_get_by_order_";
    private final static String LIST_CACHE_HEAD = "profile_comparison_list";
    
    public ProfileComparison getProfileComparison(int id){
        return profileComparisonMapper.getProfileComparison(id);
    }
    
    public ProfileComparison getProfileComparisonByOrderIndex(int orderIndex){
    	ProfileComparison ret = (ProfileComparison) InProcessCache.getInstance().get(CACHE_HEAD + orderIndex);
    	
    	if (ret != null) {
    		return ret;
    	}
    	
    	ret = profileComparisonMapper.getProfileComparisonByOrderIndex(orderIndex);
    	
    	InProcessCache.getInstance().set(CACHE_HEAD + orderIndex, ret);
    	
    	return ret;
    	
    }

    public List<ProfileComparison> getProfileComparisonList(){
    	@SuppressWarnings("unchecked")
    	List<ProfileComparison> retList = (List<ProfileComparison>) InProcessCache.getInstance().get(LIST_CACHE_HEAD);
    	if (retList != null){
    		return retList;
    	}
    	retList = profileComparisonMapper.getProfileComparisonList();
    	InProcessCache.getInstance().set(LIST_CACHE_HEAD, retList);
    	return retList;
    }

    public int insert(ProfileComparison profileComparison){
        return profileComparisonMapper.insert(profileComparison);
    }

    public void update(ProfileComparison profileComparison){
		    profileComparisonMapper.update(profileComparison);    
    }

    public void deleteById(int id){
        profileComparisonMapper.deleteById(id);
    }

}