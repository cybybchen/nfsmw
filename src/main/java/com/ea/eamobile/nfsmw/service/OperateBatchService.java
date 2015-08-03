package com.ea.eamobile.nfsmw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.OperateBatch;
import com.ea.eamobile.nfsmw.model.mapper.OperateBatchMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Mar 14 21:41:55 CST 2013
 * @since 1.0
 */
 @Service
public class OperateBatchService {

    @Autowired
    private OperateBatchMapper operateBatchMapper;
    


    public int insert(OperateBatch operateBatch){
        return operateBatchMapper.insert(operateBatch);
    }


}