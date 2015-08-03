package com.ea.eamobile.nfsmw.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.eamobile.nfsmw.constants.Const;
import com.ea.eamobile.nfsmw.constants.CtaContentConst;
import com.ea.eamobile.nfsmw.model.User;
import com.ea.eamobile.nfsmw.protoc.Commands.ErrorCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestModifyUserInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseModifyUserInfoCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.UserInfo;
import com.ea.eamobile.nfsmw.service.CtaContentService;
import com.ea.eamobile.nfsmw.service.UserInfoMessageService;
import com.ea.eamobile.nfsmw.service.UserService;
import com.ea.eamobile.nfsmw.service.WordService;

/**
 * 修改名称或头像
 * 
 * @author ma.ruofei@ea.com
 * 
 */
@Service
public class ModifyUserInfoCommandService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoMessageService userInfoMessageService;
    @Autowired
    private CtaContentService ctaContentService;
    @Autowired
    private WordService wordService;

    public ResponseModifyUserInfoCommand getResponseModifyUserInfoCommand(RequestModifyUserInfoCommand reqcmd, User user) {
        ResponseModifyUserInfoCommand.Builder builder = ResponseModifyUserInfoCommand.newBuilder();
        UserInfo.Builder userBuilder = UserInfo.newBuilder();
        String message = "";
        boolean changedHead = false;
        // 需要修改头像
        if (reqcmd.hasHeadIndex() && user.getHeadIndex() != reqcmd.getHeadIndex()) {
            int headIndex = reqcmd.getHeadIndex();
            if (headIndex < -1 || headIndex > Const.HEAD_INDEX_MAX) {
                message = ctaContentService.getCtaContent(CtaContentConst.WRONG_HEAD_INDEX).getContent();
                return returnErrorCommand(builder, 1, message);
            }
            user.setHeadIndex(headIndex);
            changedHead = true;
        }

        boolean changedName = false;
        // 需要修改昵称
        if (reqcmd.hasNickname()) {
            String nickName = wordService.filterSpecialChar(reqcmd.getNickname());
            if (!nickName.equals(user.getName())) {
                if (wordService.isCensorable(nickName)) {
                    message = ctaContentService.getCtaContent(CtaContentConst.CENSOR_WORD).getContent();
                    return returnErrorCommand(builder, 5, message);
                }
                boolean hasChanged = user.getIsNicknameChanged();
                if (hasChanged) {
                    if (user.getGold() < Const.CHANGE_NAME_COST) {
                        message = ctaContentService.getCtaContent(CtaContentConst.NOT_ENOUGH_MONEY_TO_CHANGE_NAME)
                                .getContent();
                        return returnErrorCommand(builder, 4, message);
                    }
                    user.setGold(user.getGold() - Const.CHANGE_NAME_COST);
                }

                // if (user.getGold() < Const.CHANGE_NAME_COST) {
                // message =
                // ctaContentService.getCtaContent(CtaContentConst.NOT_ENOUGH_MONEY_TO_CHANGE_NAME).getContent();
                // return returnErrorCommand(builder, 4, message);
                // }

                if (userService.getNickNameCount(nickName) > 0) {
                    message = ctaContentService.getCtaContent(CtaContentConst.DUPLICATE_NAME).getContent();
                    return returnErrorCommand(builder, 3, message);
                }
                if (nickName.length() > 8) {
                    message = ctaContentService.getCtaContent(CtaContentConst.NAME_LENTH_TO_LONG).getContent();
                    return returnErrorCommand(builder, 6, message);
                }
                // 修改过还要扣钱

                userBuilder.setNickname(nickName);
                userBuilder.setHeadIndex(user.getHeadIndex());
                changedName = true;
                user.setIsNicknameChanged(true);
                user.setName(nickName);
            }
        }
        // 有变化 进行更新操作
        if (changedHead || changedName) {
            userService.updateUser(user);
            userService.clearCacheUser(user.getId());
        }
        // 构建返回的userinfo
        userInfoMessageService.buildUserInfoMessage(userBuilder, user);
        builder.setUserinfo(userBuilder);
        return builder.build();
    }

    private ResponseModifyUserInfoCommand returnErrorCommand(ResponseModifyUserInfoCommand.Builder builder, int code,
            String message) {
        ErrorCommand.Builder errBuilder = ErrorCommand.newBuilder();
        errBuilder.setCode(code + "");
        errBuilder.setMessage(message);
        builder.setResult(errBuilder.build());
        return builder.build();
    }

}
