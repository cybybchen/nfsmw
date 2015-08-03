package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.UserChartlet;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Nov 02 17:14:26 CST 2012
 * @since 1.0
 */
public interface UserChartletMapper {

    public UserChartlet getUserChartlet(int id);

    public List<UserChartlet> getUserChartletList();

    public int insert(UserChartlet userChartlet);

    public void update(UserChartlet userChartlet);

    public void deleteById(int id);

    public UserChartlet getUserChartletByUserId(@Param("userId") long userId, @Param("chartletId") int chartletId);

}