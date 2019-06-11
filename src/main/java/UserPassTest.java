import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/Kappa")
public class UserPassTest {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean createUser(UserPass user){
        System.out.println(user);
        return true;
    }
}
