package com.ea.eamobile.nfsmw.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.PurchaseRecord;
import com.ea.eamobile.nfsmw.model.mapper.PurchaseRecordMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Nov 27 10:34:37 CST 2012
 * @since 1.0
 */
 @Service
public class PurchaseRecordService {

    @Autowired
    private PurchaseRecordMapper purchaseRecordMapper;
    
    public PurchaseRecord getPurchaseRecord(int id){
        return purchaseRecordMapper.getPurchaseRecord(id);
    }

    public List<PurchaseRecord> getPurchaseRecordList(){
        return purchaseRecordMapper.getPurchaseRecordList();
    }

    public int insert(PurchaseRecord purchaseRecord){
        return purchaseRecordMapper.insert(purchaseRecord);
    }
    
    public int getEnergyNumCurrentDay(Date buyDate,long userId){
        Integer i=purchaseRecordMapper.getEnergyNumCurrentDay(buyDate,userId);
        if(i==null){
            i=0;
        }
        if(i>10){
            i=10;
        }
        return i;
    }
    
    public int getBuyedNumByPurchaseId(int purchaseId,long userId){
        Integer i=purchaseRecordMapper.getBuyedNumByPurchaseId(purchaseId,userId);
        if(i==null){
            i=0;
        }
        return i;
    }

    public void update(PurchaseRecord purchaseRecord){
        purchaseRecordMapper.update(purchaseRecord);    
    }

    public void deleteById(int id){
        purchaseRecordMapper.deleteById(id);
    }

}