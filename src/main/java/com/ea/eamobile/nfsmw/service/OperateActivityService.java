package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.model.OperateActivity;
import com.ea.eamobile.nfsmw.model.mapper.OperateActivityMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Mar 22 12:02:04 CST 2013
 * @since 1.0
 */
@Service
public class OperateActivityService {

    @Autowired
    private OperateActivityMapper operateActivityMapper;

    @Autowired
    private MemcachedClient cache;

    private void clear(int id) {
        cache.delete(CacheKey.OPERATE_ACTIVITY_BYID + id);
        cache.delete(CacheKey.OPERATE_ACTIVITY_LIST);
    }

    public OperateActivity getOperateActivity(int id) {
        OperateActivity operateActivity = (OperateActivity) cache.get(CacheKey.OPERATE_ACTIVITY_BYID + id);
        if (operateActivity == null) {
            operateActivity = operateActivityMapper.getOperateActivity(id);
            cache.set(CacheKey.OPERATE_ACTIVITY_BYID + id, operateActivity, MemcachedClient.HOUR);
        }
        return operateActivity;
    }

    public OperateActivity getOperateActivityByTime(int time) {
        List<OperateActivity> operateActivities = getOperateActivityList();
        for (OperateActivity operateActivity : operateActivities) {
            if (operateActivity.getStartTime() < time && time < operateActivity.getEndTime()) {
                return operateActivity;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<OperateActivity> getOperateActivityList() {
        List<OperateActivity> list = (List<OperateActivity>) cache.get(CacheKey.OPERATE_ACTIVITY_LIST);
        if (list == null) {
            list = operateActivityMapper.getOperateActivityList();
            cache.set(CacheKey.OPERATE_ACTIVITY_LIST, list, MemcachedClient.HOUR);
        }
        return list;

    }

    public int insert(OperateActivity operateActivity) {
        clear(operateActivity.getId());
        return operateActivityMapper.insert(operateActivity);
    }

    public void update(OperateActivity operateActivity) {
        clear(operateActivity.getId());
        operateActivityMapper.update(operateActivity);
    }

    public void deleteById(int id) {
        clear(id);
        operateActivityMapper.deleteById(id);
    }

    public int getMaxEndTime() {
        Integer result = operateActivityMapper.getMaxEndTime();
        if (result == null) {
            result = 0;
        }
        return result;
    }

}