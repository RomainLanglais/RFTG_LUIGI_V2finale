package com.superowl.rftg.luigi.utils;

import com.superowl.rftg.luigi.models.Utilisateur;

/**
 * Gestionnaire de session utilisateur
 * Stocke l'utilisateur connecté en mémoire
 */
public class SessionManager {
    private static Utilisateur utilisateurConnecte = null;
    private static String jwtToken = null; // Pour Phase 2

    /**
     * Enregistrer l'utilisateur connecté
     */
    public static void setUtilisateur(Utilisateur user) {
        utilisateurConnecte = user;
    }

    /**
     * Obtenir l'utilisateur connecté
     */
    public static Utilisateur getUtilisateur() {
        return utilisateurConnecte;
    }

    /**
     * Vérifier si un utilisateur est connecté
     */
    public static boolean estConnecte() {
        return utilisateurConnecte != null;
    }

    /**
     * Déconnexion
     */
    public static void deconnexion() {
        utilisateurConnecte = null;
        jwtToken = null;
    }

    /**
     * Enregistrer le token JWT (pour Phase 2)
     */
    public static void setToken(String token) {
        jwtToken = token;
    }

    /**
     * Obtenir le token JWT (pour Phase 2)
     */
    public static String getToken() {
        return jwtToken;
    }
}
