package testData;

import dto.employer.EmployerDTO;
import dto.employer.IEmployerDTO;
import dto.worker.IWorkerDTO;
import dto.worker.WorkerHiberDTO;

import java.awt.*;
import java.time.LocalDate;

/**
 * @author Rasmus Sander Larsen
 */
public class TestDataController {

    /*
    -------------------------- Fields --------------------------
     */

    // region WorkerDTO Test Data

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

    // endregion

    // region Employer Test Material

    // region EmployerDTO No. 1 (TestWorkerNo1)
    private static int employerNo1_workerID = tw1_workerID;
    private static String employerNo1_name = "EmployerName1";
    private static Color employerNo1_color = Color.decode("#FAEBD7");
    private static String employerNo1_telephone = "12345678";


    // endregion

    // region EmployerDTO No. 2 (TestWorkerNo1)
    private static int employerNo2_workerID = tw1_workerID;
    private static String employerNo2_name = "EmployerName2";
    private static Color employerNo2_color = Color.decode("#55a7e4");
    private static String employerNo2_telephone = "87654321";

    // endregion

    // region EmployerDTO No. 2 (TestWorkerNo2)
    private static int employerNo3_workerID = tw2_workerID;
    private static String employerNo3_name = "EmployerName2";
    private static Color employerNo3_color = Color.decode("#55a7e4");
    private static String employerNo3_telephone = "87654321";

    // endregion

    /*
    ----------------------- Constructor -------------------------
     */
    
    
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

    //region Test Workers Objects

    public IWorkerDTO getTestWorkerNo1 () {
        IWorkerDTO testWorkerNo1 = new WorkerHiberDTO();
        testWorkerNo1.setFirstName(tw1_firstName);
        testWorkerNo1.setSurName(tw1_surName);
        testWorkerNo1.setEmail(tw1_email);
        testWorkerNo1.setPassword(tw1_pass);
        testWorkerNo1.setBirthday(tw1_birthday);

        return testWorkerNo1;
    }

    public IWorkerDTO getTestWorkerNo2 () {

        IWorkerDTO testWorkerNo2 = new WorkerHiberDTO();
        testWorkerNo2.setFirstName(tw2_firstName);
        testWorkerNo2.setSurName(tw2_surName);
        testWorkerNo2.setEmail(tw2_email);
        testWorkerNo2.setPassword(tw2_pass);
        testWorkerNo2.setBirthday(tw2_birthday);

        return testWorkerNo2;
    }

    // endregion

    // region Test Employers Objects

    // Setting up employerNo1 (TestWorkerNo1)
    public IEmployerDTO getEmployerNo1 () {

        IEmployerDTO employerNo1 = new EmployerDTO();

        employerNo1.setWorkerID(employerNo1_workerID);
        employerNo1.setName(employerNo1_name);
        employerNo1.setColor(employerNo1_color);
        employerNo1.setTelephone(employerNo1_telephone);

        return employerNo1;
    }

    // Setting up employerNo2 (TestWorkerNo1)
    public IEmployerDTO getEmployerNo2 () {

        IEmployerDTO employerNo2 = new EmployerDTO();

        employerNo2.setWorkerID(employerNo2_workerID);
        employerNo2.setName(employerNo2_name);
        employerNo2.setColor(employerNo2_color);
        employerNo2.setTelephone(employerNo2_telephone);

        return employerNo2;
    }

    // Setting up employerNo2 (TestWorkerNo1)
    public IEmployerDTO getEmployerNo3 () {

        IEmployerDTO employerNo2 = new EmployerDTO();

        employerNo2.setWorkerID(employerNo3_workerID);
        employerNo2.setName(employerNo3_name);
        employerNo2.setColor(employerNo3_color);
        employerNo2.setTelephone(employerNo3_telephone);

        return employerNo2;
    }

    // endregion
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
