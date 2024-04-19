package Controller;

import Model.DAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class HomeController {
    private DAO dao = new DAO();

    @FXML
    public void initialize() throws SQLException {
        dao.UploadData();
    }

    public void GoToAthlete() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Athlete.fxml"));
        Parent root = loader.load();

        AthleteController athleteController = loader.getController();
        athleteController.initData(dao);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());

        Stage AthleteWindow = new Stage();
        AthleteWindow.setScene(scene);
        AthleteWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
        AthleteWindow.setTitle("Athlètes");
        AthleteWindow.show();
    }

    public void GoToSport() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Sport.fxml"));
        Parent root = loader.load();

        SportController sportController = loader.getController();
        sportController.initData(dao);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());

        Stage SportWindow = new Stage();
        SportWindow.setScene(scene);
        SportWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
        SportWindow.setTitle("Sports");
        SportWindow.show();
    }

    public void GoToEvenement() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Evenement.fxml"));
        Parent root = loader.load();

        EvenementController evenementController = loader.getController();
        evenementController.initData(dao);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());

        Stage EvenementWindow = new Stage();
        EvenementWindow.setScene(scene);
        EvenementWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
        EvenementWindow.setTitle("Évènements");
        EvenementWindow.show();
    }

    public void GoToResultat() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Resultat.fxml"));
        Parent root = loader.load();

        ResultatController resultatController = loader.getController();
        resultatController.initData(dao);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());

        Stage ResultatWindow = new Stage();
        ResultatWindow.setScene(scene);
        ResultatWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
        ResultatWindow.setTitle("Résultats");
        ResultatWindow.show();
    }
}
