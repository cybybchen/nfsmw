package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.UserFilter;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Jun 17 11:50:46 CST 2013
 * @since 1.0
 */
public interface UserFilterMapper {

    public UserFilter getUserFilter(@Param("filterOption") int option,@Param("userId") long userId);
    
    public List<Long> getUserFilterList(int option);

    public int insert(UserFilter userFilter);

    public void deleteById(int id);

}