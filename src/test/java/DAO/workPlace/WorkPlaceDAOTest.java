package DAO.workPlace;

import DTOs.workPlace.WorkPlaceDTO;
import db.DBController;
import db.IConnPool;
import db.MySQL_TEST_DB;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;


/**
 * @author Rasmus Sander Larsen
 */
public class WorkPlaceDAOTest {

    IConnPool test_DB = new MySQL_TEST_DB();

    DBController dbController = new DBController(test_DB);

    WorkPlaceDAO workPlaceDAO = new WorkPlaceDAO(dbController);


    // region Test Material

    // region WorkplaceDTO No. 1

    int workplaceNo1_workerID = 1;
    String workplaceNo1_name = "Bilka - Hundige";
    Color workplaceNo1_color = Color.decode("#"+"FAEBD7");
    int workplaceNo1_telephone = 12345678;

    // endregion

    // endregion

    @Test
    public void getWorkPlace() {




    }

    @Test
    public void getWorkPlaceList() {
    }

    @Test
    public void getWorkPlaceList1() {
    }

    @Test
    public void createWorkPlace() {

        int nextWorkplaceID = dbController.getNextAutoIncreamental("Workplaces");

        WorkPlaceDTO workPlaceDTONo1 = new WorkPlaceDTO();
        workPlaceDTONo1.setWorkerID(workplaceNo1_workerID);
        workPlaceDTONo1.setName(workplaceNo1_name);
        workPlaceDTONo1.setColor(workplaceNo1_color);
        workPlaceDTONo1.setTelephone(workplaceNo1_telephone);


        workPlaceDAO.createWorkPlace(workPlaceDTONo1);

        WorkPlaceDTO returnedWorkplaceDTOOfNo1 = workPlaceDAO.getWorkPlace(nextWorkplaceID);

        assertEquals(returnedWorkplaceDTOOfNo1.getWorkplaceID(),nextWorkplaceID);
        assertEquals(returnedWorkplaceDTOOfNo1.getWorkerID(),workplaceNo1_workerID);
        assertEquals(returnedWorkplaceDTOOfNo1.getName(), workplaceNo1_name);
        assertEquals(returnedWorkplaceDTOOfNo1.getColor(), workplaceNo1_color);
        assertEquals(returnedWorkplaceDTOOfNo1.getTelephone(),workplaceNo1_telephone);




    }

    @Test
    public void updateWorkPlace() {
    }

    @Test
    public void deleteWorkPlace() {
    }
}