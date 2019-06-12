package dto.worker;

import dto.address.IAddress;
import dto.workPlace.IEmployerDTO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */

@Entity
@Table (name = "Workers")
public class WorkerHiberDTO implements IWorkerDTO {

    /*
    -------------------------- Fields --------------------------
     */

    @Id
    @Column(name = "workerID")
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private int workerID;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "surname")
    private String surName;

    @Column(name = "email")
    private String email;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "pass")
    private String password;

    @Transient
    private IAddress homeAddress;

    @Transient
    private List<IEmployerDTO> employers;

    /*
    ----------------------- Constructor -------------------------
     */

    public WorkerHiberDTO() {}

    public WorkerHiberDTO(String firstName, String surName, String email, String password) {
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
        this.password = password;
        birthday = null;
        homeAddress = null;
        employers = null;
    }

    public WorkerHiberDTO(String firstName, String surName, String email, LocalDate birthday, IAddress homeAddress, List<IEmployerDTO> employers)
    {
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
        this.birthday = birthday;
        this.homeAddress = homeAddress;
        this.employers = employers;
    }
    
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public IAddress getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(IAddress homeAddress) {
        this.homeAddress = homeAddress;
    }

    public List<IEmployerDTO> getIEmployers() {
        return employers;
    }

    public void setIEmployers(List<IEmployerDTO> employers) {
        this.employers = employers;
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
