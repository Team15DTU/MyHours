package dao.employer;

import dao.ConnectionHelper;
import dao.DALException;
import dao.worker.IWorkerDAO;
import dao.worker.WorkerDAO;
import dto.employer.IEmployerDTO;
import db.IConnPool;
import db.TestConnPoolV1;
import dto.worker.IWorkerDTO;
import org.junit.*;
import testData.TestDataController;

import static org.junit.Assert.assertEquals;

/**
 * @author Rasmus Sander Larsen
 */
public class EmployerDAOTest
{
    private static IConnPool test_DB;
    private static IEmployerDAO employerDAO;
    private static IWorkerDAO iWorkerDAO;

    // Test Objects
    private static IWorkerDTO testWorkerNo1;
    private static IWorkerDTO testWorkerNo2;
    private static IEmployerDTO employerNo1;
    private static IEmployerDTO employerNo2;
    private static IEmployerDTO employerNo3;

    private static void setupTestData() throws DALException
    {
        // Setting up testWorkerNo1
        testWorkerNo1 = TestDataController.getTestWorkerNo1();

        // Setting up testWorkerNo2
        testWorkerNo2 = TestDataController.getTestWorkerNo2();

        // Creates a new Workers in the test DB.
        iWorkerDAO.createWorker(testWorkerNo1);
        iWorkerDAO.createWorker(testWorkerNo2);

        // Setting up employerNo1 (Belongs to testWorkerNo1)
        employerNo1 = TestDataController.getEmployerNo1(testWorkerNo1);

        // Setting up employerNo2 (Belongs to testWorkerNo1)
        employerNo2 = TestDataController.getEmployerNo2(testWorkerNo1);

        // Setting up employerNo3 (Belongs to testWorkerNo2)
        employerNo3 = TestDataController.getEmployerNo3(testWorkerNo2);

    }

    // endregion

    @BeforeClass
    public static void setUp() throws Exception
    {
        // Setup DAOs, ConnectionPools and DBControllers.
        test_DB = TestConnPoolV1.getInstance();
        employerDAO = new EmployerDAO(test_DB, new ConnectionHelper(test_DB));
        iWorkerDAO  = new WorkerDAO(test_DB);

        // Clear both Employers and Workers table.
        TestDataController.clearEmployerTestTable(employerDAO);
        TestDataController.clearWorkerTestTable(iWorkerDAO);

        // Setup necessary test IWorkerDTO and IEmployer objects.
        setupTestData();
    }

    @AfterClass
    public static void tearDown() throws Exception
    {
        // Clean both Worker Tables (Employers table should already be cleared by @After method.
        TestDataController.clearWorkerTestTable(iWorkerDAO);

        // Closes ConnectionPool.
        test_DB.closePool();
    }

    @After
    public void afterTest () throws DALException {
        // Clears Employers table between all tests
        TestDataController.clearEmployerTestTable(employerDAO);
    }

    /*
    ----------------------------- TESTS -----------------------------
     */

    @Test
    public void createEmployer() throws DALException {

        employerDAO.createiEmployer(employerNo1);

        IEmployerDTO returnedWorkplaceDTOOfNo1 = employerDAO.getIEmployer(employerNo1.getEmployerID());

        assertEquals( employerNo1.getEmployerID(), returnedWorkplaceDTOOfNo1.getEmployerID());
        assertEquals( employerNo1.getWorkerID(),returnedWorkplaceDTOOfNo1.getWorkerID());
        assertEquals(employerNo1.getName(), returnedWorkplaceDTOOfNo1.getName());
        assertEquals(employerNo1.getColor(),returnedWorkplaceDTOOfNo1.getColor());
        assertEquals(employerNo1.getTelephone(),returnedWorkplaceDTOOfNo1.getTelephone());
    }

    @Test
    public void getEmployerList() throws DALException {

        employerDAO.createiEmployer(employerNo1);
        assertEquals(1, employerDAO.getiEmployerList().size());
        employerDAO.createiEmployer(employerNo2);
        assertEquals(2, employerDAO.getiEmployerList().size());
        employerDAO.createiEmployer(employerNo3);
        assertEquals(3, employerDAO.getiEmployerList().size());
    }

    @Test
    public void getEmployerListFromWorkerID() throws DALException {

        employerDAO.createiEmployer(employerNo1);
        employerDAO.createiEmployer(employerNo2);
        employerDAO.createiEmployer(employerNo3);

        assertEquals(2, employerDAO.getiEmployerList(testWorkerNo1.getWorkerID()) .size());
        assertEquals(1, employerDAO.getiEmployerList(testWorkerNo2.getWorkerID()) .size());
    }

    @Test
    public void getEmployer() throws DALException {

        employerDAO.createiEmployer(employerNo2);

        IEmployerDTO returnedEmployerNo2 = employerDAO.getIEmployer(employerNo2.getEmployerID());

        assertEquals(employerNo2.getEmployerID(),returnedEmployerNo2.getEmployerID());
        assertEquals(employerNo2.getWorkerID(),returnedEmployerNo2.getWorkerID());
        assertEquals(employerNo2.getName(),returnedEmployerNo2.getName());
        assertEquals(employerNo2.getColor(), returnedEmployerNo2.getColor());
        assertEquals(employerNo2.getTelephone(), returnedEmployerNo2.getTelephone());
    }

    @Test
    public void updateWorkPlace() throws DALException {

        employerDAO.createiEmployer(employerNo1);

        IEmployerDTO updatedEmployerNo1 = employerNo1;

        updatedEmployerNo1.setName(employerNo2.getName());
        updatedEmployerNo1.setColor(employerNo2.getColor());
        updatedEmployerNo1.setTelephone(employerNo2.getTelephone());

        // Updates the WorkplaceDTO with the information that is passed into the method
        employerDAO.updateiEmployer(employerNo1);

        IEmployerDTO returnedUpdatedEmployerNo1 = employerDAO.getIEmployer(employerNo1.getEmployerID());

        assertEquals(employerNo1.getEmployerID(), returnedUpdatedEmployerNo1.getEmployerID());
        assertEquals(employerNo1.getWorkerID(), returnedUpdatedEmployerNo1.getWorkerID());
        assertEquals(employerNo1.getName(), employerNo2.getName());
        assertEquals(employerNo1.getColor(), employerNo2.getColor());
        assertEquals(employerNo1.getTelephone(), employerNo2.getTelephone());
    }

    @Test
    public void deleteWorkPlace() throws DALException {

        employerDAO.createiEmployer(employerNo1);

        assertEquals(1, employerDAO.getiEmployerList().size());

        employerDAO.deleteiEmployer(employerNo1.getEmployerID());

        assertEquals(0, employerDAO.getiEmployerList().size());
    }

}