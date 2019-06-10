package DAO.activity;

import DTOs.activity.IActivityDTO;
import db.IConnPool;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class ActivityDAO implements IActivityDAO {

    /*
    -------------------------- Fields --------------------------
     */

    private IConnPool iConnPool;
    
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public ActivityDAO(IConnPool iConnPool) {
        this.iConnPool = iConnPool;
    }
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

    @Override
    public IActivityDTO getIShift(int userID, LocalDateTime dateAndTime) {
        return null;
    }

    @Override
    public List<IActivityDTO> getIShiftList() {
        return null;
    }

    @Override
    public List<IActivityDTO> getIShiftList(int jobID) {
        return null;
    }

    @Override
    public List<IActivityDTO> getIShiftList(int jobID, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        return null;
    }

    @Override
    public void createIShift(IActivityDTO shift) {

    }

    @Override
    public void updateIShift(IActivityDTO shift) {

    }

    @Override
    public void deleteIShift(int userID, LocalDateTime dateAndTime) {

    }

    /*
    ---------------------- Support Methods ----------------------
     */


}
