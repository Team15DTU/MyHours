package dto.worker;

import dao.worker.WorkerConstants;
import db.ArrayDBController;
import dto.employer.IEmployerDTO;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */

@Entity
@Table (name = WorkerConstants.TABLENAME)
public class WorkerHiberDTO implements IWorkerDTO {

    /*
    -------------------------- Fields --------------------------
     */

    @Id
    @Column(name = WorkerConstants.id)
    //@GeneratedValue(generator="increment")
    //@GenericGenerator(name="increment", strategy = "increment")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int workerID;

    @Column(name = WorkerConstants.firstname)
    private String firstName;

    @Column(name = WorkerConstants.surname)
    private String surName;

    @Column(name = WorkerConstants.email)
    private String email;

    @Column(name = WorkerConstants.birthday)
    private LocalDate birthday;

    @Column(name = WorkerConstants.password)
    private String password;

    @Transient
    private List<IEmployerDTO> employers;

    /*
    ----------------------- Constructor -------------------------
     */

    public WorkerHiberDTO()
    {
        workerID = ArrayDBController.workerID++;
        employers = new ArrayList<>();
    }

    public WorkerHiberDTO(String firstName, String surName, String email, String password) {
        workerID = ArrayDBController.workerID++;
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
        this.password = password;
        employers = new ArrayList<>();
        birthday = null;
    }

    public WorkerHiberDTO(String firstName, String surName, String email, LocalDate birthday, List<IEmployerDTO> employers)
    {
        workerID = ArrayDBController.workerID++;
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
        this.birthday = birthday;
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
