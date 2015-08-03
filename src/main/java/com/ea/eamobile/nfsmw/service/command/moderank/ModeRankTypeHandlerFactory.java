package com.ea.eamobile.nfsmw.service.command.moderank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.RaceMode;

@Service
public class ModeRankTypeHandlerFactory {

    @Autowired
    RaceTimeHandler raceTimeHandler;
    @Autowired
    AverageSpeedHandler speedHandler;

    public ModeRankTypeHandler create(RaceMode mode) {
        return create(mode.getRankType());
    }

    public ModeRankTypeHandler create(int rankType) {
        ModeRankTypeHandler handler = null;
        switch (rankType) {
        case Match.MODE_RANK_TYPE_BY_TIME:
            handler = raceTimeHandler;
            break;
        case Match.MODE_RANK_TYPE_BY_AVGSPEED:
            handler = speedHandler;
            break;
        default:
            break;
        }
        return handler;
    }
}
