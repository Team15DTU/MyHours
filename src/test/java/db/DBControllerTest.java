package db;

import dao.ConnectionHelper;
import dao.activity.ActivityDAO;
import dao.activity.IActivityDAO;
import dao.employer.EmployerDAO;
import dao.employer.IEmployerDAO;
import dao.job.IJobDAO;
import dao.job.JobDAO;
import dao.worker.IWorkerDAO;
import dao.worker.WorkerDAO;
import dto.activity.IActivityDTO;
import dto.employer.IEmployerDTO;
import dto.job.IJobDTO;
import dto.worker.IWorkerDTO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import testData.TestDataController;

import java.sql.Connection;

public class DBControllerTest
{
	
	private IConnPool connPool;
	
	@BeforeClass
	public void setUp() throws Exception
	{
		connPool 		 = TestConnPoolV1.getInstance();
		ConnectionHelper help 	 = new ConnectionHelper(connPool);
		IWorkerDAO workerDAO 	 = new WorkerDAO(connPool);
		IEmployerDAO employerDAO = new EmployerDAO(connPool, help);
		IJobDAO jobDAO 			 = new JobDAO(connPool, help);
		IActivityDAO activityDAO = new ActivityDAO(connPool, help);
		
		IWorkerDTO worker1 = TestDataController.getTestWorkerNo1();
		IWorkerDTO worker2 = TestDataController.getTestWorkerNo2();
		IEmployerDTO employer1 = TestDataController.getEmployerNo1(worker1);
		IEmployerDTO employer2 = TestDataController.getEmployerNo2(worker2);
		IJobDTO job1 = TestDataController.getJobNo1(employer1);
		IJobDTO job2 = TestDataController.getJobNo2(employer2);
		IActivityDTO activity1 = TestDataController.getActivityNo1(job1);
		IActivityDTO activity2 = TestDataController.getActivityNo2(job2);
		
		workerDAO.createWorker(worker1);
		workerDAO.createWorker(worker2);
		
		employerDAO.createiEmployer(employer1);
		employerDAO.createiEmployer(employer2);
		
		jobDAO.createIJob(job1);
		jobDAO.createIJob(job2);
		
		activityDAO.createiActivity(activity1);
		activityDAO.createiActivity(activity2);
	}
	
	@AfterClass
	public void tearDown() throws Exception
	{
		Connection conn = connPool.getConn();
		
		// Delete everything in all tables
		
	}
	
	@Test
	public void getiWorkerDAO()
	{
	}
	
	@Test
	public void setiWorkerDAO()
	{
	}
	
	@Test
	public void getiEmployerDAO()
	{
	}
	
	@Test
	public void setiEmployerDAO()
	{
	}
	
	@Test
	public void getiJobDAO()
	{
	}
	
	@Test
	public void setiJobDAO()
	{
	}
	
	@Test
	public void getiActivityDAO()
	{
	}
	
	@Test
	public void setiActivityDAO()
	{
	}
	
	@Test
	public void getInstance()
	{
	}
	
	@Test
	public void getNextAutoIncremental()
	{
		//TODO: Correct this test
		/*
		System.out.println( dbController.getNextAutoIncremental("Workers"));
		IWorkerDTO testWorker = new WorkerDTO("FirstName","Surname","Testtest@testtest.dk");
		testWorker.setBirthday(LocalDate.now());
		testWorker.setPassword("Password");
		dbController.createWorker(testWorker);
		System.out.println(dbController.getNextAutoIncremental("Workers"));
		 */
	}
	
	@Test
	public void setTimeZoneFromSQLServer()
	{
	}
	
	@Test
	public void loginCheck()
	{
	}
	
	@Test
	public void createWorker()
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
}