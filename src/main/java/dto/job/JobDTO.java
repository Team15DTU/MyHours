package dto.job;

import db.ArrayDBController;
import dto.activity.IActivityDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class JobDTO implements IJobDTO {

    /*
    -------------------------- Fields --------------------------
     */

    private int jobID;
    private int employerID;
    private String jobName;
    private LocalDate hireDate;
    private LocalDate finishDate;
    private double stdSalary;
    private List<IActivityDTO> iActivityDTOList;
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public JobDTO () {
        jobID = ArrayDBController.jobID++;
        iActivityDTOList = new ArrayList<>();
    }

    public JobDTO (int employerID, String jobName, double stdSalary) {
        this.employerID = employerID;
        this.jobName = jobName;
        this.stdSalary = stdSalary;
        hireDate = null;
        finishDate = null;
        iActivityDTOList = new ArrayList<>();
    }

    public JobDTO (int employerID, String jobName, double stdSalary, LocalDate hireDate, LocalDate finishDate, List<IActivityDTO> iActivityDTOList) {
        jobID = ArrayDBController.jobID++;
        this.employerID = employerID;
        this.jobName = jobName;
        this.stdSalary = stdSalary;
        this.hireDate = hireDate;
        this.finishDate = finishDate;
        this.iActivityDTOList = iActivityDTOList;
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

    public int getEmployerID() {
        return employerID;
    }

    public void setEmployerID(int employerID) {
        this.employerID = employerID;
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

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public double getStdSalary() {
        return stdSalary;
    }

    public void setStdSalary(double stdSalary) {
        this.stdSalary = stdSalary;
    }

    public List<IActivityDTO> getiActivityDTOList() {
        return iActivityDTOList;
    }

    public void setiActivityDTOList(List<IActivityDTO> iActivityDTOList) {
        this.iActivityDTOList = iActivityDTOList;
    }

    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

    public String toString () {
        StringBuilder toStringBuilder = new StringBuilder();
        toStringBuilder.append("JobID: \t\t\t\t" + jobID + "\n");
        toStringBuilder.append("EmployerID:\t\t" + employerID + "\n");
        toStringBuilder.append("Job Name: \t\t\t" + jobName + "\n");
        if (hireDate != null) {
            toStringBuilder.append("HireDate: \t\t\t" + hireDate.toString() + "//" + hireDate.getDayOfMonth() + "-" + hireDate.getMonthValue() + "-" + hireDate.getYear() + "\n");
        }
        if (finishDate != null) {
            toStringBuilder.append("FinishDate: \t\t\t" + finishDate.toString() + "//" + finishDate.getDayOfMonth() + "-" + finishDate.getMonthValue() + "-" + finishDate.getYear() + "\n");
        }
        toStringBuilder.append("Standard Salary:\t" + stdSalary + "\n");

        return toStringBuilder.toString();
    }

    /*
    ---------------------- Support Methods ----------------------
     */


}
