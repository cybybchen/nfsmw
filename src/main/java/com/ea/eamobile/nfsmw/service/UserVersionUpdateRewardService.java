package com.ea.eamobile.nfsmw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.UserVersionUpdateReward;
import com.ea.eamobile.nfsmw.model.mapper.UserVersionUpdateRewardMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Mar 28 17:48:00 CST 2013
 * @since 1.0
 */
 @Service
public class UserVersionUpdateRewardService {

    @Autowired
    private UserVersionUpdateRewardMapper userVersionUpdateRewardMapper;
    
    public UserVersionUpdateReward getUserVersionUpdateReward(long userId){
        return userVersionUpdateRewardMapper.getUserVersionUpdateReward(userId);
    }

    public int insert(UserVersionUpdateReward userVersionUpdateReward){
        return userVersionUpdateRewardMapper.insert(userVersionUpdateReward);
    }

    public void update(UserVersionUpdateReward userVersionUpdateReward){
		    userVersionUpdateRewardMapper.update(userVersionUpdateReward);    
    }


}