package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.Advertise;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Dec 13 14:34:54 CST 2012
 * @since 1.0
 */
public interface AdvertiseMapper {

    
  
    public Advertise getAdvertise(int id);

    public List<Advertise> getAdvertiseList();

    public int insert(Advertise advertise);

    public void update(Advertise advertise);

    public void deleteById(int id);

}