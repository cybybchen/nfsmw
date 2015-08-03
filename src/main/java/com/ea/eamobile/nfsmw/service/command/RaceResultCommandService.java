package com.ea.eamobile.nfsmw.service.command;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestRaceResultCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseRaceResultCommand;
import com.ea.eamobile.nfsmw.service.command.raceresult.RaceResultHandler;
import com.ea.eamobile.nfsmw.service.command.raceresult.RaceResultHandlerFactory;

@Service
public class RaceResultCommandService {

    @Autowired
    private RaceResultHandlerFactory handlerFactory;


    public ResponseRaceResultCommand getResponseRaceResultCommand(RequestRaceResultCommand reqcmd, User user,
            Commands.ResponseCommand.Builder responseBuilder) throws SQLException {

        RaceResultHandler handler = handlerFactory.create(reqcmd.getGameMode());
        return handler.handle(reqcmd, user, responseBuilder);
    }

}
