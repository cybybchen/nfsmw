package com.ea.eamobile.nfsmw.model.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

import com.ea.eamobile.nfsmw.model.UserCarSlot;

public class UserCarSlotListHandler implements ResultSetHandler<List<UserCarSlot>> {

    @Override
    public List<UserCarSlot> handle(ResultSet rs) throws SQLException {
        List<UserCarSlot> ret = new ArrayList<UserCarSlot>();
        while (rs.next()) {
            UserCarSlot slot = new UserCarSlot();
            slot.setId(rs.getInt("id"));
            slot.setUserCarId(rs.getInt("user_car_id"));
            slot.setSlotId(rs.getInt("slot_id"));
            ret.add(slot);
        }
        return ret;
    }

}
