package com.superowl.rftg.luigi.utils;

import com.superowl.rftg.luigi.models.Film;
import java.util.ArrayList;

/**
 * Gestionnaire du panier
 * Stocke les films ajoutés en mémoire
 */
public class PanierManager {
    private static ArrayList<Film> films = new ArrayList<>();

    /**
     * Ajouter un film au panier
     */
    public static void ajouterFilm(Film film) {
        films.add(film);
    }

    /**
     * Obtenir tous les films du panier
     */
    public static ArrayList<Film> getFilms() {
        return films;
    }

    /**
     * Vider le panier
     */
    public static void vider() {
        films.clear();
    }

    /**
     * Obtenir le nombre de films
     */
    public static int getNombre() {
        return films.size();
    }
}
