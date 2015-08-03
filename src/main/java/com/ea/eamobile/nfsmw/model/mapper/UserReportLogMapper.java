package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.UserReportLog;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Apr 01 13:51:32 CST 2013
 * @since 1.0
 */
public interface UserReportLogMapper {

    public UserReportLog getUserReportLog(long id);

    public List<UserReportLog> getUserReportLogList();

    public int insert(UserReportLog userReportLog);

    public void update(UserReportLog userReportLog);

    public void deleteById(long id);
    
    public UserReportLog getUserReportLogByIdAndData(@Param("userId")long userId, @Param("reportedId")long reportedId, @Param("reportDate")int reportDate);

}