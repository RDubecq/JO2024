package Model;

public class Resultat {
    private int idResultat;
    private String Temps;
    private String Score;
    private String Gagnant;
    private String Medailles;
    private int idSport;
    private int idEvenement;

    public Resultat(int idResultat, String temps, String score, String gagnant, String medailles, int idSport, int idEvenement) {
        this.idResultat = idResultat;
        Temps = temps;
        Score = score;
        Gagnant = gagnant;
        Medailles = medailles;
        this.idSport = idSport;
        this.idEvenement = idEvenement;
    }

    public int getIdResultat() {
        return idResultat;
    }
    public void setIdResultat(int idResultat) {
        this.idResultat = idResultat;
    }

    public String getTemps() {
        return Temps;
    }
    public void setTemps(String temps) {
        Temps = temps;
    }

    public String getScore() {
        return Score;
    }
    public void setScore(String score) {
        Score = score;
    }

    public String getGagnant() {
        return Gagnant;
    }
    public void setGagnant(String gagnant) {
        Gagnant = gagnant;
    }

    public String getMedailles() {
        return Medailles;
    }
    public void setMedailles(String medailles) {
        Medailles = medailles;
    }

    public int getIdSport() {
        return idSport;
    }
    public void setIdSport(int idSport) {
        this.idSport = idSport;
    }

    public int getIdEvenement() {
        return idEvenement;
    }
    public void setIdEvenement(int idEvenement) {
        this.idEvenement = idEvenement;
    }
}
