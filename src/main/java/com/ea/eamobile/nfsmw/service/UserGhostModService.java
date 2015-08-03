package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.UserGhostMod;
import com.ea.eamobile.nfsmw.model.mapper.UserGhostModMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Sep 26 11:27:23 CST 2012
 * @since 1.0
 */
@Service
public class UserGhostModService {

    @Autowired
    private UserGhostModMapper ghostModMapper;

    public List<UserGhostMod> getGhostModByGhostId(long ghostId) {
        return ghostModMapper.getGhostModByGhostId(ghostId);
    }

    public int insert(UserGhostMod ghostMod) {
        return ghostModMapper.insert(ghostMod);
    }

    public int insertBatch(List<UserGhostMod> mods) {
        return ghostModMapper.insertBatch(mods);
    }

    public void update(UserGhostMod ghostMod) {
        ghostModMapper.update(ghostMod);
    }

    public void deleteById(long ghostId) {
        ghostModMapper.deleteByGhostId(ghostId);
    }

}