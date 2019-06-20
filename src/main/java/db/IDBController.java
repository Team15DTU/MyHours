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
    void createWorker   (IWorkerDTO workerDTO);
    
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
    void createEmployer (IEmployerDTO employer);
    
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
	boolean			updateEmployer		(IEmployerDTO employerDTO);

    /*------------------------------------------------------------
    | Delete                                                   |
    -------------------------------------------------------------*/
    boolean         deleteEmployer (int employerID);
    
    //endregion
    
    //region Job
    
    /*------------------------------------------------------------
    | Creators                                                   |
    -------------------------------------------------------------*/
    void createJob      (IJobDTO job);
    
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
	boolean			updateJob			(IJobDTO jobDTO);

    /*------------------------------------------------------------
    | Delete                                                   |
    -------------------------------------------------------------*/
    boolean         deleteJob (int jobID);
    
    //endregion
    
    //region Activity
    
    /*------------------------------------------------------------
    | Creators                                                   |
    -------------------------------------------------------------*/
    void createActivity (IActivityDTO activity);
    
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
	boolean				updateActivity	(IActivityDTO activityDTO);

	/*------------------------------------------------------------
    | Delete                                                   |
    -------------------------------------------------------------*/
    void             deleteActivity (ActivityDTO activityDTO) throws DALException;

    //endregion
    
    /*------------------------------------------------------------
    | Utilities                                                  |
    -------------------------------------------------------------*/
    String  setTimeZoneFromSQLServer    ();

    void logOut                         (HttpServletRequest request);
    boolean isSessionActive             (HttpServletRequest request);
	boolean loginCheck                  (WorkerDTO user, HttpServletRequest request);
}
