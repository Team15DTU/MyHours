package testData;

import dao.DALException;
import dao.activity.IActivityDAO;
import dao.employer.IEmployerDAO;
import dao.job.IJobDAO;
import dao.worker.IWorkerDAO;
import dto.activity.ActivityDTO;
import dto.activity.IActivityDTO;
import dto.employer.EmployerDTO;
import dto.employer.IEmployerDTO;
import dto.job.IJobDTO;
import dto.job.JobDTO;
import dto.worker.IWorkerDTO;
import dto.worker.WorkerHiberDTO;

import java.awt.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class TestDataController {

    /*
    -------------------------- Fields --------------------------
     */

    // region WorkerDTO Test Data

    // region testWorker1 information

    private static int testWorkerNo1_workerID = 1;
    private static String testWorkerNo1_firstName = "FirstName1";
    private static String testWorkerNo1_surName = "SurName1";
    private static String testWorkerNo1_email = "test1@test.dk";
    private static String testWorkerNo1_pass = "test1Password";
    private static LocalDate testWorkerNo1_birthday = LocalDate.now();

    // endregion

    // region testWorkerNo2 information

    private static int testWorkerNo2_workerID = 2;
    private static String testWorkerNo2_firstName = "FirstName2";
    private static String testWorkerNo2_surName = "SurName2";
    private static String testWorkerNo2_email = "test2@test.dk";
    private static String testWorkerNo2_pass = "test2Password";
    private static LocalDate testWorkerNo2_birthday = LocalDate.now().minusDays(2);

    // endregion

    // endregion

    // region Employer Test Data

    // region EmployerDTO No. 1
    private static String employerNo1_name = "EmployerName1";
    private static Color employerNo1_color = Color.decode("#FAEBD7");
    private static String employerNo1_telephone = "12345678";


    // endregion

    // region EmployerDTO No. 2
    private static String employerNo2_name = "EmployerName2";
    private static Color employerNo2_color = Color.decode("#55a7e4");
    private static String employerNo2_telephone = "87654321";

    // endregion

    // region EmployerDTO No. 3
    private static String employerNo3_name = "EmployerName2";
    private static Color employerNo3_color = Color.decode("#55a7e4");
    private static String employerNo3_telephone = "87654321";

    // endregion

    // endregion

    // region JobDTO Test Data

    // region JobDTO No. 1

    private static String jobNo1_jobName = "JobName1";
    private static LocalDate jobNo1_hireDate = LocalDate.now().minusMonths(12);
    private static LocalDate jobNo1_finishDate = jobNo1_hireDate.plusMonths(6);
    private static double jobNo1_stdSalary = 100.00;

    // endregion

    // region JobDTO No. 2

    private static String jobNo2_jobName = "JobName2";
    private static LocalDate jobNo2_hireDate = LocalDate.now().minusMonths(24);
    private static LocalDate jobNo2_finishDate = jobNo1_hireDate.plusMonths(12);
    private static double jobNo2_stdSalary = 200.50;

    // endregion

    // region JobDTO No. 3

    private static String jobNo3_jobName = "JobName3";
    private static LocalDate jobNo3_hireDate = LocalDate.now().minusMonths(6);
    private static LocalDate jobNo3_finishDate = jobNo1_hireDate.plusMonths(3);
    private static double jobNo3_stdSalary = 300.00;

    // endregion

    // endregion

    // region ActivityDTO Test Data

    // region ActivityDTO No. 1

    private static LocalDateTime activityNo1_startingDateTime = LocalDateTime.now().minusMonths(12);
    private static LocalDateTime activityNo1_endingDateTime = activityNo1_startingDateTime.plusMonths(6);
    private static Duration activityNo1_pause = Duration.ofMinutes(30); // break in minutes!
    private static double activityNo1_activityValue = 1500.50;

    // endregion

    // region ActivityDTO No. 2

    private static LocalDateTime activityNo2_startingDateTime = LocalDateTime.now().minusMonths(24);
    private static LocalDateTime activityNo2_endingDateTime = activityNo2_startingDateTime.plusMonths(12);
    private static Duration activityNo2_pause = Duration.ofMinutes(90); // break in minutes!
    private static double activityNo2_activityValue = 3000.57;

    // endregion

    // region ActivityDTO No. 3

    private static LocalDateTime activityNo3_startingDateTime = LocalDateTime.now().minusMonths(6);
    private static LocalDateTime activityNo3_endingDateTime = activityNo3_startingDateTime.plusMonths(3);
    private static Duration activityNo3_pause = Duration.ofMinutes(90); // break in minutes!
    private static double activityNo3_activityValue = 3000.57;

    // endregion

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

    public static IWorkerDTO getTestWorkerNo1 () {
        IWorkerDTO testWorkerNo1 = new WorkerHiberDTO();
        testWorkerNo1.setFirstName(testWorkerNo1_firstName);
        testWorkerNo1.setSurName(testWorkerNo1_surName);
        testWorkerNo1.setEmail(testWorkerNo1_email);
        testWorkerNo1.setPassword(testWorkerNo1_pass);
        testWorkerNo1.setBirthday(testWorkerNo1_birthday);

        return testWorkerNo1;
    }

    public static IWorkerDTO getTestWorkerNo2 () {

        IWorkerDTO testWorkerNo2 = new WorkerHiberDTO();
        testWorkerNo2.setFirstName(testWorkerNo2_firstName);
        testWorkerNo2.setSurName(testWorkerNo2_surName);
        testWorkerNo2.setEmail(testWorkerNo2_email);
        testWorkerNo2.setPassword(testWorkerNo2_pass);
        testWorkerNo2.setBirthday(testWorkerNo2_birthday);

        return testWorkerNo2;
    }

    // endregion

    // region Test Employers Objects

    // Setting up employerNo1
    public static IEmployerDTO getEmployerNo1 (IWorkerDTO belongsToWorkerDTO) {

        IEmployerDTO employerNo1 = new EmployerDTO();

        employerNo1.setWorkerID(belongsToWorkerDTO.getWorkerID());
        employerNo1.setName(employerNo1_name);
        employerNo1.setColor(employerNo1_color);
        employerNo1.setTelephone(employerNo1_telephone);

        return employerNo1;
    }

    // Setting up employerNo2
    public static IEmployerDTO getEmployerNo2 (IWorkerDTO belongsToWorkerDTO) {

        IEmployerDTO employerNo2 = new EmployerDTO();

        employerNo2.setWorkerID(belongsToWorkerDTO.getWorkerID());
        employerNo2.setName(employerNo2_name);
        employerNo2.setColor(employerNo2_color);
        employerNo2.setTelephone(employerNo2_telephone);

        return employerNo2;
    }

    // Setting up employerNo3
    public static IEmployerDTO getEmployerNo3 (IWorkerDTO belongsToWorkerDTO) {

        IEmployerDTO employerNo2 = new EmployerDTO();
        employerNo2.setWorkerID(belongsToWorkerDTO.getWorkerID());
        employerNo2.setName(employerNo3_name);
        employerNo2.setColor(employerNo3_color);
        employerNo2.setTelephone(employerNo3_telephone);

        return employerNo2;
    }

    // endregion

    // region Test Jobs Objects

    public static IJobDTO getJobNo1 (IEmployerDTO belongsToEmployerDTO) {
        IJobDTO jobNo1 = new JobDTO();

        jobNo1.setEmployerID(belongsToEmployerDTO.getEmployerID());
        jobNo1.setJobName(jobNo1_jobName);
        jobNo1.setHireDate(jobNo1_hireDate);
        jobNo1.setFinishDate(jobNo1_finishDate);
        jobNo1.setStdSalary(jobNo1_stdSalary);

        return jobNo1;
    }

    public static IJobDTO getJobNo2 (IEmployerDTO belongsToEmployerDTO) {
        IJobDTO jobNo2 = new JobDTO();

        jobNo2.setEmployerID(belongsToEmployerDTO.getEmployerID());
        jobNo2.setJobName(jobNo2_jobName);
        jobNo2.setHireDate(jobNo2_hireDate);
        jobNo2.setFinishDate(jobNo2_finishDate);
        jobNo2.setStdSalary(jobNo2_stdSalary);

        return jobNo2;
    }

    public static IJobDTO getJobNo3 (IEmployerDTO belongsToEmployerDTO) {
        IJobDTO jobNo3 = new JobDTO();

        jobNo3.setEmployerID(belongsToEmployerDTO.getEmployerID());
        jobNo3.setJobName(jobNo3_jobName);
        jobNo3.setHireDate(jobNo3_hireDate);
        jobNo3.setFinishDate(jobNo3_finishDate);
        jobNo3.setStdSalary(jobNo3_stdSalary);

        return jobNo3;
    }

    // endregion

    // region Test Activity Objects

    public static IActivityDTO getActivityNo1 (IJobDTO belongsToJObDTO){
        IActivityDTO activityNo1 = new ActivityDTO();

        activityNo1.setJobID(belongsToJObDTO.getJobID());
        activityNo1.setStartingDateTime(activityNo1_startingDateTime);
        activityNo1.setEndingDateTime(activityNo1_endingDateTime);
        activityNo1.setPause(activityNo1_pause);
        activityNo1.setActivityValue(activityNo1_activityValue);

        return activityNo1;
    }

    public static IActivityDTO getActivityNo2(IJobDTO belongsToJObDTO){
        IActivityDTO activityNo2 = new ActivityDTO();

        activityNo2.setJobID(belongsToJObDTO.getJobID());
        activityNo2.setStartingDateTime(activityNo2_startingDateTime);
        activityNo2.setEndingDateTime(activityNo2_endingDateTime);
        activityNo2.setPause(activityNo2_pause);
        activityNo2.setActivityValue(activityNo2_activityValue);

        return activityNo2;
    }

    public static IActivityDTO getActivityNo3 (IJobDTO belongsToJObDTO){
        IActivityDTO activityNo3 = new ActivityDTO();

        activityNo3.setJobID(belongsToJObDTO.getJobID());
        activityNo3.setStartingDateTime(activityNo3_startingDateTime);
        activityNo3.setEndingDateTime(activityNo3_endingDateTime);
        activityNo3.setPause(activityNo3_pause);
        activityNo3.setActivityValue(activityNo3_activityValue);

        return activityNo3;
    }

    // endregion

    // region Support Methods for Cleaning of tables

    public static void clearWorkerTestTable (IWorkerDAO iWorkerDAO) throws DALException {
        // Deletes all workers from test DB.
        for (IWorkerDTO workerDTO : iWorkerDAO.getWorkerList()){
            iWorkerDAO.deleteWorker(workerDTO.getEmail());
        }
    }

    public static void clearEmployerTestTable (IEmployerDAO iEmployerDAO) throws DALException {
        // Deletes all Employers from DB.
        for (IEmployerDTO employerDTO : iEmployerDAO.getiEmployerList()){
            iEmployerDAO.deleteiEmployer(employerDTO.getEmployerID());
        }
    }

    public static void clearJobTestTable (IJobDAO iJobDAO) throws DALException {
        // Deletes all Jobs from DB.
        for (IJobDTO jobDTO : iJobDAO.getIJobList()){
            iJobDAO.deleteIJob(jobDTO.getJobID());
        }
    }

    public static void clearActivityTestTable (IActivityDAO iActivityDAO) throws DALException {
        // Deletes all Jobs from DB.
        for (IActivityDTO activityDTO : iActivityDAO.getiActivityList()){
            iActivityDAO.deleteiActivity(activityDTO.getActivityID());
        }
    }




    // endregion
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
