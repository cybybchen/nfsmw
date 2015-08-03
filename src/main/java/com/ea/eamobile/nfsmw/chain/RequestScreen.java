package com.ea.eamobile.nfsmw.chain;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.constants.ErrorConst;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands.ErrorCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.GhostInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBindingConfirmCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBindingInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBindingResultCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBindingStartCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBindingTokenCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBuyCarCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBuyItemCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestChallengeMathInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestGarageCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestGetRewardCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestGhostRecordCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestGotchaCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestIapCheckCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestModeInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestModifyUserInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestProfileLikeCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestProfileNextCarCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestProfileReportCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestProfileUserDataCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestProfileVSCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestRaceResultCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestRaceStartCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestRecordUserRaceActionCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestRegistJaguarCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestResourceCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestRpLeaderboardCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestStoreDetailCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestSystemCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestTournamentCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestTournamentDetailCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestTournamentNum;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestTournamentRewardDetailCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestTournamentSignUpCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestTrackCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestTutorialRewardCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestUpgradeSlotCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestUseChartletCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseCommand.Builder;

public abstract class RequestScreen implements RequestHandle {

    private RequestHandle nullUserErrorHandle = new NullUserErrorHandle();
    private static final Logger logger = LoggerFactory.getLogger(RequestScreen.class);

    protected abstract boolean handleLoginCommand(RequestCommand request, Builder responseBuilder);

    protected abstract boolean handleCommand(RequestTrackCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestRaceResultCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestTournamentCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestRegistJaguarCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestTournamentRewardDetailCommand cmd, Builder responseBuilder,
            User user);

    protected abstract boolean handleCommand(RequestTournamentDetailCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestModeInfoCommand cmd, Builder responseBuilder, User user);

    /**
     * 添加游戏版本号入参以便DLC分版本下发
     * 
     * @param cmd
     * @param responseBuilder
     * @param user
     * @param gameEdition
     * @return
     */
    protected abstract boolean handleCommand(RequestResourceCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestTournamentSignUpCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestRaceStartCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestBindingStartCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestBindingResultCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestBindingConfirmCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestGarageCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestBuyCarCommand cmd, Builder responseBuilder, User user);
    
//    protected abstract boolean handleCommand(RequestSendCar cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestUpgradeSlotCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestUseChartletCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestBuyItemCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestStoreDetailCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestGetRewardCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestModifyUserInfoCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestIapCheckCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestRpLeaderboardCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestProfileUserDataCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestProfileLikeCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestProfileReportCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestProfileVSCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestRecordUserRaceActionCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestProfileNextCarCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestTutorialRewardCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestGhostRecordCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestChallengeMathInfoCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestGotchaCommand cmd, Builder responseBuilder, User user);
    
//    protected abstract boolean handleCommand(RequestGatcha cmd, Builder responseBuilder, User user);
    
//    protected abstract boolean handleCommand(RequestGatchaInfo cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestSystemCommand cmd, Builder responseBuilder, User user);

    protected abstract boolean handleCommand(RequestTournamentNum cmd, Builder responseBuilder, User user);
    
    protected abstract boolean handleCommand(RequestBindingTokenCommand cmd, Builder responseBuilder, User user);
    protected abstract boolean handleCommand(RequestBindingInfoCommand cmd, Builder responseBuilder, User user);

    @Override
    public boolean handleRequest(NFSRequest req, NFSResponse rep) {
        boolean result = true;
        RequestCommand request = req.command;
        ResponseCommand.Builder responseBuilder = rep.command;
        User user = req.user;

        if (user != null && ((user.getAccountStatus() & Const.IS_BAN) == 1)) {
            ErrorCommand.Builder erBuilder = ErrorCommand.newBuilder();
            erBuilder.setCode(String.valueOf(ErrorConst.BIND_USER.getCode()));
            erBuilder.setMessage(ErrorConst.BIND_USER.getMesssage());
            responseBuilder.setErrorCommand(erBuilder.build());
            return false;
        }
        if (request.hasLoginCommand()) {
            handleLoginCommand(request, responseBuilder);
        } else {
            /**
             * <pre>
             * 排除非登录接口用户为空的情况
             * 注意：user为空的情况出现在首次登录，不会出现请求其他cmd时用户为空的情况
             * 判断：非login cmd ，user==null就返回用户空错误
             * </pre>
             */
            if (user == null) {
                nullUserErrorHandle.handleRequest(req, rep);
                return false;
            }
        }

        if (request.hasTrackCommand()) {
            RequestTrackCommand cmd = request.getTrackCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }
        if (request.hasRaceResultCommand()) {
            RequestRaceResultCommand cmd = request.getRaceResultCommand();
            if (result) {
                cmd = initRequestRaceResultCmd(cmd);
                result = handleCommand(cmd, responseBuilder, user);
            }
        }

        if (request.hasRequestTournamentNum()) {
            RequestTournamentNum cmd = request.getRequestTournamentNum();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }

        if (request.hasTournamentCommand()) {
            RequestTournamentCommand cmd = request.getTournamentCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }

        if (request.hasRegistJaguar()) {
            RequestRegistJaguarCommand cmd = request.getRegistJaguar();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }

        if (request.hasTournamentRewardDetailCommand()) {
            RequestTournamentRewardDetailCommand cmd = request.getTournamentRewardDetailCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }
        if (request.hasTournamentDetailCommand()) {
            RequestTournamentDetailCommand cmd = request.getTournamentDetailCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }
        if (request.hasGhostCommand()) {
            RequestModeInfoCommand cmd = request.getGhostCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }

        if (request.hasResourceCommand()) {
            RequestResourceCommand cmd = request.getResourceCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }
        if (request.hasTournamentSignUpCommand()) {
            RequestTournamentSignUpCommand cmd = request.getTournamentSignUpCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }
        if (request.hasRaceStartCommand()) {
            RequestRaceStartCommand cmd = request.getRaceStartCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }
        if (request.hasBindingStartCommand()) {
            RequestBindingStartCommand cmd = request.getBindingStartCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }
        if (request.hasBindingResultCommand()) {
            RequestBindingResultCommand cmd = request.getBindingResultCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }
        if (request.hasBindingConfirmCommand()) {
            RequestBindingConfirmCommand cmd = request.getBindingConfirmCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }
        if (request.hasBindingTokenCommand()) {
        	RequestBindingTokenCommand cmd = request.getBindingTokenCommand();
        	if (result) {
        		result = handleCommand(cmd, responseBuilder, user);
        	}
        }
        if (request.hasBindingInfoCommand()) {
        	RequestBindingInfoCommand cmd = request.getBindingInfoCommand();
        	if (result) {
        		result = handleCommand(cmd, responseBuilder, user);
        	}
        }
        // garage
        if (request.hasGarageCommand()) {
            RequestGarageCommand cmd = request.getGarageCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }
        if (request.hasBuyCarCommand()) {
            RequestBuyCarCommand cmd = request.getBuyCarCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }
        if (request.hasUpgradeSlotCommand()) {
            RequestUpgradeSlotCommand cmd = request.getUpgradeSlotCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }
        if (request.hasUseChartletCommand()) {
            RequestUseChartletCommand cmd = request.getUseChartletCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }

        if (request.hasBuyItemCommand()) {
            RequestBuyItemCommand cmd = request.getBuyItemCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }

        if (request.hasStoreDetailCommand()) {
            RequestStoreDetailCommand cmd = request.getStoreDetailCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }

        if (request.hasGetRward()) {
            RequestGetRewardCommand cmd = request.getGetRward();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }
        if (request.hasModifyUserInfoCommand()) {
            RequestModifyUserInfoCommand cmd = request.getModifyUserInfoCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }

        if (request.hasIapCheckCommand()) {
            RequestIapCheckCommand cmd = request.getIapCheckCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }

        if (request.hasRpLeaderboardCommand()) {
            RequestRpLeaderboardCommand cmd = request.getRpLeaderboardCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }

        if (request.hasProfileUserDataCommand()) {
            RequestProfileUserDataCommand cmd = request.getProfileUserDataCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }

        if (request.hasProfileLikeCommand()) {
            RequestProfileLikeCommand cmd = request.getProfileLikeCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }

        if (request.hasProfileReportCommand()) {
            RequestProfileReportCommand cmd = request.getProfileReportCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }

        if (request.hasProfileVSCommand()) {
            RequestProfileVSCommand cmd = request.getProfileVSCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }

        if (request.hasRecordUserRaceActionCommand()) {
            RequestRecordUserRaceActionCommand cmd = request.getRecordUserRaceActionCommand();

            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }

        if (request.hasProfileNextCarCommand()) {
            RequestProfileNextCarCommand cmd = request.getProfileNextCarCommand();

            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }

        if (request.hasTutorialRewardCommand()) {
            RequestTutorialRewardCommand cmd = request.getTutorialRewardCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }

        if (request.hasGhostRecordCommand()) {
            RequestGhostRecordCommand cmd = request.getGhostRecordCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }

        if (request.hasChallengeMathInfoCommand()) {
            RequestChallengeMathInfoCommand cmd = request.getChallengeMathInfoCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }
        if (request.hasCheatInfoCommand()) {
            if (result) {
                result = handleCommand(request, responseBuilder, user);
            }
        }
        if (request.hasSystemCommand()) {
            RequestSystemCommand cmd = request.getSystemCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }

        if (request.hasGotchaCommand()) {
        	logger.info("99999999999999999 hasGotchaCommand");
            RequestGotchaCommand cmd = request.getGotchaCommand();
            if (result) {
                result = handleCommand(cmd, responseBuilder, user);
            }
        }

//        if (request.hasRequestSendCar()) {
//            RequestSendCar cmd = request.getRequestSendCar();
//            if (result) {
//                result = handleCommand(cmd, responseBuilder, user);
//            }
//        }
        
//        if (request.hasRequestGatcha()) {
//        	logger.info("99999999999999999 hasRequestGotcha");
//            //RequestGotchaCommand cmd = request.getGotchaCommand();
//            RequestGatcha cmd = request.getRequestGatcha();
//            if (result) {
//                result = handleCommand(cmd, responseBuilder, user);
//            }
//        }
        return result;
    }

    private RequestRaceResultCommand initRequestRaceResultCmd(RequestRaceResultCommand reqcmd) {
        RequestRaceResultCommand.Builder rrrcBuilder = RequestRaceResultCommand.newBuilder(reqcmd);
        GhostInfo.Builder gBuilder = GhostInfo.newBuilder(reqcmd.getGhosts());
        float raceTime = reqcmd.getGhosts().getRaceTime();
        BigDecimal racebd = new BigDecimal(raceTime);
        raceTime = racebd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        float avergeSpeed = reqcmd.getGhosts().getAverageSpd();
        BigDecimal raceas = new BigDecimal(avergeSpeed);
        avergeSpeed = raceas.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        gBuilder.setRaceTime(raceTime);
        gBuilder.setAverageSpd(avergeSpeed);
        rrrcBuilder.setGhosts(gBuilder.build());
        return rrrcBuilder.build();
    }

}
