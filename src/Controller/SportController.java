package Controller;

import Model.Athlete;
import Model.DAO;
import Model.Sport;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleIntegerProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SportController {
    @FXML
    private TableView SportsTable;

    ArrayList<Sport> sports = new ArrayList<>();
    private DAO dao = new DAO();




    public void initData(DAO dao) {
        sports.addAll(dao.getSports());
        ObservableList<Sport> observableList = FXCollections.observableArrayList(sports);
        SportsTable.setItems(observableList);
    }

    public void DisplayData() {
        if (SportsTable != null) {
            TableColumn<Sport, String> sportColumn = (TableColumn<Sport, String>) SportsTable.getColumns().get(0);
            sportColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSport()));

            TableColumn<Sport, Integer> countColumn = (TableColumn<Sport, Integer>) SportsTable.getColumns().get(1);
            /*
            countColumn.setCellValueFactory(data -> {
                int athleteCount = 0;
                try {
                    athleteCount = getAthleteCountForSport(data.getValue());
                } catch (SQLException e) {
                    e.printStackTrace(); // Gérer l'exception selon vos besoins
                }
                return new SimpleIntegerProperty(athleteCount).asObject();
            });

             */

            ObservableList<Sport> observableList = FXCollections.observableArrayList();
            observableList.addAll(sports);
            SportsTable.setItems(observableList);
        } else {
            System.out.println("BUG");
        }
    }



    private void RefreshTable() throws SQLException {
        dao.refreshDatabase();
        initData(dao);

        for (Sport sport : sports) {
            //System.out.println(sport.getSport());
        }
        DisplayData();
    }

    private int getAthleteCountForSport(Sport sport) throws SQLException {
        int athleteCount = 0;

        String query = "SELECT COUNT(*) AS AthleteCount FROM Athlete WHERE Sport_IdSport = ?";
        try (PreparedStatement statement = dao.getConnection().prepareStatement(query)) {
            statement.setInt(1, sport.getIdSport());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                athleteCount = resultSet.getInt("AthleteCount");
            }
        }

        return athleteCount;
    }
}
