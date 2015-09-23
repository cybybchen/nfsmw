package com.ea.eamobile.nfsmw.service.command.raceresult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Match;

@Service
public class RaceResultHandlerFactory {

    @Autowired
    CareerModeRaceResultHandler careerHandler;
    @Autowired
    TierModeRaceResultHandler tierHandler;
    @Autowired
    DailyModeRaceResultHandler dailyHandler;
    @Autowired
    GoldModeRaceResultHandler goldHandler;

    public RaceResultHandler create(int gameMode) {

        RaceResultHandler handler = null;
        switch (gameMode) {
        case Match.CAREER_MODE:
            handler = careerHandler;
            break;
        case Match.TIER_MODE:
            handler = tierHandler;
            break;
        case Match.EVERYDAY_RACE_MODE:
            handler = dailyHandler;
            break;
        case Match.GOLD_MODE:
        	handler = goldHandler;
        	break;
        default:
            break;
        }
        return handler;
    }

}
