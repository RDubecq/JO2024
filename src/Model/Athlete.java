package Model;

import java.util.Date;

public class Athlete {
    private int IdAthlete;
    private String Prenom;
    private String Nom;
    private Date Naissance;
    private String Pays;
    private String Sexe;
    private int IdSport;

    public Athlete(int idAthlete, String prenom, String nom, Date naissance, String pays, String sexe, int idSport) {
        IdAthlete = idAthlete;
        Prenom = prenom;
        Nom = nom;
        Naissance = naissance;
        Pays = pays;
        Sexe = sexe;
        this.IdSport = IdSport;
    }

    public int getIdAthlete() {
        return IdAthlete;
    }
    public void setIdAthlete(int idAthlete) {
        IdAthlete = idAthlete;
    }

    public String getPrenom() {
        return Prenom;
    }
    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getNom() {
        return Nom;
    }
    public void setNom(String nom) {
        Nom = nom;
    }

    public Date getNaissance() {
        return Naissance;
    }
    public void setNaissance(Date naissance) {
        Naissance = naissance;
    }

    public String getPays() {
        return Pays;
    }
    public void setPays(String pays) {
        Pays = pays;
    }

    public String getSexe() {
        return Sexe;
    }
    public void setSexe(String sexe) {
        Sexe = sexe;
    }

    public int getIdSport() {
        return IdSport;
    }
    public void setIdSport(int IdSport) {
        this.IdSport = IdSport;
    }
}
