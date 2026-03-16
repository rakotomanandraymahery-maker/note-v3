INSERT INTO candidat (nom, prenom, adresse, lien_photo) VALUES
('Candidat1', 'prenom', 'Antananarivo', 'photo1.jpg'),
('Candidat2', 'prenom', 'Fianarantsoa', 'photo2.jpg');

INSERT INTO correcteur (nom, prenom, adresse, lien_photo) VALUES
('Correcteur1', 'prenom', 'Toamasina', 'corr1.jpg'),
('Correcteur2', 'prenom', 'Toamasina', 'corr2.jpg'),
('Correcteur3', 'prenom', 'Toamasina', 'corr3.jpg');

INSERT INTO matiere (nom, coef) VALUES
('JAVA', 6),
('PHP', 5);

INSERT INTO examen (nom, date_debut, date_fin) VALUES
('Baccalaureat 2026', '2026-07-01', '2026-07-10');

INSERT INTO note (correcteur_id, candidat_id, matiere_id, examen_id, note) VALUES
-- Candidat 1 - JAVA : notes très divergentes pour tester "plus petit"
(1, 1, 1, 1, 18),
(2, 1, 1, 1, 8),
(3, 1, 1, 1, 7),
(3, 1, 1, 1, 19),
-- Candidat 2 - PHP : notes proches pour tester "moyenne"
(1, 2, 2, 1, 12),
(2, 2, 2, 1, 13),
(3, 2, 2, 1, 11);

INSERT INTO operateur (operateur) VALUES 
("<"),
("<="),
(">"),
(">=");

INSERT INTO resolution (resolution) VALUES 
("plus petit"),
("plus grand"),
("moyenne");

INSERT INTO parametre (matiere_id, operateur_id, seuil_sum_diff, resolution_id) VALUES 
(1, 1, 20, 2),  -- JAVA, < 20 plus grand
(1, 2, 20.5, 3),  -- JAVA, <= 20.5  moyenne
(1, 3, 18.5, 1),  -- JAVA, > 18.5  plus petit
(1, 4, 18, 2),  -- JAVA, >= 18  plus grand
(2, 1, 15, 2),  -- PHP, < 15 plus grand
(2, 2, 16, 3),  -- PHP, <= 16 moyenne
(2, 3, 12, 1),  -- PHP, > 12 plus petit
(2, 4, 10, 1);  -- PHP, >= 10 plus grand

-- 15, 15, 12, 10

-- 3, 5
-- 3,5
-- 3

-- 19

-- 1 - 1
-- 2 - 20.5 - 19 = 1.5
-- 3 - 19 - 18.5 = 0.5
-- 4 - 19 - 18 = 1



-- COPIE DE LAS BASE

-- mysqldump -u root -p db1 > db1.sql
-- mysql -u root -p db2 < db1.sql

--15,15,12,10