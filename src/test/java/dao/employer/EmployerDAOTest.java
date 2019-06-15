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
import hibernate.HibernateProperties;
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

    private static int employerNo1_workerID_assigned;
    private static String employerNo1_name = "EmployerName1";
    private static Color employerNo1_color = Color.decode("#FAEBD7");
    private static String employerNo1_telephone = "12345678";
    private static int employereNo1_id_assigned;

    private static IEmployerDTO employerNo1;

    // endregion

    // region EmployerDTO No. 2

    private static int employerNo2_workerID_assigned;
    private static String employerNo2_name = "EmployerName2";
    private static Color employerNo2_color = Color.decode("#55a7e4");
    private static String employerNo2_telephone = "87654321";
    private static int employerNo2_id_assigned;

    private static IEmployerDTO employerNo2;

    // endregion

    //region WorkerDTO No. 1

    private int tw1_workerID_assigned;
    private static String tw1_firstName = "FirstName1";
    private static String tw1_surName = "SurName1";
    private static String tw1_email = "test1@test.dk";
    private static String tw1_pass = "test1Password";
    private static LocalDate tw1_birthday = LocalDate.now();
    private static IWorkerDTO testWorker1;

    //endregion

    private static void setupTestData() throws DALException
    {
        // Setting up testWorker1
        testWorker1 = new WorkerHiberDTO();
        testWorker1.setFirstName(tw1_firstName);
        testWorker1.setSurName(tw1_surName);
        testWorker1.setEmail(tw1_email);
        testWorker1.setPassword(tw1_pass);
        testWorker1.setBirthday(tw1_birthday);

        // Creates a new Worker in the test DB.
        iWorkerDAO.createWorker(testWorker1);

        // Setting up employerNo1
        employerNo1 = new EmployerDTO();
        employerNo1.setWorkerID(testWorker1.getWorkerID());
        employerNo1.setName(employerNo1_name);
        employerNo1.setColor(employerNo1_color);
        employerNo1.setTelephone(employerNo1_telephone);

        // Setting up employerNo2
        employerNo2 = new EmployerDTO();
        employerNo2.setWorkerID(testWorker1.getWorkerID());
        employerNo2.setName(employerNo2_name);
        employerNo2.setColor(employerNo2_color);
        employerNo2.setTelephone(employerNo2_telephone);
    }

    // endregion

    @BeforeClass
    public static void setUp() throws Exception
    {
        // Setup DAOs, ConnectionPools and DBControllers.
        test_DB = TestConnPoolV1.getInstance();
        dbController = new DBController(test_DB, new HibernateProperties().getTestDB());
        iEmployerDAO = dbController.getiEmployerDAO();
        iWorkerDAO  = dbController.getiWorkerDAO();

        // Clear both Employers and Workers table.
        clearBothTestTables();

        // Setup necessary test IWorkerDTO and IEmployer objects.
        setupTestData();
    }

    @AfterClass
    public static void tearDown() throws Exception
    {
        // Clean both Worker Tables (Employers table should already be cleared by @After method.
        clearWorkerTestTable();

        // Closes ConnectionPool.
        test_DB.closePool();
    }

    @After
    public void afterTest () throws DALException {
        clearEmployerTestTable();
    }

    /*
    ----------------------------- TESTS -----------------------------
     */

    @Test
    public void createEmployer() throws DALException {

        // WorkplaceDTO No. 1

        int nextAutoIncrementalForWorkplaceNo1 = dbController.getNextAutoIncremental("Employers");

        iEmployerDAO.createiEmployer(employerNo1);

        IEmployerDTO returnedWorkplaceDTOOfNo1 = iEmployerDAO.getIEmployer(nextAutoIncrementalForWorkplaceNo1);

        assertEquals(returnedWorkplaceDTOOfNo1.getEmployerID(), nextAutoIncrementalForWorkplaceNo1);
        assertEquals(returnedWorkplaceDTOOfNo1.getWorkerID(), testWorker1.getWorkerID());
        assertEquals(returnedWorkplaceDTOOfNo1.getName(), employerNo1_name);
        assertEquals(returnedWorkplaceDTOOfNo1.getColor(), employerNo1_color);
        assertEquals(returnedWorkplaceDTOOfNo1.getTelephone(), employerNo1_telephone);

        iEmployerDAO.deleteiEmployer(nextAutoIncrementalForWorkplaceNo1);

    }

    @Test
    public void getEmployerList() throws DALException {

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

        int nextAutoIncrementalForWorkplaceNo1 = dbController.getNextAutoIncremental(EmployerConstants.TABLENAME);

        iEmployerDAO.createiEmployer(employerNo1);

        // Creates WorkplaceDTO No. 2

        int nextAutoIncrementalForWorkplaceNo2 = dbController.getNextAutoIncremental(EmployerConstants.TABLENAME);

        iEmployerDAO.createiEmployer(employerNo2);

        // Test of getiEmployerList(int workerID)

        List<IEmployerDTO> workPlaceDTOListFromWorkerID;

        workPlaceDTOListFromWorkerID = iEmployerDAO.getiEmployerList(testWorker1.getWorkerID());

        assertEquals(2, workPlaceDTOListFromWorkerID.size()); // TODO: Der skal oprettes en ekstra TestWorker

        iEmployerDAO.deleteiEmployer(nextAutoIncrementalForWorkplaceNo1);
        iEmployerDAO.deleteiEmployer(nextAutoIncrementalForWorkplaceNo2);

    }

    @Test
    public void getEmployer() throws DALException {

        // WorkplaceDTO No. 2

        int nextAutoIncrementalForWorkplaceNo2 = dbController.getNextAutoIncremental(EmployerConstants.TABLENAME);

        iEmployerDAO.createiEmployer(employerNo2);

        IEmployerDTO returnedWorkplaceDTOOfNo2 = iEmployerDAO.getIEmployer(nextAutoIncrementalForWorkplaceNo2);

        assertEquals(returnedWorkplaceDTOOfNo2.getEmployerID(), nextAutoIncrementalForWorkplaceNo2);
        assertEquals(returnedWorkplaceDTOOfNo2.getWorkerID(), testWorker1.getWorkerID());
        assertEquals(returnedWorkplaceDTOOfNo2.getName(), employerNo2_name);
        assertEquals(returnedWorkplaceDTOOfNo2.getColor(), employerNo2_color);
        assertEquals(returnedWorkplaceDTOOfNo2.getTelephone(), employerNo2_telephone);

        iEmployerDAO.deleteiEmployer(nextAutoIncrementalForWorkplaceNo2);

    }

    @Test
    public void updateWorkPlace() throws DALException {

        // WorkplaceDTO No. 1

        int nextAutoIncrementalForWorkplaceNo1 = dbController.getNextAutoIncremental(EmployerConstants.TABLENAME);

        iEmployerDAO.createiEmployer(employerNo1);

        IEmployerDTO workplaceDTOOfNo1ToUpdate = iEmployerDAO.getIEmployer(nextAutoIncrementalForWorkplaceNo1);

        // Values of WorkplaceDTO No. 2 inserted into "workplaceDTOOfNo1ToUpdate".

        workplaceDTOOfNo1ToUpdate.setWorkerID(employerNo2_workerID_assigned);
        workplaceDTOOfNo1ToUpdate.setName(employerNo2_name);
        workplaceDTOOfNo1ToUpdate.setColor(employerNo2_color);
        workplaceDTOOfNo1ToUpdate.setTelephone(employerNo2_telephone);

        // Updates the WorkplaceDTO with the information that is passed into the method

        iEmployerDAO.updateiEmployer(workplaceDTOOfNo1ToUpdate);

        assertEquals(workplaceDTOOfNo1ToUpdate.getEmployerID(), nextAutoIncrementalForWorkplaceNo1);
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

        int nextAutoIncrementalForEmployerNo1 = dbController.getNextAutoIncremental(EmployerConstants.TABLENAME);

        iEmployerDAO.createiEmployer(employerNo1);

        int noOfEmployersInTable = iEmployerDAO.getiEmployerList().size();

        assertEquals(noOfEmployersInTable, 1);

        iEmployerDAO.deleteiEmployer(nextAutoIncrementalForEmployerNo1);

        int noOfEmployersInTableAfterDeletion = iEmployerDAO.getiEmployerList().size();

        assertEquals(noOfEmployersInTableAfterDeletion,0);

        // The created employer is deleted as part of the test.

    }


    private static void clearBothTestTables () throws DALException {
        // Deletes all Employers from DB.
        for (IEmployerDTO employerDTO : iEmployerDAO.getiEmployerList()){
            iEmployerDAO.deleteiEmployer(employerDTO.getEmployerID());
        }

        // Deletes all workers from test DB.
        for (IWorkerDTO workerDTO : iWorkerDAO.getWorkerList()){
            iWorkerDAO.deleteWorker(workerDTO.getEmail());
        }
    }

    private static void clearEmployerTestTable () throws DALException {
        // Deletes all Employers from DB.
        for (IEmployerDTO employerDTO : iEmployerDAO.getiEmployerList()){
            iEmployerDAO.deleteiEmployer(employerDTO.getEmployerID());
        }
    }

    private static void clearWorkerTestTable () throws DALException {
        // Deletes all workers from test DB.
        for (IWorkerDTO workerDTO : iWorkerDAO.getWorkerList()){
            iWorkerDAO.deleteWorker(workerDTO.getEmail());
        }
    }



}