package com.ea.eamobile.nfsmw.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.model.UserConfig;
import com.ea.eamobile.nfsmw.model.handler.UserConfigHandler;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestSystemCommand.SystemConfigType;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;
import com.ea.eamobile.nfsmw.utils.RWSplit;

@Service
public class UserConfigService {
    @Autowired
    private MemcachedClient cache;

    private static final Map<SystemConfigType, Integer> TYPE2INT = new HashMap<SystemConfigType, Integer>();
    private static final Map<Integer, SystemConfigType> INT2TYPE = new HashMap<Integer, SystemConfigType>();
    static {
        TYPE2INT.put(SystemConfigType.UserLanguage, 0);
        TYPE2INT.put(SystemConfigType.SynergyUID, 1);
        INT2TYPE.put(0, SystemConfigType.UserLanguage);
        INT2TYPE.put(1, SystemConfigType.SynergyUID);
    }

    private String buildKey(long userId, SystemConfigType type) {
        return CacheKey.USER_CONFIG + userId + "_" + TYPE2INT.get(type);
    }

    public UserConfig getUserConfig(long userId, SystemConfigType type) throws SQLException {
        UserConfig config = (UserConfig) cache.get(buildKey(userId, type));
        if (config != null) {
            return config;
        }
        String sql = "SELECT id, user_id, type, content FROM user_config WHERE user_id = ? AND type = ?";
        QueryRunner run = new QueryRunner(RWSplit.getInstance().getReadDataSource());
        config= run.query(sql, new UserConfigHandler(), userId, TYPE2INT.get(type));
        cache.set(buildKey(userId, type), config, MemcachedClient.DAY);
        return config;
    }

    public void save(long userId, SystemConfigType type, String content) throws SQLException {
        String sql = "INSERT INTO user_config (user_id, type, content) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE content=?";
        QueryRunner run = new QueryRunner(RWSplit.getInstance().getWriteDataSource());
        run.update(sql, userId, TYPE2INT.get(type), content, content);
        cache.delete(buildKey(userId, type));
    }

}