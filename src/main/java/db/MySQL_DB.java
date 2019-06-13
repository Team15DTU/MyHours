package db;

import dao.DALException;

import java.sql.*;

/**
 * @author Rasmus Sander Larsen
 */
@Deprecated
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

    @Override
    public String getUser() {
        return user;
    }


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

    @Override
    public Connection getConn() throws DALException
    {
        try {
            return DriverManager.getConnection("jdbc:mysql://"+url,user, password);
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public void releaseConnection(Connection connection) throws DALException
    {
        try {
            if (!connection.isClosed())
                connection.close();
        }
        catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }
    
    @Override
    public void closePool() throws DALException
    {
        // Just to satisfy IConnPool
    }
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
