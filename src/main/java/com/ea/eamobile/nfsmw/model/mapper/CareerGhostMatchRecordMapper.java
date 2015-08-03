package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;
import com.ea.eamobile.nfsmw.model.CareerGhostMatchRecord;

/**
 * @author ma.ruofei
 * @version 1.0 Wed May 22 20:57:09 CST 2013
 * @since 1.0
 */
public interface CareerGhostMatchRecordMapper {

    public CareerGhostMatchRecord getCareerGhostMatchRecord(int id);

    public List<CareerGhostMatchRecord> getCareerGhostMatchRecordList();

    public int insert(CareerGhostMatchRecord careerGhostMatchRecord);

    public void update(CareerGhostMatchRecord careerGhostMatchRecord);

    public void deleteById(int id);

}