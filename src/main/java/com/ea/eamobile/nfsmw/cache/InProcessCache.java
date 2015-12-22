package com.ea.eamobile.nfsmw.cache;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InProcessCache {
	private static final Logger log = LoggerFactory.getLogger(InProcessCache.class);
	
    private static InProcessCache _instance = null;
    private static ConcurrentHashMap<String, Object> _cache = null;

    public static InProcessCache getInstance() {
    	if (_instance == null) {
    		_instance = new InProcessCache();
    		_cache = new ConcurrentHashMap<String, Object>();
    		_cache.clear();
    	}
        return _instance;
    }

    private InProcessCache() {
    }

    

    public synchronized Object get(String key) {
//    	if (key.equals("getTierCarLimitListByTierId1") || key.equals("getTierCarLimitListByTierId2")) {
//    		log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//    		log.error("ley is: " + key + ", the value is: " + _cache.get(key));
//    	}
    	if (_cache.containsKey(key)) {
    		return _cache.get(key);
    	} else {
    		return null;
    	}
    }

    public synchronized void set(String key, Object value) {
        if (value == null)
            return;
        
//        if (key.equals("getTierCarLimitListByTierId1") || key.equals("getTierCarLimitListByTierId2")) {
//        	log.error("****************************************************************************");
//        	log.error("before:" + _cache.get(key));
//        	log.error("before value:" + value);
//        }
        _cache.put(key, value);
//        if (key.equals("getTierCarLimitListByTierId1") || key.equals("getTierCarLimitListByTierId2")) {
//        	log.error("after:" + _cache.get(key));
//        	log.error("after value:" + value);
//        	log.error("****************************************************************************");
//        }
    }

    public void expire(String key) {
        _cache.remove(key);
    }

}
