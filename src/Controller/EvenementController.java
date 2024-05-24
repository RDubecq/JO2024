package Controller;

import Model.Athlete;
import Model.DAO;
import Model.Evenement;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EvenementController {
    @FXML
    private TableView EvenementsTable;

    private ArrayList<Evenement> evenements = new ArrayList<>();
    private DAO dao = new DAO();





    public void initData(DAO dao) {
        evenements.clear();
        evenements.addAll(dao.getEvenements());
    }

    public void DisplayData() {
        if (EvenementsTable != null) {
            TableColumn<Evenement, String> titreColumn = (TableColumn<Evenement, String>) EvenementsTable.getColumns().get(0);
            titreColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitre()));

            TableColumn<Evenement, String> typeColumn = (TableColumn<Evenement, String>) EvenementsTable.getColumns().get(1);
            typeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getType()));

            TableColumn<Evenement, String> lieuColumn = (TableColumn<Evenement, String>) EvenementsTable.getColumns().get(2);
            lieuColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLieu()));

            TableColumn<Evenement, String> dateheureColumn = (TableColumn<Evenement, String>) EvenementsTable.getColumns().get(3);
            dateheureColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate_Heure()));

            ObservableList<Evenement> observableList = FXCollections.observableArrayList();
            observableList.addAll(evenements);
            EvenementsTable.setItems(observableList);
        }
    }

    private void RefreshTable() throws SQLException {
        dao.refreshDatabase();
        initData(dao);

        for (Evenement evenement : evenements) {
            System.out.println(evenement.getDate_Heure());
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
}
