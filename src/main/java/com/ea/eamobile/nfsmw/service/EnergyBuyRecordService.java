package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.EnergyBuyRecord;
import com.ea.eamobile.nfsmw.model.mapper.EnergyBuyRecordMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Oct 15 14:42:10 CST 2012
 * @since 1.0
 */
 @Service
public class EnergyBuyRecordService {

    @Autowired
    private EnergyBuyRecordMapper energyBuyRecordMapper;
    
    public EnergyBuyRecord getEnergyBuyRecord(int id){
        return energyBuyRecordMapper.getEnergyBuyRecord(id);
    }

    public List<EnergyBuyRecord> getEnergyBuyRecordList(){
        return energyBuyRecordMapper.getEnergyBuyRecordList();
    }

    public int insert(EnergyBuyRecord energyBuyRecord){
        return energyBuyRecordMapper.insert(energyBuyRecord);
    }

    public void update(EnergyBuyRecord energyBuyRecord){
		    energyBuyRecordMapper.update(energyBuyRecord);    
    }

    public void deleteById(int id){
        energyBuyRecordMapper.deleteById(id);
    }

}