package db;

import java.sql.Connection;

/**
 * @author Rasmus Sander Larsen
 */
public class DBController implements IConnPool {

    /*
    -------------------------- Fields --------------------------
     */
    
    private MySQL_DB mySQL_DB;
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public DBController (MySQL_DB mySQL_DB) {

        this.mySQL_DB  = mySQL_DB;
    }
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

    @Override
    public Connection getConn() {
        return mySQL_DB.createConnection();
    }
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
