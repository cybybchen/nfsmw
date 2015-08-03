package com.ea.eamobile.nfsmw.service.ghost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.model.CareerGhost;
import com.ea.eamobile.nfsmw.model.ModeModifier;
import com.ea.eamobile.nfsmw.model.RaceMode;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.service.CareerGhostService;
import com.ea.eamobile.nfsmw.service.ModeModifierService;
import com.ea.eamobile.nfsmw.view.BaseGhost;

@Service
public class UserGhostPoolService {
    @Autowired
    private CareerGhostService careerGhostService;
    @Autowired
    private ModeModifierService modeModifierService;
    private static final int[] modeArray = { 100111, 100121, 100131, 100221, 100211, 100231, 100331, 100321, 100311 };

    private void specialHandle(int modeId, User user, List<? extends BaseGhost> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        if (user.getId() > 430000 && user.getId() < 433000) {
            if (Arrays.binarySearch(modeArray, modeId) > -1) {
                for (BaseGhost ghost : list) {
                    ghost.setRaceTime(ghost.getRaceTime() * 10f);
                    ghost.setAverageSpeed(ghost.getAverageSpeed() / 10f);
                }
            }
        }
    }

    public List<? extends BaseGhost> getGhosts(User user, RaceMode raceMode, int num) {
        List<? extends BaseGhost> result = null;
        ModeModifier modeModifier = modeModifierService.getModeModifier(raceMode.getId());
        float raceTime = modeModifier.getStandardTime();
        CareerGhost careerGhost = careerGhostService.getCareerGhostByUserIdAndModeType(user.getId(),
                raceMode.getModeType());
        if (careerGhost != null) {
            raceTime = careerGhost.getRaceTime();
        }
        if (num == 5) {
            result = getOneVsFiveCareerGhost(raceTime, user.getId(), modeModifier, raceMode.getModeType());
        } else if (num == 1) {
            result = getOneVsOneCareerGhost(raceTime, user.getId(), modeModifier, raceMode.getModeType());
        }
        specialHandle(raceMode.getId(), user, result);
        return result;
    }

    private List<CareerGhost> getOneVsOneCareerGhost(float raceTime, long userId, ModeModifier modeModifier,
            int modeType) {
        List<CareerGhost> result = new ArrayList<CareerGhost>();
        CareerGhost careerGhost = careerGhostService.getCareerGhostFromTimeRange(Const.MAX_RACETIME, raceTime
                + modeModifier.getModifier1v1(), modeType, Const.CHECK_IS_MOST_RACETIME);
        if (careerGhost != null) {
            result.add(careerGhost);
        }
        return result;
    }

    private List<CareerGhost> getOneVsFiveCareerGhost(float raceTime, long userId, ModeModifier modeModifier,
            int modeType) {
        List<CareerGhost> result = new ArrayList<CareerGhost>();
        CareerGhost firstCareerGhost = careerGhostService.getCareerGhostFromTimeRange(
                raceTime + modeModifier.getModifier2(), raceTime + modeModifier.getModifier1(), modeType,
                Const.CHECK_IS_LEAST_RACETIME);
        if (firstCareerGhost != null) {
            result.add(firstCareerGhost);
        }
        CareerGhost sencondCareerGhost = careerGhostService.getCareerGhostFromTimeRange(
                raceTime + modeModifier.getModifier3(), raceTime + modeModifier.getModifier2(), modeType,
                Const.CHECK_IS_MOST_RACETIME);
        if (sencondCareerGhost != null) {
            result.add(sencondCareerGhost);
        }
        CareerGhost thirdCareerGhost = careerGhostService.getCareerGhostFromTimeRange(
                raceTime + modeModifier.getModifier4(), raceTime + modeModifier.getModifier3(), modeType,
                Const.CHECK_IS_MOST_RACETIME);
        if (thirdCareerGhost != null) {
            result.add(thirdCareerGhost);
        }
        CareerGhost forthCareerGhost = careerGhostService.getCareerGhostFromTimeRange(
                raceTime + modeModifier.getModifier5(), raceTime + modeModifier.getModifier4(), modeType,
                Const.CHECK_IS_MOST_RACETIME);
        if (forthCareerGhost != null) {
            result.add(forthCareerGhost);
        }
        CareerGhost fifthCareerGhost = careerGhostService.getCareerGhostFromTimeRange(raceTime
                + modeModifier.getModifier5()+0.1f, raceTime
                + modeModifier.getModifier5(), modeType, Const.CHECK_IS_MOST_RACETIME);
        if (fifthCareerGhost != null) {
            result.add(fifthCareerGhost);
        }
        return result;
    }
}
