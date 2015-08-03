package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.SpendActivityReward;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Apr 17 14:36:58 CST 2013
 * @since 1.0
 */
public interface SpendActivityRewardMapper {

    public SpendActivityReward getSpendActivityReward(int id);

    public List<SpendActivityReward> getSpendActivityRewardList(int spendActivityId);

    public int insert(SpendActivityReward spendActivityReward);

    public void update(SpendActivityReward spendActivityReward);

    public void deleteByRewardIdAndActivityId(@Param("spendActivityId") int spendActivityId,
            @Param("spendRewardId") int spendRewardId);

    public void deleteBySpendActivityId(int spendActivityId);

}