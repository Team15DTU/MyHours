package db.connectionPools;

import DAO.DALException;
import db.IConnPool;

import java.sql.Connection;

/**
 * @author Alfred Röttger Rydahl
 */

public class ConnPoolV1 implements IConnPool {
	
    /*------------------------------------------------------------
    | Fields                                                     |
    -------------------------------------------------------------*/
    /*------------------------------------------------------------
    | Constructors                                               |
    -------------------------------------------------------------*/
    /*------------------------------------------------------------
    | Properties                                                 |
    -------------------------------------------------------------*/
    /*------------------------------------------------------------
    | Public Methods                                             |
    -------------------------------------------------------------*/
	
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
