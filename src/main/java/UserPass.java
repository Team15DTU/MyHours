
public class UserPass {
    private String username;
    private String password;

    public UserPass(){

    }
    public UserPass(String username, String password){
        this.username=username;
        this.password=password;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }

    public void setUsername(String username){
        this.username=username;
        System.out.println(username);

    }

    public void setPassword(String password){
        this.password=password;
        System.out.println(password);

    }

}
