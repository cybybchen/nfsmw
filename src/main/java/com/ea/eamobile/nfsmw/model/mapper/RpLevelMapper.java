package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.RpLevel;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Oct 17 15:58:53 CST 2012
 * @since 1.0
 */
public interface RpLevelMapper {

    public RpLevel getRpLevel(int level);

    public int getLevelByRpNum(int rpNum);

    public List<RpLevel> getLevelList();

}