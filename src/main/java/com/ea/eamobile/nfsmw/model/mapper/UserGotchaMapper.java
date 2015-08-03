package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.UserGotcha;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Mar 27 10:01:11 CST 2013
 * @since 1.0
 */
public interface UserGotchaMapper {

    public UserGotcha getUserGotcha(long id);
    
    public UserGotcha getUserGotchaByUidGid(@Param("userId") long userId,@Param("gotchaId")int gotchaId);

    public List<UserGotcha> getUserGotchaList();

    public int insert(UserGotcha userGotcha);

    public void update(UserGotcha userGotcha);

    public void deleteById(int id);

}