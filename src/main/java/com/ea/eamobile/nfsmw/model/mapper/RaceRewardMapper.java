package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.RaceReward;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Oct 18 14:56:40 CST 2012
 * @since 1.0
 */
public interface RaceRewardMapper {

    public Integer getRewardId(@Param("rpLevel") int rpLevel, @Param("gameMode") int gameMode,
            @Param("raceType") int raceType, @Param("position") int position, @Param("isFinish") int isFinish);
    
    public List<RaceReward> getRaceRewards();

}