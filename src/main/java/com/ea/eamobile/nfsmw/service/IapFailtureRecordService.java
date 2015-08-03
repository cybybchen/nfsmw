package com.ea.eamobile.nfsmw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.IapFailtureRecord;
import com.ea.eamobile.nfsmw.model.mapper.IapFailtureRecordMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Mar 20 07:22:04 ADT 2013
 * @since 1.0
 */
@Service
public class IapFailtureRecordService {

    @Autowired
    private IapFailtureRecordMapper iapFailtureRecordMapper;

    public int insert(IapFailtureRecord iapFailtureRecord) {
        return iapFailtureRecordMapper.insert(iapFailtureRecord);
    }

}