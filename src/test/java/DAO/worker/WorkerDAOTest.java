package DAO.worker;

import DAO.Conn;
import DAO.DALException;
import DTOs.address.Address;
import DTOs.address.IAddress;
import DTOs.workPlace.IWorkPlaceDTO;
import DTOs.worker.IWorkerDTO;
import DTOs.worker.WorkerDTO;
import db.DBController;
import db.IConnPool;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

import java.time.LocalDate;


@FixMethodOrder(MethodSorters.JVM)		// Make sure tests run in order
public class WorkerDAOTest {
	
	//region Test Material
	
	IConnPool connPool = new Conn();
	
	String firstName0 = "Bo"; String surName0 = "Børgesen";
	String email0 = String.format("%s.%s@hotmail.com", firstName0, surName0);
	LocalDate birthday0 = LocalDate.now();
	IWorkerDTO worker0 = new WorkerDTO(firstName0, surName0, email0, birthday0, null, null);
	
	String firstName1 = "Geden"; String surName1 = "Johannes";
	String email1 = String.format("%s.%s@hotmail.com", firstName1, surName1);
	LocalDate birthday1 = LocalDate.now();
	IAddress address1 = new Address("Rumænien", "Babuska", "Babuski", 666, 69);
	IWorkerDTO worker1 = new WorkerDTO(firstName1, surName1, email1, birthday1, address1, null);
	
	IWorkerDTO[] testWorkers = {worker0, worker1};

	//endregion
	
	@Test
	public void createWorker() throws DALException
	{
		IWorkerDAO workerDAO = new DBController(connPool).getiWorkerDAO();
		
		// Try to Create them
		for (IWorkerDTO worker : testWorkers)
			workerDAO.createWorker(worker, "FuckingPassword");
		
		// Delete again
		for (IWorkerDTO worker : testWorkers)
			workerDAO.deleteWorker(worker.getEmail());
	}
	
	@Test
	public void getWorker() throws DALException
	{
		IWorkerDAO workerDAO = new DBController(connPool).getiWorkerDAO();
		
		// Try to Create them
		for (IWorkerDTO worker : testWorkers)
			workerDAO.createWorker(worker, "FuckingPassword");
		
		// Validate DB data
		for (IWorkerDTO worker : testWorkers)
		{
			IWorkerDTO fromDB = workerDAO.getWorker(worker.getEmail());
			
			assertEquals(fromDB.getFirstName(), worker.getFirstName());
			assertEquals(fromDB.getSurName(), worker.getSurName());
			assertEquals(fromDB.getEmail(), worker.getEmail());
			assertEquals(fromDB.getBirthday(), worker.getBirthday());
			assertEquals(fromDB.getHomeAddress(), worker.getHomeAddress());
			for (IWorkPlaceDTO workPlace : worker.getWorkPlaces())
				assertTrue(fromDB.getWorkPlaces().contains(workPlace));
		}
		
		// Delete again
		for (IWorkerDTO worker : testWorkers)
			workerDAO.deleteWorker(worker.getEmail());
	}
	
	@Test
	public void getWorkerList()
	{
	}
	
	@Test
	public void updateWorker()
	{
	}
	
	@Test
	public void deleteWorker()
	{}
}