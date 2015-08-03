package com.ea.eamobile.nfsmw.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Match;
import com.ea.eamobile.nfsmw.model.TournamentGroup;
import com.ea.eamobile.nfsmw.model.TournamentGroupClass;
import com.ea.eamobile.nfsmw.model.TournamentUser;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.handler.IntListHandler;
import com.ea.eamobile.nfsmw.model.handler.Inthandler;
import com.ea.eamobile.nfsmw.model.handler.TournamentUserHandler;
import com.ea.eamobile.nfsmw.model.handler.TournamentUserListHandler;
import com.ea.eamobile.nfsmw.model.mapper.TournamentUserMapper;
import com.ea.eamobile.nfsmw.utils.RWSplit;

/**
 * @author ma.ruofei
 * @version 1.0 Mon Sep 24 13:49:57 CST 2012
 * @since 1.0
 */
@Service
public class TournamentUserService {

    @Autowired
    private TournamentUserMapper tournamentUserMapper;

    @Autowired
    private TournamentGroupService tournamentGroupService;

    @Autowired
    private TournamentGroupClassService tournamentGroupClassService;

    public List<Integer> getTournamentUser(long userId) throws SQLException {
        QueryRunner run = new QueryRunner(RWSplit.getInstance().getReadDataSource());
        return run.query("SELECT tournament_online_id from tournament_user where user_id = ? and result!=0",
                new IntListHandler(), userId);
    }

    public List<TournamentUser> getTourUserList(int onlineId) throws SQLException {
        QueryRunner run = new QueryRunner(RWSplit.getInstance().getReadDataSource());
        return run.query("SELECT tournament_online_id,user_id,result,class_id,average_speed "
                + "from tournament_user where tournament_online_id = ? ", new TournamentUserListHandler(), onlineId);
    }

    public int insert(TournamentUser tournamentUser) {
        return tournamentUserMapper.insert(tournamentUser);
    }

    public void update(TournamentUser tournamentUser) {

        tournamentUserMapper.update(tournamentUser);
    }

    public void updateLeftTimes(long userId, int tournamentOnlineId) {
        tournamentUserMapper.updateLeftTimes(userId, tournamentOnlineId);
    }

    public void deleteById(int id) {
        tournamentUserMapper.deleteById(id);
    }

    public TournamentUser getTournamentUserByUserIdAndTOnlineId(long userId, int tournamentOnlineId)
            throws SQLException {
        QueryRunner run = new QueryRunner(RWSplit.getInstance().getReadDataSource());
        return run.query("SELECT * from tournament_user where user_id = ? and tournament_online_id=?",
                new TournamentUserHandler(), userId, tournamentOnlineId);
    }

    public TournamentUser getTournamentUserByUserIdAndTOnlineIdFromMaster(long userId, int tournamentOnlineId) {
        return tournamentUserMapper.getTournamentUserByUserIdAndTournamentOnlineId(userId, tournamentOnlineId);
    }

    public int getRank(TournamentUser tuser, int rankType) {
        int onlineId = tuser.getTournamentOnlineId();
        int classId = tuser.getClassId();
        Integer rank = 0;
        try {
            QueryRunner run = new QueryRunner(RWSplit.getInstance().getReadDataSource());
            String sql = null;
            float racetime = 0;
            if (rankType == Match.MODE_RANK_TYPE_BY_TIME) {
                racetime = tuser.getResult();
                sql = "select count(1) from tournament_user where tournament_online_id=? and class_id=? and result < ? ";
            } else {
                racetime = tuser.getAverageSpeed();
                sql = "select count(1) from tournament_user where tournament_online_id=? and class_id=? and average_speed > ?";
            }
            rank = run.query(sql, new Inthandler(), onlineId, classId, racetime)+1;
        } catch (SQLException e) {
        }
        return rank;
    }

    public TournamentUser getTournamentUser(User user, int tournamentOnlineId) throws SQLException {
        if (user == null) {
            return null;
        }
        TournamentUser tournamentUser = getTournamentUserByUserIdAndTOnlineId(user.getId(), tournamentOnlineId);
        if (tournamentUser != null) {
            tournamentUser.setUser(user);
        }

        return tournamentUser;
    }

    public int getGroupSumByGroupIdAndTournamentOnlineId(int groupId, int tournamentOnlineId) {
        if (tournamentUserMapper.getGroupSumByGroupIdAndTournamentOnlineId(groupId, tournamentOnlineId) == null) {
            return 0;
        } else {
            return tournamentUserMapper.getGroupSumByGroupIdAndTournamentOnlineId(groupId, tournamentOnlineId);
        }
    }

    public int getClassId(User u, int tournamentId, int tournamentOnlineId, boolean needSignUp) {
        int result = 0;
        int groupId = tournamentGroupService.getGroupIdForUser(tournamentId, u.getLevel());
        TournamentGroup tf = tournamentGroupService.getTournamentGroup(groupId);
        int maxClassId = tournamentGroupClassService.getMaxTournamentGroupClass(tournamentOnlineId, groupId);
        if (!needSignUp) {
            return maxClassId;
        }
        if (maxClassId == 0) {
            TournamentGroupClass tournamentGroupClass = new TournamentGroupClass();
            tournamentGroupClass.setGroupId(groupId);
            tournamentGroupClass.setUserCount(1);
            tournamentGroupClass.setTournamentOnlineId(tournamentOnlineId);
            tournamentGroupClassService.insert(tournamentGroupClass);
            result = tournamentGroupClass.getId();
        } else {
            TournamentGroupClass tournamentGroupClass = tournamentGroupClassService.getTournamentGroupClass(maxClassId);
            if (tournamentGroupClass.getUserCount() < tf.getPersonNum()) {
                tournamentGroupClass.setUserCount(tournamentGroupClass.getUserCount() + 1);
                tournamentGroupClassService.update(tournamentGroupClass);
                result = maxClassId;
            } else {
                TournamentGroupClass tournamentGroupClassNew = new TournamentGroupClass();
                tournamentGroupClassNew.setGroupId(groupId);
                tournamentGroupClassNew.setUserCount(1);
                tournamentGroupClassNew.setTournamentOnlineId(tournamentOnlineId);
                tournamentGroupClassService.insert(tournamentGroupClassNew);
                result = tournamentGroupClassNew.getId();
            }
        }
        return result;
    }

    public TournamentUser buildUser(String name, long userId, int onlineId, int groupId, int classId, int raceNum) {
        TournamentUser tourUser = new TournamentUser();
        tourUser.setName(name);
        tourUser.setResult(0);
        tourUser.setAverageSpeed(0);
        tourUser.setTournamentOnlineId(onlineId);
        tourUser.setUserId(userId);
        tourUser.setGroupId(groupId);
        tourUser.setClassId(classId);
        tourUser.setLefttimes(raceNum);
        tourUser.setIsGetReward(0);
        return tourUser;
    }

    public void deleteByUserId(long id) {
        tournamentUserMapper.deleteByUserId(id);
    }

}
