package com.ea.eamobile.nfsmw.service.command;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.ProfileComparisonType;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.UserRaceAction;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestRecordUserRaceActionCommand;
import com.ea.eamobile.nfsmw.service.UserRaceActionService;

@Service
public class RecordUserRaceActionCommandService {

	@Autowired
	private UserRaceActionService userRaceActionService;
	
	public void record(RequestRecordUserRaceActionCommand cmd, User user) throws SQLException {
		long userId = user.getId();
		List<UserRaceAction> userRaceActionList = userRaceActionService.getUserRaceActionListByUserId(user.getId());
		Map<Integer, UserRaceAction> userRaceActionMap = mapUserRaceActionList(userRaceActionList);
		updateUserRaceAction(userRaceActionMap, userId, ProfileComparisonType.TAKE_DOWN_COPS.getIndex(), cmd.getCopsKill());
		updateUserRaceAction(userRaceActionMap, userId, ProfileComparisonType.CONSUMABLE_COLLECTOR.getIndex(), cmd.getConsumable());
		updateUserRaceAction(userRaceActionMap, userId, ProfileComparisonType.DRIFT_KING.getIndex(), cmd.getDrift());
		updateUserRaceAction(userRaceActionMap, userId, ProfileComparisonType.JUMPER.getIndex(), cmd.getJump());
		updateUserRaceAction(userRaceActionMap, userId, ProfileComparisonType.BILLBOARD_DESTROYER.getIndex(), cmd.getBillBoard());
	}

    /**
     * 更新数据库
     * 
     * @param userRaceActionList: 数据库中已有数据列表
     * @param userId
     * @param valueId: 数据类型id
     * @param requestData: 前端发送来的新数据
     * @return
     */
	private void updateUserRaceAction(Map<Integer, UserRaceAction> userRaceActionMap, long userId, int valueId, int requestData){
		UserRaceAction userRaceAction = null;
        if (userRaceActionMap == null || ((userRaceAction = userRaceActionMap.get(valueId)) == null)){
			userRaceActionService.insert(userId, valueId, requestData);
		} else {
			int value = userRaceAction.getValue();
			userRaceAction.setValue(value + requestData);// 当前存储的数据为累加数据
			userRaceActionService.update(userRaceAction);
		}
	}
	
	private Map<Integer, UserRaceAction> mapUserRaceActionList(List<UserRaceAction> userRaceActionList) {
		if (userRaceActionList == null || userRaceActionList.isEmpty())return null;
		
		Map<Integer, UserRaceAction> userRaceActionMap = new HashMap<Integer, UserRaceAction>();
		for (UserRaceAction tmp : userRaceActionList){
			userRaceActionMap.put(tmp.getValueId(), tmp);
		}
		return userRaceActionMap;
	}
	
}
