package com.ea.eamobile.nfsmw.service.command.racetype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Match;

@Service
public class RaceTypeHandlerFactory {

    @Autowired
    SpeedRunHandler speedRunHandler;
    @Autowired
    HotRideHandler hotRideHandler;
    @Autowired
    OneVsFiveHandler oneVsFiveHandler;
    @Autowired
    OneVsOneHandler oneVsOneHandler;

    public RaceTypeHandler create(int raceType) {
        RaceTypeHandler handler = null;
        switch (raceType) {
        case Match.RACE_TYPE_1V1:
            handler = oneVsOneHandler;
            break;
        case Match.RACE_TYPE_1V5:
            handler = oneVsFiveHandler;
            break;
        case Match.RACE_TYPE_SPEED_RUN:
            handler = speedRunHandler;
            break;
        case Match.RACE_TYPE_HOT_RIDE:
            handler = hotRideHandler;
            break;
        default:
            break;
        }
        return handler;
    }
}
