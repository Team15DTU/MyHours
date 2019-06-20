package dto.activity;

import db.ArrayDBController;
import dto.job.IJobDTO;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
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
    private Duration pause; // break in minutes!
    private double activityValue;

    /*
    ----------------------- Constructor -------------------------
     */
    
    public ActivityDTO() {
        activityID = ArrayDBController.activityID++;
    }

    public ActivityDTO(Timestamp startingDateTime, Timestamp endingDateTime, int jobID) {
        activityID = ArrayDBController.activityID++;
        this.startingDateTime = startingDateTime;
        this.endingDateTime = endingDateTime;
        this.jobID = jobID;

    }

    public ActivityDTO(Timestamp startingDateTime, Timestamp endingDateTime, int jobID, Duration pause) {
        activityID = ArrayDBController.activityID++;
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


    public Timestamp getStartingDateTime() {
        return startingDateTime;
    }

    public void setStartingDateTime(Timestamp startingDateTime) {
        this.startingDateTime = startingDateTime;
    }

    public Timestamp getEndingDateTime() {
        return endingDateTime;
    }

    public void setEndingDateTime(Timestamp endingDateTime) {
        this.endingDateTime = endingDateTime;
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
    
    public void calculateActivityValue(IJobDTO jobDTO) {
        double stdSalaryPrMin = jobDTO.getStdSalary()/(double)60;

        long payedMinutesOnShift = Duration.between(startingDateTime.toLocalDateTime(),endingDateTime.toLocalDateTime()).minus(pause).toMinutes();

        activityValue = payedMinutesOnShift*stdSalaryPrMin;
    }
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
