package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.CareerGhostMod;
import com.ea.eamobile.nfsmw.model.mapper.CareerGhostModMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Sep 26 11:27:23 CST 2012
 * @since 1.0
 */
@Service
public class CareerGhostModService {

    @Autowired
    private CareerGhostModMapper ghostModMapper;

    public List<CareerGhostMod> getGhostModByGhostId(long ghostId) {
        return ghostModMapper.getGhostModByGhostId(ghostId);
    }

    public int insert(CareerGhostMod ghostMod) {
        return ghostModMapper.insert(ghostMod);
    }

    public int insertBatch(List<CareerGhostMod> mods) {
        return ghostModMapper.insertBatch(mods);
    }

    public void update(CareerGhostMod ghostMod) {
        ghostModMapper.update(ghostMod);
    }

    public void deleteById(long ghostId) {
        ghostModMapper.deleteByGhostId(ghostId);
    }

}