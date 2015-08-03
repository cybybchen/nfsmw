package com.ea.eamobile.nfsmw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.ModeDistance;
import com.ea.eamobile.nfsmw.model.mapper.ModeDistanceMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Feb 22 14:39:54 CST 2013
 * @since 1.0
 */
@Service
public class ModeDistanceService {

    @Autowired
    private ModeDistanceMapper modeDistanceMapper;

    public ModeDistance getModeDistance(int modeId) {
        ModeDistance ret = (ModeDistance) InProcessCache.getInstance().get("getModeDistance."+modeId);
        if (ret != null) {
            return ret;
        }
        ret = modeDistanceMapper.getModeDistance(modeId);
        InProcessCache.getInstance().set("getModeDistance."+modeId, ret);
        return ret;
    }

    public List<ModeDistance> getModeDistanceList() {
        List<ModeDistance> result = modeDistanceMapper.getModeDistanceList();
        if (result == null) {
            result = new ArrayList<ModeDistance>();
        }
        return result;
    }

}