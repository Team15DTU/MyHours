package db;

import DAO.DALException;
import DTOs.job.IJobDTO;
import DTOs.activity.IActivityDTO;
import DTOs.workPlace.IEmployerDTO;
import DTOs.worker.IWorkerDTO;

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
    
    //endregion
    
    //region Activity
    
    /*------------------------------------------------------------
    | Creators                                                   |
    -------------------------------------------------------------*/
    void createActivity (IActivityDTO activity)                 throws DALException;
    
    /*------------------------------------------------------------
    | Get singles                                                |
    -------------------------------------------------------------*/
    IActivityDTO 	getIActivity        (int id)                throws DALException;
    
    /*------------------------------------------------------------
    | Get lists                                                  |
    -------------------------------------------------------------*/
    
    //endregion
    
    /*------------------------------------------------------------
    | Utilities                                                  |
    -------------------------------------------------------------*/
    String setTimeZoneFromSQLServer() throws DALException;
}
