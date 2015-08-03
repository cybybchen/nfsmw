package com.ea.eamobile.nfsmw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.UserInitGold;
import com.ea.eamobile.nfsmw.model.mapper.UserInitGoldMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 05 15:42:32 CST 2013
 * @since 1.0
 */
@Service
public class UserInitGoldService {

    @Autowired
    private UserInitGoldMapper userInitGoldMapper;

    public UserInitGold getUserInitGold(int level) {
        return userInitGoldMapper.getUserInitGold(level);
    }

    public void update(int level) {
        userInitGoldMapper.update(level);
    }

}