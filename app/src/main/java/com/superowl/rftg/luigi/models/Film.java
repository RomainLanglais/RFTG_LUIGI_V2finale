package com.superowl.rftg.luigi.models;

/**
 * Modèle Film - Version simplifiée pour Luigi
 * Correspond au modèle backend mais avec moins de champs
 */
public class Film {
    private Integer filmId;
    private String title;
    private String description;
    // Constructeur vide
    public Film() {}

    // Constructeur complet
    public Film(Integer filmId, String title, String description) {
        this.filmId = filmId;
        this.title = title;
        this.description = description;
    }

    // Getters
    public Integer getFilmId() { return filmId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }

    // Setters
    public void setFilmId(Integer filmId) { this.filmId = filmId; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return title;
    }
}
