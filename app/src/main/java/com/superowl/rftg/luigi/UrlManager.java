package com.superowl.rftg.luigi;

import com.superowl.rftg.rftg_luigi_v2.R;

/* Liste déroulante serveur */
public class UrlManager {
    private static String URLConnexion = "http://10.0.2.2:8180";
    private static String JWT_TOKEN = "";

    public static void init(android.content.Context context) {
        JWT_TOKEN = context.getString(R.string.jwt_token);
    }

    public static String getJwtToken() {
        return JWT_TOKEN;
    }

    public static String getURLConnexion() {
        return URLConnexion;
    }

    public static void setURLConnexion(String url) {
        URLConnexion = url;
    }
}
