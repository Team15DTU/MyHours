package DAO.shift;

import db.IConnPool;
import db.TestConnPoolV1;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShiftDAOTest
{
	private IConnPool connPool;
	
	@Before
	public void setUp() throws Exception
	{ connPool = TestConnPoolV1.getInstance(); }
	
	@After
	public void tearDown() throws Exception
	{ connPool.closePool(); }
	
	@Test
	public void getIShift()
	{
	}
	
	@Test
	public void getIShiftList()
	{
	}
	
	@Test
	public void getIShiftList1()
	{
	}
	
	@Test
	public void getIShiftList2()
	{
	}
	
	@Test
	public void createIShift()
	{
	}
	
	@Test
	public void updateIShift()
	{
	}
	
	@Test
	public void deleteIShift()
	{}
}