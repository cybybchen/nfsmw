package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.model.SpendReward;
import com.ea.eamobile.nfsmw.model.mapper.SpendRewardMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Apr 17 14:36:58 CST 2013
 * @since 1.0
 */
@Service
public class SpendRewardService {

    @Autowired
    private SpendRewardMapper spendRewardMapper;

    @Autowired
    private MemcachedClient cache;

    private void clear(int id) {
        cache.delete(CacheKey.SPEND_REWARD_BYID + id);
        cache.delete(CacheKey.SPEND_REWARD_LIST);
    }

    public SpendReward getSpendReward(int id) {
        SpendReward spendReward = (SpendReward) cache.get(CacheKey.SPEND_REWARD_BYID + id);
        if (spendReward == null) {
            spendReward = spendRewardMapper.getSpendReward(id);
            cache.set(CacheKey.SPEND_REWARD_BYID + id, spendReward, MemcachedClient.HOUR);
        }
        return spendReward;
    }

    @SuppressWarnings("unchecked")
    public List<SpendReward> getSpendRewardList() {
        List<SpendReward> list = (List<SpendReward>) cache.get(CacheKey.SPEND_REWARD_LIST);
        if (list == null) {
            list = spendRewardMapper.getSpendRewardList();
            cache.set(CacheKey.SPEND_REWARD_LIST, list, MemcachedClient.HOUR);
        }
        return list;
    }

    public int insert(SpendReward spendReward) {
        clear(spendReward.getId());
        return spendRewardMapper.insert(spendReward);
    }

    public void update(SpendReward spendReward) {
        clear(spendReward.getId());
        spendRewardMapper.update(spendReward);
    }

    public void deleteById(int id) {
        clear(id);
        spendRewardMapper.deleteById(id);
    }

}