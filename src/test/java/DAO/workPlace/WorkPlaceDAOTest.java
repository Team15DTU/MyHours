package DAO.workPlace;

import DTOs.workPlace.WorkPlaceDTO;
import db.DBController;
import db.IConnPool;
import db.MySQL_TEST_DB;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * @author Rasmus Sander Larsen
 */
public class WorkPlaceDAOTest {

    private IConnPool test_DB = new MySQL_TEST_DB();

    private DBController dbController = new DBController(test_DB);

    private WorkPlaceDAO workPlaceDAO = new WorkPlaceDAO(dbController);

    // region Test Material

    // region WorkplaceDTO No. 1

    private int workplaceNo1_workerID = 1;
    private String workplaceNo1_name = "Bilka - Hundige";
    private Color workplaceNo1_color = Color.decode("#FAEBD7");
    private int workplaceNo1_telephone = 12345678;
    private int workplaceNo1_workplaceID_assigned;

    private WorkPlaceDTO workPlaceDTONo1 = new WorkPlaceDTO();

    // endregion

    // region WorkplaceDTO No. 2

    private int workplaceNo2_workerID = 2;
    private String workplaceNo2_name = "DTU - Lyngby";
    private Color workplaceNo2_color = Color.decode("#55a7e4");
    private int workplaceNo2_telephone = 87654321;
    int workplaceNo2_workplaceID_assigned;

    private WorkPlaceDTO workPlaceDTONo2 = new WorkPlaceDTO();

    // endregion

    public WorkPlaceDAOTest () {

        // Setting up workPlaceDTONo1
        workPlaceDTONo1.setWorkerID(workplaceNo1_workerID);
        workPlaceDTONo1.setName(workplaceNo1_name);
        workPlaceDTONo1.setColor(workplaceNo1_color);
        workPlaceDTONo1.setTelephone(workplaceNo1_telephone);

        // Setting up workPlaceDTONo2
        workPlaceDTONo2.setWorkerID(workplaceNo2_workerID);
        workPlaceDTONo2.setName(workplaceNo2_name);
        workPlaceDTONo2.setColor(workplaceNo2_color);
        workPlaceDTONo2.setTelephone(workplaceNo2_telephone);

    }

    // endregion

    /*
    ----------------------------- TESTS -----------------------------
     */

    @Test
    public void createWorkPlaces() {

        // WorkplaceDTO No. 1

        int nextAutoIncrementalForWorkplaceNo1 = dbController.getNextAutoIncremental("Workplaces");
        workplaceNo1_workplaceID_assigned = nextAutoIncrementalForWorkplaceNo1;

        workPlaceDAO.createWorkPlace(workPlaceDTONo1);

        WorkPlaceDTO returnedWorkplaceDTOOfNo1 = workPlaceDAO.getWorkPlace(nextAutoIncrementalForWorkplaceNo1);

        assertEquals(returnedWorkplaceDTOOfNo1.getWorkplaceID(), nextAutoIncrementalForWorkplaceNo1);
        assertEquals(returnedWorkplaceDTOOfNo1.getWorkerID(), workplaceNo1_workerID);
        assertEquals(returnedWorkplaceDTOOfNo1.getName(), workplaceNo1_name);
        assertEquals(returnedWorkplaceDTOOfNo1.getColor(), workplaceNo1_color);
        assertEquals(returnedWorkplaceDTOOfNo1.getTelephone(),workplaceNo1_telephone);

        // WorkplaceDTO No. 1

        int nextAutoIncrementalForWorkplaceNo2 = dbController.getNextAutoIncremental("Workplaces");

        workplaceNo2_workplaceID_assigned = nextAutoIncrementalForWorkplaceNo2;

        workPlaceDAO.createWorkPlace(workPlaceDTONo2);

        WorkPlaceDTO returnedWorkplaceDTOOfNo2 = workPlaceDAO.getWorkPlace(nextAutoIncrementalForWorkplaceNo2);

        assertEquals(returnedWorkplaceDTOOfNo2.getWorkplaceID(), nextAutoIncrementalForWorkplaceNo2);
        assertEquals(returnedWorkplaceDTOOfNo2.getWorkerID(), workplaceNo2_workerID);
        assertEquals(returnedWorkplaceDTOOfNo2.getName(), workplaceNo2_name);
        assertEquals(returnedWorkplaceDTOOfNo2.getColor(), workplaceNo2_color);
        assertEquals(returnedWorkplaceDTOOfNo2.getTelephone(),workplaceNo2_telephone);

    }

    @Test
    public void getWorkPlaceList() {

        // Test of getWorkPlaceList()

        List<WorkPlaceDTO> workPlaceDTOList;

        workPlaceDTOList = workPlaceDAO.getWorkPlaceList();

        assertEquals(workPlaceDTOList.size(),2);

    }

    @Test
    public void getWorkPlaceListFromWorkerID() {

        // Test of getWorkPlaceList(int workerID)

        List<WorkPlaceDTO> workPlaceDTOListFromWorkerID;

        workPlaceDTOListFromWorkerID = workPlaceDAO.getWorkPlaceList(workplaceNo1_workerID);

        int listSize = workPlaceDTOListFromWorkerID.size();

        assertEquals(listSize,1);

    }

    @Test
    public void getWorkPlace() {

        WorkPlaceDTO returnedWorkplaceDTO = workPlaceDAO.getWorkPlace(workplaceNo2_workplaceID_assigned);

        assertEquals(returnedWorkplaceDTO.getWorkplaceID(),workplaceNo2_workplaceID_assigned);
        assertEquals(returnedWorkplaceDTO.getWorkerID(), workplaceNo2_workerID);
        assertEquals(returnedWorkplaceDTO.getName(), workplaceNo2_name);
        assertEquals(returnedWorkplaceDTO.getColor(),workplaceNo2_color);
        assertEquals(returnedWorkplaceDTO.getTelephone(), workplaceNo2_telephone);

    }

    @Test
    public void updateWorkPlace() {

    }

    @Test
    public void deleteWorkPlace() {
    }
}