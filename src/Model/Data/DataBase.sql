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


INSERT INTO Sport(Sport) VALUES ("Football");
INSERT INTO Sport(Sport) VALUES ("Rugby");
INSERT INTO Sport(Sport) VALUES ("Tennis");
INSERT INTO Sport(Sport) VALUES ("Basket");

INSERT INTO Evenement(Nom, Type, Lieu, Date_Heure) VALUES ("Cérémonie d'ouverture", "Cérémonie", "Paris", "2024-07-26 19:30:00");
INSERT INTO Evenement(Nom, Type, Lieu, Date_Heure) VALUES ("Cérémonie de fermeture", "Cérémonie", "Stade de France", '2024-08-11 21:00:00');

INSERT INTO Athlete(Prenom, Nom, Naissance, Pays, Sexe, Sport_IdSport) VALUES ("Rafael", "Dubecq", "2003-07-02", "France", "Homme", 1);

INSERT INTO Resultat(Temps, Score, Gagnant, Medailles, Sport_IdSport, Evenement_IdEvenement) VALUES ("1", "1", "1", "1", 1, 1);