package DTOs.shift;

import DAO.shift.IShiftDAO;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author Rasmus Sander Larsen
 */
public class ShiftDTO implements IShiftDTO {

    /*
    -------------------------- Fields --------------------------
     */
    
    private LocalDateTime startingDateTime;
    private LocalDateTime endingDateTime;
    private int jobID;
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

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
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
