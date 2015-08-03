package com.ea.eamobile.nfsmw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.UserGotcha;
import com.ea.eamobile.nfsmw.model.mapper.UserGotchaMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Mar 27 10:01:11 CST 2013
 * @since 1.0
 */
 @Service
public class UserGotchaService {

    @Autowired
    private UserGotchaMapper userGotchaMapper;
    
    public int insert(UserGotcha userGotcha){
        return userGotchaMapper.insert(userGotcha);
    }

    public void update(UserGotcha userGotcha){
		    userGotchaMapper.update(userGotcha);    
    }

    public UserGotcha getUserGotchaByUidGid(long userId, int gotchaId) {
        return userGotchaMapper.getUserGotchaByUidGid(userId, gotchaId);
    }

}