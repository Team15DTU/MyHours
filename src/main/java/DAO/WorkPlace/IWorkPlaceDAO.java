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

    // returns a WorkPlaceDTO from an UserID and workPlaceID FIXME: Do we need to create a WorkplaceID?.
    // FIXME: Or can we use UserID and WorkPlace.name(say it is a PrimaryKey)?
    WorkPlaceDTO getWorkPlace (int userID, int workPlaceID);

    // Returns a List of WorkPlaceDTO object.
    List<WorkPlaceDTO> getWorkPlaceList (WorkerDTO workerDTO);

    // Inserts the data from a WorkPlaceDTO into DB.
    void createWorkPlace (WorkPlaceDTO workPlaceDTO);

    // Updates the data on a WorkPlace row in the DB.
    void updateWorkPlace (WorkPlaceDTO workPlaceDTO);

    // Deletes all information about one WorkPlace, from an UserID and WorkPlace.Name TODO: Maybe use workPlaceID?.
    void deleteWorkPlace (int userID, String workPlaceName);


}
