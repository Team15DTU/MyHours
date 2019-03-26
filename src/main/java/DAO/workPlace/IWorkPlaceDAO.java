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

    // returns a WorkPlaceDTO from an UserID and workPlaceID FIXME: Do we need to create a WorkplaceID?.
    // FIXME: Or can we use UserID and workPlace.name(say it is a PrimaryKey)?
    WorkPlaceDTO getWorkPlace (int userID, int workPlaceID);

    // Returns a List of WorkPlaceDTO object.
    List<WorkPlaceDTO> getWorkPlaceList (WorkerDTO workerDTO);

    // Inserts the data from a WorkPlaceDTO into DB.
    void createWorkPlace (WorkPlaceDTO workPlaceDTO);

    // Updates the data on a workPlace row in the DB.
    void updateWorkPlace (WorkPlaceDTO workPlaceDTO);

    // Deletes all information about one workPlace, from an UserID and workPlace.Name TODO: Maybe use workPlaceID?.
    void deleteWorkPlace (int userID, String workPlaceName);


}
