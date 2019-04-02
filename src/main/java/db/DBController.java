package db;

import DAO.worker.WorkerDAO;

import java.sql.*;
import java.util.TimeZone;

/**
 * @author Rasmus Sander Larsen
 */
public class DBController {

    /*
    -------------------------- Fields --------------------------
     */
    
    private IConnPool iConnPool;
    private IWorkerDAO workerDAO;

    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public DBController () {

        iConnPool = new MySQL_DB();

        TimeZone.setDefault(TimeZone.getTimeZone(setTimeZoneFromSQLServer()));

        workerDAO = new WorkerDAO(iConnPool);

    }
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

    public Connection createConnection() {

        return iConnPool.getConn();

    }

    public int getNextAutoIncremental(String tableName) {

        int nextAIValue;

        try (Connection c = createConnection()) {

            Statement statement = c.createStatement();
            statement.executeQuery("ANALYZE TABLE " + tableName);

            PreparedStatement pStatement = c.prepareStatement(
                    "SELECT `auto_increment` FROM INFORMATION_SCHEMA.TABLES " +
                            " WHERE table_name = ?");
            pStatement.setString(1, tableName);

            ResultSet resultset = pStatement.executeQuery();

            resultset.next();

            nextAIValue = resultset.getInt(1);

        } catch (SQLException e) {
            nextAIValue = 666;
            e.printStackTrace();
        }

        return nextAIValue;
    }
    
    /*
    ---------------------- Support Methods ----------------------
     */

    private String setTimeZoneFromSQLServer ()  {
        try (Connection c = createConnection()) {

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT @@system_time_zone");
            resultSet.next();
            return resultSet.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
            return "GMT";
        }
    }


}
