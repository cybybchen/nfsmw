package com.ea.eamobile.nfsmw.model.mapper;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.FinishRatioAddition;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Mar 13 14:32:03 CST 2013
 * @since 1.0
 */
public interface FinishRatioAdditionMapper {

    public FinishRatioAddition getFinishRatioAddition(@Param("modeId") int modeId, @Param("type") int type);

}