package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.CtaContent;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Jan 22 20:22:17 CST 2013
 * @since 1.0
 */
public interface CtaContentMapper {

    public CtaContent getCtaContent(int id);

    public List<CtaContent> getCtaContentList();

    public int insert(CtaContent ctaContent);

    public void update(CtaContent ctaContent);

    public void deleteById(int id);

}