package DTOs.worker;

import DTOs.workPlace.WorkPlaceDTO;
import DTOs.address.Address;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class WorkerDTO implements IWorkerDTO {

    /*
    -------------------------- Fields --------------------------
     */

    private int workerID;
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

    public int getWorkerID() {
        return workerID;
    }

    public void setWorkerID(int workerID) {
        this.workerID = workerID;
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
    
    public String toString () {

        StringBuilder toStringBuilder = new StringBuilder();

        toStringBuilder.append("WorkerID:\t\t" + workerID + "\n");
        toStringBuilder.append("First Name:\t\t" + firstName + "\n");
        toStringBuilder.append("Surname:\t\t" + surName + "\n");
        toStringBuilder.append("E-mail:\t\t\t" + email + "\n");
        toStringBuilder.append("Birthday:\t\t" + birthday + "\n");

        return toStringBuilder.toString();
    }
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
