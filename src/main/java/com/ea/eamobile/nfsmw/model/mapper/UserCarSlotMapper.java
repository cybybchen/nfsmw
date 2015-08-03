package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.UserCarSlot;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Nov 02 16:09:45 CST 2012
 * @since 1.0
 */
public interface UserCarSlotMapper {

    public UserCarSlot getUserCarSlot(int id);

    public List<UserCarSlot> getUserCarSlotList();

    public int insert(UserCarSlot userCarSlot);

    public void update(UserCarSlot userCarSlot);

    public void deleteById(int id);

    public List<UserCarSlot> getSlotListByUserCarId(long userCarId);

}