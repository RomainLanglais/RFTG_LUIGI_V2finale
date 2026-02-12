package com.superowl.rftg.luigi.models;

/**
 * Modèle Utilisateur
 * VERSION BOUCHON : Utilisateurs fictifs
 * Phase 2 : Utilisateurs depuis le backend
 */
public class Utilisateur {
    private Integer id;
    private String username;
    private String nom;
    private String prenom;

    public Utilisateur() {}

    public Utilisateur(Integer id, String username, String nom, String prenom) {
        this.id = id;
        this.username = username;
        this.nom = nom;
        this.prenom = prenom;
    }

    // Getters
    public Integer getId() { return id; }
    public String getUsername() { return username; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }

    // Setters
    public void setId(Integer id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    /**
     * Obtenir le nom complet
     */
    public String getNomComplet() {
        return prenom + " " + nom;
    }
}
