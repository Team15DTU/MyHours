package dao.employer;

import dao.DALException;
import dto.employer.IEmployerDTO;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public interface IEmployerDAO {

    /*
    ---------------------- Public Methods -----------------------
     */

    // returns a EmployerDTO from an workplaceID
    IEmployerDTO getIEmployer(int workplaceID) throws DALException;

    // Returns a List of ALL EmployerDTO object.
    List<IEmployerDTO> getiEmployerList() throws DALException;

    // Returns a List of ONE workers EmployerDTO object.
    List<IEmployerDTO> getiEmployerList(int workerID) throws DALException;

    // Inserts the data from a EmployerDTO into DB.
    void createiEmployer(IEmployerDTO workPlaceDTO) throws DALException;

    // Updates the data on a employer row in the DB.
    void updateiEmployer(IEmployerDTO workPlaceDTO) throws DALException;

    // Deletes all information about one employer, from an UserID and employer.Name TODO: Maybe use workPlaceID?.
    void deleteiEmployer(int workplaceID) throws DALException;

}
