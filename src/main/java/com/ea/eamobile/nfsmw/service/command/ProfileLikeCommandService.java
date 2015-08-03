package com.ea.eamobile.nfsmw.service.command;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserCar;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestProfileLikeCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseProfileLikeCommand;
import com.ea.eamobile.nfsmw.service.UserCarLikeLogService;
import com.ea.eamobile.nfsmw.service.UserCarLikeService;
import com.ea.eamobile.nfsmw.service.UserCarService;

@Service
public class ProfileLikeCommandService {

    @Autowired
    private UserCarLikeService userCarLikeService;
    @Autowired
    private UserCarLikeLogService userCarLikeLogService;
    @Autowired
    private UserCarService userCarService;

    public ResponseProfileLikeCommand getProfileLikeCommand(RequestProfileLikeCommand reqcmd, User user,
            Commands.ResponseCommand.Builder responseBuilder) throws SQLException {

        ResponseProfileLikeCommand.Builder builder = ResponseProfileLikeCommand.newBuilder();

        buildProfileLikeCommand(builder, reqcmd.getUserCarId(), user.getId());

        return builder.build();
    }

    private void buildProfileLikeCommand(ResponseProfileLikeCommand.Builder builder, long userCarId, long userId)
            throws SQLException {

        int likeNum = 0;

        boolean hasLiked = userCarLikeLogService.hasLog(userId, userCarId);
        if (!hasLiked) {
            UserCar userCar = userCarService.getUserCar(userCarId);
            if (userCar != null) {
                userCarLikeLogService.insert(userId, userCarId, userCar.getUserId());
                userCarLikeService.insert(userCarId);
                likeNum = userCarLikeService.getLikeCountByUserCarId(userCarId);
            }
        }
        builder.setLikeNum(likeNum);
    }

}
