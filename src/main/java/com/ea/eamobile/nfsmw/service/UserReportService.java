package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.UserReport;
import com.ea.eamobile.nfsmw.model.mapper.UserReportMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Apr 01 13:51:34 CST 2013
 * @since 1.0
 */
@Service
public class UserReportService {

    @Autowired
    private UserReportMapper userReportMapper;

    public UserReport getUserReport(long id) {
        return userReportMapper.getUserReport(id);
    }

    public List<UserReport> getUserReportList() {
        return userReportMapper.getUserReportList();
    }

    public int insert(UserReport userReport) {
        return userReportMapper.insert(userReport);
    }

    public void update(UserReport userReport) {
        userReportMapper.update(userReport);
    }

    public void deleteById(long id) {
        userReportMapper.deleteById(id);
    }

    public void add(long userId, long reportedId) {
        // report
        UserReport userReport = userReportMapper.getUserReportByUserId(userId);
        if (userReport != null) {
            userReport.setReportCount(userReport.getReportCount() + 1);
            userReportMapper.update(userReport);
        } else {
            userReport = new UserReport();
            userReport.setUserId(userId);
            userReport.setReportCount(1);
            userReport.setReportedCount(0);
            userReportMapper.insert(userReport);
        }

        // reported
        UserReport userReported = userReportMapper.getUserReportByUserId(reportedId);
        if (userReported != null) {
            userReported.setReportedCount(userReported.getReportedCount() + 1);
            userReportMapper.update(userReported);
        } else {
            userReported = new UserReport();
            userReported.setUserId(reportedId);
            userReported.setReportCount(0);
            userReported.setReportedCount(1);
            userReportMapper.insert(userReported);
        }
    }

}