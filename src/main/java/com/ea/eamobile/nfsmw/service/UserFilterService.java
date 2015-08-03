package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.model.UserFilter;
import com.ea.eamobile.nfsmw.model.mapper.UserFilterMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Jun 17 11:50:46 CST 2013
 * @since 1.0
 */
@Service
public class UserFilterService {

    private static final int FILTER_GLB = 1; // 超跑会过滤

    @Autowired
    private UserFilterMapper userFilterMapper;
    @Autowired
    private MemcachedClient cache;

    public UserFilter getUserFilter(int option, long userId) {
        return userFilterMapper.getUserFilter(option, userId);
    }

    @SuppressWarnings("unchecked")
    public List<Long> getUserFilterList(int option) {
        List<Long> list = (List<Long>) cache.get(CacheKey.USER_FILTER + option);
        if (list == null) {
            list = userFilterMapper.getUserFilterList(option);
            cache.set(CacheKey.USER_FILTER + option, list);
        }
        return list;
    }

    /**
     * 是否需要从超跑会过滤
     * 
     * @param userId
     * @return
     */
    public boolean needFilterFromGlb(long userId) {
        List<Long> list = getUserFilterList(FILTER_GLB);
        if (list == null || list.size() == 0) {
            return false;
        }
        return list.contains(userId);
    }

    public int insert(UserFilter userFilter) {
        cache.delete(CacheKey.USER_FILTER + userFilter.getFilterOption());
        return userFilterMapper.insert(userFilter);
    }

    public void deleteById(int id) {
        userFilterMapper.deleteById(id);
    }
}