package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.CarSlot;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Nov 02 16:09:45 CST 2012
 * @since 1.0
 */
public interface CarSlotMapper {

    
    public CarSlot getCarSlot(int id);
   
    public CarSlot getFirstCarSlot(String carId);

    public List<CarSlot> getCarSlotList();

    public int insert(CarSlot carSlot);

    public void update(CarSlot carSlot);

    public void deleteById(int id);

}