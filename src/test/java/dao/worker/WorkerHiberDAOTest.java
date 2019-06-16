package dao.worker;

import dao.DALException;
import db.DBController;
import db.connectionPools.ConnPoolV1;
import dto.worker.IWorkerDTO;
import dto.worker.WorkerHiberDTO;
import hibernate.HibernateProperties;
import hibernate.HibernateUtil;
import org.junit.*;

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
    private static HibernateUtil hibernateUtil;
    private static IWorkerDAO workerHiberDAO;
    private static IWorkerDTO testWorker1;
    private static IWorkerDTO testWorker2;

    // region testWorker1 information

    private static int tw1_workerID = 1;
    private static String tw1_firstName = "FirstName1";
    private static String tw1_surName = "SurName1";
    private static String tw1_email = "test1@test.dk";
    private static String tw1_pass = "test1Password";
    private static LocalDate tw1_birthday = LocalDate.now();

    // endregion

    // region testWorker2 information

    private static int tw2_workerID = 2;
    private static String tw2_firstName = "FirstName2";
    private static String tw2_surName = "SurName2";
    private static String tw2_email = "test2@test.dk";
    private static String tw2_pass = "test2Password";
    private static LocalDate tw2_birthday = LocalDate.now().minusDays(2);

    // endregion

    // region TestSetup

    private static void setupTestData () {

        testWorker1 = new WorkerHiberDTO();
        testWorker1.setFirstName(tw1_firstName);
        testWorker1.setSurName(tw1_surName);
        testWorker1.setEmail(tw1_email);
        testWorker1.setPassword(tw1_pass);
        testWorker1.setBirthday(tw1_birthday);

        testWorker2 = new WorkerHiberDTO();
        testWorker2.setFirstName(tw2_firstName);
        testWorker2.setSurName(tw2_surName);
        testWorker2.setEmail(tw2_email);
        testWorker2.setPassword(tw2_pass);
        testWorker2.setBirthday(tw2_birthday);

    }

    @BeforeClass
    public static void setUp() throws Exception {

        //dbController = new DBController();
        hibernateUtil = new HibernateUtil(new HibernateProperties().getTestDB());
        hibernateUtil.setup();

        TimeZone.setDefault(TimeZone.getTimeZone(hibernateUtil.getTimeZoneFromDB()));
        workerHiberDAO = new WorkerHiberDAO(hibernateUtil);//dbController.getiWorkerDAO();

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
        assertEquals(tw1_firstName, returnedWorkerNo1.getFirstName());
        assertEquals(tw1_surName, returnedWorkerNo1.getSurName());
        assertEquals(tw1_email, returnedWorkerNo1.getEmail());
        assertEquals(tw1_pass,returnedWorkerNo1.getPassword());
        assertEquals(tw1_birthday, returnedWorkerNo1.getBirthday());

    }

    @Test
    public void getWorker() throws DALException {
        workerHiberDAO.createWorker(testWorker2);

        IWorkerDTO returnedWorker2 = workerHiberDAO.getWorker(testWorker2.getEmail());

        assertEquals(tw2_firstName, returnedWorker2.getFirstName());
        assertEquals(tw2_surName, returnedWorker2.getSurName());
        assertEquals(tw2_email, returnedWorker2.getEmail());
        assertEquals(tw2_pass, returnedWorker2.getPassword());
        assertEquals(tw2_birthday, returnedWorker2.getBirthday());

    }

    @Test
    public void getWorkerList() throws DALException {
        List<IWorkerDTO> workerListBefore = workerHiberDAO.getWorkerList();
        assertEquals(0, workerListBefore.size());

        workerHiberDAO.createWorker(testWorker1);
        workerHiberDAO.createWorker(testWorker2);

        List<IWorkerDTO> workerListAfter = workerHiberDAO.getWorkerList();
        assertEquals(2, workerListAfter.size());
        assertEquals(tw2_firstName,workerListAfter.get(1).getFirstName());
        assertEquals(tw1_email, workerListAfter.get(0).getEmail());

    }

    @Test
    public void updateWorker() throws DALException {

        workerHiberDAO.createWorker(testWorker1);
        testWorker1.setFirstName(tw2_firstName);
        testWorker1.setSurName(tw2_surName);
        testWorker1.setEmail(tw2_email);
        testWorker1.setPassword(tw2_pass + "_Changed");

        workerHiberDAO.updateWorker(testWorker1);
        IWorkerDTO gettedWorker1 = workerHiberDAO.getWorker(testWorker1.getEmail());
        //assertEquals(1, gettedWorker1.getWorkerID()); TODO: Get assigned id!
        assertEquals(tw2_firstName, gettedWorker1.getFirstName());
        assertEquals(tw2_surName, gettedWorker1.getSurName());
        assertEquals(tw2_email, gettedWorker1.getEmail());
        assertEquals(tw2_pass + "_Changed",gettedWorker1.getPassword());

        testWorker1.setFirstName(tw1_firstName);
        testWorker1.setSurName(tw1_surName);
        testWorker1.setEmail(tw1_email);
        testWorker1.setPassword(tw1_pass);

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