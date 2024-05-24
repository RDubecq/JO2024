import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    static FXMLLoader loader;
    static Parent root;
    static public Scene scene;
    static public Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        loader = new FXMLLoader(getClass().getResource("View/Connection/Connection.fxml"));
        root = loader.load();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("View/style.css").toExternalForm());
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("View/Image/logoJO2024simple.png")));
        primaryStage.setTitle("Jeux Olympiques Paris 2024");
        primaryStage.setScene(scene);
        stage = primaryStage;
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
