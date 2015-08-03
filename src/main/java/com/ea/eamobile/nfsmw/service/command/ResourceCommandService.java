package com.ea.eamobile.nfsmw.service.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.model.Resource;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestResourceCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResourceItemInfo;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseResourceCommand;
import com.ea.eamobile.nfsmw.service.ResourceService;
import com.ea.eamobile.nfsmw.service.ResourceVersionService;
import com.ea.eamobile.nfsmw.utils.ConfigUtil;

@Service
public class ResourceCommandService {

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private ResourceVersionService versionService;

    public static final int ACTION_DOWN = 1;
    public static final int ACTION_DELETE = 3;

    public ResponseResourceCommand getResponseResourceoCommand(RequestResourceCommand reqcmd, User user, int gameEdition) {
        ResponseResourceCommand.Builder builder = ResponseResourceCommand.newBuilder();
        fillResourceCommand(builder, reqcmd.getClientVersion(), user,gameEdition);
        return builder.build();
    }

    /**
     * 返回资源列表给前端
     * 
     * @param builder
     */
    private void fillResourceCommand(ResponseResourceCommand.Builder builder, int clientVersion, User user,int gameEdition) {

        int version = versionService.getVisibleVersion(user, clientVersion,gameEdition);
        builder.setRootPath(ConfigUtil.RESOURCE_PREFIX + "/" + version);
        builder.setVersion(version);
        List<ResourceItemInfo> iteminfos = buildResourceList(version, clientVersion);
        builder.addAllItems(iteminfos);
    }

    private List<ResourceItemInfo> buildResourceList(int version, int clientVersion) {
        List<ResourceItemInfo> list = Collections.emptyList();
        // 客户端版本是后台传给前端的，如果用户版本已最高就不需要下载
        if (clientVersion >= version) {
            return list;
        }
        list = new ArrayList<ResourceItemInfo>();
        ResourceItemInfo.Builder builder = ResourceItemInfo.newBuilder();
        List<Resource> resources = resourceService.getResourceListByVersion(version);
        for (Resource res : resources) {
            builder.setPath(res.getFileName());
            builder.setSize(res.getSize());
            builder.setMd5(res.getMd5());
            int action = res.getStatus() <= 0 ? ACTION_DOWN : res.getStatus();
            builder.setAction(action);
            list.add(builder.build());
        }
        return list;
    }

}
