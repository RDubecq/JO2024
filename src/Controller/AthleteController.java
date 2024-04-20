package Controller;

import Model.Athlete;
import Model.DAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

public class AthleteController {
    @FXML
    private TableView AthletesTable;

    @FXML
    private TextField AddAthlete_Nom;
    @FXML
    private TextField AddAthlete_Prenom;
    @FXML
    private TextField AddAthlete_Pays;
    @FXML
    private TextField AddAthlete_Sexe;
    @FXML
    private TextField AddAthlete_Naissance;
    @FXML
    private TextField AddAthlete_Sport;

    @FXML
    private TextField EditAthlete_Nom;
    @FXML
    private TextField EditAthlete_Prenom;
    @FXML
    private TextField EditAthlete_Pays;
    @FXML
    private TextField EditAthlete_Sexe;
    @FXML
    private TextField EditAthlete_Naissance;
    @FXML
    private TextField EditAthlete_Sport;

    @FXML
    private TextField DeleteAthlete_Nom;
    @FXML
    private TextField DeleteAthlete_Prenom;
    @FXML
    private TextField DeleteAthlete_Naissance;

    private ArrayList<Athlete> athletes = new ArrayList<>();
    private DAO dao = new DAO();




    public void initData(DAO dao) {
        athletes.addAll(dao.getAthletes());
    }

    public void DisplayData() {
        TableColumn<Athlete, String> nomColumn = (TableColumn<Athlete, String>) AthletesTable.getColumns().get(0);
        nomColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNom()));

        TableColumn<Athlete, String> prenomColumn = (TableColumn<Athlete, String>) AthletesTable.getColumns().get(1);
        prenomColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrenom()));

        TableColumn<Athlete, String> paysColumn = (TableColumn<Athlete, String>) AthletesTable.getColumns().get(2);
        paysColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPays()));

        TableColumn<Athlete, String> sexeColumn = (TableColumn<Athlete, String>) AthletesTable.getColumns().get(3);
        sexeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSexe()));

        TableColumn<Athlete, String> ageColumn = (TableColumn<Athlete, String>) AthletesTable.getColumns().get(4);
        ageColumn.setCellValueFactory(data -> new SimpleStringProperty(DateToString(data.getValue().getNaissance())));

        TableColumn<Athlete, String> sportColumn = (TableColumn<Athlete, String>) AthletesTable.getColumns().get(5);
        sportColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNomSport()));

        ObservableList<Athlete> observableList = FXCollections.observableArrayList();
        observableList.addAll(athletes);
        AthletesTable.setItems(observableList);
    }

    private static String DateToString(Date date) {
        if (date == null) {
            return "";
        }
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    private java.sql.Date StringToDate(String dateString) throws SQLException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(dateString, formatter);
            return java.sql.Date.valueOf(date);
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public static boolean ValidDate(String dateString) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate.parse(dateString, dateFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean ValidSport(Connection connection, String sportName) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean isValid = false;

        try {
            String query = "SELECT COUNT(*) AS count FROM Sport WHERE Sport = ?";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, sportName);
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

    private void AlertMessage(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }




    // ADD ATHLETE
    public void AddAthleteWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/AddAthlete.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());
        Stage AddAthleteWindow = new Stage();
        AddAthleteWindow.setScene(scene);
        AddAthleteWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
        AddAthleteWindow.setTitle("Ajouter un athlète");
        AddAthleteWindow.show();
    }

    public void AddAthleteClear() {
        AddAthlete_Nom.clear();
        AddAthlete_Prenom.clear();
        AddAthlete_Pays.clear();
        AddAthlete_Sexe.clear();
        AddAthlete_Naissance.clear();
        AddAthlete_Sport.clear();
    }

    public void AddAthleteGetData() throws SQLException {
        Connection connection = dao.getConnection();

        if (ValidDate(AddAthlete_Naissance.getText())) {
            String nom = AddAthlete_Nom.getText();
            String prenom = AddAthlete_Prenom.getText();
            String pays = AddAthlete_Pays.getText();
            String sexe = AddAthlete_Sexe.getText();
            String naissance = AddAthlete_Naissance.getText();
            String sport = AddAthlete_Sport.getText();

            if (!nom.isEmpty() && !prenom.isEmpty() && !pays.isEmpty() && !sexe.isEmpty() && !sport.isEmpty()) {
                if (ValidSport(connection, sport)) {
                    AddAthlete(nom, prenom, pays, sexe, naissance, sport);
                } else {
                    AlertMessage(Alert.AlertType.ERROR, "Erreur", "Sport invalide", "Le sport indiqué n'est pas présent dans la base de données.");
                }
            } else {
                AlertMessage(Alert.AlertType.ERROR, "Erreur", "Données incompètes", "Merci de remplir tous les champs.");
            }
        } else {
            AlertMessage(Alert.AlertType.ERROR, "Erreur", "Erreur de format de date", "Veuillez entrer une date au format dd/mm/yyyy");
        }
    }

    public void AddAthlete(String nom, String prenom, String pays, String sexe, String dateNaissance, String sport) throws SQLException {
        Connection connection = dao.getConnection();
        String queryCheck = "SELECT COUNT(*) FROM Athlete WHERE Nom = ? AND Prenom = ? AND Naissance = ?";
        String queryInsert = "INSERT INTO Athlete (Nom, Prenom, Naissance, Pays, Sexe, Sport_IdSport) VALUES (?, ?, ?, ?, ?, (SELECT IdSport FROM Sport WHERE Sport = ?))";

        try (PreparedStatement checkStatement = connection.prepareStatement(queryCheck)) {
            checkStatement.setString(1, nom);
            checkStatement.setString(2, prenom);
            checkStatement.setDate(3, StringToDate(dateNaissance));
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                AlertMessage(Alert.AlertType.ERROR, "Erreur", "Athlète déjà enregistré.", "Vous ne pouvez pas enregistrer deux fois le même athlète.");
            } else {
                try (PreparedStatement insertStatement = connection.prepareStatement(queryInsert)) {
                    insertStatement.setString(1, nom);
                    insertStatement.setString(2, prenom);
                    insertStatement.setDate(3, StringToDate(dateNaissance));
                    insertStatement.setString(4, pays);
                    insertStatement.setString(5, sexe);
                    insertStatement.setString(6, sport);

                    insertStatement.executeUpdate();

                    AddAthleteClear();
                    AlertMessage(Alert.AlertType.INFORMATION, "Enregistrement effectué", "Enregistrement effectué !", "");
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                    throw e;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }




    // EDIT ATHLETE
    public void EditAthleteWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/EditAthlete.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());
        Stage AddAthleteWindow = new Stage();
        AddAthleteWindow.setScene(scene);
        AddAthleteWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
        AddAthleteWindow.setTitle("Modifier un athlète");
        AddAthleteWindow.show();
    }

    public void EditAthleteClear() {
        EditAthlete_Nom.clear();
        EditAthlete_Prenom.clear();
        EditAthlete_Pays.clear();
        EditAthlete_Sexe.clear();
        EditAthlete_Naissance.clear();
        EditAthlete_Sport.clear();
    }

    public void EditAthleteGetData() throws SQLException {
        Connection connection = dao.getConnection();

        if (ValidDate(EditAthlete_Naissance.getText())) {
            String nom = EditAthlete_Nom.getText();
            String prenom = EditAthlete_Prenom.getText();
            String pays = EditAthlete_Pays.getText();
            String sexe = EditAthlete_Sexe.getText();
            String naissance = EditAthlete_Naissance.getText();
            String sport = EditAthlete_Sport.getText();

            if (!nom.isEmpty() && !prenom.isEmpty() && !pays.isEmpty() && !sexe.isEmpty() && !sport.isEmpty()) {
                if (ValidSport(connection, sport)) {
                    EditAthlete(nom, prenom, pays, sexe, naissance, sport);
                } else {
                    AlertMessage(Alert.AlertType.ERROR, "Erreur", "Sport invalide", "Le sport indiqué n'est pas présent dans la base de données.");
                }
            } else {
                AlertMessage(Alert.AlertType.ERROR, "Erreur", "Données incompètes", "Merci de remplir tous les champs.");
            }
        } else {
            AlertMessage(Alert.AlertType.ERROR, "Erreur", "Erreur de format de date", "Veuillez entrer une date au format dd/mm/yyyy");
        }
    }

    public void EditAthlete(String nom, String prenom, String pays, String sexe, String dateNaissance, String sport) {

    }




    // DELETE ATHLETE
    public void DeleteAthleteWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/DeleteAthlete.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());
        Stage AddAthleteWindow = new Stage();
        AddAthleteWindow.setScene(scene);
        AddAthleteWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
        AddAthleteWindow.setTitle("Supprimer un athlète");
        AddAthleteWindow.show();
    }

    public void DeleteAthleteClear() {
        DeleteAthlete_Nom.clear();
        DeleteAthlete_Prenom.clear();
        DeleteAthlete_Naissance.clear();
    }

    public void DeleteAthleteGetData() throws SQLException {
        if (ValidDate(DeleteAthlete_Naissance.getText())) {
            String nom = DeleteAthlete_Nom.getText();
            String prenom = DeleteAthlete_Prenom.getText();
            String naissance = DeleteAthlete_Naissance.getText();

            if (!nom.isEmpty() && !prenom.isEmpty() && !naissance.isEmpty()) {
                DeleteAthlete(nom, prenom, naissance);
            } else {
                AlertMessage(Alert.AlertType.ERROR, "Erreur", "Données incompètes", "Merci de remplir tous les champs.");
            }
        } else {
            AlertMessage(Alert.AlertType.ERROR, "Erreur", "Erreur de format de date", "Veuillez entrer une date au format dd/mm/yyyy");
        }
    }

    public void DeleteAthlete(String nom, String prenom, String dateNaissance) throws SQLException {
        Connection connection = dao.getConnection();
        String query = "DELETE FROM Athlete WHERE Nom = ? AND Prenom = ? AND Naissance = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nom);
            statement.setString(2, prenom);
            statement.setDate(3, StringToDate(dateNaissance));

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                AlertMessage(Alert.AlertType.ERROR, "Erreur", "Données inconnues", "Ces données ne sont pas dans la base de données.");
            } else {
                AlertMessage(Alert.AlertType.INFORMATION, "Enregistrement effectué", "Enregistrement effectué !", "");
                DeleteAthleteClear();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

}
