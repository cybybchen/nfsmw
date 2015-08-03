package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.TournamentLeaderboardChangeRecord;
import com.ea.eamobile.nfsmw.model.mapper.TournamentLeaderboardChangeRecordMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Feb 27 14:34:50 CST 2013
 * @since 1.0
 */
 @Service
public class TournamentLeaderboardChangeRecordService {

    @Autowired
    private TournamentLeaderboardChangeRecordMapper tournamentLeaderboardChangeRecordMapper;
    
    public TournamentLeaderboardChangeRecord getTournamentLeaderboardChangeRecord(int id){
        return tournamentLeaderboardChangeRecordMapper.getTournamentLeaderboardChangeRecord(id);
    }

    public List<TournamentLeaderboardChangeRecord> getTournamentLeaderboardChangeRecordList(){
        return tournamentLeaderboardChangeRecordMapper.getTournamentLeaderboardChangeRecordList();
    }

    public int insert(TournamentLeaderboardChangeRecord tournamentLeaderboardChangeRecord){
        return tournamentLeaderboardChangeRecordMapper.insert(tournamentLeaderboardChangeRecord);
    }

    public void update(TournamentLeaderboardChangeRecord tournamentLeaderboardChangeRecord){
		    tournamentLeaderboardChangeRecordMapper.update(tournamentLeaderboardChangeRecord);    
    }

    public void deleteById(int id){
        tournamentLeaderboardChangeRecordMapper.deleteById(id);
    }

}