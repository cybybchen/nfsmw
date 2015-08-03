package com.ea.eamobile.nfsmw.model.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

public class IntListHandler implements ResultSetHandler<List<Integer>> {

    @Override
    public List<Integer> handle(ResultSet rs) throws SQLException {
        List<Integer> ret = new ArrayList<Integer>();
        while (rs.next()) {
            ret.add(rs.getInt(1));
        }
        return ret;
    }

}
