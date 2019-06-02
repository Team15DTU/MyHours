package db.connectionPools;

import DAO.DALException;
import db.IConnPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//TODO: Make all outside callable functions threading compatible

/**
 * @author Alfred Röttger Rydahl
 */
public class ConnPoolV1 implements IConnPool {
	
    /*------------------------------------------------------------
    | Fields                                                     |
    -------------------------------------------------------------*/
    protected static ConnPoolV1 instance;
    
    //region keepAlive()
    protected int refreshRate 	= 30000; 	// 30 seconds
	protected int validTimeout	= 2;		// 02 seconds
	protected boolean stop		= false;
	//endregion
    
    public static final int MAXCONNS = 8;
    
    //region DB Info
	protected final String url = "ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185097?";
	protected final String user = "s185097";
	protected final String password = "qsNAphOJ13ySzlpn1kh6Y";
	//endregion
    
    protected List<Connection> freeConnList;
    protected List<Connection> usedConnList;
    
    /*------------------------------------------------------------
    | Constructors                                               |
    -------------------------------------------------------------*/
	/**
	 * Creates the ConnPoolV1 object, and initializing everything.
	 * @throws DALException Data Access Layer Exception
	 */
	protected ConnPoolV1() throws DALException
	{
		// Instantiating Lists
		freeConnList = new ArrayList<>(MAXCONNS);
		usedConnList = new ArrayList<>(MAXCONNS);
		
		// Create all Connections
		boolean success = true; DALException exception = null;
		try
		{
			for (int i = 0; i < MAXCONNS; i++)
				freeConnList.add(createConnection());
		}
		catch (DALException e)
		{
			System.err.println("ERROR: Creating Connection Pool");
			exception = e;
			success = false;
		}
		
		//Start thread to keep connections alive
		keepAlive();
		
		// Make sure to throw exception
		if ( !success )
			throw exception;
	}
	
    /*------------------------------------------------------------
    | Properties                                                 |
    -------------------------------------------------------------*/
	/**
	 * Gets the total amount of connections in this pool, both in-use and
	 * free connections.
	 * @return Amount of connections in pool as int
	 */
	public int getSize()
	{
		return freeConnList.size() + usedConnList.size();
	}
	
	/**
	 * Gets the total amount of available connections in the connection
	 * pool.
	 * @return amount of available connections as int
	 */
	public int getFreeConns()
	{
		return freeConnList.size();
	}
	
	/**
	 * Gets the total amount of connections in use.
	 * @return Amount of connections in use
	 */
	public int getUsedConns()
	{
		return usedConnList.size();
	}
	
	/**
	 * Gets the current refresh rate of every connection update.
	 * @return Rate in milliseconds as int
	 */
	public int getRefreshRate()
	{
		return refreshRate;
	}
	
	/**
	 * Sets the refresh rate of the connection update.
	 * @param millis The time to wait in milliseconds
	 */
	public void setRefreshRate(int millis)
	{
		this.refreshRate = millis;
	}
	
	/**
	 * Gets the time which is used to determine how long to wait for a
	 * connection to return valid.
	 * @return The time in seconds as int
	 */
	public int getValidTimeout()
	{
		return validTimeout;
	}
	
	/**
	 * Sets the time to wait for a connection to be determined alive.
	 * @param validTimeout Time in seconds
	 */
	public void setValidTimeout(int validTimeout)
	{
		this.validTimeout = validTimeout;
	}
	
	/*------------------------------------------------------------
    | Public Methods                                             |
    -------------------------------------------------------------*/
	/**
	 * Gives the instance of the Connection Pool.
	 * @return ConnPoolV1 object
	 * @throws DALException Data Access Layer Exception
	 */
	public synchronized static ConnPoolV1 getInstance() throws DALException
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
	public synchronized void releaseConnection(Connection connection) throws DALException
	{
		// Put connection back into freeConnList and remove from usedConnList
		usedConnList.remove(connection);
		freeConnList.add(connection);
	}
	
	/**
	 * Gives a live Connection to the Database, which needs to be returned after
	 * use.
	 * @return Return a Connection object
	 * @throws DALException Data Access Layer Exception
	 */
	@Override
	public synchronized Connection getConn() throws DALException
	{
		//TODO: Implement me!
		// Make sure connection is alive
		// Move from free to used

		// Get a connection
		// Check if it's alive
			// Return connection
		// Otherwise try a new one
		
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
	protected Connection createConnection() throws DALException
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
	
	/**
	 * Keeps all Connections alive in freeConnList. For every "refreshRate" milliseconds
	 * it runs through the whole List, and checks if there's any problems with any
	 * of the connections.
	 * If a problems with a connection is detected, it refreshes the connection by
	 * creating a new one, and makes the same variable point to the newly created
	 * connection.
	 * As the method is called, it starts and runs on its own thread, and the main thread
	 * continues execution. The thread is a daemon thread, that runs in the background
	 * with low priority.
	 */
	protected void keepAlive()
	{
		// Encapsulate in thread
		Thread t = new Thread(() ->
		{
			// Start forever loop
			while (!stop) {
				
				// Sleep the thread a set amount of time
				try
				{
					Thread.sleep(refreshRate);
				}
				catch (InterruptedException e)
				{
					System.err.println("ERROR: Couldn't sleep Connection refresh thread - " + e.getMessage());
				}
				
				// Loop through all free connections
				for ( Connection c : freeConnList )
				{
					// Check if GC is closing down
					if (stop)
						break;
					
					try
					{
						// Check if it's closed or has errors
						if ( c.isClosed() || !(c.getWarnings() == null) || !c.isValid(validTimeout) )
							c = createConnection();
					}
					catch (SQLException e)
					{
						System.err.println("ERROR: keepAlive error - " + e.getMessage());
					}
					catch (DALException e)
					{
						System.err.println("ERROR: DALException keepAlive error - " + e.getMessage());
					}
				}
			}
		});
		
		// Set it as daemon thread and start
		t.setDaemon(true);
		t.start();
	}
	
	/**
	 * Extends the finalize method to make sure that all connections is
	 * closed before termination.
	 * @throws Throwable Exception
	 */
	@Override
	protected void finalize() throws Throwable
	{
		//TODO: Research if there's another method that isn't deprecated
		// that the GC uses
		
		// Make keepAlive thread stop
		stop = true;
		
		// Close all connections in both Lists
		for ( Connection c : freeConnList )
		{
			if ( !c.isClosed() )
				c.close();
		}
		for ( Connection c : usedConnList )
		{
			if ( !c.isClosed() )
				c.close();
		}
		
		super.finalize();
	}
}
