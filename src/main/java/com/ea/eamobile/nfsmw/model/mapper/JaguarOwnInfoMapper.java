package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.JaguarOwnInfo;
import com.ea.eamobile.nfsmw.view.DeviceCount;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Feb 18 11:49:07 CST 2013
 * @since 1.0
 */
public interface JaguarOwnInfoMapper {

    public List<JaguarOwnInfo> getJaguarOwnInfoList(int id);

    public int insert(JaguarOwnInfo jaguarOwnInfo);

    public JaguarOwnInfo getByMobileOrEmail(@Param("mobile") String mobile,@Param("email") String email);

    public List<DeviceCount> getDeviceCount();

    public List<JaguarOwnInfo> getByEmail(String email);

    public List<JaguarOwnInfo> getByMobile(String mobile);

    public int getTotal();

}