package DAO.worker;

import DTOs.worker.WorkerDTO;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class WorkerDAO implements IWorkerDAO {

    /*
    -------------------------- Fields --------------------------
     */
    
    
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

    @Override
    public List<WorkerDTO> getWorkerList() {
        return null;
    }

    @Override
    public void createWorker(WorkerDTO worker, String password) {

    }

    @Override
    public void updateWorker(WorkerDTO worker) {

    }

    @Override
    public void deleteWorker(String email) {

    }

    public WorkerDTO getWorker (String email) {

        return new WorkerDTO();
    }
    
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
