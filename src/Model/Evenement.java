package Model;

import java.sql.Date;
import java.time.LocalDateTime;

public class Evenement {
    private int IdEvenement;
    private String Titre;
    private String Sport;
    private String Type;
    private String Lieu;
    private String Date;

    public Evenement(int idEvenement, String titre, String sport, String type, String lieu, String date) {
        IdEvenement = idEvenement;
        Titre = titre;
        Sport = sport;
        Type = type;
        Lieu = lieu;
        Date = date;
    }

    public int getIdEvenement() {
        return IdEvenement;
    }
    public void setIdEvenement(int idEvenement) {
        IdEvenement = idEvenement;
    }

    public String getTitre() {
        return Titre;
    }
    public void setTitre(String titre) {
        Titre = titre;
    }

    public String getSport() {
        return Sport;
    }
    public void setSport(String sport) {
        Sport = sport;
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

    public String getDate() {
        return Date;
    }
    public void setDate(String date) {
        Date = date;
    }
}
