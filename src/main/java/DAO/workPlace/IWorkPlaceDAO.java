package DAO.workPlace;

import DAO.DALException;
import dto.workPlace.IWorkPlaceDTO;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public interface IWorkPlaceDAO {

    /*
    ---------------------- Public Methods -----------------------
     */

    // returns a WorkPlaceDTO from an workplaceID
    IWorkPlaceDTO getIWorkPlace(int workplaceID) throws DALException;

    // Returns a List of ALL WorkPlaceDTO object.
    List<IWorkPlaceDTO> getIWorkPlaceList() throws DALException;

    // Returns a List of ONE workers WorkPlaceDTO object.
    List<IWorkPlaceDTO> getIWorkPlaceList(int workerID) throws DALException;

    // Inserts the data from a WorkPlaceDTO into DB.
    void createIWorkPlace(IWorkPlaceDTO workPlaceDTO) throws DALException;

    // Updates the data on a workPlace row in the DB.
    void updateIWorkPlace(IWorkPlaceDTO workPlaceDTO) throws DALException;

    // Deletes all information about one workPlace, from an UserID and workPlace.Name TODO: Maybe use workPlaceID?.
    void deleteIWorkPlace(int workplaceID) throws DALException;

}
