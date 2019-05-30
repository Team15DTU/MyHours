package db.connectionPools;

import DAO.DALException;
import db.IConnPool;

import java.sql.Connection;
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
}
