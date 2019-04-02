package db;

import DTOs.worker.WorkerDTO;

/**
 * @author Rasmus Sander Larsen
 */
public interface IDBController  {

    void createWorker(WorkerDTO workerDTO);

    String setTimeZoneFromSQLServer();

}
