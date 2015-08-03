package com.ea.eamobile.nfsmw.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.CareerBestRacetimeRecord;
import com.ea.eamobile.nfsmw.model.CareerStandardResult;
import com.ea.eamobile.nfsmw.model.Leaderboard;
import com.ea.eamobile.nfsmw.model.LeaderboardChangeRecord;
import com.ea.eamobile.nfsmw.model.RaceMode;
import com.ea.eamobile.nfsmw.model.Track;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.service.CareerBestRacetimeRecordService;
import com.ea.eamobile.nfsmw.service.CareerStandardResultService;
import com.ea.eamobile.nfsmw.service.LeaderboardChangeRecordService;
import com.ea.eamobile.nfsmw.service.LeaderboardService;
import com.ea.eamobile.nfsmw.service.RaceModeService;
import com.ea.eamobile.nfsmw.service.RpLevelService;
import com.ea.eamobile.nfsmw.service.TournamentLeaderboardService;
import com.ea.eamobile.nfsmw.service.TournamentUserService;
import com.ea.eamobile.nfsmw.service.TrackService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.service.UserTrackService;
import com.ea.eamobile.nfsmw.view.LeaderboardInfoVIew;

@Controller
@RequestMapping("/nfsmw/admin")
public class LeaderboardAction {

    @Autowired
    UserService userService;
    @Autowired
    RpLevelService rplevelService;
    @Autowired
    UserTrackService userTrackService;
    @Autowired
    TrackService trackService;
    @Autowired
    RaceModeService raceModeService;
    @Autowired
    LeaderboardService leaderboardService;
    @Autowired
    LeaderboardChangeRecordService leaderboardChangeRecordService;
    @Autowired
    CareerStandardResultService careerStandardResultService;
    @Autowired
    CareerBestRacetimeRecordService careerBestRacetimeRecordService;
    @Autowired
    TournamentLeaderboardService tournamentLeaderboardService;
    @Autowired
    TournamentUserService tournamentUserService;

    @RequestMapping("leaderboardhome")
    public String home(Model model, HttpServletResponse response,
            @RequestParam(value = "modeId", required = false, defaultValue = "100111") int modeId) {
        // setModelByTier(1, model, 0);
        List<RaceMode> modeList = new ArrayList<RaceMode>();
        RaceMode raceMode = raceModeService.getModeById(modeId);
        for (int i = 1; i < 5; i++) {
            List<Track> trackList = trackService.queryByTierAndStarNum(i, 1);
            for (Track track : trackList) {
                List<RaceMode> tempList = raceModeService.getTrackModes(track.getId());
                modeList.addAll(tempList);
            }
        }
        model.addAttribute("modeList", modeList);
        model.addAttribute("selectedModeId", modeId);
        setLeaderboardInfo(raceMode, model);
        return "leaderboard/leaderboardHome";
    }

    @RequestMapping("refreshleaderboard")
    public String refreshLeaderboard(Model model, HttpServletResponse response,
            @RequestParam(value = "modeId", required = false, defaultValue = "100111") int modeId) {

        // setModelByTier(1, model, 0);
        RaceMode raceMode = raceModeService.getModeById(modeId);
        leaderboardService.deleteByModeType(modeId);
        List<CareerBestRacetimeRecord> careerBestRacetimeRecords = new ArrayList<CareerBestRacetimeRecord>();
        if (raceMode.getRankType() == Match.MODE_RANK_TYPE_BY_TIME) {
            careerBestRacetimeRecords = careerBestRacetimeRecordService.getTopTenByRaceTime(modeId);
        }
        if (raceMode.getRankType() == Match.MODE_RANK_TYPE_BY_AVGSPEED) {
            careerBestRacetimeRecords = careerBestRacetimeRecordService.getTopTenByAverageSpeed(modeId);
        }
        for (CareerBestRacetimeRecord careerBestRacetimeRecord : careerBestRacetimeRecords) {
            Leaderboard leaderboard = new Leaderboard();
            User user = userService.getUser(careerBestRacetimeRecord.getUserId());
            if (user == null) {
                careerBestRacetimeRecordService.deleteByUserId(careerBestRacetimeRecord.getUserId());
                continue;
            }
            leaderboard.setHeadIndex(user.getHeadIndex());
            leaderboard.setHeadUrl(user.getHeadUrl());
            leaderboard.setModeType(modeId);
            if (raceMode.getRankType() == Match.MODE_RANK_TYPE_BY_TIME) {
                leaderboard.setResult(careerBestRacetimeRecord.getRaceTime());
            }
            if (raceMode.getRankType() == Match.MODE_RANK_TYPE_BY_AVGSPEED) {
                leaderboard.setResult(careerBestRacetimeRecord.getAverageSpeed());
            }
            leaderboard.setUserId(user.getId());
            leaderboard.setUserName(user.getName());
            leaderboardService.insertLeaderboard(leaderboard);
        }
        List<RaceMode> modeList = new ArrayList<RaceMode>();

        for (int i = 1; i < 5; i++) {
            List<Track> trackList = trackService.queryByTierAndStarNum(i, 1);
            for (Track track : trackList) {
                List<RaceMode> tempList = raceModeService.getTrackModes(track.getId());
                modeList.addAll(tempList);
            }
        }
        model.addAttribute("modeList", modeList);
        model.addAttribute("selectedModeId", modeId);
        setLeaderboardInfo(raceMode, model);
        return "leaderboard/leaderboardHome";
    }

    private void setLeaderboardInfo(RaceMode mode, Model model) {
        List<LeaderboardInfoVIew> leaderboardInfoVIews = new ArrayList<LeaderboardInfoVIew>();
        List<Leaderboard> leaderboards = leaderboardService.getLeaderboardByMode(mode);

        for (Leaderboard leaderboard : leaderboards) {
            LeaderboardInfoVIew leaderboardInfoVIew = new LeaderboardInfoVIew();
            LeaderboardChangeRecord leaderboardChangeRecord = new LeaderboardChangeRecord();
            User user = userService.getUser(leaderboard.getUserId());
            CareerStandardResult careerStandardResult = careerStandardResultService.getCareerStandardResult(mode
                    .getModeType());
            if (mode.getRankType() == Match.MODE_RANK_TYPE_BY_AVGSPEED) {
                leaderboardInfoVIew.setAverageSpeed(leaderboard.getResult() * 2.237f);
                leaderboardChangeRecord = leaderboardChangeRecordService
                        .getLeaderboardChangeRecordByUserIdAndAverageSpeed(leaderboard.getUserId(),
                                leaderboard.getResult());
            }
            if (mode.getRankType() == Match.MODE_RANK_TYPE_BY_TIME) {
                leaderboardInfoVIew.setRaceTime(leaderboard.getResult());
                leaderboardChangeRecord = leaderboardChangeRecordService.getLeaderboardChangeRecordByUserIdAndRaceTime(
                        leaderboard.getUserId(), leaderboard.getResult());
            }
            if (leaderboardChangeRecord != null) {
                leaderboardInfoVIew.setFirstConsumble(leaderboardChangeRecord.getFirstConsumbleId());
                leaderboardInfoVIew.setSecondConsumble(leaderboardChangeRecord.getSecondConsumbleId());
                leaderboardInfoVIew.setThirdConsumble(leaderboardChangeRecord.getThirdConsumbleId());
                leaderboardInfoVIew.setCarId(leaderboardChangeRecord.getCarId());
            }
            leaderboardInfoVIew.setRegularAverageSpeed(careerStandardResult.getAverageSpeed());
            leaderboardInfoVIew.setStandardRaceTime(careerStandardResult.getRaceTime());
            leaderboardInfoVIew.setUserId(leaderboard.getUserId());
            leaderboardInfoVIew.setUserName(leaderboard.getUserName());
            leaderboardInfoVIew.setIsBan(user.getAccountStatus() & Const.IS_BAN);
            leaderboardInfoVIew.setIsRecord(user.getAccountStatus() & Const.IS_NORECORD >> 1);
            leaderboardInfoVIew.setIsNoGhost(user.getAccountStatus() & Const.IS_NOGHOST >> 2);
            leaderboardInfoVIews.add(leaderboardInfoVIew);
        }
        model.addAttribute("leaderboardInfoList", leaderboardInfoVIews);
    }

    private void setModelByTier(int tier, Model model, int modeId) {
        int selectModeId = modeId;

        List<Track> trackList = trackService.queryByTierAndStarNum(tier, 1);
        Track selectTrack = trackService.queryTrack(tier);
        List<RaceMode> modeList = new ArrayList<RaceMode>();
        for (Track track : trackList) {
            List<RaceMode> tempList = raceModeService.getTrackModes(track.getId());
            modeList.addAll(tempList);
        }
        if (selectModeId == 0) {
            selectModeId = selectTrack.getFirstMode();
        }
        model.addAttribute("selectedModeId", selectModeId);
        model.addAttribute("selectedTierId", tier);
        model.addAttribute("modeList", modeList);
    }

    @RequestMapping("selectTier")
    public String selectTier(Model model, HttpServletResponse response,
            @RequestParam(value = "tier", required = false, defaultValue = "1") int tier) {
        setModelByTier(tier, model, 0);
        return "leaderboard/leaderboardHome";
    }

    @RequestMapping("selectMode")
    public String selectMode(Model model, HttpServletResponse response,
            @RequestParam(value = "tier", required = false, defaultValue = "100111") int modeId) {
        RaceMode raceMode = raceModeService.getModeById(modeId);
        setModelByTier(Integer.parseInt(raceMode.getTrackId()), model, modeId);
        return "leaderboard/leaderboardHome";
    }

    @RequestMapping("leaderboard/careerhome")
    public String careerHome(Model model, HttpServletResponse response) {
        return "leaderboard/careerhome";
    }

    @RequestMapping("leaderboard/searchByUserId")
    public String careerSearchByUserId(
            @RequestParam(value = "userid", required = false, defaultValue = "") long userId, Model model,
            HttpServletResponse response) {

        return "leaderboard/userleaderboard";
    }

    @RequestMapping("changeAccountStatus")
    public String changeUserStatus(@RequestParam(value = "userId", required = false, defaultValue = "") long userId,
            @RequestParam(value = "ban", required = false, defaultValue = "0") int ban,
            @RequestParam(value = "norecord", required = false, defaultValue = "0") int norecord,
            @RequestParam(value = "noghost", required = false, defaultValue = "0") int noghost,
            @RequestParam(value = "ghostRecord", required = false, defaultValue = "0") int ghostRecord,
            @RequestParam(value = "showMod", required = false, defaultValue = "0") int showMod, Model model,
            HttpServletResponse response) {
        User user = userService.getUserForAdmin(userId);
        int status = 0;
        status = 1 * ban + norecord * 2 + noghost * 4 + ghostRecord * 8 + showMod * 16;
        user.setAccountStatus(status);
        userService.updateUser(user);
        if (norecord == 1) {
            leaderboardService.deleteByUserId(userId);
            careerBestRacetimeRecordService.deleteByUserId(userId);
            tournamentLeaderboardService.deleteByUserId(userId);
            tournamentUserService.deleteByUserId(userId);
        }
        model.addAttribute("user", user);
        model.addAttribute("userId", userId);
        model.addAttribute("isban", ban);
        model.addAttribute("isNoRecord", norecord);
        model.addAttribute("isNoGhost", noghost);
        model.addAttribute("ghostRecord", ghostRecord);
        model.addAttribute("message", "Successful!");
        return "leaderboard/userChangeStatus";
    }

    @RequestMapping("updateStatus")
    public String updateStatus(@RequestParam(value = "userId", required = false, defaultValue = "") long userId,
            Model model, HttpServletResponse response) {
        User user = userService.getUser(userId);
        int ban = user.getAccountStatus() & Const.IS_BAN;
        int norecord = (user.getAccountStatus() & Const.IS_NORECORD) >> 1;
        int noghost = (user.getAccountStatus() & Const.IS_NOGHOST) >> 2;
        int ghostRecord = (user.getAccountStatus() & Const.IS_GHOSTRECORD) >> 3;
        int showMod = (user.getAccountStatus() & Const.IS_SHOWMOD) >> 4;
        model.addAttribute("user", user);
        model.addAttribute("userId", userId);
        model.addAttribute("isban", ban);
        model.addAttribute("isNoRecord", norecord);
        model.addAttribute("isNoGhost", noghost);
        model.addAttribute("ghostRecord", ghostRecord);
        model.addAttribute("showMod", showMod);
        model.addAttribute("message", "Successful!");
        return "leaderboard/userChangeStatus";
    }

}
