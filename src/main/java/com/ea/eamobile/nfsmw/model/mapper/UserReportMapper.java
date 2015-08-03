package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.UserReport;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Apr 01 13:51:32 CST 2013
 * @since 1.0
 */
public interface UserReportMapper {

    public UserReport getUserReport(long id);

    public List<UserReport> getUserReportList();

    public int insert(UserReport userReport);

    public void update(UserReport userReport);

    public void deleteById(long id);
    
    public UserReport getUserReportByUserId(long userId);

}