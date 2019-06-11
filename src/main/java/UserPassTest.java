import DTOs.worker.IWorkerDTO;
import DTOs.worker.WorkerDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/Kappa")
public class UserPassTest {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean createUser(WorkerDTO user){
        System.out.println(user);
        System.out.println(user.getEmail() +" "+user.getPassword());

        System.out.println(user);
        return true;
    }
}
