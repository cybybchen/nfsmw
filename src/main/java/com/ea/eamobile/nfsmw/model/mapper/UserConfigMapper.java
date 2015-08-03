package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ea.eamobile.nfsmw.model.UserConfig;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Apr 10 13:50:59 CST 2013
 * @since 1.0
 */
public interface UserConfigMapper {

    public UserConfig getUserConfig(@Param("userId") long userId,@Param("type") int type);

    public List<UserConfig> getUserConfigList(long userId);

    public int insert(UserConfig userConfig);

    public void update(UserConfig userConfig);

}