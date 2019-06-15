package dao.worker;

import dao.DALException;
import db.DBController;
import db.connectionPools.ConnPoolV1;
import dto.worker.IWorkerDTO;
import dto.worker.WorkerHiberDTO;
import hibernate.HibernateProperties;
import hibernate.HibernateUtil;
import org.junit.*;
import testData.TestDataController;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.TimeZone;

import static org.junit.Assert.*;

/**
 * @author Rasmus Sander Larsen
 */
public class WorkerHiberDAOTest {

    private static DBController dbController;
    private static TestDataController testDataController;
    private static HibernateUtil hibernateUtil;
    private static IWorkerDAO workerHiberDAO;
    private static IWorkerDTO testWorker1;
    private static IWorkerDTO testWorker2;

    // region TestSetup

    private static void setupTestData () {

        testWorker1 = testDataController.getTestWorkerNo1();

        testWorker2 = testDataController.getTestWorkerNo2();

    }

    @BeforeClass
    public static void setUp() throws Exception {

        //dbController = new DBController();
        hibernateUtil = new HibernateUtil(new HibernateProperties().getTestDB());
        hibernateUtil.setup();

        TimeZone.setDefault(TimeZone.getTimeZone(hibernateUtil.getTimeZoneFromDB()));
        workerHiberDAO = new WorkerHiberDAO(hibernateUtil);//dbController.getiWorkerDAO();

        testDataController = new TestDataController();

        // makes sure that Workers Table is empty.
        clearWorkerTestTable();

        // Creates Test Worker objects.
        setupTestData();

    }

    @AfterClass
    public static void tearDown() throws Exception {
        // Closes HibernateUtil
        hibernateUtil.exit();
    }

    @After
    public void afterTest() throws DALException {
        // Clear Workers Table between tests
        clearWorkerTestTable();
    }

    //endregion

    @Test
    public void createWorker() throws DALException {

        //int idAssignedToCreatedWorker = dbController.getNextAutoIncremental(WorkerConstants.TABLENAME);

        workerHiberDAO.createWorker(testWorker1);

        IWorkerDTO returnedWorkerNo1 = workerHiberDAO.getWorker(testWorker1.getEmail());

        //assertEquals(idAssignedToCreatedWorker,gettedWorker1.getWorkerID());
        assertEquals(testWorker1.getFirstName(), returnedWorkerNo1.getFirstName());
        assertEquals(testWorker1.getSurName(), returnedWorkerNo1.getSurName());
        assertEquals(testWorker1.getEmail(), returnedWorkerNo1.getEmail());
        assertEquals(testWorker1.getPassword(),returnedWorkerNo1.getPassword());
        assertEquals(testWorker1.getBirthday(), returnedWorkerNo1.getBirthday());

    }

    @Test
    public void getWorker() throws DALException {
        workerHiberDAO.createWorker(testWorker2);

        IWorkerDTO returnedWorker2 = workerHiberDAO.getWorker(testWorker2.getEmail());

        assertEquals(testWorker2.getFirstName(), returnedWorker2.getFirstName());
        assertEquals(testWorker2.getSurName(), returnedWorker2.getSurName());
        assertEquals(testWorker2.getEmail(), returnedWorker2.getEmail());
        assertEquals(testWorker2.getPassword(), returnedWorker2.getPassword());
        assertEquals(testWorker2.getBirthday(), returnedWorker2.getBirthday());

    }

    @Test
    public void getWorkerList() throws DALException {
        List<IWorkerDTO> workerListBefore = workerHiberDAO.getWorkerList();
        assertEquals(0, workerListBefore.size());

        workerHiberDAO.createWorker(testWorker1);
        workerHiberDAO.createWorker(testWorker2);

        List<IWorkerDTO> workerListAfter = workerHiberDAO.getWorkerList();
        assertEquals(2, workerListAfter.size());
        assertEquals(testWorker2.getFirstName(),workerListAfter.get(1).getFirstName());
        assertEquals(testWorker1.getEmail(), workerListAfter.get(0).getEmail());

    }

    @Test
    public void updateWorker() throws DALException {

        workerHiberDAO.createWorker(testWorker1);
        testWorker1.setFirstName(testWorker2.getFirstName());
        testWorker1.setSurName(testWorker2.getSurName());
        testWorker1.setEmail(testWorker2.getEmail());
        testWorker1.setPassword(testWorker2.getPassword() + "_Changed");

        workerHiberDAO.updateWorker(testWorker1);
        IWorkerDTO gettedWorker1 = workerHiberDAO.getWorker(testWorker1.getEmail());
        //assertEquals(1, gettedWorker1.getWorkerID()); TODO: Get assigned id!
        assertEquals(testWorker2.getFirstName(), gettedWorker1.getFirstName());
        assertEquals(testWorker2.getSurName(), gettedWorker1.getSurName());
        assertEquals(testWorker2.getEmail(), gettedWorker1.getEmail());
        assertEquals(testWorker2.getPassword() + "_Changed",gettedWorker1.getPassword());

        testWorker1 = testDataController.getTestWorkerNo1();

    }

    @Test
    public void deleteWorker() throws DALException {

        workerHiberDAO.createWorker(testWorker1);

        List<IWorkerDTO> workerListBefore = workerHiberDAO.getWorkerList();
        assertEquals(1, workerListBefore.size());

        workerHiberDAO.deleteWorker(testWorker1.getEmail());

        List<IWorkerDTO> workerListAfter = workerHiberDAO.getWorkerList();
        assertEquals(0,workerListAfter.size());

    }

    private static void clearWorkerTestTable () throws DALException {
        // Deletes all Workers from Worker testTable
        for (IWorkerDTO workerDTO : workerHiberDAO.getWorkerList()) {
            workerHiberDAO.deleteWorker(workerDTO.getEmail());
        }
    }
}