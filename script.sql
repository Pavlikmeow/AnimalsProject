-- Создание базы данных
CREATE DATABASE IF NOT EXISTS Human_friends;

-- Использование базы данных
USE Human_friends;

-- Создание таблицы для классов животных
CREATE TABLE IF NOT EXISTS animal_classes (
                                              Id INT AUTO_INCREMENT PRIMARY KEY,
                                              Class_name VARCHAR(20) NOT NULL
);

-- Вставка данных в таблицу классов животных
INSERT INTO animal_classes (Class_name)
VALUES
    ('Pack Animals'),
    ('Home Animals');

-- Создание таблицы для вьючных животных
CREATE TABLE IF NOT EXISTS packed_animals (
                                              Id INT AUTO_INCREMENT PRIMARY KEY,
                                              Genus_name VARCHAR(20) NOT NULL,
                                              Class_id INT,
                                              FOREIGN KEY (Class_id) REFERENCES animal_classes (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Вставка данных в таблицу вьючных животных
INSERT INTO packed_animals (Genus_name, Class_id)
VALUES
    ('Horses', 1),
    ('Camels', 1),
    ('Donkeys', 1);

-- Создание таблицы для домашних животных
CREATE TABLE IF NOT EXISTS home_animals (
                                            Id INT AUTO_INCREMENT PRIMARY KEY,
                                            Genus_name VARCHAR(20) NOT NULL,
                                            Class_id INT,
                                            FOREIGN KEY (Class_id) REFERENCES animal_classes (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Вставка данных в таблицу домашних животных
INSERT INTO home_animals (Genus_name, Class_id)
VALUES
    ('Cats', 2),
    ('Dogs', 2),
    ('Hamsters', 2);

-- Создание таблицы для кошек
CREATE TABLE IF NOT EXISTS cats (
                                    Id INT AUTO_INCREMENT PRIMARY KEY,
                                    Name VARCHAR(20),
                                    Birthday DATE,
                                    Commands VARCHAR(50),
                                    Genus_id INT,
                                    FOREIGN KEY (Genus_id) REFERENCES home_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Вставка данных в таблицу кошек
INSERT INTO cats (Name, Birthday, Commands, Genus_id)
VALUES
    ('Pupa', '2011-01-01', 'meow-meow-meow', 1),
    ('Oleg', '2016-01-01', 'stay away!', 1),
    ('Darkness', '2017-01-01', '', 1);

-- Создание таблицы для собак
CREATE TABLE IF NOT EXISTS dogs (
                                    Id INT AUTO_INCREMENT PRIMARY KEY,
                                    Name VARCHAR(20),
                                    Birthday DATE,
                                    Commands VARCHAR(50),
                                    Genus_id INT,
                                    FOREIGN KEY (Genus_id) REFERENCES home_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Вставка данных в таблицу собак
INSERT INTO dogs (Name, Birthday, Commands, Genus_id)
VALUES
    ('Duke', '2020-01-01', 'come here, lie down, paw, speak', 2),
    ('Graf', '2021-06-12', 'sit, lie down, paw', 2),
    ('Ball', '2018-05-01', 'sit, lie down, paw, track, fetch', 2),
    ('Boss', '2021-05-10', 'sit, lie down, eww, place', 2);

-- Создание таблицы для хомяков
CREATE TABLE IF NOT EXISTS hamsters (
                                        Id INT AUTO_INCREMENT PRIMARY KEY,
                                        Name VARCHAR(20),
                                        Birthday DATE,
                                        Commands VARCHAR(50),
                                        Genus_id INT,
                                        FOREIGN KEY (Genus_id) REFERENCES home_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Вставка данных в таблицу хомяков
INSERT INTO hamsters (Name, Birthday, Commands, Genus_id)
VALUES
    ('Little one', '2020-10-12', '', 3),
    ('Bear', '2021-03-12', 'attack from above', 3),
    ('Ninja', '2022-07-11', NULL, 3),
    ('Brownie', '2022-05-10', NULL, 3);

-- Создание таблицы для лошадей
CREATE TABLE IF NOT EXISTS horses (
                                      Id INT AUTO_INCREMENT PRIMARY KEY,
                                      Name VARCHAR(20),
                                      Birthday DATE,
                                      Commands VARCHAR(50),
                                      Genus_id INT,
                                      FOREIGN KEY (Genus_id) REFERENCES packed_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Вставка данных в таблицу лошадей
INSERT INTO horses (Name, Birthday, Commands, Genus_id)
VALUES
    ('Thunder', '2020-01-12', 'run, trot', 1),
    ('Sunset', '2017-03-12', 'run, trot, hop', 1),
    ('Baikal', '2016-07-12', 'run, trot, hop, snort', 1),
    ('Lightning', '2020-11-10', 'run, trot, hop', 1);

-- Создание таблицы для ослов
CREATE TABLE IF NOT EXISTS donkeys (
                                       Id INT AUTO_INCREMENT PRIMARY KEY,
                                       Name VARCHAR(20),
                                       Birthday DATE,
                                       Commands VARCHAR(50),
                                       Genus_id INT,
                                       FOREIGN KEY (Genus_id) REFERENCES packed_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Вставка данных в таблицу ослов
INSERT INTO donkeys (Name, Birthday, Commands, Genus_id)
VALUES
    ('First', '2019-04-10', NULL, 2),
    ('Second', '2020-03-12', "", 2),
    ('Third', '2021-07-12', "", 2),
    ('Fourth', '2022-12-10', NULL, 2);

-- Создание таблицы для верблюдов
CREATE TABLE IF NOT EXISTS camels (
                                      Id INT AUTO_INCREMENT PRIMARY KEY,
                                      Name VARCHAR(20),
                                      Birthday DATE,
                                      Commands VARCHAR(50),
                                      Genus_id INT,
                                      FOREIGN KEY (Genus_id) REFERENCES packed_animals (Id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Вставка данных в таблицу верблюдов
INSERT INTO camels (Name, Birthday, Commands, Genus_id)
VALUES
    ('Humpy', '2022-04-10', 'come back', 3),
    ('Male', '2019-03-12', 'stop', 3),
    ('Siphon', '2015-07-12', 'turn', 3),
    ('Beard', '2022-12-10', 'smile', 3);

-- Удаление записей о верблюдах
DELETE FROM camels;

-- Объединение таблиц лошадей и ослов
CREATE TABLE IF NOT EXISTS horses_and_donkeys AS
SELECT * FROM horses
UNION
SELECT * FROM donkeys;

-- Создание временной таблицы для молодых животных
CREATE TEMPORARY TABLE IF NOT EXISTS young_animals AS
SELECT *
FROM (
         SELECT * FROM cats
         UNION
         SELECT * FROM dogs
         UNION
         SELECT * FROM hamsters
         UNION
         SELECT * FROM horses
         UNION
         SELECT * FROM donkeys
     ) AS combined_animals
WHERE TIMESTAMPDIFF(YEAR, `Birthday`, CURDATE()) BETWEEN 1 AND 3;

-- Вычисление возраста с точностью до месяца
CREATE TABLE IF NOT EXISTS age_calculated AS
SELECT *,
       TIMESTAMPDIFF(MONTH, `Birthday`, CURDATE()) AS `Age_in_months`
FROM `young_animals`;

-- Объединение всех созданных таблиц в одну
CREATE TABLE IF NOT EXISTS all_animals AS
SELECT * FROM horses_and_donkeys
UNION ALL
SELECT * FROM age_calculated;