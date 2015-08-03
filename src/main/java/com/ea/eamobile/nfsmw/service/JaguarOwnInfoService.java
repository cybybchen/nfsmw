package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.model.JaguarOwnInfo;
import com.ea.eamobile.nfsmw.model.mapper.JaguarOwnInfoMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;
import com.ea.eamobile.nfsmw.view.DeviceCount;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Feb 18 11:49:07 CST 2013
 * @since 1.0
 */
@Service
public class JaguarOwnInfoService {

    @Autowired
    private JaguarOwnInfoMapper jaguarOwnInfoMapper;
    @Autowired
    private MemcachedClient cache;

    public List<JaguarOwnInfo> getJaguarOwnInfoList(int from) {
        return jaguarOwnInfoMapper.getJaguarOwnInfoList(from);
    }

    public int insert(JaguarOwnInfo jaguarOwnInfo) {
        return jaguarOwnInfoMapper.insert(jaguarOwnInfo);
    }

    public void save(String name, String mobile, String email, int gender, String province, String city, int year,
            int season, String testArea, int budget, String ip, int device,int age) {
        JaguarOwnInfo info = new JaguarOwnInfo();
        info.setName(name);
        info.setEmail(email);
        info.setMobile(mobile);
        info.setGender(gender);
        info.setProvince(province);
        info.setCity(city);
        info.setBuyYear(year);
        info.setBuySeason(season);
        info.setTestArea(testArea);
        info.setBudget(budget);
        ip = ip != null ? ip : "";
        info.setIp(ip);
        info.setCreateTime(System.currentTimeMillis() / 1000);
        info.setDevice(device);
        info.setAge(age);
        insert(info);
    }

    public boolean hasSubmit(String mobile, String email) {
        JaguarOwnInfo info = jaguarOwnInfoMapper.getByMobileOrEmail(mobile, email);
        return info != null;
    }

    public List<DeviceCount> getDeviceCount() {
        return jaguarOwnInfoMapper.getDeviceCount();
    }

    /**
     * 根据条件查询
     * 
     * @param email
     * @param mobile
     * @param from
     * @return
     */
    public List<JaguarOwnInfo> getJaguarOwnInfoList(String email, String mobile, int from) {
        if (StringUtils.isNotBlank(email)) {
            return jaguarOwnInfoMapper.getByEmail(email);
        }
        if (StringUtils.isNotBlank(mobile)) {
            return jaguarOwnInfoMapper.getByMobile(mobile);
        }
        return getJaguarOwnInfoList(from);
    }

    public int getTotal() {
        Integer total = (Integer) cache.get(CacheKey.JAGUAR_OWN_TOTAL);
        if (total == null) {
            total = jaguarOwnInfoMapper.getTotal();
            cache.set(CacheKey.JAGUAR_OWN_TOTAL, total);
        }
        return total;
    }

}