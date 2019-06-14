package db;

import dao.DALException;
import dto.job.IJobDTO;
import dto.activity.IActivityDTO;
import dto.employer.IEmployerDTO;
import dto.worker.IWorkerDTO;
import dto.worker.WorkerDTO;

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
    
    //endregion
    
    /*------------------------------------------------------------
    | Utilities                                                  |
    -------------------------------------------------------------*/
    String  setTimeZoneFromSQLServer    ();
	int     getNextAutoIncremental      (String tableName);
	boolean loginCheck                  (WorkerDTO user);
}
