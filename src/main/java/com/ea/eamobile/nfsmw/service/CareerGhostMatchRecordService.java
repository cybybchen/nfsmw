package com.ea.eamobile.nfsmw.service;

import java.util.List;
import com.ea.eamobile.nfsmw.model.CareerGhostMatchRecord;
import com.ea.eamobile.nfsmw.model.mapper.CareerGhostMatchRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ma.ruofei
 * @version 1.0 Wed May 22 20:57:09 CST 2013
 * @since 1.0
 */
 @Service
public class CareerGhostMatchRecordService {

    @Autowired
    private CareerGhostMatchRecordMapper careerGhostMatchRecordMapper;
    
    public CareerGhostMatchRecord getCareerGhostMatchRecord(int id){
        return careerGhostMatchRecordMapper.getCareerGhostMatchRecord(id);
    }

    public List<CareerGhostMatchRecord> getCareerGhostMatchRecordList(){
        return careerGhostMatchRecordMapper.getCareerGhostMatchRecordList();
    }

    public int insert(CareerGhostMatchRecord careerGhostMatchRecord){
        return careerGhostMatchRecordMapper.insert(careerGhostMatchRecord);
    }

    public void update(CareerGhostMatchRecord careerGhostMatchRecord){
		    careerGhostMatchRecordMapper.update(careerGhostMatchRecord);    
    }

    public void deleteById(int id){
        careerGhostMatchRecordMapper.deleteById(id);
    }

}