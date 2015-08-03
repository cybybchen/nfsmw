package com.ea.eamobile.nfsmw.model.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

import com.ea.eamobile.nfsmw.model.TournamentUser;

public class TournamentUserListHandler implements ResultSetHandler<List<TournamentUser>> {

    @Override
    public List<TournamentUser> handle(ResultSet rs) throws SQLException {
        List<TournamentUser> list = new ArrayList<TournamentUser>();
        while (rs.next()) {
            TournamentUser user = new TournamentUser();
            user.setTournamentOnlineId(rs.getInt("tournament_online_id"));
//            user.setGroupId(rs.getInt("group_id"));
            user.setUserId(rs.getLong("user_id"));
//            user.setName(rs.getString("name"));
            user.setResult(rs.getFloat("result"));
//            user.setLefttimes(rs.getInt("lefttimes"));
            user.setClassId(rs.getInt("class_id"));
//            user.setIsGetReward(rs.getInt("is_get_reward"));
            user.setAverageSpeed(rs.getFloat("average_speed"));
            list.add(user);
        }
        return list;
    }

}
