package com.ea.eamobile.nfsmw.service.command;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestSystemCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestSystemCommand.SystemConfigType;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseCommand.Builder;
import com.ea.eamobile.nfsmw.service.UserConfigService;

@Service
public class ConfigCommandService {

    @Autowired
    private UserConfigService configService;

    public void config(RequestSystemCommand reqcmd, Builder responseBuilder, User user) throws SQLException {
        SystemConfigType type = reqcmd.getType();
        String content = reqcmd.getContent();
        configService.save(user.getId(), type, content);
    }

}
