package Controller;

import Model.DAO;
import Model.Resultat;

import java.util.ArrayList;

public class ResultatController {
    private ArrayList<Resultat> resultats = new ArrayList<>();

    public void initData(DAO dao) {
        resultats.addAll(dao.getResultats());
    }
}
