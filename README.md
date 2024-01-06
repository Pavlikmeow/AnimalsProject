# AnimalsProject
 ## Информация о проекте
 Необходимо организовать систему учета для питомника в котором живут домашние и Pack animals. 
# Операционные системы и виртуализация (Linux)
 ## 1. Использование команды cat в Linux
   - Создать два текстовых файла: "Pets"(Домашние животные) и "Pack animals"(вьючные животные), используя команду `cat` в терминале Linux. В первом файле перечислить собак, кошек и хомяков. Во втором — лошадей, верблюдов и ослов.
   - Объединить содержимое этих двух файлов в один и просмотреть его содержимое.
   - Переименовать получившийся файл в "Human Friends"(.
Пример конечного вывода после команды “ls” :
Desktop Documents Downloads  HumanFriends.txt  Music  PackAnimals.txt  Pets.txt  Pictures  Videos
![image](https://github.com/Pavlikmeow/AnimalsProject/assets/99546306/7f8ffe5e-b1bd-4afc-831c-eded8c116157)


## 2. Работа с директориями в Linux
   - Создать новую директорию и переместить туда файл "Human Friends".
![image](https://github.com/Pavlikmeow/AnimalsProject/assets/99546306/a7672c7b-f570-489f-b95e-87b1899aca7f)
## 3. Работа с MySQL в Linux. “Установить MySQL на вашу вычислительную машину ”
   - Подключить дополнительный репозиторий MySQL и установить один из пакетов из этого репозитория.
![image](https://github.com/Pavlikmeow/AnimalsProject/assets/99546306/5e8199e3-4714-4a65-9d1f-a53ee1e0dbc2)

## 4. Управление deb-пакетами
   - Установить и затем удалить deb-пакет, используя команду `dpkg`.
![image](https://github.com/Pavlikmeow/AnimalsProject/assets/99546306/570950d2-f08f-4dec-a6a6-a50916b7bc98)

## 5. История команд в терминале Ubuntu
   - Сохранить и выложить [историю](https://github.com/Pavlikmeow/AnimalsProject/blob/master/command_history.txt) ваших терминальных команд в Ubuntu.
![image](https://github.com/Pavlikmeow/AnimalsProject/assets/99546306/764cfecc-deb5-4ec0-b8a1-3668db0ffd9b)

# Объектно-ориентированное программирование 
## 6. Диаграмма классов
   - Создать [диаграмму классов](https://github.com/Pavlikmeow/AnimalsProject/blob/master/class_diagram.drawio) с родительским классом "Животные", и двумя подклассами: "Pets" и "Pack animals".
В составы классов которых в случае Pets войдут классы: собаки, кошки, хомяки, а в класс Pack animals войдут: Лошади, верблюды и ослы).
Каждый тип животных будет характеризоваться (например, имена, даты рождения, выполняемые команды и т.д)
Диаграмму можно нарисовать в любом редакторе, такими как Lucidchart, Draw.io, Microsoft Visio и других.
![image](https://github.com/Pavlikmeow/AnimalsProject/assets/99546306/46e119f8-333e-49c3-864a-c02c3d57ccf9)

## 7. Работа с MySQL (Задача выполняется в случае успешного выполнения задачи “Работа с MySQL в Linux. “Установить MySQL на вашу машину”
(Выполнено)

## 7.1. После создания диаграммы классов в 6 пункте, в 7 пункте база данных "Human Friends" должна быть структурирована в соответствии с этой диаграммой. Например, можно создать таблицы, которые будут соответствовать классам "Pets" и "Pack animals", и в этих таблицах будут поля, которые характеризуют каждый тип животных (например, имена, даты рождения, выполняемые команды и т.д.). 
## 7.2   - В ранее подключенном MySQL создать базу данных с названием "Human Friends".
   - Создать таблицы, соответствующие иерархии из вашей диаграммы классов.
   - Заполнить таблицы данными о животных, их командах и датами рождения.
   - Удалить записи о верблюдах и объединить таблицы лошадей и ослов.
   - Создать новую таблицу для животных в возрасте от 1 до 3 лет и вычислить их возраст с точностью до месяца.
   - Объединить все созданные таблицы в одну, сохраняя информацию о принадлежности к исходным таблицам.

[посмотреть SQL скрипт файл](https://github.com/Pavlikmeow/AnimalsProject/blob/master/script.sql)

```
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
```
## 8. ООП и Java
  - Создать иерархию классов в Java, который будет повторять диаграмму классов созданную в задаче 6(Диаграмма классов) .

## 9. Программа-реестр домашних животных
  - Написать [программу на Java](https://github.com/Pavlikmeow/AnimalsProject/blob/master/src/main/java/AnimalProgram.java), которая будет имитировать реестр домашних животных. 
Должен быть реализован следующий функционал:
    
## 9.1. Добавление нового животного
- Реализовать функциональность для добавления новых животных в реестр.       
Животное должно определяться в правильный класс (например, "собака", "кошка", "хомяк" и т.д.)
        
 
## 9.2. Список команд животного
- Вывести список команд, которые может выполнять добавленное животное (например, "сидеть", "лежать").
        
## 9.3. Обучение новым командам
- Добавить возможность обучать животных новым командам.
  
## 9.4 Вывести список животных по дате рождения

## 9.5. Навигация по меню
- Реализовать консольный пользовательский интерфейс с меню для навигации между вышеуказанными функциями.

![image](https://github.com/Pavlikmeow/AnimalsProject/assets/99546306/1d429b68-915f-47a4-b0e0-b11daa447640)

        
## 10. Счетчик животных
Создать механизм, который позволяет вывести на экран общее количество созданных животных любого типа (Как домашних, так и вьючных), то есть при создании каждого нового животного счетчик увеличивается на “1”. 

![image](https://github.com/Pavlikmeow/AnimalsProject/assets/99546306/8793bc1f-cef5-45b2-9ce0-5f8c433f1757)





