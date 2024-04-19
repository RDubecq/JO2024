package Controller;

import Model.DAO;
import Model.Evenement;

import java.util.ArrayList;

public class EvenementController {
    private ArrayList<Evenement> evenements = new ArrayList<>();

    public void initData(DAO dao) {
        evenements.addAll(dao.getEvenements());
    }
}
