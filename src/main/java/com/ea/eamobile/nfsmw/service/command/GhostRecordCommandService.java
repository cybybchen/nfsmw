package com.ea.eamobile.nfsmw.service.command;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestGhostRecordCommand;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.utils.ConfigUtil;

@Service
public class GhostRecordCommandService {

    @Autowired
    private UserService userService;

    Logger log = LoggerFactory.getLogger(GhostRecordCommandService.class);

    public void getResponseTutorialRewardCommand(RequestGhostRecordCommand reqcmd, long userId,
            Commands.ResponseCommand.Builder responseBuilder) {

        User user = userService.getUser(userId);
        Date now = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        DateFormat formatToHour = new SimpleDateFormat("yyyy-MM-dd-HH");
        boolean mkdirSuccess = true;
        String path = ConfigUtil.GHOSTRECORD_PATH + formatToHour.format(now) + "/" + user.getId();
        String fileName = user.getId() + "-" + format.format(now) + "-" + reqcmd.getModeId();
        File dir = new File(path);
        if (!dir.exists()) {
            mkdirSuccess = dir.mkdirs();
            if (!mkdirSuccess) {
                log.error("mkdir erro");
            }
        }
        if (mkdirSuccess) {
            File file = new File(path + "/" + fileName);
            if (!file.exists()) {
                try {
                    boolean createFileSuccess = file.createNewFile();
                    if (createFileSuccess) {
                        FileOutputStream out = new FileOutputStream(file, true);
                        out.write(reqcmd.toByteArray());
                        out.close();
                    }

                } catch (IOException e) {

                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

}
