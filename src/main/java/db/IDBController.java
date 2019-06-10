package db;

import DAO.DALException;
import DTOs.job.IJobDTO;
import DTOs.activity.IActivityDTO;
import DTOs.workPlace.IEmployerDTO;
import DTOs.worker.IWorkerDTO;

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
    void createWorker   (IWorkerDTO workerDTO, String password) throws DALException;
    
    /*------------------------------------------------------------
    | Get singles                                                |
    -------------------------------------------------------------*/
    IWorkerDTO      getIWorkerDTO       (String email)          throws DALException;
    IWorkerDTO      getIWorkerDTO       (int id)                throws DALException;
    
    /*------------------------------------------------------------
    | Get lists                                                  |
    -------------------------------------------------------------*/
    List<IWorkerDTO> getIWorkerDTOList  ()                      throws DALException;
    List<IWorkerDTO> getIWorkerDTOList  (int minID, int maxID)  throws DALException;
	List<IWorkerDTO> getIWorkerDTOList  (String name)      		throws DALException;
    
    //endregion
    
    //region Employer
    
    /*------------------------------------------------------------
    | Creators                                                   |
    -------------------------------------------------------------*/
    void createEmployer (IEmployerDTO employer)                	throws DALException;
    
    /*------------------------------------------------------------
    | Get singles                                                |
    -------------------------------------------------------------*/
    IEmployerDTO 	getIEmployerDTO     (int id)                throws DALException;
    
    /*------------------------------------------------------------
    | Get lists                                                  |
    -------------------------------------------------------------*/
    List<IEmployerDTO> getIEmployerList ()						throws DALException;
    List<IEmployerDTO> getIEmployerList (int minID, int maxID)	throws DALException;
	List<IEmployerDTO> getIEmployerList (String name)			throws DALException;
    
    //endregion
    
    //region Job
    
    /*------------------------------------------------------------
    | Creators                                                   |
    -------------------------------------------------------------*/
    void createJob      (IJobDTO job)                           throws DALException;
    
    /*------------------------------------------------------------
    | Get singles                                                |
    -------------------------------------------------------------*/
    IJobDTO         getIJobDTO          (int id)                throws DALException;
    
    /*------------------------------------------------------------
    | Get lists                                                  |
    -------------------------------------------------------------*/
    List<IJobDTO>	getIJobDTOList		()						throws DALException;
    List<IJobDTO>	getIJobDTOList		(int employerID)		throws DALException;
	List<IJobDTO>	getIJobDTOList		(String name)			throws DALException;
	List<IJobDTO>	getIJobDTOList		(double minSalary, double maxSalary) throws DALException;	// FUCK!
    
    //endregion
    
    //region Activity
    
    /*------------------------------------------------------------
    | Creators                                                   |
    -------------------------------------------------------------*/
    void createActivity (IActivityDTO activity)                 	throws DALException;
    
    /*------------------------------------------------------------
    | Get singles                                                |
    -------------------------------------------------------------*/
    IActivityDTO 		getIActivity        (int id)                throws DALException;
    
    /*------------------------------------------------------------
    | Get lists                                                  |
    -------------------------------------------------------------*/
    List<IActivityDTO>  getIActivityList 	()						throws DALException;
    List<IActivityDTO>  getIActivityList 	(int jobID)				throws DALException;
	List<IActivityDTO>  getIActivityList 	(Date date)				throws DALException;
	List<IActivityDTO>  getIActivityList 	(double minVal, double maxVal)	throws DALException;	// Again ...
    
    //endregion
    
    /*------------------------------------------------------------
    | Utilities                                                  |
    -------------------------------------------------------------*/
    String setTimeZoneFromSQLServer() throws DALException;
	int getNextAutoIncremental(String tableName) throws DALException;
}
