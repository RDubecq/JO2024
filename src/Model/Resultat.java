package Model;

public class Resultat {
    private int idResultat;
    private String Gagnant;
    private int idSport;
    private int idEvenement;

    public Resultat(int idResultat, String gagnant, int idSport, int idEvenement) {
        this.idResultat = idResultat;
        Gagnant = gagnant;
        this.idSport = idSport;
        this.idEvenement = idEvenement;
    }

    public int getIdResultat() {
        return idResultat;
    }
    public void setIdResultat(int idResultat) {
        this.idResultat = idResultat;
    }

    public String getGagnant() {
        return Gagnant;
    }
    public void setGagnant(String gagnant) {
        Gagnant = gagnant;
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
