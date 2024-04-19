package Controller;

import Model.Athlete;
import Model.DAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AthleteController {
    @FXML
    private TableView AthletesTable;
    private ArrayList<Athlete> athletes = new ArrayList<>();

    @FXML
    public void initialize() {
        DisplayData();
    }

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
        ageColumn.setCellValueFactory(data -> new SimpleStringProperty(dateToString(data.getValue().getNaissance())));

        TableColumn<Athlete, String> sportColumn = (TableColumn<Athlete, String>) AthletesTable.getColumns().get(5);
        sportColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNomSport()));

        ObservableList<Athlete> observableList = FXCollections.observableArrayList();
        observableList.addAll(athletes);
        AthletesTable.setItems(observableList);
    }

    private static String dateToString(Date date) {
        if (date == null) {
            return "";
        }
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }
}
