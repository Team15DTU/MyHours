package dao.activity;

import dao.DALException;
import dao.employer.IEmployerDAO;
import dao.job.IJobDAO;
import dao.worker.IWorkerDAO;
import db.DBController;
import db.IConnPool;
import db.TestConnPoolV1;
import dto.activity.IActivityDTO;
import dto.employer.IEmployerDTO;
import dto.job.IJobDTO;
import dto.worker.IWorkerDTO;
import hibernate.HibernateProperties;
import org.junit.*;
import testData.TestDataController;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Rasmus Sander Larsen
 */
public class ActivityDAOTest {

    private final double doubleEqualTolorance = 0.0001; // TODO: Find rigtig værdi
    private final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static IConnPool test_DB;
    private static IEmployerDAO iEmployerDAO;
    private static IWorkerDAO iWorkerDAO;
    private static IJobDAO iJobDAO;
    private static IActivityDAO iActivityDAO;

    // region Test Material

    // Test Objects
    private static IWorkerDTO testWorkerNo1;
    private static IWorkerDTO testWorkerNo2;
    private static IEmployerDTO employerNo1;
    private static IEmployerDTO employerNo2;
    private static IEmployerDTO employerNo3;
    private static IJobDTO jobNo1;
    private static IJobDTO jobNo2;
    private static IJobDTO jobNo3;
    private static IActivityDTO activityNo1;
    private static IActivityDTO activityNo2;
    private static IActivityDTO activityNo3;

    private static void setupTestData() throws DALException
    {
        // region Setting up Test Workers
        // Setting up testWorkerNo1
        testWorkerNo1 = TestDataController.getTestWorkerNo1();
        // Setting up testWorkerNo2
        testWorkerNo2 = TestDataController.getTestWorkerNo2();

        // Creates a new Workers in the test DB.
        iWorkerDAO.createWorker(testWorkerNo1);
        iWorkerDAO.createWorker(testWorkerNo2);
        // endregion

        // region Setting up Test Employers
        // Setting up employerNo1 (Belongs to testWorkerNo1)
        employerNo1 = TestDataController.getEmployerNo1(testWorkerNo1);
        // Setting up employerNo2 (Belongs to testWorkerNo1)
        employerNo2 = TestDataController.getEmployerNo2(testWorkerNo1);
        // Setting up employerNo3 (Belongs to testWorkerNo2)
        employerNo3 = TestDataController.getEmployerNo3(testWorkerNo2);

        // Creates Test Employers in test DB
        iEmployerDAO.createiEmployer(employerNo1);
        iEmployerDAO.createiEmployer(employerNo2);
        iEmployerDAO.createiEmployer(employerNo3); // TODO: Skal der kun være 2 employers ?

        // endregion

        // region Setting up Test Jobs
        // Setting up jobNo1 (Belongs to EmployerNo1)
        jobNo1 = TestDataController.getJobNo1(employerNo1);
        // Setting up jobNo2 (Belongs to EmployerNo1)
        jobNo2 = TestDataController.getJobNo2(employerNo1);
        // Setting up jobNo3 (Belongs to EmployerNo2)
        jobNo3 = TestDataController.getJobNo3(employerNo2);
        // Creating Test Jobs in test DB
        iJobDAO.createIJob(jobNo1);
        iJobDAO.createIJob(jobNo2);
        iJobDAO.createIJob(jobNo3);
        // endregion

        // region Setting up Test Activities
        //Setting up ActivityNo1 (Belongs to JobNo1)
        activityNo1 = TestDataController.getActivityNo1(jobNo1);
        //Setting up ActivityNo1 (Belongs to JobNo1)
        activityNo2 = TestDataController.getActivityNo2(jobNo1);
        //Setting up ActivityNo1 (Belongs to JobNo2)
        activityNo3 = TestDataController.getActivityNo3(jobNo2);

        // endregion
    }

    // endregion

    // region Before and After annotations

    @BeforeClass
    public static void setUp() throws Exception {

        // Setup DAOs, ConnectionPools and DBControllers.
        test_DB = TestConnPoolV1.getInstance();
        DBController dbController = new DBController(test_DB, new HibernateProperties().getTestDB());

        iWorkerDAO  = dbController.getiWorkerDAO();
        iEmployerDAO = dbController.getiEmployerDAO();
        iJobDAO = dbController.getiJobDAO();
        iActivityDAO = dbController.getiActivityDAO();

        // Clear Activities, Jobs, Employers and Workers table in test DB.
        TestDataController.clearActivityTestTable(iActivityDAO);
        TestDataController.clearJobTestTable(iJobDAO);
        TestDataController.clearEmployerTestTable(iEmployerDAO);
        TestDataController.clearWorkerTestTable(iWorkerDAO);

        // Setup necessary test IWorkerDTO and IEmployer objects.
        setupTestData();
    }

    @AfterClass
    public static void tearDown() throws DALException {
        // Clears all tables
        TestDataController.clearActivityTestTable(iActivityDAO);
        TestDataController.clearJobTestTable(iJobDAO);
        TestDataController.clearEmployerTestTable(iEmployerDAO);
        TestDataController.clearWorkerTestTable(iWorkerDAO);

        // Closes ConnectionPool
        test_DB.closePool();
    }

    @After
    public void afterEachTest() throws DALException {
        TestDataController.clearActivityTestTable(iActivityDAO);
    }

    // endregion

    @Test
    public void getiActivity() throws DALException {
        iActivityDAO.createiActivity(activityNo1);

        IActivityDTO returnedActivityNo1 = iActivityDAO.getiActivity(activityNo1.getActivityID());

        assertEquals(activityNo1.getActivityID(),returnedActivityNo1.getActivityID());
        assertEquals(activityNo1.getJobID(),returnedActivityNo1.getJobID());
        assertEquals(dtf.format(activityNo1.getStartingDateTime()),dtf.format(returnedActivityNo1.getStartingDateTime()));
        assertEquals(dtf.format(activityNo1.getEndingDateTime()),dtf.format(returnedActivityNo1.getEndingDateTime()));
        assertEquals(activityNo1.getPause(),returnedActivityNo1.getPause());
        assertEquals(activityNo1.getActivityValue(),returnedActivityNo1.getActivityValue(),doubleEqualTolorance);
    }

    @Test
    public void getiActivity1() {
    }

    @Test
    public void getiActivityList() throws DALException {

        iActivityDAO.createiActivity(activityNo1);
        assertEquals(1,iActivityDAO.getiActivityList().size());
        iActivityDAO.createiActivity(activityNo2);
        assertEquals(2,iActivityDAO.getiActivityList().size());
        iActivityDAO.createiActivity(activityNo3);
        assertEquals(3,iActivityDAO.getiActivityList().size());
    }

    @Test
    public void getiActivityListByJobID() throws DALException {
        iActivityDAO.createiActivity(activityNo1);
        iActivityDAO.createiActivity(activityNo2);
        iActivityDAO.createiActivity(activityNo3);
        assertEquals(2,iActivityDAO.getiActivityList(jobNo1.getJobID()).size());
        assertEquals(1,iActivityDAO.getiActivityList(jobNo2.getJobID()).size());
    }

    @Test
    public void getiActivityList2() {
    }

    @Test
    public void createiActivity() throws DALException {
        iActivityDAO.createiActivity(activityNo1);

        IActivityDTO returnedActivityNo1 = iActivityDAO.getiActivity(activityNo1.getActivityID());
        assertEquals(activityNo1.getActivityID(),returnedActivityNo1.getActivityID());
        assertEquals(activityNo1.getJobID(),returnedActivityNo1.getJobID());
        assertEquals(dtf.format(activityNo1.getStartingDateTime()),dtf.format(returnedActivityNo1.getStartingDateTime()));
        assertEquals(dtf.format(activityNo1.getEndingDateTime()),dtf.format(returnedActivityNo1.getEndingDateTime()));
        assertEquals(activityNo1.getPause(),returnedActivityNo1.getPause());
        assertEquals(activityNo1.getActivityValue(),returnedActivityNo1.getActivityValue(),doubleEqualTolorance);
    }

    @Test
    public void updateiActivity() {
    }

    @Test
    public void deleteiActivity() throws DALException {
        iActivityDAO.createiActivity(activityNo1);
        iActivityDAO.createiActivity(activityNo2);

        List<IActivityDTO> activityList = iActivityDAO.getiActivityList();
        assertEquals(2, activityList.size());
        assertEquals(activityNo1.getActivityValue(), activityList.get(0).getActivityValue(),doubleEqualTolorance);
        assertEquals(activityNo2.getActivityValue(), activityList.get(1).getActivityValue(),doubleEqualTolorance);

        iActivityDAO.deleteiActivity(activityNo2.getActivityID());

        List<IActivityDTO> activityListAfterDeletion = iActivityDAO.getiActivityList();
        assertEquals(1, activityListAfterDeletion.size());
        assertEquals(activityNo1.getActivityValue(),activityListAfterDeletion.get(0).getActivityValue(),doubleEqualTolorance);
    }

    @Test
    public void deleteiActivity1() {
    }
}