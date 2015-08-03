package com.ea.eamobile.nfsmw.service.dao.helper.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ea.eamobile.nfsmw.utils.ConfigUtil;

import net.sinofool.dbpool.DBPool;

// TODO we user e.printStackTrace in this class, this may cause problems,
// we may delete them if we need to
public abstract class GeneralDaoDbHelper<T extends GeneralDataHelper> extends GeneralDaoHelper<T> {

    private static final Logger logger = LoggerFactory.getLogger(GeneralDaoDbHelper.class);

    private String dbInstance = null;
    private String dbTable = null;
    private String dbKey = null;
    private String logStr = null;
    private static final String dbFieldPlaceHolder = "?dbFieldPlaceHolderForLog?";
    private static final String dbKeyPlaceHolder = "?dbKeyPlaceHolderForLog?";
    private static String sqlPlaceHolder = "?sqlPlaceHolderForLog?";

    private static final DBPool dbPool = new DBPool();

    static {
        dbPool.initialize(ConfigUtil.DBPOOL_PROXY);
    }

    protected GeneralDaoDbHelper(String dbInstance, String dbTable, String dbKey) {
        this.dbInstance = dbInstance;
        this.dbTable = dbTable;
        this.dbKey = dbKey;
        logStr = " DbInstance = " + dbInstance + " DbTable = " + dbTable + " DbKey(" + dbFieldPlaceHolder + ") = "
                + dbKeyPlaceHolder + " Sql = " + sqlPlaceHolder + " ";
    }

    protected abstract T create();

    @Override
    public Map<String, T> get(List<String> keys) {
        DataSource dataSource = dbPool.getDataSource(dbInstance, DBPool.READ_ACCESS, "");

        QueryRunner qr = new QueryRunner(dataSource);

        String sql = buildSelectSql(dbKey, keys);

        logger.debug("get Sql = " + sql);

        try {
            Map<String, T> rs = qr.query(sql, new DbDataHandler());
            // TODO check rs, if not get enough data log it
            return rs;
        } catch (SQLException e) {
            if (logger.isWarnEnabled()) {
                StringBuilder keyStr = new StringBuilder("");
                for (String key : keys) {
                    keyStr.append(key + " ");
                }
                logger.warn("get"
                        + logStr.replace(dbFieldPlaceHolder, dbKey).replace(dbKeyPlaceHolder, keyStr.toString())
                                .replace(sqlPlaceHolder, sql) + e);
            }
        }

        return new HashMap<String, T>();
    }

    @Override
    public Integer set(String key, T value) {
        DataSource dataSource = dbPool.getDataSource(dbInstance, DBPool.WRITE_ACCESS, "");

        QueryRunner qr = new QueryRunner(dataSource);

        String sql = buildUpdateSql(key, value);

        if (sql == null) {
            logger.warn("set"
                    + logStr.replace(dbFieldPlaceHolder, dbKey).replace(dbKeyPlaceHolder, key)
                            .replace(sqlPlaceHolder, "") + " sql is null!");
            return 0;
        } else {
            logger.info("set Sql = " + sql);
        }

        try {
            return qr.update(sql);
        } catch (SQLException e) {
            logger.error("set"
                    + logStr.replace(dbFieldPlaceHolder, dbKey).replace(dbKeyPlaceHolder, key)
                            .replace(sqlPlaceHolder, sql) + e);
        }

        return 0;
    }

    @Override
    public Integer delete(String key) {
        DataSource dataSource = dbPool.getDataSource(dbInstance, DBPool.WRITE_ACCESS, "");

        QueryRunner qr = new QueryRunner(dataSource);

        String sql = buildDeleteSql(key);

        logger.debug("delete Sql = " + sql);

        try {
            return qr.update(sql);
        } catch (SQLException e) {
            logger.error("delete"
                    + logStr.replace(dbFieldPlaceHolder, dbKey).replace(dbKeyPlaceHolder, key)
                            .replace(sqlPlaceHolder, sql) + e);
        }

        return 0;
    }

    @Override
    public String getIdentityString() {
        return "DbInstance = " + dbInstance + " DbTable = " + dbTable + " DbKey = " + dbKey;
    }

    public Integer insert(Long key, T value) {
        return insert(String.valueOf(key), value);
    }

    /*
     * return the generated key from server
     */
    public Long insert(T value) {
        DataSource dataSource = dbPool.getDataSource(dbInstance, DBPool.WRITE_ACCESS, "");

        String sql = buildInsertSql(value);

        if (sql == null) {
            logger.debug("insert"
                    + logStr.replace(dbFieldPlaceHolder, dbKey).replace(dbKeyPlaceHolder, "generatedKey")
                            .replace(sqlPlaceHolder, "") + " sql is null!");
            return -1L;
        } else {
            logger.debug("insert Sql = " + sql);
        }

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            logger.debug("insert err ", e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                DbUtils.close(conn);
            } catch (SQLException e) {
            }
        }

        return -1L;
    }

    public Integer insert(String key, T value) {
        DataSource dataSource = dbPool.getDataSource(dbInstance, DBPool.WRITE_ACCESS, "");

        QueryRunner qr = new QueryRunner(dataSource);

        String sql = buildInsertSql(value);

        if (sql == null) {
            logger.warn("insert"
                    + logStr.replace(dbFieldPlaceHolder, dbKey).replace(dbKeyPlaceHolder, key)
                            .replace(sqlPlaceHolder, "") + " sql is null!");
            return 0;
        } else {
            logger.warn("insert Sql = " + sql);
        }

        try {
            return qr.update(sql);
        } catch (SQLException e) {
            logger.error("insert err ", e.getMessage());
        }

        return 0;
    }

    public List<T> getByField(String field, List<String> values) {
        DataSource dataSource = dbPool.getDataSource(dbInstance, DBPool.READ_ACCESS, "");

        QueryRunner qr = new QueryRunner(dataSource);

        String sql = buildSelectSql(field, values);

        logger.debug("getByField Sql = " + sql);

        try {
            return qr.query(buildSelectSql(field, values), new DbDataListHandler());
        } catch (SQLException e) {
            logger.error("get by field err errorcode is {}, msg is {}",  e.getErrorCode() , e.getMessage());
        }

        return new ArrayList<T>();
    }

    public class DbDataListHandler implements ResultSetHandler<List<T>> {

        @Override
        public List<T> handle(ResultSet rs) throws SQLException {
            List<T> dataList = new ArrayList<T>();
            while (rs.next()) {
                T data = create();
                data.parseDbData(rs);
                dataList.add(data);
            }
            return dataList;
        }
    }

    public List<T> getByField(String field, String[] values) {
        List<String> valueList = new ArrayList<String>(Arrays.asList(values));
        return getByField(field, valueList);
    }

    public List<T> getByField(String field, String value) {
        List<String> values = new ArrayList<String>();
        values.add(value);
        return getByField(field, values);
    }

    public T getSingleByField(String field, String value) {
        List<String> values = new ArrayList<String>();
        values.add(value);
        return getSingleByField(field, values);
    }

    public T getSingleByField(String field, List<String> values) {
        List<T> rs = getByField(field, values);
        if (!rs.isEmpty()) {
            return rs.get(0);
        }

        return null;
    }

    public class DbDataHandler implements ResultSetHandler<Map<String, T>> {

        @Override
        public Map<String, T> handle(ResultSet rs) throws SQLException {
            Map<String, T> dataMap = new HashMap<String, T>();
            while (rs.next()) {
                String key = rs.getString(dbKey);
                T data = create();
                data.parseDbData(rs);
                dataMap.put(key, data);
            }
            return dataMap;
        }
    }

    public Object query(String sql, ResultSetHandler<?> rsHandler) {
        DataSource dataSource = dbPool.getDataSource(dbInstance, DBPool.READ_ACCESS, "");

        QueryRunner qr = new QueryRunner(dataSource);

        logger.debug("query Sql = " + sql);

        try {
            return qr.query(sql, rsHandler);
        } catch (SQLException e) {
            logger.error("query Sql = " + sql + " get exception " + e);
        }

        return null;
    }

    public int update(String sql) {
        DataSource dataSource = dbPool.getDataSource(dbInstance, DBPool.WRITE_ACCESS, "");

        QueryRunner qr = new QueryRunner(dataSource);

        logger.debug("update Sql = " + sql);

        try {
            return qr.update(sql);
        } catch (SQLException e) {
            logger.error("update Sql = " + sql + " get exception " + e);
        }

        return 0;
    }

    protected String buildSelectSql(String field, List<String> keys) {
        StringBuffer keysSql = new StringBuffer();
        int listSize = keys.size();
        int index = 0;
        for (String key : keys) {
            keysSql.append("\"");
            keysSql.append(key);
            keysSql.append("\"");
            if (++index != listSize) {
                keysSql.append(", ");
            }
        }

        return "SELECT * FROM " + dbTable + " where " + field + " IN ( " + keysSql.toString() + " )";
    }

    protected String buildUpdateSql(String key, T data) {
        Map<String, String> dataMap = data.getDataMap();
        if (dataMap.isEmpty()) {
            return null;
        }

        data.deleteIgnoreDataMap(dataMap);

        StringBuilder sql = new StringBuilder("UPDATE " + dbTable + " SET ");

        int dataMapSize = dataMap.size();
        int index = 0;

        for (Entry<String, String> ent : dataMap.entrySet()) {
            sql.append(ent.getKey()).append(" = '").append(ent.getValue()).append("' ");
            if (++index != dataMapSize) {
                sql.append(",");
            }
        }

        sql.append("WHERE " + dbKey + " = ").append(key);
        return sql.toString();
    }

    protected String buildDeleteSql(String key) {
        return "DELETE FROM " + dbTable + " WHERE " + dbKey + " = " + key;
    }

    protected String buildInsertSql(T data) {
        Map<String, String> dataMap = data.getDataMap();
        data.addDefaultDataMap(dataMap);
        if (dataMap.isEmpty()) {
            return null;
        }

        int dataMapSize = dataMap.size();
        int index = 0;

        StringBuilder keyList = new StringBuilder(" ( ");
        StringBuffer valueList = new StringBuffer();
        valueList.append("(");

        for (Entry<String, String> ent : dataMap.entrySet()) {
            keyList.append(ent.getKey());
            valueList.append("\"").append(ent.getValue()).append("\"");
            if (++index != dataMapSize) {
                keyList.append(", ");
                valueList.append(", ");
            }
        }

        keyList.append(" ) ");
        valueList.append(" ) ");

        return "INSERT INTO " + dbTable + keyList.toString() + "VALUES" + valueList.toString();
    }

}
