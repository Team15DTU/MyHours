package db;

import DTOs.worker.WorkerDTO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    
    public DBController () {
        mySQL_DB = new MySQL_DB();
    }
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */
    
    public void insertWorkerDTOInto (String tableName, WorkerDTO workerDTO) {

        try (Connection c = mySQL_DB.createConnection()) {

            PreparedStatement statement =
                    c.prepareStatement("INSERT INTO workers(firstname, surname, email, birthday, code) VALUES (?,?,?,?,?)");
            statement.setString(1, workerDTO.getFirstName());
            statement.setString(2, workerDTO.getSurName());
            statement.setString(3, workerDTO.getEmail());
            statement.setDate(4, Date.valueOf(workerDTO.getBirthday()));
            statement.setString(5, "Hejhejhej");

            statement.executeUpdate();

            System.out.println("Worker have been added to: \t DB: myhours \tTable: " + tableName);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
