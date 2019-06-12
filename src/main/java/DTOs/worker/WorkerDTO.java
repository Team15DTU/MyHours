package DTOs.worker;

import DTOs.address.IAddress;
import DTOs.workPlace.IEmployerDTO;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
@Path("/Test2")
public class WorkerDTO implements IWorkerDTO {

    /*
    -------------------------- Fields --------------------------
     */

    private int workerID;
    private String firstName;
    private String surName;
    private String email;
    private String password;
    private LocalDate birthday;
    private IAddress homeAddress;
    private List<IEmployerDTO> employers;
    
    /*
    ----------------------- Constructor -------------------------
     */
    public WorkerDTO () {}

    public WorkerDTO (String firstName, String surName, String email)
    {
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
        birthday = null;
        homeAddress = null;
        employers = null;
    }

    public WorkerDTO (String email, String password)
    {
        this.email = email;
        this.password = password;
    }
    
    /**
     * This constructs a WorkerDTO object with all fields specified.
     * @param workerID ID of Worker
     * @param firstName Firstname
     * @param surName Surname
     * @param email Unique email of Worker
     * @param password Password
     * @param birthday Workers day of birth
     * @param homeAddress Address of Worker
     * @param employers List of Workers employers
     */
    public WorkerDTO(int workerID, String firstName, String surName, String email, String password, LocalDate birthday, IAddress homeAddress, List<IEmployerDTO> employers)
    {
        this.workerID = workerID;
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.homeAddress = homeAddress;
        this.employers = employers;
    }
    
    public WorkerDTO(String firstName, String surName, String email, LocalDate birthday, IAddress homeAddress, List<IEmployerDTO> employers)
    {
        this.firstName = firstName;
        this.surName = surName;
        this.email = email;
        this.birthday = birthday;
        this.homeAddress = homeAddress;
        this.employers = employers;
        this.password = null;
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
    
    public String getPassword() { return password; }
    
    public void setPassword(String password) { this.password = password; }
    
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public IAddress getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(IAddress homeAddress) {
        this.homeAddress = homeAddress;
    }
    
    @Override
    public List<IEmployerDTO> getIEmployers() { return employers; }
    
    @Override
    public void setIEmployers(List<IEmployerDTO> workPlaces) { this.employers = workPlaces; }


    @POST
    @Path("/hey")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean loginCheck(WorkerDTO user) {
        System.out.println(user);
        System.out.println(user.getEmail() + " " + user.getPassword());

        System.out.println(user);

        //System.out.println(worker.getEmail()+" "+worker.getPassword());

        return true;
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
