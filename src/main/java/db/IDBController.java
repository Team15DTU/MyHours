package db;

import DAO.DALException;
import dto.worker.IWorkerDTO;

/**
 * @author Rasmus Sander Larsen
 */
public interface IDBController  {

    void createWorker(IWorkerDTO workerDTO, String password) throws DALException;

    String setTimeZoneFromSQLServer() throws DALException;

    IWorkerDTO getIWorkerDTO (String email) throws DALException;
}
