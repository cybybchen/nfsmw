package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.UserCar;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Nov 02 16:09:45 CST 2012
 * @since 1.0
 */
public interface UserCarMapper {

    public UserCar getUserCar(long id);
    
    public UserCar getUserCarByUserIdAndCarId(@Param("userId")long userId,@Param("carId")String CarId);    

    public List<UserCar> getUserCarList(long userId);

    public int insert(UserCar userCar);

    public void update(UserCar userCar);

    public void deleteById(long id);
    
}