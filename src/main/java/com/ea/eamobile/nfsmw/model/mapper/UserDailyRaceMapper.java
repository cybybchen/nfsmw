package com.ea.eamobile.nfsmw.model.mapper;

import com.ea.eamobile.nfsmw.model.UserDailyRace;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Nov 19 16:49:27 CST 2012
 * @since 1.0
 */
public interface UserDailyRaceMapper {

    public UserDailyRace getUserDailyRace(long userId);

    public int insert(UserDailyRace userDailyRace);

    public void update(UserDailyRace userDailyRace);

    public void delete(long userId);


}