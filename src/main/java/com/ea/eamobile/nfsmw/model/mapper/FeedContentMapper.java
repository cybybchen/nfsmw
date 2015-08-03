package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.FeedContent;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Feb 01 22:10:52 CST 2013
 * @since 1.0
 */
public interface FeedContentMapper {

    public FeedContent getFeedContent(int id);

    public List<FeedContent> getFeedContentList();

    public int insert(FeedContent feedContent);

    public void update(FeedContent feedContent);

    public void deleteById(int id);

}