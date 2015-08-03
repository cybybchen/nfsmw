package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.CareerGhostMod;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Sep 26 11:27:22 CST 2012
 * @since 1.0
 */
public interface CareerGhostModMapper {

    public List<CareerGhostMod> getGhostModByGhostId(long ghostId);

    public List<CareerGhostMod> getGhostModList();

    public int insert(CareerGhostMod ghostMod);
    
    public int insertBatch(List<CareerGhostMod> mods);

    public void update(CareerGhostMod ghostMod);

    public void deleteByGhostId(long ghostId);

}