package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.GotchaRecord;
import com.ea.eamobile.nfsmw.model.mapper.GotchaRecordMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Thu May 02 17:00:12 CST 2013
 * @since 1.0
 */
@Service
public class GotchaRecordService {

    @Autowired
    private GotchaRecordMapper gotchaRecordMapper;

    public GotchaRecord getGotchaRecord(int id) {
        return gotchaRecordMapper.getGotchaRecord(id);
    }

    public List<GotchaRecord> getGotchaRecordList() {
        return gotchaRecordMapper.getGotchaRecordList();
    }

    public int insert(GotchaRecord gotchaRecord) {
        return gotchaRecordMapper.insert(gotchaRecord);
    }

    public void update(GotchaRecord gotchaRecord) {
        gotchaRecordMapper.update(gotchaRecord);
    }

    public void deleteById(int id) {
        gotchaRecordMapper.deleteById(id);
    }

    public Integer getMaxTimeByUserId(long userId) {
        Integer time = gotchaRecordMapper.getMaxTimeByUserId(userId);
        if (time == null) {
            return 0;
        }

        return time;
    }

}