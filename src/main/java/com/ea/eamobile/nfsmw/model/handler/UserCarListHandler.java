package com.ea.eamobile.nfsmw.model.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

import com.ea.eamobile.nfsmw.model.UserCar;

public class UserCarListHandler implements ResultSetHandler<List<UserCar>> {

    @Override
    public List<UserCar> handle(ResultSet rs) throws SQLException {
        List<UserCar> ret = new ArrayList<UserCar>();
        while (rs.next()) {
            UserCar car = new UserCar();
            car.setId(rs.getInt("id"));
            car.setUserId(rs.getInt("user_id"));
            car.setScore(rs.getInt("score"));
            ret.add(car);
        }
        return ret;
    }

}
