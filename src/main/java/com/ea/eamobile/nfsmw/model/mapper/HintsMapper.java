package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.Hints;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Jan 16 19:35:38 CST 2013
 * @since 1.0
 */
public interface HintsMapper {

    public Hints getHints(int id);


    public List<Hints> getHintsList();

    public int insert(Hints hints);

    public void update(Hints hints);

    public void deleteById(int id);

}