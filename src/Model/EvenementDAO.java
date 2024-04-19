package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.*;

public class EvenementDAO {
    public ArrayList<Evenement> getAllEvenements(Connection connection) {
        ArrayList<Evenement> evenements = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            String query = "SELECT * FROM evenement";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("idEvenement");
                String nom = resultSet.getString("Nom");
                String type = resultSet.getString("Type");
                String lieu = resultSet.getString("Lieu");
                Date date_heure = resultSet.getDate("Date_Heure");
                Evenement evenement = new Evenement(id, nom, type, lieu, date_heure);
                evenements.add(evenement);
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

        return evenements;
    }
}
