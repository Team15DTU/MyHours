package dao.employer;

import dao.DALException;
import dto.employer.EmployerDTO;
import dto.employer.IEmployerDTO;
import db.DBController;
import db.IConnPool;
import db.TestConnPoolV1;
import org.junit.*;

import java.awt.*;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Rasmus Sander Larsen
 */
public class EmployerDAOTest
{
    private static IConnPool test_DB;

    private static DBController dbController;

    private static IEmployerDAO iEmployerDAO;

    // region Test Material

    // region WorkplaceDTO No. 1

    private int workplaceNo1_workerID = 1;
    private String workplaceNo1_name = "Bilka - Hundige";
    private Color workplaceNo1_color = Color.decode("#FAEBD7");
    private int workplaceNo1_telephone = 12345678;
    private int workplaceNo1_workplaceID_assigned;

    private IEmployerDTO workPlaceDTONo1 = new EmployerDTO();

    // endregion

    // region WorkplaceDTO No. 2

    private int workplaceNo2_workerID = 2;
    private String workplaceNo2_name = "DTU - Lyngby";
    private Color workplaceNo2_color = Color.decode("#55a7e4");
    private int workplaceNo2_telephone = 87654321;
    int workplaceNo2_workplaceID_assigned;

    private IEmployerDTO workPlaceDTONo2 = new EmployerDTO();

    // endregion

    public EmployerDAOTest() throws DALException
    {
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
    
    @BeforeClass
    public static void setUp() throws Exception
    {
        test_DB = TestConnPoolV1.getInstance();
    
        dbController = DBController.getInstance();  //TODO: Use change ConnectionPool method here
    
        iEmployerDAO = dbController.getiEmployerDAO();
    }
    
    @AfterClass
    public static void tearDown() throws Exception
    {
        test_DB.closePool();
    }
    
    /*
    ----------------------------- TESTS -----------------------------
     */

    @Test
    public void createWorkPlaces() throws DALException {

        // WorkplaceDTO No. 1

        int nextAutoIncrementalForWorkplaceNo1 = dbController.getNextAutoIncremental("Workplaces");

        iEmployerDAO.createIWorkPlace(workPlaceDTONo1);

        IEmployerDTO returnedWorkplaceDTOOfNo1 = iEmployerDAO.getIWorkPlace(nextAutoIncrementalForWorkplaceNo1);

        assertEquals(returnedWorkplaceDTOOfNo1.getWorkplaceID(), nextAutoIncrementalForWorkplaceNo1);
        assertEquals(returnedWorkplaceDTOOfNo1.getWorkerID(), workplaceNo1_workerID);
        assertEquals(returnedWorkplaceDTOOfNo1.getName(), workplaceNo1_name);
        assertEquals(returnedWorkplaceDTOOfNo1.getColor(), workplaceNo1_color);
        assertEquals(returnedWorkplaceDTOOfNo1.getTelephone(),workplaceNo1_telephone);

        iEmployerDAO.deleteIWorkPlace(nextAutoIncrementalForWorkplaceNo1);

    }

    @Test
    public void getWorkPlaceList() throws DALException {

        // Creates WorkplaceDTO No. 1

        int nextAutoIncrementalForWorkplaceNo1 = dbController.getNextAutoIncremental("Workplaces");

        iEmployerDAO.createIWorkPlace(workPlaceDTONo1);

        // Creates WorkplaceDTO No. 2

        int nextAutoIncrementalForWorkplaceNo2 = dbController.getNextAutoIncremental("Workplaces");

        iEmployerDAO.createIWorkPlace(workPlaceDTONo2);

        // Test of getIWorkPlaceList()

        List<IEmployerDTO> workPlaceDTOList;

        workPlaceDTOList= iEmployerDAO.getIWorkPlaceList();

        assertEquals(workPlaceDTOList.size(),2);

        iEmployerDAO.deleteIWorkPlace(nextAutoIncrementalForWorkplaceNo1);
        iEmployerDAO.deleteIWorkPlace(nextAutoIncrementalForWorkplaceNo2);

    }

    @Test
    public void getWorkPlaceListFromWorkerID() throws DALException {

        // Creates WorkplaceDTO No. 1

        int nextAutoIncrementalForWorkplaceNo1 = dbController.getNextAutoIncremental("Workplaces");

        iEmployerDAO.createIWorkPlace(workPlaceDTONo1);

        // Creates WorkplaceDTO No. 2

        int nextAutoIncrementalForWorkplaceNo2 = dbController.getNextAutoIncremental("Workplaces");

        iEmployerDAO.createIWorkPlace(workPlaceDTONo2);

        // Test of getIWorkPlaceList(int workerID)

        List<IEmployerDTO> workPlaceDTOListFromWorkerID;

        workPlaceDTOListFromWorkerID = iEmployerDAO.getIWorkPlaceList(workplaceNo1_workerID);

        assertEquals(workPlaceDTOListFromWorkerID.size(),1);

        iEmployerDAO.deleteIWorkPlace(nextAutoIncrementalForWorkplaceNo1);
        iEmployerDAO.deleteIWorkPlace(nextAutoIncrementalForWorkplaceNo2);

    }

    @Test
    public void getWorkPlace() throws DALException {

        // WorkplaceDTO No. 2

        int nextAutoIncrementalForWorkplaceNo2 = dbController.getNextAutoIncremental("Workplaces");

        iEmployerDAO.createIWorkPlace(workPlaceDTONo2);

        IEmployerDTO returnedWorkplaceDTOOfNo2 = iEmployerDAO.getIWorkPlace(nextAutoIncrementalForWorkplaceNo2);

        assertEquals(returnedWorkplaceDTOOfNo2.getWorkplaceID(), nextAutoIncrementalForWorkplaceNo2);
        assertEquals(returnedWorkplaceDTOOfNo2.getWorkerID(), workplaceNo2_workerID);
        assertEquals(returnedWorkplaceDTOOfNo2.getName(), workplaceNo2_name);
        assertEquals(returnedWorkplaceDTOOfNo2.getColor(), workplaceNo2_color);
        assertEquals(returnedWorkplaceDTOOfNo2.getTelephone(),workplaceNo2_telephone);

        iEmployerDAO.deleteIWorkPlace(nextAutoIncrementalForWorkplaceNo2);

    }

    @Test
    public void updateWorkPlace() throws DALException {

        // WorkplaceDTO No. 1

        int nextAutoIncrementalForWorkplaceNo1 = dbController.getNextAutoIncremental("Workplaces");

        iEmployerDAO.createIWorkPlace(workPlaceDTONo1);

        IEmployerDTO workplaceDTOOfNo1ToUpdate = iEmployerDAO.getIWorkPlace(nextAutoIncrementalForWorkplaceNo1);

        // Values of WorkplaceDTO No. 2 inserted into "workplaceDTOOfNo1ToUpdate".

        workplaceDTOOfNo1ToUpdate.setWorkerID(workplaceNo2_workerID);
        workplaceDTOOfNo1ToUpdate.setName(workplaceNo2_name);
        workplaceDTOOfNo1ToUpdate.setColor(workplaceNo2_color);
        workplaceDTOOfNo1ToUpdate.setTelephone(workplaceNo2_telephone);

        // Updates the WorkplaceDTO with the information that is passed into the method

        iEmployerDAO.updateIWorkPlace(workplaceDTOOfNo1ToUpdate);

        assertEquals(workplaceDTOOfNo1ToUpdate.getWorkplaceID(), nextAutoIncrementalForWorkplaceNo1);
        assertEquals(workplaceDTOOfNo1ToUpdate.getWorkerID(), workplaceNo2_workerID);
        assertEquals(workplaceDTOOfNo1ToUpdate.getName(), workplaceNo2_name);
        assertEquals(workplaceDTOOfNo1ToUpdate.getColor(), workplaceNo2_color);
        assertEquals(workplaceDTOOfNo1ToUpdate.getTelephone(),workplaceNo2_telephone);

        // Deletes the created Workplace

        iEmployerDAO.deleteIWorkPlace(nextAutoIncrementalForWorkplaceNo1);

    }
    
    @Test
    public void deleteWorkPlace() throws DALException {

        // WorkplaceDTO No. 1

        int nextAutoIncrementalForWorkplaceNo1 = dbController.getNextAutoIncremental("Workplaces");

        iEmployerDAO.createIWorkPlace(workPlaceDTONo1);

        int noOfWorkplacesInTable = iEmployerDAO.getIWorkPlaceList().size();

        assertEquals(noOfWorkplacesInTable, 1);

        iEmployerDAO.deleteIWorkPlace(nextAutoIncrementalForWorkplaceNo1);

        int noOfWorkPlacesInTableAfterDelettion = iEmployerDAO.getIWorkPlaceList().size();

        assertEquals(noOfWorkPlacesInTableAfterDelettion,0);

    }

}