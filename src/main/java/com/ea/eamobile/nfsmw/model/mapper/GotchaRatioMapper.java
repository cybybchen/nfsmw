package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.GotchaRatio;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 26 18:00:24 CST 2013
 * @since 1.0
 */
public interface GotchaRatioMapper {

    public GotchaRatio getGotchaRatio(int id);

    public List<GotchaRatio> getGotchaRatioList(int gotchaId);

    public int insert(GotchaRatio gotchaRatio);

    public void update(GotchaRatio gotchaRatio);

    public void deleteById(int id);

}