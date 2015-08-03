package com.ea.eamobile.nfsmw.model.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

import com.ea.eamobile.nfsmw.model.UserConfig;

public class UserConfigHandler implements ResultSetHandler<UserConfig> {

    @Override
    public UserConfig handle(ResultSet rs) throws SQLException {

        while (rs.next()) {
            UserConfig ret = new UserConfig();
            ret.setId(rs.getLong("id"));
            ret.setUserId(rs.getLong("user_id"));
            ret.setType(rs.getInt("type"));
            ret.setContent(rs.getString("content"));
            return ret;
        }
        return null;

    }

}
