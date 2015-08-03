package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.UserGhostMod;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Sep 26 11:27:22 CST 2012
 * @since 1.0
 */
public interface UserGhostModMapper {

    public List<UserGhostMod> getGhostModByGhostId(long ghostId);

    public List<UserGhostMod> getGhostModList();

    public int insert(UserGhostMod ghostMod);
    
    public int insertBatch(List<UserGhostMod> mods);

    public void update(UserGhostMod ghostMod);

    public void deleteByGhostId(long ghostId);

}