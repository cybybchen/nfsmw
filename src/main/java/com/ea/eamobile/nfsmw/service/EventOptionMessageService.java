package com.ea.eamobile.nfsmw.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.EventOptionOverride;
import com.ea.eamobile.nfsmw.protoc.Commands.AiSettingsMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.CashRewardMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.CountdownInitialTimeMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.EventOptionMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.MedalPositionMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.MedalScoreMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.MedalTimeMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.OpponentCollectionMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.OpponentMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.PenaltyTimeMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.RaceEventMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.RaceTrafficCongestionMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.TrafficCarSpawnDescsMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.TrafficFlowMessage;
import com.ea.eamobile.nfsmw.protoc.Commands.TrafficFlowMessage.Builder;

@Service
public class EventOptionMessageService {

    @Autowired
    EventOptionOverrideService eventOptionOverrideService;

    public List<EventOptionMessage> getEventOptionMessageList() {
        List<EventOptionMessage> result = new ArrayList<EventOptionMessage>();
        Map<Integer, List<EventOptionOverride>> map = eventOptionOverrideService.getEventOptionOverridesMap();
        for (Map.Entry<Integer, List<EventOptionOverride>> entry : map.entrySet()) {
            EventOptionMessage message = buildEventOptionMessage(entry.getKey(), entry.getValue());
            result.add(message);
        }
        return result;
    }

    private EventOptionMessage buildEventOptionMessage(int modeId, List<EventOptionOverride> list) {
        EventOptionMessage.Builder builder = EventOptionMessage.newBuilder();
        // required params
        builder.setModeId(modeId);
        builder.setFileName("");
        // option params
        if (list != null && list.size() > 0) {
            // build builder
            CashRewardMessage.Builder cashRewardBuilder = CashRewardMessage.newBuilder();
            MedalPositionMessage.Builder mpbuilder = MedalPositionMessage.newBuilder();
            MedalTimeMessage.Builder mtBuilder = MedalTimeMessage.newBuilder();
            MedalScoreMessage.Builder msBuilder = MedalScoreMessage.newBuilder();
            PenaltyTimeMessage.Builder ptbuilder = PenaltyTimeMessage.newBuilder();
            CountdownInitialTimeMessage.Builder citBuilder = CountdownInitialTimeMessage.newBuilder();
            RaceEventMessage.Builder rebuilder = RaceEventMessage.newBuilder();
            OpponentCollectionMessage.Builder ocbuilder = OpponentCollectionMessage.newBuilder();
            TrafficFlowMessage.Builder tfbuilder = TrafficFlowMessage.newBuilder();
            // fill data
            for (EventOptionOverride option : list) {
                if (StringUtils.isBlank(option.getName())) {
                    continue;
                }
                // 单层结构的无需提取方法处理
                if (isNameOption(option, "fileName")) {
                    builder.setFileName(option.getValue());
                } else if (isNameOption(option, "bronze")) {
                    cashRewardBuilder.setBronze(getValueInt(option));
                } else if (isNameOption(option, "silver")) {
                    cashRewardBuilder.setSilver(getValueInt(option));
                } else if (isNameOption(option, "gold")) {
                    cashRewardBuilder.setGold(getValueInt(option));
                } else if (isNameOption(option, "positionBronze")) {
                    mpbuilder.setPositionBronze(getValueInt(option));
                } else if (isNameOption(option, "positionSilver")) {
                    mpbuilder.setPositionSilver(getValueInt(option));
                } else if (isNameOption(option, "positionGold")) {
                    mpbuilder.setPositionGold(getValueInt(option));
                } else if (isNameOption(option, "timeBronze")) {
                    mtBuilder.setTimeBronze(getValueInt(option));
                } else if (isNameOption(option, "timeSilver")) {
                    mtBuilder.setTimeSilver(getValueInt(option));
                } else if (isNameOption(option, "timeGold")) {
                    mtBuilder.setTimeGold(getValueInt(option));
                } else if (isNameOption(option, "scoreBronze")) {
                    msBuilder.setScoreBronze(getValueInt(option));
                } else if (isNameOption(option, "scoreSilver")) {
                    msBuilder.setScoreSilver(getValueInt(option));
                } else if (isNameOption(option, "scoreGold")) {
                    msBuilder.setScoreGold(getValueInt(option));
                } else if (isNameOption(option, "maximumTime")) {
                    ptbuilder.setMaximumTime(getValueFloat(option));
                } else if (isNameOption(option, "initialTime")) {
                    citBuilder.setInitialTime(getValueFloat(option));
                } else if (isNameOption(option, "raceEvent")) { // raceEvent
                    List<EventOptionOverride> options = eventOptionOverrideService.getEventOptionOverrideMapByFather().get(option.getId());
                    fillRaceEvent(rebuilder, options);
                } else if (isNameOption(option, "opponentCollection")) { // OpponentCollectionMessage
                    List<EventOptionOverride> options = eventOptionOverrideService.getEventOptionOverrideMapByFather().get(option.getId());
                    fillOpponentCollection(ocbuilder, options);
                } else if (isNameOption(option, "trafficFlow")) { // trafficFlow
                    List<EventOptionOverride> options = eventOptionOverrideService.getEventOptionOverrideMapByFather().get(option.getId());
                    fillTrafficFlow(tfbuilder, options);
                }
                // set all builder to bean
                builder.setCashReward(cashRewardBuilder.build());
                builder.setMedalPosition(mpbuilder.build());
                builder.setMedalTime(mtBuilder.build());
                builder.setMedalScore(msBuilder.build());
                builder.setPenaltyTime(ptbuilder.build());
                builder.setInitialTime(citBuilder.build());
                builder.setRaceEvent(rebuilder.build());
                builder.setOpponentCollection(ocbuilder.build());
                builder.setTrafficFlow(tfbuilder.build());
            }
        }
        return builder.build();
    }

    private void fillOpponentCollection(
            com.ea.eamobile.nfsmw.protoc.Commands.OpponentCollectionMessage.Builder ocbuilder, List<EventOptionOverride> options) {
        List<OpponentMessage> oppList = Collections.emptyList();
        if (options != null) {
            oppList = new ArrayList<OpponentMessage>();

            for (EventOptionOverride opponent : options) {
                List<EventOptionOverride> details = eventOptionOverrideService.getEventOptionOverrideMapByFather().get(opponent.getId());
                OpponentMessage om = buildOpponentMessage(details);
                oppList.add(om);
            }
            ocbuilder.addAllOpponent(oppList);
        }
    }

    private void fillRaceEvent(com.ea.eamobile.nfsmw.protoc.Commands.RaceEventMessage.Builder rebuilder,
            List<EventOptionOverride> options) {
        if (options != null && options.size() > 0) {
            for (EventOptionOverride option : options) {
                if (isNameOption(option, "raceType")) { // race event builder
                    rebuilder.setRaceType(option.getValue());
                } else if (isNameOption(option, "raceFSMPrefabOverride")) {
                    rebuilder.setRaceFSMPrefabOverride(option.getValue());
                } else if (isNameOption(option, "carRestriction")) {
                    rebuilder.setCarRestriction(option.getValue());
                } else if (isNameOption(option, "trackName")) {
                    rebuilder.setTrackName(option.getValue());
                } else if (isNameOption(option, "name")) {
                    rebuilder.setName(option.getValue());
                } else if (isNameOption(option, "location")) {
                    rebuilder.setLocation(option.getValue());
                } else if (isNameOption(option, "blacklistEvent")) {
                    rebuilder.setBlacklistEvent(getValueInt(option) == 1);
                } else if (isNameOption(option, "classRestriction")) {
                    rebuilder.setClassRestriction(getValueInt(option));
                } else if (isNameOption(option, "environmentPrefab")) {
                    rebuilder.setEnvironmentPrefab(option.getValue());
                } else if (isNameOption(option, "trafficCarCount")) {
                    rebuilder.setTrafficCarCount(getValueInt(option));
                } else if (isNameOption(option, "openWorldTrack")) {
                    rebuilder.setOpenWorldTrack(getValueInt(option) == 1);
                } else if (isNameOption(option, "autoLogId")) {
                    rebuilder.setAutoLogId(getValueInt(option));
                } else if (isNameOption(option, "pursuitType")) {
                    rebuilder.setPursuitType(option.getValue());
                } else if (isNameOption(option, "startLineNoSpawnZone")) {
                    rebuilder.setStartLineNoSpawnZone(getValueFloat(option));
                } else if (isNameOption(option, "finishLineNoSpawnZone")) {
                    rebuilder.setFinishLineNoSpawnZone(getValueFloat(option));
                } else if (isNameOption(option, "spawnDistance")) {
                    rebuilder.setSpawnDistance(getValueFloat(option));
                } else if (isNameOption(option, "startLine")) {
                    rebuilder.setStartLine(option.getValue());
                } else if (isNameOption(option, "finishLine")) {
                    rebuilder.setFinishLine(option.getValue());
                } else if (isNameOption(option, "endOfTrack")) {
                    rebuilder.setEndOfTrack(option.getValue());
                } else if (isNameOption(option, "checkpointCollection")) {
                    rebuilder.setCheckpointCollection(option.getValue());
                }
            }
        }
    }

    private void fillTrafficFlow(Builder tfbuilder, List<EventOptionOverride> options) {
        List<RaceTrafficCongestionMessage> rtcList = new ArrayList<RaceTrafficCongestionMessage>();
        for (EventOptionOverride option : options) {
            if (isNameOption(option, "maxIncomingCarsOverride")) {
                tfbuilder.setMaxIncomingCarsOverride(getValueInt(option));
            } else if (isNameOption(option, "maxOutcomingCarsOverride")) {
                tfbuilder.setMaxOutcomingCarsOverride(getValueInt(option));
            } else if (isNameOption(option, "outgoingLightMinDistance")) {
                tfbuilder.setOutgoingLightMinDistance(getValueFloat(option));
            } else if (isNameOption(option, "outgoingLightMaxDistance")) {
                tfbuilder.setOutgoingLightMaxDistance(getValueFloat(option));
            } else if (isNameOption(option, "outgoingHeavyMinDistance")) {
                tfbuilder.setOutgoingHeavyMinDistance(getValueFloat(option));
            } else if (isNameOption(option, "outgoingHeavyMaxDistance")) {
                tfbuilder.setOutgoingHeavyMaxDistance(getValueFloat(option));
            } else if (isNameOption(option, "incomingLightMinDistance")) {
                tfbuilder.setIncomingLightMinDistance(getValueFloat(option));
            } else if (isNameOption(option, "incomingLightMaxDistance")) {
                tfbuilder.setIncomingLightMaxDistance(getValueFloat(option));
            } else if (isNameOption(option, "incomingHeavyMinDistance")) {
                tfbuilder.setIncomingHeavyMinDistance(getValueFloat(option));
            } else if (isNameOption(option, "incomingHeavyMaxDistance")) {
                tfbuilder.setIncomingHeavyMaxDistance(getValueFloat(option));
            } else if (isNameOption(option, "raceTrafficCongestion")) { // raceTrafficCongestion list
                List<EventOptionOverride> congestions = eventOptionOverrideService.getEventOptionOverrideMapByFather().get(option.getId());
                RaceTrafficCongestionMessage rtc = buildRaceTrafficCongestionMessage(congestions);
                rtcList.add(rtc);

            } else if (isNameOption(option, "trfficCarSpawnDescs")) { // trfficCarSpawnDescs
                List<EventOptionOverride> descs = eventOptionOverrideService.getEventOptionOverrideMapByFather().get(option.getId());
                TrafficCarSpawnDescsMessage trafficCarSpawnDescsMessage = buildTrafficCarSpawnDescsMessage(descs);
                tfbuilder.setTrfficCarSpawnDescs(trafficCarSpawnDescsMessage);
            }
        }
        tfbuilder.addAllRaceTrafficCongestion(rtcList);

    }

    private boolean isNameOption(EventOptionOverride option, String name) {
        return name.equals(option.getName());
    }

    private OpponentMessage buildOpponentMessage(List<EventOptionOverride> list) {
        OpponentMessage.Builder ombuBuilder = OpponentMessage.newBuilder();
        for (EventOptionOverride option : list) {
            if (isNameOption(option, "driverName")) {
                ombuBuilder.setDriverName(option.getValue());
            } else if (isNameOption(option, "carDescriptionName")) {
                ombuBuilder.setCarDescriptionName(option.getValue());
            } else if (isNameOption(option, "colourIndex")) {
                ombuBuilder.setColourIndex(getValueInt(option));
            } else if (isNameOption(option, "stationary")) {
                ombuBuilder.setStationary(getValueInt(option) == 1);
            } else if (isNameOption(option, "aiSettings")) {
                List<EventOptionOverride> aiSettings = eventOptionOverrideService.getEventOptionOverrideMapByFather().get(option.getId());
                AiSettingsMessage aim = buildAiSettingsMessage(aiSettings);
                ombuBuilder.setAiSettings(aim);
            }
        }
        return ombuBuilder.build();
    }

    private AiSettingsMessage buildAiSettingsMessage(List<EventOptionOverride> aiSettings) {
        AiSettingsMessage.Builder aibuilder = AiSettingsMessage.newBuilder();
        for (EventOptionOverride option : aiSettings) {
            if (isNameOption(option, "speedFactor")) {
                aibuilder.setSpeedFactor(getValueFloat(option));
            } else if (isNameOption(option, "corneringFactor")) {
                aibuilder.setCorneringFactor(getValueFloat(option));
            } else if (isNameOption(option, "racingLineScale")) {
                aibuilder.setRacingLineScale(getValueFloat(option));
            } else if (isNameOption(option, "rubberBandingTweaksToUse")) {
                aibuilder.setRubberBandingTweaksToUse(getValueInt(option));
            } else if (isNameOption(option, "rubberBandingDifficulty")) {
                aibuilder.setRubberBandingDifficulty(getValueFloat(option));
            } else if (isNameOption(option, "rubberBandingTargetDifficulty")) {
                aibuilder.setRubberBandingTargetDifficulty(getValueFloat(option));
            } else if (isNameOption(option, "pathfindingSkill")) {
                aibuilder.setPathfindingSkill(getValueFloat(option));
            } else if (isNameOption(option, "nitroSkill")) {
                aibuilder.setNitroSkill(getValueFloat(option));
            } else if (isNameOption(option, "maxHealth")) {
                aibuilder.setMaxHealth(getValueFloat(option));
            }
        }
        return aibuilder.build();
    }

    private RaceTrafficCongestionMessage buildRaceTrafficCongestionMessage(List<EventOptionOverride> options) {
        RaceTrafficCongestionMessage.Builder rtcbuilder = RaceTrafficCongestionMessage.newBuilder();
        for (EventOptionOverride option : options) {
            if (isNameOption(option, "raceCompletetionFactor")) {
                rtcbuilder.setRaceCompletetionFactor(getValueFloat(option));
            } else if (isNameOption(option, "outgoingTrafficCongestionFactor")) {
                rtcbuilder.setOutgoingTrafficCongestionFactor(getValueFloat(option));
            } else if (isNameOption(option, "oncomingTrafficCongestionFactor")) {
                rtcbuilder.setOncomingTrafficCongestionFactor(getValueFloat(option));
            }
        }
        return rtcbuilder.build();
    }

    private TrafficCarSpawnDescsMessage buildTrafficCarSpawnDescsMessage(List<EventOptionOverride> options) {
        TrafficCarSpawnDescsMessage.Builder tcsdbuilder = TrafficCarSpawnDescsMessage.newBuilder();
        List<String> result = new ArrayList<String>();
        for (EventOptionOverride option : options) {
            if (isNameOption(option, "prefabld")) {
                result.add(option.getValue());
            }
        }
        tcsdbuilder.addAllPrefabld(result);
        return tcsdbuilder.build();
    }

    private int getValueInt(EventOptionOverride option) {
        if (option == null || StringUtils.isBlank(option.getValue())) {
            return 0;
        }
        return Integer.parseInt(option.getValue());
    }

    private float getValueFloat(EventOptionOverride option) {
        if (option == null || StringUtils.isBlank(option.getValue())) {
            return 0;
        }
        return Float.parseFloat(option.getValue());
    }
}
