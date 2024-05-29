package Model;

public class Medailles {
    private int idMedailles;
    private String Type;
    private String Pays;

    public Medailles(int idMedailles, String type, String pays) {
        this.idMedailles = idMedailles;
        Type = type;
        Pays = pays;
    }

    public int getIdMedailles() {
        return idMedailles;
    }
    public void setIdMedailles(int idMedailles) {
        this.idMedailles = idMedailles;
    }

    public String getType() {
        return Type;
    }
    public void setType(String type) {
        Type = type;
    }

    public String getPays() {
        return Pays;
    }
    public void setPays(String pays) {
        Pays = pays;
    }
}
