INSERT INTO candidat (nom, prenom, adresse, lien_photo) VALUES
('Rakoto', 'Jean', 'Antananarivo', 'photo1.jpg'),
('Rabe', 'Marie', 'Fianarantsoa', 'photo2.jpg');

INSERT INTO correcteur (nom, prenom, adresse, lien_photo) VALUES
('Randria', 'Paul', 'Toamasina', 'corr1.jpg'),
('Rasoanaivo', 'Luc', 'Toamasina', 'corr2.jpg'),
('Nivo', 'Arisoa', 'Toamasina', 'corr3.jpg');

INSERT INTO matiere (nom, coef) VALUES
('Math', 6),
('Physique', 5);

INSERT INTO examen (nom, date_debut, date_fin) VALUES
('Baccalaureat 2025', '2025-07-01', '2025-07-10');

INSERT INTO note (correcteur_id, candidat_id, matiere_id, examen_id, note) VALUES
(1, 1, 1, 1, 10),
(1, 1, 2, 1, 15),

(2, 1, 1, 1, 15),
(2, 1, 2, 1, 5),

(3, 1, 1, 1, 12),
(3, 1, 2, 1, 9);

INSERT INTO operateur (operateur) VALUES 
("<="),
(">="),


INSERT INTO resolution (resolution) VALUES 
("plus petit"),
("moyenne"),
("plus grand");

INSERT INTO parametre (matiere_id, operateur_id, seuil_sum_diff, resolution_id) VALUES 
(1, 1, 5, 1),  -- ??? < 5 => plus petit
(1, 1, 10, 3),  -- ??? < 10 => plus grand
(1, 2, 10, 2);  -- ??? > 10 => moyenne