package Model;

import java.sql.Date;
import java.time.LocalDateTime;

public class Evenement {
    private int IdEvenement;
    private String Titre;
    private String Type;
    private String Lieu;
    private String Date_Heure;

    public Evenement(int idEvenement, String titre, String type, String lieu, String date_Heure) {
        IdEvenement = idEvenement;
        Titre = titre;
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

    public String getTitre() {
        return Titre;
    }
    public void setTitre(String titre) {
        Titre = titre;
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

    public String getDate_Heure() {
        return Date_Heure;
    }
    public void setDate_Heure(String date_Heure) {
        Date_Heure = date_Heure;
    }
}
