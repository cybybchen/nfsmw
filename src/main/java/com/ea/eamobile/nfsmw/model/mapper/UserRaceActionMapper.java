package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.UserRaceAction;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Apr 01 13:51:32 CST 2013
 * @since 1.0
 */
public interface UserRaceActionMapper {

    public UserRaceAction getUserRaceAction(long id);
    
    public UserRaceAction getUserRaceActionByUserIdAndValueId(@Param("userId")long userId, @Param("valueId")int valueId);
    
    public List<UserRaceAction> getUserRaceActionListByUserId(long userId);

    public List<UserRaceAction> getUserRaceActionList();

    public int insert(UserRaceAction userRaceAction);

    public void update(UserRaceAction userRaceAction);

    public void deleteById(long id);

}