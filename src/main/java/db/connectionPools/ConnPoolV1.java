package db.connectionPools;

import DAO.DALException;
import db.IConnPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Alfred Röttger Rydahl
 */

public class ConnPoolV1 implements IConnPool {
	
    /*------------------------------------------------------------
    | Fields                                                     |
    -------------------------------------------------------------*/
    private static ConnPoolV1 instance;
    public static final int MAXCONNS = 8;
	private final String url = "ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185097?";
	private final String user = "s185097";
	private final String password = "qsNAphOJ13ySzlpn1kh6Y";
    
    private List<Connection> freeConnList;
    private List<Connection> usedConnList;
    
    /*------------------------------------------------------------
    | Constructors                                               |
    -------------------------------------------------------------*/
	
	private ConnPoolV1()
	{
		//TODO: Instantiate Lists
		//TODO: Create all Connections
	}
	
    /*------------------------------------------------------------
    | Properties                                                 |
    -------------------------------------------------------------*/
    /*------------------------------------------------------------
    | Public Methods                                             |
    -------------------------------------------------------------*/
	
	/**
	 * Gives the instance of the Connection Pool.
	 * @return ConnPoolV1 object
	 */
	public static ConnPoolV1 getInstance()
	{
		if ( instance == null )
			instance = new ConnPoolV1();
		
		return instance;
	}
	
	/**
	 * Returns the Connection to the connection pool.
	 * @param connection The Connection to return
	 * @throws DALException Data Access Layer Exception
	 */
	@Override
	public void releaseConnection(Connection connection) throws DALException {
	
	}
	
	/**
	 * Gives a live Connection to the Database, which needs to be returned after
	 * use.
	 * @return Return a Connection object
	 * @throws DALException Data Access Layer Exception
	 */
	@Override
	public Connection getConn() throws DALException {
		return null;
	}
	
    /*------------------------------------------------------------
    | Private Methods                                            |
    -------------------------------------------------------------*/
	
	/**
	 * Establishes a connection with the Database.
	 * @return Connection object
	 * @throws DALException Data Access Layer Exception
	 */
	private Connection createConnection() throws DALException
	{
		try
		{
			return DriverManager.getConnection("jdbc:mysql://"+url,user, password);
		}
		catch (SQLException e)
		{
			System.err.println("ERROR: Create Connection Failure!");
			throw new DALException(e.getMessage(), e.getCause());
		}
	}
}
