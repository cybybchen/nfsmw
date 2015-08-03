package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.RaceStartRecord;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 05 19:46:24 CST 2013
 * @since 1.0
 */
public interface RaceStartRecordMapper {

    public RaceStartRecord getRaceStartRecord(long userId);

    public List<RaceStartRecord> getRaceStartRecordList();

    public int insert(RaceStartRecord raceStartRecord);

    public void update(RaceStartRecord raceStartRecord);

    public void deleteById(int id);

}