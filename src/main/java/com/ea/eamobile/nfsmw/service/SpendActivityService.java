package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.model.SpendActivity;
import com.ea.eamobile.nfsmw.model.mapper.SpendActivityMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Apr 17 14:36:58 CST 2013
 * @since 1.0
 */
@Service
public class SpendActivityService {

    @Autowired
    private SpendActivityMapper spendActivityMapper;
    @Autowired
    private MemcachedClient cache;

    private void clear(int id) {
        cache.delete(CacheKey.SPEND_ACTIVITY_BYID + id);
        cache.delete(CacheKey.SPEND_ACTIVITY_LIST);
    }

    public SpendActivity getSpendActivity(int id) {
        SpendActivity spendActivity = (SpendActivity) cache.get(CacheKey.SPEND_ACTIVITY_BYID + id);
        if (spendActivity == null) {
            spendActivity = spendActivityMapper.getSpendActivity(id);
            cache.set(CacheKey.SPEND_ACTIVITY_BYID + id, spendActivity, MemcachedClient.HOUR);
        }
        return spendActivity;
    }

    @SuppressWarnings("unchecked")
    public List<SpendActivity> getSpendActivityList() {
        List<SpendActivity> list = (List<SpendActivity>) cache.get(CacheKey.SPEND_ACTIVITY_LIST);
        if (list == null) {
            list = spendActivityMapper.getSpendActivityList();
            cache.set(CacheKey.SPEND_ACTIVITY_LIST, list, MemcachedClient.HOUR);
        }
        return list;
    }

    public SpendActivity getSpendActivityByTime(int time) {
        List<SpendActivity> spendActivities = getSpendActivityList();
        for (SpendActivity spendActivity : spendActivities) {
            if (time > spendActivity.getStartTime() && time < spendActivity.getEndTime()) {
                return spendActivity;
            }
        }
        return null;
    }

    public int insert(SpendActivity spendActivity) {
        clear(spendActivity.getId());
        return spendActivityMapper.insert(spendActivity);
    }

    public void update(SpendActivity spendActivity) {
        clear(spendActivity.getId());
        spendActivityMapper.update(spendActivity);
    }

    public void deleteById(int id) {
        clear(id);
        spendActivityMapper.deleteById(id);
    }

}