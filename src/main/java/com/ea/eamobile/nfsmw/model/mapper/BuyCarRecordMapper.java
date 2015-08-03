package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;
import com.ea.eamobile.nfsmw.model.BuyCarRecord;

/**
 * @author ma.ruofei
 * @version 1.0 Thu May 02 17:00:12 CST 2013
 * @since 1.0
 */
public interface BuyCarRecordMapper {

    public BuyCarRecord getBuyCarRecord(int id);

    public List<BuyCarRecord> getBuyCarRecordList();

    public int insert(BuyCarRecord buyCarRecord);

    public void update(BuyCarRecord buyCarRecord);

    public void deleteById(int id);

}