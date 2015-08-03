package com.ea.eamobile.nfsmw.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.model.Leaderboard;
import com.ea.eamobile.nfsmw.model.RaceMode;
import com.ea.eamobile.nfsmw.model.mapper.LeaderboardMapper;
import com.ea.eamobile.nfsmw.service.command.moderank.ModeRankTypeHandler;
import com.ea.eamobile.nfsmw.service.command.moderank.ModeRankTypeHandlerFactory;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;
import com.ea.eamobile.nfsmw.view.CacheUser;

@Service
public class LeaderboardService {

    @Autowired
    private LeaderboardMapper leaderboardMapper;
    @Autowired
    private ModeRankTypeHandlerFactory rankTypeHandlerFactory;
    @Autowired
    private UserService userService;
    @Autowired
    private MemcachedClient cache;

    public void insertLeaderboard(Leaderboard leaderboard) {
        cache.delete(CacheKey.LEADERBOARD + leaderboard.getModeType());
        leaderboardMapper.insert(leaderboard);
    }

    public void updateLeaderboard(Leaderboard leaderboard) {
        cache.delete(CacheKey.LEADERBOARD + leaderboard.getModeType());
        leaderboardMapper.update(leaderboard);
    }

    @SuppressWarnings("unchecked")
    public List<Leaderboard> getLeaderboardByMode(RaceMode mode) {
        List<Leaderboard> list = (List<Leaderboard>) cache.get(CacheKey.LEADERBOARD + mode.getModeType());
        if (list == null) {
            list = leaderboardMapper.getLeaderboardByMode(mode.getModeType());
            List<Leaderboard> result = new ArrayList<Leaderboard>();
            if (list != null && list.size() > 0) {
                for (Leaderboard board : list) {
                    if (refreshUserInfo(board)) {
                        result.add(board);
                    }
                }
                ComparatorLeaderboard cl = new ComparatorLeaderboard();
                cl.setRankType(mode.getRankType());
                Collections.sort(result, cl);
            }
            cache.set(CacheKey.LEADERBOARD + mode.getModeType(), result, MemcachedClient.HOUR);
            return result;
        }
        return list;
    }

    private boolean refreshUserInfo(Leaderboard board) {
        CacheUser cacheUser = userService.getCacheUser(board.getUserId());
        if (cacheUser == null || ((cacheUser.getAccountStatus() & Const.IS_NORECORD) == Const.IS_NORECORD)) {
            return false;
        }
        board.setUserName(cacheUser.getName());
        board.setHeadIndex(cacheUser.getHeadIndex());
        board.setHeadUrl(cacheUser.getHeadUrl());
        return true;

    }

    public void updateLeaderboard(Leaderboard userBoard, RaceMode mode) {
        ModeRankTypeHandler handler = rankTypeHandlerFactory.create(mode);
        List<Leaderboard> list = getLeaderboardByMode(mode);
        Leaderboard self = getSelf(userBoard, list);
        Leaderboard last = getLast(list);
        // 空榜直接添加 || 榜上没自己直接添加
        if (list.size() == 0 || (list.size() < Const.LEADERBOARD_NUM && self == null)) {
            insertLeaderboard(userBoard);
            return;
        }
        if (self != null && !handler.isFaster(userBoard.getResult(), self.getResult())) {
            return;
        }
        // 榜上有自己根据成绩更新
        if (self != null && handler.isFaster(userBoard.getResult(), self.getResult())) {
            self = replaceBoard(userBoard, self);
            updateLeaderboard(self);
            return;
        }
        // 把老末替换掉

        if (last != null && handler.isFaster(userBoard.getResult(), last.getResult())) {
            last = replaceBoard(userBoard, last);
            updateLeaderboard(last);
            return;
        }

    }

    private Leaderboard replaceBoard(Leaderboard userBoard, Leaderboard last) {
        last.setResult(userBoard.getResult());
        last.setUserId(userBoard.getUserId());
        last.setUserName(userBoard.getUserName());
        last.setHeadIndex(userBoard.getHeadIndex());
        last.setHeadUrl(userBoard.getHeadUrl());
        return last;
    }

    /**
     * 判断自己是否在榜上
     * 
     * @param userBoard
     * @param topTenList
     * @return
     */
    private Leaderboard getSelf(Leaderboard userBoard, List<Leaderboard> topTenList) {
        for (Leaderboard lb : topTenList) {
            if (lb.getUserId() == userBoard.getUserId()) {
                return lb;
            }
        }
        return null;
    }

    /**
     * 添加了对榜数量的判断
     * 
     * @param list
     * @return
     */
    private Leaderboard getLast(List<Leaderboard> list) {
        if (list != null && list.size() >= Const.LEADERBOARD_NUM) {
            return list.get(Const.LEADERBOARD_NUM - 1);
        }
        return null;
    }

    public Leaderboard getLeaderboardByModeIdAndUserId(int modeType, long userId) {
        return leaderboardMapper.getLeaderboardByModeIdAndUserId(modeType, userId);
    }

    public List<Leaderboard> getLeaderboardByUserId(long userId) {
        return leaderboardMapper.getLeaderboardByUserId(userId);
    }

    public void deleteByUserId(long userId) {
        List<Leaderboard> list = getLeaderboardByUserId(userId);
        if(list!=null){
            for(Leaderboard lb : list){
                cache.delete(CacheKey.LEADERBOARD + lb.getModeType());
            }
        }
        leaderboardMapper.deleteByUserId(userId);
    }

    public void deleteByModeType(int modeType) {
        cache.delete(CacheKey.LEADERBOARD + modeType);
        leaderboardMapper.deleteByModeType(modeType);
    }
}
