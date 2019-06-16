package dao.activity;

import dao.DALException;
import dto.activity.IActivityDTO;

import java.time.LocalDate;
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
    IActivityDTO getiActivity(int userID, LocalDateTime dateAndTime) throws DALException;

    // TODO: Det her er bedre end ovenstående.
    IActivityDTO getiActivity(int activityID) throws DALException;

    // Returns a List of ActivityDTO object.
    List<IActivityDTO> getiActivityList() throws DALException;

    // Returns a List of ActivityDTO object.
    List<IActivityDTO> getiActivityList(int jobID) throws DALException;

    // Returns a List of ActivityDTO object.
    List<IActivityDTO> getiActivityList(int jobID, LocalDate fromDate, LocalDate toDate) throws DALException;

    //TODO: LIST som får alle en brugers vagter.

    // Inserts the data from a ActivityDTO into DB.
    void createiActivity (IActivityDTO activity) throws DALException;

    // Updates the data on a Shifts row in the DB.
    void updateiActivity (IActivityDTO activity) throws DALException;

    // Deletes all information about one Shift, from an UserID and a Date and time of the activity TODO: Maybe use ShiftID?.
    void deleteiActivity (int userID, LocalDateTime dateAndTime) throws DALException;

    // TODO: Det her giver bedre mening end ovenstående
    void deleteiActivity (int activityID) throws DALException;

}
