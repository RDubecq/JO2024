CREATE TABLE IF NOT EXISTS Sport (
    IdSport INTEGER PRIMARY KEY AUTO_INCREMENT,
    Sport VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS Evenement (
    IdEvenement INTEGER PRIMARY KEY AUTO_INCREMENT,
    Nom VARCHAR(100),
    Type VARCHAR(100),
    Lieu VARCHAR(100),
    Date_Heure DATETIME
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


INSERT INTO Evenement(Nom, Type, Lieu, Date_Heure) VALUES ("Cérémonie d'ouverture", "Cérémonie", "Paris", "2024-07-26 19:30:00");
INSERT INTO Evenement(Nom, Type, Lieu, Date_Heure) VALUES ("Cérémonie de fermeture", "Cérémonie", "Stade de France", '2024-08-11 21:00:00');
INSERT INTO Evenement(Nom, Type, Lieu, Date_Heure) VALUES ("France-Allemagne", "Football", "Stade de France", '2024-08-13 21:00:00');

INSERT INTO Athlete(Prenom, Nom, Naissance, Pays, Sexe, Sport_IdSport) VALUES
("Lionel", "Messi", "1987-06-24", "Argentine", "Homme", 11),
("Rafael", "Dubecq", "2003-07-02", "France", "Homme", 23),
("Monstre", "Coucou", "2000-01-01", "France", "Homme", 17);

