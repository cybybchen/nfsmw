package com.ea.eamobile.nfsmw.service.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.service.dao.helper.UserDaoHelper;
import com.ea.eamobile.nfsmw.service.dao.helper.UserDataHelper;

@Component("userDao")
//@Repository
public class UserDao {

	private static final UserDaoHelper userDaoHelper = new UserDaoHelper();

	public User queryById(long userId) {
		return userDaoHelper.get(userId);
	}

	public List<User> getUsersByIds(Long[] ids) {
		List<UserDataHelper> rs = userDaoHelper.get(ids);
		if (!rs.isEmpty()) {
			return new ArrayList<User>(rs);
		}
		return null;
	}

	public User getUserByWillowtreeToken(String willowtreeToken) {
		return (User) userDaoHelper.getSingleByField("willowtree_token",
				willowtreeToken);

	}
	
	public User getUserByUid(String uid) {
		return (User) userDaoHelper.getSingleByField("uid",
				uid);
	}

	public User getUserByName(String name) {
		return (User) userDaoHelper.getSingleByField("name", name);
	}

	public List<User> getUsersByTokens(String[] tokens) {
		List<UserDataHelper> rs = userDaoHelper.getByField("willowtree_token",
				tokens);
		if (!rs.isEmpty()) {
			return new ArrayList<User>(rs);
		}
		return null;
	}

	public List<User> getTopHundredUser() {
		return userDaoHelper.getTopHundredUser();
	}

	public Integer getResouceVersionUserCount(int version) {
		return userDaoHelper.getResouceVersionUserCount(version);
	}

	public int getNickNameCount(String nickname) {
		return userDaoHelper.getNickNameCount(nickname);
	}

	public Integer getRpRank(int rpNum) {
		return userDaoHelper.getRpRank(rpNum);
	}

	public Long insert(User user) {
		return userDaoHelper.insert(new UserDataHelper(user));
	}

	public void update(User user) {
		userDaoHelper.set(user.getId(), new UserDataHelper(user));
	}

	public void updateConsumeEnergy(long userId, int reduceEnergyNum) {
		userDaoHelper.updateConsumeEnergy(userId, reduceEnergyNum);
	}

	public void updateRpNum(long userId, int reduceEnergyNum) {
		userDaoHelper.updateRpNum(userId, reduceEnergyNum);
	}

	public void updateStarNum(long userId, int addStarNum) {
		userDaoHelper.updateStarNum(userId, addStarNum);
	}

	public void updateTier(long userId, int tier) {
		UserDataHelper user = new UserDataHelper();
		user.setTier(tier);
		userDaoHelper.set(userId, user);
	}

	public void updateReward(long userid, int gainRpNum, int gainStarNum,
			int money, int gold, int energy) {
		userDaoHelper.updateReward(userid, gainRpNum, gainStarNum, money, gold,
				energy);
	}

	public void updateStatusByIds(Map<String, Object> params) {
		userDaoHelper.updateStatusByIds(params);
	}

	public void deleteById(long id) {

		userDaoHelper.delete(id);

	}
	
    public static void main(String[] args){
    	ApplicationContext cxt = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/nfsmw-servlet.xml");
    	UserDao us=(UserDao)cxt.getBean("userDao");
    	User u= (User)us.queryById(1);
    	System.out.println(u.getId());
    }
}
