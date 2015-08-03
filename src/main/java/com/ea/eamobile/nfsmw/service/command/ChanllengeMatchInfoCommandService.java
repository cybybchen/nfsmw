package com.ea.eamobile.nfsmw.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestChallengeMathInfoCommand;

@Service
public class ChanllengeMatchInfoCommandService {

    @Autowired
    private PushCommandService pushService;
    

    public void setChanllengeMatchInfoCommand(RequestChallengeMathInfoCommand reqcmd,
            User user, Commands.ResponseCommand.Builder responseBuilder) {
        
        pushService.pushDailyRaceInfoCommand(responseBuilder, user, 1, 0);
       
    }

}
