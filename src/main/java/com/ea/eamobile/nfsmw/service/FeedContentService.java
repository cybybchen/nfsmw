package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.model.FeedContent;
import com.ea.eamobile.nfsmw.model.mapper.FeedContentMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Feb 01 22:10:52 CST 2013
 * @since 1.0
 */
@Service
public class FeedContentService {

    @Autowired
    private FeedContentMapper feedContentMapper;
    @Autowired
    private MemcachedClient cache;

    private void clear(int id) {
        cache.delete(CacheKey.FEED + id);
        cache.delete(CacheKey.FEED_LIST);
    }

    public FeedContent getFeedContent(int id) {
        FeedContent feed = (FeedContent) cache.get(CacheKey.FEED + id);
        if (feed == null) {
            feed = feedContentMapper.getFeedContent(id);
            cache.set(CacheKey.FEED + id, feed, MemcachedClient.HOUR);
        }
        return feed;
    }

    @SuppressWarnings("unchecked")
    public List<FeedContent> getFeedContentList() {
        List<FeedContent> list = (List<FeedContent>) cache.get(CacheKey.FEED_LIST);
        if(list==null){
            list = feedContentMapper.getFeedContentList();
            cache.set(CacheKey.FEED_LIST, list, MemcachedClient.HOUR);
        }
        return list;
    }

    public int insert(FeedContent feedContent) {
        clear(feedContent.getId());
        return feedContentMapper.insert(feedContent);
    }

    public void update(FeedContent feedContent) {
        clear(feedContent.getId());
        feedContentMapper.update(feedContent);
    }

    public void deleteById(int id) {
        clear(id);
        feedContentMapper.deleteById(id);
    }

}