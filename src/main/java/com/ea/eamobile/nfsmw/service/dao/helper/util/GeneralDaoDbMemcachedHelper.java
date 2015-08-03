package com.ea.eamobile.nfsmw.service.dao.helper.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class GeneralDaoDbMemcachedHelper<T extends GeneralDataHelper> extends GeneralDaoHelper<T> {

    private static final Logger logger = LoggerFactory.getLogger(GeneralDaoDbMemcachedHelper.class);

    private GeneralDaoDbHelper<T> dbHelper = null;
    private GeneralDaoMemcachedHelper<T> cacheHelper = null;

    private String logStr = null;

    public GeneralDaoDbMemcachedHelper(GeneralDaoDbHelper<T> dbHelper, GeneralDaoMemcachedHelper<T> cacheHelper) {
        this.dbHelper = dbHelper;
        this.cacheHelper = cacheHelper;
        logStr = getIdentityString();
    }

    @Override
    public Map<String, T> get(List<String> keys) {
        Map<String, T> rs = cacheHelper.get(keys);

        if (rs.size() != keys.size()) {
            List<String> cacheMissedKeys = findMissedKeys(keys, rs);

            if (!cacheMissedKeys.isEmpty()) {

                if (logger.isDebugEnabled()) {
                    logger.debug(logStr + " get cache missed keys = " + formatKeys(cacheMissedKeys));
                }

                Map<String, T> missedRs = dbHelper.get(cacheMissedKeys);
                for (Entry<String, T> ent : missedRs.entrySet()) {
                    cacheHelper.set(ent.getKey(), ent.getValue());
                    rs.put(ent.getKey(), ent.getValue());
                }

                if (missedRs.size() != cacheMissedKeys.size()) {
                    List<String> dbMissedKeys = findMissedKeys(cacheMissedKeys, missedRs);
                    if (logger.isWarnEnabled()) {
                        logger.debug(logStr + " get cache and db all missed keys = " + formatKeys(dbMissedKeys));
                    }
                }
            } else {
                logger.debug(logStr + " get input param list have duplicate values!");
            }
        }

        return rs;
    }

    @Override
    public Integer set(String key, T value) {
        int rs = dbHelper.set(key, value);

        if (rs == 0) {
            logger.debug(logStr + " set db failed key = " + key);
            return 0;
        }

        if (reload(key) == null) {
            logger.debug(logStr + " set db sucess but reload cache failed key = " + key + ", will delete cache");
            cacheHelper.delete(key);
        }

        return rs;
    }

    @Override
    public Integer delete(String key) {
        if (dbHelper.delete(key) == 0) {
            logger.debug(logStr + " delete db failed key = " + key);
            return 0;
        }

        if (cacheHelper.delete(key) == 0) {
            logger.debug(logStr + " delete db success but cache failed key = " + key);
            return 0;
        }

        return 1;
    }

    @Override
    public String getIdentityString() {
        return "Cache Identity = " + cacheHelper.getIdentityString() + " Db Identity = " + dbHelper.getIdentityString();
    }

    /*
     * use generatedKey to load cache
     */
    public Long insert(T value) {
        Long generatedKey = dbHelper.insert(value);

        if (generatedKey == -1L) {
            logger.debug(logStr + " insert db failed key = " + generatedKey);
            return -1L;
        }

        if (reload(generatedKey) == null) {
            logger.debug(logStr + " insert db sucess but reload cache failed key = " + generatedKey);
        }

        return generatedKey;
    }

    public Integer insert(String key, T value) {
        int rs = dbHelper.insert(key, value);

        if (rs == 0) {
            logger.debug(logStr + " insert db failed key = " + key);
            return 0;
        }

        return reload(key) == null ? 0 : rs;
    }

    public T reload(String key) {
        T rs = dbHelper.get(key);
        if (rs == null) {
            logger.debug(logStr + " reload get from db failed key = " + key);
            return null;
        }

        if (cacheHelper.set(key, rs) == 0) {
            logger.debug(logStr + " reload get from db success but set cache failed key = " + key);
            return null;
        }

        return rs;
    }

    public T reload(Long key) {
        return reload(String.valueOf(key));
    }

    protected int update(String sql, String key) {
        int rs = dbHelper.update(sql);

        if (rs == 0) {
            logger.debug(logStr + " update failed key = " + key);
            return 0;
        }

        if (reload(key) == null) {
            logger.debug(logStr + " update db success but reload cache failed key = " + key + ", will delete cache");
            cacheHelper.delete(key);
            return 0;
        }

        return rs;
    }

    protected int update(String sql, List<String> keys) {
        int rs = dbHelper.update(sql);

        if (rs == 0) {
            logger.debug(logStr + " update db failed keys = " + formatKeys(keys));
            return 0;
        }

        List<String> failedKeys = new ArrayList<String>();
        for (String key : keys) {
            if (reload(key) == null) {
                failedKeys.add(key);
            }
        }

        if (!failedKeys.isEmpty()) {
            logger.debug(logStr + " update db success but reload cache failed keys = " + formatKeys(failedKeys)
                    + ", will delete cache");
            for (String key : failedKeys) {
                cacheHelper.delete(key);
            }
        }

        return rs;
    }

    protected int update(String sql, Long key) {
        return update(sql, String.valueOf(key));
    }

    protected List<String> findMissedKeys(List<String> keys, Map<String, T> data) {
        List<String> missedKeys = new ArrayList<String>();
        for (String key : keys) {
            if (!data.containsKey(key)) {
                missedKeys.add(key);
            }
        }
        return missedKeys;
    }

    protected String formatKeys(List<String> keys) {
        StringBuilder keyStr = new StringBuilder("");
        for (String key : keys) {
            keyStr.append(key).append(" ");
        }
        return keyStr.toString();
    }

}
