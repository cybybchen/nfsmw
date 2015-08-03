package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.Track;

public interface TrackMapper {

    public Track queryById(int id);

    public void insert(Track track);

    public void update(Track track);

    public void deleteById(int id);

    public List<Track> getTracks();

    public List<Track> getTracksByTier(int tier);

    public int getTierCount();

    public List<Track> queryByTierAndStarNum(@Param("tier") int tier, @Param("star") int star);

}
