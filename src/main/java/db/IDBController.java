package db;

import DAO.DALException;
import DTOs.job.IJobDTO;
import DTOs.activity.IActivityDTO;
import DTOs.workPlace.IEmployerDTO;
import DTOs.worker.IWorkerDTO;

/**
 * @author Rasmus Sander Larsen
 */
public interface IDBController
{
    /*------------------------------------------------------------
    | Creators                                                   |
    -------------------------------------------------------------*/
    void createWorker   (IWorkerDTO workerDTO, String password) throws DALException;
    void createEmployer (IEmployerDTO employer)                	throws DALException;
    void createJob      (IJobDTO job)                           throws DALException;
    void createActivity (IActivityDTO activity)                 throws DALException;
    
    /*------------------------------------------------------------
    | Get singles                                                |
    -------------------------------------------------------------*/
    IWorkerDTO      getIWorkerDTO       (String email)          throws DALException;
    IWorkerDTO      getIWorkerDTO       (int id)                throws DALException;
    IEmployerDTO 	getIEmployerDTO     (int id)                throws DALException;
    IJobDTO         getIJobDTO          (int id)                throws DALException;
    IActivityDTO 	getIActivity        (int id)                throws DALException;
    
    /*------------------------------------------------------------
    | Get lists                                                  |
    -------------------------------------------------------------*/
    
    
    /*------------------------------------------------------------
    | Utilities                                                  |
    -------------------------------------------------------------*/
    String setTimeZoneFromSQLServer() throws DALException;
}
