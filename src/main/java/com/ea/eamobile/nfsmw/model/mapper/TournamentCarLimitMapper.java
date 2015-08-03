package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.TournamentCarLimit;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Nov 20 11:13:32 CST 2012
 * @since 1.0
 */
public interface TournamentCarLimitMapper {

    public List<String> getTournamentCarLimitByGroupId(int groupId);

    public List<TournamentCarLimit> getTournamentCarLimitList();

}