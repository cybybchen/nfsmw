package com.ea.eamobile.nfsmw.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ea.eamobile.nfsmw.chain.GenericErrorHandle;
import com.ea.eamobile.nfsmw.chain.NFSRequest;
import com.ea.eamobile.nfsmw.chain.NFSResponse;
import com.ea.eamobile.nfsmw.chain.RequestHandle;
import com.ea.eamobile.nfsmw.protoc.Commands.RequestCommand;
import com.ea.eamobile.nfsmw.protoc.Commands.ResponseCommand;

@Controller
public class GamedataAction {
    private static final Logger logger = LoggerFactory.getLogger(GamedataAction.class);
    private static final String CONTENT_TYPE = "application/octet-stream";

    @Autowired
    @Qualifier("chainOfScreens")
    private ArrayList<RequestHandle> chainOfScreens;

    private RequestHandle genericErrorHandle = new GenericErrorHandle();

    private NFSRequest http2cmd(HttpServletRequest request) throws IOException {
        InputStream in = request.getInputStream();
        try {
            RequestCommand requestCommand = RequestCommand.parseFrom(in);
            logger.debug("PROCESSING request={}, bytes={}", requestCommand, requestCommand.getSerializedSize());
            NFSRequest ret = new NFSRequest();
            ret.command = requestCommand;
            ret.user = null;
            return ret;
        } finally {
            in.close();
        }
    }

    private void cmd2http(NFSResponse nfsResponse, HttpServletResponse response) throws IOException {
        ResponseCommand responseCommand = nfsResponse.command.build();
        logger.debug("PROCESSING response={}", responseCommand);
        OutputStream out = response.getOutputStream();
        try {
            responseCommand.writeTo(out);
            out.flush();
        } finally {
            out.close();
        }
    }

    @RequestMapping("/gamedata")
    public void exec(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType(CONTENT_TYPE);
            NFSRequest req = null;
            NFSResponse rep = new NFSResponse();
            try {
                req = http2cmd(request);
            } catch (Exception e) {
                logger.error("NFMW_REQUEST_ERROR", e);
            }
            try {
                if (req != null) {
                    // 此处其实null 避免screen顺序变更多加一句真实赋值在headscreen
                    NfsThreadLocal.set(req.user);
                }
                boolean result = true;
                // Maybe null for new user.
                for (RequestHandle handle : chainOfScreens) {
                    result = handle.handleRequest(req, rep);
                    if (!result) {
                        break;
                    }
                }
                cmd2http(rep, response);
            } catch (Exception e) {
                genericErrorHandle.handleRequest(req, rep);
                logger.error("NFMW_RESPONSE_ERROR", e);
            } finally {
                // remove threadlocal
                NfsThreadLocal.clear();
            }
        } catch (Throwable e) {
            logger.error("NFMW_ERRO", e);
        }
    }

}
