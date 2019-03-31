package DAO.job;

import DAO.DALException;
import DAO.workPlace.WorkPlaceDAO;
import DTOs.job.JobDTO;
import DTOs.workPlace.WorkPlaceDTO;
import db.DBController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class JobDAO implements IJobDAO {

    // Names on columns in the DB table: Jobs
    public enum JobTableColumns {
        jobID,
        workplaceID,
        jobName,
        hireDate,
        stdSalary
    }

    /*
    -------------------------- Fields --------------------------
     */
    
    private DBController dbController;
    private final String JOBS_TABLENAME = "Jobs";
    
    /*
    ----------------------- Constructor -------------------------
     */

    public JobDAO (DBController dbController) {

        this.dbController = dbController;
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

            // Sets workplaceID.
            jobDTOToReturn.setWorkplaceID(resultSet.getInt(JobTableColumns.workplaceID.toString()));


            // Sets JobName.
            jobDTOToReturn.setJobName(resultSet.getString(JobTableColumns.jobName.toString()));
            // Sets HireDate (LocalDate object). If no date, hireDate is set to null.
            if (resultSet.getDate(JobTableColumns.hireDate.toString()) != null) {
                jobDTOToReturn.setHireDate(resultSet.getDate(JobTableColumns.hireDate.name()).toLocalDate());
            } else {
                jobDTOToReturn.setHireDate(null);
            }
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
            pStatement.setInt(1, jobDTO.getWorkplaceID());
            pStatement.setString(2, jobDTO.getJobName());
            // Inserts null if no hire date.
            if (jobDTO.getHireDate() != null ) {
                pStatement.setDate(3, Date.valueOf(jobDTO.getHireDate()));
            } else {
                pStatement.setDate(3,null);
            }
            pStatement.setDouble(4, jobDTO.getStdSalary());

            pStatement.executeUpdate();

        } catch (SQLException e) {
            throw  new DALException(e.getMessage());
        }
    }

    @Override
    public int updateJob(JobDTO jobDTO) throws DALException {

        String query = String.format("UPDATE %s SET %s = ? , %s = ? , %s = ? , %s = ? WHERE %s = ?",
                JOBS_TABLENAME, JobTableColumns.workplaceID, JobTableColumns.jobName, JobTableColumns.hireDate,
                JobTableColumns.stdSalary, JobTableColumns.jobID);

        try (Connection c = dbController.getConn()){

            PreparedStatement pStatement = c.prepareStatement(query);

            pStatement.setInt(1, jobDTO.getWorkplaceID());
            pStatement.setString(2, jobDTO.getJobName());
            if (jobDTO.getHireDate() != null) {
                pStatement.setDate(3, Date.valueOf(jobDTO.getHireDate()));
            } else {
                pStatement.setDate(3, null);
            }
            pStatement.setDouble(4, jobDTO.getStdSalary());
            pStatement.setInt(5, jobDTO.getJobID());

            pStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

        return 0;
    }

    @Override
    public void deleteJob(int jobID) throws DALException {

        try (Connection c = dbController.getConn()) {

            Statement statement = c.createStatement();
            statement.executeQuery(
                    "DELETE FROM " + JOBS_TABLENAME + " WHERE " + JobTableColumns.jobID + " = '" + jobID + "'");

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

    }

    /*
    ---------------------- Support Methods ----------------------
     */



}
