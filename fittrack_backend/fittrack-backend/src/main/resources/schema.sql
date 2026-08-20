CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_email_verified TINYINT(1) DEFAULT 0,
    objectif VARCHAR(255)
);

CREATE TABLE activity_types (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(60) NOT NULL,
    category VARCHAR(45)
);

CREATE TABLE workouts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATETIME NOT NULL,
    duration_minutes INT,
    notes VARCHAR(255),
    user_id INT NOT NULL,
    distance_km INT,
    calories INT,
    activity_type_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (activity_type_id) REFERENCES activity_types(id)
);

CREATE TABLE meals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATETIME NOT NULL,
    meal_type VARCHAR(45),
    is_cheat_meal TINYINT(1) DEFAULT 0,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE food_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    calories_per_100gr INT,
    carbs_per_100gr INT,
    fat_per_100gr INT,
    protein_per_100gr INT
);

CREATE TABLE meals_food_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    meal_id INT NOT NULL,
    food_item_id INT NOT NULL,
    quantity_grams INT NOT NULL,
    FOREIGN KEY (meal_id) REFERENCES meals(id),
    FOREIGN KEY (food_item_id) REFERENCES food_items(id)
);

CREATE TABLE body_measurements (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATETIME NOT NULL,
    weight_kg FLOAT,
    body_fat_percentage FLOAT,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE alcohol_logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATETIME NOT NULL,
    quantity_units INT,
    notes VARCHAR(255),
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);