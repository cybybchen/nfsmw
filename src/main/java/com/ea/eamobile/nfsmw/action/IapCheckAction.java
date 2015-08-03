package com.ea.eamobile.nfsmw.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ea.eamobile.nfsmw.constants.IapConst;
import com.ea.eamobile.nfsmw.utils.ConfigUtil;

@Controller
@RequestMapping("/nfsmw/admin")
public class IapCheckAction {

    protected static final String COOKIE_NAME = "ticket";

    @RequestMapping("iapcheck")
    public String home(Model model, HttpServletRequest request, HttpServletResponse response) {
        if (!isLogin(request)) {
            return "admin/login";
        }

        return "admin/iapcheck";
    }

    @RequestMapping("checkIapInfo")
    public String checkIapInfo(Model model, HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "iapinfo", required = false, defaultValue = "") String iapinfo) {
        if (!isLogin(request)) {
            return "admin/login";
        }
        String encoding = "UTF-8";
        Map<Character, Integer> valueMap = new HashMap<Character, Integer>();

        valueMap.put((char) '0', 0);
        valueMap.put((char) '1', 1);
        valueMap.put((char) '2', 2);
        valueMap.put((char) '3', 3);
        valueMap.put((char) '4', 4);
        valueMap.put((char) '5', 5);
        valueMap.put((char) '6', 6);
        valueMap.put((char) '7', 7);
        valueMap.put((char) '8', 8);
        valueMap.put((char) '9', 9);
        valueMap.put((char) 'a', 10);
        valueMap.put((char) 'b', 11);
        valueMap.put((char) 'c', 12);
        valueMap.put((char) 'd', 13);
        valueMap.put((char) 'e', 14);
        valueMap.put((char) 'f', 15);

        String message = "";
        String line = null;
        try {
            byte[] bse64 = null;

            try {
                bse64 = Base64.encodeBase64(hexToString(iapinfo, IapConst.HEX_VALUE_MAP).getBytes(encoding));
            } catch (UnsupportedEncodingException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            URL dataUrl = new URL("https://buy.itunes.apple.com/verifyReceipt");
            HttpURLConnection con = (HttpURLConnection) dataUrl.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("content-type", "text/json");
            con.setRequestProperty("Proxy-Connection", "Keep-Alive");
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream(), encoding);
            String str = "";
            if (bse64 != null) {
                str = String.format(Locale.CHINA, "{\"receipt-data\":\"" + new String(bse64, encoding) + "\"}");

            }
            out.write(str);
            out.flush();
            out.close();
            InputStream is = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, encoding));

            while ((line = reader.readLine()) != null) {

                try {
                    if (!line.startsWith("{")) {
                        line = "{" + line;
                    }
                    if (!line.endsWith("}")) {
                        line = line + "}";
                    }

                    JSONObject json = new JSONObject(line);
                    if (!json.isNull("status")) {
                        message = json.toString();
                        int status = json.getInt("status");
                        if (status == 0) {
                            JSONObject receiptResult = json.getJSONObject("receipt");
                            String productId = receiptResult.getString("product_id");
                            int num = receiptResult.getInt("quantity");
                            long transactionId = receiptResult.getLong("transaction_id");
                            System.out.println("productId=" + productId + "quantity=" + num + "transaction_id="
                                    + transactionId);
                        }
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("message", message);
        return "admin/iapcheck";
    }

    protected String buildCookie() {
        String username = ConfigUtil.ADMIN_USER;
        String password = ConfigUtil.ADMIN_PASSWORD;
        return DigestUtils.md5Hex(username + password);
    }

    protected boolean isLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equals(COOKIE_NAME) && cookie.getValue().equals(buildCookie())) {
                    return true;
                }
            }
        }
        return false;
    }

    private String hexToString(String src, Map<Character, Integer> valueMap) {
        StringBuffer ret = new StringBuffer();
        boolean isPart = false;
        char currentChar = 0;
        for (int i = 0; i < src.length(); ++i) {
            char c = src.charAt(i);
            if (c == '<' || c == '>' || c == ' ') {
                continue;
            }
            if (isPart) {
                currentChar = (char) ((valueMap.get(currentChar) << 4) | valueMap.get(c));
                ret.append(currentChar);
                isPart = false;
            } else {
                currentChar = c;
                isPart = true;
            }
        }
        return ret.toString();
    }

    //
    // private static byte[] HexString2Bytes(String src) {
    // byte[] ret = new byte[src.length() / 2];
    // byte[] tmp = src.getBytes();
    // for (int i = 0; i < tmp.length / 2; i++) {
    // ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
    // }
    // return ret;
    // }

    // private static byte uniteBytes(byte src0, byte src1) {
    // byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
    // _b0 = (byte) (_b0 << 4);
    // byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
    // byte ret = (byte) (_b0 ^ _b1);
    // return ret;
    // }
    //
    // private String deccc(String src, Map<Character, Integer> valueMap) {
    //
    // StringBuffer ret = new StringBuffer();
    // boolean isPart = false;
    // char currentChar = 0;
    // for (int i = 0; i < src.length(); ++i) {
    // char c = src.charAt(i);
    // if (c == '<' || c == '>' || c == ' ') {
    // continue;
    // }
    // if (isPart) {
    // currentChar = (char) ((valueMap.get(currentChar) << 4) | valueMap.get(c));
    // ret.append(currentChar);
    // isPart = false;
    // } else {
    // currentChar = c;
    // isPart = true;
    // }
    // }
    // return ret.toString();
    // }

}
