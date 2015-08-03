package com.ea.eamobile.nfsmw.service.command.moderank;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.CareerBestRacetimeRecord;
import com.ea.eamobile.nfsmw.model.ComparatorCareerBestRacetimeRecord;
import com.ea.eamobile.nfsmw.model.TournamentUser;
import com.ea.eamobile.nfsmw.protoc.Commands.GhostInfo;

@Service
public class RaceTimeHandler extends ModeRankTypeHandler {

    @Override
    public float getGhostResult(GhostInfo ghost) {
        return ghost.getRaceTime();
    }

    @Override
    public float diffResult(float time1, float time2) {
        return time1 - time2;
    }

    @Override
    public boolean isFaster(float time1, float time2) {
        return time1 < time2;
    }

    @Override
    public float getRecord(CareerBestRacetimeRecord record) {
        return record.getRaceTime();
    }

    @Override
    public boolean isFaster(float raceTime, float avgSpeed, CareerBestRacetimeRecord record) {
        return raceTime < record.getRaceTime();
    }

    @Override
    public boolean isFaster(float raceTime1, float avgSpeed1, float raceTime2, float avgSpeed2) {
        return raceTime1 < raceTime2;
    }

    @Override
    public int getRank(int onlineId, TournamentUser tourUser) {
        return tourRecordRankService.getRank(tourUser,Match.MODE_RANK_TYPE_BY_TIME);
    }

    @Override
    public float getResult(TournamentUser tournamentUser) {
        // TODO Auto-generated method stub
        return tournamentUser.getResult();
    }

    @Override
    public void sortRecord(List<CareerBestRacetimeRecord> records) {
        Collections.sort(records, new ComparatorCareerBestRacetimeRecord());
    }

}
