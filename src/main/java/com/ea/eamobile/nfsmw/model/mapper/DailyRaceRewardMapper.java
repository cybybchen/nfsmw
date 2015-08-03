package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.DailyRaceReward;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Nov 20 11:13:32 CST 2012
 * @since 1.0
 */
public interface DailyRaceRewardMapper {

    public int getRewardId(@Param("rpLevel")int rpLevel,@Param("duraNum")int duraNum,@Param("isFinish") int isFinish);

    public List<DailyRaceReward> getDailyRaceRewardList();


}