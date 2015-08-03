package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.RaceMode;

public interface RaceModeMapper {

    public RaceMode queryById(int id);

    public void insert(RaceMode raceMode);

    public void update(RaceMode raceMode);

    public void deleteById(int id);

    public List<RaceMode> getTrackModes(String trackId);

    public List<RaceMode> getUpModeTrackModes(@Param("modeType") int modeType, @Param("star") int star);

    public int getModeTypeById(int modeId);

    public List<Integer> getFirstModeType();
}
