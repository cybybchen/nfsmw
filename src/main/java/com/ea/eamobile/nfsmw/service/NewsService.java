package com.ea.eamobile.nfsmw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.model.News;
import com.ea.eamobile.nfsmw.model.mapper.NewsMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Feb 01 11:46:57 CST 2013
 * @since 1.0
 */
@Service
public class NewsService {

    // 公告
    public static final int BOARD_TYPE = 1;
    // 活动
    public static final int ACTION_TYPE = 2;
    // css
    public static final int CSS_TYPE = 3;
    // 联系我们
    public static final int CONTACT_TYPE = 4;

    @Autowired
    private NewsMapper newsMapper;
    @Autowired
    private MemcachedClient cache;

    public int insert(News news) {
        cache.delete(CacheKey.NEWS + news.getType());
        cache.delete(CacheKey.NEWS_MAX_TIME);
        return newsMapper.insert(news);
    }

    public void update(News news) {
        cache.delete(CacheKey.NEWS + news.getType());
        cache.delete(CacheKey.NEWS_MAX_TIME);
        news.setCreateTime((int) (System.currentTimeMillis() / 1000));
        newsMapper.update(news);
    }

    public void save(int type, String content) {
        News news = new News();
        news.setContent(content);
        news.setType(type);
        news.setCreateTime((int) (System.currentTimeMillis() / 1000));
        insert(news);
    }

    public News getNewsByType(int type) {
        News news = (News) cache.get(CacheKey.NEWS + type);
        if (news == null) {
            news = newsMapper.getNewsByType(type);
            cache.set(CacheKey.NEWS + type, news);
        }
        return news;
    }

    public String getBoard() {
        News board = getNewsByType(BOARD_TYPE);
        if (board != null) {
            return board.getContent();
        }
        return "";
    }

    public String getAction() {
        News action = getNewsByType(ACTION_TYPE);
        if (action != null) {
            return action.getContent();
        }
        return "";
    }

    public String getCss() {
        News css = getNewsByType(CSS_TYPE);
        if (css != null) {
            return css.getContent();
        }
        return "";
    }

    public String getContact() {
        News ct = getNewsByType(CONTACT_TYPE);
        if (ct != null) {
            return ct.getContent();
        }
        return "";
    }

    public int getMaxTime() {
        Integer result = (Integer) cache.get(CacheKey.NEWS_MAX_TIME);
        if (result == null) {
            result = newsMapper.getMaxTime();
            cache.set(CacheKey.NEWS_MAX_TIME, result);
        }
        if (result == null) {
            result = 0;
        }
        return result;
    }

}