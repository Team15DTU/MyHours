package dao.worker;

import dao.DALException;
import dto.worker.IWorkerDTO;
import hibernate.HibernateProperties;
import hibernate.HibernateUtil;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import testData.TestDataController;

import java.util.List;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

/**
 * @author Rasmus Sander Larsen
 */
public class WorkerHiberDAOTest {

    private static HibernateUtil hibernateUtil;
    private static IWorkerDAO iWorkerDAO;
    private static IWorkerDTO testWorker1;
    private static IWorkerDTO testWorker2;

    // region TestSetup

    private static void setupTestData () {

        testWorker1 = TestDataController.getTestWorkerNo1();

        testWorker2 = TestDataController.getTestWorkerNo2();

    }

    @BeforeClass
    public static void setUp() throws Exception {

        hibernateUtil = new HibernateUtil(new HibernateProperties().getTestDB());
        hibernateUtil.setup();

        TimeZone.setDefault(TimeZone.getTimeZone(hibernateUtil.getTimeZoneFromDB()));
        iWorkerDAO = new WorkerHiberDAO(hibernateUtil);

        // Clear Workers table.
        TestDataController.clearWorkerTestTable(iWorkerDAO);

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
        TestDataController.clearWorkerTestTable(iWorkerDAO);
    }

    //endregion

    @Test
    public void createWorker() throws DALException {

        iWorkerDAO.createWorker(testWorker1);

        IWorkerDTO returnedWorkerNo1 = iWorkerDAO.getWorker(testWorker1.getEmail());

        assertEquals(testWorker1.getWorkerID(), returnedWorkerNo1.getWorkerID());
        assertEquals(testWorker1.getFirstName(), returnedWorkerNo1.getFirstName());
        assertEquals(testWorker1.getSurName(), returnedWorkerNo1.getSurName());
        assertEquals(testWorker1.getEmail(), returnedWorkerNo1.getEmail());
        assertEquals(testWorker1.getPassword(),returnedWorkerNo1.getPassword());
        assertEquals(testWorker1.getBirthday(), returnedWorkerNo1.getBirthday());

    }

    @Test
    public void getWorker() throws DALException {
        iWorkerDAO.createWorker(testWorker2);

        IWorkerDTO returnedWorker2 = iWorkerDAO.getWorker(testWorker2.getEmail());

        assertEquals(testWorker2.getFirstName(), returnedWorker2.getFirstName());
        assertEquals(testWorker2.getSurName(), returnedWorker2.getSurName());
        assertEquals(testWorker2.getEmail(), returnedWorker2.getEmail());
        assertEquals(testWorker2.getPassword(), returnedWorker2.getPassword());
        assertEquals(testWorker2.getBirthday(), returnedWorker2.getBirthday());
        
        //TODO: getWorker(int id) needs to be tested!

    }

    @Test
    public void getWorkerList() throws DALException {
        List<IWorkerDTO> workerListBefore = iWorkerDAO.getWorkerList();
        assertEquals(0, workerListBefore.size());

        iWorkerDAO.createWorker(testWorker1);
        iWorkerDAO.createWorker(testWorker2);

        List<IWorkerDTO> workerListAfter = iWorkerDAO.getWorkerList();
        assertEquals(2, workerListAfter.size());
        assertEquals(testWorker2.getFirstName(),workerListAfter.get(1).getFirstName());
        assertEquals(testWorker1.getEmail(), workerListAfter.get(0).getEmail());

    }

    @Test
    public void updateWorker() throws DALException {

        iWorkerDAO.createWorker(testWorker1);
        testWorker1.setFirstName(testWorker2.getFirstName());
        testWorker1.setSurName(testWorker2.getSurName());
        testWorker1.setEmail(testWorker2.getEmail());
        testWorker1.setPassword(testWorker2.getPassword() + "_Changed");

        iWorkerDAO.updateWorker(testWorker1);
        IWorkerDTO gettedWorker1 = iWorkerDAO.getWorker(testWorker1.getEmail());
        assertEquals(testWorker1.getWorkerID(), gettedWorker1.getWorkerID());
        assertEquals(testWorker2.getFirstName(), gettedWorker1.getFirstName());
        assertEquals(testWorker2.getSurName(), gettedWorker1.getSurName());
        assertEquals(testWorker2.getEmail(), gettedWorker1.getEmail());
        assertEquals(testWorker2.getPassword() + "_Changed",gettedWorker1.getPassword());

        testWorker1 = TestDataController.getTestWorkerNo1();
    }

    @Test
    public void deleteWorker() throws DALException {

        iWorkerDAO.createWorker(testWorker1);

        List<IWorkerDTO> workerListBefore = iWorkerDAO.getWorkerList();
        assertEquals(1, workerListBefore.size());

        iWorkerDAO.deleteWorker(testWorker1.getEmail());

        List<IWorkerDTO> workerListAfter = iWorkerDAO.getWorkerList();
        assertEquals(0,workerListAfter.size());

    }
}