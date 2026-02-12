package com.superowl.rftg.luigi.database;

import com.superowl.rftg.luigi.models.Utilisateur;
import com.superowl.rftg.luigi.utils.SessionManager;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * DAO pour la table staff (utilisateurs)
 * Connexion au backend Toad via API REST
 */
public class StaffDAO {

    public StaffDAO() {
    }

    /**
     * Authentifier un utilisateur via le backend REST
     * 1. POST /api/auth/login → récupère le token JWT
     * 2. GET /staffs → trouve le staff correspondant au username
     * @param username nom d'utilisateur
     * @param password mot de passe
     * @return Utilisateur si trouvé, null sinon
     */
    public Utilisateur authenticate(String username, String password) {
        try {
            // Étape 1 : Authentification → obtenir le token JWT
            JSONObject loginBody = new JSONObject();
            loginBody.put("username", username);
            loginBody.put("password", password);

            String authResponse = ApiHelper.post("/api/auth/login", loginBody.toString());
            if (authResponse == null) {
                return null;
            }

            JSONObject authJson = new JSONObject(authResponse);
            String token = authJson.getString("token");

            // Stocker le token dans le SessionManager
            SessionManager.setToken(token);

            // Étape 2 : Récupérer les infos du staff connecté
            String staffResponse = ApiHelper.get("/staffs", token);
            if (staffResponse == null) {
                return null;
            }

            JSONArray staffArray = new JSONArray(staffResponse);
            for (int i = 0; i < staffArray.length(); i++) {
                JSONObject staff = staffArray.getJSONObject(i);
                if (staff.getString("username").equals(username)) {
                    int id = staff.getInt("staffId");
                    String lastName = staff.getString("lastName");
                    String firstName = staff.getString("firstName");

                    return new Utilisateur(id, username, lastName, firstName);
                }
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
