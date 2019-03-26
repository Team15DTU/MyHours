import DAO.DALException;
import DAO.job.JobDAO;
import DAO.workPlace.WorkPlaceDAO;
import DTOs.job.JobDTO;
import DTOs.workPlace.WorkPlaceDTO;
import db.DBController;
import db.MySQL_DB;

import java.awt.*;

/**
 * @author Rasmus Sander Larsen
 */
public class JobDAOTester {

    public static void main(String[] args) throws DALException {

        MySQL_DB mySQL_db = new MySQL_DB();

        DBController dbController = new DBController(mySQL_db);
        WorkPlaceDAO workPlaceDAO = new WorkPlaceDAO(dbController);

        JobDAO jobDAO = new JobDAO(dbController, workPlaceDAO);

        WorkPlaceDTO testWorkplaceDTO = workPlaceDAO.getWorkPlace(1);

        JobDTO testJobDTO = new JobDTO();

        testJobDTO.setJobName("TestJob2");
        testJobDTO.setWorkPlaceDTO(testWorkplaceDTO);
        testJobDTO.setJobName("Flaskedreng");
        testJobDTO.setStdSalary(150.00);

        //System.out.println(testJobDTO);

        // jobDAO.createJob(testJobDTO);

        // System.out.println(jobDAO.getJob(1));
        // workPlaceDAO.createWorkPlace(workplace);
        //System.out.println(workplace);

       // System.out.println(workPlaceDAO.getWorkPlace(2));


        int counter = 1;

        for (JobDTO jobDTO : jobDAO.getJobList("workplaceID = 2" )) {
            System.out.println("~~~~ Job: " + counter++ + " ~~~~");
            System.out.println(jobDTO);
        }

        /*
        WorkPlaceDTO updatingWP = workPlaceDAO.getWorkPlace(13);

        System.out.println("OPDATING WP");
        System.out.println(updatingWP);

        updatingWP.setTelephone(12345678);
        workPlaceDAO.updateWorkPlace(updatingWP);

        System.out.println(workPlaceDAO.getWorkPlace(13));

        workPlaceDAO.deleteWorkPlace(13);

        int counter = 1;

        for (WorkPlaceDTO workPlaceDTO : workPlaceDAO.getWorkPlaceList()) {
            System.out.println("~~~~ Workplace: " + counter++ + " ~~~~");
            System.out.println(workPlaceDTO);
        }
        */

    }

    public enum hej {
        hej,
        farvel
    }
}
