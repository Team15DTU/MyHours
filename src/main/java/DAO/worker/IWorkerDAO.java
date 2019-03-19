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

    // returns a WorkerDTO from an e-mail
    WorkerDTO getWorker (String email);

    // Returns a List of workerDTO objects.
    List<WorkerDTO> getWorkerList ();

    // Inserts the data from the WorkerDTO and a password into the DB.
    void createWorker (WorkerDTO worker, String password);

    // Updates the data on a Worker row in the DB.
    void updateWorker (WorkerDTO worker);

    // Deletes all information about one Worker, from its email.
    void deleteWorker (String email);

}
