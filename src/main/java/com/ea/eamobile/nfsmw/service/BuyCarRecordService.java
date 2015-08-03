package com.ea.eamobile.nfsmw.service;

import java.util.List;
import com.ea.eamobile.nfsmw.model.BuyCarRecord;
import com.ea.eamobile.nfsmw.model.mapper.BuyCarRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ma.ruofei
 * @version 1.0 Thu May 02 17:00:12 CST 2013
 * @since 1.0
 */
 @Service
public class BuyCarRecordService {

    @Autowired
    private BuyCarRecordMapper buyCarRecordMapper;
    
    public BuyCarRecord getBuyCarRecord(int id){
        return buyCarRecordMapper.getBuyCarRecord(id);
    }

    public List<BuyCarRecord> getBuyCarRecordList(){
        return buyCarRecordMapper.getBuyCarRecordList();
    }

    public int insert(BuyCarRecord buyCarRecord){
        return buyCarRecordMapper.insert(buyCarRecord);
    }

    public void update(BuyCarRecord buyCarRecord){
		    buyCarRecordMapper.update(buyCarRecord);    
    }

    public void deleteById(int id){
        buyCarRecordMapper.deleteById(id);
    }

}