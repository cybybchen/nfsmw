package com.ea.eamobile.nfsmw.cache;

import java.util.concurrent.ConcurrentHashMap;

public class InProcessCache {

    private static InProcessCache _instance = new InProcessCache();

    public static InProcessCache getInstance() {
        return _instance;
    }

    private InProcessCache() {
    }

    private ConcurrentHashMap<String, Object> _cache = new ConcurrentHashMap<String, Object>();

    public Object get(String key) {
        return _cache.get(key);
    }

    public void set(String key, Object value) {
        if (value == null)
            return;
        _cache.put(key, value);
    }

    public void expire(String key) {
        _cache.remove(key);
    }

}
