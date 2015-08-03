package com.ea.eamobile.nfsmw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.model.SystemConfig;
import com.ea.eamobile.nfsmw.model.mapper.SystemConfigMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Dec 25 16:56:54 CST 2012
 * @since 1.0
 */
@Service
public class SystemConfigService {

    private static final int EVENT_OPTION_VERSION = 1;

    @Autowired
    private SystemConfigMapper systemConfigMapper;
    @Autowired
    private MemcachedClient cache;

    public SystemConfig getSystemConfig(int id) {
        SystemConfig ret = (SystemConfig) cache.get(CacheKey.SYSTEM_CONFIG + id);
        if (ret != null) {
            return ret;
        }
        ret = systemConfigMapper.getSystemConfig(id);
        cache.set(CacheKey.SYSTEM_CONFIG + id, ret);
        return ret;
    }

    private void clearCache(int id) {
        cache.delete(CacheKey.SYSTEM_CONFIG + id);
    }

    public List<SystemConfig> getSystemConfigList() {
        return systemConfigMapper.getSystemConfigList();
    }

    public int insert(SystemConfig systemConfig) {
        return systemConfigMapper.insert(systemConfig);
    }

    public void update(SystemConfig systemConfig) {
        clearCache(systemConfig.getId());
        systemConfigMapper.update(systemConfig);
    }

    public void deleteById(int id) {
        clearCache(id);
        systemConfigMapper.deleteById(id);
    }

    /**
     * 获取系统的EventOption版本号 用来判断是否给用户下发
     * 
     * @return
     */
    public String getEventOptionVersion() {
        SystemConfig config = getSystemConfig(EVENT_OPTION_VERSION);
        if (config != null) {
            return config.getValue();
        }
        return "";
    }

    public List<Integer> getCtaParam() {
        List<Integer> result = new ArrayList<Integer>();
        SystemConfig ctaParam = getSystemConfig(Const.CTA_PARAM_SYSTEMCONFIG_ID);
        if (ctaParam != null) {
            String[] paramList = ctaParam.getValue().split(",");
            for (int i = 0; i < paramList.length; i++) {
                Integer temp = Integer.parseInt(paramList[i]);
                result.add(temp);
            }
        }
        return result;
    }

    public List<Float> getSpeedFactor() {
        List<Float> result = new ArrayList<Float>();
        SystemConfig ctaParam = getSystemConfig(Const.SPEED_FACTOR_SYSTEMCONFIG_ID);
        if (ctaParam != null) {
            String[] paramList = ctaParam.getValue().split(",");
            for (int i = 0; i < paramList.length; i++) {
                Float temp = Float.parseFloat(paramList[i]);
                result.add(temp);
            }
        }
        return result;
    }

    public int getWeiBoShareById(int id) {
        int result = 1;
        SystemConfig systemConfig = getSystemConfig(id);
        if (systemConfig != null) {
            result = Integer.parseInt(systemConfig.getValue());
        }
        return result;
    }

    /**
     * mod状态：0 关闭，1 开启，2 自动弹
     * 
     * @param status
     */
    public void updateModStatus(int status) {
        if (status < 0 || status > 2) {
            return;
        }
        SystemConfig config = getSystemConfig(Const.MOD_CONFIG);
        if (config == null) {
            // insert
            config = new SystemConfig();
            config.setId(Const.MOD_CONFIG);
            config.setName("mod_enabled");
            config.setValue(String.valueOf(status));
            insert(config);
        } else {
            config.setValue(String.valueOf(status));
            update(config);
        }

    }
}