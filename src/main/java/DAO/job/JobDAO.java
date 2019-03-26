package DAO.job;

import DAO.DALException;
import DAO.workPlace.WorkPlaceDAO;
import DTOs.job.JobDTO;
import DTOs.workPlace.WorkPlaceDTO;
import db.DBController;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class JobDAO implements IJobDAO {

    /*
    -------------------------- Fields --------------------------
     */
    
    private DBController dbController;
    private final String JOBS_TABLENAME = "Jobs";

    private WorkPlaceDAO workPlaceDAO;
    
    /*
    ----------------------- Constructor -------------------------
     */

    public JobDAO (DBController dbController, WorkPlaceDAO workPlaceDAO) {

        this.dbController = dbController;
        this.workPlaceDAO = workPlaceDAO;
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
    public JobDTO getJob(int jobID) throws DALException {

        JobDTO jobDTOToReturn = new JobDTO();

        try (Connection c = dbController.getConn()) {

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + JOBS_TABLENAME + " WHERE " + JobTableColumns.jobID + " = " + jobID);

            //Gets first row as a result of the query.
            resultSet.next();

            // Sets JobID.
            jobDTOToReturn.setJobID(resultSet.getInt(JobTableColumns.jobID.toString()));

            // Creating workPlaceDTO and sets it as Jobs WorkplaceDTO. TODO: Skal vi have workplaceID i stedet for WorkplaceDTO objekt?
            // TODO: Eller skal vi finde en anden måde at få fingrene i WorkplaceListen?
            WorkPlaceDTO workPlaceDTO = workPlaceDAO.getWorkPlace(resultSet.getInt(JobTableColumns.workplaceID.toString()));
            jobDTOToReturn.setWorkPlaceDTO(workPlaceDTO);

            // Sets JobName.
            jobDTOToReturn.setJobName(resultSet.getString(JobTableColumns.jobName.toString()));
            // Sets HireDate (LocalDate object).
            jobDTOToReturn.setHireDate(resultSet.getDate(JobTableColumns.hireDate.toString()).toLocalDate());
            // Sets stdSalary.
            jobDTOToReturn.setStdSalary(resultSet.getDouble(JobTableColumns.stdSalary.toString()));

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

        return jobDTOToReturn;
    }

    @Override
    public List<JobDTO> getJobList() throws DALException {
        List<JobDTO> jobDTOListToReturn = new ArrayList<>();

        try (Connection c = dbController.getConn()) {

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT " + JobTableColumns.jobID + " FROM " + JOBS_TABLENAME );

            while (resultSet.next()) {
                jobDTOListToReturn.add(getJob(resultSet.getInt(JobTableColumns.jobID.toString())));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

        return jobDTOListToReturn;
    }

    @Override
    public List<JobDTO> getJobList(String condition) throws DALException {
        List<JobDTO> jobDTOListToReturn = new ArrayList<>();

        try (Connection c = dbController.getConn()) {

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT " + JobTableColumns.jobID + " FROM " + JOBS_TABLENAME + " WHERE " + condition );

            while (resultSet.next()) {
                jobDTOListToReturn.add(getJob(resultSet.getInt(JobTableColumns.jobID.toString())));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

        return jobDTOListToReturn;
    }

    @Override
    public void createJob(JobDTO jobDTO) throws DALException {

        String query = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?,?,?,?)",
                JOBS_TABLENAME, JobTableColumns.workplaceID, JobTableColumns.jobName, JobTableColumns.hireDate, JobTableColumns.stdSalary);

        try (Connection c = dbController.getConn()) {

            PreparedStatement pStatement = c.prepareStatement(query);
            pStatement.setInt(1, jobDTO.getWorkPlaceDTO().getWorkplaceID());
            pStatement.setString(2, jobDTO.getJobName());
            pStatement.setDate(3, Date.valueOf(jobDTO.getHireDate()));
            pStatement.setDouble(4, jobDTO.getStdSalary());

            pStatement.executeUpdate();

        } catch (SQLException e) {
            throw  new DALException(e.getMessage());
        }
    }

    @Override
    public int updateJob(JobDTO jobDTO) throws DALException {
        return 0;
    }

    @Override
    public void deleteJob(int jobID) throws DALException {

    }


    
    /*
    ---------------------- Support Methods ----------------------
     */

    public enum JobTableColumns {
        jobID,
        workplaceID,
        jobName,
        hireDate,
        stdSalary
    }

}
