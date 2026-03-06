package com.superowl.rftg.luigi.database;

import com.superowl.rftg.luigi.models.Film;
import com.superowl.rftg.luigi.UrlManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * DAO pour la table film
 * Connexion au backend Toad via API REST
 */
public class FilmDAO {

    public FilmDAO() {
    }

    /**
     * Récupérer tous les films depuis le backend REST
     * GET /films avec token JWT
     */
    public ArrayList<Film> getAllFilms() {
        ArrayList<Film> films = new ArrayList<>();

        try {
            String token = UrlManager.getJwtToken();
            String response = ApiHelper.get("/films", token);
            if (response == null) {
                return films;
            }

            JSONArray filmsArray = new JSONArray(response);
            for (int i = 0; i < filmsArray.length(); i++) {
                JSONObject filmJson = filmsArray.getJSONObject(i);

                int filmId = filmJson.getInt("filmId");
                String title = filmJson.optString("title", "");
                String description = filmJson.optString("description", "");

                films.add(new Film(filmId, title, description));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return films;
    }
}
