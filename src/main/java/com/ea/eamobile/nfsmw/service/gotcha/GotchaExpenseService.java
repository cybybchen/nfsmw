package com.ea.eamobile.nfsmw.service.gotcha;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.cache.InProcessCache;
import com.ea.eamobile.nfsmw.model.GotchaExpense;
import com.ea.eamobile.nfsmw.model.mapper.GotchaExpenseMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 26 17:59:56 CST 2013
 * @since 1.0
 */
@Service
public class GotchaExpenseService {

    @Autowired
    private GotchaExpenseMapper gotchaExpenseMapper;

    private String buildKey(int id, int level) {
        return "getGotchaExpense." + id + "_" + level;
    }

    public GotchaExpense getGotchaExpense(int gotchaId, int level) {
        GotchaExpense ret = (GotchaExpense) InProcessCache.getInstance().get(buildKey(gotchaId, level));
        if (ret != null) {
            return ret;
        }
        ret = gotchaExpenseMapper.getGotchaExpense(gotchaId, level);
        InProcessCache.getInstance().set(buildKey(gotchaId, level), ret);
        return ret;
    }

    @SuppressWarnings("unchecked")
    public List<GotchaExpense> getGotchaExpenseList(int gotchaId) {
        List<GotchaExpense> list = (List<GotchaExpense>) InProcessCache.getInstance().get(
                "getGotchaExpenseList." + gotchaId);
        if (list == null) {
            list = gotchaExpenseMapper.getGotchaExpenseList(gotchaId);
            InProcessCache.getInstance().set("getGotchaExpenseList." + gotchaId, list);
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<GotchaExpense> getAllGotchaExpenseList() {
        List<GotchaExpense> list = (List<GotchaExpense>) InProcessCache.getInstance().get("getAllGotchaExpenseList");
        if (list == null) {
            list = gotchaExpenseMapper.getAllGotchaExpenseList();
            InProcessCache.getInstance().set("getAllGotchaExpenseList", list);
        }
        return list;
    }

}