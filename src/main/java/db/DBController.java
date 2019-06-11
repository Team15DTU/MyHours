package db;

import DAO.DALException;
import DAO.job.IJobDAO;
import DAO.job.JobDAO;
import DAO.activity.IActivityDAO;
import DAO.activity.ActivityDAO;
import DAO.employer.IEmployerDAO;
import DAO.employer.EmployerDAO;
import DAO.worker.IWorkerDAO;
import DAO.worker.WorkerDAO;
import DTOs.job.IJobDTO;
import DTOs.activity.IActivityDTO;
import DTOs.workPlace.IEmployerDTO;
import DTOs.worker.IWorkerDTO;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author Rasmus Sander Larsen
 */
public class DBController implements IDBController {

    /*
    -------------------------- Fields --------------------------
     */
    
    private static DBController instance;
    
    private IConnPool connPool;
    private IWorkerDAO iWorkerDAO;
    private IEmployerDAO iEmployerDAO;
    private IJobDAO iJobDAO;
    private IActivityDAO iActivityDAO;
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    private DBController (IConnPool connPool) throws DALException
    {

        this.connPool = connPool;

        TimeZone.setDefault(TimeZone.getTimeZone(setTimeZoneFromSQLServer()));

        iWorkerDAO      = new WorkerDAO(this.connPool);
        iEmployerDAO    = new EmployerDAO(this.connPool);
        iJobDAO         = new JobDAO(this.connPool);
        iActivityDAO    = new ActivityDAO(this.connPool);

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
    
    //endregion
    
    /*
    ---------------------- Public Methods -----------------------
     */
    
    /**
     * Gives the instance of the DBController. If the DBController doesn't
     * exist yet, it will create a new, with the given Connection Pool.
     * @param connPool The Connection Pool to use
     * @return DBController object
     * @throws DALException Data Access Layer Exception
     */
	public static DBController getInstance(IConnPool connPool) throws DALException
	{
		if ( instance == null )
		{
			instance = new DBController(connPool);
		}
		
		return instance;
	}
    
    //region Utility
    
    @Override
    public int getNextAutoIncremental(String tableName) throws DALException
    {
        Connection c = connPool.getConn();

        try {

            Statement statement = c.createStatement();
            statement.executeQuery("ANALYZE TABLE " + tableName);

            PreparedStatement pStatement = c.prepareStatement(
                    "SELECT `auto_increment` FROM INFORMATION_SCHEMA.TABLES " +
                            " WHERE table_name = ?");
            pStatement.setString(1, tableName);

            ResultSet resultset = pStatement.executeQuery();

            resultset.next();

            return resultset.getInt(1);

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            connPool.releaseConnection(c);
        }
    }
    
    @Override
    public String setTimeZoneFromSQLServer ()  throws DALException
    {
        Connection c = connPool.getConn();
        try {
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT @@system_time_zone");
            resultSet.next();
            return resultSet.getString(1);
            
        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            connPool.releaseConnection(c);
        }
    }
    
    @Override
    public boolean loginCheck(String email, String password) throws DALException
    {
        //TODO: Implement this
        return false;
    }
    
    //endregion

    //region Worker
    
    public void createWorker (IWorkerDTO workerDTO) throws DALException
    { iWorkerDAO.createWorker(workerDTO); }
    
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
    @Override
    public IWorkerDTO getIWorkerDTO (String email) throws DALException
    {
        // Get the worker from DB, and make object
        IWorkerDTO worker = createFullIWorkerDTO(email);
        
        if ( worker == null )
            System.err.println("ERROR: Couldn't create WorkerDTO object");
        
        return worker;
    }
    
    @Override
    public IWorkerDTO getIWorkerDTO (int id) throws DALException
    { return null; }
    
    @Override
    public List<IWorkerDTO> getIWorkerDTOList () throws DALException
    { return null; }
    
    @Override
    public List<IWorkerDTO> getIWorkerDTOList (int minID, int maxID) throws DALException
    { return null; }
    
    @Override
    public List<IWorkerDTO> getIWorkerDTOList (String name) throws DALException
    { return null; }
    
    //endregion
    
    //region Employer
    @Override
    public void createEmployer(IEmployerDTO employer) throws DALException
    { }
    
    @Override
    public IEmployerDTO getIEmployerDTO(int id) throws DALException
    { return null; }
    
    @Override
    public List<IEmployerDTO> getIEmployerList() throws DALException
    { return null; }
    
    @Override
    public List<IEmployerDTO> getIEmployerList(int minID, int maxID) throws DALException
    { return null; }
    
    @Override
    public List<IEmployerDTO> getIEmployerList(String name) throws DALException
    { return null; }
    
    //endregion
    
    //region Job
    
    @Override
    public void createJob(IJobDTO job) throws DALException
    { }
    
    @Override
    public IJobDTO getIJobDTO(int id) throws DALException
    { return null; }
    
    @Override
    public List<IJobDTO> getIJobDTOList() throws DALException
    { return null; }
    
    @Override
    public List<IJobDTO> getIJobDTOList(int employerID) throws DALException
    { return null; }
    
    @Override
    public List<IJobDTO> getIJobDTOList(String name) throws DALException
    { return null; }
    
    @Override
    public List<IJobDTO> getIJobDTOList(double minSalary, double maxSalary) throws DALException
    { return null; }
    
    //endregion
    
    //region Activity
    
    @Override
    public void createActivity(IActivityDTO activity) throws DALException
    { }
    
    @Override
    public IActivityDTO getIActivity(int id) throws DALException
    { return null; }
    
    @Override
    public List<IActivityDTO> getIActivityList() throws DALException
    { return null; }
    
    @Override
    public List<IActivityDTO> getIActivityList(int jobID) throws DALException
    { return null; }
    
    @Override
    public List<IActivityDTO> getIActivityList(Date date) throws DALException
    { return null; }
    
    @Override
    public List<IActivityDTO> getIActivityList(double minVal, double maxVal) throws DALException
    { return null; }
    
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
        
        // Sets WorkerDTOs List<IWorkplaceDTO> workplaces via WorkplaceDAO.
        workerDTOToReturn.setIEmployers(iEmployerDAO.getIWorkPlaceList(workerDTOToReturn.getWorkerID()));
        
        // Sets WorkplaceDTOs List<IJobDTO> jobList via JobDAO, for each WorkplaceDTO in Workers List<WorkplaceDTO>
        for (IEmployerDTO workPlaceDTO : workerDTOToReturn.getIEmployers()) {
            List<IJobDTO> iJobDToList = iJobDAO.getIJobList(workPlaceDTO.getWorkplaceID());
            workPlaceDTO.setIJobList(iJobDToList);
        }
        // Sets JobDTOs List<IActivityDTO> shiftList via ActivityDAO, for each IJobDTO in each IWorkplaceDTO in Workers List<WorkplaceDTO>
        for (IEmployerDTO iworkPlaceDTO : workerDTOToReturn.getIEmployers()) {
            for (IJobDTO iJobDTO : iworkPlaceDTO.getIJobList()) {
                List<IActivityDTO> iActivityDTOList = iActivityDAO.getIShiftList(iJobDTO.getJobID());
                iJobDTO.setIShiftDTOList(iActivityDTOList);
            }
        }

        return workerDTOToReturn;
    }

}
