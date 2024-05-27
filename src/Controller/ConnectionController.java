package Controller;

import Model.DAO;
import javafx.fxml.FXML;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ConnectionController {
    @FXML
    public TextField Connection_Utilisateur;
    @FXML
    public PasswordField Connection_MDP;

    @FXML
    public TextField CreateAccount_Nom;
    @FXML
    public TextField CreateAccount_Prenom;
    @FXML
    public TextField CreateAccount_Naissance;
    @FXML
    public TextField CreateAccount_Utilisateur;
    @FXML
    public PasswordField CreateAccount_MDP;

    @FXML
    private AnchorPane Connection;
    @FXML
    private AnchorPane CreateAccount;

    private DAO dao = new DAO();





    @FXML
    public void initialize() throws SQLException {
        dao.UploadData();
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

    public void ClearConnection() {
        Connection_Utilisateur.clear();
        Connection_MDP.clear();
    }

    public void ClearCreateAccount() {
        CreateAccount_Nom.clear();
        CreateAccount_Prenom.clear();
        CreateAccount_Naissance.clear();
        CreateAccount_Utilisateur.clear();
        CreateAccount_MDP.clear();
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





    @FXML
    private void ConnectionGetData() throws SQLException, IOException {
        String Utilisateur = Connection_Utilisateur.getText();
        String MDP = Connection_MDP.getText();

        if (!Utilisateur.isEmpty() && !MDP.isEmpty()) {
            Connection(Utilisateur, MDP);
        } else {
            AlertMessage(Alert.AlertType.ERROR, "Erreur", "Données incompètes", "Merci de remplir tous les champs.");
        }
    }

    private void Connection(String utilisateur, String mdp) throws SQLException, IOException {
        Connection connection = dao.getConnection();
        String query = "SELECT * FROM utilisateur WHERE NomUtilisateur = ? AND MDP = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, utilisateur);
            statement.setString(2, mdp);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    GoToHome();
                    CloseWindow(Connection);
                } else {
                    AlertMessage(Alert.AlertType.ERROR, "Erreur", "Données inexistantes", "Merci de remplir avec des données existantes.");
                }
            }
        }
    }





    @FXML
    private void CreateAccountGetData() throws SQLException {
        if (ValidDate(CreateAccount_Naissance.getText())) {
            String Nom = CreateAccount_Nom.getText();
            String Prenom = CreateAccount_Prenom.getText();
            String Naissance = CreateAccount_Naissance.getText();
            String Utilisateur = CreateAccount_Utilisateur.getText();
            String MDP = CreateAccount_MDP.getText();

            if (!Nom.isEmpty() && !Prenom.isEmpty() && !Naissance.isEmpty() && !Utilisateur.isEmpty() && !MDP.isEmpty()) {
                CreateAccount(Nom, Prenom, Naissance, Utilisateur, MDP);
            }
        }
    }

    public void CreateAccount(String nom, String prenom, String naissance, String utilisateur, String mdp) throws SQLException {
        Connection connection = dao.getConnection();
        String queryCheck = "SELECT COUNT(*) FROM Utilisateur WHERE NomUtilisateur = ?";
        String queryInsert = "INSERT INTO Utilisateur (Nom, Prenom, Naissance, NomUtilisateur, MDP) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement checkStatement = connection.prepareStatement(queryCheck)) {
            checkStatement.setString(1, utilisateur);
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                AlertMessage(Alert.AlertType.ERROR, "Erreur", "Utilisateur déjà enregistré.", "Vous ne pouvez pas enregistrer deux fois le même utilisateur.");
            } else {
                try (PreparedStatement insertStatement = connection.prepareStatement(queryInsert)) {
                    insertStatement.setString(1, nom);
                    insertStatement.setString(2, prenom);
                    insertStatement.setDate(3, StringToDate(naissance));
                    insertStatement.setString(4, utilisateur);
                    insertStatement.setString(5, mdp);

                    insertStatement.executeUpdate();

                    ClearCreateAccount();
                    AlertMessage(Alert.AlertType.INFORMATION, "Enregistrement effectué", "Enregistrement effectué !", "");
                    CloseWindow(CreateAccount);
                    GoToHome();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                    throw e;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }






    public void GoToHome() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Home.fxml"));
        Parent root = loader.load();

        dao.refreshDatabase();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());

        Stage HomeWindow = new Stage();
        HomeWindow.setScene(scene);
        HomeWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
        HomeWindow.setTitle("Jeux Olympiques Paris 2024");
        HomeWindow.show();
    }

    public void CreateAccount() throws SQLException, IOException {
        CloseWindow(Connection);
        GoToCreateAccount();
    }

    public void GoToCreateAccount() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Connexion/CreateAccount.fxml"));
        Parent root = loader.load();

        dao.refreshDatabase();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());

        Stage CreateAccountWindow = new Stage();
        CreateAccountWindow.setScene(scene);
        CreateAccountWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
        CreateAccountWindow.setTitle("Création de compte");
        CreateAccountWindow.show();
    }

}
