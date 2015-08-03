package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.OperateActivity;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Mar 22 12:02:03 CST 2013
 * @since 1.0
 */
public interface OperateActivityMapper {

    public OperateActivity getOperateActivity(int id);

    public OperateActivity getOperateActivityByTime(int time);
    
    public Integer getMaxEndTime();

    public List<OperateActivity> getOperateActivityList();

    public int insert(OperateActivity operateActivity);

    public void update(OperateActivity operateActivity);

    public void deleteById(int id);

}