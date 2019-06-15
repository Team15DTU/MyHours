package dao.job;

import dao.ConnectionHelper;
import dao.DALException;
import dao.employer.EmployerDAO;
import dao.employer.IEmployerDAO;
import dao.worker.IWorkerDAO;
import dao.worker.WorkerHiberDAO;
import db.DBController;
import db.IConnPool;
import db.TestConnPoolV1;
import dto.employer.IEmployerDTO;
import dto.job.IJobDTO;
import dto.worker.IWorkerDTO;
import hibernate.HibernateProperties;
import hibernate.HibernateUtil;
import org.junit.*;
import testData.TestDataController;

import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.*;

/**
 * @author Rasmus Sander Larsen
 */
public class JobDAOTest {

    private final double stdSalaryDifferenceTolerance = 0.001;

    private static IConnPool test_DB;
    private static ConnectionHelper connectionHelper; // TODO: Burde ConnectionHelper klassen være static?
    private static HibernateUtil hibernateUtil;
    private static IEmployerDAO iEmployerDAO;
    private static IWorkerDAO iWorkerDAO;
    private static IJobDAO iJobDAO;

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

        // endregion
    }

    // endregion

    // region Before and After annotations

    @BeforeClass
    public static void setUp() throws Exception {

        // Setup DAOs, ConnectionPools and DBControllers.
        test_DB = TestConnPoolV1.getInstance();
        connectionHelper = new ConnectionHelper(test_DB);
        hibernateUtil = new HibernateUtil(new HibernateProperties().getTestDB());
        hibernateUtil.setup();

        // Sets timezone (DBController would do it normally)
        TimeZone.setDefault(TimeZone.getTimeZone(hibernateUtil.getTimeZoneFromDB()));

        iWorkerDAO  = new WorkerHiberDAO(hibernateUtil);
        iEmployerDAO = new EmployerDAO(test_DB, connectionHelper);
        iJobDAO = new JobDAO(test_DB,connectionHelper);

        // Clear both Employers and Workers table.
        TestDataController.clearJobTestTable(iJobDAO);
        TestDataController.clearEmployerTestTable(iEmployerDAO);
        TestDataController.clearWorkerTestTable(iWorkerDAO);

        // Setup necessary test IWorkerDTO and IEmployer objects.
        setupTestData();
    }

    @AfterClass
    public static void tearDown() throws DALException {
        // Clears all tables
        TestDataController.clearJobTestTable(iJobDAO);
        TestDataController.clearEmployerTestTable(iEmployerDAO);
        TestDataController.clearWorkerTestTable(iWorkerDAO);

        // Closes ConnectionPool
        test_DB.closePool();
    }

    @After
    public void afterEachTest() throws DALException {
        TestDataController.clearJobTestTable(iJobDAO);
    }

    // endregion

    @Test
    public void getIJob() throws DALException {
        iJobDAO.createIJob(jobNo2);

        IJobDTO returnedJobNo1 = iJobDAO.getIJob(jobNo2.getJobID());

        assertEquals(jobNo2.getJobID(),returnedJobNo1.getJobID());
        assertEquals(jobNo2.getEmployerID(),returnedJobNo1.getEmployerID());
        assertEquals(jobNo2.getJobName(),returnedJobNo1.getJobName());
        assertEquals(jobNo2.getHireDate(),returnedJobNo1.getHireDate());
        assertEquals(jobNo2.getFinishDate(),returnedJobNo1.getFinishDate());
        assertEquals(jobNo2.getStdSalary(),returnedJobNo1.getStdSalary(), stdSalaryDifferenceTolerance); // TODO: Vi skal finde en tilladt tolerance.
    }

    @Test
    public void getFullJobList() throws DALException {
        assertEquals(0, iJobDAO.getIJobList().size());
        iJobDAO.createIJob(jobNo1);
        assertEquals(1, iJobDAO.getIJobList().size());
        iJobDAO.createIJob(jobNo2);
        assertEquals(2, iJobDAO.getIJobList().size());
        iJobDAO.createIJob(jobNo3);
        assertEquals(3, iJobDAO.getIJobList().size());
    }

    @Test
    public void getJobListFromEmployerID() throws DALException {
        iJobDAO.createIJob(jobNo1);
        iJobDAO.createIJob(jobNo2);
        iJobDAO.createIJob(jobNo3);
        // 2 Jobs have been assigned to EmployerNo1
        assertEquals(2,iJobDAO.getIJobList(employerNo1.getEmployerID()).size());
        // 1 Job have been assigned to EmployerNo2
        assertEquals(1,iJobDAO.getIJobList(employerNo2.getEmployerID()).size());
    }

    @Test
    public void getJobListFromCondition() {
    }

    @Test
    public void createIJob() throws DALException {
        iJobDAO.createIJob(jobNo1);

        IJobDTO returnedJobNo1 = iJobDAO.getIJob(jobNo1.getJobID());

        assertEquals(jobNo1.getJobID(),returnedJobNo1.getJobID());
        assertEquals(jobNo1.getEmployerID(),returnedJobNo1.getEmployerID());
        assertEquals(jobNo1.getJobName(),returnedJobNo1.getJobName());
        assertEquals(jobNo1.getHireDate(),returnedJobNo1.getHireDate());
        assertEquals(jobNo1.getFinishDate(),returnedJobNo1.getFinishDate());
        assertEquals(jobNo1.getStdSalary(),returnedJobNo1.getStdSalary(), stdSalaryDifferenceTolerance); // TODO: Vi skal finde en tilladt tolerance.
    }

    @Test
    public void updateIJob() throws DALException {
        iJobDAO.createIJob(jobNo2);

        IJobDTO updatedJobNo2 = jobNo2;
        updatedJobNo2.setJobName(jobNo3.getJobName());
        updatedJobNo2.setHireDate(jobNo3.getHireDate());
        updatedJobNo2.setFinishDate(jobNo3.getFinishDate());
        updatedJobNo2.setStdSalary(jobNo3.getStdSalary());

        iJobDAO.updateIJob(updatedJobNo2);

        IJobDTO returnedUpdatedJobNo2 = iJobDAO.getIJob(jobNo2.getJobID());

        assertEquals(jobNo2.getEmployerID(),returnedUpdatedJobNo2.getEmployerID());
        assertEquals(jobNo3.getJobName(),returnedUpdatedJobNo2.getJobName());
        assertEquals(jobNo3.getHireDate(),returnedUpdatedJobNo2.getHireDate());
        assertEquals(jobNo3.getFinishDate(),returnedUpdatedJobNo2.getFinishDate());
        assertEquals(jobNo3.getStdSalary(),returnedUpdatedJobNo2.getStdSalary(),stdSalaryDifferenceTolerance);
    }

    @Test
    public void deleteIJob() throws DALException {
        iJobDAO.createIJob(jobNo1);
        iJobDAO.createIJob(jobNo2);

        List<IJobDTO> jobsInTable = iJobDAO.getIJobList();
        assertEquals(2,jobsInTable.size());
        assertEquals(jobNo1.getJobName(),jobsInTable.get(0).getJobName());
        assertEquals(jobNo2.getJobName(),jobsInTable.get(1).getJobName());

        iJobDAO.deleteIJob(jobNo2.getJobID());

        List<IJobDTO> jobsInTableAfterDeletion = iJobDAO.getIJobList();
        assertEquals(1,jobsInTableAfterDeletion.size());
        assertEquals(jobNo1.getJobName(), jobsInTableAfterDeletion.get(0).getJobName());
    }
}