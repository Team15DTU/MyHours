package dao.employer;

import dao.DALException;
import dao.worker.IWorkerDAO;
import dto.employer.IEmployerDTO;
import db.DBController;
import db.IConnPool;
import db.TestConnPoolV1;
import dto.worker.IWorkerDTO;
import hibernate.HibernateProperties;
import org.junit.*;
import testData.TestDataController;

import static org.junit.Assert.assertEquals;

/**
 * @author Rasmus Sander Larsen
 */
public class EmployerDAOTest
{
    private static IConnPool test_DB;
    private static DBController dbController; // TODO: Skal DBControlleren bare væk?
    private static IEmployerDAO iEmployerDAO;
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
        dbController = new DBController(test_DB, new HibernateProperties().getTestDB());
        iEmployerDAO = dbController.getiEmployerDAO();
        iWorkerDAO  = dbController.getiWorkerDAO();

        // Clear both Employers and Workers table.
        TestDataController.clearEmployerTestTable(iEmployerDAO);
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
        TestDataController.clearEmployerTestTable(iEmployerDAO);
    }
    
    /*
    ----------------------------- TESTS -----------------------------
     */

    @Test
    public void createEmployer() throws DALException {

        iEmployerDAO.createiEmployer(employerNo1);

        IEmployerDTO returnedWorkplaceDTOOfNo1 = iEmployerDAO.getIEmployer(employerNo1.getEmployerID());

        assertEquals( employerNo1.getEmployerID(), returnedWorkplaceDTOOfNo1.getEmployerID());
        assertEquals( employerNo1.getWorkerID(),returnedWorkplaceDTOOfNo1.getWorkerID());
        assertEquals(employerNo1.getName(), returnedWorkplaceDTOOfNo1.getName());
        assertEquals(employerNo1.getColor(),returnedWorkplaceDTOOfNo1.getColor());
        assertEquals(employerNo1.getTelephone(),returnedWorkplaceDTOOfNo1.getTelephone());
    }

    @Test
    public void getEmployerList() throws DALException {

        iEmployerDAO.createiEmployer(employerNo1);
        assertEquals(1, iEmployerDAO.getiEmployerList().size());
        iEmployerDAO.createiEmployer(employerNo2);
        assertEquals(2, iEmployerDAO.getiEmployerList().size());
        iEmployerDAO.createiEmployer(employerNo3);
        assertEquals(3, iEmployerDAO.getiEmployerList().size());
    }

    @Test
    public void getEmployerListFromWorkerID() throws DALException {

        iEmployerDAO.createiEmployer(employerNo1);
        iEmployerDAO.createiEmployer(employerNo2);
        iEmployerDAO.createiEmployer(employerNo3);

        assertEquals(2, iEmployerDAO.getiEmployerList(testWorkerNo1.getWorkerID()) .size());
        assertEquals(1, iEmployerDAO.getiEmployerList(testWorkerNo2.getWorkerID()) .size());
    }

    @Test
    public void getEmployer() throws DALException {

        iEmployerDAO.createiEmployer(employerNo2);

        IEmployerDTO returnedEmployerNo2 = iEmployerDAO.getIEmployer(employerNo2.getEmployerID());

        assertEquals(employerNo2.getEmployerID(),returnedEmployerNo2.getEmployerID());
        assertEquals(employerNo2.getWorkerID(),returnedEmployerNo2.getWorkerID());
        assertEquals(employerNo2.getName(),returnedEmployerNo2.getName());
        assertEquals(employerNo2.getColor(), returnedEmployerNo2.getColor());
        assertEquals(employerNo2.getTelephone(), returnedEmployerNo2.getTelephone());
    }

    @Test
    public void updateWorkPlace() throws DALException {

        iEmployerDAO.createiEmployer(employerNo1);

        IEmployerDTO updatedEmployerNo1 = employerNo1;

        updatedEmployerNo1.setName(employerNo2.getName());
        updatedEmployerNo1.setColor(employerNo2.getColor());
        updatedEmployerNo1.setTelephone(employerNo2.getTelephone());

        // Updates the WorkplaceDTO with the information that is passed into the method
        iEmployerDAO.updateiEmployer(employerNo1);

        IEmployerDTO returnedUpdatedEmployerNo1 = iEmployerDAO.getIEmployer(employerNo1.getEmployerID());

        assertEquals(employerNo1.getEmployerID(), returnedUpdatedEmployerNo1.getEmployerID());
        assertEquals(employerNo1.getWorkerID(), returnedUpdatedEmployerNo1.getWorkerID());
        assertEquals(employerNo1.getName(), employerNo2.getName());
        assertEquals(employerNo1.getColor(), employerNo2.getColor());
        assertEquals(employerNo1.getTelephone(), employerNo2.getTelephone());
    }
    
    @Test
    public void deleteWorkPlace() throws DALException {

        iEmployerDAO.createiEmployer(employerNo1);

        assertEquals(1, iEmployerDAO.getiEmployerList().size());

        iEmployerDAO.deleteiEmployer(employerNo1.getEmployerID());

        assertEquals(0, iEmployerDAO.getiEmployerList().size());
    }

}