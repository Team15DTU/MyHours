package DAO.workPlace;

import DAO.DALException;
import DTOs.workPlace.WorkPlaceDTO;
import db.DBController;
import db.IConnPool;
import db.MySQL_TEST_DB;
import org.junit.Test;

import java.awt.*;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Rasmus Sander Larsen
 */
public class WorkPlaceDAOTest {

    private IConnPool test_DB = new MySQL_TEST_DB();

    private DBController dbController = new DBController(test_DB);

    private IWorkPlaceDAO iWorkPlaceDAO = dbController.getiWorkPlaceDAO();

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

    public WorkPlaceDAOTest () throws DALException {

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
    public void createWorkPlaces() throws DALException {

        // WorkplaceDTO No. 1

        int nextAutoIncrementalForWorkplaceNo1 = dbController.getNextAutoIncremental("Workplaces");

        iWorkPlaceDAO.createWorkPlace(workPlaceDTONo1);

        WorkPlaceDTO returnedWorkplaceDTOOfNo1 = iWorkPlaceDAO.getWorkPlace(nextAutoIncrementalForWorkplaceNo1);

        assertEquals(returnedWorkplaceDTOOfNo1.getWorkplaceID(), nextAutoIncrementalForWorkplaceNo1);
        assertEquals(returnedWorkplaceDTOOfNo1.getWorkerID(), workplaceNo1_workerID);
        assertEquals(returnedWorkplaceDTOOfNo1.getName(), workplaceNo1_name);
        assertEquals(returnedWorkplaceDTOOfNo1.getColor(), workplaceNo1_color);
        assertEquals(returnedWorkplaceDTOOfNo1.getTelephone(),workplaceNo1_telephone);

        iWorkPlaceDAO.deleteWorkPlace(nextAutoIncrementalForWorkplaceNo1);

    }

    @Test
    public void getWorkPlaceList() throws DALException {

        // Creates WorkplaceDTO No. 1

        int nextAutoIncrementalForWorkplaceNo1 = dbController.getNextAutoIncremental("Workplaces");

        iWorkPlaceDAO.createWorkPlace(workPlaceDTONo1);

        // Creates WorkplaceDTO No. 2

        int nextAutoIncrementalForWorkplaceNo2 = dbController.getNextAutoIncremental("Workplaces");

        iWorkPlaceDAO.createWorkPlace(workPlaceDTONo2);

        // Test of getWorkPlaceList()

        List<WorkPlaceDTO> workPlaceDTOList;

        workPlaceDTOList= iWorkPlaceDAO.getWorkPlaceList();

        assertEquals(workPlaceDTOList.size(),2);

        iWorkPlaceDAO.deleteWorkPlace(nextAutoIncrementalForWorkplaceNo1);
        iWorkPlaceDAO.deleteWorkPlace(nextAutoIncrementalForWorkplaceNo2);

    }

    @Test
    public void getWorkPlaceListFromWorkerID() throws DALException {

        // Creates WorkplaceDTO No. 1

        int nextAutoIncrementalForWorkplaceNo1 = dbController.getNextAutoIncremental("Workplaces");

        iWorkPlaceDAO.createWorkPlace(workPlaceDTONo1);

        // Creates WorkplaceDTO No. 2

        int nextAutoIncrementalForWorkplaceNo2 = dbController.getNextAutoIncremental("Workplaces");

        iWorkPlaceDAO.createWorkPlace(workPlaceDTONo2);

        // Test of getWorkPlaceList(int workerID)

        List<WorkPlaceDTO> workPlaceDTOListFromWorkerID;

        workPlaceDTOListFromWorkerID = iWorkPlaceDAO.getWorkPlaceList(workplaceNo1_workerID);

        assertEquals(workPlaceDTOListFromWorkerID.size(),1);

        iWorkPlaceDAO.deleteWorkPlace(nextAutoIncrementalForWorkplaceNo1);
        iWorkPlaceDAO.deleteWorkPlace(nextAutoIncrementalForWorkplaceNo2);

    }

    @Test
    public void getWorkPlace() throws DALException {

        // WorkplaceDTO No. 2

        int nextAutoIncrementalForWorkplaceNo2 = dbController.getNextAutoIncremental("Workplaces");

        iWorkPlaceDAO.createWorkPlace(workPlaceDTONo2);

        WorkPlaceDTO returnedWorkplaceDTOOfNo2 = iWorkPlaceDAO.getWorkPlace(nextAutoIncrementalForWorkplaceNo2);

        assertEquals(returnedWorkplaceDTOOfNo2.getWorkplaceID(), nextAutoIncrementalForWorkplaceNo2);
        assertEquals(returnedWorkplaceDTOOfNo2.getWorkerID(), workplaceNo2_workerID);
        assertEquals(returnedWorkplaceDTOOfNo2.getName(), workplaceNo2_name);
        assertEquals(returnedWorkplaceDTOOfNo2.getColor(), workplaceNo2_color);
        assertEquals(returnedWorkplaceDTOOfNo2.getTelephone(),workplaceNo2_telephone);

        iWorkPlaceDAO.deleteWorkPlace(nextAutoIncrementalForWorkplaceNo2);

    }

    @Test
    public void updateWorkPlace() throws DALException {

        // WorkplaceDTO No. 1

        int nextAutoIncrementalForWorkplaceNo1 = dbController.getNextAutoIncremental("Workplaces");

        iWorkPlaceDAO.createWorkPlace(workPlaceDTONo1);

        WorkPlaceDTO workplaceDTOOfNo1ToUpdate = iWorkPlaceDAO.getWorkPlace(nextAutoIncrementalForWorkplaceNo1);

        // Values of WorkplaceDTO No. 2 inserted into "workplaceDTOOfNo1ToUpdate".

        workplaceDTOOfNo1ToUpdate.setWorkerID(workplaceNo2_workerID);
        workplaceDTOOfNo1ToUpdate.setName(workplaceNo2_name);
        workplaceDTOOfNo1ToUpdate.setColor(workplaceNo2_color);
        workplaceDTOOfNo1ToUpdate.setTelephone(workplaceNo2_telephone);

        // Updates the WorkplaceDTO with the information that is passed into the method

        iWorkPlaceDAO.updateWorkPlace(workplaceDTOOfNo1ToUpdate);

        assertEquals(workplaceDTOOfNo1ToUpdate.getWorkplaceID(), nextAutoIncrementalForWorkplaceNo1);
        assertEquals(workplaceDTOOfNo1ToUpdate.getWorkerID(), workplaceNo2_workerID);
        assertEquals(workplaceDTOOfNo1ToUpdate.getName(), workplaceNo2_name);
        assertEquals(workplaceDTOOfNo1ToUpdate.getColor(), workplaceNo2_color);
        assertEquals(workplaceDTOOfNo1ToUpdate.getTelephone(),workplaceNo2_telephone);

        // Deletes the created Workplace

        iWorkPlaceDAO.deleteWorkPlace(nextAutoIncrementalForWorkplaceNo1);

    }


    @Test
    public void deleteWorkPlace() throws DALException {

        // WorkplaceDTO No. 1

        int nextAutoIncrementalForWorkplaceNo1 = dbController.getNextAutoIncremental("Workplaces");

        iWorkPlaceDAO.createWorkPlace(workPlaceDTONo1);

        int noOfWorkplacesInTable = iWorkPlaceDAO.getWorkPlaceList().size();

        assertEquals(noOfWorkplacesInTable, 1);

        iWorkPlaceDAO.deleteWorkPlace(nextAutoIncrementalForWorkplaceNo1);

        int noOfWorkPlacesInTableAfterDelettion = iWorkPlaceDAO.getWorkPlaceList().size();

        assertEquals(noOfWorkPlacesInTableAfterDelettion,0);

    }

}