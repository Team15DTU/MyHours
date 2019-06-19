package db;

import dto.worker.IWorkerDTO;
import org.junit.*;
import org.junit.runners.MethodSorters;
import testData.TestDataController;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.JVM)		// Make sure tests run in order
public class ArrayDBControllerTest
{
	private static ArrayDBController controller;
	private static ArrayList<IWorkerDTO> testData;
	
	@BeforeClass
	public static void setUp() throws Exception
	{
		controller = new ArrayDBController();
	}
	
	@Before
	public void setUP()
	{
		testData = new ArrayList<>(2);
		testData.add(TestDataController.getTestWorkerNo1());
		testData.add(TestDataController.getTestWorkerNo2());
		controller.setWorkerList(testData);
	}
	
	@AfterClass
	public static void tearDown() throws Exception
	{
	}
	
	@Test
	public void createWorker() throws Exception
	{
		// Change the arraylist in the DBController
		controller.setWorkerList(new ArrayList<>());
		
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
	public void getIWorkerDTO()
	{
		IWorkerDTO realWorker 	= TestDataController.getTestWorkerNo1();
		IWorkerDTO worker		= controller.getIWorkerDTO(realWorker.getWorkerID());
		
		// Check data is correct
		assertEquals(realWorker.getFirstName(), worker.getFirstName());
		assertEquals(realWorker.getSurName(), worker.getSurName());
		assertEquals(realWorker.getEmail(), worker.getEmail());
		assertEquals(realWorker.getPassword(), worker.getPassword());
	}
	
	@Test
	public void getIWorkerDTO1()
	{
		IWorkerDTO realWorker 	= TestDataController.getTestWorkerNo2();
		IWorkerDTO worker		= controller.getIWorkerDTO(realWorker.getEmail());
		
		// Check data is correct
		assertEquals(realWorker.getFirstName(), worker.getFirstName());
		assertEquals(realWorker.getSurName(), worker.getSurName());
		assertEquals(realWorker.getEmail(), worker.getEmail());
		assertEquals(realWorker.getPassword(), worker.getPassword());
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
		// Get the worker to update
		IWorkerDTO xWorker = controller.getWorkerList().get(0);
		
		// Change data
		xWorker.setFirstName("Alfred");
		xWorker.setSurName("Rydahl");
		xWorker.setEmail("Alfred@Rydahl.com");
		
		// Try the update
		controller.updateWorker(xWorker);
		
		// Get the updated worker
		IWorkerDTO yWorker = controller.getIWorkerDTO(xWorker.getWorkerID());
		
		// Check the data
		assertEquals(xWorker.getWorkerID(), yWorker.getWorkerID());
		assertEquals(xWorker.getFirstName(), yWorker.getFirstName());
		assertEquals(xWorker.getSurName(), yWorker.getSurName());
		assertEquals(xWorker.getEmail(), yWorker.getEmail());
	}
	
	@Test
	public void deleteWorker()
	{
		// First check that there's two workers
		assertEquals(testData.size(), controller.getIWorkerDTOList().size());
		
		// Remove all workers
		int size = testData.size();
		for ( int i=0; i < size; i++ )
			controller.deleteWorker(controller.getWorkerList().get(0).getEmail());
		
		// Check if all is removed
		assertEquals(0, controller.getIWorkerDTOList().size());
	}
	
	@Test
	public void createEmployer()
	{
		IWorkerDTO worker1 = controller.getIWorkerDTOList().get(0);
		IWorkerDTO worker2 = controller.getIWorkerDTOList().get(1);
		
		// Test that there's no employers
		assertEquals(0, worker1.getIEmployers().size());
		assertEquals(0, worker2.getIEmployers().size());
		
		// Create employers for both workers
		controller.createEmployer(TestDataController.getEmployerNo1(worker1));
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