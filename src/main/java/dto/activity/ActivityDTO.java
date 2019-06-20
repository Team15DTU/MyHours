package dto.activity;

import java.sql.Timestamp;
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
    private Timestamp startingDateTime;
    private Timestamp endingDateTime;
    private int pause; // break in minutes!
    private double activityValue;

    /*
    ----------------------- Constructor -------------------------
     */
    
    public ActivityDTO() {}

    public ActivityDTO(Timestamp startingDateTime, Timestamp endingDateTime, int jobID) {
        this.startingDateTime = startingDateTime;
        this.endingDateTime = endingDateTime;
        this.jobID = jobID;

    }

    public ActivityDTO(Timestamp startingDateTime, Timestamp endingDateTime, int jobID, int pause) {
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

    @Override
    public Timestamp getStartingDateTime() {
        return startingDateTime;
    }

    public void setStartingDateTime(Timestamp startingDateTime) {
        this.startingDateTime = startingDateTime;
    }

    @Override
    public Timestamp getEndingDateTime() {
        return endingDateTime;
    }

    public void setEndingDateTime(Timestamp endingDateTime) {
        this.endingDateTime = endingDateTime;
    }

    public int getPause() {
        return pause;
    }

    public void setPause(int pause) {
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
