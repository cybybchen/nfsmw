package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.ModeStandardResult;

/**
 * @author ma.ruofei
 * @version 1.0 Fri May 24 17:51:48 CST 2013
 * @since 1.0
 */
public interface ModeStandardResultMapper {

    public ModeStandardResult getModeStandardResult(@Param("modeId") int modeId, @Param("carId") String carId);

    public List<ModeStandardResult> getModeStandardResultList();

    public int insert(ModeStandardResult modeStandardResult);

    public void update(ModeStandardResult modeStandardResult);

    public void deleteById(int id);

}