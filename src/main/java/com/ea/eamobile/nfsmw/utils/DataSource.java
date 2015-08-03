package com.ea.eamobile.nfsmw.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSource extends org.apache.commons.dbcp.BasicDataSource {

    private static final Logger log = LoggerFactory.getLogger(DataSource.class);

    public DataSource() {
        this.driverClassName = ConfigUtil.JDBC_DRIVER;
        this.testOnBorrow = true;
        this.testWhileIdle = true;
        this.validationQuery = "SELECT 1";
        
        this.setInitialSize(10);
        this.setMaxActive(512);
        this.setMaxIdle(10);
        this.setMinIdle(2);
        this.setMinEvictableIdleTimeMillis(50 * 1000L);
        this.setNumTestsPerEvictionRun(100);
        this.setTimeBetweenEvictionRunsMillis(30 * 1000L);
        
        this.url = ConfigUtil.JDBC_URL;
        this.username = ConfigUtil.JDBC_USERNAME;
        this.password = ConfigUtil.JDBC_PASSWORD;
        log.info("DataSource initialized with {} ", this.url);
    }

    public DataSource(String url) {
        this.driverClassName = ConfigUtil.JDBC_DRIVER;
        this.testOnBorrow = true;
        this.testWhileIdle = true;
        this.validationQuery = "SELECT 1";
        
        this.setInitialSize(10);
        this.setMaxActive(512);
        this.setMaxIdle(10);
        this.setMinIdle(2);
        this.setMinEvictableIdleTimeMillis(50 * 1000L);
        this.setNumTestsPerEvictionRun(100);
        this.setTimeBetweenEvictionRunsMillis(30 * 1000L);
        
        this.url = url;
        this.username = ConfigUtil.JDBC_USERNAME;
        this.password = ConfigUtil.JDBC_PASSWORD;
        log.info("DataSource initialized with {} ", this.url);
    }
}
