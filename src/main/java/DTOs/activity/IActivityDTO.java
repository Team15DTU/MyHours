package DTOs.activity;

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

    LocalDateTime getStartingDateTime();
    void setStartingDateTime(LocalDateTime startingDateTime);

    LocalDateTime getEndingDateTime();
    void setEndingDateTime(LocalDateTime endingDateTime);

    int getJobID();
    void setJobID(int jobID);

    Duration getPause();
    void setPause(Duration pause);

    double getShiftValue();
    void setShiftValue(double shiftValue);

    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */
    
    
    

}
