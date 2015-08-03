package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.ea.eamobile.nfsmw.model.EventOptionOverride;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Dec 03 10:56:22 CST 2012
 * @since 1.0
 */
public interface EventOptionOverrideMapper {
    
    @Cacheable(value = "memory", key = "'EventOptionOverrideMapper.getEventOptionOverrideByModeId.'+#modeId")
    public List<EventOptionOverride> getEventOptionOverrideByModeId(int modeId);

    @Cacheable(value = "memory", key = "'EventOptionOverrideMapper.getEventOptionOverrideList'")
    public List<EventOptionOverride> getEventOptionOverrideList();
    
    public List<Integer> getEventOptionOverrideModeIdList();

    public int insert(EventOptionOverride eventOptionOverride);

    public void update(EventOptionOverride eventOptionOverride);

    public void deleteById(int id);

}