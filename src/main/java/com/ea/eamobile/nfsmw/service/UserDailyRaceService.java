package com.ea.eamobile.nfsmw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.model.UserDailyRace;
import com.ea.eamobile.nfsmw.model.mapper.UserDailyRaceMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Nov 19 16:49:27 CST 2012
 * @since 1.0
 */
@Service
public class UserDailyRaceService {

    @Autowired
    private UserDailyRaceMapper userDailyRaceMapper;
    @Autowired
    private MemcachedClient cache;

    private String buildKey(long userId) {
        return CacheKey.USER_DAILY_RACE + userId;
    }

    public UserDailyRace getUserDailyRace(long userId) {
        UserDailyRace race = (UserDailyRace) cache.get(buildKey(userId));
        if (race == null) {
            race = userDailyRaceMapper.getUserDailyRace(userId);
            cache.set(buildKey(userId), race, MemcachedClient.DAY);
        }
        return race;
    }

    public int insert(UserDailyRace userDailyRace) {
        cache.delete(buildKey(userDailyRace.getUserId()));
        return userDailyRaceMapper.insert(userDailyRace);
    }

    public void update(UserDailyRace userDailyRace) {
        cache.delete(buildKey(userDailyRace.getUserId()));
        userDailyRaceMapper.update(userDailyRace);
    }

    public void delete(long userId) {
        cache.delete(buildKey(userId));
        userDailyRaceMapper.delete(userId);
    }

}