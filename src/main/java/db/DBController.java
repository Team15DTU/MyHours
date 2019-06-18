package db;

import dao.ConnectionHelper;
import dao.DALException;
import dao.activity.ActivityConstants;
import dao.activity.ActivityDAO;
import dao.activity.IActivityDAO;
import dao.employer.EmployerConstants;
import dao.employer.EmployerDAO;
import dao.employer.IEmployerDAO;
import dao.job.IJobDAO;
import dao.job.JobConstants;
import dao.job.JobDAO;
import dao.worker.IWorkerDAO;
import dao.worker.WorkerConstants;
import dao.worker.WorkerHiberDAO;
import db.connectionPools.ConnPoolV1;
import dto.activity.ActivityDTO;
import dto.activity.IActivityDTO;
import dto.employer.EmployerDTO;
import dto.employer.IEmployerDTO;
import dto.job.IJobDTO;
import dto.job.JobDTO;
import dto.worker.IWorkerDTO;
import dto.worker.WorkerDTO;
import hibernate.HibernateProperties;
import hibernate.HibernateUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * @author Rasmus Sander Larsen
 */
@Path("/DBController")
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
			hibernateUtil 	= HibernateUtil.getInstance(new HibernateProperties().getRealDB());
			this.connPool   = ConnPoolV1.getInstance();
			connectionHelper = new ConnectionHelper(this.connPool);
			
			TimeZone.setDefault(TimeZone.getTimeZone(setTimeZoneFromSQLServer()));
			
			iWorkerDAO      = new WorkerHiberDAO(hibernateUtil);
			iEmployerDAO    = new EmployerDAO(this.connPool, this.connectionHelper);
			iJobDAO         = new JobDAO(this.connPool, this.connectionHelper);
			iActivityDAO    = new ActivityDAO(this.connPool,connectionHelper);
		}
		catch ( Exception e )
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
            hibernateUtil 	= HibernateUtil.getInstance(hibernateProperties);
    
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


	@POST
	@Path("/Logout")
	@Override
	public void logOut(@Context HttpServletRequest request){
			HttpSession session = request.getSession();
			session.invalidate();
	}

    @POST
	@Path("/isSessionActive")
    @Override
    public boolean isSessionActive(@Context HttpServletRequest request){
		boolean sessionStatus=false;
		if (request.getSession(false) != null){
			sessionStatus=true;
		}
		return sessionStatus;
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
    public boolean loginCheck(WorkerDTO user, @Context HttpServletRequest request)
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

            if (success) {
                HttpSession oldSession = request.getSession();
                if (oldSession != null) {
                    oldSession.invalidate();
                }

                HttpSession session = request.getSession(true);

                // Store users email in session
                session.setAttribute("userEmail",email);

                // Set the the time before the session expires to 10 minutes
                session.setMaxInactiveInterval(10*60);
            }

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
    public void createWorker (WorkerDTO workerDTO) throws DALException{
        Connection c = connPool.getConn();

        // The query to make
        String query =
                String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?,?,?,?,?)",
                        WorkerConstants.TABLENAME, WorkerConstants.firstname, WorkerConstants.surname, WorkerConstants.email,
                        WorkerConstants.birthday, WorkerConstants.password);

        try {

            PreparedStatement statement = c.prepareStatement(query);

            statement.setString(1, workerDTO.getFirstName());
            statement.setString(2, workerDTO.getSurName());
            statement.setString(3, workerDTO.getEmail());
            statement.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
            statement.setString(5, workerDTO.getPassword());

            statement.executeUpdate();

        }
        catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        finally {
            // Return the Connection to the Pool
            connPool.releaseConnection(c);
        }
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
	@Override
	public boolean updateWorker(IWorkerDTO workerDTO)
    {
        boolean success = false;

        try
        {
            iWorkerDAO.updateWorker(workerDTO);
            success = true;
        }
        catch ( DALException e )
        {
            System.err.println("ERROR: DBController updateWorker() - " + e.getMessage());
        }
        return success;
    }

    @DELETE
    @Path("/deleteWorker")
    @Override
    public boolean deleteWorker(String email){
        boolean success = false;

        try
        {
            iWorkerDAO.deleteWorker(email);
            success = true;
        }
        catch ( DALException e )
        {
            System.err.println("ERROR: DBController deleteWorker() - " + e.getMessage());
        }
        return success;
    }
	
	//endregion
    
    //region Employer
	@POST
	@Path("/createEmployer")
	@Consumes(MediaType.APPLICATION_JSON)
    @Override
    public void createEmployer(EmployerDTO employer) throws DALException {
        Connection c = connPool.getConn();

        String createQuery = String.format(
                "INSERT INTO %s (%s, %s, %s, %s) VALUES (?,?,?,?)",
                EmployerConstants.TABLENAME,
                EmployerConstants.workerID, // ParameterIndex 1
                EmployerConstants.employerName, // ParameterIndex 2
                EmployerConstants.color, // ParameterIndex 3
                EmployerConstants.tlf); // ParameterIndex 4

        try {

            PreparedStatement pStatement = c.prepareStatement(createQuery);

            pStatement.setInt(1, employer.getWorkerID());
            pStatement.setString(2, employer.getName());
            pStatement.setString(3, null);
            pStatement.setString(4, employer.getTelephone());

            pStatement.executeUpdate();

        } catch (SQLException e) {
            connectionHelper.catchSQLExceptionAndDoRollback(c, e, "DBController.createEmployer");
        } finally {
            connectionHelper.finallyActionsForConnection(c, "DBController.createEmployer");
        }
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
    {
    	/*
    	To indicate to frontend that an error happened, but it
    	won't crash as the list won't be null.
    	 */
        List<IEmployerDTO> list = new ArrayList<>();

        // Try to create the list
        try
        {
            list = iEmployerDAO.getiEmployerList();
        }
        catch ( DALException e )
        {
            System.err.println("ERROR: getIEmployerList() DALException - " + e.getMessage());
        }
        catch ( Exception e )
        {
            System.err.println("ERROR: Unknown Exception getIEmployerList() - " + e.getMessage());
        }

        return list;
    }
    
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
	@Override
	public boolean updateEmployer(IEmployerDTO employerDTO)
    {
        boolean success = false;

        try
        {
            iEmployerDAO.updateiEmployer(employerDTO);
            success = true;
        }
        catch ( DALException e )
        {
            System.err.println("ERROR: DBController updateEmployer() - " + e.getMessage());
        }
        return success;
    }

    @DELETE
    @Path("/deleteEmployer")
    @Override
    public boolean deleteEmployer(int employerID){
        boolean success = false;

        try
        {
            iEmployerDAO.deleteiEmployer(employerID);
            success = true;
        }
        catch ( DALException e )
        {
            System.err.println("ERROR: DBController deleteEmployer() - " + e.getMessage());
        }
        return success;
    }
	
	//endregion
    
    //region Job
    
	@POST
	@Path("/createJob")
	@Consumes(MediaType.APPLICATION_JSON)
    @Override
    public void createJob(JobDTO job) throws DALException {

        Connection c = connPool.getConn();

        String createQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?,?,?,?,?)",
                JobConstants.TABLENAME,
                JobConstants.employerID,    // ParameterIndex 1
                JobConstants.jobName,       // ParameterIndex 2
                JobConstants.hireDate,      // ParameterIndex 3
                JobConstants.finishDate,    // ParameterIndex 4
                JobConstants.salary);       // ParameterIndex 5

        try {

            PreparedStatement pStatement = c.prepareStatement(createQuery);

            pStatement.setInt(1, job.getEmployerID());
            pStatement.setString(2, job.getJobName());

            // Inserts null if no hire date.
            if (job.getHireDate() != null ) {
                pStatement.setDate(3, java.sql.Date.valueOf(job.getHireDate()));
            } else {
                pStatement.setDate(3,null);
            }
            // Inserts null if no hire date.
            if (job.getFinishDate() != null ) {
                pStatement.setDate(4, java.sql.Date.valueOf(job.getFinishDate()));
            } else {
                pStatement.setDate(4,null);
            }
            pStatement.setDouble(5, job.getStdSalary());

            pStatement.executeUpdate();


        } catch (SQLException e) {
            connectionHelper.catchSQLExceptionAndDoRollback(c,e, "DBController.createJob");
        } finally {
            connectionHelper.finallyActionsForConnection(c, "DBController.createIJob");
        }
    }
    
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
    {
    	/*
    	To indicate to frontend that an error happened, but it
    	won't crash as the list won't be null.
    	 */
        List<IJobDTO> list = new ArrayList<>();

        // Try to create the list
        try
        {
            list = iJobDAO.getIJobList();
        }
        catch ( DALException e )
        {
            System.err.println("ERROR: getIJobList() DALException - " + e.getMessage());
        }
        catch ( Exception e )
        {
            System.err.println("ERROR: Unknown Exception getIJobList() - " + e.getMessage());
        }

        return list;
    }
    
    @GET
	@Path("/getJobList/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<IJobDTO> getIJobDTOList(@PathParam("id") int employerID)
    { return null; }
    
    @GET
	@Path("/getJobListbyName/{name}")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<IJobDTO> getIJobDTOList(@PathParam("name") String name)
    { return null; }


    //Can replace getIJobDTOList() with no parameters, sine parameters is null when not assigned. (Path has been change to avoid conflicts)
    @GET
	@Path("/getJobListSalary")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<IJobDTO> getIJobDTOList(@QueryParam("minSalary") double minSalary, @QueryParam("maxSalary")double maxSalary)
    { return null; }
	
    @PUT
	@Path("/updateJob")
	@Override
	public boolean updateJob(IJobDTO jobDTO)
    {
        boolean success = false;

        try
        {
            iJobDAO.updateIJob(jobDTO);
            success = true;
        }
        catch ( DALException e )
        {
            System.err.println("ERROR: DBController updateJob() - " + e.getMessage());
        }
        return success;
    }

    @DELETE
    @Path("/deleteJob")
    @Override
    public boolean deleteJob(int jobID){
        boolean success = false;

        try
        {
            iJobDAO.deleteIJob(jobID);
            success = true;
        }
        catch ( DALException e )
        {
            System.err.println("ERROR: DBController deleteJob() - " + e.getMessage());
        }
        return success;
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
    public void createActivity(ActivityDTO activity) throws DALException {
        System.out.println(activity);
        Connection c = connPool.getConn();

        String createQuery = String.format("INSERT INTO %s (%s,%s,%s,%s,%s) VALUES (?,?,?,?,?)",
                ActivityConstants.TABLENAME,
                ActivityConstants.jobID,                // ParameterIndex 1
                ActivityConstants.startDateTime,        // ParameterIndex 2
                ActivityConstants.endDateTime,          // ParameterIndex 3
                ActivityConstants.pause,                // ParameterIndex 4
                ActivityConstants.activityValue);       // ParameterIndex 5

        try {

            PreparedStatement preparedStatement = c.prepareStatement(createQuery);
            preparedStatement.setInt(1,activity.getJobID());
            preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(activity.getStartingDateTime().toString()));
            preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(activity.getEndingDateTime().toString()));
            preparedStatement.setLong(4,0);
            preparedStatement.setDouble(5,activity.getActivityValue());

            preparedStatement.executeUpdate();


        } catch (SQLException e){
            connectionHelper.catchSQLExceptionAndDoRollback(c,e, "DBController.createiActivity");
        } finally {
            connectionHelper.finallyActionsForConnection(c,"DBController.createiActivity");
        }
    }
    
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
    {
    	/*
    	To indicate to frontend that an error happened, but it
    	won't crash as the list won't be null.
    	 */
        List<IActivityDTO> list = new ArrayList<>();

        // Try to create the list
        try
        {
            list = iActivityDAO.getiActivityList();
        } catch ( Exception e )
        {
            System.err.println("ERROR: Unknown Exception getIActivityList() - " + e.getMessage());
        }

        return list;
    }
    
    @GET
	@Path("/getActivityList/{jobID}")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<IActivityDTO> getIActivityList(@PathParam("jobID") int jobID)
    { return null; }
    
    @GET
	@Path("/getActivityListByDate/{date}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<IActivityDTO> getIActivityList(@PathParam("date") Date date)
    { return null; }

	//Can replace getIActivityList() with no parameters, sine parameters is null when not assigned. (Path has been change to avoid conflicts)
    @GET
	@Path("/getActivityListValue")
	@Produces(MediaType.APPLICATION_JSON)
    @Override
    public List<IActivityDTO> getIActivityList(@QueryParam("minVal") double minVal, @QueryParam("maxVal") double maxVal)
    { return null; }
	
	@PUT
	@Path("/updateActivity")
	@Override
	public boolean updateActivity(IActivityDTO activityDTO)
    {
        boolean success = false;

        try
        {
            iActivityDAO.updateiActivity(activityDTO);
            success = true;
        }
        catch ( Exception e )
        {
            System.err.println("ERROR: DBController updateActivity() - " + e.getMessage());
        }
        return success;
    }

    @DELETE
    @Path("/deleteActivity")
    @Override
    public void deleteActivity(ActivityDTO activityDTO) throws DALException {

        Connection c = connPool.getConn();

        String deleteQuery = String.format("DELETE FROM %s WHERE %s = ?",
                ActivityConstants.TABLENAME,
                ActivityConstants.id);

        try {

            PreparedStatement preparedStatement = c.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, activityDTO.getActivityID());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            connectionHelper.catchSQLExceptionAndDoRollback(c,e,"DBController.deleteiActivity");
        } finally {
            connectionHelper.finallyActionsForConnection(c,"DBController.deleteiActivity");
        }
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
