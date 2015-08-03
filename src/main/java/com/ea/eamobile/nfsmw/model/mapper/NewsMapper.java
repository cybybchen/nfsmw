package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.News;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Feb 01 11:46:57 CST 2013
 * @since 1.0
 */
public interface NewsMapper {

    public News getNews(int id);

    public List<News> getNewsList();

    public int insert(News news);

    public void update(News news);

    public void deleteById(int id);

    public News getNewsByType(int type);

    public Integer getMaxTime();

}