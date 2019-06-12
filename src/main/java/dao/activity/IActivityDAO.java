package dao.activity;

import dto.activity.IActivityDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public interface IActivityDAO {

    /*
    ---------------------- Public Methods -----------------------
     */

    // returns a ActivityDTO from an UserID and a Date and time of the activity TODO: Maybe use ShiftID?.
    IActivityDTO getIShift (int userID, LocalDateTime dateAndTime);

    // Returns a List of ActivityDTO object.
    List<IActivityDTO> getIShiftList ();

    // Returns a List of ActivityDTO object.
    List<IActivityDTO> getIShiftList (int jobID);

    // Returns a List of ActivityDTO object.
    List<IActivityDTO> getIShiftList (int jobID, LocalDateTime fromDateTime, LocalDateTime toDateTime);

    //TODO: LIST som får alle en brugers vagter.

    // Inserts the data from a ActivityDTO into DB.
    void createIShift (IActivityDTO shift);

    // Updates the data on a Shifts row in the DB.
    void updateIShift (IActivityDTO shift);

    // Deletes all information about one Shift, from an UserID and a Date and time of the activity TODO: Maybe use ShiftID?.
    void deleteIShift (int userID, LocalDateTime dateAndTime);

}
