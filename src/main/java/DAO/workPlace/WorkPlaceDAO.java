package DAO.workPlace;

import DTOs.workPlace.WorkPlaceDTO;
import DTOs.worker.WorkerDTO;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class WorkPlaceDAO implements IWorkPlaceDAO {

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
    public WorkPlaceDTO getWorkPlace(int userID, int workPlaceID) {
        return null;
    }

    @Override
    public List<WorkPlaceDTO> getWorkPlaceList(WorkerDTO workerDTO) {
        return null;
    }

    @Override
    public void createWorkPlace(WorkPlaceDTO workPlaceDTO) {

    }

    @Override
    public void updateWorkPlace(WorkPlaceDTO workPlaceDTO) {

    }

    @Override
    public void deleteWorkPlace(int userID, String workPlaceName) {

    }

    /*
    ---------------------- Support Methods ----------------------
     */


}
