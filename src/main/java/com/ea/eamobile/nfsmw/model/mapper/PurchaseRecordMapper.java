package com.ea.eamobile.nfsmw.model.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.PurchaseRecord;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Nov 27 10:34:37 CST 2012
 * @since 1.0
 */
public interface PurchaseRecordMapper {

    public PurchaseRecord getPurchaseRecord(int id);

    public List<PurchaseRecord> getPurchaseRecordList();

    public int insert(PurchaseRecord purchaseRecord);
    
    public Integer getEnergyNumCurrentDay(@Param("buyDate")Date buyDate,@Param("userId")long userId);
    
    public Integer getBuyedNumByPurchaseId(@Param("purchaseId")int purchaseId,@Param("userId")long userId);

    public void update(PurchaseRecord purchaseRecord);

    public void deleteById(int id);

}