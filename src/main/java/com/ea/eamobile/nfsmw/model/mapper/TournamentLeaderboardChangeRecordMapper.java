package com.ea.eamobile.nfsmw.model.mapper;

import java.util.List;

import com.ea.eamobile.nfsmw.model.TournamentLeaderboardChangeRecord;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Feb 27 14:34:50 CST 2013
 * @since 1.0
 */
public interface TournamentLeaderboardChangeRecordMapper {

    public TournamentLeaderboardChangeRecord getTournamentLeaderboardChangeRecord(int id);

    public List<TournamentLeaderboardChangeRecord> getTournamentLeaderboardChangeRecordList();

    public int insert(TournamentLeaderboardChangeRecord tournamentLeaderboardChangeRecord);

    public void update(TournamentLeaderboardChangeRecord tournamentLeaderboardChangeRecord);

    public void deleteById(int id);

}