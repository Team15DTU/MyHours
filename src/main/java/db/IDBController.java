package db;

import DAO.DALException;
import DTOs.worker.WorkerDTO;

/**
 * @author Rasmus Sander Larsen
 */
public interface IDBController  {

    void createWorker(WorkerDTO workerDTO) throws DALException;

    String setTimeZoneFromSQLServer() throws DALException;

}
