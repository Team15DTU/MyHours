package DTOs.workPlace;

import DTOs.job.JobDTO;

import java.awt.*;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class WorkPlaceDTO {

    /*
    -------------------------- Fields --------------------------
     */
    
    private String name;
    private Color color;
    private int tlf;
    private List<JobDTO> jobList;
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public WorkPlaceDTO () {}
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"

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

    public int getTlf() {
        return tlf;
    }

    public void setTlf(int tlf) {
        this.tlf = tlf;
    }

    public List<JobDTO> getJobDTOList() {
        return jobList;
    }

    public void setJobDTOList(List<JobDTO> jobDTOList) {
        this.jobList = jobDTOList;
    }


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */
    
    
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
