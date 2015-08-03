package com.ea.eamobile.nfsmw.service.ghost;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.Tournament;
import com.ea.eamobile.nfsmw.model.TournamentGhost;
import com.ea.eamobile.nfsmw.model.TournamentLeaderboard;
import com.ea.eamobile.nfsmw.model.TournamentOnline;
import com.ea.eamobile.nfsmw.model.TournamentUser;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.service.TournamentGhostService;
import com.ea.eamobile.nfsmw.service.TournamentLeaderboardService;
import com.ea.eamobile.nfsmw.service.TournamentOnlineService;
import com.ea.eamobile.nfsmw.service.TournamentService;
import com.ea.eamobile.nfsmw.service.TournamentUserService;
import com.ea.eamobile.nfsmw.view.BaseGhost;

@Service
public class TourGhostPoolService {

    @Autowired
    protected TournamentGhostService tourGhostService;
    @Autowired
    protected TournamentOnlineService onlineService;
    @Autowired
    protected TournamentUserService tourUserService;
    @Autowired
    protected TournamentService tourService;
    @Autowired
    protected TournamentLeaderboardService tourlbService;

    protected static final Logger log = LoggerFactory.getLogger(TourGhostPoolService.class);

    public List<? extends BaseGhost> getGhosts(User user, int onlineId, int num) {
        List<BaseGhost> result = Collections.emptyList();
        TournamentUser self;
        try {
            self = tourUserService.getTournamentUser(user, onlineId);
            if (self == null) {
                // 走到这是因为还没报名就要取ghost的2B设计 用户此时没有分配classid
                // 此处返回online下的ghost
                List<TournamentGhost> onlineGhosts = tourGhostService.getTournamentGhostList(onlineId);
                if (onlineGhosts == null || onlineGhosts.size() == 0) {
                    return Collections.emptyList();
                }
                result = new ArrayList<BaseGhost>();
                for (TournamentGhost ghost : onlineGhosts) {
                    if (ghost.getUserId() == user.getId()) {
                        continue;
                    }
                    result.add(ghost);
                    if (result.size() >= num) {
                        return result;
                    }
                }
                return result;
            }
            result = getLbGhosts(onlineId, num, self);
        } catch (SQLException e) {
            log.error("get tour user err", e);
        }
        return result;
    }

    private List<BaseGhost> getLbGhosts(int onlineId, int num, TournamentUser tourUser) {
        int classId = tourUser.getClassId();
        long userId = tourUser.getUserId();
        TournamentOnline online = onlineService.getTournamentOnline(onlineId);
        if (online == null) {
            return Collections.emptyList();
        }
        Tournament tour = tourService.getTournament(online.getTournamentId());
        if (tour == null) {
            return Collections.emptyList();
        }
        List<BaseGhost> result = new ArrayList<BaseGhost>();
        int rankType = tour.getType();
        // 从leaderboard取基准数据
        List<TournamentLeaderboard> lblist = tourlbService.getLeaderboard(onlineId, classId, rankType);
        if (lblist == null || lblist.size() == 0) {
            return Collections.emptyList();
        }
        // lb 数量小于要匹配数量 排除自己全部返回
        if (lblist.size() <= num) {
            for (TournamentLeaderboard lb : lblist) {
                if (lb.getUserId() == userId) {
                    continue;
                }
                TournamentGhost ghost = tourGhostService.getGhost(onlineId, lb.getUserId());
                if (ghost != null) {
                    result.add(ghost);
                }
            }
        } else {
            result = getLbByRole(lblist, tourUser, num);
        }
        return result;
    }

    private List<BaseGhost> getLbByRole(List<TournamentLeaderboard> lblist, TournamentUser tourUser, int num) {
        List<BaseGhost> result = new ArrayList<BaseGhost>();
        // 先得到位置
        TournamentLeaderboard selfLb = adapteLb(tourUser);
        int index = lblist.indexOf(selfLb) + 1;
        // 如果比自己成绩好的ghost数量够，直接取
        int startIndex = 0;
        if (index > num) {
            startIndex = Math.max(index - num - 1, 0); // 开始添加的索引位置
        } else {
            // 数量不够 顺序填充
            if (index == 0) {
                // 如果index==0 说明用户不在榜上 需要取lb后5位
                startIndex = Math.max(0, lblist.size() - num);
            } else {
                startIndex = 0;
            }
        }
        int onlineId = tourUser.getTournamentOnlineId();
        for (int i = startIndex; i < lblist.size(); i++) {
            TournamentLeaderboard lb = lblist.get(i);
            if (lb.getUserId() == tourUser.getUserId()) {
                continue;
            }
            TournamentGhost ghost =  tourGhostService.getGhost(onlineId, lb.getUserId());
            if (ghost != null) {
                result.add(ghost);
            }
            if (result.size() >= num) {
                break;
            }
        }
        return result;
    }

    private TournamentLeaderboard adapteLb(TournamentUser tuser) {
        TournamentLeaderboard lb = new TournamentLeaderboard();
        lb.setUserId(tuser.getUserId());
        lb.setTournamentOnlineId(tuser.getTournamentOnlineId());
        lb.setClassId(tuser.getClassId());
        return lb;
    }

}