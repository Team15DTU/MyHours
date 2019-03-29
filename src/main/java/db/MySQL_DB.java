package db;

import DAO.DALException;
import com.mysql.cj.x.protobuf.MysqlxResultset;

import java.io.PipedReader;
import java.sql.*;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TimeZone;

/**
 * @author Rasmus Sander Larsen
 */
public class MySQL_DB {

    /*
    -------------------------- Fields --------------------------
     */

    private final String url = "ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185097?";
    private final String user = "s185097";
    private final String password = "qsNAphOJ13ySzlpn1kh6Y";

    /*
    ----------------------- Constructor -------------------------
     */

    public MySQL_DB () {

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

    public Connection createConnection()  {
        try {
            return DriverManager.getConnection("jdbc:mysql://"+url,user, password);
        } catch (SQLException e) {
            throw new IllegalStateException();
        }
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
