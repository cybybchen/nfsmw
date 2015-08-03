package com.ea.eamobile.nfsmw.service.dao.helper.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class GeneralDaoMemcachedHelper<T extends GeneralDataHelper>
		extends GeneralDaoHelper<T> {

	private static final MemcachedClient cache = new MemcachedClient();

	private int expiredTime = 0;

	static {
		cache.init();
	}

	abstract protected String getPrefix();

	protected GeneralDaoMemcachedHelper() {
		this.expiredTime = MemcachedClient.DAY;
	}

	protected GeneralDaoMemcachedHelper(int expiredTime) {
		this.expiredTime = expiredTime;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, T> get(List<String> keys) {
		Map<String, Object> data = cache.get(buildKeys(keys));
		// TODO check data, if not get enough data log it
		Map<String, T> rs = new HashMap<String, T>();
		if (!data.isEmpty()) {
			for (Entry<String, Object> ent : data.entrySet()) {
				rs.put(separateKey(ent.getKey()), (T) ent.getValue());
			}
		}
		
		return rs;
	}

	@Override
	public Integer set(String key, T value) {
		return cache.syncSet(buildKey(key), value, expiredTime) ? 1 : 0;
	}

	@Override
	public Integer delete(String key) {
		return cache.syncDelete(buildKey(key)) ? 1 : 0;
	}

	@Override
	public String getIdentityString() {
		return getPrefix();
	}

	private String buildKey(String key) {
		return getPrefix() + key;
	}

	private List<String> buildKeys(List<String> keys) {
		List<String> keyList = new ArrayList<String>();
		for (String key : keys) {
			keyList.add(buildKey(key));
		}
		return keyList;
	}

	private String separateKey(String key) {
		return key.substring(getPrefix().length());
	}
}
