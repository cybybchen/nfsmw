package com.ea.eamobile.nfsmw.service.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CarConst;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestRegistJaguarCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseRegistJaguarCommand;
import com.ea.eamobile.nfsmw.service.UserCarService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.view.CarView;

@Service
public class RegistJaguarCommandService {

    @Autowired
    private UserService userService;
    @Autowired
    private PushCommandService pushService;
    @Autowired
    private UserCarService userCarService;

    public ResponseRegistJaguarCommand getResponseRegistJaguarCommand(RequestRegistJaguarCommand reqcmd, User user,
            Commands.ResponseCommand.Builder responseBuilder)throws SQLException {
        ResponseRegistJaguarCommand.Builder builder = ResponseRegistJaguarCommand.newBuilder();
        long userId = user.getId();
        if(reqcmd.getJaguarType()==0){
            user.setIsWriteJaguar(1);
            userService.updateUser(user);
            builder.setLevelRaceCanUse(true);
        }
        else if(reqcmd.getJaguarType()==1){
            userCarService.buyCar(userId, CarConst.FREE_CAR,true);
            List<CarView> carList=userCarService.getGarageCarListByCarId(userId,CarConst.FREE_CAR);
            List<CarView> result=new ArrayList<CarView>();
            for(CarView cv:carList){
                cv.setLock(false);
                cv.setStatus(1);
                result.add(cv);
            }
            
            pushService.pushUserCarInfoCommand(responseBuilder, result,userId);
            builder.setLevelRaceCanUse(true);
        }
        return builder.build();
    }

   
}
