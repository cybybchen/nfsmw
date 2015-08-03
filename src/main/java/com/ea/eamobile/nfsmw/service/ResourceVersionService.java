package com.ea.eamobile.nfsmw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.CacheKey;
import com.ea.eamobile.nfsmw.constants.ResourceConst;
import com.ea.eamobile.nfsmw.model.ResourceVersion;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.model.mapper.ResourceVersionMapper;
import com.ea.eamobile.nfsmw.service.dao.helper.util.MemcachedClient;

/**
 * @author ma.ruofei
 * @version 1.0 Wed Nov 07 16:33:25 CST 2012
 * @since 1.0
 */
@Service
public class ResourceVersionService {

    @Autowired
    private ResourceVersionMapper resourceVersionMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private MemcachedClient cache;

    public ResourceVersion getResourceVersion(int id) {
        return resourceVersionMapper.getResourceVersion(id);
    }

    @SuppressWarnings("unchecked")
    public List<ResourceVersion> getResourceVersionList() {
        List<ResourceVersion> list = (List<ResourceVersion>) cache.get(CacheKey.RESOURCE_VERSION);
        if (list == null) {
            list = resourceVersionMapper.getResourceVersionList();
            cache.set(CacheKey.RESOURCE_VERSION, list);
        }
        return list;
    }

    public int insert(ResourceVersion resourceVersion) {
        clear();
        return resourceVersionMapper.insert(resourceVersion);
    }

    public int update(ResourceVersion resourceVersion) {
        clear();
        return resourceVersionMapper.update(resourceVersion);
    }

    public void clear() {
        cache.delete(CacheKey.RESOURCE_VERSION);
    }

    public void deleteById(int id) {
        resourceVersionMapper.deleteById(id);
    }

    /**
     * 根据状态取最高版本
     * 
     * @param status
     * @param gameEdition
     * @return
     */
    public ResourceVersion getLastestVersionByStatus(int status, int gameEdition) {
        List<ResourceVersion> list = getResourceVersionList();
        if (list != null) {
            ResourceVersion temp = null;
            for (ResourceVersion version : list) {
                if (version.getStatus() == status && version.getGameEdition() == gameEdition) {
                    if (temp == null) {
                        temp = version;
                    }
                    if (version.getVersion() > temp.getVersion()) {
                        temp = version;
                    }
                }
            }
            return temp;
        }
        return null;
    }

    /**
     * 取用户可见的最高版本号 如果版本有变化会更新用户version Modify Date 20130516:还要根据游戏版本号进行判断
     * 
     * @param user
     * @param clientVersion
     * @param gameEdition
     * @return
     */
    public int getVisibleVersion(User user, int clientVersion, int gameEdition) {
        int liveVersion = 0;
        ResourceVersion live = getLastestVersionByStatus(ResourceConst.STATUS_LIVE, gameEdition);
        if (live != null) {
            liveVersion = live.getVersion();
        }
        // 1.如果用户可见版本高于live版本 说明是内测用户（后台修改的）
        if (user.getVersion() >= liveVersion) {
            //如果是个老用户，使用新设备，也会进入此分支
            if(live==null){
                return liveVersion;
            }
            return user.getVersion();
        } else {
            // 3.否则都返回当前最大的live版本
            if (clientVersion >= liveVersion) {
                return clientVersion;
            }
            // 更新用户的可见版本字段
            user.setVersion(liveVersion);
            userService.updateUser(user);
        }
        return liveVersion;
    }

    public boolean validStatus(int status) {
        return status == ResourceConst.STATUS_CB || status == ResourceConst.STATUS_OB
                || status == ResourceConst.STATUS_LIVE;
    }

    public boolean isExist(int version) {
        List<ResourceVersion> list = getResourceVersionList();
        if (list != null && list.size() > 0) {
            for (ResourceVersion v : list) {
                if (v.getVersion() == version) {
                    return true;
                }
            }
        }
        return false;
    }
}