package DTOs.workPlace;

import DTOs.job.IJobDTO;


import java.awt.*;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class WorkPlaceDTO implements IWorkPlaceDTO {

    /*
    -------------------------- Fields --------------------------
     */

    private int workplaceID;
    private int workerID;
    private String name;
    private Color color;
    private int telephone;
    private List<IJobDTO> iJobList;

    /*
    ----------------------- Constructor -------------------------
     */
    
    public WorkPlaceDTO () {}

    public WorkPlaceDTO (int workerID, String name) {
        this.workerID = workerID;
        this.name = name;
        color = null;
        iJobList = null;
    }

    public WorkPlaceDTO (int workerID, String name, Color color, int telephone, List<IJobDTO> iJobList) {
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

    public int getWorkplaceID() {
        return workplaceID;
    }

    public void setWorkplaceID(int workplaceID) {
        this.workplaceID = workplaceID;
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

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
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

        toStringBuilder.append("WorkplaceID: \t" + workplaceID + "\n");
        toStringBuilder.append("WorkerID: \t" + workerID + "\n");
        toStringBuilder.append("Workplace Name: \t" + name + "\n");
        toStringBuilder.append("Color: \t" + color.toString() + "\n");
        toStringBuilder.append("Telephone: \t" + telephone + "\n");

        return toStringBuilder.toString();
    }
    
    /*
    ---------------------- Support Methods ----------------------
     */

}
