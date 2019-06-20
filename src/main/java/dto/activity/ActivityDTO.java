package dto.activity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonFormat(pattern = "yyyy-dd-MM'T'HH:mm")
    //@JsonFormat(pattern="dd-MM-yyyy HH:mm" ) // Til Firefox
    private Timestamp startingDateTime;
    @JsonFormat(pattern = "yyyy-dd-MM'T'HH:mm")
    private Timestamp endingDateTime;
    private int pauseInMinuts; // break in minutes!
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

    public ActivityDTO(Timestamp startingDateTime, Timestamp endingDateTime, int jobID, int pause) {
        activityID = ArrayDBController.activityID++;
        this.startingDateTime = startingDateTime;
        this.endingDateTime = endingDateTime;
        this.jobID = jobID;
        this.pauseInMinuts = pause;
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

    public int getPauseInMinuts() {
        return pauseInMinuts;
    }

    public void setPauseInMinuts(int pauseInMinuts) {
        this.pauseInMinuts = pauseInMinuts;
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

        long payedMinutesOnShift = Duration.between(startingDateTime.toLocalDateTime(),endingDateTime.toLocalDateTime()).minusMinutes(pauseInMinuts).toMinutes();

        activityValue = payedMinutesOnShift*stdSalaryPrMin;
    }
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
