package Model;

import java.util.ArrayList;
import java.sql.*;

public class ResultatDAO {
    public ArrayList<Resultat> getAllResultats(Connection connection) {
        ArrayList<Resultat> resultats = new ArrayList<>();

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            String query = "SELECT * FROM resultat";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("IdResultat");
                String gagnant = resultSet.getString("Gagnant");
                int idsport = resultSet.getInt("Sport_IdSport");
                int idevenement = resultSet.getInt("Evenement_IdEvenement");
                Resultat resultat = new Resultat(id, gagnant, idsport, idevenement);
                resultats.add(resultat);
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

        return resultats;
    }
}
