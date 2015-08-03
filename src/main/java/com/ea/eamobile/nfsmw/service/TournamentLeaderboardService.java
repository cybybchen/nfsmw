package com.ea.eamobile.nfsmw.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.model.TournamentLeaderboard;
import com.ea.eamobile.nfsmw.model.TournamentOnline;
import com.ea.eamobile.nfsmw.model.TournamentUser;
import com.ea.eamobile.nfsmw.model.mapper.TournamentLeaderboardMapper;
import com.ea.eamobile.nfsmw.service.command.moderank.ModeRankTypeHandler;
import com.ea.eamobile.nfsmw.service.command.moderank.ModeRankTypeHandlerFactory;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;
import com.ea.eamobile.nfsmw.view.CacheUser;

/**
 * @author ma.ruofei
 * @version 1.0 Tue Nov 06 15:20:44 CST 2012
 * @since 1.0
 */
@Service
public class TournamentLeaderboardService {

    @Autowired
    private TournamentLeaderboardMapper lbMapper;
    @Autowired
    private ModeRankTypeHandlerFactory rankTypeHandlerFactory;
    @Autowired
    private UserService userService;
    @Autowired
    private MemcachedClient cache;

    private String buildKey(int onlineId, int classId) {
        return CacheKey.TOURNAMENT_LEADERBOARD + onlineId + "_" + classId;
    }

    private void clear(TournamentLeaderboard lb) {
        if (lb != null)
            cache.delete(buildKey(lb.getTournamentOnlineId(), lb.getClassId()));
    }

    public int insert(TournamentLeaderboard lb) {
        clear(lb);
        return lbMapper.insert(lb);
    }

    public void update(TournamentLeaderboard lb) {
        clear(lb);
        lbMapper.update(lb);
    }

    @SuppressWarnings("unchecked")
    public List<TournamentLeaderboard> getLeaderboard(int onlineId, int classId, int rankType) {
        List<TournamentLeaderboard> list = (List<TournamentLeaderboard>) cache.get(buildKey(onlineId, classId));
        if (list == null) {
            list = lbMapper.getLeaderboard(onlineId, classId);
            List<TournamentLeaderboard> result = new ArrayList<TournamentLeaderboard>();
            if (list != null && list.size() > 0) {
                // 刷新一下可能变更的用户名或头像
                for (TournamentLeaderboard board : list) {
                    if (refreshUserInfo(board)) {
                        result.add(board);
                    }
                }
                ComparatorLeaderboard comparatorLeaderboard = new ComparatorLeaderboard();
                comparatorLeaderboard.setRankType(rankType);
                Collections.sort(result, comparatorLeaderboard);
            }
            cache.set(buildKey(onlineId, classId), result, MemcachedClient.HOUR);
            return result;
        }
        return list;
    }

    private boolean refreshUserInfo(TournamentLeaderboard board) {
        CacheUser cacheUser = userService.getCacheUser(board.getUserId());
        if (cacheUser == null || ((cacheUser.getAccountStatus() & Const.IS_NORECORD) == Const.IS_NORECORD)) {
            return false;
        }
        board.setUserName(cacheUser.getName());
        board.setHeadIndex(cacheUser.getHeadIndex());
        board.setHeadUrl(cacheUser.getHeadUrl());
        return true;

    }

    public void updateLeaderboard(TournamentLeaderboard userBoard, TournamentOnline online) {
        int rankType = online.getTournament().getType();
        ModeRankTypeHandler handler = rankTypeHandlerFactory.create(rankType);
        List<TournamentLeaderboard> list = getLeaderboard(online.getId(), userBoard.getClassId(), rankType);
        TournamentLeaderboard self = getSelf(userBoard, list);
        TournamentLeaderboard last = getLast(list);
        // 空榜直接添加 || 榜上没自己直接添加
        if (list.size() == 0 || (list.size() < Const.LEADERBOARD_NUM && self == null)) {
            insert(userBoard);
            return;
        }
        if (self != null && !handler.isFaster(userBoard.getResult(), self.getResult())) {
            return;
        }
        if (self != null && handler.isFaster(userBoard.getResult(), self.getResult())) {
            self = replaceBoard(userBoard, self);
            update(self);
            return;
        }

        // 把老末替换掉
        if (last != null && handler.isFaster(userBoard.getResult(), last.getResult())) {
            last = replaceBoard(userBoard, last);
            update(last);
            return;
        }
        // 榜上有自己根据成绩更新

    }

    private TournamentLeaderboard getSelf(TournamentLeaderboard userBoard, List<TournamentLeaderboard> list) {
        for (TournamentLeaderboard lb : list) {
            if (lb.getUserId() == userBoard.getUserId()) {
                return lb;
            }
        }
        return null;
    }

    private TournamentLeaderboard getLast(List<TournamentLeaderboard> list) {
        if (list != null && list.size() >= Const.LEADERBOARD_NUM) {
            return list.get(Const.LEADERBOARD_NUM - 1);
        }
        return null;
    }

    private TournamentLeaderboard replaceBoard(TournamentLeaderboard userBoard, TournamentLeaderboard last) {
        last.setResult(userBoard.getResult());
        last.setUserId(userBoard.getUserId());
        last.setUserName(userBoard.getUserName());
        last.setHeadIndex(userBoard.getHeadIndex());
        last.setHeadUrl(userBoard.getHeadUrl());
        return last;
    }

    public TournamentLeaderboard getTournamentLeaderboardByUserId(int tournamentOnlineId, int classId, long userId) {
        return lbMapper.getLeaderboardByUserId(tournamentOnlineId, classId, userId);
    }

    public void deleteByUserId(long id) {
        lbMapper.deleteByUserId(id);
    }

    public List<TournamentLeaderboard> getRankNumberOneList(int tournamentOnlineId) {
        return lbMapper.getRankNumberOneList(tournamentOnlineId);
    }

    public List<TournamentLeaderboard> getSpeedRankNumberOneList(int tournamentOnlineId) {
        return lbMapper.getSpeedRankNumberOneList(tournamentOnlineId);
    }

    /**
     * 获取用户在lb中的名次 如果不在返回0
     * 
     * @param tuser
     * @return
     */
    public int getRankFromLeaderboard(TournamentUser tuser, int rankType) {
        if (tuser == null || tuser.getResult()==0) {
            return 0;
        }
        List<TournamentLeaderboard> list = getLeaderboard(tuser.getTournamentOnlineId(), tuser.getClassId(), rankType);
        if(list==null || list.size()==0){
            return 1;
        }
        int rank = 0;
        for(TournamentLeaderboard lb : list){
            rank ++;
            if(tuser.getUserId()==lb.getUserId()){
                return rank;
            }
        }
        return 0;
    }
}