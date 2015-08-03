package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.GotchaFragment;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 26 18:00:24 CST 2013
 * @since 1.0
 */
public interface GotchaFragmentMapper {

    public GotchaFragment getGotchaPart(int id);

    public List<GotchaFragment> getGotchaPartList();

    public int insert(GotchaFragment gotchaPart);

    public void update(GotchaFragment gotchaPart);

    public void deleteById(int id);

}