package dto.employer;

import dto.job.IJobDTO;


import java.awt.*;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class EmployerDTO implements IEmployerDTO {

    /*
    -------------------------- Fields --------------------------
     */

    private int employerID;
    private int workerID;
    private String name;
    private Color color;
    private String telephone;
    private List<IJobDTO> iJobList;

    /*
    ----------------------- Constructor -------------------------
     */
    
    public EmployerDTO() {}

    public EmployerDTO(int workerID, String name)
    {
        this.workerID = workerID;
        this.name = name;
        color = null;
        iJobList = null;
    }

    public EmployerDTO(int workerID, String name, Color color, String  telephone, List<IJobDTO> iJobList)
    {
        this.workerID = workerID;
        this.name = name;
        this.color = color;
        this.telephone = telephone;
        this.iJobList = iJobList;
    }
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"

    public int getEmployerID() {
        return employerID;
    }

    public void setEmployerID(int employerID) {
        this.employerID = employerID;
    }

    public int getWorkerID() {
        return workerID;
    }

    public void setWorkerID(int workerID) {
        this.workerID = workerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<IJobDTO> getIJobList() {
        return iJobList;
    }

    public void setIJobList(List<IJobDTO> iJobList) {
        this.iJobList = iJobList;
    }

    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */
    
    public String toString () {
        StringBuilder toStringBuilder = new StringBuilder();

        toStringBuilder.append("EmployerID: \t" + employerID + "\n");
        toStringBuilder.append("WorkerID: \t" + workerID + "\n");
        toStringBuilder.append("Employer Name: \t" + name + "\n");
        toStringBuilder.append("Color: \t" + color.toString() + "\n");
        toStringBuilder.append("Telephone: \t" + telephone + "\n");

        return toStringBuilder.toString();
    }
    
    /*
    ---------------------- Support Methods ----------------------
     */

}
