package com.superowl.rftg.luigi.database;

import com.superowl.rftg.luigi.models.Utilisateur;
import org.json.JSONObject;
import java.security.MessageDigest;

/**
 * DAO pour la table customers
 * Authentification via POST /customers/verify (endpoint public, sans token)
 */
public class StaffDAO {

    public StaffDAO() {
    }

    /**
     * Authentifier un customer via POST /customers/verify
     * @param username email du customer
     * @param password mot de passe en clair
     * @return Utilisateur si trouvé, null sinon
     */
    private String hashMD5(String input) {
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(input.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(hash[i]);
                if (hex.length() == 1) {
                    sb.append('0');
                    sb.append(hex.charAt(hex.length() - 1));
                } else {
                    sb.append(hex.substring(hex.length() - 2));
                }
            }
            return sb.toString();
        } catch (Exception e) {
            return input;
        }
    }

    public Utilisateur authenticate(String username, String password) {
        try {
            JSONObject loginBody = new JSONObject();
            loginBody.put("email", username);
            loginBody.put("password", hashMD5(password));

            String response = ApiHelper.post("/customers/verify", loginBody.toString());
            if (response == null) {
                return null;
            }

            JSONObject json = new JSONObject(response);
            int customerId = json.getInt("customerId");
            if (customerId == -1) {
                return null;
            }

            return new Utilisateur(customerId, username, username, "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
