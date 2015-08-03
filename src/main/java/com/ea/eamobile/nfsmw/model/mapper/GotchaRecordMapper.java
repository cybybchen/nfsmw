package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.GotchaRecord;

/**
 * @author ma.ruofei
 * @version 1.0 Thu May 02 17:00:12 CST 2013
 * @since 1.0
 */
public interface GotchaRecordMapper {

    public GotchaRecord getGotchaRecord(int id);

    public List<GotchaRecord> getGotchaRecordList();

    public int insert(GotchaRecord gotchaRecord);

    public void update(GotchaRecord gotchaRecord);

    public void deleteById(int id);
    
    public Integer getMaxTimeByUserId(long userId);

}