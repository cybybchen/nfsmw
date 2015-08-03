package com.ea.eamobile.nfsmw.model.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

import com.ea.eamobile.nfsmw.model.UserChartlet;

public class UserChartLetHandler implements ResultSetHandler<UserChartlet> {

    @Override
    public UserChartlet handle(ResultSet rs) throws SQLException {

        while (rs.next()) {
            UserChartlet ret = new UserChartlet();
            ret.setChartletId(rs.getInt("chartlet_id"));
            ret.setId(rs.getInt("id"));
            ret.setIsOwned(rs.getInt("is_owned"));
            ret.setRentTime(rs.getTimestamp("rent_time"));
            ret.setUserId(rs.getLong("user_Id"));
            return ret;
        }
        return null;

    }

}
