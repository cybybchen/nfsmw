package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.RaceModeUnlock;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Sep 25 16:33:42 CST 2012
 * @since 1.0
 */
public interface RaceModeUnlockMapper {

    public RaceModeUnlock getRaceModeUnlock(int id);

    public List<RaceModeUnlock> getRaceModeUnlockList();

    public int insert(RaceModeUnlock raceModeUnlock);

    public void update(RaceModeUnlock raceModeUnlock);

    public void deleteById(int id);

}