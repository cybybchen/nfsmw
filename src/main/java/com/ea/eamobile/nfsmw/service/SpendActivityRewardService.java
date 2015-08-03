package com.ea.eamobile.nfsmw.service;

import java.util.List;
import com.ea.eamobile.nfsmw.model.SpendActivityReward;
import com.ea.eamobile.nfsmw.model.mapper.SpendActivityRewardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Apr 17 14:36:58 CST 2013
 * @since 1.0
 */
@Service
public class SpendActivityRewardService {

    @Autowired
    private SpendActivityRewardMapper spendActivityRewardMapper;

    public SpendActivityReward getSpendActivityReward(int id) {
        return spendActivityRewardMapper.getSpendActivityReward(id);
    }

    public List<SpendActivityReward> getSpendActivityRewardList(int spendActivityId) {
        return spendActivityRewardMapper.getSpendActivityRewardList(spendActivityId);
    }

    public int insert(SpendActivityReward spendActivityReward) {
        return spendActivityRewardMapper.insert(spendActivityReward);
    }

    public void update(SpendActivityReward spendActivityReward) {
        spendActivityRewardMapper.update(spendActivityReward);
    }

    public void deleteById(int spendActivityId, int spendRewardId) {
        spendActivityRewardMapper.deleteByRewardIdAndActivityId(spendActivityId, spendRewardId);
    }

    public void deleteBySpendActivityId(int spendActivityId) {
        spendActivityRewardMapper.deleteBySpendActivityId(spendActivityId);
    }

}