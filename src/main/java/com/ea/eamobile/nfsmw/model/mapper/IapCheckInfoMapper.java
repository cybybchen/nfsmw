package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.IapCheckInfo;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Dec 19 21:13:48 CST 2012
 * @since 1.0
 */
public interface IapCheckInfoMapper {

    public IapCheckInfo getIapCheckInfo(int id);
    
    public IapCheckInfo getIapCheckInfoByTransactionId(long transactionId);
    
    public IapCheckInfo getIapCheckInfoByUserIdAndProductId(IapCheckInfo iapCheckInfo);

    public List<IapCheckInfo> getIapCheckInfoList();

    public int insert(IapCheckInfo iapCheckInfo);

    public void update(IapCheckInfo iapCheckInfo);

    public void deleteById(int id);

}