# Guide de lancement de l'application Luigi Films

## État du projet
L'application est maintenant prête à être lancée dans Android Studio avec une base de données SQLite intégrée !

## Corrections et améliorations apportées
1. Correction du fichier `AndroidManifest.xml` (suppression du texte "22" en extra)
2. Configuration de Lint pour ne pas bloquer le build sur les warnings
3. **Intégration d'une base de données SQLite** :
   - Création de `DatabaseHelper` pour gérer la base de données
   - Création de `StaffDAO` pour l'authentification des utilisateurs
   - Création de `FilmDAO` pour la gestion des films
   - Migration des données "bouchon" vers la base de données
   - Modification de `ConnexionActivity` pour utiliser SQLite
   - Modification de `ListeFilmsActivity` pour charger les films depuis SQLite
4. Build de test réussi

## Comment lancer l'application dans Android Studio

### Étape 1 : Ouvrir le projet
1. Lancez Android Studio
2. Cliquez sur "Open" ou "Open an existing project"
3. Naviguez vers : `C:\BTS\ANDROID\Projets\RFTG_Luigi_V2`
4. Sélectionnez le dossier et cliquez sur "OK"

### Étape 2 : Synchroniser Gradle
1. Attendez que Android Studio charge le projet
2. Si un message de synchronisation Gradle apparaît, cliquez sur "Sync Now"
3. Attendez la fin de la synchronisation

### Étape 3 : Configurer un émulateur
Si vous n'avez pas encore d'émulateur :
1. Cliquez sur l'icône "Device Manager" dans la barre d'outils
2. Cliquez sur "Create Device"
3. Choisissez un appareil (ex: Pixel 4)
4. Sélectionnez une image système (recommandé: API 34 / Android 14)
5. Cliquez sur "Finish"

### Étape 4 : Lancer l'application
1. Sélectionnez votre émulateur dans la liste déroulante en haut
2. Cliquez sur le bouton vert "Run" (▶️) ou appuyez sur Shift+F10
3. Attendez que l'émulateur démarre et que l'application s'installe

## Base de données

**MISE À JOUR** : L'application utilise maintenant une base de données SQLite locale !

La base de données est créée automatiquement au premier lancement de l'application avec les données suivantes :

### Utilisateurs de test disponibles
- **alice** / **1234** (Alice Dupont)
- **bob** / **1234** (Bob Martin)
- **test** / **test** (Utilisateur Test)

### Films disponibles
8 films sont stockés dans la base de données SQLite :
1. Matrix (5.99€) - Sci-Fi, Action
2. Inception (4.99€) - Sci-Fi, Thriller
3. Interstellar (6.50€) - Sci-Fi, Drame
4. Le Seigneur des Anneaux (7.99€) - Fantasy, Action
5. Avatar (5.50€) - Sci-Fi, Action
6. Gladiateur (4.50€) - Action, Drame
7. Le Parrain (8.99€) - Drame
8. Pulp Fiction (6.99€) - Drame, Thriller

## Structure de la base de données

L'application utilise les tables suivantes :

### Table `staff`
Stocke les utilisateurs de l'application :
- `staff_id` : Identifiant unique
- `username` : Nom d'utilisateur
- `password` : Mot de passe (en clair pour les tests)
- `first_name` : Prénom
- `last_name` : Nom
- `email` : Adresse email
- `active` : Statut actif/inactif

### Table `film`
Stocke les films disponibles à la location :
- `film_id` : Identifiant unique
- `title` : Titre du film
- `description` : Description
- `release_year` : Année de sortie
- `rental_rate` : Prix de location
- `rental_duration` : Durée de location
- `length` : Durée du film (minutes)
- `rating` : Classification

### Table `category`
Catégories de films (Action, Sci-Fi, Drame, Fantasy, Thriller)

### Table `film_category`
Association entre films et catégories (many-to-many)

## Architecture de l'application

### Écrans (Activities)
L'application comporte 5 écrans :
1. **ConnexionActivity** - Écran de connexion (point d'entrée)
2. **ListeFilmsActivity** - Liste des films disponibles
3. **DetailsFilmActivity** - Détails d'un film
4. **PanierActivity** - Panier de location
5. **ValidationCommandeActivity** - Validation de la commande

### Package `database`
- **DatabaseHelper.java** : Gère la création et la mise à jour de la base SQLite
- **StaffDAO.java** : Data Access Object pour l'authentification des utilisateurs
- **FilmDAO.java** : Data Access Object pour la gestion des films

### Package `models`
- **Film.java** : Modèle représentant un film
- **Utilisateur.java** : Modèle représentant un utilisateur

### Package `utils`
- **SessionManager.java** : Gestion de la session utilisateur
- **PanierManager.java** : Gestion du panier de location

## Lien avec la base Peach

Cette application est basée sur la structure de la base de données Peach (dérivée de Sakila) située dans :
```
C:\BTS\dev\2ème_année\Web_service_RFTG\BTS_SIO_RFTG-2025-2026_BackEnd_Toad\peach-database
```

La structure SQLite Android est une version simplifiée des tables Peach pour fonctionner en mode hors ligne.

## Prochaines étapes (Phase 2)
L'application est prête pour la Phase 2 qui consistera à :
- Remplacer les accès SQLite par des appels REST
- Connecter au backend Toad
- Synchroniser avec la base de données Peach côté serveur
- Gérer l'authentification via JWT
- Implémenter les fonctionnalités de location complètes

## Versions utilisées
- Android Gradle Plugin: 8.1.1
- Kotlin: 1.8.10
- compileSdk: 34 (Android 14)
- minSdk: 24 (Android 7.0)
- targetSdk: 34 (Android 14)

## Build
Pour rebuilder l'application en ligne de commande :
```bash
./gradlew clean assembleDebug
```

L'APK sera généré dans : `app/build/outputs/apk/debug/app-debug.apk`

## Emplacement de la base de données

Sur l'appareil/émulateur Android, la base de données est créée dans :
```
/data/data/com.superowl.rftg.rftg_luigi_v2/databases/luigi.db
```

Vous pouvez la consulter avec Android Studio Database Inspector ou adb shell.
