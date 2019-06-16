package dto.activity;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author Rasmus Sander Larsen
 */
public class ActivityDTO implements IActivityDTO {

    /*
    -------------------------- Fields --------------------------
     */

    private int activityID;
    private int jobID;
    private LocalDateTime startingDateTime;
    private LocalDateTime endingDateTime;
    private Duration pause; // break in minutes!
    private double activityValue;

    /*
    ----------------------- Constructor -------------------------
     */
    
    public ActivityDTO() {}

    public ActivityDTO(LocalDateTime startingDateTime, LocalDateTime endingDateTime, int jobID) {
        this.startingDateTime = startingDateTime;
        this.endingDateTime = endingDateTime;
        this.jobID = jobID;

    }

    public ActivityDTO(LocalDateTime startingDateTime, LocalDateTime endingDateTime, int jobID, Duration pause) {
        this.startingDateTime = startingDateTime;
        this.endingDateTime = endingDateTime;
        this.jobID = jobID;
        this.pause = pause;
    }

    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"


    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public LocalDateTime getStartingDateTime() {
        return startingDateTime.withNano(0);
    }

    public void setStartingDateTime(LocalDateTime startingDateTime) {
        this.startingDateTime = startingDateTime.withNano(0);
    }

    public LocalDateTime getEndingDateTime() {
        return endingDateTime.withNano(0);
    }

    public void setEndingDateTime(LocalDateTime endingDateTime) {
        this.endingDateTime = endingDateTime.withNano(0);
    }

    public Duration getPause() {
        return pause;
    }

    public void setPause(Duration pause) {
        this.pause = pause;
    }

    public double getActivityValue() {
        return activityValue;
    }

    public void setActivityValue(double activityValue) {
        this.activityValue = activityValue;
    }

    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */
    
    
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
