package db;

import DAO.DALException;
import DTOs.job.IJobDTO;
import DTOs.shift.IShiftDTO;
import DTOs.workPlace.IWorkPlaceDTO;
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
    void createEmployer (IWorkPlaceDTO employer)                throws DALException;
    void createJob      (IJobDTO job)                           throws DALException;
    void createActivity (IShiftDTO activity)                    throws DALException;
    
    /*------------------------------------------------------------
    | Get singles                                                |
    -------------------------------------------------------------*/
    IWorkerDTO      getIWorkerDTO       (String email)          throws DALException;
    IWorkerDTO      getIWorkerDTO       (int id)                throws DALException;
    IWorkPlaceDTO   getIEmployerDTO     (int id)                throws DALException;
    IJobDTO         getIJobDTO          (int id)                throws DALException;
    IShiftDTO       getIActivity        (int id)                throws DALException;
    
    /*------------------------------------------------------------
    | Get lists                                                  |
    -------------------------------------------------------------*/
    
    
    /*------------------------------------------------------------
    | Utilities                                                  |
    -------------------------------------------------------------*/
    String setTimeZoneFromSQLServer() throws DALException;
}
