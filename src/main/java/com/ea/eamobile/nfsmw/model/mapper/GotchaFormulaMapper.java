package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.GotchaFormula;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 26 18:03:31 CST 2013
 * @since 1.0
 */
public interface GotchaFormulaMapper {

    public GotchaFormula getGotchaFormula(int id);

    public List<GotchaFormula> getGotchaFormulaList();

    public int insert(GotchaFormula gotchaFormula);

    public void update(GotchaFormula gotchaFormula);

    public void deleteById(int id);

}