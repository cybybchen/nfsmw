package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.TierMode;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Sep 27 15:36:13 CST 2012
 * @since 1.0
 */
public interface TierModeMapper {

    public TierMode getTierMode(int tier);

    public TierMode getTierModeByModeId(int modeId);

    public List<TierMode> getTierModeList();

    public int insert(TierMode tierMode);

    public void update(TierMode tierMode);

    public void deleteById(int tier);

}