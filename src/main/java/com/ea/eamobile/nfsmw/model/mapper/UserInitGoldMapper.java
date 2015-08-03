package com.ea.eamobile.nfsmw.model.mapper;

import com.ea.eamobile.nfsmw.model.UserInitGold;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 05 15:42:32 CST 2013
 * @since 1.0
 */
public interface UserInitGoldMapper {

    public UserInitGold getUserInitGold(int level);

    public void update(int level);



}