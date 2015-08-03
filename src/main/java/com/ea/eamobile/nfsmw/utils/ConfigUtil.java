package com.ea.eamobile.nfsmw.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigUtil {
    private static final Logger logger = LoggerFactory.getLogger(ConfigUtil.class);
    public static final List<String> servers = new ArrayList<String>();

    private static Properties readProps(String filename, Properties props) {
        InputStream is = ConfigUtil.class.getResourceAsStream(filename);
        try {
            props.load(is);
        } catch (Exception e) {
            logger.error("Error loading nfmw.properties", e);
        } finally {
            try {
                is.close();
            } catch (Exception e) {
            }
        }
        return props;
    }

    static {
        Properties props = new Properties();
        readProps("/nfsmw.properties", props);
        readProps("/override.properties", props);

        JDBC_DRIVER = props.getProperty("jdbc.driverClassName");
        JDBC_URL = props.getProperty("jdbc.url");
        JDBC_READONLY_URL = props.getProperty("jdbc.readonly.url");
        JDBC_READONLY_BACKUP_URL = props.getProperty("jdbc.readonly.backup.url");
        JDBC_USERNAME = props.getProperty("jdbc.username");
        JDBC_PASSWORD = props.getProperty("jdbc.password");

        MEMCACHE_SERVERS = props.getProperty("memcache.servers");

        COMMAND_URL = props.getProperty("command.url");
        MAIN_COMMAND_URL = props.getProperty("maincommand.url");
        NFSMW_URL = props.getProperty("nfsmw.url");
        WILLOWTREE_PREFIX = props.getProperty("willowtree.prefix");
        RESOURCE_PREFIX = props.getProperty("resource.prefix");

        RESOURCE_TEST_COUNT = props.getProperty("resource.testcount");
        RESOURCE_TESTER = props.getProperty("resource.tester");
        ADMIN_USER = props.getProperty("admin.user");
        ADMIN_PASSWORD = props.getProperty("admin.password");
        // game info
        CLIENT_ID = props.getProperty("client.id");
        CLIENT_SECRET = props.getProperty("client.secret");
        GHOSTRECORD_PATH = props.getProperty("ghostrecord.path");
        // version
        GAME_VERSION = props.getProperty("game_version");
        // 加2个活动日期，用来控制统计sql先执行
        ACT_START = props.getProperty("act_start");
        ACT_END = props.getProperty("act_end");
        IAP_ACTIVITY_CONTENT = props.getProperty("act_content");
        servers.add(ConfigUtil.MAIN_COMMAND_URL);
        servers.add(ConfigUtil.COMMAND_URL);
        SERVER_URL = props.getProperty("server.url", "http://nfs.people.com.cn");
        RACE_ADD_TIME = props.getProperty("race_add_time");
        RACE_ADD_SPEED = props.getProperty("race_add_speed");
        DBPOOL_PROXY = props.getProperty("dbpool.proxy");
        SANBOX_OPEN = Boolean.valueOf(props.getProperty("sandbox_open"));
    }
    public static final String JDBC_DRIVER;
    public static final String JDBC_URL;
    public static final String JDBC_READONLY_URL;
    public static final String JDBC_USERNAME;
    public static final String JDBC_PASSWORD;
    public static final String MEMCACHE_SERVERS;
    public static final String COMMAND_URL;
    public static final String MAIN_COMMAND_URL;
    public static final String NFSMW_URL;
    public static final String WILLOWTREE_PREFIX;
    public static final String RESOURCE_PREFIX;
    public static final String RESOURCE_TEST_COUNT;
    public static final String RESOURCE_TESTER;
    public static final String ADMIN_USER;
    public static final String ADMIN_PASSWORD;
    public static final String CLIENT_ID;
    public static final String CLIENT_SECRET;
    public static final String GHOSTRECORD_PATH;
    public static final String GAME_VERSION;
    public static final String ACT_START;
    public static final String ACT_END;
    public static final String SERVER_URL;
    public static final String JDBC_READONLY_BACKUP_URL;
    public static final String IAP_ACTIVITY_CONTENT;
    public static final String RACE_ADD_TIME;
    public static final String RACE_ADD_SPEED;
    public static final String DBPOOL_PROXY;
    public static final Boolean SANBOX_OPEN;

}
