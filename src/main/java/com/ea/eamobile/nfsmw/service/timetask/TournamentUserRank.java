package com.ea.eamobile.nfsmw.service.timetask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.model.Tournament;
import com.ea.eamobile.nfsmw.model.TournamentLeaderboard;
import com.ea.eamobile.nfsmw.model.TournamentOnline;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.service.TournamentGroupClassService;
import com.ea.eamobile.nfsmw.service.TournamentLeaderboardService;
import com.ea.eamobile.nfsmw.service.TournamentOnlineService;
import com.ea.eamobile.nfsmw.service.TournamentService;
import com.ea.eamobile.nfsmw.service.UserService;

@Service
public class TournamentUserRank {
    Timer timer;

    @Autowired
    private TournamentOnlineService tournamentOnlineService;
    @Autowired
    private TournamentService tournamentService;
    @Autowired
    private TournamentLeaderboardService tournamentLeaderboardService;
    @Autowired
    private UserService userService;
    @Autowired
    private TournamentGroupClassService tournamentGroupClassService;

    Logger log = LoggerFactory.getLogger(TournamentUserRank.class);

    private static long lastRunTime = System.currentTimeMillis();

    public void init(int minute) {
        timer = new Timer();
        timer.schedule(new TournamentUserRankTimeTask(), 0, minute * 1000l * 60);
    }

    class TournamentUserRankTimeTask extends TimerTask {
        public void run() {
            handleTournamentUserRank();
        }
    }

    private void handleTournamentUserRank() {
        long nowTime = System.currentTimeMillis();
        List<TournamentOnline> tournamentOnlines = tournamentOnlineService.getTournamentOnlineListByEndTime(
                (int) (lastRunTime / 1000), (int) (nowTime / 1000));
        if (tournamentOnlines != null && tournamentOnlines.size() > Const.CAN_CAL_TOURNAMENT_ONLINE_SIZE) {

            lastRunTime = nowTime;
            Map<Long, Integer> firstUserMap = new HashMap<Long, Integer>();
            for (TournamentOnline tournamentOnline : tournamentOnlines) {
                log.warn("tournamentOnlineList:" + tournamentOnline.getId());
                Tournament tournament = tournamentService.getTournament(tournamentOnline.getTournamentId());
                if (tournament == null) {
                    continue;
                }

                List<TournamentLeaderboard> tournamentLeaderboards = new ArrayList<TournamentLeaderboard>();
                List<Integer> classIdList = tournamentGroupClassService
                        .getTournamentGroupClassListByOnlineId(tournamentOnline.getId());
                if (classIdList == null || classIdList.size() == 0) {
                    continue;
                }
                for (Integer classId : classIdList) {
                    List<TournamentLeaderboard> leaderboards = tournamentLeaderboardService.getLeaderboard(
                            tournamentOnline.getId(), classId, tournament.getType());
                    if (leaderboards != null && leaderboards.size() > 0) {
                        tournamentLeaderboards.add(leaderboards.get(0));
                    }
                }
                for (TournamentLeaderboard tournamentLeaderboard : tournamentLeaderboards) {
                    if (firstUserMap.get(tournamentLeaderboard.getUserId()) == null) {
                        firstUserMap.put(tournamentLeaderboard.getUserId(), 1);
                    } else {
                        int num = firstUserMap.get(tournamentLeaderboard.getUserId());
                        num = num + 1;
                        firstUserMap.put(tournamentLeaderboard.getUserId(), num);
                    }
                }
            }

            handleUserMap(tournamentOnlines.size(), firstUserMap);
        }
    }

    private void handleUserMap(int size, Map<Long, Integer> firstUserMap) {
//        log.warn("userMap:" + firstUserMap);
        List<Long> userIdList = new ArrayList<Long>();
        for (Map.Entry<Long, Integer> entry : firstUserMap.entrySet()) {
            if (entry.getValue() == size) {
                userIdList.add(entry.getKey());
            }
        }

        for (Long userId : userIdList) {
            User user = userService.getUser(userId);
            if ((user.getAccountStatus() & Const.IS_GHOSTRECORD) == 0) {
                user.setAccountStatus(user.getAccountStatus() | Const.IS_GHOSTRECORD);
                userService.updateUser(user);
                log.warn("userIdListEveryOne:" + user.getId());
            }
        }
//        log.warn("userIdList:" + userIdList);
    }
}
