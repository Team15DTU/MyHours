package db;

import dao.DALException;
import dto.activity.ActivityDTO;
import dto.activity.IActivityDTO;
import dto.employer.EmployerDTO;
import dto.employer.IEmployerDTO;
import dto.job.IJobDTO;
import dto.job.JobDTO;
import dto.worker.IWorkerDTO;
import dto.worker.WorkerDTO;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.util.Date;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public interface IDBController
{
    //region Worker
	
	/*------------------------------------------------------------
	| Creators                                                   |
	-------------------------------------------------------------*/
    void createWorker   (WorkerDTO workerDTO) throws DALException;
    
    /*------------------------------------------------------------
    | Get singles                                                |
    -------------------------------------------------------------*/
    IWorkerDTO      getIWorkerDTO       (String email);
    IWorkerDTO      getIWorkerDTO       (int id);
    
    /*------------------------------------------------------------
    | Get lists                                                  |
    -------------------------------------------------------------*/
    List<IWorkerDTO> getIWorkerDTOList  ();
    List<IWorkerDTO> getIWorkerDTOList  (int minID, int maxID);
	List<IWorkerDTO> getIWorkerDTOList  (String name);
	
	/*------------------------------------------------------------
    | Update	                                                 |
    -------------------------------------------------------------*/
	boolean			updateWorker		(IWorkerDTO workerDTO);

    /*------------------------------------------------------------
    | Delete	                                                 |
    -------------------------------------------------------------*/
    boolean			deleteWorker		(String email);

    //endregion
    
    //region Employer
    
    /*------------------------------------------------------------
    | Creators                                                   |
    -------------------------------------------------------------*/
    void createEmployer (EmployerDTO employer) throws DALException;
    
    /*------------------------------------------------------------
    | Get singles                                                |
    -------------------------------------------------------------*/
    IEmployerDTO 	getIEmployerDTO     (int id);
    
    /*------------------------------------------------------------
    | Get lists                                                  |
    -------------------------------------------------------------*/
    List<IEmployerDTO> getIEmployerList ();
    List<IEmployerDTO> getIEmployerList (int minID, int maxID);
	List<IEmployerDTO> getIEmployerList (String name);
	
	/*------------------------------------------------------------
    | Update	                                                 |
    -------------------------------------------------------------*/
	void			updateEmployer		(IEmployerDTO employerDTO) throws DALException;

    /*------------------------------------------------------------
    | Delete                                                   |
    -------------------------------------------------------------*/
    boolean         deleteEmployer (int employerID);
    
    //endregion
    
    //region Job
    
    /*------------------------------------------------------------
    | Creators                                                   |
    -------------------------------------------------------------*/
    void createJob      (JobDTO job) throws DALException;
    
    /*------------------------------------------------------------
    | Get singles                                                |
    -------------------------------------------------------------*/
    IJobDTO         getIJobDTO          (int id);
    
    /*------------------------------------------------------------
    | Get lists                                                  |
    -------------------------------------------------------------*/
    List<IJobDTO>	getIJobDTOList		();
    List<IJobDTO>	getIJobDTOList		(int employerID);
	List<IJobDTO>	getIJobDTOList		(String name);
	List<IJobDTO>	getIJobDTOList		(double minSalary, double maxSalary);
	
	/*------------------------------------------------------------
    | Update	                                                 |
    -------------------------------------------------------------*/
	void			updateJob			(IJobDTO jobDTO) throws DALException;

    /*------------------------------------------------------------
    | Delete                                                   |
    -------------------------------------------------------------*/
    boolean         deleteJob (int jobID);
    
    //endregion
    
    //region Activity
    
    /*------------------------------------------------------------
    | Creators                                                   |
    -------------------------------------------------------------*/
    void createActivity (ActivityDTO activity) throws DALException;
    
    /*------------------------------------------------------------
    | Get singles                                                |
    -------------------------------------------------------------*/
    IActivityDTO 		getIActivity        (int id);
    
    /*------------------------------------------------------------
    | Get lists                                                  |
    -------------------------------------------------------------*/
    List<IActivityDTO>  getIActivityList 	();
    List<IActivityDTO>  getIActivityList 	(int jobID);
	List<IActivityDTO>  getIActivityList 	(Date date);
	List<IActivityDTO>  getIActivityList 	(double minVal, double maxVal);
	
	/*------------------------------------------------------------
    | Update	                                                 |
    -------------------------------------------------------------*/
	void				updateActivity	(IActivityDTO activityDTO) throws DALException;

	/*------------------------------------------------------------
    | Delete                                                   |
    -------------------------------------------------------------*/
    void             deleteActivity (ActivityDTO activityDTO) throws DALException;

    //endregion
    
    /*------------------------------------------------------------
    | Utilities                                                  |
    -------------------------------------------------------------*/
    String  setTimeZoneFromSQLServer    ();
	int     getNextAutoIncremental      (String tableName);

    void logOut                         (HttpServletRequest request);
    boolean isSessionActive             (HttpServletRequest request);
	boolean loginCheck                  (WorkerDTO user, HttpServletRequest request);
}
