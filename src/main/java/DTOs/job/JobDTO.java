package DTOs.job;

import DTOs.shift.ShiftDTO;
import DTOs.ruleSet.RuleSet;
import DTOs.workPlace.WorkPlaceDTO;
import DTOs.address.Address;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class JobDTO {

    /*
    -------------------------- Fields --------------------------
     */

    private int jobID;
    private WorkPlaceDTO workPlaceDTO; //TODO: Skal vi bruge WorkplaceDTO objekt eller bare WorkplaceID?
    private String jobName;
    private LocalDate hireDate;
    private double stdSalary;
    private Address jobAddress;
    private RuleSet ruleSet;
    private List<ShiftDTO> shifts;
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public JobDTO () {
    }
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public WorkPlaceDTO getWorkPlaceDTO() {
        return workPlaceDTO;
    }

    public void setWorkPlaceDTO(WorkPlaceDTO workPlaceDTO) {
        this.workPlaceDTO = workPlaceDTO;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public double getStdSalary() {
        return stdSalary;
    }

    public void setStdSalary(double stdSalary) {
        this.stdSalary = stdSalary;
    }

    public Address getJobAddress() {
        return jobAddress;
    }

    public void setJobAddress(Address jobAddress) {
        this.jobAddress = jobAddress;
    }

    public RuleSet getRuleSet() {
        return ruleSet;
    }

    public void setRuleSet(RuleSet ruleSet) {
        this.ruleSet = ruleSet;
    }

    public List<ShiftDTO> getShifts() {
        return shifts;
    }

    public void setShifts(List<ShiftDTO> shifts) {
        this.shifts = shifts;
    }


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

    public String toString () {
        StringBuilder toStringBuilder = new StringBuilder();
        toStringBuilder.append("JobID: \t\t\t\t" + jobID + "\n");
        toStringBuilder.append("WorkplaceID:\t\t" + workPlaceDTO.getWorkplaceID() + "\n");
        toStringBuilder.append("Job Name: \t\t\t" + jobName + "\n");
        if (hireDate != null) {
            toStringBuilder.append("HireDate: \t\t\t" + hireDate.toString() + "//" + hireDate.getDayOfMonth() + "-" + hireDate.getMonthValue() + "-" + hireDate.getYear() + "\n");
        }
        toStringBuilder.append("Standard Salary:\t" + stdSalary + "\n");

        return toStringBuilder.toString();
    }

    /*
    ---------------------- Support Methods ----------------------
     */


}
