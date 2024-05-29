package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MedaillesDAO {
    public ArrayList<Medailles> getAllMedailles(Connection connection) {
        ArrayList<Medailles> medailles = new ArrayList<>();

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            String query = "SELECT * FROM medailles";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("IdMedailles");
                String type = resultSet.getString("Type");
                String pays = resultSet.getString("Pays");
                Medailles medaille = new Medailles(id, type, pays);
                medailles.add(medaille);
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

        return medailles;
    }
}
