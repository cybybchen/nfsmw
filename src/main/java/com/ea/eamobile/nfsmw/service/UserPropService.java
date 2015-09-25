package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.bean.UserPropBean;
import com.ea.eamobile.nfsmw.model.mapper.UserPropMapper;

@Service
public class UserPropService {

    @Autowired
    private UserPropMapper userPropMapper;
    @Autowired
    private FinishRatioService ratioService;
    @Autowired
    private FinishRatioAdditionService finishRatioAdditionService;
    @Autowired
    private SystemConfigService systemConfigService;

    public void insertUserProp(UserPropBean userProp) {
    	userPropMapper.insert(userProp);
    }

    public void updateUserProp(UserPropBean userProp) {
    	userPropMapper.update(userProp);
    }

    public void delete(int id) {
    	userPropMapper.deleteById(id);
    }

    public void deleteByUserId(long userId) {
    	userPropMapper.deleteByUserId(userId);
    }

    public UserPropBean queryUserProp(int id) {
        return userPropMapper.queryById(id);
    }

    public UserPropBean getUserPropByPropId(long userId, int propId) {
        return userPropMapper.getUserPropByPropId(userId, propId);
    }

    public List<UserPropBean> getUserProps(long userId) {
        return userPropMapper.getUserProps(userId);
    }

    public UserPropBean addUserProp(long userId, int propId, int propCount) {
    	UserPropBean userProp = getUserPropByPropId(userId, propId);
    	if (userProp == null) {
    		userProp = buildProp(userId, propId, propCount);
    		insertUserProp(userProp);
    	} else {
    		userProp = buildProp(userId, propId, userProp.getPropCount() + propCount);
    		updateUserProp(userProp);
    	}
    	
    	return userProp;
    }
    
    private UserPropBean buildProp(long userId, int propId, int propCount) {
    	UserPropBean prop = new UserPropBean();
    	prop.setUserId(userId);
    	prop.setPropId(propId);
    	prop.setPropCount(propCount);
     
        return prop;
    }

}
