package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;
import com.ea.eamobile.nfsmw.model.SpendActivity;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Apr 17 14:36:58 CST 2013
 * @since 1.0
 */
public interface SpendActivityMapper {

    public SpendActivity getSpendActivity(int id);

    public List<SpendActivity> getSpendActivityList();

    public int insert(SpendActivity spendActivity);

    public void update(SpendActivity spendActivity);

    public void deleteById(int id);

}