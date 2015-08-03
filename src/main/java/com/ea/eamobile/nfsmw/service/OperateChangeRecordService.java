package com.ea.eamobile.nfsmw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.OperateChangeRecord;
import com.ea.eamobile.nfsmw.model.mapper.OperateChangeRecordMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Mar 14 21:41:55 CST 2013
 * @since 1.0
 */
@Service
public class OperateChangeRecordService {

    @Autowired
    private OperateChangeRecordMapper operateChangeRecordMapper;

    public int insert(OperateChangeRecord operateChangeRecord) {
        return operateChangeRecordMapper.insert(operateChangeRecord);
    }

}