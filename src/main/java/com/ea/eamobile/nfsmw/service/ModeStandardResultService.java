package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.ModeStandardResult;
import com.ea.eamobile.nfsmw.model.mapper.ModeStandardResultMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Fri May 24 17:51:48 CST 2013
 * @since 1.0
 */
@Service
public class ModeStandardResultService {

    @Autowired
    private ModeStandardResultMapper modeStandardResultMapper;

    public ModeStandardResult getModeStandardResult(int modeId, String carId) {
        ModeStandardResult ret = (ModeStandardResult) InProcessCache.getInstance().get(
                "getModeStandardResult." + modeId + "." + carId);
        if (ret != null) {
            return ret;
        }
        ret = modeStandardResultMapper.getModeStandardResult(modeId, carId);
        InProcessCache.getInstance().set("getModeStandardResult." + modeId + "." + carId, ret);
        return ret;

    }

    public List<ModeStandardResult> getModeStandardResultList() {
        return modeStandardResultMapper.getModeStandardResultList();
    }

    public int insert(ModeStandardResult modeStandardResult) {
        return modeStandardResultMapper.insert(modeStandardResult);
    }

    public void update(ModeStandardResult modeStandardResult) {
        modeStandardResultMapper.update(modeStandardResult);
    }

    public void deleteById(int id) {
        modeStandardResultMapper.deleteById(id);
    }

}