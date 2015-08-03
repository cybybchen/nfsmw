package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.model.JaguarRentInfo;
import com.ea.eamobile.nfsmw.model.mapper.JaguarRentInfoMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;
import com.ea.eamobile.nfsmw.view.DeviceCount;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Feb 18 11:49:07 CST 2013
 * @since 1.0
 */
@Service
public class JaguarRentInfoService {

    @Autowired
    private JaguarRentInfoMapper jaguarRentInfoMapper;
    @Autowired
    private MemcachedClient cache;

    public List<JaguarRentInfo> getJaguarRentInfoList(int from) {
        return jaguarRentInfoMapper.getJaguarRentInfoList(from);
    }

    public int insert(JaguarRentInfo jaguarRentInfo) {
        return jaguarRentInfoMapper.insert(jaguarRentInfo);
    }

    public boolean hasSubmit(String mobile, String email) {
        JaguarRentInfo info = jaguarRentInfoMapper.getByMobileEmail(mobile, email);
        return info != null;
    }

    public void save(String name, String mobile, String email, int gender, String ip, int device,int age) {
        JaguarRentInfo info = new JaguarRentInfo();
        info.setName(name);
        info.setAge(age);
        info.setMobile(mobile);
        info.setEmail(email);
        info.setGender(gender);
        ip = ip != null ? ip : "";
        info.setIp(ip);
        info.setCreateTime(System.currentTimeMillis() / 1000L);
        info.setDevice(device);
        insert(info);
    }

    public List<DeviceCount> getDeviceCount() {
        return jaguarRentInfoMapper.getDeviceCount();
    }

    public List<JaguarRentInfo> getJaguarRentInfoList(String email, String mobile, int from) {
        if (StringUtils.isNotBlank(email)) {
            return jaguarRentInfoMapper.getByEmail(email);
        }
        if (StringUtils.isNotBlank(mobile)) {
            return jaguarRentInfoMapper.getByMobile(mobile);
        }

        return getJaguarRentInfoList(from);
    }

    public int getTotal() {
        Integer total = (Integer) cache.get(CacheKey.JAGUAR_RENT_TOTAL);
        if (total == null) {
            total = jaguarRentInfoMapper.getTotal();
            cache.set(CacheKey.JAGUAR_RENT_TOTAL, total);
        }
        return total;
    }

}