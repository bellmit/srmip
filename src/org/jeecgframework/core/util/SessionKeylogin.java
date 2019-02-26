package org.jeecgframework.core.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SessionKeylogin {

    public String getSessionKey(String strUser) {
        String strSessionKey = "";
        String serverIp = ResourceUtil.getConfigByName("rtx.server.ip");
        String strURL = "http://" + serverIp + ":8012/GetSession.cgi?receiver=" + strUser;

        try {
            java.net.URL url = new URL(strURL);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            strSessionKey = reader.readLine();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return strSessionKey;
    }

}
