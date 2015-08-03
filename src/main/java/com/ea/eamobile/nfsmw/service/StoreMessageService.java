package com.ea.eamobile.nfsmw.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.utils.DateUtil;

@Service
public class StoreMessageService {

    @Autowired
    private PurchaseRecordService purchaseRecordService;
    
    public int getEnergySerialNumber(long userId) {
        int serialNum = purchaseRecordService.getEnergyNumCurrentDay(DateUtil.setToDayStartTime(new Date()), userId);
        if (serialNum == 10) {
            return 10;
        }
        return serialNum + 1;

    }

    public int getBuyedNum(long userId, int purchaseId) {
        int buyedNum = purchaseRecordService.getBuyedNumByPurchaseId(purchaseId, userId);
        return buyedNum;

    }
}
