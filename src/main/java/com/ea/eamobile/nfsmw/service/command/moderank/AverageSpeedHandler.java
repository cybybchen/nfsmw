package com.ea.eamobile.nfsmw.service.command.moderank;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.CareerBestRacetimeRecord;
import com.ea.eamobile.nfsmw.model.ComparatorSpeedCareerBestRacetimeRecord;
import com.ea.eamobile.nfsmw.model.TournamentUser;
import com.ea.eamobile.nfsmw.protoc.Commands.GhostInfo;

@Service
public class AverageSpeedHandler extends ModeRankTypeHandler {

    
    @Override
    public float getGhostResult(GhostInfo ghost) {
        return ghost.getAverageSpd();
    }

    @Override
    public float diffResult(float time1, float time2) {
        return time2 - time1;
    }

    @Override
    public boolean isFaster(float time1, float time2) {
        return time1 > time2;
    }

    @Override
    public float getRecord(CareerBestRacetimeRecord record) {
        return record.getAverageSpeed();
    }

    @Override
    public boolean isFaster(float raceTime, float avgSpeed, CareerBestRacetimeRecord record) {
        return avgSpeed > record.getAverageSpeed();
    }

    @Override
    public boolean isFaster(float raceTime1, float avgSpeed1, float raceTime2, float avgSpeed2) {
        return avgSpeed1 > avgSpeed2;
    }

    @Override
    public int getRank(int onlineId, TournamentUser tourUser) {
        return tourRecordRankService.getRank(tourUser,Match.MODE_RANK_TYPE_BY_AVGSPEED);
    }

    @Override
    public float getResult(TournamentUser tournamentUser) {
        // TODO Auto-generated method stub
        return tournamentUser.getAverageSpeed();
    }

    @Override
    public void sortRecord(List<CareerBestRacetimeRecord> records) {
        Collections.sort(records, new ComparatorSpeedCareerBestRacetimeRecord());
    }


}
