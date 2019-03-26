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
    
    private String name;
    private LocalDate hireDate;
    private WorkPlaceDTO workPlaceDTO;
    private Address jobAddress;
    private RuleSet ruleSet;
    private List<ShiftDTO> shifts;
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public JobDTO () {}
    
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

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public WorkPlaceDTO getWorkPlaceDTO() {
        return workPlaceDTO;
    }

    public void setWorkPlaceDTO(WorkPlaceDTO workPlaceDTO) {
        this.workPlaceDTO = workPlaceDTO;
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
    
    
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
