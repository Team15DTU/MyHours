package DAO.worker;

import DTOs.workPlace.WorkPlaceDTO;
import DTOs.worker.WorkerDTO;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public interface IWorkerDAO {

    /*
    ---------------------- Public Methods -----------------------
     */

    /**
     * This Methods should return a WorkerDTO object, containing all the details matching the inputted email
     * @param email this is the email the methods creates a WorkerDTO from.
     * @return a WorkerDTO containing the correct details.
     */
    WorkerDTO getWorker (String email);

    /**
     * This Method returns a List of WorkerDTO objects.
     * @return a List<WorkerDTO>
     */
    List<WorkerDTO> getWorkerList ();


    /**
     * This Method returns a List of WorkerDTO objects.
     * @return a List<WorkerDTO>
     */
    List<WorkerDTO> getWorkerList (int noOfWorkersOnList);

    /**
     * This Method inserts the details from the inputted WorkerDTO into the MYSQL_DB
     * @param worker This is the object containing the details to pass into the DB.
     * @param password This is the password which should be added to the respected WorkerDTO.
     */
    void createWorker (WorkerDTO worker, String password);

    /**
     * This Method finds the Worker matching the WorkerDTO object that is inputted
     * and updates the existing details in the DB with the information from the inputted WorkerDTO.
     * @param worker This object contains the details that DB should be updated with.
     */
    void updateWorker (WorkerDTO worker, String password);

    /**
     * This method should delete all information about a specific Worker matching the inputted email.
     * @param email This is the email matching the Worker we wishes to delete.
     */
    void deleteWorker (String email);

}
