package dto.activity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dto.job.IJobDTO;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author Rasmus Sander Larsen
 */
@JsonDeserialize(as = ActivityDTO.class)
public interface IActivityDTO {

    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"

    int getActivityID();
    void setActivityID(int activityID);

    int getJobID();
    void setJobID(int jobID);

    LocalDateTime getStartingDateTime();
    void setStartingDateTime(LocalDateTime startingDateTime);

    LocalDateTime getEndingDateTime();
    void setEndingDateTime(LocalDateTime endingDateTime);

    Duration getPause();
    void setPause(Duration pause);

    double getActivityValue();
    void setActivityValue(double activityValue);

    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

    public void calculateActivityValue(IJobDTO jobDTO);
}
