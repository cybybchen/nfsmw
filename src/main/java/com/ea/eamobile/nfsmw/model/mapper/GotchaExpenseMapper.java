package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.GotchaExpense;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Mar 26 17:59:56 CST 2013
 * @since 1.0
 */
public interface GotchaExpenseMapper {

    public GotchaExpense getGotchaExpense(@Param("gotchaId") int gotchaId,@Param("level") int level);

    public List<GotchaExpense> getGotchaExpenseList(int gotchaId);
    
    public List<GotchaExpense> getAllGotchaExpenseList();

}