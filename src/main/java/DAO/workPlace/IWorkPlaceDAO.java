package DAO.workPlace;

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
    WorkPlaceDTO getWorkPlace (int workplaceID);

    // Returns a List of ALL WorkPlaceDTO object.
    List<WorkPlaceDTO> getWorkPlaceList ();

    // Returns a List of ONE workers WorkPlaceDTO object.
    List<WorkPlaceDTO> getWorkPlaceList (int workerID);

    // Inserts the data from a WorkPlaceDTO into DB.
    void createWorkPlace (WorkPlaceDTO workPlaceDTO);

    // Updates the data on a workPlace row in the DB.
    void updateWorkPlace (WorkPlaceDTO workPlaceDTO);

    // Deletes all information about one workPlace, from an UserID and workPlace.Name TODO: Maybe use workPlaceID?.
    void deleteWorkPlace (int workplaceID);

}
