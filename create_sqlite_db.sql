-- Script de création de la base SQLite pour l'application Android Luigi
-- Version simplifiée de la base Peach

-- Table staff (utilisateurs de l'application)
CREATE TABLE IF NOT EXISTS staff (
  staff_id INTEGER PRIMARY KEY AUTOINCREMENT,
  first_name TEXT NOT NULL,
  last_name TEXT NOT NULL,
  username TEXT NOT NULL UNIQUE,
  password TEXT NOT NULL,
  email TEXT,
  active INTEGER NOT NULL DEFAULT 1,
  last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table film
CREATE TABLE IF NOT EXISTS film (
  film_id INTEGER PRIMARY KEY AUTOINCREMENT,
  title TEXT NOT NULL,
  description TEXT,
  release_year INTEGER,
  rental_duration INTEGER NOT NULL DEFAULT 3,
  rental_rate REAL NOT NULL DEFAULT 4.99,
  length INTEGER,
  replacement_cost REAL NOT NULL DEFAULT 19.99,
  rating TEXT DEFAULT 'G',
  last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table category
CREATE TABLE IF NOT EXISTS category (
  category_id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT NOT NULL,
  last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table film_category
CREATE TABLE IF NOT EXISTS film_category (
  film_id INTEGER NOT NULL,
  category_id INTEGER NOT NULL,
  last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (film_id, category_id),
  FOREIGN KEY (film_id) REFERENCES film(film_id),
  FOREIGN KEY (category_id) REFERENCES category(category_id)
);

-- Insertion des utilisateurs de test
INSERT INTO staff (staff_id, first_name, last_name, username, password, email, active) VALUES
(1, 'Alice', 'Dupont', 'alice', '1234', 'alice@luigi.com', 1),
(2, 'Bob', 'Martin', 'bob', '1234', 'bob@luigi.com', 1),
(3, 'Utilisateur', 'Test', 'test', 'test', 'test@luigi.com', 1);

-- Insertion des catégories
INSERT INTO category (category_id, name) VALUES
(1, 'Action'),
(2, 'Sci-Fi'),
(3, 'Drame'),
(4, 'Fantasy'),
(5, 'Thriller');

-- Insertion des films de test
INSERT INTO film (film_id, title, description, release_year, rental_rate, length, rating) VALUES
(1, 'Matrix', 'Un hacker découvre que le monde est une simulation créée par des machines.', 1999, 5.99, 136, 'R'),
(2, 'Inception', 'Un voleur s''infiltre dans les rêves des gens pour voler leurs secrets.', 2010, 4.99, 148, 'PG-13'),
(3, 'Interstellar', 'Une équipe d''explorateurs voyage à travers un trou de ver spatial.', 2014, 6.50, 169, 'PG-13'),
(4, 'Le Seigneur des Anneaux', 'Frodon doit détruire l''anneau unique pour sauver la Terre du Milieu.', 2001, 7.99, 178, 'PG-13'),
(5, 'Avatar', 'Un marine paraplégique explore la planète Pandora.', 2009, 5.50, 162, 'PG-13'),
(6, 'Gladiateur', 'Un général romain cherche à se venger de l''empereur corrompu.', 2000, 4.50, 155, 'R'),
(7, 'Le Parrain', 'L''histoire d''une famille mafieuse italo-américaine.', 1972, 8.99, 175, 'R'),
(8, 'Pulp Fiction', 'Plusieurs histoires entrelacées dans le milieu criminel de Los Angeles.', 1994, 6.99, 154, 'R');

-- Association films <-> catégories
INSERT INTO film_category (film_id, category_id) VALUES
(1, 2), -- Matrix - Sci-Fi
(1, 1), -- Matrix - Action
(2, 2), -- Inception - Sci-Fi
(2, 5), -- Inception - Thriller
(3, 2), -- Interstellar - Sci-Fi
(3, 3), -- Interstellar - Drame
(4, 4), -- LOTR - Fantasy
(4, 1), -- LOTR - Action
(5, 2), -- Avatar - Sci-Fi
(5, 1), -- Avatar - Action
(6, 1), -- Gladiateur - Action
(6, 3), -- Gladiateur - Drame
(7, 3), -- Le Parrain - Drame
(8, 3), -- Pulp Fiction - Drame
(8, 5); -- Pulp Fiction - Thriller
