package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.SystemConfig;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Dec 25 16:56:54 CST 2012
 * @since 1.0
 */
public interface SystemConfigMapper {

    public SystemConfig getSystemConfig(int id);

    public List<SystemConfig> getSystemConfigList();

    public int insert(SystemConfig systemConfig);

    public void update(SystemConfig systemConfig);

    public void deleteById(int id);

}