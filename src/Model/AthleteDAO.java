package Model;

import java.util.ArrayList;
import java.sql.*;

public class AthleteDAO {
    public ArrayList<Athlete> getAllAthletes(Connection connection) {
        ArrayList<Athlete> athletes = new ArrayList<>();

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            String query = "SELECT Athlete.*, Sport.Sport AS NomSport FROM Athlete " + "JOIN Sport ON Athlete.Sport_IdSport = Sport.IdSport";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("idAthlete");
                String prenom = resultSet.getString("Prenom");
                String nom = resultSet.getString("Nom");
                Date naissance = resultSet.getDate("Naissance");
                String pays = resultSet.getString("Pays");
                String sexe = resultSet.getString("Sexe");
                String sport = resultSet.getString("NomSport");
                Athlete athlete = new Athlete(id, prenom, nom, naissance, pays, sexe, sport);
                athletes.add(athlete);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return athletes;
    }
}
