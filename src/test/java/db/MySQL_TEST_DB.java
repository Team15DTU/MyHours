package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Rasmus Sander Larsen
 */
public class MySQL_TEST_DB implements IConnPool {

    // DiplomPortal Database - Alfred (TEST DB)
    private String password = "iowRz3Cj5QgJ8Ok7dX7iI";
    private String user = "s160107";
    private String url = "ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s160107?";

    public Connection getConn() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://"+url, user,password);
        } catch (SQLException e){
            e.printStackTrace();
        }

        // Return the Connection
        return conn;
    }
}