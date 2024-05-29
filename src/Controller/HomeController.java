package Controller;

import Model.DAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class HomeController {
    @FXML
    private AnchorPane Home;

    private DAO dao = new DAO();





    @FXML
    public void initialize() throws SQLException {
        dao.UploadData();
    }

    private void CloseWindow(AnchorPane anchorPane) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }





    // REDIRECTIONS
    public void GoToAthlete() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Athlete/Athlete.fxml"));
        Parent root = loader.load();

        dao.refreshDatabase();
        AthleteController athleteController = loader.getController();
        athleteController.initData(dao);
        athleteController.DisplayData();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());

        Stage AthleteWindow = new Stage();
        AthleteWindow.setScene(scene);
        AthleteWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
        AthleteWindow.setTitle("Athlètes");
        AthleteWindow.show();
    }

    public void GoToSport() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Sport/Sport.fxml"));
        Parent root = loader.load();

        dao.refreshDatabase();
        SportController sportController = loader.getController();
        sportController.initData(dao);
        sportController.DisplayData();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());

        Stage SportWindow = new Stage();
        SportWindow.setScene(scene);
        SportWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
        SportWindow.setTitle("Sports");
        SportWindow.show();
    }

    public void GoToEvenement() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Evenement/Evenement.fxml"));
        Parent root = loader.load();

        dao.refreshDatabase();
        EvenementController evenementController = loader.getController();
        evenementController.initData(dao);
        evenementController.DisplayData();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());

        Stage EvenementWindow = new Stage();
        EvenementWindow.setScene(scene);
        EvenementWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
        EvenementWindow.setTitle("Évènements");
        EvenementWindow.show();
    }

    public void GoToResultat() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Resultat/Resultat.fxml"));
        Parent root = loader.load();

        ResultatController resultatController = loader.getController();
        resultatController.initData(dao);
        resultatController.DisplayData();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());

        Stage ResultatWindow = new Stage();
        ResultatWindow.setScene(scene);
        ResultatWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
        ResultatWindow.setTitle("Résultats");
        ResultatWindow.show();
    }

    public void GoToCalendrier() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Calendrier/Calendrier.fxml"));
        Parent root = loader.load();

        dao.refreshDatabase();
        CalendrierController calendrierController = loader.getController();
        calendrierController.initData(dao);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());

        Stage CalendrierWindow = new Stage();
        CalendrierWindow.setScene(scene);
        CalendrierWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
        CalendrierWindow.setTitle("Calendrier des évènements");
        CalendrierWindow.show();
    }





    // DECONNEXION
    public void Deconnexion() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Connexion/Connexion.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());
        Stage ConnexionWindow = new Stage();
        ConnexionWindow.setScene(scene);
        ConnexionWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
        ConnexionWindow.setTitle("Jeux Olympiques Paris 2024");
        ConnexionWindow.show();

        CloseWindow(Home);
    }
}
