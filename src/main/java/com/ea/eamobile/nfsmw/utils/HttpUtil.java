package com.ea.eamobile.nfsmw.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil<T> {
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private final HTTPBodyResolver<T> outputResolver;

    public HttpUtil(HTTPBodyResolver<T> outputResolver) {
        this.outputResolver = outputResolver;
    }

    public HTTPBodyResolver<T> getOutputResolver() {
        return outputResolver;
    }

    public void loadpost(String url, InputStream input) {
        PostMethod httpMethod = new PostMethod(url);
        httpMethod.setRequestEntity(new InputStreamRequestEntity(input));
        HttpClient httpClient = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        Protocol easyhttps = new Protocol("https", (ProtocolSocketFactory) new EasySSLProtocolSocketFactory(), 443);
        Protocol.registerProtocol("https", easyhttps);
        try {
            httpClient.executeMethod(httpMethod);
        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public T post(String url, InputStream input) {
        PostMethod httpMethod = new PostMethod(url);
        httpMethod.setRequestEntity(new InputStreamRequestEntity(input));
        try {
            return request(httpMethod);
        } finally {
            httpMethod.releaseConnection();
        }
    }

    public T get(String url) {
        GetMethod httpMethod = new GetMethod(url);
        // 使用系统提供的默认的恢复策略
        httpMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        try {
            return request(httpMethod);
        } finally {
            httpMethod.releaseConnection();
        }
    }

    private T request(HttpMethod method) {
        HttpClient httpClient = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
        Protocol easyhttps = new Protocol("https", (ProtocolSocketFactory) new EasySSLProtocolSocketFactory(), 443);
        Protocol.registerProtocol("https", easyhttps);
        try {
            int statusCode = httpClient.executeMethod(method);// 执行getMethod
            if (statusCode != HttpStatus.SC_OK) {
                logger.error("HTTP code error: {}", statusCode);
                return null;
            }
            InputStream in = method.getResponseBodyAsStream();
            try {
                return outputResolver.solve(in);
            } catch (Exception e) {
                logger.error("Exception while receiving", e);
            }
        } catch (HttpException e) {
            logger.error("HttpException while sending", e);
        } catch (IOException e) {
            logger.error("IOException while sending", e);
        }
        return null;
    }

    public static String post(String reqUrl, Map<String, String> parameters) {
        Protocol easyhttps = new Protocol("https", (ProtocolSocketFactory) new EasySSLProtocolSocketFactory(), 443);
        Protocol.registerProtocol("https", easyhttps);
        HttpURLConnection conn = null;
        try {
            String params = generatorParamString(parameters);
            URL url = new URL(reqUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            byte[] binaryStr = params.getBytes("UTF-8");
            conn.getOutputStream().write(binaryStr, 0, binaryStr.length);
            conn.getOutputStream().flush();
            conn.getOutputStream().close();
            String responseContent = getContent(conn);
            conn.getResponseCode();
            return responseContent.trim();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return "";
    }

    private static String getContent(HttpURLConnection urlConn) {
        try {
            String responseContent = null;
            InputStream in = urlConn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String tempLine = rd.readLine();
            StringBuffer tempStr = new StringBuffer();
            String crlf = System.getProperty("line.separator");
            while (tempLine != null) {
                tempStr.append(tempLine);
                tempStr.append(crlf);
                tempLine = rd.readLine();
            }
            responseContent = tempStr.toString();
            rd.close();
            in.close();
            return responseContent;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static String generatorParamString(Map<String, String> params) throws UnsupportedEncodingException {
        String paramString = "";
        if (params != null && params.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String name = entry.getKey();
                String value = URLEncoder.encode(entry.getValue(), "UTF-8");
                sb.append(name).append("=").append(value).append("&");
            }
            paramString = sb.toString();
            paramString = paramString.substring(0, paramString.length() - 1);
        }
        return paramString;
    }
}
