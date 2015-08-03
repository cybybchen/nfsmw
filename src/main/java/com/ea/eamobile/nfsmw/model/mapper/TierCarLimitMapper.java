package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.TierCarLimit;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Nov 29 14:38:39 CST 2012
 * @since 1.0
 */
public interface TierCarLimitMapper {

    public TierCarLimit getTierCarLimit(int id);

    public List<TierCarLimit> getTierCarLimitList();
    
    public List<String> getTierCarLimitListByTierId(int tierId);

    public int insert(TierCarLimit tierCarLimit);

    public void update(TierCarLimit tierCarLimit);

    public void deleteById(int id);

}