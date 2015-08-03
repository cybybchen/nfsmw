package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.TournamentGroupClass;
import com.ea.eamobile.nfsmw.model.mapper.TournamentGroupClassMapper;

/**
 * @author ma.ruofei
 * @version 1.0 Thu Jan 24 17:21:03 CST 2013
 * @since 1.0
 */
@Service
public class TournamentGroupClassService {

    @Autowired
    private TournamentGroupClassMapper tournamentGroupClassMapper;

    public TournamentGroupClass getTournamentGroupClass(int classId) {
        return tournamentGroupClassMapper.getTournamentGroupClass(classId);
    }

    public int insert(TournamentGroupClass tournamentGroupClass) {
        return tournamentGroupClassMapper.insert(tournamentGroupClass);
    }

    public void update(TournamentGroupClass tournamentGroupClass) {
        tournamentGroupClassMapper.update(tournamentGroupClass);
    }

    public int getMaxTournamentGroupClass(int tournamentOnlineId, int groupId) {
        Integer result = tournamentGroupClassMapper.getMaxTournamentGroupClass(tournamentOnlineId, groupId);
        if (result == null) {
            result = 0;
        }
        return result;

    }

    public List<Integer> getTournamentGroupClassListByOnlineId(int tournamentOnlineId) {
        return tournamentGroupClassMapper.getTournamentGroupClassListByOnlineId(tournamentOnlineId);
    }

}