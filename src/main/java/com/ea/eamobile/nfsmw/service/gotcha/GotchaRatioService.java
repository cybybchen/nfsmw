package com.ea.eamobile.nfsmw.service.gotcha;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.GotchaRatio;
import com.ea.eamobile.nfsmw.model.mapper.GotchaRatioMapper;
import com.ea.eamobile.nfsmw.utils.NumberUtil;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 26 18:00:24 CST 2013
 * @since 1.0
 */
@Service
public class GotchaRatioService {

    @Autowired
    private GotchaRatioMapper gotchaRatioMapper;

    public GotchaRatio getGotchaRatio(int id) {
        return gotchaRatioMapper.getGotchaRatio(id);
    }

    @SuppressWarnings("unchecked")
    public List<GotchaRatio> getGotchaRatioList(int gotchaId) {
        List<GotchaRatio> list = (List<GotchaRatio>) InProcessCache.getInstance().get("getGotchaRatioList." + gotchaId);
        if (list == null) {
            list = gotchaRatioMapper.getGotchaRatioList(gotchaId);
            InProcessCache.getInstance().set("getGotchaRatioList" + gotchaId, list);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    private List<Integer> getRateList(int gotchaId) {
        List<Integer> list = (List<Integer>) InProcessCache.getInstance().get(
                "GotchaRatioService.getRateList." + gotchaId);
        if (list == null) {
            List<GotchaRatio> ratios = getGotchaRatioList(gotchaId);
            if (ratios == null || ratios.size() == 0) {
                return Collections.emptyList();
            }
            list = new ArrayList<Integer>();
            for (GotchaRatio ratio : ratios) {
                list.add(ratio.getWeight());
            }
            InProcessCache.getInstance().set("GotchaRatioService.getRateList." + gotchaId, list);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    private List<Integer> getRewardList(int gotchaId) {
        List<Integer> list = (List<Integer>) InProcessCache.getInstance().get(
                "GotchaRatioService.getRewardList." + gotchaId);
        if (list == null) {
            List<GotchaRatio> ratios = getGotchaRatioList(gotchaId);
            if (ratios == null || ratios.size() == 0) {
                return Collections.emptyList();
            }
            list = new ArrayList<Integer>();
            for (GotchaRatio ratio : ratios) {
                list.add(ratio.getRewardId());
            }
            InProcessCache.getInstance().set("GotchaRatioService.getRewardList." + gotchaId, list);
        }
        return list;
    }

    /**
     * 随机生成奖励id
     * @param gotchaId
     * @return
     */
    public int getRandomReward(int gotchaId) {
        List<Integer> rates = getRateList(gotchaId);
        List<Integer> rewards = getRewardList(gotchaId);
        if (rates == null || rewards == null) {
            return 0;
        }
        return Math.max(0,NumberUtil.randomRate(rates, rewards));
    }

    public int insert(GotchaRatio gotchaRatio) {
        return gotchaRatioMapper.insert(gotchaRatio);
    }

    public void update(GotchaRatio gotchaRatio) {
        gotchaRatioMapper.update(gotchaRatio);
    }

    public void deleteById(int id) {
        gotchaRatioMapper.deleteById(id);
    }

}