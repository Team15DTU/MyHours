package DTOs.job;

import DTOs.address.Address;
import DTOs.ruleSet.RuleSet;
import DTOs.shift.IShiftDTO;
import DTOs.shift.ShiftDTO;

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

    List<IShiftDTO> getiShiftDTOList();

    void setiShiftDTOList(List<IShiftDTO> iShiftDTOList);


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */
    


}
