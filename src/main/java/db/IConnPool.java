package db;

import dao.DALException;

import java.sql.Connection;

public interface IConnPool
{
	Connection getConn() throws DALException;

	void releaseConnection (Connection connection);
	
	void closePool() throws DALException;

	String getUser();

}
