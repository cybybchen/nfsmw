package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.FinishRatio;
import com.ea.eamobile.nfsmw.model.RaceMode;
import com.ea.eamobile.nfsmw.model.mapper.FinishRatioMapper;

@Service
public class FinishRatioService {

    @Autowired
    FinishRatioMapper ratioMapper;

    public List<FinishRatio> getFinishRatios() {
        @SuppressWarnings("unchecked")
        List<FinishRatio> ret = (List<FinishRatio>) InProcessCache.getInstance().get("finish_ratio_get");
        if (ret != null) {
            return ret;
        }
        ret = ratioMapper.getFinishRatios();
        InProcessCache.getInstance().set("finish_ratio_get", ret);
        return ret;
    }

    /**
     * 根据mode类型和名次取完成度
     * 
     * @param type
     * @param rank
     * @return
     */
    public int getFinishRatioValue(int modeType, int rank) {
        List<FinishRatio> list = getFinishRatios();
        for (FinishRatio ratio : list) {
            if (ratio.getModeType() == modeType && ratio.getRank() == rank) {
                return ratio.getValue();
            }
        }
        return 0;
    }

    public FinishRatio getFinishRatio(int finishRatioType, int rank) {
        List<FinishRatio> list = getFinishRatios();
        for (FinishRatio ratio : list) {
            if (ratio.getModeType() == finishRatioType && ratio.getRank() == rank) {
                return ratio;
            }
        }
        return null;
    }

    /**
     * 取完成度百分比数值
     * 
     * @param modeType
     * @param rank
     * @return
     */
    public int getFinishRatioPercentValue(RaceMode mode, int rank) {
        int finishRatio = getFinishRatioValue(mode.getFinishRatioType(), rank);
        float ratio = ((float) finishRatio / mode.getValue()) * 100;
        return Math.min(100, (int) Math.floor(ratio));
    }
}
