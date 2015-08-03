package com.ea.eamobile.nfsmw.utils;

import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtil {

    public static final ObjectMapper jsonMapper = new ObjectMapper();

    public static String toJson(Object value) {
        String json = null;
        try {
            if (value != null) {
                json = jsonMapper.writeValueAsString(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (json == null) {
            json = "{\"error\",\"nodata\"}";
        }
        return json;
    }
    
    public static void main(String[] args) {
        System.out.println(toJson(null));
    }
}
