package com.superowl.rftg.luigi.models;

/**
 * Modèle Film - Version simplifiée pour Luigi
 * Correspond au modèle backend mais avec moins de champs
 */
public class Film {
    private Integer filmId;
    private String title;
    private String description;
    private Double rentalRate; // Prix de location

    // Constructeur vide
    public Film() {}

    // Constructeur complet
    public Film(Integer filmId, String title, String description, Double rentalRate) {
        this.filmId = filmId;
        this.title = title;
        this.description = description;
        this.rentalRate = rentalRate;
    }

    // Getters
    public Integer getFilmId() { return filmId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Double getRentalRate() { return rentalRate; }

    // Setters (pour Phase 2 - parsing JSON)
    public void setFilmId(Integer filmId) { this.filmId = filmId; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setRentalRate(Double rentalRate) { this.rentalRate = rentalRate; }

    /**
     * Formater le prix pour affichage
     */
    public String getPrixFormate() {
        if (rentalRate == null) return "0.00€";
        return String.format("%.2f€", rentalRate);
    }

    /**
     * Affichage dans la liste
     */
    @Override
    public String toString() {
        return title + " - " + getPrixFormate();
    }
}
