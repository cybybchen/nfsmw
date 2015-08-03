package com.ea.eamobile.nfsmw.chain;


public interface RequestHandle {
    public boolean handleRequest(NFSRequest request, NFSResponse response);
}
