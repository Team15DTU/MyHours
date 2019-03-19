package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Rasmus Sander Larsen
 */
public class MySQL_DB {

    /*
    -------------------------- Fields --------------------------
     */

    private final String url = "jdbc:mysql://mysql26.unoeuro.com:3306/runningessentials_dk_db_myhours";
    private final String user = "runningesse_dk";
    private final String password = "ras92mus";
    
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public MySQL_DB () {}
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

    public Connection createConnection()  {
        try {
            return DriverManager.getConnection(url,user, password);
        } catch (SQLException e) {
            throw new IllegalStateException();
        }
    }
    
    /*
    ---------------------- Support Methods ----------------------
     */



}
