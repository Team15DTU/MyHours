package db;

import DAO.DALException;
import DAO.worker.IWorkerDAO;
import DAO.worker.WorkerDAO;
import DTOs.worker.WorkerDTO;

import java.sql.*;
import java.util.TimeZone;

/**
 * @author Rasmus Sander Larsen
 */
public class DBController {

    /*
    -------------------------- Fields --------------------------
     */
    
    private IConnPool connPool;
    private IWorkerDAO workerDAO;

    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public DBController () throws DALException {

        connPool = new MySQL_DB();

        TimeZone.setDefault(TimeZone.getTimeZone(setTimeZoneFromSQLServer()));

        workerDAO = new WorkerDAO(connPool);

    }
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

    public int getNextAutoIncremental(String tableName) throws DALException {

        try (Connection c = connPool.getConn()) {

            Statement statement = c.createStatement();
            statement.executeQuery("ANALYZE TABLE " + tableName);

            PreparedStatement pStatement = c.prepareStatement(
                    "SELECT `auto_increment` FROM INFORMATION_SCHEMA.TABLES " +
                            " WHERE table_name = ?");
            pStatement.setString(1, tableName);

            ResultSet resultset = pStatement.executeQuery();

            resultset.next();

            return resultset.getInt(1);

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }
    
    /*
    ---------------------- Support Methods ----------------------
     */

    private String setTimeZoneFromSQLServer ()  throws DALException{
        Connection c = connPool.getConn();
        try {
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT @@system_time_zone");
            resultSet.next();
            return resultSet.getString(1);

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            connPool.releaseConnection(c);
        }
    }

    public void createWorker (WorkerDTO workerDTO, String password) throws DALException {

        workerDAO.createWorker(workerDTO,password);

    }

}
