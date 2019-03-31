package db;

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
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public DBController (IConnPool iConnPool) {

        this.iConnPool = iConnPool;

        TimeZone.setDefault(TimeZone.getTimeZone(setTimeZoneFromSQLServer()));

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

    public int getNextAutoIncreamental (String tableName) {

        int nextAIValue;

        try (Connection c = createConnection()) {

            PreparedStatement pStatement = c.prepareStatement(
                    "SELECT `auto_increment` FROM INFORMATION_SCHEMA.TABLES\n" +
                            "WHERE table_name = ?");
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
