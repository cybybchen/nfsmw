package com.ea.eamobile.nfsmw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.UserRefreshTime;
import com.ea.eamobile.nfsmw.model.mapper.UserRefreshTimeMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Apr 11 12:17:35 CST 2013
 * @since 1.0
 */
@Service
public class UserRefreshTimeService {

    @Autowired
    private UserRefreshTimeMapper userRefreshTimeMapper;

    public UserRefreshTime getUserRefreshTime(long userId) {
        return userRefreshTimeMapper.getUserRefreshTime(userId);
    }

    public int insert(UserRefreshTime userRefreshTime) {
        return userRefreshTimeMapper.insert(userRefreshTime);
    }

    public void update(UserRefreshTime userRefreshTime) {
        userRefreshTimeMapper.update(userRefreshTime);
    }

}