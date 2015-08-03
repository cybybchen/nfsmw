package com.ea.eamobile.nfsmw.model.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

import com.ea.eamobile.nfsmw.model.UserTrack;

public class UserTrackListHandler implements ResultSetHandler<List<UserTrack>> {

    @Override
    public List<UserTrack> handle(ResultSet rs) throws SQLException {
        List<UserTrack> ret = new ArrayList<UserTrack>();
        while (rs.next()) {
            UserTrack ut = new UserTrack();
            ut.setId(rs.getInt("id"));
            ut.setUserId(rs.getInt("user_id"));
            ut.setTrackId(rs.getString("track_id"));
            ut.setModeId(rs.getInt("mode_id"));
            ut.setValue(rs.getInt("value"));
            ut.setIsNew(rs.getInt("is_new"));
            ut.setIsFinish(rs.getInt("is_finish"));
            ret.add(ut);
        }
        return ret;
    }

}
