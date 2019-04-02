package DAO.shift;

import DTOs.shift.ShiftDTO;
import DTOs.worker.WorkerDTO;
import db.IConnPool;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class ShiftDAO implements IShiftDAO {

    /*
    -------------------------- Fields --------------------------
     */

    private IConnPool iConnPool;
    
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public ShiftDAO (IConnPool iConnPool) {
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
    public ShiftDTO getShift(int userID, LocalDateTime dateAndTime) {
        return null;
    }

    @Override
    public List<ShiftDTO> getShiftList() {
        return null;
    }

    @Override
    public List<ShiftDTO> getShiftList(int jobID) {
        return null;
    }

    @Override
    public List<ShiftDTO> getShiftList(int jobID, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        return null;
    }

    @Override
    public void createShift(ShiftDTO shift) {

    }

    @Override
    public void updateShift(ShiftDTO shift) {

    }

    @Override
    public void deleteShift(int userID, LocalDateTime dateAndTime) {

    }

    /*
    ---------------------- Support Methods ----------------------
     */


}
