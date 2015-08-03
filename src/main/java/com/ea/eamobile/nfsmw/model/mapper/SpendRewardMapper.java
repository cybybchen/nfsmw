package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.SpendReward;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Apr 17 14:36:58 CST 2013
 * @since 1.0
 */
public interface SpendRewardMapper {

    public SpendReward getSpendReward(int id);
    
    public List<SpendReward> getSpendRewardList();

    public int insert(SpendReward spendReward);

    public void update(SpendReward spendReward);

    public void deleteById(int id);

}