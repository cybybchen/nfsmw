package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.ModeDistance;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Feb 22 14:39:54 CST 2013
 * @since 1.0
 */
public interface ModeDistanceMapper {


    public ModeDistance getModeDistance(int modeId);

    public List<ModeDistance> getModeDistanceList();

}