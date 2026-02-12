package com.superowl.rftg.luigi.database;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Helper pour les appels REST au backend Toad
 * Remplace DatabaseHelper (SQLite) par des appels HTTP
 */
public class ApiHelper {

    // 10.0.2.2 = localhost de la machine hôte depuis l'émulateur Android
    public static final String BASE_URL = "http://10.0.2.2:8180";

    /**
     * Requête GET avec token JWT
     */
    public static String get(String endpoint, String token) throws Exception {
        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        if (token != null) {
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        int code = conn.getResponseCode();
        if (code != 200) {
            conn.disconnect();
            return null;
        }

        String response = readResponse(conn);
        conn.disconnect();
        return response;
    }

    /**
     * Requête POST avec body JSON
     */
    public static String post(String endpoint, String jsonBody) throws Exception {
        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        OutputStream os = conn.getOutputStream();
        os.write(jsonBody.getBytes("UTF-8"));
        os.close();

        int code = conn.getResponseCode();
        if (code != 200) {
            conn.disconnect();
            return null;
        }

        String response = readResponse(conn);
        conn.disconnect();
        return response;
    }

    private static String readResponse(HttpURLConnection conn) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();
        return sb.toString();
    }
}
