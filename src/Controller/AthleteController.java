package Controller;

import Model.Athlete;
import Model.DAO;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import java.util.ArrayList;

public class AthleteController {
    @FXML
    private TableView AthletesTable;
    private ArrayList<Athlete> athletes = new ArrayList<>();

    public void initData(DAO dao) {
        athletes.addAll(dao.getAthletes());
    }
}
