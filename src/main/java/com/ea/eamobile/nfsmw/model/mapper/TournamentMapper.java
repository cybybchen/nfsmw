package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.Tournament;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Sep 24 13:49:57 CST 2012
 * @since 1.0
 */
public interface TournamentMapper {

    public Tournament getTournament(int id);

    public List<Tournament> getTournamentList();

    public int insert(Tournament tournament);

    public void update(Tournament tournament);

    public void deleteById(int id);

}