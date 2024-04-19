package Model;

import java.sql.Date;
import java.time.LocalDateTime;

public class Evenement {
    private int IdEvenement;
    private String Nom;
    private String Type;
    private String Lieu;
    private Date Date_Heure;

    public Evenement(int idEvenement, String nom, String type, String lieu, Date date_Heure) {
        IdEvenement = idEvenement;
        Nom = nom;
        Type = type;
        Lieu = lieu;
        Date_Heure = date_Heure;
    }

    public int getIdEvenement() {
        return IdEvenement;
    }
    public void setIdEvenement(int idEvenement) {
        IdEvenement = idEvenement;
    }

    public String getNom() {
        return Nom;
    }
    public void setNom(String nom) {
        Nom = nom;
    }

    public String getType() {
        return Type;
    }
    public void setType(String type) {
        Type = type;
    }

    public String getLieu() {
        return Lieu;
    }
    public void setLieu(String lieu) {
        Lieu = lieu;
    }

    public Date getDate_Heure() {
        return Date_Heure;
    }
    public void setDate_Heure(Date date_Heure) {
        Date_Heure = date_Heure;
    }
}
