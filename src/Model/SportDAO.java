package Model;

import java.util.ArrayList;
import java.sql.*;

public class SportDAO {
    public ArrayList<Sport> getAllSports(Connection connection) {
        ArrayList<Sport> sports = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            String query = "SELECT * FROM sport";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("idSport");
                String nom = resultSet.getString("Sport");
                Sport sport = new Sport(id, nom);
                sports.add(sport);
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

        return sports;
    }
}
