CREATE TABLE IF NOT EXISTS Sport (
    IdSport INTEGER PRIMARY KEY AUTO_INCREMENT,
    Sport VARCHAR(100)
);


CREATE TABLE IF NOT EXISTS Evenement (
    IdEvenement INTEGER PRIMARY KEY AUTO_INCREMENT,
    Titre VARCHAR(100),
    Sport VARCHAR(100),
    Type VARCHAR(100),
    Lieu VARCHAR(100),
    Date DATE
);


CREATE TABLE IF NOT EXISTS Athlete (
    IdAthlete INTEGER PRIMARY KEY AUTO_INCREMENT,
    Prenom VARCHAR(100),
    Nom VARCHAR(100),
    Naissance DATE,
    Pays VARCHAR(100),
    Sexe VARCHAR(100),
    Sport_IdSport INT NOT NULL,
    INDEX fk_Athlete_Sport_idx (Sport_IdSport ASC),
    CONSTRAINT fk_Athlete_Sport FOREIGN KEY (Sport_IdSport) REFERENCES Sport (IdSport) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS Resultat (
    IdResultat INTEGER PRIMARY KEY AUTO_INCREMENT,
    Temps VARCHAR(100),
    Score VARCHAR(100),
    Gagnant VARCHAR(100),
    Medailles VARCHAR(100),
    Sport_IdSport INT NOT NULL,
    Evenement_IdEvenement INT NOT NULL,
    INDEX fk_Resultat_Sport_idx (Sport_IdSport ASC),
    CONSTRAINT fk_Resultat_Sport FOREIGN KEY (Sport_IdSport) REFERENCES Sport (IdSport) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX fk_Resultat_Evenement_idx (Evenement_IdEvenement ASC),
    CONSTRAINT fk_Resultat_Evenement FOREIGN KEY (Evenement_IdEvenement) REFERENCES Evenement (IdEvenement) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS Utilisateur (
    IdUtilisateur INTEGER PRIMARY KEY AUTO_INCREMENT,
    Nom VARCHAR(100),
    Prenom VARCHAR(100),
    Naissance DATE,
    NomUtilisateur VARCHAR(100),
    MDP VARCHAR(100),
    Role INTEGER
);


CREATE TABLE IF NOT EXISTS Evenement_Athlete (
    Evenement_Id INT NOT NULL,
    Athlete_Id INT NOT NULL,
    PRIMARY KEY (Evenement_Id, Athlete_Id),
    CONSTRAINT fk_Evenement_Athlete_Evenement FOREIGN KEY (Evenement_Id) REFERENCES Evenement (IdEvenement) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_Evenement_Athlete_Athlete FOREIGN KEY (Athlete_Id) REFERENCES Athlete (IdAthlete) ON DELETE CASCADE ON UPDATE CASCADE
);


INSERT INTO Sport(Sport) VALUES
("Athlétisme"), -- ID 1
("Aviron"), -- ID 2
("Badminton"), -- ID 3
("Basketball"), -- ID 4
("Boxe"), -- ID 5
("Canoë-Kayak"), -- ID 6
("Cyclisme"), -- ID 7
("Équitation"), -- ID 8
("Escalade"), -- ID 9
("Escrime"), -- ID 10
("Football"), -- ID 11
("Golf"), -- ID 12
("Gymnastique"), -- ID 13
("Haltérophilie"), -- ID 14
("Handball"), -- ID 15
("Hockey sur gazon"), -- ID 16
("Judo"), -- ID 17
("Lutte"), -- ID 18
("Natation"), -- ID 19
("Pentathlon moderne"), -- ID 20
("Rugby à sept"), -- ID 21
("Skateboard"), -- ID 22
("Surf"), -- ID 23
("Taekwondo"), -- ID 24
("Tennis"), -- ID 25
("Tennis de table"), -- ID 26
("Tir sportif"), -- ID 27
("Tir à l'arc"), -- ID 28
("Triathlon"), -- ID 29
("Voile"), -- ID 30
("Volleyball"), -- ID 31
("Water-polo"); -- ID 32


INSERT INTO Athlete(Prenom, Nom, Naissance, Pays, Sexe, Sport_IdSport) VALUES
-- Athlétisme (ID 1)
("Usain", "Bolt", "1986-08-21", "Jamaïque", "Homme", 1), -- ID 1
("Allyson", "Felix", "1985-11-18", "États-Unis", "Femme", 1), -- ID 2
("Eliud", "Kipchoge", "1984-11-05", "Kenya", "Homme", 1),
-- Aviron (ID 2)
("Steve", "Redgrave", "1962-03-23", "Royaume-Uni", "Homme", 2),
("Ekaterina", "Karsten", "1972-06-02", "Biélorussie", "Femme", 2),
-- Badminton (ID 3)
("Carolina", "Marin", "1993-06-15", "Espagne", "Femme", 3),
("Lin", "Dan", "1983-10-14", "Chine", "Homme", 3),
-- Basketball (ID 4)
("Kevin", "Durant", "1988-09-29", "États-Unis", "Homme", 4),
("LeBron", "James", "1984-12-30", "États-Unis", "Homme", 4),
("Sue", "Bird", "1980-10-16", "États-Unis", "Femme", 4), -- ID 10
-- Boxe (ID 5)
("Katie", "Taylor", "1986-07-02", "Irlande", "Femme", 5),
("Anthony", "Joshua", "1989-10-15", "Royaume-Uni", "Homme", 5),
-- Canoë-Kayak (ID 6)
("Tony", "Estanguet", "1978-05-06", "France", "Homme", 6),
("Birgit", "Fischer", "1962-02-25", "Allemagne", "Femme", 6),
-- Cyclisme (ID 7)
("Chris", "Froome", "1985-05-20", "Royaume-Uni", "Homme", 7),
("Marianne", "Vos", "1987-05-13", "Pays-Bas", "Femme", 7),
("Peter", "Sagan", "1990-01-26", "Slovaquie", "Homme", 7),
-- Équitation (ID 8)
("Charlotte", "Dujardin", "1985-07-13", "Royaume-Uni", "Femme", 8),
("Michael", "Jung", "1982-07-31", "Allemagne", "Homme", 8),
-- Escalade (ID 9)
("Janja", "Garnbret", "1999-03-12", "Slovénie", "Femme", 9), -- ID 20
("Adam", "Ondra", "1993-02-05", "République tchèque", "Homme", 9),
-- Escrime (ID 10)
("Ruben", "Limardo", "1985-08-03", "Venezuela", "Homme", 10),
("Valentina", "Vezzali", "1974-02-14", "Italie", "Femme", 10),
-- Football (ID 11)
("Lionel", "Messi", "1987-06-24", "Argentine", "Homme", 11),
("Marta", "Vieira da Silva", "1986-02-19", "Brésil", "Femme", 11),
("Cristiano", "Ronaldo", "1985-02-05", "Portugal", "Homme", 11),
-- Golf (ID 12)
("Rory", "McIlroy", "1989-05-04", "Irlande", "Homme", 12),
("Nelly", "Korda", "1998-07-28", "États-Unis", "Femme", 12),
-- Gymnastique (ID 13)
("Simone", "Biles", "1997-03-14", "États-Unis", "Femme", 13),
("Kohei", "Uchimura", "1989-01-03", "Japon", "Homme", 13), -- ID 30
("Nadia", "Comăneci", "1961-11-12", "Roumanie", "Femme", 13),
-- Haltérophilie (ID 14)
("Lasha", "Talakhadze", "1993-10-02", "Géorgie", "Homme", 14),
("Li", "Wenwen", "2000-03-05", "Chine", "Femme", 14),
-- Handball (ID 15)
("Nikola", "Karabatic", "1984-04-11", "France", "Homme", 15),
("Cristina", "Neagu", "1988-08-26", "Roumanie", "Femme", 15),
-- Hockey sur gazon (ID 16)
("Luciana", "Aymar", "1977-08-10", "Argentine", "Femme", 16),
("Teun", "de Nooijer", "1976-03-22", "Pays-Bas", "Homme", 16),
-- Judo (ID 17)
("Teddy", "Riner", "1989-04-07", "France", "Homme", 17),
("Ryoko", "Tani", "1975-09-06", "Japon", "Femme", 17),
-- Lutte (ID 18)
("Aleksandr", "Karelin", "1967-09-19", "Russie", "Homme", 18), -- ID 40
("Kaori", "Icho", "1984-06-13", "Japon", "Femme", 18),
-- Natation (ID 19)
("Michael", "Phelps", "1985-06-30", "États-Unis", "Homme", 19),
("Katie", "Ledecky", "1997-03-17", "États-Unis", "Femme", 19),
("Caeleb", "Dressel", "1996-08-16", "États-Unis", "Homme", 19),
-- Pentathlon moderne (ID 20)
("David", "Svoboda", "1985-03-19", "République tchèque", "Homme", 20),
("Laura", "Asadauskaitė", "1984-02-28", "Lituanie", "Femme", 20),
-- Rugby à sept (ID 21)
("Perry", "Baker", "1986-06-29", "États-Unis", "Homme", 21),
("Charlotte", "Caslick", "1995-03-09", "Australie", "Femme", 21),
-- Skateboard (ID 22)
("Nyjah", "Huston", "1994-11-30", "États-Unis", "Homme", 22),
("Sky", "Brown", "2008-07-12", "Royaume-Uni", "Femme", 22),
-- Surf (ID 23)
("Carissa", "Moore", "1992-08-27", "États-Unis", "Femme", 23), -- ID 50
("Gabriel", "Medina", "1993-12-22", "Brésil", "Homme", 23), -- ID 51
("John John", "Florence", "1992-10-18", "États-Unis", "Homme", 23), -- ID 52
-- Taekwondo (ID 24)
("Hadi", "Saei", "1976-06-10", "Iran", "Homme", 24),
("Jade", "Jones", "1993-03-21", "Royaume-Uni", "Femme", 24),
-- Tennis (ID 25)
("Rafael", "Nadal", "1986-06-03", "Espagne", "Homme", 25), -- ID 55
("Serena", "Williams", "1981-09-26", "États-Unis", "Femme", 25),
("Novak", "Djokovic", "1987-05-22", "Serbie", "Homme", 25), -- ID 57
-- Tennis de table (ID 26)
("Ma", "Long", "1988-10-20", "Chine", "Homme", 26),
("Ding", "Ning", "1990-06-20", "Chine", "Femme", 26),
-- Tir sportif (ID 27)
("Vincent", "Hancock", "1989-03-19", "États-Unis", "Homme", 27), -- ID 60
("Jin", "Jong-oh", "1979-09-24", "Corée du Sud", "Homme", 27),
-- Tir à l'arc (ID 28)
("Kim", "Woojin", "1992-06-20", "Corée du Sud", "Homme", 28),
("Lisa", "Unruh", "1988-04-12", "Allemagne", "Femme", 28),
-- Triathlon (ID 29)
("Alistair", "Brownlee", "1988-04-23", "Royaume-Uni", "Homme", 29),
("Flora", "Duffy", "1987-09-30", "Bermudes", "Femme", 29),
-- Voile (ID 30)
("Hannah", "Mills", "1988-02-29", "Royaume-Uni", "Femme", 30),
("Santiago", "Lange", "1961-09-22", "Argentine", "Homme", 30),
-- Volleyball (ID 31)
("Kerri", "Walsh Jennings", "1978-08-15", "États-Unis", "Femme", 31),
("Giba", "1976-12-23", "Brésil", "Homme", 31),
-- Water-polo (ID 32)
("Maggie", "Steffens", "1993-06-04", "États-Unis", "Femme", 32), -- ID 70
("Tibor", "Benedek", "1972-07-12", "Hongrie", "Homme", 32);


INSERT INTO Evenement(Titre, Sport, Type, Lieu, Date) VALUES
("Cérémonie d'ouverture", "Autres", "Cérémonie", "Paris", "2024-07-26"), -- ID 1
("Cérémonie de fermeture", "Autres", "Cérémonie", "Stade de France", "2024-08-11"), -- ID 2
("Tour 1 Match 1", "Tennis", "Évènement sportif", "Paris", "2024-08-03"), -- ID 3
("Tour 1 Série 1", "Surf", "Évènement sportif", "Tahiti", "2024-07-27"); -- ID 4


INSERT INTO Evenement_Athlete(Evenement_Id, Athlete_Id) VALUES
(3, 56),
(3, 58),
(4, 52),
(4,53);


INSERT INTO Utilisateur(Nom, Prenom, Naissance, NomUtilisateur, MDP, Role) VALUES
("Admin", "Admin", "2000-01-01", "Admin", "Admin", 1);