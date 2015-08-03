package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.Reward;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Sep 24 18:44:41 CST 2012
 * @since 1.0
 */
public interface RewardMapper {

    public Reward getReward(int id);

    public List<Reward> getRewardList();

    public int insert(Reward reward);

    public void update(Reward reward);

    public void deleteById(int id);

}