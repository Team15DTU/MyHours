package db;

import DTOs.worker.WorkerDTO;

import java.sql.Connection;

/**
 * @author Rasmus Sander Larsen
 */
public class DBController {

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

    public Connection createConnection () {
        return mySQL_DB.createConnection();
    }
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
