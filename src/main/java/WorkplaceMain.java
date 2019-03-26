import DAO.workPlace.WorkPlaceDAO;
import DTOs.workPlace.WorkPlaceDTO;
import db.DBController;
import db.MySQL_DB;

import java.awt.*;

/**
 * @author Rasmus Sander Larsen
 */
public class WorkplaceMain {

    public static void main(String[] args) {

        MySQL_DB mySQL_db = new MySQL_DB("ras92mus");

        DBController dbController = new DBController(mySQL_db);

        WorkPlaceDAO workPlaceDAO = new WorkPlaceDAO(dbController);

        WorkPlaceDTO workplace = new WorkPlaceDTO();
        workplace.setWorkerID(2);
        workplace.setName("Bilka - Ishøj");
        workplace.setColor(Color.decode("#"+"FAEBD7"));
        workplace.setTelephone(12345678);

        // workPlaceDAO.createWorkPlace(workplace);
        //System.out.println(workplace);

       // System.out.println(workPlaceDAO.getWorkPlace(2));

        /*
        int counter = 1;

        for (WorkPlaceDTO workPlaceDTO : workPlaceDAO.getWorkPlaceList()) {
            System.out.println("~~~~ Workplace: " + counter++ + " ~~~~");
            System.out.println(workPlaceDTO);
        } */

        WorkPlaceDTO updatingWP = workPlaceDAO.getWorkPlace(13);

        System.out.println("OPDATING WP");
        System.out.println(updatingWP);

        updatingWP.setTelephone(12345678);
        workPlaceDAO.updateWorkPlace(updatingWP);

        System.out.println(workPlaceDAO.getWorkPlace(13));

        workPlaceDAO.deleteWorkPlace(13);

        int counter = 1;

        for (WorkPlaceDTO workPlaceDTO : workPlaceDAO.getWorkPlaceList()) {
            System.out.println("~~~~ Workplace: " + counter++ + " ~~~~");
            System.out.println(workPlaceDTO);
        }

    }
}
