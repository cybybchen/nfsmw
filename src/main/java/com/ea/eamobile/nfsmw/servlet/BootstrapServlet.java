package com.ea.eamobile.nfsmw.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ea.eamobile.nfsmw.service.CareerGhostService;
import com.ea.eamobile.nfsmw.service.rank.RecordRankService;
import com.ea.eamobile.nfsmw.service.rank.TourRecordRankService;
import com.ea.eamobile.nfsmw.service.timetask.TournamentUserRank;

/**
 * @author yang.kun@ea.com
 * @version 1.0 2012-7-5
 */
public class BootstrapServlet {
    protected static final Logger log = LoggerFactory.getLogger(BootstrapServlet.class);
    @Autowired
    private RecordRankService rankService;
    @Autowired
    private TourRecordRankService tourRankService;
    @Autowired
    private TournamentUserRank tournamentUserRank;
    @Autowired
    private CareerGhostService careerGhostService;

    private static boolean running = true;

    public static boolean isRunning() {
        return running;
    }

    public static void setRunning(boolean running) {
        BootstrapServlet.running = running;
    }

    public void initialize() {
        if (isRunning()) {
            rankService.init();
            tourRankService.init();
            tournamentUserRank.init(60);
            careerGhostService.initPool();
        }
    }

    public void cleanup() {

    }
}
