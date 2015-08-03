package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.ExpenseReward;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Mar 08 13:28:36 CST 2013
 * @since 1.0
 */
public interface ExpenseRewardMapper {

    public ExpenseReward getExpenseReward(int gold);

    public List<ExpenseReward> getExpenseRewardList();

}