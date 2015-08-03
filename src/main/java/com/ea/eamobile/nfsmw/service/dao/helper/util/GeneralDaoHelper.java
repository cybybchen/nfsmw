package com.ea.eamobile.nfsmw.service.dao.helper.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class GeneralDaoHelper<T extends GeneralDataHelper> {

	private static final Logger logger = LoggerFactory
			.getLogger(GeneralDaoHelper.class);

	public abstract Map<String, T> get(List<String> keys);

	public abstract Integer set(String key, T value);

	public abstract Integer delete(String key);

	public abstract String getIdentityString();

	public List<T> getDataList(List<String> keys) {
		Map<String, T> dataMap = get(keys);
		List<T> rs = new ArrayList<T>();
		for (Entry<String, T> entry : dataMap.entrySet()) {
			rs.add(entry.getValue());
		}
		return rs;
	}

	public T get(String key) {
		List<String> keys = new ArrayList<String>();
		keys.add(key);
		Map<String, T> rs = get(keys);
		if (rs.isEmpty()) {
			logger.debug("get empty data key = " + key);
			return null;
		}
		return rs.get(key);
	}

	public T get(Long key) {
		return get(String.valueOf(key));
	}

	public List<T> get(Long[] keys) {
		List<String> keysList = new ArrayList<String>();
		for (Long key : keys) {
			keysList.add(String.valueOf(key));
		}
		return getDataList(keysList);
	}

	public Integer set(Long key, T value) {
		return set(String.valueOf(key), value);
	}

	public Integer delete(Long key) {
		return delete(String.valueOf(key));
	}
}
