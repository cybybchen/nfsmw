package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.CareerGhost;

/**
 * @author ma.ruofei
 * @version 1.0 Mon May 20 14:47:25 CST 2013
 * @since 1.0
 */
public interface CareerGhostMapper {

    public CareerGhost getCareerGhost(int id);

    public CareerGhost getCareerGhostByUserIdAndModeType(@Param("userId") long userId, @Param("modeType") int modeType);

    public List<CareerGhost> getCareerGhostList();

    public int insert(CareerGhost careerGhost);

    public void update(CareerGhost careerGhost);

    public void deleteById(int id);

    public int getCareerGhostNumByRaceTime(@Param("raceTime") float raceTime, @Param("modeType") int modeType);

    public int getMoreRaceTimeCareerGhostNumByRaceTime(@Param("raceTime") float raceTime,
            @Param("modeType") int modeType);

    public int getLessRaceTimeCareerGhostNumByRaceTime(@Param("raceTime") float raceTime,
            @Param("modeType") int modeType);

    public List<Integer> getCareerGhostListByRaceTime(@Param("raceTime") float raceTime, @Param("modeType") int modeType);

    public Float getCareerGhostMaxRaceTimeByModeType(int modeType);

    public Float getCareerGhostMinRaceTimeByModeType(int modeType);

}