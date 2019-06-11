package DAO.worker;

import DAO.DALException;
import dto.worker.IWorkerDTO;
import dto.worker.WorkerDTO;
import dto.worker.WorkerHiberDTO;
import hibernate.HibernateUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.zip.DataFormatException;

import static org.junit.Assert.*;

/**
 * @author Rasmus Sander Larsen
 */
public class WorkerHiberDAOTest {

    private HibernateUtil hibernateUtil;
    private WorkerHiberDAO workerHiberDAO;
    private IWorkerDTO testWorker1;
    private IWorkerDTO testWorker2;

    // region testWorker1 information

    private int tw1_workerID = 1;
    private String tw1_firstName = "FirstName1";
    private String tw1_surName = "SurName1";
    private String tw1_email = "test1@test.dk";
    private String tw1_pass = "test1Password";
    private LocalDate tw1_birthday = LocalDate.now();

    // endregion

    // region testWorker2 information

    private int tw2_workerID = 2;
    private String tw2_firstName = "FirstName2";
    private String tw2_surName = "SurName2";
    private String tw2_email = "test2@test.dk";
    private String tw2_pass = "test2Password";
    private LocalDate tw2_birthday = LocalDate.now().minusDays(2);

    // endregion

    // region TestSetup

    public WorkerHiberDAOTest () {

        testWorker1 = new WorkerHiberDTO();
        testWorker1.setFirstName(tw1_firstName);
        testWorker1.setSurName(tw1_surName);
        testWorker1.setEmail(tw1_email);
        testWorker1.setPass(tw1_pass);
        testWorker1.setBirthday(tw1_birthday);

        testWorker2 = new WorkerHiberDTO();
        testWorker2.setFirstName(tw2_firstName);
        testWorker2.setSurName(tw2_surName);
        testWorker2.setEmail(tw2_email);
        testWorker2.setPass(tw2_pass);
        testWorker2.setBirthday(tw2_birthday);

    }

    @Before
    public void setUp() throws Exception {
        hibernateUtil = new HibernateUtil();
        hibernateUtil.setup();
        workerHiberDAO = new WorkerHiberDAO(hibernateUtil);

    }

    @After
    public void tearDown() throws Exception {
        hibernateUtil.exit();
    }

    //endregion

    @Test
    public void createWorker() throws DALException {
        workerHiberDAO.createWorker(testWorker1,"Ligemeget");

        IWorkerDTO gettedWorker1 = workerHiberDAO.getWorker(testWorker1.getEmail());

        assertEquals(tw1_firstName, gettedWorker1.getFirstName());
        assertEquals(tw1_surName, gettedWorker1.getSurName());
        assertEquals(tw1_email, gettedWorker1.getEmail());
        assertEquals(tw1_pass,gettedWorker1.getPass());
        assertEquals(tw1_birthday, gettedWorker1.getBirthday());
    }

    @Test
    public void getWorker() throws DALException {
        workerHiberDAO.createWorker(testWorker2,"Ligemeget");

        IWorkerDTO gettedWorker2 = workerHiberDAO.getWorker(testWorker2.getEmail());

        assertEquals(tw2_firstName, gettedWorker2.getFirstName());
        assertEquals(tw2_surName, gettedWorker2.getSurName());
        assertEquals(tw2_email, gettedWorker2.getEmail());
        assertEquals(tw2_pass, gettedWorker2.getPass());
        assertEquals(tw2_birthday, gettedWorker2.getBirthday());
    }

    @Test
    public void getWorkerList() throws DALException {
        List<IWorkerDTO> workerListBefore = workerHiberDAO.getWorkerList();
        assertEquals(0, workerListBefore.size());

        workerHiberDAO.createWorker(testWorker1, "Ligemeget");
        workerHiberDAO.createWorker(testWorker2, "Ligemeget");

        List<IWorkerDTO> workerListAfter = workerHiberDAO.getWorkerList();
        assertEquals(2, workerListAfter.size());
        assertEquals(tw2_firstName,workerListAfter.get(1).getFirstName());
        assertEquals(tw1_email, workerListAfter.get(0).getEmail());
    }

    @Test
    public void updateWorker() throws DALException {

        workerHiberDAO.createWorker(testWorker1, "Ligemeget");
        testWorker1.setFirstName(tw2_firstName);
        testWorker1.setSurName(tw2_surName);
        testWorker1.setEmail(tw2_email);
        testWorker1.setPass(tw2_pass + "_Changed");

        workerHiberDAO.updateWorker(testWorker1, "Ligemeget");
        IWorkerDTO gettedWorker1 = workerHiberDAO.getWorker(testWorker1.getEmail());
        assertEquals(1, gettedWorker1.getWorkerID());
        assertEquals(tw2_firstName, gettedWorker1.getFirstName());
        assertEquals(tw2_surName, gettedWorker1.getSurName());
        assertEquals(tw2_email, gettedWorker1.getEmail());
        assertEquals(tw2_pass + "_Changed",gettedWorker1.getPass());

    }

    @Test
    public void deleteWorker() throws DALException {

        workerHiberDAO.createWorker(testWorker1, "Ligemeget");

        List<IWorkerDTO> workerListBefore = workerHiberDAO.getWorkerList();
        assertEquals(1, workerListBefore.size());

        workerHiberDAO.deleteWorker(testWorker1.getEmail());

        List<IWorkerDTO> workerListAfter = workerHiberDAO.getWorkerList();
        assertEquals(0,workerListAfter.size());

    }
}