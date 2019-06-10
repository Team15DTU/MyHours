package dto.job;

import dto.address.Address;
import dto.ruleSet.RuleSet;
import dto.shift.IShiftDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public interface IJobDTO {

    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"

    int getJobID();

    void setJobID(int jobID);

    int getWorkplaceID();

    void setWorkplaceID(int workplaceID);

    String getJobName();

    void setJobName(String jobName);

    LocalDate getHireDate();

    void setHireDate(LocalDate hireDate);

    double getStdSalary();

    void setStdSalary(double stdSalary);

    Address getJobAddress();

    void setJobAddress(Address jobAddress);

    RuleSet getRuleSet();

    void setRuleSet(RuleSet ruleSet);

    List<IShiftDTO> getIShiftDTOList();

    void setIShiftDTOList(List<IShiftDTO> iShiftDTOList);


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */
    


}