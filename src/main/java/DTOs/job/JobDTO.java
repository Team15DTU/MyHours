package DTOs.job;

import DTOs.shift.ShiftDTO;
import DTOs.ruleSet.RuleSet;
import DTOs.address.Address;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class JobDTO implements IJobDTO {

    /*
    -------------------------- Fields --------------------------
     */

    private int jobID;
    private int workplaceID;
    private String jobName;
    private LocalDate hireDate;
    private double stdSalary;
    private Address jobAddress;
    private RuleSet ruleSet;
    private List<ShiftDTO> shiftList;
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public JobDTO () {}
    public JobDTO (int workplaceID, String jobName, double stdSalary) {
        this.workplaceID = workplaceID;
        this.jobName = jobName;
        this.stdSalary = stdSalary;
        hireDate = null;
        jobAddress = null;
        ruleSet = null;
        shiftList = null;
    }
    public JobDTO (int workplaceID, String jobName, double stdSalary, LocalDate hireDate, Address jobAddress, RuleSet ruleSet, List<ShiftDTO> shiftList) {
        this.workplaceID = workplaceID;
        this.jobName = jobName;
        this.stdSalary = stdSalary;
        this.hireDate = hireDate;
        this.jobAddress = jobAddress;
        this.ruleSet = ruleSet;
        this.shiftList = shiftList;
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

    public int getWorkplaceID() {
        return workplaceID;
    }

    public void setWorkplaceID(int workplaceID) {
        this.workplaceID = workplaceID;
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

    public List<ShiftDTO> getShiftList() {
        return shiftList;
    }

    public void setShiftList(List<ShiftDTO> shiftList) {
        this.shiftList = shiftList;
    }


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

    public String toString () {
        StringBuilder toStringBuilder = new StringBuilder();
        toStringBuilder.append("JobID: \t\t\t\t" + jobID + "\n");
        toStringBuilder.append("WorkplaceID:\t\t" + workplaceID + "\n");
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
