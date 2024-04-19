package Controller;

import Model.DAO;
import Model.Sport;
import java.util.ArrayList;

public class SportController {
    ArrayList<Sport> sports = new ArrayList<>();

    public void initData(DAO dao) {
        sports.addAll(dao.getSports());
    }

    public void print() {
        for (Sport sport : sports) {
            System.out.println(sport.getSport());
        }
    }
}
