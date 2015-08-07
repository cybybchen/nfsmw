package com.ea.eamobile.nfsmw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.model.RpLeaderBoard;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

@Service
public class RpLeaderBoardService {
	@Autowired
	private UserService userService;
	@Autowired
	private MemcachedClient cache;

	/**
	 * 取top50的世界排行榜
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RpLeaderBoard> getLeaderboardList() {
		List<RpLeaderBoard> list = (List<RpLeaderBoard>) cache
				.get(CacheKey.RP_LEADERBOARD_FIFTY);
		if (list == null) {
			list = buildLeaderboardList();
			cache.set(CacheKey.RP_LEADERBOARD_FIFTY, list,
					MemcachedClient.MIN * 15);
		}
		return list;
	}

	private List<RpLeaderBoard> buildLeaderboardList() {
		List<RpLeaderBoard> result = new ArrayList<RpLeaderBoard>();
		List<User> list = getTop100();
		for (int i = 0; i < list.size(); i++) {
			// if (i >= 50)
			if (result.size() >= 50)
				break;
			User user = list.get(i);
			if (user == null) {
				continue;
			}
			// rm delete user
			if (user.getName().contains("DELETE_")) {
				continue;
			}

			// 排除被封停的用户
			if ((user.getAccountStatus() & Const.IS_BAN) == 1) {
				continue;
			}
			RpLeaderBoard board = buildBoard(user);
			result.add(board);
		}
		return result;
	}

	private RpLeaderBoard buildBoard(User user) {
		RpLeaderBoard rpLeaderBoard = new RpLeaderBoard();
		rpLeaderBoard.setHeadIndex(user.getHeadIndex());
		rpLeaderBoard.setHeadUrl(user.getHeadUrl() == null ? "" : user
				.getHeadUrl());
		rpLeaderBoard.setMostWantedNum(user.getStarNum());
		rpLeaderBoard.setName(user.getName());
		rpLeaderBoard.setRpLevel(user.getLevel());
		rpLeaderBoard.setRpNum(user.getRpNum());
		rpLeaderBoard.setUserId(user.getId());
		return rpLeaderBoard;
	}

	@SuppressWarnings("unchecked")
	private List<User> getTop100() {
		List<User> list = (List<User>) cache
				.get(CacheKey.RP_LEADERBOARD_HUNDRED);
		if (list == null) {
			list = userService.getTopHundredUser();
			cache.set(CacheKey.RP_LEADERBOARD_HUNDRED, list,
					MemcachedClient.MIN * 15);
		}
		return list;
	}

	/**
	 * 取用户的排名
	 * 
	 * @param user
	 * @return
	 */
	public int getUserRank(User user) {
		List<User> list = getTop100();
		for (int i = 0; i < list.size(); i++) {
			User lbuser = list.get(i);
			if (lbuser.getName().contains("DELETE_") || (lbuser.getAccountStatus() & Const.IS_BAN) == 1) {
				list.remove(lbuser);
				--i;
				continue;
			}
			
			if (user.getId() == lbuser.getId()) {
				return i + 1;
			}
		}
		return 101;
	}

}
