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

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Rasmus Sander Larsen
 */
public class ActivityDAOTest {

    private final double doubleEqualTolerance = 0.01;

    private static IConnPool test_DB;
    private static DBController dbController;
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
    private static IActivityDTO activityNo4;

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
        //Setting up ActivityNo2 (Belongs to JobNo1)
        activityNo2 = TestDataController.getActivityNo2(jobNo1);
        //Setting up ActivityNo3 (Belongs to JobNo2)
        activityNo3 = TestDataController.getActivityNo3(jobNo2);
        //Setting up ActivityNo4 (Belongs to JobNo2)
        activityNo4 = TestDataController.getActivityNo4(jobNo2);


        // endregion
    }

    // endregion

    // region Before and After annotations

    @BeforeClass
    public static void setUp() throws Exception {

        // Setup DAOs, ConnectionPools and DBControllers.
        test_DB = TestConnPoolV1.getInstance();
        dbController = new DBController(test_DB, new HibernateProperties().getTestDB());

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
        dbController.getHibernateUtil().exit();
    }

    @After
    public void afterEachTest() throws DALException {
        TestDataController.clearActivityTestTable(iActivityDAO);
    }

    // endregion

    @Test
    public void createiActivity() throws DALException {
        iActivityDAO.createiActivity(activityNo1);

        IActivityDTO returnedActivityNo1 = iActivityDAO.getiActivity(activityNo1.getActivityID());
        assertEquals(activityNo1.getActivityID(),returnedActivityNo1.getActivityID());
        assertEquals(activityNo1.getJobID(),returnedActivityNo1.getJobID());
        assertEquals(activityNo1.getStartingDateTime(),returnedActivityNo1.getStartingDateTime());
        assertEquals(activityNo1.getEndingDateTime(), returnedActivityNo1.getEndingDateTime());
        assertEquals(activityNo1.getPauseInMinuts(),returnedActivityNo1.getPauseInMinuts());
        assertEquals(activityNo1.getActivityValue(),returnedActivityNo1.getActivityValue(), doubleEqualTolerance);
    }

    @Test
    public void getiActivity() throws DALException {
        iActivityDAO.createiActivity(activityNo1);

        IActivityDTO returnedActivityNo1 = iActivityDAO.getiActivity(activityNo1.getActivityID());

        assertEquals(activityNo1.getActivityID(),returnedActivityNo1.getActivityID());
        assertEquals(activityNo1.getJobID(),returnedActivityNo1.getJobID());
        assertEquals(activityNo1.getStartingDateTime(),returnedActivityNo1.getStartingDateTime());
        assertEquals(activityNo1.getEndingDateTime(),returnedActivityNo1.getEndingDateTime());
        assertEquals(activityNo1.getPauseInMinuts(),returnedActivityNo1.getPauseInMinuts());
        assertEquals(activityNo1.getActivityValue(),returnedActivityNo1.getActivityValue(), doubleEqualTolerance);
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
    public void getiActivityListBetweenDatesWithJobID() throws DALException {
        iActivityDAO.createiActivity(activityNo1);
        iActivityDAO.createiActivity(activityNo2);
        iActivityDAO.createiActivity(activityNo3);

        List<IActivityDTO> activityListJobNo1BetweenNo1 = iActivityDAO
                .getiActivityList(jobNo1.getJobID(), LocalDate.now().minusMonths(12),LocalDate.now().minusMonths(12));
        List<IActivityDTO> activityListJobNo1BetweenNo2 = iActivityDAO
                .getiActivityList(jobNo1.getJobID(), LocalDate.now().minusMonths(12),LocalDate.now().minusMonths(6));
        List<IActivityDTO> activityListJobNo1BetweenNo3 = iActivityDAO
                .getiActivityList(jobNo2.getJobID(), LocalDate.now().minusMonths(12),LocalDate.now().minusMonths(6));


        assertEquals(1,activityListJobNo1BetweenNo1.size());
        assertEquals(2,activityListJobNo1BetweenNo2.size());
        assertEquals(0,activityListJobNo1BetweenNo3.size());
    }

    @Test
    public void getiActivityListBetweenDates() throws DALException {
        iActivityDAO.createiActivity(activityNo1);
        iActivityDAO.createiActivity(activityNo2);
        iActivityDAO.createiActivity(activityNo3);

        List<IActivityDTO> activityListJobNo1BetweenNo1 = iActivityDAO
                .getiActivityList(LocalDate.now().minusMonths(6),LocalDate.now().minusMonths(6));
        List<IActivityDTO> activityListJobNo1BetweenNo2 = iActivityDAO
                .getiActivityList(LocalDate.now().minusMonths(12),LocalDate.now().minusMonths(6));
        List<IActivityDTO> activityListJobNo1BetweenNo3 = iActivityDAO
                .getiActivityList(LocalDate.now().minusMonths(24),LocalDate.now().minusMonths(6));
        List<IActivityDTO> activityListJobNo1BetweenNo4 = iActivityDAO
                .getiActivityList(LocalDate.now().minusMonths(24),LocalDate.now().minusMonths(12));
        List<IActivityDTO> activityListJobNo1BetweenNo5 = iActivityDAO
                .getiActivityList(LocalDate.now().minusMonths(32),LocalDate.now().minusMonths(25));
        List<IActivityDTO> activityListJobNo1BetweenNo6 = iActivityDAO
                .getiActivityList(LocalDate.now().minusMonths(6).plusDays(1),LocalDate.now().minusMonths(6).plusDays(1));

        assertEquals(1,activityListJobNo1BetweenNo1.size());
        assertEquals(2,activityListJobNo1BetweenNo2.size());
        assertEquals(3,activityListJobNo1BetweenNo3.size());
        assertEquals(2,activityListJobNo1BetweenNo4.size());
        assertEquals(0,activityListJobNo1BetweenNo5.size());
        assertEquals(0,activityListJobNo1BetweenNo6.size());
    }

    @Test
    public void updateiActivity() throws DALException {
        iActivityDAO.createiActivity(activityNo2);

        IActivityDTO updatedActivityNo2 = activityNo2;
        updatedActivityNo2.setStartingDateTime(activityNo3.getStartingDateTime());
        updatedActivityNo2.setEndingDateTime(activityNo3.getEndingDateTime());
        updatedActivityNo2.setPauseInMinuts(activityNo3.getPauseInMinuts());
        updatedActivityNo2.setActivityValue(activityNo3.getActivityValue());

        iActivityDAO.updateiActivity(updatedActivityNo2);

        IActivityDTO returnedUpdatedActivityNo2 = iActivityDAO.getiActivity(updatedActivityNo2.getActivityID());
        assertEquals(activityNo3.getStartingDateTime(),returnedUpdatedActivityNo2.getStartingDateTime());
        assertEquals(activityNo3.getEndingDateTime(), returnedUpdatedActivityNo2.getEndingDateTime());
        assertEquals(activityNo3.getPauseInMinuts(),returnedUpdatedActivityNo2.getPauseInMinuts());
        assertEquals(activityNo3.getActivityValue(),returnedUpdatedActivityNo2.getActivityValue(), doubleEqualTolerance);
    }

    @Test
    public void deleteiActivity() throws DALException {
        iActivityDAO.createiActivity(activityNo1);
        iActivityDAO.createiActivity(activityNo2);

        List<IActivityDTO> activityList = iActivityDAO.getiActivityList();
        assertEquals(2, activityList.size());
        assertEquals(activityNo1.getActivityValue(), activityList.get(0).getActivityValue(), doubleEqualTolerance);
        assertEquals(activityNo2.getActivityValue(), activityList.get(1).getActivityValue(), doubleEqualTolerance);

        iActivityDAO.deleteiActivity(activityNo2.getActivityID());

        List<IActivityDTO> activityListAfterDeletion = iActivityDAO.getiActivityList();
        assertEquals(1, activityListAfterDeletion.size());
        assertEquals(activityNo1.getActivityValue(),activityListAfterDeletion.get(0).getActivityValue(), doubleEqualTolerance);
    }

    @Test
    public void testCalculationOfActivityValue() {

        activityNo4.calculateActivityValue(jobNo2);
        assertEquals(1169.583,activityNo4.getActivityValue(), doubleEqualTolerance);

    }
}