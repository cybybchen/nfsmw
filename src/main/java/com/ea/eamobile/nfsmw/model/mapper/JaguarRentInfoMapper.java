package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.JaguarRentInfo;
import com.ea.eamobile.nfsmw.view.DeviceCount;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Feb 18 11:49:07 CST 2013
 * @since 1.0
 */
public interface JaguarRentInfoMapper {

    public List<JaguarRentInfo> getJaguarRentInfoList(int id);

    public int insert(JaguarRentInfo jaguarRentInfo);

    public JaguarRentInfo getByMobileEmail(@Param("mobile") String mobile,@Param("email") String email);
    
    public List<DeviceCount> getDeviceCount();

    public List<JaguarRentInfo> getByEmail(String email);

    public List<JaguarRentInfo> getByMobile(String mobile);

    public int getTotal();

}