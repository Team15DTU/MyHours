package db.connectionPools;

import dao.DALException;
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
    protected static ConnPoolV1 instance;
    
    //region keepAlive()
    protected int refreshRate 	= 30000; 	// 30 seconds
	protected int validTimeout	= 2;		// 02 seconds
	protected boolean stop		= false;
	//endregion
    
    public static final int MAXCONNS = 8;
    
    //region DB Info
	protected static String url = "ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185097?";
	protected static String user = "s185097";
	protected static String password = "qsNAphOJ13ySzlpn1kh6Y";
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
	public synchronized void setRefreshRate(int millis)
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
	public synchronized void setValidTimeout(int validTimeout)
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
		// Make sure connection given back isn't null
		if ( connection == null )
			return;
		
		try
		{
			// Roll back unfinished transactions
			if ( !connection.getAutoCommit() )
				connection.rollback();
		}
		catch (SQLException e)
		{
			System.err.println("ERROR: Release connection and Rollback failure");
			throw new DALException(e.getMessage());
		}
		catch (Exception e)
		{
			System.err.println("ERROR: Release connection - " + e.getMessage());
		}
		finally
		{
			// Put connection back into freeConnList and remove from usedConnList
			usedConnList.remove(connection);
			freeConnList.add(connection);
		}
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
		Connection connection;

		// Start loop
		while (true)
		{
			// Check if there's any free connections
			if ( freeConnList.isEmpty() )
			{
				// Wait a little and try again
				try
				{
					wait(200);	// 0,2 seconds
				}
				catch ( InterruptedException e )
				{
					System.err.println("ERROR: getConn() delay error - " + e.getMessage());
					throw new DALException(e.getMessage());
				}
			}
			
			else
			{
				try
				{
					// Get the first connection
					connection = freeConnList.get(freeConnList.size() - 1);
					
					// If connection isClosed then make a new and return
					if ( connection == null || connection.isClosed() )
					{
						connection = createConnection();
						
						usedConnList.add(connection);
						freeConnList.remove(freeConnList.size() - 1);
						
						return connection;
					}
					
					// Otherwise, return connection normally
					else
					{
						usedConnList.add(connection);
						freeConnList.remove(connection);
						
						return connection;
					}
				}
				catch ( SQLException e)
				{
					System.err.println("ERROR: getConn() failure - " + e.getMessage());
					throw new DALException(e.getMessage());
				}
				catch ( Exception e )
				{
					System.err.println("ERROR: Unknown getConn() failure - " + e.getMessage());
					throw new DALException(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * This method is shutting down the connection pool. Making sure
	 * that every connection is closed correctly, and stops all
	 * related threads.
	 * @throws DALException Data Access Layer Exception
	 */
	@Override
	public void closePool() throws DALException
	{
		// Make keepAlive thread stop
		stop = true;
		
		// Close all connections in both Lists
		try
		{
			for (Connection c : freeConnList) { closeConnection(c); }
			for (Connection c : usedConnList) { closeConnection(c); }
		}
		catch ( SQLException e )
		{
			System.err.println("ERROR: Error trying to close connection pool - " + e.getMessage());
			throw new DALException( e.getMessage() );
		}
		finally
		{
			// Erase reference to instance and call GC
			instance = null;
			System.gc();
		}
	}
	
    /*------------------------------------------------------------
    | Protected Methods                                            |
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
	 * Closed the given SQL Connection the correct way. If the connection
	 * is already closed, then this won't do anything.
	 * @param c The SQL Connection to close
	 * @throws SQLException Handle this
	 */
	protected void closeConnection(Connection c) throws SQLException
	{
		// Check if connection is already closed
		if ( !c.isClosed() )
		{
			// If autocommit == true, then just close
			if ( c.getAutoCommit() ) { c.close(); }
			
			// Otherwise, make sure to make rollback first
			else { c.rollback(); c.close(); }
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
}
