package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.model.UserCarFragment;
import com.ea.eamobile.nfsmw.model.mapper.UserCarFragmentMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Apr 08 14:28:18 CST 2013
 * @since 1.0
 */
@Service
public class UserCarFragmentService {

    @Autowired
    private UserCarFragmentMapper userCarFragmentMapper;
    @Autowired
    private MemcachedClient cache;

    private String buildKey(long userId, int fragId) {
        return CacheKey.USER_CAR_FRAGMENT + userId + "_" + fragId;
    }

    public UserCarFragment getUserCarFragment(long userId, int fragmentId) {
        String key = buildKey(userId, fragmentId);
        UserCarFragment frag = (UserCarFragment) cache.get(key);
        if (frag == null) {
            frag = userCarFragmentMapper.getUserCarFragment(userId, fragmentId);
            cache.set(key, frag, MemcachedClient.HOUR);
        }
        return frag;
    }

    public UserCarFragment build(long userId, int fragmentId, int count) {
        UserCarFragment frag = new UserCarFragment();
        frag.setUserId(userId);
        frag.setFragmentId(fragmentId);
        frag.setCount(count);
        return frag;
    }

    public List<UserCarFragment> getUserCarFragmentList(long userId) {
        return userCarFragmentMapper.getUserCarFragmentList(userId);
    }

    public int insert(UserCarFragment userCarFragment) {
        return userCarFragmentMapper.insert(userCarFragment);
    }

    public void update(UserCarFragment userCarFragment) {
        userCarFragmentMapper.update(userCarFragment);
        String key = buildKey(userCarFragment.getUserId(), userCarFragment.getFragmentId());
        cache.delete(key);
    }

    public void deleteById(int id) {
        userCarFragmentMapper.deleteById(id);
    }

}