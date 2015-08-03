package com.ea.eamobile.nfsmw.model.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;

public class Inthandler implements ResultSetHandler<Integer> {

    @Override
    public Integer handle(ResultSet rs) throws SQLException {
        while (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

}
