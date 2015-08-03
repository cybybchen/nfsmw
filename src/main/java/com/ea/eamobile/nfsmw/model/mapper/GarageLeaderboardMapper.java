package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.GarageLeaderboard;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 05 16:27:05 CST 2013
 * @since 1.0
 */
public interface GarageLeaderboardMapper {

    public GarageLeaderboard getGarageLeaderboard(long userId);

    public List<GarageLeaderboard> getList();

    public int insert(GarageLeaderboard garageLeaderboard);

    public void update(GarageLeaderboard garageLeaderboard);
    
    public void insertBatch(List<GarageLeaderboard> list);

    public void deleteAll();

}