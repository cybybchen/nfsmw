package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.Track;
import com.ea.eamobile.nfsmw.model.mapper.TrackMapper;

@Service
public class TrackService {

    @Autowired
    private TrackMapper trackMapper;

    public void insertTrack(Track track) {
        trackMapper.insert(track);
    }

    public void updateTrack(Track track) {
        trackMapper.update(track);
    }

    public void delete(int id) {
        trackMapper.deleteById(id);
    }


    public Track queryTrack(int id) {
        Track ret = (Track) InProcessCache.getInstance().get("track_track_"+id);
        if (ret != null) {
            return ret;
        }
        ret = trackMapper.queryById(id);
        InProcessCache.getInstance().set("track_track_"+id, ret);
        return ret;
    }

    public List<Track> getTracksByTier(int tier) {
        @SuppressWarnings("unchecked")
        List<Track> ret = (List<Track>) InProcessCache.getInstance().get("track_tier_"+tier);
        if (ret != null) {
            return ret;
        }
        ret = trackMapper.getTracksByTier(tier);
        InProcessCache.getInstance().set("track_tier_"+tier, ret);
        return ret;
    }

    /**
     * 取Tier的总数量
     * @return
     */

    public int getTierCount() {
        Integer ret = (Integer) InProcessCache.getInstance().get("track_tier_count");
        if (ret != null) {
            return ret;
        }
        ret = trackMapper.getTierCount();
        InProcessCache.getInstance().set("track_tier_count", ret);
        return ret;
    }
    

    public List<Track> queryByTierAndStarNum(int tier,int star) {
        return trackMapper.queryByTierAndStarNum(tier, star);
    }

}
