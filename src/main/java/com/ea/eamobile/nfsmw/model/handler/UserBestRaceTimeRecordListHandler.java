package com.ea.eamobile.nfsmw.model.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

import com.ea.eamobile.nfsmw.model.CareerBestRacetimeRecord;

public class UserBestRaceTimeRecordListHandler implements ResultSetHandler<List<CareerBestRacetimeRecord>> {

    @Override
    public List<CareerBestRacetimeRecord> handle(ResultSet rs) throws SQLException {
        List<CareerBestRacetimeRecord> ret = new ArrayList<CareerBestRacetimeRecord>();
        while (rs.next()) {
            CareerBestRacetimeRecord rec = new CareerBestRacetimeRecord();
            rec.setId(rs.getInt("id"));
            rec.setModeType(rs.getInt("mode_type"));
            rec.setRaceTime(rs.getFloat("race_time"));
            rec.setAverageSpeed(rs.getFloat("average_speed"));
            ret.add(rec);
        }
        return ret;
    }

}
