package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.CareerBestRacetimeRecord;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Nov 05 15:58:43 CST 2012
 * @since 1.0
 */
public interface CareerBestRacetimeRecordMapper {

    /**
     * only admin delete user use this
     * @param userId
     * @return
     */
    public List<CareerBestRacetimeRecord> getByUserId(long userId);
    
    public CareerBestRacetimeRecord getCareerBestRacetimeRecordByModeIdAndUserId(@Param("userId") long userId,
            @Param("modeType") int modeType);

    public int insert(CareerBestRacetimeRecord careerBestRacetimeRecord);

    public void update(CareerBestRacetimeRecord careerBestRacetimeRecord);

    public void deleteByUserId(long userId);

    public int getRank(@Param("modeType") int modeType, @Param("raceTime") float raceTime, @Param("userId") long userId);

    public int getRankBySpeed(@Param("modeType") int modeType, @Param("averageSpeed") float averageSpeed,
            @Param("userId") long userId);

    public List<CareerBestRacetimeRecord> getTopTenByAverageSpeed(int modeType);

    public List<CareerBestRacetimeRecord> getTopTenByRaceTime(int modeType);

    public List<CareerBestRacetimeRecord> getList(@Param("from") int from,@Param("to") int to);
    public Integer getMaxId();

}