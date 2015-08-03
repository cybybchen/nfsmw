package com.ea.eamobile.nfsmw.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HTTPStringResolver implements HTTPBodyResolver<String> {

    @Override
    public String solve(InputStream in) {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String strLine = "";
            while ((strLine = br.readLine()) != null) {
                sb.append(strLine.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
