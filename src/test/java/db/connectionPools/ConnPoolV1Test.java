package db.connectionPools;

import DAO.DALException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class ConnPoolV1Test {
	
	private ConnPoolV1 connPool;
	private int connSize;
	
	@Before
	public void setUp() throws Exception
	{
		connPool = ConnPoolV1.getInstance();
		connSize = connPool.getSize();
	}
	
	@After
	public void tearDown() throws Exception
	{
		try
		{
			connPool.finalize();
		}
		catch (Throwable e)
		{
			System.err.println("ERROR: Couldn't call finalize() in tearDown() - " + e.getMessage());
		}
	}
	
	@Test
	public void getSize()
	{
		assertEquals(connSize, connPool.getSize());
	}
	
	@Test
	public void getFreeConns()
	{
		assertEquals(connSize, connPool.getFreeConns());
		
		// Take one connection
		Connection c = null;
		try
		{
			c = connPool.getConn();
		}
		catch (DALException e)
		{
			System.err.println("ERROR: Couldn't get a connection from the pool - " + e.getMessage());
		}
		
		assertEquals(connSize-1, connPool.getFreeConns());
		
		// Put back connection
		try
		{
			connPool.releaseConnection(c);
		}
		catch (DALException e)
		{
			System.err.println("ERROR: Couldn't release connection back to the pool - " + e.getMessage());
		}
		
		assertEquals(connSize, connPool.getFreeConns());
	}
	
	@Test
	public void getUsedConns() {
	}
	
	@Test
	public void getRefreshRate() {
	}
	
	@Test
	public void setRefreshRate() {
	}
	
	@Test
	public void getValidTimeout() {
	}
	
	@Test
	public void setValidTimeout() {
	}
	
	@Test
	public void releaseConnection() {
	}
	
	@Test
	public void getConn() {
	}
}