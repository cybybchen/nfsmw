package com.ea.eamobile.nfsmw.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.CarMaxSpeed;
import com.ea.eamobile.nfsmw.model.ModeDistance;
import com.ea.eamobile.nfsmw.model.RaceMode;
import com.ea.eamobile.nfsmw.model.TierMode;
import com.ea.eamobile.nfsmw.model.TournamentUser;

@Service
public class RaceResultFilterService {

    @Autowired
    private ModeDistanceService modeDistanceService;
    @Autowired
    private CarMaxSpeedService carMaxSpeedService;
    @Autowired
    private RaceModeService raceModeService;
    @Autowired
    private TrackCarTypeService trackCarTypeService;
    @Autowired
    private TournamentUserService tournamentUserService;
    @Autowired
    private TournamentCarLimitService tournamentCarLimitService;
    @Autowired
    private TierCarLimitService tierCarLimitService;
    @Autowired
    private TierModeService tierModeService;

    private Map<Integer, Map<String, Float>> MODE_CAR_RESULT_MAP = null;

    private void initModeCarMap() {
        MODE_CAR_RESULT_MAP = new ConcurrentHashMap<Integer, Map<String, Float>>();
        List<ModeDistance> modeDistanceList = modeDistanceService.getModeDistanceList();
        List<CarMaxSpeed> carMaxSpeedList = carMaxSpeedService.getCarMaxSpeedList();
        for (ModeDistance modeDistance : modeDistanceList) {
            Map<String, Float> tempCarTimeMap = new ConcurrentHashMap<String, Float>();
            for (CarMaxSpeed carMaxSpeed : carMaxSpeedList) {
                float minRaceTime = modeDistance.getDistance() * Const.HOUR_SECONDS / carMaxSpeed.getMaxSpeed();
                tempCarTimeMap.put(carMaxSpeed.getCarId(), minRaceTime);
            }
            MODE_CAR_RESULT_MAP.put(modeDistance.getModeId(), tempCarTimeMap);
        }
    }

    public boolean isCheatCarId(int raceId, String carId, int gameMode, long userId) throws SQLException {
        if (gameMode == Match.CAREER_MODE) {
            RaceMode raceMode = raceModeService.getModeById(raceId);
            if (raceMode == null) {
                return true;
            }
            String trackId = raceMode.getTrackId();
            List<String> carIds = trackCarTypeService.getCarTypesByTrack(trackId);
            if (carIds == null || carIds.size() == 0) {
                return true;
            }
            if (!carIds.contains(carId)) {
                return true;
            }
        }
        if (gameMode == Match.GOLD_MODE) {
            RaceMode raceMode = raceModeService.getModeById(raceId);
            if (raceMode == null) {
                return true;
            }
            List<String> carIds = tierCarLimitService.getTierCarLimitListByTierId(1);
            carIds.addAll(tierCarLimitService.getTierCarLimitListByTierId(2));
            if (carIds == null || carIds.size() == 0) {
                return true;
            }
            if (!carIds.contains(carId)) {
                return true;
            }
        }
        if (gameMode == Match.TOURNAMENT_MODE) {
            TournamentUser tournamentUser = tournamentUserService.getTournamentUserByUserIdAndTOnlineId(userId, raceId);
            if (tournamentUser == null) {
                return true;
            }
            List<String> carIds = tournamentCarLimitService.getTournamentCarLimit(tournamentUser.getGroupId());
            if (carIds == null || carIds.size() == 0) {
                return true;
            }
            if (!carIds.contains(carId)) {
                return true;
            }

        }

        if (gameMode == Match.TIER_MODE) {
            TierMode tierMode = tierModeService.getTierModeByModeId(raceId);
            if (tierMode == null) {
                return true;
            }
            List<String> carIds = tierCarLimitService.getTierCarLimitListByTierId(tierMode.getTier());
            if (carIds == null || carIds.size() == 0) {
                return true;
            }
            if (!carIds.contains(carId)) {
                return true;
            }

        }

        return false;

        // if(gameMode)

    }

    public boolean isCheatRaceTime(int modeId, String carId, float raceTime) {
        boolean result = false;
        if (MODE_CAR_RESULT_MAP == null) {
            initModeCarMap();
        }

        Map<String, Float> modeMap = MODE_CAR_RESULT_MAP.get(modeId);
        CarMaxSpeed carMaxSpeed = carMaxSpeedService.getCarMaxSpeed(carId);
        if (carMaxSpeed == null) {
            return raceTime < (Const.DEFAULT_MODE_DISTANCE * Const.HOUR_SECONDS / Const.DEFAULT_CAR_MAX_SPEED);
        }
        if (modeMap != null) {
            Float tempRaceTime = modeMap.get(carId);
            if (tempRaceTime == null) {
                return true;
            }
            return raceTime < modeMap.get(carId);
        }

//        result = raceTime < (Const.DEFAULT_MODE_DISTANCE * Const.HOUR_SECONDS / carMaxSpeed.getMaxSpeed());

        return result;

    }

    // public static final Map<Integer,Map<String,Float>> MODE_CAR_RESULT;
}
