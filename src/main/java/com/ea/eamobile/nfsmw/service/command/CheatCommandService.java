package com.ea.eamobile.nfsmw.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.CheatUserInfoRecord;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestCheatInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseCommand.Builder;
import com.ea.eamobile.nfsmw.service.CheatUserInfoRecordService;

@Service
public class CheatCommandService extends BaseCommandService {

    @Autowired
    private CheatUserInfoRecordService cheatUserInfoRecordService;

    public void getResponseCheatCommand(RequestCommand request, Builder responseBuilder, User user) {
        RequestCheatInfoCommand reqcmd = request.getCheatInfoCommand();
        // log.warn("cheat:" + user.getId() + "," + reqcmd.getCheatType() + "," + reqcmd.getCarConfigName());
        // if (reqcmd.getCheatType() == Const.CHEAT_TYPE_CAR_CONFIG_FILE) {
        // ErrorCommand errorCommand = buildErrorCommand(ErrorConst.CHEAT_RACETIME);
        // responseBuilder.setErrorCommand(errorCommand);
        // }
        int cheatType = 0;
        String carName = "";
        int middleGearSpeed = 0;
        int topGearSpeed = 0;
        String md5 = "";
        String userSelectCarId = "";
        if (reqcmd.hasCarConfigName()) {
            carName = reqcmd.getCarConfigName();
        }
        if (reqcmd.hasCheatType()) {
            cheatType = reqcmd.getCheatType();
        }
        if (reqcmd.hasExeFileMD5()) {
            md5 = reqcmd.getExeFileMD5();
        }
        if (reqcmd.hasMidGearSpd()) {
            middleGearSpeed = reqcmd.getMidGearSpd();
        }
        if (reqcmd.hasTopGearSpd()) {
            topGearSpeed = reqcmd.getTopGearSpd();
        }
        if (reqcmd.hasUserSelectCarID()) {
            userSelectCarId = reqcmd.getUserSelectCarID();
        }
        CheatUserInfoRecord cheatUserInfoRecord = new CheatUserInfoRecord();
        cheatUserInfoRecord.setCheatType(cheatType);
        cheatUserInfoRecord.setUserId(user.getId());
        cheatUserInfoRecord.setCarName(carName);
        cheatUserInfoRecord.setMiddleGearSpeed(middleGearSpeed);
        cheatUserInfoRecord.setTopGearSpeed(topGearSpeed);
        cheatUserInfoRecord.setVersion(request.getHead().getVersion());
        cheatUserInfoRecord.setMd5(md5);
        cheatUserInfoRecord.setUserSelectCarId(userSelectCarId);
        cheatUserInfoRecordService.insert(cheatUserInfoRecord);
    }

}
