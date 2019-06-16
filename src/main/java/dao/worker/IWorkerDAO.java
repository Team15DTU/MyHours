package dao.worker;

import dao.DALException;
import dto.worker.IWorkerDTO;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */

//TODO: getWorker(int id) needs to be implemented

public interface IWorkerDAO {

    /*
    ---------------------- Public Methods -----------------------
     */

    /**
     * This Methods should return a WorkerDTO object, containing all the details matching the inputted email
     * @param email this is the email the methods creates a WorkerDTO from.
     * @return a WorkerDTO containing the correct details.
	 * @exception DALException Will throw a DALException.
     */
    IWorkerDTO getWorker (String email) throws DALException;

    /**
     * This Method returns a List of WorkerDTO objects.
     * @return a List<WorkerDTO>
	 * @exception DALException Will throw a DALException.
     */
    List<IWorkerDTO> getWorkerList () throws DALException;

    /**
     * This Method inserts the details from the inputted WorkerDTO into the MYSQL_DB
     * @param worker This is the object containing the details to pass into the DB.
	 * @exception DALException Will throw a DALException.
     */
    void createWorker (IWorkerDTO worker) throws DALException;

    /**
     * This Method finds the Worker matching the WorkerDTO object that is inputted
     * and updates the existing details in the DB with the information from the inputted WorkerDTO.
	 * @param worker This object contains the details that DB should be updated with.
	 * @return Will return the number of rows affected.
	 * @exception DALException Will throw a DALException.
	 */
    int updateWorker (IWorkerDTO worker) throws DALException;

    /**
     * This method should delete all information about a specific Worker matching the inputted email.
     * @param email This is the email matching the Worker we wishes to delete.
	 * @exception DALException Will throw a DALException.
     */
    void deleteWorker (String email) throws DALException;

}
