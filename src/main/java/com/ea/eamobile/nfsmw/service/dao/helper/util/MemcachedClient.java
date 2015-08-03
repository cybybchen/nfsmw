package com.ea.eamobile.nfsmw.service.dao.helper.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.spy.memcached.ConnectionFactoryBuilder.Locator;
import net.spy.memcached.ConnectionFactoryBuilder.Protocol;
import net.spy.memcached.DefaultHashAlgorithm;
import net.spy.memcached.FailureMode;
import net.spy.memcached.spring.MemcachedClientFactoryBean;
import net.spy.memcached.transcoders.SerializingTranscoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.utils.ConfigUtil;

public class MemcachedClient {

	private static final Logger log = LoggerFactory
			.getLogger(MemcachedClient.class);
	public static final int MIN = 60;
	public static final int HOUR = 3600;
	public static final int DAY = 86400;

	private static net.spy.memcached.MemcachedClient client;
	private static boolean running = true;

	private static MemcachedClientFactoryBean cacheFactory;

	public synchronized void init() {
		// init clientFactory
	        if (cacheFactory == null) {
	            cacheFactory = new MemcachedClientFactoryBean();
	            
	            cacheFactory.setProtocol(Protocol.BINARY);
	            SerializingTranscoder transCoder = new SerializingTranscoder();
	            transCoder.setCompressionThreshold(1024);
	            cacheFactory.setTranscoder(transCoder);
	            cacheFactory.setDaemon(true);
	            cacheFactory.setOpTimeout(2000);
	            cacheFactory.setHashAlg(DefaultHashAlgorithm.KETAMA_HASH);
	            cacheFactory.setLocatorType(Locator.CONSISTENT);
	            cacheFactory.setFailureMode(FailureMode.Redistribute);
	            cacheFactory.setUseNagleAlgorithm(false);
	        }
		// init clientFactory
		if (client == null) {
			try {
				cacheFactory.setServers(ConfigUtil.MEMCACHE_SERVERS);
				client = (net.spy.memcached.MemcachedClient) cacheFactory
						.getObject();
				// try to connect server
				get("TEST");
				log.warn("memcache start finish...");
			} catch (Exception e) {
				running = false;
				log.error("memcache client create err, shutdown. {}",
						e.getMessage());
			}

		}
	}

	public void set(String key, Object value) {
		if (running && value != null)
			client.set(buildKey(key), DAY, value);
	}

	public void set(String key, Object value, int expired) {
		if (running && value != null)
			client.set(buildKey(key), expired, value);
	}

	public Object get(String key) {
		if (running) {
			return client.get(buildKey(key));
		}
		return null;
	}

	public void delete(String key) {
		if (running)
			client.delete(buildKey(key));
	}

	public boolean syncSet(String key, Object value) {
		if (running && value != null) {
			try {
				return client.set(buildKey(key), DAY, value).get();
			} catch (Exception e) {
				// TODO add log
			}
		}

		return false;
	}

	public boolean syncSet(String key, Object value, int expired) {
		if (running && value != null) {
			try {
				return client.set(buildKey(key), expired, value).get();
			} catch (Exception e) {
				// TODO add log
			}
		}

		return false;
	}

	public boolean syncDelete(String key) {
		if (running) {
			try {
				return client.delete(buildKey(key)).get();
			} catch (Exception e) {
				// TODO add log
			}
		}

		return false;
	}

	public Map<String, Object> get(List<String> keys) {
		Map<String, Object> rs = new HashMap<String, Object>();
		if (running) {
			Map<String, Object> data = client.getBulk(buildKeys(keys));
			if (!data.isEmpty()) {
				for (Entry<String, Object> ent : data.entrySet()) {
					rs.put(separateKey(ent.getKey()), ent.getValue());
				}
			}
		}
		return rs;
	}

	private String buildKey(String key) {
		return CacheKey.PREFIX + key;
	}

	private List<String> buildKeys(List<String> keys) {
		List<String> keyList = new ArrayList<String>();
		for (String key : keys) {
			keyList.add(buildKey(key));
		}
		return keyList;
	}

	private String separateKey(String key) {
		return key.substring(CacheKey.PREFIX.length());
	}

}
