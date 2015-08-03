package com.ea.eamobile.nfsmw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.TrackCarType;
import com.ea.eamobile.nfsmw.model.mapper.TrackCarTypeMapper;

@Service
public class TrackCarTypeService {

    @Autowired
    private TrackCarTypeMapper trackCarTypeMapper;

    public void insertTrackCarType(TrackCarType trackCarType) {
        trackCarTypeMapper.insert(trackCarType);
    }

    public void updateTrackCarType(TrackCarType trackCarType) {
        trackCarTypeMapper.update(trackCarType);
    }

    public void delete(int id) {
        trackCarTypeMapper.deleteById(id);
    }

//    public TrackCarType queryTrackCarType(int id) {
//        return trackCarTypeMapper.queryById(id);
//    }

    public List<TrackCarType> getTrackCarTypes() {
        @SuppressWarnings("unchecked")
        List<TrackCarType> ret = (List<TrackCarType>) InProcessCache.getInstance().get("trackcartype_trackcartype");
        if (ret != null) {
            return ret;
        }
        ret = trackCarTypeMapper.getTrackCarTypes();
        InProcessCache.getInstance().set("trackcartype_trackcartype", ret);
        return ret;
    }

    /**
     * 根据赛道id取对应的车类型
     * 
     * @param trackId
     * @return
     */

    public List<String> getCarTypesByTrack(String trackId) {
        List<TrackCarType> types = getTrackCarTypes();
        List<String> list = new ArrayList<String>();
        for (TrackCarType type : types) {
            if(trackId.equals(type.getTrackId()))
                list.add(type.getCarId());
        }
        return list;
    }

}
