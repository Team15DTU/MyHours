package dao.activity;

import db.IConnPool;
import db.TestConnPoolV1;
import org.junit.*;

public class ActivityDAOTest
{
	private static IConnPool connPool;
	
	@BeforeClass
	public static void setUp() throws Exception
	{ connPool = TestConnPoolV1.getInstance(); }
	
	@AfterClass
	public static void tearDown() throws Exception
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