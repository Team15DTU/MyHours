package DAO.job;

import DAO.DALException;
import dto.job.IJobDTO;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public interface IJobDAO {

    /**
     * This methods returns a JobDTO object with details from DB matching the inputted jobID.
     * @param jobID This is the jobID we are looking for in the DB and the details that is loaded into the returned JobDTO.
     * @return A JobDTO object with details matching the information in DB on th inputted jobID.
     * @throws DALException Will throw a DALException.
     */
    IJobDTO getIJob(int jobID) throws DALException;

    /**
     * This method returns a List of JobDTOs. This list should contain all jobs existing in the DB.
     * @return A List of JobDTO objects.
     * @throws DALException Will throw a DALException.
     */
    List<IJobDTO> getIJobList () throws DALException;

    /**
     * This method returns a List of JobDTOs. This list should contain all jobs existing in the DB.
     * @return A List of JobDTO objects.
     * @throws DALException Will throw a DALException.
     */
    List<IJobDTO> getIJobList (int workplaceID) throws DALException;

    /**
     * This method returns a List of JobDTOs matching the inputted condition.
     * @param specialCondition This sting will be inserted after the "WHERE" clause in the "SELECT * FROM" query.
     * @return A List object of JobDTO objects matching the inputted Condition.
     * @throws DALException Will throw a DALException.
     */
    List<IJobDTO> getIJobList (String specialCondition) throws DALException;

    /**
     * This method inserts the details from the inputted JobDTO object into the DB.
     * @param jobDTO The values of the JobDTO object variables are inserted into the correct table in the DB.
     * @throws DALException Will throw a DALException.
     */
    void createIJob (IJobDTO jobDTO) throws DALException;

    /**
     * This method updates a Job details in the DB, with the values from the inputted JobDTO object.
     * @param jobDTO This is the JobDTO object which values the Job is updated with.
     * @return Returns a number saying how many columns which had its value changed as a result of the query.
     * @throws DALException Will throw a DALException.
     */
    int updateIJob (IJobDTO jobDTO) throws DALException;

    /**
     * This method will delete all information about the inputted jobID.
     * @param jobID This jobID is the job that will be deleted.
     * @throws DALException Will throw a DALException.
     */
    void deleteIJob (int jobID) throws DALException;

}
