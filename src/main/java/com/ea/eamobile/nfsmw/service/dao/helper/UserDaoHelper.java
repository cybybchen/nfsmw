package com.ea.eamobile.nfsmw.service.dao.helper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.service.dao.helper.util.GeneralDaoDbHelper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.GeneralDaoDbMemcachedHelper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.GeneralDaoMemcachedHelper;

public class UserDaoHelper extends GeneralDaoDbMemcachedHelper<UserDataHelper> {

    private static final UserDaoDbHelper dbHelper = new UserDaoDbHelper();
    private static final UserDaoMemcachedHelper cacheHelper = new UserDaoMemcachedHelper();

    public UserDaoHelper() {
        super(dbHelper, cacheHelper);
    }

    public UserDataHelper getSingleByField(String field, String value) {
        return dbHelper.getSingleByField(field, value);
    }

    public List<UserDataHelper> getByField(String field, String[] values) {
        return dbHelper.getByField(field, values);
    }

    @SuppressWarnings("unchecked")
    public List<User> getTopHundredUser() {
        String sql = "SELECT * from user order by rp_num desc limit 100";
        return (List<User>) dbHelper.query(sql, dbHelper.new DbDataListHandler());
    }

    public Integer getResouceVersionUserCount(int version) {
        String sql = "SELECT count(1) from user where version = " + version;

        return (Integer) dbHelper.query(sql, new DbIntegerHandler());
    }

    public int getNickNameCount(String nickname) {
        String sql = "SELECT count(*) from user where name = \"" + nickname + "\"";

        return (Integer) dbHelper.query(sql, new DbIntegerHandler());
    }

    public Integer getRpRank(int rpNum) {
        String sql = "SELECT count(*) from user where " + rpNum + " < rp_num";

        return (Integer) dbHelper.query(sql, new DbIntegerHandler());
    }

    public void updateConsumeEnergy(long userId, int reduceEnergyNum) {

        String sql = "UPDATE user SET energy=energy-" + reduceEnergyNum + " WHERE id = " + userId;
        update(sql, userId);
    }

    public void updateRpNum(long userId, int reduceEnergyNum) {
        String sql = "UPDATE user SET rp_num=rp_num+" + reduceEnergyNum + " WHERE id = " + userId;
        update(sql, userId);
    }

    public void updateStarNum(long userId, int addStarNum) {
        String sql = "UPDATE user SET star_num=star_num+" + addStarNum + " WHERE id = " + userId;
        update(sql, userId);
    }

    public void updateReward(long userId, int gainRpNum, int gainStarNum, int money, int gold, int energy) {
        String sql = "UPDATE user SET rp_num=rp_num+" + gainRpNum + ",star_num=star_num+" + gainStarNum
                + ",money=money+" + money + ",gold=gold+" + gold + ",energy=energy+" + energy + " WHERE id = " + userId;
        update(sql, userId);
    }

    public void updateStatusByIds(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder("UPDATE user SET account_status=" + (String) params.get("accountStatus")
                + " where id in (");
        @SuppressWarnings("unchecked")
        Collection<Object> collection = (Collection<Object>) params.get("item");
        List<String> keys = new ArrayList<String>();
        int dataMapSize = collection.size();
        int index = 0;
        for (Object o : collection) {
            String str = (String) o;
            sql.append(str);
            keys.add(str);
            if (++index != dataMapSize) {
                sql.append(", ");
            }
        }
        sql.append(")");
        update(sql.toString(), keys);
    }

    protected static class UserDaoDbHelper extends GeneralDaoDbHelper<UserDataHelper> {

        protected UserDaoDbHelper() {
            super("user", "user", "id");
        }

        @Override
        protected UserDataHelper create() {
            return new UserDataHelper();
        }

    }

    protected static class UserDaoMemcachedHelper extends GeneralDaoMemcachedHelper<UserDataHelper> {

        @Override
        protected String getPrefix() {
            return CacheKey.USER;
        }

    }

    protected static class DbLongHandler implements ResultSetHandler<Long> {

        @Override
        public Long handle(ResultSet rs) throws SQLException {
            if (rs.next()) {
                return rs.getLong(1);
            }
            return 0l;
        }
    }

    protected static class DbIntegerHandler implements ResultSetHandler<Integer> {

        @Override
        public Integer handle(ResultSet rs) throws SQLException {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }
}
