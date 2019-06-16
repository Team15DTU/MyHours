package db;

import dao.ConnectionHelper;
import dao.DALException;
import dao.activity.ActivityDAO;
import dao.activity.IActivityDAO;
import dao.employer.EmployerConstants;
import dao.employer.EmployerDAO;
import dao.employer.IEmployerDAO;
import dao.job.IJobDAO;
import dao.job.JobDAO;
import dao.worker.IWorkerDAO;
import dao.worker.WorkerConstants;
import dao.worker.WorkerHiberDAO;
import db.connectionPools.ConnPoolV1;
import dto.activity.IActivityDTO;
import dto.employer.IEmployerDTO;
import dto.job.IJobDTO;
import dto.worker.IWorkerDTO;
import dto.worker.WorkerDTO;
import hibernate.HibernateProperties;
import hibernate.HibernateUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.Date;
import java.util.*;

/**
 * @author Rasmus Sander Larsen
 */
@Path("/DBControl")
public class DBController implements IDBController
{

    /*
    -------------------------- Fields --------------------------
     */

    private IConnPool connPool;
    private HibernateUtil hibernateUtil;
    private IWorkerDAO iWorkerDAO;
    private IEmployerDAO iEmployerDAO;
    private IJobDAO iJobDAO;
    private IActivityDAO iActivityDAO;
    private ConnectionHelper connectionHelper;
    
    /*
    ----------------------- Constructor -------------------------
     */
	
	/**
	 * This Constructor is used for the REST. As it can't take any
	 * parameters, and needs to be public atm.
	 */
	public DBController ()
	{
		try
		{
			this.connPool   = ConnPoolV1.getInstance();
			connectionHelper = new ConnectionHelper(this.connPool);
			hibernateUtil 	= new HibernateUtil(new HibernateProperties().getRealDB());
			hibernateUtil.setup();
			
			TimeZone.setDefault(TimeZone.getTimeZone(setTimeZoneFromSQLServer()));
			
			iWorkerDAO      = new WorkerHiberDAO(hibernateUtil);
			iEmployerDAO    = new EmployerDAO(this.connPool, this.connectionHelper);
			iJobDAO         = new JobDAO(this.connPool, this.connectionHelper);
			iActivityDAO    = new ActivityDAO(this.connPool,connectionHelper);
		}
		catch ( DALException e )
		{
			System.err.println("ERROR: DBController constructor Failure - " + e.getMessage());
		}
	}
	
	/**
	 * Constructor to use for tests, as you can specify which
	 * connection pool to use.
	 * @param connPool A Connection Pool that implements the interface IConnPool
	 */
    public DBController (IConnPool connPool, Properties hibernateProperties)
    {
        try
        {
            this.connPool   = connPool;
            connectionHelper = new ConnectionHelper(this.connPool);
            hibernateUtil 	= new HibernateUtil(hibernateProperties);
            hibernateUtil.setup();
    
            TimeZone.setDefault(TimeZone.getTimeZone(setTimeZoneFromSQLServer()));
    
            iWorkerDAO      = new WorkerHiberDAO(hibernateUtil);
            iEmployerDAO    = new EmployerDAO(this.connPool,connectionHelper);
            iJobDAO         = new JobDAO(this.connPool, this.connectionHelper);
            iActivityDAO    = new ActivityDAO(this.connPool, connectionHelper);
        }
        catch ( Exception e )
        {
            System.err.println("ERROR: DBController constructor Failure - " + e.getMessage());
        }

    }
    
    /*
    ------------------------ Properties -------------------------
     */

    //region Properties
    
    public IWorkerDAO getiWorkerDAO() {
        return iWorkerDAO;
    }

    public void setiWorkerDAO(IWorkerDAO iWorkerDAO) {
        this.iWorkerDAO = iWorkerDAO;
    }

    public IEmployerDAO getiEmployerDAO() {
        return iEmployerDAO;
    }

    public void setiEmployerDAO(IEmployerDAO iEmployerDAO) {
        this.iEmployerDAO = iEmployerDAO;
    }

    public IJobDAO getiJobDAO() {
        return iJobDAO;
    }

    public void setiJobDAO(IJobDAO iJobDAO) {
        this.iJobDAO = iJobDAO;
    }

    public IActivityDAO getiActivityDAO() {
        return iActivityDAO;
    }

    public void setiActivityDAO(IActivityDAO iActivityDAO) {
        this.iActivityDAO = iActivityDAO;
    }

    public HibernateUtil getHibernateUtil() {
        return hibernateUtil;
    }

    public void setHibernateUtil(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }

    //endregion
    
    /*
    ---------------------- Public Methods -----------------------
     */
    
    //region Utility
    
    /**
     * This method changes the Connection pool to the given
     * Connection Pool.
     * @param connPool Connection Pool that implements IConnPool interface
     */
    public void changeConnPool(IConnPool connPool)
    {
        this.connPool = connPool;
    }
	
    //TODO: Write documentation!
	/**
	 *
	 * @param tableName
	 * @return
	 */
	@Override
    public int getNextAutoIncremental(String tableName)
    {
		Connection c = null;
		try {
			c = connPool.getConn();

            Statement statement = c.createStatement();
            ResultSet analyzeResultSet = statement.executeQuery("ANALYZE TABLE " + tableName);

            // Shit works
			//TODO: Fix hardcoded Database
            PreparedStatement pStatement = c.prepareStatement(
                    "SELECT * FROM information_schema.TABLES where TABLE_SCHEMA = ?" +
							" AND TABLE_NAME = ?" )
					;

            pStatement.setString(1, (String) hibernateUtil.getProperties().get("hibernate.connection.username"));
            pStatement.setString(2, EmployerConstants.TABLENAME);

            ResultSet resultset = pStatement.executeQuery();

            resultset.next();

            return resultset.getInt("AUTO_INCREMENT");


        }
		catch (Exception e)
		{
			System.err.println("ERROR: getNextAutoIncremental() - " + e.getMessage());
			return -1;
        }
		finally
		{
			if ( c != null )
            	connPool.releaseConnection(c);
        }
    }
	
	/**
	 * Gets the time zone of the server.
	 * @return Time zone as a String e.g. "UTC"
	 */
	@Override
    public String setTimeZoneFromSQLServer ()
    {
		Connection c = null;
        try {
			c = connPool.getConn();
			Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT @@system_time_zone");
            resultSet.next();
            return resultSet.getString(1);
            
        }
        catch (Exception e)
		{
			System.err.println("ERROR: setTimeZoneFromSQLServer() - " + e.getMessage());
			return "UTC";
        }
        finally
		{
			if ( c != null )
            	connPool.releaseConnection(c);
        }
    }
	
	/**
	 * This method checks if there's a correlation between the
	 * provided email and password. All exceptions is handled by
     * the method.
	 * @return True if there's a correlation
	 */
	@POST
	@Path("/loginCheck")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
    public boolean loginCheck(WorkerDTO user)
    {
    	String email 	= user.getEmail();
    	String password = user.getPassword();
    	
    	// Boolean to return
		boolean success = false;
    	
        // Query to be used
        String query  = String.format("SELECT %s, %s FROM %s WHERE %s = ? AND %s = ?",
				WorkerConstants.email, WorkerConstants.password, WorkerConstants.TABLENAME,
				WorkerConstants.email, WorkerConstants.password);
        
		Connection conn = null; PreparedStatement stmt;
        try
        {
            // Get connection from pool
            conn = connPool.getConn();
            
            // Create preparedStatement
            stmt = conn.prepareStatement(query);
            stmt.setString(1, email); stmt.setString(2, password);
            
            // Execute
            ResultSet rs = stmt.executeQuery();
            
            // Check if there was a match
            if ( rs.next() )
                success = true;
	
			// Close statement
			stmt.close();
        }
        catch ( DALException e )
        {
			System.err.println("ERROR: DALException thrown in loginCheck() - " + e.getMessage());
        }
        catch ( SQLException e )
        {
			System.err.println("ERROR: SQLException thrown in loginCheck() - " + e.getMessage());
        }
        catch ( Exception e )
		{
			System.err.println("ERROR: Unknown error in loginCheck() - " + e.getMessage());
		}
        finally
        {
        	if ( conn != null )
        		connPool.releaseConnection(conn);
        }
        
        return success;
    }
    
    //endregion

    //region Worker
	
	/**
	 * This method takes an object that implements the IWorkerDTO
	 * interface, and saves it in the database.
	 * @param workerDTO Object that implements the IWorkerDTO interface
	 */
	@POST
	@Path("/createWorker")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
    public boolean createWorker (IWorkerDTO workerDTO)
    {
    	boolean success = false;
    	try
		{
			iWorkerDAO.createWorker(workerDTO);
			success = true;
		}
    	catch ( DALException e )
		{
			System.err.println("ERROR: createWorker() - " + e.getMessage());
		}
    	catch ( Exception e )
		{
			System.err.println("ERROR: Unknown error createWorker() - " + e.getMessage());
		}
    	
    	return success;
    }
    
    /**
     * This methods returns a FULL IWorkerDTO Object.
     * Including:
     * 1) A list of the workers Workplaces.
     * 2) Each of those Workplaces contains a list of its Jobs
     * 3) Each of those Jobs contains a list of its Shifts
     * @param email We find the Worker, from its email as it is unique
     * @return A IWorkerDTO
     */
    @GET
	@Path("/getWorkerEmail/{email}")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public IWorkerDTO getIWorkerDTO (@PathParam("email") String email)
    {
    	IWorkerDTO worker = new WorkerDTO("Failure", "Failure", "Failure@failure.com");
    	try
		{
			// Get the worker from DB, and make object
			worker = createFullIWorkerDTO(email);
		}
    	catch ( DALException e )
		{
			System.err.println("ERROR: DALException getIWorkerDTO() - " + e.getMessage());
		}
    	catch ( Exception e )
		{
			System.err.println("ERROR: Unknown exception getIWorkerDTO() - " + e.getMessage());
		}
        
        return worker;
    }
	
	/**
	 * Method finds a Worker from an ID, and returns
	 * a full IWorkerDTO object.
	 * @param id The unique ID of the Worker
	 * @return Object that implements IWorkerDTO interface
	 */
	@GET
	@Path("/getWorker/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public IWorkerDTO getIWorkerDTO (@PathParam("id") int id)
    {
    	return null;
    }
	
	/**
	 * Method get a full list of Workers in the
	 * database.
	 * @return List<IWorkerDTO>
	 */
	@GET
	@Path("/getWorkersList")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<IWorkerDTO> getIWorkerDTOList ()
    {
    	/*
    	To indicate to frontend that an error happened, but it
    	won't crash as the list won't be null.
    	 */
    	List<IWorkerDTO> list = new ArrayList<>();
    	list.add(new WorkerDTO("Failure", "Failure", "Failure"));
    	
    	// Try to create the list
    	try
		{
			list = iWorkerDAO.getWorkerList();
		}
    	catch ( DALException e )
		{
			System.err.println("ERROR: getIWorkerDTOList() DALException - " + e.getMessage());
		}
    	catch ( Exception e )
		{
			System.err.println("ERROR: Unknown Exception getIWorkerDTOList() - " + e.getMessage());
		}
    	
    	return list;
    }
    
    @GET
	@Path("/getWorkersListRange")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<IWorkerDTO> getIWorkerDTOList (@QueryParam("minID") int minID, @QueryParam("maxID") int maxID)
    {
    	/*
    	Make sure to check if parameters minID and maxID is given.
    	 */
    	return null;
    }
    
    @GET
	@Path("/getWorkersList/{name}")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<IWorkerDTO> getIWorkerDTOList (@PathParam("name") String name)
    { return null; }
	
    @PUT
	@Path("/updateWorker")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public boolean updateWorker(IWorkerDTO workerDTO)
	{ return false; }
	
	//endregion
    
    //region Employer
	@POST
	@Path("/createEmployer")
	@Consumes(MediaType.APPLICATION_JSON)
    @Override
    public boolean createEmployer(IEmployerDTO employer)
    {
    	boolean success = false;
    	
    	try
		{
			iEmployerDAO.createiEmployer(employer);
			success = true;
		}
    	catch ( DALException e )
		{
			System.err.println("ERROR: DBController createEmployer() - " + e.getMessage());
		}
    	return success;
	}
    
    @GET
	@Path("/getEmployer/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public IEmployerDTO getIEmployerDTO(@PathParam("id") int id)
    { return null; }
    
    @GET
	@Path("/getEmployerList")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<IEmployerDTO> getIEmployerList()
    { return null; }
    
    @GET
	@Path("/getEmployerListRange")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<IEmployerDTO> getIEmployerList(@QueryParam("minID") int minID, @QueryParam("maxID") int maxID)
    {
    	/*
    	Make sure to check if parameters minID and maxID is given.
    	 */
    	return null;
    }
    
    @GET
	@Path("/getEmployerList/{name}")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<IEmployerDTO> getIEmployerList(@PathParam("name") String name)
    { return null; }
	
    @PUT
	@Path("/updateEmployer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public boolean updateEmployer(IEmployerDTO employerDTO)
	{
		return false;
	}
	
	//endregion
    
    //region Job
    
	@POST
	@Path("/createJob")
	@Consumes(MediaType.APPLICATION_JSON)
    @Override
    public boolean createJob(IJobDTO job)
    { return false; }
    
    @GET
	@Path("/getJob/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public IJobDTO getIJobDTO(@PathParam("id") int id)
    { return null; }
    
    @GET
	@Path("/getJobList")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<IJobDTO> getIJobDTOList()
    { return null; }
    
    @GET
	@Path("/getJobList/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<IJobDTO> getIJobDTOList(@PathParam("id") int employerID)
    { return null; }
    
    @GET
	@Path("/getJobList/{name}")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<IJobDTO> getIJobDTOList(@PathParam("name") String name)
    { return null; }
    
    @GET
	@Path("/getJobList")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<IJobDTO> getIJobDTOList(@QueryParam("minSalary") double minSalary, @QueryParam("maxSalary")double maxSalary)
    { return null; }
	
    @PUT
	@Path("/updateJob")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public boolean updateJob(IJobDTO jobDTO)
	{
		return false;
	}
	
	//endregion
    
    //region Activity
	
	/**
	 * Takes an object that implements IActivityDTO interface, and
	 * saving the activity in the database.
	 * @param activity Object implementing IActivityDTO
	 */
	@POST
	@Path("/createActivity")
	@Consumes(MediaType.APPLICATION_JSON)
    @Override
    public boolean createActivity(IActivityDTO activity)
    { return false; }
    
    @GET
	@Path("/getActivity/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public IActivityDTO getIActivity(@PathParam("id") int id)
    { return null; }
    
    @GET
	@Path("/getActivityList")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<IActivityDTO> getIActivityList()
    { return null; }
    
    @GET
	@Path("/getActivityList/{jobID}")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<IActivityDTO> getIActivityList(@PathParam("jobID") int jobID)
    { return null; }
    
    @GET
	@Path("/getActivityList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<IActivityDTO> getIActivityList(Date date)
    { return null; }
    
    @GET
	@Path("/getActivityList")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<IActivityDTO> getIActivityList(@QueryParam("minVal") double minVal, @QueryParam("maxVal") double maxVal)
    { return null; }
	
	@PUT
	@Path("/updateActivity")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public boolean updateActivity(IActivityDTO activityDTO)
	{
		return false;
	}
	
	//endregion
    
    /*
    ---------------------- Support Methods ----------------------
     */

    /**
     * This methods returns a FULL IWorkerDTO Object.
     * Including:
     * 1) A list of the workers Workplaces.
     * 2) Each of those Workplaces contains a list of its Jobs
     * 3) Each of those Jobs contains a list of its Shifts
     * @param email We find the Worker, from its email as it is unique
     * @return A IWorkerDTO
     * @throws DALException Will throw a DALException.
     */
    private IWorkerDTO createFullIWorkerDTO (String email) throws DALException
    {
        // Gets the IWorkerDTO
        IWorkerDTO workerDTOToReturn = iWorkerDAO.getWorker(email);
        
        // Sets WorkerDTOs List<IEmployersDTO> employers via EmployerDAO.
        workerDTOToReturn.setIEmployers(iEmployerDAO.getiEmployerList(workerDTOToReturn.getWorkerID()));
        
        // Sets EmployerDTOs List<IJobDTO> jobList via JobDAO, for each EmployerDTO in Workers List<EmployerDTO>
        for (IEmployerDTO employerDTO : workerDTOToReturn.getIEmployers()) {
            List<IJobDTO> iJobDToList = iJobDAO.getIJobList(employerDTO.getEmployerID());
            employerDTO.setIJobList(iJobDToList);
        }
        // Sets JobDTOs List<IActivityDTO> shiftList via ActivityDAO, for each IJobDTO in each IEmployerDTO in Workers List<EmployerDTO>
        for (IEmployerDTO employerDTO : workerDTOToReturn.getIEmployers()) {
            for (IJobDTO iJobDTO : employerDTO.getIJobList()) {
                List<IActivityDTO> iActivityDTOList = iActivityDAO.getiActivityList(iJobDTO.getJobID());
                iJobDTO.setiActivityDTOList(iActivityDTOList);
            }
        }

        return workerDTOToReturn;
    }

}
