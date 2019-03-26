package DAO.WorkPlace;

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

    // Updates the data on a WorkPlace row in the DB.
    void updateWorkPlace (WorkPlaceDTO workPlaceDTO);

    // Deletes all information about one WorkPlace, from an workplaceID.
    void deleteWorkPlace (int workplaceID);


}
