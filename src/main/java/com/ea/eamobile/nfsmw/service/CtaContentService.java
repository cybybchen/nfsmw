package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.model.CtaContent;
import com.ea.eamobile.nfsmw.model.mapper.CtaContentMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Jan 22 20:22:18 CST 2013
 * @since 1.0
 */
@Service
public class CtaContentService {

    @Autowired
    private CtaContentMapper ctaContentMapper;
    @Autowired
    private MemcachedClient cache;

    public CtaContent getCtaContent(int id) {
        CtaContent content = (CtaContent) cache.get(CacheKey.CTA_CONTENT + id);
        if (content == null) {
            content = ctaContentMapper.getCtaContent(id);
            if (content == null) {
                content = new CtaContent();
                content.setId(id);
                content.setContent("");
            } else {
                cache.set(CacheKey.CTA_CONTENT + id, content);
            }
        }
        return content;
    }

    private void clearCache(int id) {
        cache.delete(CacheKey.CTA_CONTENT + id);
        cache.delete(CacheKey.CTA_CONTENT_LIST);
    }

    @SuppressWarnings("unchecked")
    public List<CtaContent> getCtaContentList() {
        List<CtaContent> list = (List<CtaContent>) cache.get(CacheKey.CTA_CONTENT_LIST);
        if (list == null) {
            list = ctaContentMapper.getCtaContentList();
            cache.set(CacheKey.CTA_CONTENT_LIST, list);
        }
        return list;
    }

    public int insert(CtaContent ctaContent) {
        clearCache(ctaContent.getId());
        return ctaContentMapper.insert(ctaContent);
    }

    public void update(CtaContent ctaContent) {
        clearCache(ctaContent.getId());
        ctaContentMapper.update(ctaContent);
    }

    public void deleteById(int id) {
        clearCache(id);
        ctaContentMapper.deleteById(id);
    }

}