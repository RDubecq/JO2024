package Model;

public class Sport {
    private int IdSport;
    private String Sport;

    public Sport(int idSport, String sport) {
        IdSport = idSport;
        Sport = sport;
    }

    public int getIdSport() {
        return IdSport;
    }
    public void setIdSport(int idSport) {
        IdSport = idSport;
    }

    public String getSport() {
        return Sport;
    }
    public void setSport(String sport) {
        Sport = sport;
    }
}
