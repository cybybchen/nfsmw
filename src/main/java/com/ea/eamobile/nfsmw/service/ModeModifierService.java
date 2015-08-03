package com.ea.eamobile.nfsmw.service;

import java.util.List;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.model.ModeModifier;
import com.ea.eamobile.nfsmw.model.mapper.ModeModifierMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Jun 03 11:37:44 CST 2013
 * @since 1.0
 */
@Service
public class ModeModifierService {

    @Autowired
    private ModeModifierMapper modeModifierMapper;
    @Autowired
    private MemcachedClient cache;

    private void clear(int id) {
        cache.delete(CacheKey.MODE_MODIFIER_BYID + id);
        cache.delete(CacheKey.MODE_MODIFIER_LIST);
    }

    public ModeModifier getModeModifier(int id) {
        ModeModifier result = (ModeModifier) cache.get(CacheKey.MODE_MODIFIER_BYID + id);
        if (result == null) {
            result = modeModifierMapper.getModeModifier(id);
            cache.set(CacheKey.MODE_MODIFIER_BYID + id, result, MemcachedClient.HOUR);
        }
        if (result == null) {
            result = new ModeModifier();
            result.setModeId(0);
            result.setModifier1(-0.5f);
            result.setModifier2(0f);
            result.setModifier3(0.5f);
            result.setModifier4(1f);
            result.setModifier5(1.5f);
            result.setModifier1v1(0.5f);
        }
        return result;

    }

    @SuppressWarnings("unchecked")
    public List<ModeModifier> getModeModifierList() {
        List<ModeModifier> list = (List<ModeModifier>) cache.get(CacheKey.MODE_MODIFIER_LIST);
        if (list == null) {
            list = modeModifierMapper.getModeModifierList();
            cache.set(CacheKey.MODE_MODIFIER_LIST, list, MemcachedClient.HOUR);
        }
        return list;

    }

    public int insert(ModeModifier modeModifier) {
        return modeModifierMapper.insert(modeModifier);
    }

    public void update(ModeModifier modeModifier) {
        clear(modeModifier.getModeId());
        modeModifierMapper.update(modeModifier);
    }

    public void deleteById(int id) {
        modeModifierMapper.deleteById(id);
    }

}