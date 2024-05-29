package Controller;

import Model.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResultatController {
    @FXML
    private TableView ResultatTable;
    @FXML
    private TableView ClassementTable;

    @FXML
    private BarChart<String, Number> MedaillesBarChart;

    private ObservableList<Map.Entry<String, Map<String, Integer>>> classementList = null;

    @FXML
    private AnchorPane AddResultat;
    @FXML
    private AnchorPane DeleteResultat;

    @FXML
    private TextField AddResultat_Sport;
    @FXML
    private TextField AddResultat_Evenement;
    @FXML
    private TextField AddResultat_Gagnant;

    private ArrayList<Resultat> resultats = new ArrayList<>();
    private ArrayList<Medailles> medailles = new ArrayList<>();
    private DAO dao = new DAO();





    public void initData(DAO dao) {
        resultats.addAll(dao.getResultats());
        medailles.addAll(dao.getMedailles());
    }

    public void DisplayData() {
        if (ResultatTable != null) {
            TableColumn<Resultat, String> sportColumn = (TableColumn<Resultat, String>) ResultatTable.getColumns().get(0);
            sportColumn.setCellValueFactory(data -> new SimpleStringProperty(getSportName(data.getValue().getIdSport())));

            TableColumn<Resultat, String> evenementColumn = (TableColumn<Resultat, String>) ResultatTable.getColumns().get(1);
            evenementColumn.setCellValueFactory(data -> new SimpleStringProperty(getEvenementName(data.getValue().getIdEvenement())));

            TableColumn<Resultat, String> gagnantColumn = (TableColumn<Resultat, String>) ResultatTable.getColumns().get(2);
            gagnantColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGagnant()));

            ObservableList<Resultat> observableList = FXCollections.observableArrayList();
            observableList.addAll(resultats);
            ResultatTable.setItems(observableList);
        }

        if (ClassementTable != null) {
            Map<String, Map<String, Integer>> classementMedailles = new HashMap<>();

            for (Medailles medailles : medailles) {
                String pays = medailles.getPays();
                String typeMedaille = medailles.getType();

                classementMedailles.putIfAbsent(pays, new HashMap<>());
                Map<String, Integer> medaillesPays = classementMedailles.get(pays);

                medaillesPays.put(typeMedaille, medaillesPays.getOrDefault(typeMedaille, 0) + 1);
            }

            classementList = FXCollections.observableArrayList(classementMedailles.entrySet());
            classementList.sort((entry1, entry2) -> {
                int total1 = entry1.getValue().getOrDefault("Or", 0) + entry1.getValue().getOrDefault("Argent", 0) + entry1.getValue().getOrDefault("Bronze", 0);
                int total2 = entry2.getValue().getOrDefault("Or", 0) + entry2.getValue().getOrDefault("Argent", 0) + entry2.getValue().getOrDefault("Bronze", 0);
                return Integer.compare(total2, total1);
            });

            TableColumn<Map.Entry<String, Map<String, Integer>>, String> paysColumn = (TableColumn<Map.Entry<String, Map<String, Integer>>, String>) ClassementTable.getColumns().get(0);
            paysColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKey()));

            TableColumn<Map.Entry<String, Map<String, Integer>>, Integer> orColumn = (TableColumn<Map.Entry<String, Map<String, Integer>>, Integer>) ClassementTable.getColumns().get(1);
            orColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getValue().getOrDefault("Or", 0)).asObject());

            TableColumn<Map.Entry<String, Map<String, Integer>>, Integer> argentColumn = (TableColumn<Map.Entry<String, Map<String, Integer>>, Integer>) ClassementTable.getColumns().get(2);
            argentColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getValue().getOrDefault("Argent", 0)).asObject());

            TableColumn<Map.Entry<String, Map<String, Integer>>, Integer> bronzeColumn = (TableColumn<Map.Entry<String, Map<String, Integer>>, Integer>) ClassementTable.getColumns().get(3);
            bronzeColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getValue().getOrDefault("Bronze", 0)).asObject());

            TableColumn<Map.Entry<String, Map<String, Integer>>, Integer> totalColumn = new TableColumn<>("Total");
            totalColumn.setCellValueFactory(data -> {
                Map<String, Integer> medaillesPays = data.getValue().getValue();
                int total = medaillesPays.getOrDefault("Or", 0) + medaillesPays.getOrDefault("Argent", 0) + medaillesPays.getOrDefault("Bronze", 0);
                return new SimpleIntegerProperty(total).asObject();
            });
            totalColumn.setPrefWidth(71.25);
            ClassementTable.getColumns().add(1, totalColumn);

            ClassementTable.setItems(classementList);
        }
    }

    private void AlertMessage(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
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

    private void CloseWindow(AnchorPane anchorPane) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }





    // IDENTIFIER SPORTS & EVENEMENTS EN FONCTION DE L'ID (DOUBLE SENS)
    private String getSportName(int sportId) {
        String sportName = "";
        try (Connection connection = dao.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT Sport FROM Sport WHERE IdSport = ?")) {
            statement.setInt(1, sportId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                sportName = resultSet.getString("Sport");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sportName;
    }

    private String getEvenementName(int evenementId) {
        String evenementName = "";
        try (Connection connection = dao.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT Titre FROM Evenement WHERE IdEvenement = ?")) {
            statement.setInt(1, evenementId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                evenementName = resultSet.getString("Titre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evenementName;
    }

    private int getSportId(Connection connection, String sportName) throws SQLException {
        String query = "SELECT IdSport FROM Sport WHERE Sport = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, sportName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("IdSport");
                } else {
                    throw new SQLException("Sport not found: " + sportName);
                }
            }
        }
    }

    private int getEvenementId(Connection connection, String evenementTitle) throws SQLException {
        String query = "SELECT IdEvenement FROM Evenement WHERE Titre = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, evenementTitle);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("IdEvenement");
                } else {
                    throw new SQLException("Evenement not found: " + evenementTitle);
                }
            }
        }
    }






    // ADD RESULTAT
    public void AddResultatWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Resultat/AddResultat.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());
        Stage AddResultatWindow = new Stage();
        AddResultatWindow.setScene(scene);
        AddResultatWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
        AddResultatWindow.setTitle("Ajouter un résultat");
        AddResultatWindow.show();
    }

    public void AddResultatClear() {
        AddResultat_Sport.clear();
        AddResultat_Evenement.clear();
        AddResultat_Gagnant.clear();
    }

    public void AddResultatGetData() throws SQLException {
        Connection connection = dao.getConnection();

        String sport = AddResultat_Sport.getText();
        String evenement = AddResultat_Evenement.getText();
        String gagnant = AddResultat_Gagnant.getText();

        if (!sport.isEmpty() && !evenement.isEmpty() && !gagnant.isEmpty()) {
            if (ValidSport(connection, sport)) {
                if (ValidAthlete(connection, gagnant)) {
                    int Sport = getSportId(connection, sport);
                    int Evenement = getEvenementId(connection, evenement);
                    AddResultat(gagnant, Sport, Evenement);
                } else {
                    AlertMessage(Alert.AlertType.ERROR, "Erreur", "Athlète invalide", "L'athlète indiqué n'est pas présent dans la base de données.");
                }
            } else {
                AlertMessage(Alert.AlertType.ERROR, "Erreur", "Sport invalide", "Le sport indiqué n'est pas présent dans la base de données.");
            }
        } else {
            AlertMessage(Alert.AlertType.ERROR, "Erreur", "Données incompètes", "Merci de remplir tous les champs.");
        }

    }

    public void AddResultat(String gagnant, int sport, int evenement) throws SQLException {
        Connection connection = dao.getConnection();
        String queryInsert = "INSERT INTO Resultat (Gagnant, Sport_IdSport, Evenement) VALUES (?, ?, ?)";

        try (PreparedStatement insertStatement = connection.prepareStatement(queryInsert)) {
            insertStatement.setString(1, gagnant);
            insertStatement.setInt(2, sport);
            insertStatement.setInt(3, evenement);

            insertStatement.executeUpdate();

            AddResultatClear();
            AlertMessage(Alert.AlertType.INFORMATION, "Enregistrement effectué", "Enregistrement effectué !", "");
            CloseWindow(AddResultat);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw e;
        }
    }

    // A REFAIRE, VERIFIER SI ATHLETE EXISTE, SI EVENEMENT EXISTE, SI SPORT EXISTE





    // DELETE RESULTAT
    public void DeleteResultatWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Resultat/DeleteResultat.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());
        Stage DeleteResultatWindow = new Stage();
        DeleteResultatWindow.setScene(scene);
        DeleteResultatWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
        DeleteResultatWindow.setTitle("Supprimer un résultat");
        DeleteResultatWindow.show();
    }

    public void DeleteResultatClear() {

    }

    public void DeleteResultatGetData() throws SQLException {

    }

    public void DeleteResultat(String gagnant, String sport, String evenement) throws SQLException {

    }





    // EXPORT
    public void ExportToCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.append("Sport;Evenement;Gagnant\n");

                for (Resultat resultat : resultats) {
                    writer.append(getSportName(resultat.getIdSport())).append(";");
                    writer.append(getEvenementName(resultat.getIdEvenement())).append("\n");
                    writer.append(resultat.getGagnant()).append(";");
                }

                writer.flush();
                AlertMessage(Alert.AlertType.INFORMATION, "Export terminé", "Export au format CSV terminé.", "");
            } catch (IOException e) {
                System.err.println("An error occurred while writing the CSV file: " + e.getMessage());
            }
        }
    }





    // DIAGRAMME MEDAILLES
    public void DiagrammeWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Resultat/Diagramme.fxml"));
        Parent root = loader.load();
        ResultatController controller = loader.getController();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/View/style.css").toExternalForm());
        Stage DiagrammeWindow = new Stage();
        DiagrammeWindow.setScene(scene);
        DiagrammeWindow.getIcons().add(new Image(getClass().getResourceAsStream("/View/Image/logoJO2024simple.png")));
        DiagrammeWindow.setTitle("Diagramme des médailles");
        DiagrammeWindow.show();

        controller.afficherDonneesDansBarChart(classementList);
    }

    public void afficherDonneesDansBarChart(ObservableList<Map.Entry<String, Map<String, Integer>>> classementList) {
        if (MedaillesBarChart != null) {
            MedaillesBarChart.getData().clear();

            XYChart.Series<String, Number> orSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> argentSeries = new XYChart.Series<>();
            XYChart.Series<String, Number> bronzeSeries = new XYChart.Series<>();
            for (Map.Entry<String, Map<String, Integer>> entry : classementList) {
                String pays = entry.getKey();
                Map<String, Integer> medaillesPays = entry.getValue();

                int or = medaillesPays.getOrDefault("Or", 0);
                int argent = medaillesPays.getOrDefault("Argent", 0);
                int bronze = medaillesPays.getOrDefault("Bronze", 0);

                orSeries.getData().add(new XYChart.Data<>(pays, or));
                argentSeries.getData().add(new XYChart.Data<>(pays, argent));
                bronzeSeries.getData().add(new XYChart.Data<>(pays, bronze));
            }

            MedaillesBarChart.getData().addAll(orSeries, argentSeries, bronzeSeries);
        }
    }
}

