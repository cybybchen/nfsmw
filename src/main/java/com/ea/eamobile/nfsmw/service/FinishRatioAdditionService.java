package com.ea.eamobile.nfsmw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.FinishRatioAddition;
import com.ea.eamobile.nfsmw.model.mapper.FinishRatioAdditionMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Mar 13 14:32:03 CST 2013
 * @since 1.0
 */
@Service
public class FinishRatioAdditionService {

    @Autowired
    private FinishRatioAdditionMapper finishRatioAdditionMapper;

    public FinishRatioAddition getFinishRatioAddition(int modeId, int type) {
        FinishRatioAddition ret = (FinishRatioAddition) InProcessCache.getInstance().get(
                "getFinishRatioAddition." + modeId + "." + type);
        if (ret != null) {
            return ret;
        }
        ret = finishRatioAdditionMapper.getFinishRatioAddition(modeId, type);
        InProcessCache.getInstance().set("getFinishRatioAddition." + modeId + "." + type, ret);
        return ret;

    }

}