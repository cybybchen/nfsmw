package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.EnergyBuyRecord;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Oct 15 14:42:10 CST 2012
 * @since 1.0
 */
public interface EnergyBuyRecordMapper {

    public EnergyBuyRecord getEnergyBuyRecord(int id);

    public List<EnergyBuyRecord> getEnergyBuyRecordList();

    public int insert(EnergyBuyRecord energyBuyRecord);

    public void update(EnergyBuyRecord energyBuyRecord);

    public void deleteById(int id);

}