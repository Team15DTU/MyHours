package dto.job;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dto.activity.IActivityDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */

@JsonDeserialize(as = JobDTO.class)
public interface IJobDTO {

    // <editor-folder desc="Properties"

    int getJobID();

    void setJobID(int jobID);

    int getEmployerID();

    void setEmployerID(int employerID);

    String getJobName();

    void setJobName(String jobName);

    LocalDate getHireDate();

    void setHireDate(LocalDate hireDate);

    LocalDate getFinishDate();

    void setFinishDate(LocalDate finishDate);

    double getStdSalary();

    void setStdSalary(double stdSalary);

    List<IActivityDTO> getiActivityDTOList();

    void setiActivityDTOList(List<IActivityDTO> iActivityDTOList);

    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

}
