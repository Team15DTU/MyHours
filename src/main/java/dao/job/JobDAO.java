package dao.job;

import dao.ConnectionHelper;
import dao.DALException;
import dto.job.IJobDTO;
import dto.job.JobDTO;
import db.IConnPool;

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
    
    private IConnPool iConnPool;
    private ConnectionHelper connectionHelper;
    
    /*
    ----------------------- Constructor -------------------------
     */

    public JobDAO (IConnPool iConnPool, ConnectionHelper connectionHelper) {

        this.iConnPool = iConnPool;
        this.connectionHelper = connectionHelper;

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
    public IJobDTO getIJob(int jobID) throws DALException {

        Connection c = iConnPool.getConn();

        IJobDTO jobDTOToReturn = new JobDTO();

        String getQuery = String.format("SELECT * FROM %s WHERE %s = ?",
                JobConstants.TABLENAME,
                JobConstants.id); // ParameterIndex 1.

        try {
            PreparedStatement preparedStatement = c.prepareStatement(getQuery);
            preparedStatement.setInt(1, jobID);

            ResultSet resultSet = preparedStatement.executeQuery();

            //Gets first row as a result of the query.
            while (resultSet.next()) {
                // Sets JobID.
                jobDTOToReturn.setJobID(resultSet.getInt(JobConstants.id));
                // Sets workplaceID.
                jobDTOToReturn.setEmployerID(resultSet.getInt(JobConstants.employerID));
                // Sets JobName.
                jobDTOToReturn.setJobName(resultSet.getString(JobConstants.jobName));
                // Sets HireDate (LocalDate object). If no date, hireDate is set to null.
                if (resultSet.getDate(JobConstants.hireDate) != null) {
                    jobDTOToReturn.setHireDate(resultSet.getDate(JobConstants.hireDate).toLocalDate());
                } else {
                    jobDTOToReturn.setHireDate(null);
                }
                // Sets FinishDate (LocalDate object). If no date, FinishDate is set to null.
                if (resultSet.getDate(JobConstants.finishDate) != null) {
                    jobDTOToReturn.setFinishDate(resultSet.getDate(JobConstants.finishDate).toLocalDate());
                } else {
                    jobDTOToReturn.setFinishDate(null);
                }
                // Sets stdSalary.
                jobDTOToReturn.setStdSalary(resultSet.getDouble(JobConstants.salary));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }

        return jobDTOToReturn;
    }

    @Override
    public List<IJobDTO> getIJobList() throws DALException {
        List<IJobDTO> jobDTOListToReturn = new ArrayList<>();

        Connection c = iConnPool.getConn();

        String getAllJobIDsQeury = String.format("SELECT %s FROM %s",
                JobConstants.id,
                JobConstants.TABLENAME);
        try {

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery(getAllJobIDsQeury);

            while (resultSet.next()) {
                jobDTOListToReturn.add(getIJob(resultSet.getInt(JobConstants.id)));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }

        return jobDTOListToReturn;
    }

    @Override
    public List<IJobDTO> getIJobList(int employerID) throws DALException {
        List<IJobDTO> jobDTOListToReturn = new ArrayList<>();

        Connection c = iConnPool.getConn();

        String getEmployersJobIDsQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                JobConstants.id,
                JobConstants.TABLENAME,
                JobConstants.employerID); // ParameterIndex 1

        try {
            PreparedStatement preparedStatement = c.prepareStatement(getEmployersJobIDsQuery);
            preparedStatement.setInt(1, employerID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                jobDTOListToReturn.add(getIJob(resultSet.getInt(JobConstants.id)));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }

        return jobDTOListToReturn;
    }

    @Override // TODO: Er det her ikke bare dumt? Vi har intet at bruge denne klasse til? Ellers skal den i hvert fald ændres.
    public List<IJobDTO> getIJobList(String condition) throws DALException {
        List<IJobDTO> jobDTOListToReturn = new ArrayList<>();

        Connection c = iConnPool.getConn();

        String getJobIDsWithCondition = String.format("SELECT %s FROM %s WHERE ?",
                JobConstants.id,
                JobConstants.TABLENAME);

        try {
            PreparedStatement preparedStatement = c.prepareStatement(getJobIDsWithCondition);
            preparedStatement.setString(1, condition);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                jobDTOListToReturn.add(getIJob(resultSet.getInt(JobConstants.id)));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }

        return jobDTOListToReturn;
    }

    @Override
    public void createIJob(IJobDTO jobDTO) throws DALException {

        Connection c = iConnPool.getConn();

        String createQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?,?,?,?,?)",
                JobConstants.TABLENAME,
                JobConstants.employerID,    // ParameterIndex 1
                JobConstants.jobName,       // ParameterIndex 2
                JobConstants.hireDate,      // ParameterIndex 3
                JobConstants.finishDate,    // ParameterIndex 4
                JobConstants.salary);       // ParameterIndex 5

        try {
            c.setAutoCommit(false);

            PreparedStatement pStatement = c.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
            pStatement.setInt(1, jobDTO.getEmployerID());
            pStatement.setString(2, jobDTO.getJobName());

            // TODO: Er det ikke lige meget at tjekke om date er null, fordi hvis den er det, bliver null vel bare returneret?
            // Inserts null if no hire date.
            if (jobDTO.getHireDate() != null ) {
                pStatement.setDate(3, Date.valueOf(jobDTO.getHireDate()));
            } else {
                pStatement.setDate(3,null);
            }
            // Inserts null if no hire date.
            if (jobDTO.getFinishDate() != null ) {
                pStatement.setDate(4, Date.valueOf(jobDTO.getFinishDate()));
            } else {
                pStatement.setDate(4,null);
            }
            pStatement.setDouble(5, jobDTO.getStdSalary());

            pStatement.executeUpdate();

            ResultSet generatedKeys = pStatement.getGeneratedKeys();

            if (generatedKeys.next()){
                jobDTO.setJobID(generatedKeys.getInt(1));
            }

            c.commit();

        } catch (SQLException e) {
            connectionHelper.catchSQLExceptionAndDoRollback(c,e, "JobDAO.createIJob");
        } finally {
            connectionHelper.finallyActionsForConnection(c, "JobDAO.createIJob");
        }
    }

    @Override
    public int updateIJob(IJobDTO jobDTO) throws DALException {

        Connection c = iConnPool.getConn();

        String query = String.format("UPDATE %s SET %s = ? , %s = ? , %s = ? , %s = ? , %s = ? WHERE %s = ?",
                JobConstants.TABLENAME,
                JobConstants.employerID,    // ParameterIndex 1
                JobConstants.jobName,       // ParameterIndex 2
                JobConstants.hireDate,      // ParameterIndex 3
                JobConstants.finishDate,    // ParameterIndex 4
                JobConstants.salary,        // ParameterIndex 5
                JobConstants.id);           // ParameterIndex 6

        try {
            c.setAutoCommit(false);

            PreparedStatement pStatement = c.prepareStatement(query);

            pStatement.setInt(1, jobDTO.getEmployerID());
            pStatement.setString(2, jobDTO.getJobName());
            // TODO: Tjekket om det er null er muligvis liget meget?
            if (jobDTO.getHireDate() != null) {
                pStatement.setDate(3, Date.valueOf(jobDTO.getHireDate()));
            } else {
                pStatement.setDate(3, null);
            }
            if (jobDTO.getFinishDate() != null) {
                pStatement.setDate(4, Date.valueOf(jobDTO.getFinishDate()));
            } else {
                pStatement.setDate(4, null);
            }
            pStatement.setDouble(5, jobDTO.getStdSalary());
            pStatement.setInt(6, jobDTO.getJobID());

            pStatement.executeUpdate();

            c.commit();

        } catch (SQLException e) {
            connectionHelper.catchSQLExceptionAndDoRollback(c,e,"JobDAO.updateIJob");
        } finally {
            connectionHelper.finallyActionsForConnection(c, "JobDAO.updateIJob");
        }

        return 0;
    }

    @Override
    public void deleteIJob(int jobID) throws DALException {

        Connection c = iConnPool.getConn();

        String deleteQuery = String.format("DELETE FROM %s WHERE %s = ?",
                JobConstants.TABLENAME,
                JobConstants.id);       // ParameterIndex 1

        try {
            c.setAutoCommit(false);

            PreparedStatement preparedStatement = c.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, jobID);

            preparedStatement.executeUpdate();

            c.commit();

        } catch (SQLException e) {
            connectionHelper.catchSQLExceptionAndDoRollback(c,e, "JobDAO.deleteIJob");
        } finally {
            connectionHelper.finallyActionsForConnection(c, "JobDAO.deleteIJob");
        }
    }

    /*
    ---------------------- Support Methods ----------------------
     */



}
