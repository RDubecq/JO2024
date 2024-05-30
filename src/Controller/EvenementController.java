package Controller;

import Model.Athlete;
import Model.DAO;
import Model.Evenement;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class EvenementController {
    @FXML
    private TableView EvenementsTable;
    @FXML
    private TableView ParticipantsTable;

    @FXML
    private AnchorPane AddEvenement;
    @FXML
    private AnchorPane DeleteEvenement;
    @FXML
    private AnchorPane AddParticipant;

    @FXML
    private TextField AddEvenement_Titre;
    @FXML
    private ComboBox AddEvenement_Sport;
    @FXML
    private ComboBox AddEvenement_Type;
    @FXML
    private ComboBox AddEvenement_Lieu;
    @FXML
    private DatePicker AddEvenement_Date;

    @FXML
    private TextField DeleteEvenement_Titre;
    @FXML
    private ComboBox DeleteEvenement_Sport;
    @FXML
    private ComboBox DeleteEvenement_Type;
    @FXML
    private ComboBox DeleteEvenement_Lieu;
    @FXML
    private DatePicker DeleteEvenement_Date;

    @FXML
    private Label titreLabel;
    @FXML
    private Label sportLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label lieuLabel;
    @FXML
    private Label dateLabel;

    @FXML
    private TextField AddParticipant_Athlete;

    private int IdEvenement;
    public void setIdEvenement(int idEvenement) {
        this.IdEvenement = idEvenement;
    }

    private ArrayList<Evenement> evenements = new ArrayList<>();
    private ArrayList<Athlete> athletes = new ArrayList<>();
    private DAO dao = new DAO();





    public void initData(DAO dao) {
        evenements.clear();
        evenements.addAll(dao.getEvenements());
        athletes.clear();
        athletes.addAll(dao.getAthletes());
    }

    public void DisplayData() {
        if (EvenementsTable != null) {
            TableColumn<Evenement, String> titreColumn = (TableColumn<Evenement, String>) EvenementsTable.getColumns().get(0);
            titreColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitre()));

            TableColumn<Evenement, String> sportColumn = (TableColumn<Evenement, String>) EvenementsTable.getColumns().get(1);
            sportColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSport()));

            TableColumn<Evenement, String> typeColumn = (TableColumn<Evenement, String>) EvenementsTable.getColumns().get(2);
            typeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getType()));

            TableColumn<Evenement, String> lieuColumn = (TableColumn<Evenement, String>) EvenementsTable.getColumns().get(3);
            lieuColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLieu()));

            TableColumn<Evenement, String> dateColumn = (TableColumn<Evenement, String>) EvenementsTable.getColumns().get(4);
            dateColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate()));

            ObservableList<Evenement> observableList = FXCollections.observableArrayList();
            observableList.addAll(evenements);
            EvenementsTable.setItems(observableList);

            EvenementsTable.setRowFactory(tv -> {
                TableRow<Evenement> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (!row.isEmpty()) {
                        Evenement rowData = row.getItem();
                        showDetailsWindow(rowData);
                    }
                });
                return row;
            });
        }
    }

    private void RefreshTable() throws SQLException {
        dao.refreshDatabase();
        initData(dao);

        DisplayData();
    }

    private void AlertMessage(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }

    private void CloseWindow(AnchorPane anchorPane) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    private boolean ValidAthlete(Connection connection, String fullName) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean isValid = false;

        try {
            String[] nameParts = fullName.split(" ");
            if (nameParts.length != 2) {
                return false;
            }
            String prenom = nameParts[0];
            String nom = nameParts[1];

            String query = "SELECT COUNT(*) AS count FROM Athlete WHERE Prenom = ? AND Nom = ?";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, prenom);
            stmt.setString(2, nom);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt("count");
                isValid = count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return isValid;
    }

    private int getAthleteId(Connection connection, String fullName) throws SQLException {
        String[] nameParts = fullName.split(" ");
        if (nameParts.length != 2) {
            throw new IllegalArgumentException("Invalid full name format. Expected 'FirstName LastName'.");
        }
        String prenom = nameParts[0];
        String nom = nameParts[1];

        String query = "SELECT IdAthlete FROM Athlete WHERE Prenom = ? AND Nom = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, prenom);
            statement.setString(2, nom);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("IdAthlete");
                } else {
                    throw new SQLException("Athlete not found: " + fullName);
                }
            }
        }
    }






    // DETAILS EVENEMENTS
    private void setDetails(Evenement evenement) {
        titreLabel.setText(evenement.getTitre());
        sportLabel.setText(evenement.getSport());
        typeLabel.setText(evenement.getType());
        lieuLabel.setText(evenement.getLieu());
        dateLabel.setText(evenement.getDate());
    }

    private void showDetailsWindow(Evenement evenement) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Evenement/EvenementDetails.fxml"));
            Parent root = loader.load();
            EvenementController controller = loader.getController();
            controller.setDetails(evenement);
            controller.setIdEvenement(evenement.getIdEvenement());
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());
            Stage EvenementDetailsWindow = new Stage();
            EvenementDetailsWindow.setScene(scene);
            EvenementDetailsWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
            EvenementDetailsWindow.setTitle("Détails de l'évènement");

            ObservableList<Athlete> participants = loadParticipants(evenement.getIdEvenement());
            controller.ParticipantsTable.setItems(participants);

            TableColumn<Athlete, String> prenomColumn = new TableColumn<>("Prenom");
            prenomColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrenom()));
            prenomColumn.setPrefWidth(155);

            TableColumn<Athlete, String> nomColumn = new TableColumn<>("Nom");
            nomColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNom()));
            nomColumn.setPrefWidth(155);

            controller.ParticipantsTable.getColumns().setAll(prenomColumn, nomColumn);

            EvenementDetailsWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<Athlete> loadParticipants(int eventId) {
        ObservableList<Athlete> participants = FXCollections.observableArrayList();
        String query = "SELECT a.IdAthlete, a.Prenom, a.Nom, a.Naissance, a.Pays, a.Sexe, s.Sport " +
                "FROM Athlete a " +
                "JOIN Evenement_Athlete ea ON a.IdAthlete = ea.Athlete_Id " +
                "JOIN Sport s ON a.Sport_IdSport = s.IdSport " +
                "WHERE ea.Evenement_Id = ?";

        try (Connection connection = dao.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, eventId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idAthlete = resultSet.getInt("IdAthlete");
                String prenom = resultSet.getString("Prenom");
                String nom = resultSet.getString("Nom");
                Date naissance = resultSet.getDate("Naissance");
                String pays = resultSet.getString("Pays");
                String sexe = resultSet.getString("Sexe");
                String nomSport = resultSet.getString("Sport");

                Athlete athlete = new Athlete(idAthlete, prenom, nom, naissance, pays, sexe, nomSport);
                participants.add(athlete);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participants;
    }

    public void AddParticipantWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Evenement/AddParticipant.fxml"));
        Parent root = loader.load();
        EvenementController controller = loader.getController();
        controller.setIdEvenement(this.IdEvenement);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());
        Stage AddParticipantWindow = new Stage();
        AddParticipantWindow.setScene(scene);
        AddParticipantWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
        AddParticipantWindow.setTitle("Ajouter un participant");
        AddParticipantWindow.show();
    }

    public void AddParticipantClear() {
        AddParticipant_Athlete.clear();
    }

    public void AddParticipantGetData() throws SQLException {
        Connection connection = dao.getConnection();

        String athlete = AddParticipant_Athlete.getText();

        if (!athlete.isEmpty()) {
            if (ValidAthlete(connection, athlete)) {
                AddParticipant(IdEvenement, athlete);
            } else {
                AlertMessage(Alert.AlertType.ERROR, "Erreur", "Athlète invalide", "L'athlète indiqué n'est pas présent dans la base de données.");
            }
        } else {
            AlertMessage(Alert.AlertType.ERROR, "Erreur", "Données incompètes", "Merci de remplir tous les champs.");
        }
    }

    public void AddParticipant(int idevenement, String athlete) throws SQLException {
        Connection connection = dao.getConnection();
        int idathlete = getAthleteId(connection, athlete);
        String query = "INSERT INTO Evenement_Athlete (Evenement_Id, Athlete_Id) VALUES (?,?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idevenement);
            statement.setInt(2, idathlete);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                AddParticipantClear();
                AlertMessage(Alert.AlertType.INFORMATION, "Enregistrement effectué", "Enregistrement effectué !", "");
                CloseWindow(AddParticipant);
                RefreshTable();
            } else {
                AlertMessage(Alert.AlertType.ERROR, "Erreur", "Échec de l'enregistrement", "Impossible d'insérer les données.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    // ADD EVENEMENTS
    public void AddEvenementWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Evenement/AddEvenement.fxml"));
        Parent root = loader.load();
        EvenementController controller = loader.getController();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());
        Stage AddEvenementWindow = new Stage();
        AddEvenementWindow.setScene(scene);
        AddEvenementWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
        AddEvenementWindow.setTitle("Ajouter un évènement");
        AddEvenementWindow.show();

        controller.AddEvenement_Sport.getItems().addAll(
                "Athlétisme", "Aviron", "Badminton", "Basketball", "Boxe", "Canoë-Kayak",
                "Cyclisme", "Équitation", "Escalade", "Escrime", "Football", "Golf",
                "Gymnastique", "Haltérophilie", "Handball", "Hockey sur gazon", "Judo",
                "Lutte", "Natation", "Pentathlon moderne", "Rugby à sept", "Skateboard",
                "Surf", "Taekwondo", "Tennis", "Tennis de table", "Tir sportif",
                "Tir à l'arc", "Triathlon", "Voile", "Volleyball", "Water-polo", "Autres");
        controller.AddEvenement_Type.getItems().addAll("Cérémonie","Évènement sportif");
        controller.AddEvenement_Lieu.getItems().addAll("Stade de France", "Paris", "Bordeaux","Lyon", "Saint-Etienne", "Marseille", "Nice", "Tahiti");
    }

    public void AddEvenementClear() {
        AddEvenement_Titre.clear();
        AddEvenement_Sport.setValue(null);
        AddEvenement_Sport.cancelEdit();
        AddEvenement_Type.setValue(null);
        AddEvenement_Type.cancelEdit();
        AddEvenement_Lieu.setValue(null);
        AddEvenement_Lieu.cancelEdit();
        AddEvenement_Date.setValue(null);
        AddEvenement_Date.cancelEdit();
    }

    public void AddEvenementGetData() throws SQLException {
        String titre = AddEvenement_Titre.getText();
        String sport = (String) AddEvenement_Sport.getValue();
        String type = (String) AddEvenement_Type.getValue();
        String lieu = (String) AddEvenement_Lieu.getValue();
        String date = String.valueOf(AddEvenement_Date.getValue());

        if (!titre.isEmpty() && !sport.isEmpty() && !type.isEmpty() && !lieu.isEmpty() && !date.isEmpty()) {
            AddEvenement(titre, sport, type, lieu, date);
        } else {
            AlertMessage(Alert.AlertType.ERROR, "Erreur", "Données incompètes", "Merci de remplir tous les champs.");
        }
    }

    public void AddEvenement(String titre, String sport, String type, String lieu, String date) throws SQLException {
        Connection connection = dao.getConnection();
        String query = "INSERT INTO Evenement (Titre, Sport, Type, Lieu, Date) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, titre);
            statement.setString(2, sport);
            statement.setString(3, type);
            statement.setString(4, lieu);
            statement.setString(5, date);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                AddEvenementClear();
                AlertMessage(Alert.AlertType.INFORMATION, "Enregistrement effectué", "Enregistrement effectué !", "");
                CloseWindow(AddEvenement);
                RefreshTable();
            } else {
                AlertMessage(Alert.AlertType.ERROR, "Erreur", "Échec de l'enregistrement", "Impossible d'insérer les données.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    // DELETE EVENEMENTS
    public void DeleteEvenementWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Evenement/DeleteEvenement.fxml"));
        Parent root = loader.load();
        EvenementController controller = loader.getController();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());
        Stage DeleteEvenementWindow = new Stage();
        DeleteEvenementWindow.setScene(scene);
        DeleteEvenementWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
        DeleteEvenementWindow.setTitle("Supprimer un évènement");
        DeleteEvenementWindow.show();

        controller.DeleteEvenement_Sport.getItems().addAll(
                "Athlétisme", "Aviron", "Badminton", "Basketball", "Boxe", "Canoë-Kayak",
                "Cyclisme", "Équitation", "Escalade", "Escrime", "Football", "Golf",
                "Gymnastique", "Haltérophilie", "Handball", "Hockey sur gazon", "Judo",
                "Lutte", "Natation", "Pentathlon moderne", "Rugby à sept", "Skateboard",
                "Surf", "Taekwondo", "Tennis", "Tennis de table", "Tir sportif",
                "Tir à l'arc", "Triathlon", "Voile", "Volleyball", "Water-polo", "Autres");
        controller.DeleteEvenement_Type.getItems().addAll("Cérémonie","Évènement sportif");
        controller.DeleteEvenement_Lieu.getItems().addAll("Stade de France", "Paris", "Bordeaux","Lyon", "Saint-Etienne", "Marseille", "Nice", "Tahiti");

    }

    public void DeleteEvenementClear() {
        DeleteEvenement_Titre.clear();
        DeleteEvenement_Sport.setValue(null);
        DeleteEvenement_Sport.cancelEdit();
        DeleteEvenement_Type.setValue(null);
        DeleteEvenement_Type.cancelEdit();
        DeleteEvenement_Lieu.setValue(null);
        DeleteEvenement_Lieu.cancelEdit();
        DeleteEvenement_Date.setValue(null);
        DeleteEvenement_Date.cancelEdit();
    }

    public void DeleteEvenementGetData() throws SQLException {
        String titre = DeleteEvenement_Titre.getText();
        String sport = (String) DeleteEvenement_Sport.getValue();
        String type = (String) DeleteEvenement_Type.getValue();
        String lieu = (String) DeleteEvenement_Lieu.getValue();
        String date = String.valueOf(DeleteEvenement_Date.getValue());

        if (!titre.isEmpty() && !sport.isEmpty() && !type.isEmpty() && !lieu.isEmpty() && !date.isEmpty()) {
            DeleteEvenement(titre, sport, type, lieu, date);
        } else {
            AlertMessage(Alert.AlertType.ERROR, "Erreur", "Données incompètes", "Merci de remplir tous les champs.");
        }
    }

    public void DeleteEvenement(String titre, String sport, String type, String lieu, String date) throws SQLException {
        Connection connection = dao.getConnection();
        String query = "DELETE FROM Evenement WHERE Titre = ? AND Sport = ? AND Type = ? AND Lieu = ? AND Date = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, titre);
            statement.setString(2, sport);
            statement.setString(3, type);
            statement.setString(4, lieu);
            statement.setString(5, date);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                DeleteEvenementClear();
                AlertMessage(Alert.AlertType.INFORMATION, "Suppression effectuée", "L'événement a été supprimé avec succès !", "");
                CloseWindow(DeleteEvenement);
                RefreshTable();
            } else {
                AlertMessage(Alert.AlertType.ERROR, "Erreur", "Échec de la suppression", "Impossible de supprimer l'événement.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





    // EXPORT
    public void ExportToCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.append("Titre;Sport;Type;Lieu;Date\n");

                for (Evenement evenement : evenements) {
                    writer.append(evenement.getTitre()).append(";");
                    writer.append(evenement.getSport()).append(";");
                    writer.append(evenement.getType()).append(";");
                    writer.append(evenement.getLieu()).append(";");
                    writer.append(evenement.getDate()).append("\n");
                }

                writer.flush();
                AlertMessage(Alert.AlertType.INFORMATION, "Export terminé", "Export au format CSV terminé.", "");
            } catch (IOException e) {
                System.err.println("An error occurred while writing the CSV file: " + e.getMessage());
            }
        }
    }
}
