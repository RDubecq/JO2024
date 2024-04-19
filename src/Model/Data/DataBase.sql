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
INSERT INTO Sport(Sport) VALUES ("Athlétisme");
INSERT INTO Sport(Sport) VALUES ("Rugby");
INSERT INTO Sport(Sport) VALUES ("Skate");
INSERT INTO Sport(Sport) VALUES ("Surf");

INSERT INTO Evenement(Nom, Type, Lieu, Date_Heure) VALUES ("Cérémonie d'ouverture", "Cérémonie", "Paris", "2024-07-26 19:30:00");
INSERT INTO Evenement(Nom, Type, Lieu, Date_Heure) VALUES ("Cérémonie de fermeture", "Cérémonie", "Stade de France", '2024-08-11 21:00:00');
INSERT INTO Evenement(Nom, Type, Lieu, Date_Heure) VALUES ("France-Allemagne", "Football", "Stade de France", '2024-08-13 21:00:00');

INSERT INTO Resultat(Temps, Score, Gagnant, Medailles, Sport_IdSport, Evenement_IdEvenement) VALUES ("1", "1", "1", "1", 1, 1);

INSERT INTO Athlete(Prenom, Nom, Naissance, Pays, Sexe, Sport_IdSport) VALUES ("Lionel", "Messi", "1987-06-24", "Argentine", "Homme", 1);
INSERT INTO Athlete(Prenom, Nom, Naissance, Pays, Sexe, Sport_IdSport)
VALUES
    ("Cristiano", "Ronaldo", "1985-02-05", "Portugal", "Homme", 1),
    ("Neymar", "Jr", "1992-02-05", "Brésil", "Homme", 1),
    ("Megan", "Rapinoe", "1985-07-05", "États-Unis", "Femme", 1),
    ("Luka", "Modric", "1985-09-09", "Croatie", "Homme", 1),
    ("Ada", "Hegerberg", "1995-07-10", "Norvège", "Femme", 1)
    ;

INSERT INTO Athlete(Prenom, Nom, Naissance, Pays, Sexe, Sport_IdSport)
VALUES
    ("Usain", "Bolt", "1986-08-21", "Jamaïque", "Homme", 2),
    ("Allyson", "Felix", "1985-11-18", "États-Unis", "Femme", 2),
    ("Wayde", "Van Niekerk", "1992-07-15", "Afrique du Sud", "Homme", 2),
    ("Dafne", "Schippers", "1992-06-15", "Pays-Bas", "Femme", 2),
    ("Eliud", "Kipchoge", "1984-11-05", "Kenya", "Homme", 2)
    ;

INSERT INTO Athlete(Prenom, Nom, Naissance, Pays, Sexe, Sport_IdSport)
VALUES
    ("Jonny", "Wilkinson", "1979-05-25", "Angleterre", "Homme", 3),
    ("Richie", "McCaw", "1980-12-31", "Nouvelle-Zélande", "Homme", 3),
    ("Portia", "Woodman", "1991-03-12", "Nouvelle-Zélande", "Femme", 3),
    ("Sébastien", "Chabal", "1977-12-08", "France", "Homme", 3),
    ("Magali", "Harvey", "1989-10-05", "Canada", "Femme", 3)
    ;

INSERT INTO Athlete(Prenom, Nom, Naissance, Pays, Sexe, Sport_IdSport)
VALUES
    ("Tony", "Hawk", "1968-05-12", "États-Unis", "Homme", 4),
    ("Leticia", "Bufoni", "1993-04-13", "Brésil", "Femme", 4),
    ("Nyjah", "Huston", "1994-11-30", "États-Unis", "Homme", 4),
    ("Pamela", "Rosa", "1999-09-19", "Brésil", "Femme", 4),
    ("Ryan", "Sheckler", "1989-12-30", "États-Unis", "Homme", 4)
    ;

INSERT INTO Athlete(Prenom, Nom, Naissance, Pays, Sexe, Sport_IdSport)
VALUES
    ("Kelly", "Slater", "1972-02-11", "États-Unis", "Homme", 5),
    ("Stephanie", "Gilmore", "1988-01-29", "Australie", "Femme", 5),
    ("Gabriel", "Medina", "1993-12-22", "Brésil", "Homme", 5),
    ("Carissa", "Moore", "1992-08-27", "États-Unis", "Femme", 5),
    ("John", "John Florence", "1992-10-18", "États-Unis", "Homme", 5)
    ;
