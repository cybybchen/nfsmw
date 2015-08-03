package com.ea.eamobile.nfsmw.chain;

import com.ea.eamobile.nfsmw.constants.ErrorConst;
import com.ea.eamobile.nfsmw.protoc.Commands.ErrorCommand;

public class NullUserErrorHandle implements RequestHandle {
    @Override
    public boolean handleRequest(NFSRequest req, NFSResponse rep) {
        ErrorCommand.Builder errBuilder = ErrorCommand.newBuilder();
        errBuilder.setCode(String.valueOf(ErrorConst.USER_NULL.getCode()));
        errBuilder.setMessage(ErrorConst.USER_NULL.getMesssage());
        rep.command.setErrorCommand(errBuilder.build());
        return false;
    }
}
