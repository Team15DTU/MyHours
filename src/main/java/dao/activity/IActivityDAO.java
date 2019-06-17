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

    IActivityDTO getiActivity(int activityID) throws DALException;

    // Returns a List of ActivityDTO object.
    List<IActivityDTO> getiActivityList() throws DALException;

    // Returns a List of ActivityDTO object.
    List<IActivityDTO> getiActivityList(int jobID) throws DALException;

    // Returns a List of ActivityDTO object matching imputted JobID, and startDAte between fromDate and toDate.
    List<IActivityDTO> getiActivityList(int jobID, LocalDate fromStartDate, LocalDate toStartDate) throws DALException;

    // Returns a List of ActivityDTO object where startDate is between fromDate and toDate.
    List<IActivityDTO> getiActivityList(LocalDate fromStartDate, LocalDate toStartDate) throws DALException;

    // Inserts the data from a ActivityDTO into DB.
    void createiActivity (IActivityDTO activity) throws DALException;

    // Updates the data on a Shifts row in the DB.
    void updateiActivity (IActivityDTO activity) throws DALException;


    void deleteiActivity (int activityID) throws DALException;

}
