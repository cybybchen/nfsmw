package com.ea.eamobile.nfsmw.service;

import com.ea.eamobile.nfsmw.model.ProfileTrackLog;
import com.ea.eamobile.nfsmw.model.mapper.ProfileTrackLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ma.ruofei
 * @version 1.0 Tue May 21 10:58:49 CST 2013
 * @since 1.0
 */
 @Service
public class ProfileTrackLogService {

    @Autowired
    private ProfileTrackLogMapper profileTrackLogMapper;

    public int insert(ProfileTrackLog profileTrackLog){
        return profileTrackLogMapper.insert(profileTrackLog);
    }

}