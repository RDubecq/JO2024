package Controller;

import Model.DAO;
import Model.Sport;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

            countColumn.setCellValueFactory(data -> {
                int athleteCount = 0;
                try {
                    athleteCount = getAthleteCountForSport(data.getValue());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return new SimpleIntegerProperty(athleteCount).asObject();
            });

            ObservableList<Sport> observableList = FXCollections.observableArrayList();
            observableList.addAll(sports);
            SportsTable.setItems(observableList);
        } else {
            System.out.println("BUG");
        }
    }

    private void AlertMessage(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }





    // NOMBRE D'ATHLETES PAR SPORT
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





    // EXPORT
    public void ExportToCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.append("Sport\n");

                for (Sport sport : sports) {
                    writer.append(sport.getSport()).append("\n");
                }

                writer.flush();
                AlertMessage(Alert.AlertType.INFORMATION, "Export terminé", "Export au format CSV terminé.", "");
            } catch (IOException e) {
                System.err.println("An error occurred while writing the CSV file: " + e.getMessage());
            }
        }
    }
}
