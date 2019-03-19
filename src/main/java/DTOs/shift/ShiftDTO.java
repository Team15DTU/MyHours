package DTOs.shift;

import DTOs.job.JobDTO;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author Rasmus Sander Larsen
 */
public class ShiftDTO {

    /*
    -------------------------- Fields --------------------------
     */
    
    private LocalDateTime startingDateTime;
    private LocalDateTime endingDateTime;
    private JobDTO job;
    private Duration pause; // break in minutes!
    private double shiftValue;

    /*
    ----------------------- Constructor -------------------------
     */
    
    public ShiftDTO () {}
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"

    public LocalDateTime getStartingDateTime() {
        return startingDateTime;
    }

    public void setStartingDateTime(LocalDateTime startingDateTime) {
        this.startingDateTime = startingDateTime;
    }

    public LocalDateTime getEndingDateTime() {
        return endingDateTime;
    }

    public void setEndingDateTime(LocalDateTime endingDateTime) {
        this.endingDateTime = endingDateTime;
    }

    public JobDTO getJob() {
        return job;
    }

    public void setJob(JobDTO job) {
        this.job = job;
    }

    public Duration getPause() {
        return pause;
    }

    public void setPause(Duration pause) {
        this.pause = pause;
    }

    public double getShiftValue() {
        return shiftValue;
    }

    public void setShiftValue(double shiftValue) {
        this.shiftValue = shiftValue;
    }

    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */
    
    
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
