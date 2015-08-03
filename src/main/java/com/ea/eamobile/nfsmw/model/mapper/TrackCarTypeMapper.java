package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.TrackCarType;

public interface TrackCarTypeMapper {

    public TrackCarType queryById(int id);

    public void insert(TrackCarType trackCarType);

    public void update(TrackCarType trackCarType);

    public void deleteById(int id);

    public List<TrackCarType> getTrackCarTypes();

}
