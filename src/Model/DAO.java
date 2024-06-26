package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAO {
    private ArrayList<Sport> sports = new ArrayList<>();
    private ArrayList<Evenement> evenements = new ArrayList<>();
    private ArrayList<Athlete> athletes = new ArrayList<>();
    private ArrayList<Resultat> resultats = new ArrayList<>();
    private ArrayList<Medailles> medailles = new ArrayList<>();






    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/jo2024";
        String user = "root";
        String password = "";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw e;
        }
        return connection;
    }

    public void UploadData() throws SQLException {
        ClearData();
        Connection connection = getConnection();

        if (connection != null) {
            SportDAO sportDAO = new SportDAO();
            sports.addAll(sportDAO.getAllSports(connection));

            EvenementDAO evenementDAO = new EvenementDAO();
            evenements.addAll(evenementDAO.getAllEvenements(connection));

            AthleteDAO athleteDAO = new AthleteDAO();
            athletes.addAll(athleteDAO.getAllAthletes(connection));

            ResultatDAO resultatDAO = new ResultatDAO();
            resultats.addAll(resultatDAO.getAllResultats(connection));

            MedaillesDAO medaillesDAO = new MedaillesDAO();
            medailles.addAll(medaillesDAO.getAllMedailles(connection));

            //printDB();
        }
    }

    void printDB() {
        System.out.println("Sports");
        for (Sport sport : sports) {
            System.out.println(sport.getIdSport() + "  " + sport.getSport());
        }
        System.out.println("");
        System.out.println("Evenements");
        for (Evenement evenement : evenements) {
            System.out.println(evenement.getIdEvenement() + "  " + evenement.getTitre()  + "  " + evenement.getSport() + "  " + evenement.getType() + "  " + evenement.getLieu() + "  " + evenement.getDate());
        }
        System.out.println("");
        System.out.println("Athletes");
        for (Athlete athlete : athletes) {
            System.out.println(athlete.getIdAthlete() + " " + athlete.getPrenom() + " " + athlete.getNom() + " " + athlete.getNaissance() + " " + athlete.getPays() + " " + athlete.getSexe() + " " + athlete.getNomSport());
        }
        System.out.println("");
        System.out.println("Resultats");
        for (Resultat resultat : resultats) {
            System.out.println(resultat.getIdResultat() + " " + resultat.getGagnant() + " " + resultat.getIdSport() + " " + resultat.getIdEvenement());
        }
        System.out.println("");
        System.out.println("Médailles");
        for (Medailles medailles : medailles) {
            System.out.println(medailles.getIdMedailles() + " " + medailles.getType() + " " + medailles.getPays());
        }
    }

    public void refreshDatabase() throws SQLException {
        UploadData();
    }

    private void ClearData() {
        sports.clear();
        evenements.clear();
        athletes.clear();
        resultats.clear();
        medailles.clear();
    }





    public ArrayList<Sport> getSports() {
        return sports;
    }
    public ArrayList<Evenement> getEvenements() {
        return evenements;
    }
    public ArrayList<Athlete> getAthletes() {
        return athletes;
    }
    public ArrayList<Resultat> getResultats() {
        return resultats;
    }
    public ArrayList<Medailles> getMedailles() {
        return medailles;
    }
}
