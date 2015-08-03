package com.ea.eamobile.nfsmw.service.command.raceresult;

import java.sql.SQLException;

import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestRaceResultCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseRaceResultCommand;

/**
 * 比赛结果处理器接口 名字应该改成RaceModeHandler
 * 
 * @author ma.ruofei@ea.com
 * 
 */
public interface RaceResultHandler {

    /**
     * 处理比赛结果
     * 
     * @param reqcmd
     * @param user
     * @param responseBuilder
     * @return
     * @throws SQLException 
     */
    public ResponseRaceResultCommand handle(RequestRaceResultCommand reqcmd, User user,
            Commands.ResponseCommand.Builder responseBuilder) throws SQLException;   

    /**
     * 处理ghost
     * @param reqcmd
     * @param user
     * @param responseCommand
     * @return
     */
//    public ResponseRacerForGhostCommand handleGhost(RequestModeInfoCommand reqcmd, User user,
//            ResponseRacerForGhostCommand responseCommand);

}
