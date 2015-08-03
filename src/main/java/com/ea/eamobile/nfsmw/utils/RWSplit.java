package com.ea.eamobile.nfsmw.utils;

import java.sql.SQLException;

public class RWSplit {

    private static RWSplit instance = new RWSplit();

    public static RWSplit getInstance() {
        return instance;
    }

    private DataSource writeDataSource = new DataSource(ConfigUtil.JDBC_URL);
    private DataSource readDataSource = new DataSource(ConfigUtil.JDBC_READONLY_URL);
    private DataSource readDataBackupSource = new DataSource(ConfigUtil.JDBC_READONLY_BACKUP_URL);

    public javax.sql.DataSource getWriteDataSource() throws SQLException {
        return writeDataSource;
    }

    public javax.sql.DataSource getReadDataSource() throws SQLException {
        return readDataSource;
    }
    
    public javax.sql.DataSource getBackupReadDataSource() throws SQLException {
        return readDataBackupSource;
    }
}
