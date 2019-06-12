package DAO.employer;

import DAO.DALException;
import DTOs.workPlace.IEmployerDTO;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public interface IEmployerDAO {

    /*
    ---------------------- Public Methods -----------------------
     */

    // returns a EmployerDTO from an workplaceID
    IEmployerDTO getIWorkPlace(int workplaceID) throws DALException;

    // Returns a List of ALL EmployerDTO object.
    List<IEmployerDTO> getIWorkPlaceList() throws DALException;

    // Returns a List of ONE workers EmployerDTO object.
    List<IEmployerDTO> getIWorkPlaceList(int workerID) throws DALException;

    // Inserts the data from a EmployerDTO into DB.
    void createIWorkPlace(IEmployerDTO workPlaceDTO) throws DALException;

    // Updates the data on a employer row in the DB.
    void updateIWorkPlace(IEmployerDTO workPlaceDTO) throws DALException;

    // Deletes all information about one employer, from an UserID and employer.Name TODO: Maybe use workPlaceID?.
    void deleteIWorkPlace(int workplaceID) throws DALException;

}
