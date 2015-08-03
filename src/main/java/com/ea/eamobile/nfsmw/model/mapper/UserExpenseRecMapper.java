package com.ea.eamobile.nfsmw.model.mapper;

import com.ea.eamobile.nfsmw.model.UserExpenseRec;

/**
 * @author ma.ruofei
 * @version 1.0 Fri Mar 08 16:36:50 CST 2013
 * @since 1.0
 */
public interface UserExpenseRecMapper {

    public UserExpenseRec getUserExpenseRec(long userId);


    public int insert(UserExpenseRec userExpenseRec);

    public void update(UserExpenseRec userExpenseRec);


}