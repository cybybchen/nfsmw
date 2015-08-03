package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.TierCarLimit;
import com.ea.eamobile.nfsmw.model.mapper.TierCarLimitMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Nov 29 14:38:39 CST 2012
 * @since 1.0
 */
@Service
public class TierCarLimitService {

    @Autowired
    private TierCarLimitMapper tierCarLimitMapper;

    public TierCarLimit getTierCarLimit(int id) {
        TierCarLimit ret = (TierCarLimit) InProcessCache.getInstance().get("getTierCarLimit" + id);
        if (ret != null) {
            return ret;
        }
        ret = tierCarLimitMapper.getTierCarLimit(id);
        InProcessCache.getInstance().set("getTierCarLimit" + id, ret);
        return ret;
    }

    public List<String> getTierCarLimitListByTierId(int tierId) {
        @SuppressWarnings("unchecked")
        List<String> ret = (List<String>) InProcessCache.getInstance().get("getTierCarLimitListByTierId" + tierId);
        if (ret != null) {
            return ret;
        }
        ret = tierCarLimitMapper.getTierCarLimitListByTierId(tierId);
        InProcessCache.getInstance().set("getTierCarLimitListByTierId" + tierId, ret);
        return ret;
    }

    public int insert(TierCarLimit tierCarLimit) {
        return tierCarLimitMapper.insert(tierCarLimit);
    }

    public void update(TierCarLimit tierCarLimit) {
        tierCarLimitMapper.update(tierCarLimit);
    }

    public void deleteById(int id) {
        tierCarLimitMapper.deleteById(id);
    }

}