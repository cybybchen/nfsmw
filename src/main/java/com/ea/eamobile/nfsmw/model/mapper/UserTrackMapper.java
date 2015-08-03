package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.UserTrack;

public interface UserTrackMapper {

    public UserTrack queryById(int id);

    public void insert(UserTrack userTrack);

    public void update(UserTrack userTrack);

    public void deleteById(int id);
    
    public void deleteByUserId(long userId);

    public UserTrack getUserTrackByMode(@Param("userId") long userId, @Param("modeId") int modeId);

    public List<UserTrack> getUserTracksByTrackId(@Param("userId") long userId, @Param("trackId") String trackId);

}
