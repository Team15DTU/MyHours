package dto.job;

import dto.address.Address;
import dto.ruleSet.RuleSet;
import dto.activity.IActivityDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
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

    Address getJobAddress();

    void setJobAddress(Address jobAddress);

    RuleSet getRuleSet();

    void setRuleSet(RuleSet ruleSet);

    List<IActivityDTO> getiActivityDTOList();

    void setiActivityDTOList(List<IActivityDTO> iActivityDTOList);

    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */
    


}
