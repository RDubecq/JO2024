package Controller;

import Model.DAO;
import Model.Evenement;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class CalendrierController {
    @FXML
    private DatePicker Date;

    @FXML
    private TableView CalendrierTable;

    private ArrayList<Evenement> evenements = new ArrayList<>();
    private DAO dao = new DAO();





    public void initData(DAO dao) {
        evenements.clear();
        evenements.addAll(dao.getEvenements());

        /*
        for (Evenement evenement : evenements) {
            System.out.println(evenement.getTitre());
        }

         */
    }

    private void AlertMessage(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }





    // CALENDRIER
    public void GetDate() throws SQLException {
        LocalDate selectedDate = Date.getValue();

        if (selectedDate != null) {
            LocalDate startDate = LocalDate.of(2024, 7, 24);
            LocalDate endDate = LocalDate.of(2024, 8, 11);

            if (selectedDate.isBefore(startDate) || selectedDate.isAfter(endDate)) {
                AlertMessage(Alert.AlertType.ERROR, "Erreur", "Date invalide", "Veuillez choisir une date entre le 24 juillet 2024 et le 11 août 2024 inclus.");
            } else {
                String date = String.valueOf(selectedDate);
                GetEvenementsByDate(date);
            }
        } else {
            AlertMessage(Alert.AlertType.ERROR, "Erreur", "Merci de choisir une date.", "Merci de choisir une date.");
        }
    }

    public void GetEvenementsByDate(String date) throws SQLException {
        Connection connection = dao.getConnection();
        String query = "SELECT * FROM Evenement WHERE Date = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, date);
            ResultSet resultSet = statement.executeQuery();

            CalendrierTable.getItems().clear();
            CalendrierTable.getColumns().clear();

            TableColumn<ObservableList<String>, String> titreColumn = new TableColumn<>("Titre");
            titreColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(0)));
            titreColumn.setPrefWidth(200);

            TableColumn<ObservableList<String>, String> sportColumn = new TableColumn<>("Sport");
            sportColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(1)));
            sportColumn.setPrefWidth(200);

            TableColumn<ObservableList<String>, String> typeColumn = new TableColumn<>("Type");
            typeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(2)));
            typeColumn.setPrefWidth(200);

            TableColumn<ObservableList<String>, String> lieuColumn = new TableColumn<>("Lieu");
            lieuColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(3)));
            lieuColumn.setPrefWidth(200);

            CalendrierTable.getColumns().addAll(titreColumn, sportColumn, typeColumn, lieuColumn);

            boolean hasEvents = false;

            while (resultSet.next()) {
                hasEvents = true;

                ObservableList<String> rowData = FXCollections.observableArrayList();
                rowData.add(resultSet.getString("Titre"));
                rowData.add(resultSet.getString("Sport"));
                rowData.add(resultSet.getString("Type"));
                rowData.add(resultSet.getString("Lieu"));

                CalendrierTable.getItems().add(rowData);
            }

            if (!hasEvents) {
                AlertMessage(Alert.AlertType.INFORMATION, "Information", "Aucun événement", "Il n'y a aucun événement pour la date spécifiée.");
            }
        }
    }
}
