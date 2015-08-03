package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.TierMode;
import com.ea.eamobile.nfsmw.model.mapper.TierModeMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Sep 27 15:36:13 CST 2012
 * @since 1.0
 */
@Service
public class TierModeService {

    @Autowired
    private TierModeMapper tierModeMapper;

    public TierMode getTierMode(int tier) {
        TierMode ret = (TierMode) InProcessCache.getInstance().get("getTierMode" + tier);
        if (ret != null) {
            return ret;
        }
        ret = tierModeMapper.getTierMode(tier);
        InProcessCache.getInstance().set("getTierMode" + tier, ret);
        return ret;
    }

    public TierMode getTierModeByModeId(int modeId) {
        TierMode ret = (TierMode) InProcessCache.getInstance().get("getTierModeByModeId" + modeId);
        if (ret != null) {
            return ret;
        }
        ret = tierModeMapper.getTierModeByModeId(modeId);
        InProcessCache.getInstance().set("getTierModeByModeId" + modeId, ret);
        return ret;
    }

    public List<TierMode> getTierModeList() {
        return tierModeMapper.getTierModeList();
    }

    public int insert(TierMode tierMode) {
        return tierModeMapper.insert(tierMode);
    }

    public void update(TierMode tierMode) {
        tierModeMapper.update(tierMode);
    }

    public void deleteById(int tier) {
        tierModeMapper.deleteById(tier);
    }

}