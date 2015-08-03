package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.CensorWord;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Jan 24 14:15:32 CST 2013
 * @since 1.0
 */
public interface CensorWordMapper {

    public String getCensorWord(String word);

    public List<String> getCensorWordList();

    public int insert(CensorWord censorWord);

    public void update(CensorWord censorWord);

}