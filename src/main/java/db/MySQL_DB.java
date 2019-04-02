package db;

import DAO.DALException;

import java.sql.*;

import java.util.TimeZone;

/**
 * @author Rasmus Sander Larsen
 */
public class MySQL_DB implements IConnPool {

    /*
    -------------------------- Fields --------------------------
     */

    private final String url = "ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185097?";
    private final String user = "s185097";
    private final String password = "qsNAphOJ13ySzlpn1kh6Y";

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

    public Connection getConn() throws DALException  {
        try {
            return DriverManager.getConnection("jdbc:mysql://"+url,user, password);
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void releaseConnection(Connection connection) throws DALException {
        try {
            if (!connection.isClosed())
                connection.close();
        }
        catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    /*
    ---------------------- Support Methods ----------------------
     */


}
