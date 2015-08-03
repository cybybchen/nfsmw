package com.ea.eamobile.nfsmw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.RaceMode;
import com.ea.eamobile.nfsmw.model.mapper.RaceModeMapper;

@Service
public class RaceModeService {

    @Autowired
    private RaceModeMapper raceModeMapper;

    public void insertRaceMode(RaceMode raceMode) {
        raceModeMapper.insert(raceMode);
    }

    public void updateRaceMode(RaceMode raceMode) {
        raceModeMapper.update(raceMode);
    }

    public void delete(int id) {
        raceModeMapper.deleteById(id);
    }

    public RaceMode getModeById(int modeId) {
        RaceMode ret = (RaceMode) InProcessCache.getInstance().get("getModeById." + modeId);
        if (ret != null) {
            return ret;
        }
        ret = raceModeMapper.queryById(modeId);
        InProcessCache.getInstance().set("getModeById." + modeId, ret);
        return ret;
    }

    /**
     * 取一个赛道的总完成度数值
     * 
     * @param trackId
     * @return
     */
    public int getTrackTotalRatio(String trackId) {
        int total = 0;
        List<RaceMode> list = getTrackModes(trackId);
        if (list == null)
            return 0;
        for (RaceMode mode : list) {
            total += mode.getValue();
        }
        return total;
    }

    public List<RaceMode> getTrackModes(String trackId) {
        @SuppressWarnings("unchecked")
        List<RaceMode> ret = (List<RaceMode>) InProcessCache.getInstance().get("getTrackModes." + trackId);
        if (ret != null) {
            return ret;
        }
        ret = raceModeMapper.getTrackModes(trackId);
        InProcessCache.getInstance().set("getTrackModes." + trackId, ret);
        return ret;
    }

    public int getModeTypeById(int modeId) {
        RaceMode mode = getModeById(modeId);
        return mode != null ? mode.getModeType() : 0;
    }

    public List<Integer> getFirstModeType() {
        List<Integer> result = new ArrayList<Integer>();
        List<Integer> modeTypeList = raceModeMapper.getFirstModeType();
        if (modeTypeList != null) {
            result = modeTypeList;
        }
        return result;
    }

}
