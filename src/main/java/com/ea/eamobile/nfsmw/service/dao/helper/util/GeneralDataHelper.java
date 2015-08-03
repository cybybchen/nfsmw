package com.ea.eamobile.nfsmw.service.dao.helper.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface GeneralDataHelper {

	/*
	 * 将db返回值转换成对象
	 */
	public void parseDbData(ResultSet rs) throws SQLException;

	/*
	 * 将已经赋值修改的成员变量转化为map用来写入db
	 */
	public Map<String, String> getDataMap();

	/*
	 * 将需要有默认值的数据加入dataMap用来写入db
	 */
	public void addDefaultDataMap(Map<String, String> dataMap);

	/*
	 * 删除dataMap中db中不可以修改的字段，只有update的时候会调用，insert不会调用
	 */
	public void deleteIgnoreDataMap(Map<String, String> dataMap);

}
