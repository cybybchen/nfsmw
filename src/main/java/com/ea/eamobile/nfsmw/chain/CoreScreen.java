package com.ea.eamobile.nfsmw.chain;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBindingConfirmCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBindingInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBindingResultCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBindingStartCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBindingTokenCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBuyCarCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestBuyItemCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestChallengeMathInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestCollectEnergyCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestEnergyTimeCommand;
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
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseBindingConfirmCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseBindingInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseBindingResultCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseBindingStartCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseBindingTokenCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseBuyCarCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseBuyItemCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseCommand.Builder;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseEnergyTimeCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseGarageCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseGotchaCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseIapCheckCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseModeInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseModifyUserInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseProfileLikeCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseProfileNextCarCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseProfileReportCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseProfileUserDataCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseProfileVSCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseRaceResultCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseRaceStartCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseRacerForGhostCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseRegistJaguarCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseResourceCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseRpLeaderboardCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseStoreDetailCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseTournamentCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseTournamentDetailCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseTournamentRaceResultCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseTournamentRaceStartCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseTournamentRewardDetailCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseTournamentSignUpCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseTutorialRewardCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseUpgradeSlotCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseUseChartletCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.responseTournamentRewardCommand;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.service.command.BindingCommandService;
import com.ea.eamobile.nfsmw.service.command.ChanllengeMatchInfoCommandService;
import com.ea.eamobile.nfsmw.service.command.ConfigCommandService;
import com.ea.eamobile.nfsmw.service.command.EnergyCommandService;
import com.ea.eamobile.nfsmw.service.command.GarageCommandService;
import com.ea.eamobile.nfsmw.service.command.GhostCommandService;
import com.ea.eamobile.nfsmw.service.command.GhostRecordCommandService;
import com.ea.eamobile.nfsmw.service.command.GotchaCommandService;
import com.ea.eamobile.nfsmw.service.command.IapCheckCommandService;
import com.ea.eamobile.nfsmw.service.command.LoginCommandService;
import com.ea.eamobile.nfsmw.service.command.ModeInfoCommandService;
import com.ea.eamobile.nfsmw.service.command.ModifyUserInfoCommandService;
import com.ea.eamobile.nfsmw.service.command.ProfileLikeCommandService;
import com.ea.eamobile.nfsmw.service.command.ProfileNextCarCommandService;
import com.ea.eamobile.nfsmw.service.command.ProfileReportCommandService;
import com.ea.eamobile.nfsmw.service.command.ProfileUserDataCommandService;
import com.ea.eamobile.nfsmw.service.command.ProfileVSCommandService;
import com.ea.eamobile.nfsmw.service.command.PushCommandService;
import com.ea.eamobile.nfsmw.service.command.RaceResultCommandService;
import com.ea.eamobile.nfsmw.service.command.RaceStartCommandService;
import com.ea.eamobile.nfsmw.service.command.RecordUserRaceActionCommandService;
import com.ea.eamobile.nfsmw.service.command.RegistJaguarCommandService;
import com.ea.eamobile.nfsmw.service.command.ResourceCommandService;
import com.ea.eamobile.nfsmw.service.command.RpLeaderboardCommandService;
import com.ea.eamobile.nfsmw.service.command.StoreDetailCommandService;
import com.ea.eamobile.nfsmw.service.command.TrackCommandService;
import com.ea.eamobile.nfsmw.service.command.TutorialRewardCommandService;
import com.ea.eamobile.nfsmw.service.command.tournament.TournamentCommandService;
import com.ea.eamobile.nfsmw.service.command.tournament.TournamentDetailCommandService;
import com.ea.eamobile.nfsmw.service.command.tournament.TournamentRaceResultCommandService;
import com.ea.eamobile.nfsmw.service.command.tournament.TournamentRaceStartCommandService;
import com.ea.eamobile.nfsmw.service.command.tournament.TournamentRewardDetailCommandService;
import com.ea.eamobile.nfsmw.service.command.tournament.TournamentSignUpCommandService;

@Service
public class CoreScreen extends RequestScreen {
    private static final Logger log = LoggerFactory.getLogger(CoreScreen.class);
    @Autowired
    private RaceResultCommandService raceResultCommandService;
    @Autowired
    private TournamentRaceResultCommandService tournamentRaceResultCommandService;
    @Autowired
    private TournamentCommandService tournamentCommandService;
    @Autowired
    private TournamentRewardDetailCommandService tournamentRewardDetailCommandService;
    @Autowired
    private TournamentDetailCommandService tournamentDetailCommandService;
    @Autowired
    private GhostCommandService ghostCommandService;
    @Autowired
    private LoginCommandService loginCommandService;
    @Autowired
    private ResourceCommandService resourceCommandService;
    @Autowired
    private BindingCommandService bindingCommandService;
    @Autowired
    private TournamentSignUpCommandService tournamentSignUpCommandService;
    @Autowired
    private ModeInfoCommandService modeInfoCommandService;
    @Autowired
    private RaceStartCommandService raceStartService;
    @Autowired
    private TournamentRaceStartCommandService tournamentRaceStartService;
    @Autowired
    private GarageCommandService garageCommandService;
    @Autowired
    private ModifyUserInfoCommandService modifyUserInfoCommandService;
    @Autowired
    private RegistJaguarCommandService registJaguarCommandService;
    @Autowired
    private StoreDetailCommandService storeDetailCommandService;
    @Autowired
    private IapCheckCommandService iapCheckCommandService;
    @Autowired
    private TrackCommandService trackCommandService;
    @Autowired
    private RpLeaderboardCommandService rpLeaderboardCommandService;
    @Autowired
    private ProfileUserDataCommandService profileUserDataCommandService;
    @Autowired
    private ProfileLikeCommandService profileLikeCommandService;
    @Autowired
    private ProfileReportCommandService profileReportCommandService;
    @Autowired
    private ProfileVSCommandService profileVSCommandService;

    @Autowired
    private RecordUserRaceActionCommandService recordUserRaceActionCommandService;
    @Autowired
    private ProfileNextCarCommandService profileNextCarCommandService;

    @Autowired
    private TutorialRewardCommandService tutorialRewardCommandService;
    @Autowired
    private ChanllengeMatchInfoCommandService chanllengeMatchInfoCommandService;
    @Autowired
    private GhostRecordCommandService ghostRecordCommandService;
    @Autowired
    private UserService userService;
    @Autowired
    private PushCommandService pushService;
    @Autowired
    private ConfigCommandService configService;
    @Autowired
    private GotchaCommandService gotchaCommandService;
    @Autowired
    private EnergyCommandService energyCommandService;
    
    @Override
    protected boolean handleLoginCommand(RequestCommand request, Builder responseBuilder) {
        try {
            loginCommandService.buildResponseUserInfo(request, responseBuilder);
        } catch (Exception e) {
        	e.printStackTrace();
            log.error("login err", e.getMessage());
        }
        
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTrackCommand cmd, Builder responseBuilder, User user) {
        try {
            trackCommandService.buildTrackCommand(cmd, responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        regainEnergy(responseBuilder, user);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRaceResultCommand cmd, Builder responseBuilder, User user) {
        if (cmd.getGameMode() == Match.CAREER_MODE || cmd.getGameMode() == Match.TIER_MODE
                || cmd.getGameMode() == Match.EVERYDAY_RACE_MODE) {
            ResponseRaceResultCommand response;
            try {
                response = raceResultCommandService.getResponseRaceResultCommand(cmd, user, responseBuilder);
                if (response != null) {
                    responseBuilder.setRaceResultCommand(response);
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else if (cmd.getGameMode() == Match.TOURNAMENT_MODE) {
            ResponseTournamentRaceResultCommand response;
            try {
                response = tournamentRaceResultCommandService.getResponseTournamentRaceResultCommand(cmd, user,
                        responseBuilder);
                if (response != null) {
                    responseBuilder.setTournamentRaceReault(response);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentCommand cmd, Builder responseBuilder, User user) {
        try {
            ResponseTournamentCommand response = tournamentCommandService.getResponseTournamentCommand(cmd, user,
                    responseBuilder);
            responseBuilder.setTournamentCommand(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        regainEnergy(responseBuilder, user);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRegistJaguarCommand cmd, Builder responseBuilder, User user) {
        ResponseRegistJaguarCommand response;
        try {
            response = registJaguarCommandService.getResponseRegistJaguarCommand(cmd, user, responseBuilder);
            responseBuilder.setRegistJaguar(response);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentRewardDetailCommand cmd, Builder responseBuilder, User user) {
        try {
            ResponseTournamentRewardDetailCommand response = tournamentRewardDetailCommandService
                    .getResponseTournamentRewardDetailCommand(cmd, user);
            responseBuilder.setTournamentRewardDetailCommand(response);
            // 鍒ゆ柇鏄惁鏄畬鎴愮殑鎵嶄笅鍙�
            responseTournamentRewardCommand tournamentRewardResponse = tournamentRewardDetailCommandService
                    .getResponseTournamentRewardCommand(cmd, user, responseBuilder);
            if (tournamentRewardResponse != null) {
                responseBuilder.setRewardCommand(tournamentRewardResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        regainEnergy(responseBuilder, user);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentDetailCommand cmd, Builder responseBuilder, User user) {
        ResponseTournamentDetailCommand response = null;
        try {
            response = tournamentDetailCommandService.getResponseTournamentDetailCommand(cmd, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ResponseTournamentRaceStartCommand tournamentResponse = tournamentRaceStartService
                .getResponseTournamentRaceStartCommand(cmd, user);
        responseBuilder.setTournamentRaceStart(tournamentResponse);
        responseBuilder.setTournamentDetailCommand(response);
        regainEnergy(responseBuilder, user);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestModeInfoCommand cmd, Builder responseBuilder, User user) {
        ResponseRacerForGhostCommand response = ghostCommandService.getResponseGhostCommand(cmd, user);
        responseBuilder.setGhostCommand(response);
        if (cmd.getGameMode() == Match.CAREER_MODE) {
            ResponseModeInfoCommand modeInfoResponse = modeInfoCommandService.getResponseModeInfoCommand(cmd, user);
            responseBuilder.setModeInfoCommand(modeInfoResponse);
        }
        regainEnergy(responseBuilder, user);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestResourceCommand cmd, Builder responseBuilder, User user) {
        int gameEdition = responseBuilder.getHead().getVersion();
        ResponseResourceCommand response = resourceCommandService.getResponseResourceoCommand(cmd, user, gameEdition);
        responseBuilder.setResourceCommand(response);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentSignUpCommand cmd, Builder responseBuilder, User user) {
        ResponseTournamentSignUpCommand response = null;
        try {
            response = tournamentSignUpCommandService.getResponseTournamentSignUpCommand(cmd, user, responseBuilder);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        responseBuilder.setTournamentSignUpCommand(response);
        regainEnergy(responseBuilder, user);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRaceStartCommand cmd, Builder responseBuilder, User user) {
        ResponseRaceStartCommand response = null;
        try {
            response = raceStartService.getResponseRaceStartCommand(cmd, user, responseBuilder);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        responseBuilder.setRaceStartCommand(response);
        if (cmd.getGameMode() == Match.TOURNAMENT_MODE) {
            ResponseTournamentRaceStartCommand tournamentResponse = tournamentRaceStartService
                    .getResponseTournamentRaceStartCommand(cmd, user);
            responseBuilder.setTournamentRaceStart(tournamentResponse);
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBindingStartCommand cmd, Builder responseBuilder, User user) {
        ResponseBindingStartCommand repsonse = bindingCommandService.getBindingStartCommand(cmd, user, responseBuilder);
        responseBuilder.setBindingStartCommand(repsonse);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBindingResultCommand cmd, Builder responseBuilder, User user) {
        ResponseBindingResultCommand response = bindingCommandService.getBindingResultCommand(cmd, responseBuilder);
        responseBuilder.setBindingResultCommand(response);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBindingConfirmCommand cmd, Builder responseBuilder, User user) {
        ResponseBindingConfirmCommand response = bindingCommandService.getBindingConfirmCommand(cmd);
        responseBuilder.setBindingConfirmCommand(response);
        regainEnergy(responseBuilder, user);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBindingTokenCommand cmd, Builder responseBuilder, User user) {
        ResponseBindingTokenCommand repsonse = bindingCommandService.getBindingTokenCommand(cmd, responseBuilder);
        responseBuilder.setBindingTokenCommand(repsonse);
        return true;
    }
    
    @Override
    protected boolean handleCommand(RequestBindingInfoCommand cmd, Builder responseBuilder, User user) {
        ResponseBindingInfoCommand response = bindingCommandService.getBindingInfoCommand(cmd, responseBuilder);
        responseBuilder.setBindingInfoCommand(response);
        return true;
    }
    
    @Override
    protected boolean handleCommand(RequestGarageCommand cmd, Builder responseBuilder, User user) {
        ResponseGarageCommand response;
        try {
            response = garageCommandService.getGarageCommand(user, cmd);
            responseBuilder.setGarageCommand(response);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        regainEnergy(responseBuilder, user);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBuyCarCommand cmd, Builder responseBuilder, User user) {
        ResponseBuyCarCommand response;
        try {
            response = garageCommandService.getBuyCarCommand(user, cmd, responseBuilder);
            responseBuilder.setBuyCarCommand(response);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        regainEnergy(responseBuilder, user);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestUpgradeSlotCommand cmd, Builder responseBuilder, User user) {

        ResponseUpgradeSlotCommand response;
        try {
            response = garageCommandService.getUpgradeSlotCommand(user, cmd, responseBuilder);
            responseBuilder.setUpgradeSlotCommand(response);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        regainEnergy(responseBuilder, user);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestUseChartletCommand cmd, Builder responseBuilder, User user) {
        ResponseUseChartletCommand response;
        try {
            response = garageCommandService.getUseChartletCommand(user, cmd, responseBuilder);
            responseBuilder.setUseChartletCommand(response);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        regainEnergy(responseBuilder, user);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestBuyItemCommand cmd, Builder responseBuilder, User user) {
        ResponseBuyItemCommand response = storeDetailCommandService.getResponseBuyItemCommand(cmd, user,
                responseBuilder);
        responseBuilder.setBuyItemCommand(response);
        regainEnergy(responseBuilder, user);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestStoreDetailCommand cmd, Builder responseBuilder, User user) {
        ResponseStoreDetailCommand response = storeDetailCommandService.getResponseStoreDetailCommand(cmd, user);
        responseBuilder.setStoreDetailCommand(response);
        regainEnergy(responseBuilder, user);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestGetRewardCommand cmd, Builder responseBuilder, User user) {
        return true;
    }

    @Override
    protected boolean handleCommand(RequestModifyUserInfoCommand cmd, Builder responseBuilder, User user) {
        ResponseModifyUserInfoCommand response = modifyUserInfoCommandService.getResponseModifyUserInfoCommand(cmd,
                user);
        responseBuilder.setModifyUserInfoCommand(response);
        regainEnergy(responseBuilder, user);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestIapCheckCommand cmd, Builder responseBuilder, User user) {
        ResponseIapCheckCommand response = iapCheckCommandService
                .getResponseIapCheckCommand(cmd, user, responseBuilder);
        responseBuilder.setIapCheckCommand(response);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRpLeaderboardCommand cmd, Builder responseBuilder, User user) {
        ResponseRpLeaderboardCommand response = rpLeaderboardCommandService.getResponseRpLeaderboardCommand(cmd, user,
                responseBuilder);
        responseBuilder.setRpLeaderboardCommand(response);
        regainEnergy(responseBuilder, user);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestProfileLikeCommand cmd, Builder responseBuilder, User user) {
        try {
            ResponseProfileLikeCommand response = profileLikeCommandService.getProfileLikeCommand(cmd, user,
                    responseBuilder);
            responseBuilder.setProfileLikeCommand(response);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestProfileReportCommand cmd, Builder responseBuilder, User user) {
        try {
            ResponseProfileReportCommand response = profileReportCommandService.getProfileReportCommand(cmd, user,
                    responseBuilder);
            responseBuilder.setProfileReportCommand(response);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestProfileVSCommand cmd, Builder responseBuilder, User user) {
        try {

            ResponseProfileVSCommand response = profileVSCommandService.getProfileVSCommand(cmd, user, responseBuilder);

            responseBuilder.setProfileVSCommand(response);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestRecordUserRaceActionCommand cmd, Builder responseBuilder, User user) {
        try {
            recordUserRaceActionCommandService.record(cmd, user);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestProfileNextCarCommand cmd, Builder responseBuilder, User user) {
        try {

            ResponseProfileNextCarCommand response = profileNextCarCommandService.getProfileNextCarCommand(cmd, user,
                    responseBuilder);
            if (response != null) {
                responseBuilder.setProfileNextCarCommand(response);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected boolean handleCommand(RequestProfileUserDataCommand cmd, Builder responseBuilder, User user) {

        try {

            ResponseProfileUserDataCommand response = profileUserDataCommandService.getProfileUserDataCommand(cmd,
                    user, responseBuilder);

            if (response != null) {
                responseBuilder.setProfileUserDataCommand(response);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

    @Override
    protected boolean handleCommand(RequestTutorialRewardCommand cmd, Builder responseBuilder, User user) {
        ResponseTutorialRewardCommand response = tutorialRewardCommandService.getResponseTutorialRewardCommand(cmd,
                user, responseBuilder);
        responseBuilder.setTutorialRewardCommand(response);
        regainEnergy(responseBuilder, user);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestGhostRecordCommand cmd, Builder responseBuilder, User user) {
        ghostRecordCommandService.getResponseTutorialRewardCommand(cmd, user.getId(), responseBuilder);
        regainEnergy(responseBuilder, user);
        return true;
    }

    @Override
    protected boolean handleCommand(RequestChallengeMathInfoCommand cmd, Builder responseBuilder, User user) {
        chanllengeMatchInfoCommandService.setChanllengeMatchInfoCommand(cmd, user, responseBuilder);
        regainEnergy(responseBuilder, user);
        return true;

    }

    @Override
    protected boolean handleCommand(RequestCommand cmd, Builder responseBuilder, User user) {
        return true;
    }

//    private void regainEnergy(Builder responseBuilder, User user) {
//        Date regainTime = new Date(user.getLastRegainEnergyDate() * 1000L - Match.ENERGY_REGAIN_HOUR_SECONDS * 1000L);
//        Date currentTime = new Date(System.currentTimeMillis() - Match.ENERGY_REGAIN_HOUR_SECONDS * 1000L);
//        int days = Math.abs(DateUtil.intervalDays(currentTime, regainTime));
//        if (days >= 1 && user.getEnergy() < Match.ENERGY_MAX) {
//            user = userService.regainEnergy(user);
//            pushService.pushUserInfoCommand(responseBuilder, user);
//        }
//    }
    
    private void regainEnergy(Builder responseBuilder, User user) {
    	if (user.getEnergy() < Match.ENERGY_MAX) {
    		user = userService.regainEnergy(user);
//            pushService.pushUserInfoCommand(responseBuilder, user);
    	}
    }

    @Override
    protected boolean handleCommand(RequestSystemCommand cmd, Builder responseBuilder, User user) {
        try {
            configService.config(cmd, responseBuilder, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    protected boolean handleCommand(RequestGotchaCommand cmd, Builder responseBuilder, User user) {
        ResponseGotchaCommand gotcmd = gotchaCommandService.getResponseCommand(cmd, user, responseBuilder);
        responseBuilder.setGotchaCommand(gotcmd);

        return true;
    }

    @Override
    protected boolean handleCommand(RequestTournamentNum cmd, Builder responseBuilder, User user) {
        try {
            pushService.pushTournamentRewardNum(responseBuilder, user);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

	@Override
	protected boolean handleCommand(RequestEnergyTimeCommand cmd,
			Builder responseBuilder, User user) {
		ResponseEnergyTimeCommand etcmd = energyCommandService.getEnergyTimeCommand(user);
        responseBuilder.setEnergyTimeCommand(etcmd);

        return true;
	}

	@Override
	protected boolean handleCommand(RequestCollectEnergyCommand cmd,
			Builder responseBuilder, User user) {
		ResponseModifyUserInfoCommand modifycmd = energyCommandService.getModifyUserInfoCommand(responseBuilder, user);
        responseBuilder.setModifyUserInfoCommand(modifycmd);

        return true;
	}

//	@Override
//	protected boolean handleCommand(RequestSendCar cmd,
//			Builder responseBuilder, User user) {
//		//ResponseBuyCarCommand response;
//		ResponseSendCar response;
//        try {
//            response = garageCommandService.getSendCarCommand(user, cmd, responseBuilder);
//            responseBuilder.setResponseSendCar(response);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return true;
//	}

}
