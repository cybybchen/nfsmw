package com.ea.eamobile.nfsmw.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.ExpenseReward;
import com.ea.eamobile.nfsmw.model.mapper.ExpenseRewardMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Mar 08 13:28:36 CST 2013
 * @since 1.0
 */
@Service
public class ExpenseRewardService {

    @Autowired
    private ExpenseRewardMapper expenseRewardMapper;

    public List<ExpenseReward> getExpenseRewardList() {
        @SuppressWarnings("unchecked")
        List<ExpenseReward> ret = (List<ExpenseReward>) InProcessCache.getInstance().get("expense_reward_list");
        if (ret != null) {
            return ret;
        }
        ret = expenseRewardMapper.getExpenseRewardList();
        InProcessCache.getInstance().set("expense_reward_list", ret);
        return ret;
    }

    /**
     * 根据消费金币获取一个奖励列表 注意是累计奖励
     * 
     * @param gold
     * @return <r-index,rewardid>
     */
    public Map<Integer, Integer> getRewardMapByGold(int gold) {
        if (gold <= 0) {
            return Collections.emptyMap();
        }
        Map<Integer, Integer> map = null;
        List<ExpenseReward> expends = getExpenseRewardList();
        if (expends != null && expends.size() > 0) {
            for (int i = 0; i < expends.size(); i++) {
                ExpenseReward er = expends.get(i);
                if (gold >= er.getGold()) {
                    if (map == null) {
                        map = new HashMap<Integer, Integer>();
                    }
                    map.put(i + 1, er.getRewardId());
                } else {
                    break;
                }
            }
        }
        return map;
    }

}