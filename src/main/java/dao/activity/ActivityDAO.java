package dao.activity;

import dao.ConnectionHelper;
import dao.DALException;
import dto.activity.ActivityDTO;
import dto.activity.IActivityDTO;
import db.IConnPool;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class ActivityDAO implements IActivityDAO {

    /*
    -------------------------- Fields --------------------------
     */

    private IConnPool iConnPool;
    private ConnectionHelper connectionHelper;
    
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public ActivityDAO(IConnPool iConnPool, ConnectionHelper connectionHelper) {
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
    public IActivityDTO getiActivity(int userID, LocalDateTime dateAndTime) {
        return null;
    }

    @Override
    public IActivityDTO getiActivity(int activityID) throws DALException {
        IActivityDTO returnedActivity = new ActivityDTO();

        Connection c = iConnPool.getConn();

        String getQuery = String.format("SELECT * FROM %s WHERE %s = ?",
                ActivityConstants.TABLENAME,
                ActivityConstants.id);              // ParameterIndex 1

        try {

            PreparedStatement preparedStatement = c.prepareStatement(getQuery);
            preparedStatement.setInt(1, activityID);

            ResultSet getResultSet = preparedStatement.executeQuery();

            while (getResultSet.next()){
                returnedActivity.setActivityID(getResultSet.getInt(ActivityConstants.id));
                returnedActivity.setJobID(getResultSet.getInt(ActivityConstants.jobID));
                //TODO: SKal der tages højde for resultset med null kolonner?
                returnedActivity.setStartingDateTime(getResultSet.getTimestamp(ActivityConstants.startDateTime).toLocalDateTime());
                returnedActivity.setEndingDateTime(getResultSet.getTimestamp(ActivityConstants.endDateTime).toLocalDateTime());
                returnedActivity.setPause(Duration.ofMinutes(getResultSet.getLong(ActivityConstants.pause)));
                returnedActivity.setActivityValue(getResultSet.getDouble(ActivityConstants.activityValue));
            }

        } catch (SQLException e){
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }
        return returnedActivity;
    }

    @Override
    public List<IActivityDTO> getiActivityList() throws DALException {
        List<IActivityDTO> fullActivityList = new ArrayList<>();

        Connection c = iConnPool.getConn();

        String getActivityIDsQuery = String.format("SELECT %s FROM %s",
                ActivityConstants.id,
                ActivityConstants.TABLENAME);

        try {
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery(getActivityIDsQuery);

            while (resultSet.next()) {
                fullActivityList.add(getiActivity(resultSet.getInt(ActivityConstants.id)));
            }

        } catch (SQLException e){
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }

        return fullActivityList;
    }

    @Override
    public List<IActivityDTO> getiActivityList(int jobID) throws DALException {
        List<IActivityDTO> activityListByJobID = new ArrayList<>();

        Connection c = iConnPool.getConn();

        String getActivityIDsQueryByJobID = String.format("SELECT %s FROM %s WHERE %s = ?",
                ActivityConstants.id,
                ActivityConstants.TABLENAME,
                ActivityConstants.jobID);           // ParameterIndex 1

        try {
            PreparedStatement preparedStatement = c.prepareStatement(getActivityIDsQueryByJobID);
            preparedStatement.setInt(1,jobID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                activityListByJobID.add(getiActivity(resultSet.getInt(ActivityConstants.id)));
            }

        } catch (SQLException e){
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }

        return activityListByJobID;

    }

    @Override
    public List<IActivityDTO> getiActivityList(int jobID, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        return null;
    }

    @Override
    public void createiActivity(IActivityDTO activity) throws DALException {

        Connection c = iConnPool.getConn();

        String createQuery = String.format("INSERT INTO %s (%s,%s,%s,%s,%s) VALUES (?,?,?,?,?)",
                ActivityConstants.TABLENAME,
                ActivityConstants.jobID,                // ParameterIndex 1
                ActivityConstants.startDateTime,        // ParameterIndex 2
                ActivityConstants.endDateTime,          // ParameterIndex 3
                ActivityConstants.pause,                // ParameterIndex 4
                ActivityConstants.activityValue);       // ParameterIndex 5

        try {
            c.setAutoCommit(false);

            PreparedStatement preparedStatement = c.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,activity.getJobID());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(activity.getStartingDateTime()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(activity.getEndingDateTime()));
            preparedStatement.setLong(4,activity.getPause().toMinutes());
            preparedStatement.setDouble(5,activity.getActivityValue());

            preparedStatement.executeUpdate();

            // Sets the assigned activityID to the inputted IActivityDTO Object.
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                activity.setActivityID(generatedKeys.getInt(1));
            }

            c.commit();

        } catch (SQLException e){
            connectionHelper.catchSQLExceptionAndDoRollback(c,e, "ActivityDAO.createiActivity");
        } finally {
            connectionHelper.finallyActionsForConnection(c,"ActivityDAO.createiActivity");
        }
    }

    @Override
    public void updateiActivity(IActivityDTO activity) {

    }

    @Override
    public void deleteiActivity(int userID, LocalDateTime dateAndTime) throws DALException {


    }

    @Override
    public void deleteiActivity(int activityID) throws DALException {

        Connection c = iConnPool.getConn();

        String deleteQuery = String.format("DELETE FROM %s WHERE %s = ?",
                ActivityConstants.TABLENAME,
                ActivityConstants.id);

        try {
            c.setAutoCommit(false);

            PreparedStatement preparedStatement = c.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, activityID);

            preparedStatement.executeUpdate();

            c.commit();
        } catch (SQLException e) {
            connectionHelper.catchSQLExceptionAndDoRollback(c,e,"ActivityDAO.deleteiActivity");
        } finally {
            connectionHelper.finallyActionsForConnection(c,"ActivityDAO.deleteiActivity");
        }
    }

    /*
    ---------------------- Support Methods ----------------------
     */


}
