package db;

import DAO.DALException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class MySQL_TEST_DB implements IConnPool
{
    // DiplomPortal Database - Alfred (TEST DB)
    private String password = "iowRz3Cj5QgJ8Ok7dX7iI";
    private String user = "s160107";
    private String url = "ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s160107?";

    private List<Connection> connectionList;

    public MySQL_TEST_DB () throws DALException{

        connectionList = new ArrayList<>();
        connectionList.add(createConn());

    }

    private Connection createConn() throws DALException
    {
    try {
        return DriverManager.getConnection("jdbc:mysql://"+url,user, password);
    } catch (SQLException e) {
        throw new DALException(e.getMessage());
    }
}

    public Connection getConn() throws DALException
    {
        return connectionList.get(0);
    }

    @Override
    public void releaseConnection(Connection connection) throws DALException
    {}
    
    @Override
    public void closePool() throws DALException
    {}
}