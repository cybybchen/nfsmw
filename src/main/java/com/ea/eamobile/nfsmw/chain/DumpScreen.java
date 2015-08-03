package com.ea.eamobile.nfsmw.chain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DumpScreen implements RequestHandle {

    private static final Logger logger = LoggerFactory.getLogger(HeadScreen.class);

    @Override
    public boolean handleRequest(NFSRequest request, NFSResponse response) {
        logger.debug("Request:{}", request.command.toString());
        logger.debug("Response:{}", response.command.toString());
        return true;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
