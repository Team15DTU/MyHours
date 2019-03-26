package DAO.workPlace;

import DAO.DALException;
import DTOs.workPlace.WorkPlaceDTO;
import DTOs.worker.WorkerDTO;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public interface IWorkPlaceDAO {

    /*
    ---------------------- Public Methods -----------------------
     */

    // returns a WorkPlaceDTO from an workplaceID
    WorkPlaceDTO getWorkPlace (int workplaceID) throws DALException;

    // Returns a List of ALL WorkPlaceDTO object.
    List<WorkPlaceDTO> getWorkPlaceList () throws DALException;

    // Returns a List of ONE workers WorkPlaceDTO object.
    List<WorkPlaceDTO> getWorkPlaceList (int workerID) throws DALException;

    // Inserts the data from a WorkPlaceDTO into DB.
    void createWorkPlace (WorkPlaceDTO workPlaceDTO) throws DALException;

    // Updates the data on a workPlace row in the DB.
    void updateWorkPlace (WorkPlaceDTO workPlaceDTO) throws DALException;

    // Deletes all information about one workPlace, from an UserID and workPlace.Name TODO: Maybe use workPlaceID?.
    void deleteWorkPlace (int workplaceID) throws DALException;

}
