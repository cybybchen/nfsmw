package com.ea.eamobile.nfsmw.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.constants.RewardConst;
import com.ea.eamobile.nfsmw.model.CarChartlet;
import com.ea.eamobile.nfsmw.model.UserChartlet;
import com.ea.eamobile.nfsmw.model.bean.PropBean;
import com.ea.eamobile.nfsmw.model.bean.UserPropBean;
import com.ea.eamobile.nfsmw.model.mapper.UserPropMapper;
import com.ea.eamobile.nfsmw.utils.DateUtil;
import com.ea.eamobile.nfsmw.view.ResultInfo;

@Service
public class UserPropService {

	private static final Logger log = LoggerFactory.getLogger(UserPropService.class);
	
    @Autowired
    private UserPropMapper userPropMapper;
    @Autowired
    private FinishRatioService ratioService;
    @Autowired
    private FinishRatioAdditionService finishRatioAdditionService;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private PayService payService;
    @Autowired
    private PropService propService;

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
    		userProp = buildUserProp(userId, propId, propCount);
    		insertUserProp(userProp);
    	} else {
    		userProp = buildUserProp(userId, propId, userProp.getPropCount() + propCount);
    		updateUserProp(userProp);
    	}
    	
    	return userProp;
    }
    
    public UserPropBean useUserProp(long userId, int propId, int propCount) {
    	UserPropBean userProp = getUserPropByPropId(userId, propId);
    	if (userProp != null) {
    		if (userProp.getPropCount() >= 1) {
    			userProp.setPropCount(userProp.getPropCount() - propCount);
    			updateUserProp(userProp);
    			return userProp;
    		} else {
    			userProp = buyProp(userId, propId, propCount - userProp.getPropCount());
    			if (userProp != null) {
    				userProp.setPropCount(userProp.getPropCount() - propCount);
    				updateUserProp(userProp);
    				return userProp;
    			}
    		}
    	}
    	
    	return null;
    }
    
    public UserPropBean buyProp(long userId, int propId, int propCount) {
        ResultInfo result = new ResultInfo(false, "");
        
        PropBean prop = propService.queryProp(propId);
        prop.setPrice(prop.getPrice() * propCount);
        result = payService.buy(prop, userId);
        if (result.isSuccess()) {
            // insert car for user

        	UserPropBean userProp = addUserProp(userId, propId, propCount);

            log.debug("buy prop success,propId={},userid={}", propId, userId);
            
            return userProp;
        }

        return null;
    }
    
    private UserPropBean buildUserProp(long userId, int propId, int propCount) {
    	UserPropBean prop = new UserPropBean();
    	prop.setUserId(userId);
    	prop.setPropId(propId);
    	prop.setPropCount(propCount);
    	
    	if (propId == RewardConst.TYPE_REWARD_PROP_FREELOTTERY)
    		prop.setPropCount(1);
     
        return prop;
    }

}
