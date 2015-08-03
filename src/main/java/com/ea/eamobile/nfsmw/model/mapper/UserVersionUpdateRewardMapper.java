package com.ea.eamobile.nfsmw.model.mapper;

import com.ea.eamobile.nfsmw.model.UserVersionUpdateReward;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Mar 28 17:48:00 CST 2013
 * @since 1.0
 */
public interface UserVersionUpdateRewardMapper {

    public UserVersionUpdateReward getUserVersionUpdateReward(long userId);

    public int insert(UserVersionUpdateReward userVersionUpdateReward);

    public void update(UserVersionUpdateReward userVersionUpdateReward);


}