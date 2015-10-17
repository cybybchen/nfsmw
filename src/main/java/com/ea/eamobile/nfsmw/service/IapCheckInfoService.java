package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.IapCheckInfo;
import com.ea.eamobile.nfsmw.model.mapper.IapCheckInfoMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Dec 19 21:13:48 CST 2012
 * @since 1.0
 */
 @Service
public class IapCheckInfoService {

    @Autowired
    private IapCheckInfoMapper iapCheckInfoMapper;
    
    public IapCheckInfo getIapCheckInfo(int id){
        return iapCheckInfoMapper.getIapCheckInfo(id);
    }
    
    public IapCheckInfo getIapCheckInfoByTransactionId(long transactionId){
        return iapCheckInfoMapper.getIapCheckInfoByTransactionId(transactionId);
    }
    
    public IapCheckInfo getIapCheckInfoByUserIdAndProductId(IapCheckInfo iapCheckInfo){
        return iapCheckInfoMapper.getIapCheckInfoByUserIdAndProductId(iapCheckInfo);
    }
    
    public IapCheckInfo getIapCheckInfoByUserIdAndTransactionId(IapCheckInfo iapCheckInfo){
        return iapCheckInfoMapper.getIapCheckInfoByUserIdAndTransactionId(iapCheckInfo);
    }

    public List<IapCheckInfo> getIapCheckInfoList(){
        return iapCheckInfoMapper.getIapCheckInfoList();
    }

    public int insert(IapCheckInfo iapCheckInfo){
        return iapCheckInfoMapper.insert(iapCheckInfo);
    }

    public void update(IapCheckInfo iapCheckInfo){
		    iapCheckInfoMapper.update(iapCheckInfo);    
    }

    public void deleteById(int id){
        iapCheckInfoMapper.deleteById(id);
    }

}