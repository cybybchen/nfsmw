package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.UserLbs;
import com.ea.eamobile.nfsmw.model.mapper.UserLbsMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Jan 28 16:12:11 CST 2013
 * @since 1.0
 */
 @Service
public class UserLbsService {

    @Autowired
    private UserLbsMapper userLbsMapper;
    
    public UserLbs getUserLbs(long userId){
        return userLbsMapper.getUserLbs(userId);
    }

    public List<UserLbs> getUserLbsList(){
        return userLbsMapper.getUserLbsList();
    }

    public int insert(UserLbs userLbs){
        return userLbsMapper.insert(userLbs);
    }

    public void update(UserLbs userLbs){
		    userLbsMapper.update(userLbs);    
    }

    public void deleteById(int id){
        userLbsMapper.deleteById(id);
    }

}