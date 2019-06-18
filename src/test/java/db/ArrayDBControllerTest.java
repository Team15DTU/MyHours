package db;

import org.junit.*;
import org.junit.runners.MethodSorters;
import testData.TestDataController;

import java.awt.image.AreaAveragingScaleFilter;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.JVM)		// Make sure tests run in order
public class ArrayDBControllerTest
{
	private static ArrayDBController controller;
	
	@BeforeClass
	public static void setUp() throws Exception
	{
		controller = new ArrayDBController();
	}
	
	@AfterClass
	public static void tearDown() throws Exception
	{
	}
	
	@Test
	public void createWorker() throws Exception
	{
		// Create two workers
		controller.createWorker(TestDataController.getTestWorkerNo1());
		controller.createWorker(TestDataController.getTestWorkerNo2());
		
		// Check that they're created
		assertEquals(2, controller.getWorkerList().size());
		
		// Check first worker
		assertEquals(TestDataController.getTestWorkerNo1().getFirstName(), controller.getWorkerList().get(0).getFirstName());
		assertEquals(TestDataController.getTestWorkerNo1().getSurName(), controller.getWorkerList().get(0).getSurName());
		assertEquals(TestDataController.getTestWorkerNo1().getEmail(), controller.getWorkerList().get(0).getEmail());
		assertEquals(TestDataController.getTestWorkerNo1().getPassword(), controller.getWorkerList().get(0).getPassword());
		// Check second worker
		assertEquals(TestDataController.getTestWorkerNo2().getFirstName(), controller.getWorkerList().get(1).getFirstName());
		assertEquals(TestDataController.getTestWorkerNo2().getSurName(), controller.getWorkerList().get(1).getSurName());
		assertEquals(TestDataController.getTestWorkerNo2().getEmail(), controller.getWorkerList().get(1).getEmail());
		assertEquals(TestDataController.getTestWorkerNo2().getPassword(), controller.getWorkerList().get(1).getPassword());
	}
	
	@Test
	public void getWorkerList()
	{
	}
	
	@Test
	public void setWorkerList()
	{
	}
	
	@Test
	public void getIWorkerDTO()
	{
	}
	
	@Test
	public void getIWorkerDTO1()
	{
	}
	
	@Test
	public void getIWorkerDTOList()
	{
	}
	
	@Test
	public void getIWorkerDTOList1()
	{
	}
	
	@Test
	public void getIWorkerDTOList2()
	{
	}
	
	@Test
	public void updateWorker()
	{
	}
	
	@Test
	public void deleteWorker()
	{
	}
	
	@Test
	public void createEmployer()
	{
	}
	
	@Test
	public void getIEmployerDTO()
	{
	}
	
	@Test
	public void getIEmployerList()
	{
	}
	
	@Test
	public void getIEmployerList1()
	{
	}
	
	@Test
	public void getIEmployerList2()
	{
	}
	
	@Test
	public void updateEmployer()
	{
	}
	
	@Test
	public void deleteEmployer()
	{
	}
	
	@Test
	public void createJob()
	{
	}
	
	@Test
	public void getIJobDTO()
	{
	}
	
	@Test
	public void getIJobDTOList()
	{
	}
	
	@Test
	public void getIJobDTOList1()
	{
	}
	
	@Test
	public void getIJobDTOList2()
	{
	}
	
	@Test
	public void getIJobDTOList3()
	{
	}
	
	@Test
	public void updateJob()
	{
	}
	
	@Test
	public void deleteJob()
	{
	}
	
	@Test
	public void createActivity()
	{
	}
	
	@Test
	public void getIActivity()
	{
	}
	
	@Test
	public void getIActivityList()
	{
	}
	
	@Test
	public void getIActivityList1()
	{
	}
	
	@Test
	public void getIActivityList2()
	{
	}
	
	@Test
	public void getIActivityList3()
	{
	}
	
	@Test
	public void updateActivity()
	{
	}
	
	@Test
	public void deleteActivity()
	{
	}
	
	@Test
	public void setTimeZoneFromSQLServer()
	{
	}
	
	@Test
	public void getNextAutoIncremental()
	{
	}
	
	@Test
	public void logOut()
	{
	}
	
	@Test
	public void isSessionActive()
	{
	}
	
	@Test
	public void loginCheck()
	{
	}
}