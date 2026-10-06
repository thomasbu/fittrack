-- Insertion de l'utilisateur de test
INSERT INTO users (id, email, password, is_email_verified, objectif) 
VALUES (1, 'john@example.com', 'password123', true, 'Prise de masse') 
ON DUPLICATE KEY UPDATE id=id;

-- Catalogue d'aliments (protein_per_100gr au singulier + user_id obligatoire)
INSERT INTO food_items (id, name, calories_per_100gr, protein_per_100gr, carbs_per_100gr, fat_per_100gr, user_id) VALUES
(1, 'Blanc de poulet', 165, 31.0, 0.0, 3.6, 1),
(2, 'Riz basmati cuit', 130, 2.7, 28.0, 0.3, 1),
(3, 'Flocons d''avoine', 389, 16.9, 66.3, 6.9, 1),
(4, 'Banane', 89, 1.1, 22.8, 0.3, 1),
(5, 'Huile d''olive', 884, 0.0, 0.0, 100.0, 1)
ON DUPLICATE KEY UPDATE id=id;