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
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
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
    private AnchorPane AddAthlete;
    @FXML
    private AnchorPane DeleteAthlete;
    @FXML
    private AnchorPane EditAthlete;
    @FXML
    private AnchorPane EditAthlete2;

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
    private TextField DeleteAthlete_Nom;
    @FXML
    private TextField DeleteAthlete_Prenom;
    @FXML
    private TextField DeleteAthlete_Naissance;

    @FXML
    private TextField EditAthlete_Nom;
    @FXML
    private TextField EditAthlete_Prenom;
    @FXML
    private TextField EditAthlete_Naissance;

    @FXML
    private TextField EditAthlete2_Nom;
    @FXML
    private TextField EditAthlete2_Prenom;
    @FXML
    private TextField EditAthlete2_Pays;
    @FXML
    private TextField EditAthlete2_Sexe;
    @FXML
    private TextField EditAthlete2_Naissance;
    @FXML
    private TextField EditAthlete2_Sport;

    private ArrayList<Athlete> athletes = new ArrayList<>();
    private DAO dao = new DAO();




    public void initData(DAO dao) {
        athletes.clear();
        athletes.addAll(dao.getAthletes());
    }

    public void DisplayData() {
        if (AthletesTable != null) {
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
        } else {
            System.out.println("BUG");
        }
    }

    private void RefreshTable() throws SQLException {
        dao.refreshDatabase();
        initData(dao);

        for (Athlete athlete : athletes) {
            //System.out.println(athlete.getNom());
        }
        DisplayData();
    }

    private static String DateToString(Date date) {
        if (date == null) {
            return "";
        }
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    private java.sql.Date StringToDate(String dateString) throws SQLException {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }

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

    private void CloseWindow(AnchorPane anchorPane) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }




    // ADD ATHLETE
    public void AddAthleteWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Athlete/AddAthlete.fxml"));
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

    public void AddAthlete(String nom, String prenom, String pays, String sexe, String naissance, String sport) throws SQLException {
        Connection connection = dao.getConnection();
        String queryCheck = "SELECT COUNT(*) FROM Athlete WHERE Nom = ? AND Prenom = ? AND Naissance = ?";
        String queryInsert = "INSERT INTO Athlete (Nom, Prenom, Naissance, Pays, Sexe, Sport_IdSport) VALUES (?, ?, ?, ?, ?, (SELECT IdSport FROM Sport WHERE Sport = ?))";

        try (PreparedStatement checkStatement = connection.prepareStatement(queryCheck)) {
            checkStatement.setString(1, nom);
            checkStatement.setString(2, prenom);
            checkStatement.setDate(3, StringToDate(naissance));
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                AlertMessage(Alert.AlertType.ERROR, "Erreur", "Athlète déjà enregistré.", "Vous ne pouvez pas enregistrer deux fois le même athlète.");
            } else {
                try (PreparedStatement insertStatement = connection.prepareStatement(queryInsert)) {
                    insertStatement.setString(1, nom);
                    insertStatement.setString(2, prenom);
                    insertStatement.setDate(3, StringToDate(naissance));
                    insertStatement.setString(4, pays);
                    insertStatement.setString(5, sexe);
                    insertStatement.setString(6, sport);

                    insertStatement.executeUpdate();

                    AddAthleteClear();
                    AlertMessage(Alert.AlertType.INFORMATION, "Enregistrement effectué", "Enregistrement effectué !", "");
                    CloseWindow(AddAthlete);
                    RefreshTable();
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Athlete/EditAthlete.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());
        Stage EditAthleteWindow = new Stage();
        EditAthleteWindow.setScene(scene);
        EditAthleteWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
        EditAthleteWindow.setTitle("Modifier un athlète");
        EditAthleteWindow.show();
    }

    public void EditAthleteClear1() {
        EditAthlete_Nom.clear();
        EditAthlete_Prenom.clear();
        EditAthlete_Naissance.clear();
    }

    public void EditAthleteGetData1() throws IOException, SQLException {
        if (ValidDate(EditAthlete_Naissance.getText())) {
            String nom = EditAthlete_Nom.getText();
            String prenom = EditAthlete_Prenom.getText();
            String naissance = EditAthlete_Naissance.getText();

            if (!nom.isEmpty() && !prenom.isEmpty() && !naissance.isEmpty()) {
                EditAthlete1(nom, prenom, naissance);
            } else {
                AlertMessage(Alert.AlertType.ERROR, "Erreur", "Données incompètes", "Merci de remplir tous les champs.");
            }
        } else {
            AlertMessage(Alert.AlertType.ERROR, "Erreur", "Erreur de format de date", "Veuillez entrer une date au format dd/mm/yyyy");
        }
    }

    public void EditAthlete1(String nom, String prenom, String naissance) throws SQLException, IOException {
        Connection connection = dao.getConnection();
        String query = "SELECT A.*, S.Sport AS NomSport " + "FROM Athlete A " + "INNER JOIN Sport S ON A.Sport_IdSport = S.IdSport " + "WHERE Nom = ? AND Prenom = ? AND Naissance = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nom);
            statement.setString(2, prenom);
            statement.setDate(3, StringToDate(naissance));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    if (count > 0) {
                        String nomAthlete = resultSet.getString("Nom");
                        String prenomAthlete = resultSet.getString("Prenom");
                        String sexeAthlete = resultSet.getString("Sexe");
                        String naissanceAthlete = DateToString(resultSet.getDate("Naissance"));
                        String paysAthlete = resultSet.getString("Pays");
                        String sportAthlete = resultSet.getString("NomSport");

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Athlete/EditAthlete2.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root);
                        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());
                        Stage EditAthleteWindow2 = new Stage();
                        EditAthleteWindow2.setScene(scene);
                        EditAthleteWindow2.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
                        EditAthleteWindow2.setTitle("Modifier un athlète");
                        EditAthleteWindow2.show();

                        Text TextEditAthlete2_Nom = (Text) root.lookup("#TextEditAthlete2_Nom");
                        Text TextEditAthlete2_Prenom = (Text) root.lookup("#TextEditAthlete2_Prenom");
                        Text TextEditAthlete2_Pays = (Text) root.lookup("#TextEditAthlete2_Pays");
                        Text TextEditAthlete2_Sexe = (Text) root.lookup("#TextEditAthlete2_Sexe");
                        Text TextEditAthlete2_Naissance = (Text) root.lookup("#TextEditAthlete2_Naissance");
                        Text TextEditAthlete2_Sport = (Text) root.lookup("#TextEditAthlete2_Sport");
                        TextEditAthlete2_Nom.setText(nomAthlete);
                        TextEditAthlete2_Prenom.setText(prenomAthlete);
                        TextEditAthlete2_Pays.setText(paysAthlete);
                        TextEditAthlete2_Sexe.setText(sexeAthlete);
                        TextEditAthlete2_Naissance.setText(naissanceAthlete);
                        TextEditAthlete2_Sport.setText(sportAthlete);

                        EditAthleteClear1();
                        CloseWindow(EditAthlete);
                    }
                } else {
                    AlertMessage(Alert.AlertType.ERROR, "Erreur", "Données inconnues", "Ces données ne sont pas dans la base de données.");
                }
            }
        }
    }

    public void EditAthleteClear2() {
        EditAthlete2_Nom.clear();
        EditAthlete2_Prenom.clear();
        EditAthlete2_Pays.clear();
        EditAthlete2_Sexe.clear();
        EditAthlete2_Naissance.clear();
        EditAthlete2_Sport.clear();
    }

    public void EditAthleteGetData2() throws SQLException {
        Connection connection = dao.getConnection();

        String nom = EditAthlete2_Nom.getText();
        String prenom = EditAthlete2_Prenom.getText();
        String pays = EditAthlete2_Pays.getText();
        String sexe = EditAthlete2_Sexe.getText();
        String naissance = EditAthlete2_Naissance.getText();
        String sport = EditAthlete2_Sport.getText();

        if (!nom.isEmpty() || !prenom.isEmpty() || !pays.isEmpty() || !sexe.isEmpty() || !sport.isEmpty()) {
            if (!naissance.isEmpty()) {
                if (ValidDate(EditAthlete2_Naissance.getText())) {
                    EditAthlete2(nom, prenom, pays, sexe, naissance, sport);
                } else {
                    AlertMessage(Alert.AlertType.ERROR, "Erreur", "Erreur de format de date", "Veuillez entrer une date au format dd/mm/yyyy");
                }
            } else if (!sport.isEmpty()) {
                if (ValidSport(connection, sport)) {
                    EditAthlete2(nom, prenom, pays, sexe, naissance, sport);
                } else {
                    AlertMessage(Alert.AlertType.ERROR, "Erreur", "Sport invalide", "Le sport indiqué n'est pas présent dans la base de données.");
                }
            } else {
                EditAthlete2(nom, prenom, pays, sexe, naissance, sport);
            }
        } else {
            AlertMessage(Alert.AlertType.ERROR, "Erreur", "Données incompètes", "Merci de remplir au moins un champs.");
        }
    }

    public void EditAthlete2(String nom, String prenom, String pays, String sexe, String naissance, String sport) throws SQLException {
        System.out.println("à faire");
        CloseWindow(EditAthlete2);
    }




    // DELETE ATHLETE
    public void DeleteAthleteWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Athlete/DeleteAthlete.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());
        Stage DeleteAthleteWindow = new Stage();
        DeleteAthleteWindow.setScene(scene);
        DeleteAthleteWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
        DeleteAthleteWindow.setTitle("Supprimer un athlète");
        DeleteAthleteWindow.show();
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
                CloseWindow(DeleteAthlete);
                RefreshTable();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

}
