package dao.employer;

import dao.DALException;
import dao.worker.IWorkerDAO;
import dto.employer.EmployerDTO;
import dto.employer.IEmployerDTO;
import db.DBController;
import db.IConnPool;
import db.TestConnPoolV1;
import dto.worker.IWorkerDTO;
import dto.worker.WorkerHiberDTO;
import org.junit.*;

import java.awt.*;
import java.time.LocalDate;
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

    private static IWorkerDAO iWorkerDAO;

    // region Test Material

    // region EmployerDTO No. 1

    private int employerNo1_workerID_assigned;
    private String employerNo1_name = "Bilka - Hundige";
    private Color employerNo1_color = Color.decode("#FAEBD7");
    private String employerNo1_telephone = "12345678";
    private int employereNo1_id_assigned;

    private IEmployerDTO employerNo1 = new EmployerDTO();

    // endregion

    // region EmployerDTO No. 2

    private int employerNo2_workerID_assigned;
    private String employerNo2_name = "DTU - Lyngby";
    private Color employerNo2_color = Color.decode("#55a7e4");
    private String employerNo2_telephone = "87654321";
    int employerNo2_id_assigned;

    private IEmployerDTO employerNo2 = new EmployerDTO();

    // endregion

    //region WorkerDTO No. 1

    private int tw1_workerID_assigned;
    private String tw1_firstName = "FirstName1";
    private String tw1_surName = "SurName1";
    private String tw1_email = "test1@test.dk";
    private String tw1_pass = "test1Password";
    private LocalDate tw1_birthday = LocalDate.now();
    private IWorkerDTO testWorker1 = new WorkerHiberDTO();

    //endregion

    public EmployerDAOTest() throws DALException
    {
        // Finds the next id for a new Worker.
        //tw1_workerID_assigned = dbController.getNextAutoIncremental(WorkerConstants.TABLENAME); TODO: Skal op at køre igen.
        employerNo1_workerID_assigned = tw1_workerID_assigned=3;
        employerNo2_workerID_assigned = tw1_workerID_assigned;

        // Setting up testWorker1
        testWorker1 = new WorkerHiberDTO();
        testWorker1.setFirstName(tw1_firstName);
        testWorker1.setSurName(tw1_surName);
        testWorker1.setEmail(tw1_email);
        testWorker1.setPassword(tw1_pass);
        testWorker1.setBirthday(tw1_birthday);

        // Creates a new Worker in the test DB.
        //iWorkerDAO.createWorker(testWorker1); TODO: Der skal creates og deletes, skal have fikset metoderne.

        // Setting up employerNo1
        employerNo1.setWorkerID(employerNo1_workerID_assigned);
        employerNo1.setName(employerNo1_name);
        employerNo1.setColor(employerNo1_color);
        employerNo1.setTelephone(employerNo1_telephone);

        // Setting up employerNo2
        employerNo2.setWorkerID(employerNo2_workerID_assigned);
        employerNo2.setName(employerNo2_name);
        employerNo2.setColor(employerNo2_color);
        employerNo2.setTelephone(employerNo2_telephone);
    }

    // endregion
    
    @BeforeClass
    public static void setUp() throws Exception
    {
        test_DB = TestConnPoolV1.getInstance();
    
        dbController = new DBController(test_DB);
    
        iEmployerDAO = dbController.getiEmployerDAO();
        iWorkerDAO  = dbController.getiWorkerDAO();
    }
    
    @AfterClass
    public static void tearDown() throws Exception
    {
        test_DB.closePool();
        //iWorkerDAO.deleteWorker("test1@test.dk");
    }
    
    /*
    ----------------------------- TESTS -----------------------------
     */

    @Test
    public void createWorkPlaces() throws DALException {

        // WorkplaceDTO No. 1

        int nextAutoIncrementalForWorkplaceNo1 = dbController.getNextAutoIncremental("Employers");

        iEmployerDAO.createiEmployer(employerNo1);

        IEmployerDTO returnedWorkplaceDTOOfNo1 = iEmployerDAO.getIEmployer(nextAutoIncrementalForWorkplaceNo1);

        assertEquals(returnedWorkplaceDTOOfNo1.getWorkplaceID(), nextAutoIncrementalForWorkplaceNo1);
        assertEquals(returnedWorkplaceDTOOfNo1.getWorkerID(), employerNo1_workerID_assigned);
        assertEquals(returnedWorkplaceDTOOfNo1.getName(), employerNo1_name);
        assertEquals(returnedWorkplaceDTOOfNo1.getColor(), employerNo1_color);
        assertEquals(returnedWorkplaceDTOOfNo1.getTelephone(), employerNo1_telephone);

        iEmployerDAO.deleteiEmployer(nextAutoIncrementalForWorkplaceNo1);

    }

    @Test
    public void getWorkPlaceList() throws DALException {

        // Creates WorkplaceDTO No. 1

        int nextAutoIncrementalForWorkplaceNo1 = dbController.getNextAutoIncremental("Workplaces");

        iEmployerDAO.createiEmployer(employerNo1);

        // Creates WorkplaceDTO No. 2

        int nextAutoIncrementalForWorkplaceNo2 = dbController.getNextAutoIncremental("Workplaces");

        iEmployerDAO.createiEmployer(employerNo2);

        // Test of getiEmployerList()

        List<IEmployerDTO> workPlaceDTOList;

        workPlaceDTOList= iEmployerDAO.getiEmployerList();

        assertEquals(workPlaceDTOList.size(),2);

        iEmployerDAO.deleteiEmployer(nextAutoIncrementalForWorkplaceNo1);
        iEmployerDAO.deleteiEmployer(nextAutoIncrementalForWorkplaceNo2);

    }

    @Test
    public void getWorkPlaceListFromWorkerID() throws DALException {

        // Creates WorkplaceDTO No. 1

        int nextAutoIncrementalForWorkplaceNo1 = dbController.getNextAutoIncremental("Workplaces");

        iEmployerDAO.createiEmployer(employerNo1);

        // Creates WorkplaceDTO No. 2

        int nextAutoIncrementalForWorkplaceNo2 = dbController.getNextAutoIncremental("Workplaces");

        iEmployerDAO.createiEmployer(employerNo2);

        // Test of getiEmployerList(int workerID)

        List<IEmployerDTO> workPlaceDTOListFromWorkerID;

        workPlaceDTOListFromWorkerID = iEmployerDAO.getiEmployerList(employerNo1_workerID_assigned);

        assertEquals(workPlaceDTOListFromWorkerID.size(),1);

        iEmployerDAO.deleteiEmployer(nextAutoIncrementalForWorkplaceNo1);
        iEmployerDAO.deleteiEmployer(nextAutoIncrementalForWorkplaceNo2);

    }

    @Test
    public void getEmployer() throws DALException {

        // WorkplaceDTO No. 2

        int nextAutoIncrementalForWorkplaceNo2 = dbController.getNextAutoIncremental(EmployerConstants.TABLENAME);

        iEmployerDAO.createiEmployer(employerNo2);

        IEmployerDTO returnedWorkplaceDTOOfNo2 = iEmployerDAO.getIEmployer(nextAutoIncrementalForWorkplaceNo2);

        assertEquals(returnedWorkplaceDTOOfNo2.getWorkplaceID(), nextAutoIncrementalForWorkplaceNo2);
        assertEquals(returnedWorkplaceDTOOfNo2.getWorkerID(), employerNo2_workerID_assigned);
        assertEquals(returnedWorkplaceDTOOfNo2.getName(), employerNo2_name);
        assertEquals(returnedWorkplaceDTOOfNo2.getColor(), employerNo2_color);
        assertEquals(returnedWorkplaceDTOOfNo2.getTelephone(), employerNo2_telephone);

        iEmployerDAO.deleteiEmployer(nextAutoIncrementalForWorkplaceNo2);

    }

    @Test
    public void updateWorkPlace() throws DALException {

        // WorkplaceDTO No. 1

        int nextAutoIncrementalForWorkplaceNo1 = dbController.getNextAutoIncremental("Workplaces");

        iEmployerDAO.createiEmployer(employerNo1);

        IEmployerDTO workplaceDTOOfNo1ToUpdate = iEmployerDAO.getIEmployer(nextAutoIncrementalForWorkplaceNo1);

        // Values of WorkplaceDTO No. 2 inserted into "workplaceDTOOfNo1ToUpdate".

        workplaceDTOOfNo1ToUpdate.setWorkerID(employerNo2_workerID_assigned);
        workplaceDTOOfNo1ToUpdate.setName(employerNo2_name);
        workplaceDTOOfNo1ToUpdate.setColor(employerNo2_color);
        workplaceDTOOfNo1ToUpdate.setTelephone(employerNo2_telephone);

        // Updates the WorkplaceDTO with the information that is passed into the method

        iEmployerDAO.updateiEmployer(workplaceDTOOfNo1ToUpdate);

        assertEquals(workplaceDTOOfNo1ToUpdate.getWorkplaceID(), nextAutoIncrementalForWorkplaceNo1);
        assertEquals(workplaceDTOOfNo1ToUpdate.getWorkerID(), employerNo2_workerID_assigned);
        assertEquals(workplaceDTOOfNo1ToUpdate.getName(), employerNo2_name);
        assertEquals(workplaceDTOOfNo1ToUpdate.getColor(), employerNo2_color);
        assertEquals(workplaceDTOOfNo1ToUpdate.getTelephone(), employerNo2_telephone);

        // Deletes the created Workplace

        iEmployerDAO.deleteiEmployer(nextAutoIncrementalForWorkplaceNo1);

    }
    
    @Test
    public void deleteWorkPlace() throws DALException {

        // WorkplaceDTO No. 1

        int nextAutoIncrementalForWorkplaceNo1 = dbController.getNextAutoIncremental(EmployerConstants.TABLENAME);

        iEmployerDAO.createiEmployer(employerNo1);

        int noOfWorkplacesInTable = iEmployerDAO.getiEmployerList().size();

        assertEquals(noOfWorkplacesInTable, 1);

        iEmployerDAO.deleteiEmployer(nextAutoIncrementalForWorkplaceNo1);

        int noOfWorkPlacesInTableAfterDeletion = iEmployerDAO.getiEmployerList().size();

        assertEquals(noOfWorkPlacesInTableAfterDeletion,0);

    }

}