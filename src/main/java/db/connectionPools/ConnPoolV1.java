package db.connectionPools;

import DAO.DALException;
import db.IConnPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
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
	
	/**
	 * Creates the ConnPoolV1 object, and initializing everything.
	 * @throws DALException Data Access Layer Exception
	 */
	private ConnPoolV1() throws DALException
	{
		// Instantiating Lists
		freeConnList = new ArrayList<>(MAXCONNS);
		usedConnList = new ArrayList<>(MAXCONNS);
		
		// Create all Connections
		try
		{
			for (int i = 0; i < MAXCONNS; i++)
				freeConnList.add(createConnection());
		}
		catch (DALException e)
		{
			System.err.println("ERROR: Creating Connection Pool");
			throw e;
		}
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
	 * @throws DALException Data Access Layer Exception
	 */
	public static ConnPoolV1 getInstance() throws DALException
	{
		try
		{
			if (instance == null)
				instance = new ConnPoolV1();
			
			return instance;
		}
		catch (DALException e)
		{
			System.err.println("ERROR: Couldn't get ConnPoolV1 instance");
			throw e;
		}
	}
	
	/**
	 * Returns the Connection to the connection pool.
	 * @param connection The Connection to return
	 * @throws DALException Data Access Layer Exception
	 */
	@Override
	public void releaseConnection(Connection connection) throws DALException
	{
		//TODO: Implement me!
	}
	
	/**
	 * Gives a live Connection to the Database, which needs to be returned after
	 * use.
	 * @return Return a Connection object
	 * @throws DALException Data Access Layer Exception
	 */
	@Override
	public Connection getConn() throws DALException
	{
		//TODO: Implement me!
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
