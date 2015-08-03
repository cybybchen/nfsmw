package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.CarSlotConsumable;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Nov 02 16:09:45 CST 2012
 * @since 1.0
 */
public interface CarSlotConsumableMapper {

    public CarSlotConsumable getCarSlotConsumable(int id);

    public List<CarSlotConsumable> getCarSlotConsumableList();
    

    public List<CarSlotConsumable> getCarSlotConsumableListBySlotId(int slotId);
    
    public CarSlotConsumable getCarSlotConsumableListBySlotIdAndConType(@Param("slotId") int slotId,@Param("conType") int conType);

    public int insert(CarSlotConsumable carSlotConsumable);

    public void update(CarSlotConsumable carSlotConsumable);

    public void deleteById(int id);

}