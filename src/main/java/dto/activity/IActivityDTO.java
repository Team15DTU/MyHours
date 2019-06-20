package dto.activity;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author Rasmus Sander Larsen
 */
public interface IActivityDTO {

    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"

    int getActivityID();
    void setActivityID(int activityID);

    int getJobID();
    void setJobID(int jobID);

    Timestamp getStartingDateTime();
    void setStartingDateTime(Timestamp startingDateTime);

    Timestamp getEndingDateTime();
    void setEndingDateTime(Timestamp endingDateTime);

    int getPause();
    void setPause(int pause);

    double getActivityValue();
    void setActivityValue(double activityValue);

    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */
}
