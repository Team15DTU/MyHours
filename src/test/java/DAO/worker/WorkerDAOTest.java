package DAO.worker;

import DAO.DALException;
import DTOs.address.Address;
import DTOs.address.IAddress;
import DTOs.worker.IWorkerDTO;
import DTOs.worker.WorkerDTO;
import db.DBController;
import db.IConnPool;
import db.TestConnPoolV1;
import db.connectionPools.ConnPoolV1;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@FixMethodOrder(MethodSorters.JVM)		// Make sure tests run in order
public class WorkerDAOTest
{
	
	//region Test Material
	
	private static IConnPool connPool;
	
	private String firstName0 = "Bo"; String surName0 = "Børgesen";
	private String email0 = String.format("%s.%s@hotmail.com", firstName0, surName0);
	private LocalDate birthday0 = LocalDate.now();
	private IWorkerDTO worker0 = new WorkerDTO(firstName0, surName0, email0, birthday0, null, null);
	
	private String firstName1 = "Geden"; String surName1 = "Johannes";
	private String email1 = String.format("%s.%s@hotmail.com", firstName1, surName1);
	private LocalDate birthday1 = LocalDate.now();
	private IAddress address1 = new Address("Rumænien", "Babuska", "Babuski", 666, 69);
	private IWorkerDTO worker1 = new WorkerDTO(firstName1, surName1, email1, birthday1, address1, null);
	
	private IWorkerDTO[] testWorkers = {worker0, worker1};

	//endregion
	
	
	@BeforeClass
	public static void setUp() throws Exception
	{ connPool = TestConnPoolV1.getInstance(); }
	
	@AfterClass
	public static void tearDown() throws Exception
	{ connPool.closePool(); }
	
	@Test
	public void createWorker() throws DALException
	{
		IWorkerDAO workerDAO = DBController.getInstance(TestConnPoolV1.getInstance()).getiWorkerDAO();
		
		// Try to Create them
	/*	for (IWorkerDTO worker : testWorkers)
			workerDAO.createWorker(worker, "FuckingPassword");
		*/
		// Delete again
		for (IWorkerDTO worker : testWorkers)
			workerDAO.deleteWorker(worker.getEmail());
	}
	
	@Test
	public void getWorker() throws DALException
	{
		IWorkerDAO workerDAO = DBController.getInstance(TestConnPoolV1.getInstance()).getiWorkerDAO();
		
		// Get Alfred from DB
		IWorkerDTO worker = workerDAO.getWorker("a.rottger_rydahl@live.dk");
		
		// Validate data
		assertEquals(worker.getFirstName(), "Alfred");
		assertEquals(worker.getSurName(), "Rydahl");
		assertEquals(worker.getEmail(), "a.rottger_rydahl@live.dk");
		//TODO: Check WorkPlaces
		//TODO: Check Jobs
		//TODO: Check Shifts
	}
	
	@Test
	public void getWorkerList() throws DALException
	{
		IWorkerDAO workerDAO = DBController.getInstance(TestConnPoolV1.getInstance()).getiWorkerDAO();
		
		// Create two extra workers - three total
	/*	for (IWorkerDTO worker : testWorkers)
			workerDAO.createWorker(worker, "FuckingPassword");
		*/
		// Get list
		List<IWorkerDTO> workerList = workerDAO.getWorkerList();
		
		// Check currently added workers
		boolean first = false;
		boolean second = false;
		for (IWorkerDTO fromDB : workerList)
		{
			if (fromDB.getEmail().equals(email0))
				first = true;
			
			else if (fromDB.getEmail().equals(email1))
				second = true;
			
			if (first && second)
				break;
		}
		assertTrue(first); assertTrue(second);
		
		// Delete the extra workers
		for (IWorkerDTO worker : testWorkers)
			workerDAO.deleteWorker(worker.getEmail());
	}
	
	@Test
	public void updateWorker()
	{
	}
	
	@Test
	public void deleteWorker()
	{}
}