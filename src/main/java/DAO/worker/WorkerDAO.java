package DAO.worker;

import DAO.DALException;
import DTOs.worker.WorkerDTO;
import db.IConnPool;

import javax.management.QueryEval;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */
public class WorkerDAO implements IWorkerDAO {

    /*
    -------------------------- Fields --------------------------
     */
    
    private IConnPool connPool;
    private final String WORKERS_TABLENAME = "workers";
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public WorkerDAO(IConnPool connPool)
    {
        this.connPool = connPool;
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
    public WorkerDTO getWorker(String email) throws DALException {

        WorkerDTO workerToReturn = new WorkerDTO();

        try (Connection c = connPool.getConn()) {

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM " + WORKERS_TABLENAME + " WHERE " + Columns.email.toString() + " = '" + email + "'");

            while (resultSet.next()) {
                workerToReturn.setWorkerID(resultSet.getInt("workerid"));
                workerToReturn.setFirstName(resultSet.getString("firstname"));
                workerToReturn.setSurName(resultSet.getString("surname"));
                workerToReturn.setEmail(resultSet.getString("email"));
                workerToReturn.setBirthday(resultSet.getDate("birthday").toLocalDate());
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

        return workerToReturn;
    }

    @Override
    public List<WorkerDTO> getWorkerList() throws DALException
    {

        List<WorkerDTO> listToReturn = new ArrayList<>();

        try (Connection c = connPool.getConn()) {

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT " + Columns.email.toString() + " FROM " + WORKERS_TABLENAME);

            while (resultSet.next()) {
                listToReturn.add(getWorker(resultSet.getString(Columns.email.toString())));
            }

        }
        catch (SQLException e) {
            throw new DALException(e.getMessage());
        }

        return listToReturn;
    }

    @Override
    public void createWorker(WorkerDTO workerDTO, String password) throws DALException
    {
        // The query to make
        String query =
                String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?,?,?,?,?)",
                        WORKERS_TABLENAME, Columns.firstname.toString(), Columns.surname.toString(), Columns.email.toString(),
                        Columns.birthday.toString(), Columns.pass.toString());
                
        try (Connection c = connPool.getConn()) {

            PreparedStatement statement = c.prepareStatement(query);
            
            statement.setString(1, workerDTO.getFirstName());
            statement.setString(2, workerDTO.getSurName());
            statement.setString(3, workerDTO.getEmail());
            statement.setDate(4, Date.valueOf(workerDTO.getBirthday()));
            statement.setString(5, password);

            statement.executeUpdate();

            System.out.println("Worker have been added to: \t DB: myhours \tTable: " + WORKERS_TABLENAME);

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
    }

    @Override
    public int updateWorker(WorkerDTO worker, String password) throws DALException
    {
        int rowsAltered;
        
        // The query to make
        String query = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = %d",
                WORKERS_TABLENAME, Columns.firstname.toString(), Columns.surname.toString(), Columns.email.toString(),
                Columns.birthday.toString(), Columns.pass.toString(), Columns.workerId.toString(), worker.getWorkerID());

        try (Connection c = connPool.getConn()) {

            PreparedStatement pStatement = c.prepareStatement(query);

            pStatement.setString(1, worker.getFirstName());
            pStatement.setString(2, worker.getSurName());
            pStatement.setString(3, worker.getEmail());
            pStatement.setDate(4, Date.valueOf(worker.getBirthday()));
            pStatement.setString(5, password);

            rowsAltered = pStatement.executeUpdate();

        }
        catch (SQLException e) {
            throw new DALException(e.getMessage());
        }
        
        // Return the number of Rows effected by the update
        return rowsAltered;
    }

    @Override
    public void deleteWorker(String email) throws DALException
    {

        try (Connection c = connPool.getConn()) {

            PreparedStatement pStatement =
                    c.prepareStatement("DELETE FROM " + WORKERS_TABLENAME + " WHERE " + Columns.email.toString() + "=?");
            
            pStatement.setString(1, email);

            pStatement.executeUpdate();

        } catch (SQLException e ) {
            throw new DALException(e.getMessage());
        }
    }

    /*
    ---------------------- Support Methods ----------------------
     */
    
    private enum Columns {
    	workerId,
		firstname,
		surname,
		email,
		birthday,
		pass
	}
}
