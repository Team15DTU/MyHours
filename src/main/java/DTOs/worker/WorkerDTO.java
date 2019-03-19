package DTOs.worker;

import DTOs.workPlace.WorkPlaceDTO;
import address.Address;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class WorkerDTO {

    /*
    -------------------------- Fields --------------------------
     */

    private int userID;
    private String firstName;
    private String surName;
    private String email;
    private LocalDate birthday;
    private Address homeAddress;
    private List<WorkPlaceDTO> workPlaces;
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public WorkerDTO () {}
    
    /*
    ------------------------ Properties -------------------------
     */

    // region "Properties"

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public List<WorkPlaceDTO> getWorkPlaces() {
        return workPlaces;
    }

    public void setWorkPlaces(List<WorkPlaceDTO> workPlaces) {
        this.workPlaces = workPlaces;
    }


    // endregion
    
    /*
    ---------------------- Public Methods -----------------------
     */
    
    
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
