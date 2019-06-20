package dao.worker;

import dao.ConnectionHelper;
import dao.DALException;
import db.IConnPool;
import dto.worker.IWorkerDTO;
import dto.worker.WorkerDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class WorkerDAO implements IWorkerDAO
{
    /*
    -------------------------- Fields --------------------------
     */
    
    private IConnPool connPool;
    private final String WORKERS_TABLENAME = WorkerConstants.TABLENAME;
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public WorkerDAO(IConnPool connPool)
    {
        this.connPool = connPool;
    }

    public WorkerDAO(){

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
    public IWorkerDTO getWorker(String email) throws DALException
    {
        IWorkerDTO workerToReturn = new WorkerDTO();
        
        // Get connection from ConnPool
        Connection c = connPool.getConn();

        try {
            Statement stmtWorkers = c.createStatement();    // Stmt to get Worker Resultset
            ResultSet resultSet = stmtWorkers.executeQuery( //TODO: Need to be made preparedstmt
                    "SELECT * FROM " + WORKERS_TABLENAME + " WHERE " + WorkerConstants.email + " = '" + email + "'");

            while (resultSet.next()) {
                workerToReturn.setWorkerID(resultSet.getInt(WorkerConstants.id));
                workerToReturn.setFirstName(resultSet.getString(WorkerConstants.firstname));
                workerToReturn.setSurName(resultSet.getString(WorkerConstants.surname));
                workerToReturn.setEmail(resultSet.getString(WorkerConstants.email));
                workerToReturn.setBirthday(resultSet.getDate(WorkerConstants.birthday));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            // Return the Connection
            connPool.releaseConnection(c);
        }

        return workerToReturn;
    }
    
    @Override
    public IWorkerDTO getWorker(int id) throws DALException
    {
        String methodName = "getWorker(int id)";
        IWorkerDTO worker = new WorkerDTO("Failure", "Failure", "Failure@Fail.com");
        
        // Get a connection from the pool
        Connection conn = connPool.getConn();
        
        // Get a connection helper object
        ConnectionHelper connHelper = new ConnectionHelper(connPool);
        
        try
        {
            // Create and make the Prepared Statement
            PreparedStatement stmt = conn.prepareStatement( String.format("SELECT * FROM %s WHERE %s = ?",
                    WorkerConstants.TABLENAME, WorkerConstants.id) );
            stmt.setInt(1, id);
            
            // Execute the query
            ResultSet rs = stmt.executeQuery();
            
            // Make the worker out of the result set
            if ( rs.next() )
            {
                // Set simple worker information
                worker.setWorkerID( rs.getInt(WorkerConstants.id) );
                worker.setFirstName( rs.getString(WorkerConstants.firstname) );
                worker.setSurName( rs.getString(WorkerConstants.surname) );
                worker.setBirthday( rs.getDate( WorkerConstants.birthday ));
                worker.setEmail( rs.getString(WorkerConstants.email) );
                
                //TODO: Address of the worker needs to be set!
            }
        }
        catch ( SQLException e )
        {
            connHelper.catchSQLExceptionAndDoRollback(conn, e, methodName);
        }
        catch ( Exception e )
        {
            throw new DALException(e.getMessage(), e.getCause());
        }
        finally
        {
            // Make sure to release connection
            if ( conn != null )
                connHelper.finallyActionsForConnection(conn, methodName);
        }
        
        // Return the created worker or failed worker
        return worker;
    }
    
    @Override
    public List<IWorkerDTO> getWorkerList() throws DALException
    {
        // Get connection from the ConnPool
        Connection c = connPool.getConn();

        List<IWorkerDTO> listToReturn = new ArrayList<>();

        try {

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT " + WorkerConstants.email + " FROM " + WORKERS_TABLENAME);

            while (resultSet.next()) {
                listToReturn.add(getWorker(resultSet.getString(WorkerConstants.email)));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            // Return the connection to the ConnPool
            connPool.releaseConnection(c);
        }

        return listToReturn;
    }

    @Override
    public void createWorker(IWorkerDTO workerDTO) throws DALException
    {
        // Get a Connection from the ConnPool
        Connection c = connPool.getConn();
        
        // The query to make
        String query =
                String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?,?,?,?,?)",
                        WORKERS_TABLENAME, WorkerConstants.firstname, WorkerConstants.surname, WorkerConstants.email,
                        WorkerConstants.birthday, WorkerConstants.password);
                
        try {

            PreparedStatement statement = c.prepareStatement(query);
            
            statement.setString(1, workerDTO.getFirstName());
            statement.setString(2, workerDTO.getSurName());
            statement.setString(3, workerDTO.getEmail());
            statement.setDate(4, workerDTO.getBirthday());
            statement.setString(5, workerDTO.getPassword());

            statement.executeUpdate();

            // TODO: Print skal fjernes på et tidspunkt.
            System.out.println("Worker have been added to: \t DB: myhours \tTable: " + WORKERS_TABLENAME);

        }
        catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        finally {
            // Return the Connection to the Pool
            connPool.releaseConnection(c);
        }
    }

    @Override
    public int updateWorker(IWorkerDTO worker) throws DALException
    {
        // Get connection from ConnPool
        Connection c = connPool.getConn();

        int rowsAltered;
        
        // The query to make
        String query = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = %d",
                WORKERS_TABLENAME, WorkerConstants.firstname, WorkerConstants.surname, WorkerConstants.email,
				WorkerConstants.birthday, WorkerConstants.password, WorkerConstants.id, worker.getWorkerID());

        try {

            PreparedStatement pStatement = c.prepareStatement(query);

            pStatement.setString(1, worker.getFirstName());
            pStatement.setString(2, worker.getSurName());
            pStatement.setString(3, worker.getEmail());
            pStatement.setDate(4, worker.getBirthday());
            pStatement.setString(5, worker.getPassword());

            rowsAltered = pStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            // Return the connection to ConnPool
            connPool.releaseConnection(c);
        }
        
        // Return the number of Rows effected by the update
        return rowsAltered;
    }

    @Override
    public void deleteWorker(String email) throws DALException
    {
        // Get connection from ConnPool
        Connection c = connPool.getConn();
        try {

            PreparedStatement pStatement =
                    c.prepareStatement("DELETE FROM " + WORKERS_TABLENAME + " WHERE " + WorkerConstants.email + " = ?");
            
            pStatement.setString(1, email);

            pStatement.executeUpdate();

        } catch (SQLException e ) {
            throw new DALException(e.getMessage());
        } finally {
            // Return connection to ConnPool
            connPool.releaseConnection(c);
        }
    }


    /*
    ---------------------- Support Methods ----------------------
     */
    
}
