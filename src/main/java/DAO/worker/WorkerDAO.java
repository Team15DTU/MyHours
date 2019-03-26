package DAO.worker;

import DTOs.worker.WorkerDTO;
import db.DBController;
import db.IConnPool;

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
    public WorkerDTO getWorker(String email) {

        WorkerDTO workerToReturn = new WorkerDTO();

        try (Connection c = connPool.getConn()) {

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM " + WORKERS_TABLENAME + " WHERE email = '" + email + "'");

            while (resultSet.next()) {
                workerToReturn.setWorkerID(resultSet.getInt("workerid"));
                workerToReturn.setFirstName(resultSet.getString("firstname"));
                workerToReturn.setSurName(resultSet.getString("surname"));
                workerToReturn.setEmail(resultSet.getString("email"));
                workerToReturn.setBirthday(resultSet.getDate("birthday").toLocalDate());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workerToReturn;
    }

    @Override
    public List<WorkerDTO> getWorkerList() {

        List<WorkerDTO> listToReturn = new ArrayList<>();

        try (Connection c = connPool.getConn()) {

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT email FROM " + WORKERS_TABLENAME);

            while (resultSet.next()) {
                listToReturn.add(getWorker(resultSet.getString("email")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listToReturn;
    }

    @Override
    public void createWorker(WorkerDTO workerDTO, String password) {

        try (Connection c = connPool.getConn()) {

            PreparedStatement statement =
                    c.prepareStatement("INSERT INTO "+ WORKERS_TABLENAME +" (firstname, surname, email, birthday, pass) VALUES (?,?,?,?,?)");
            statement.setString(1, workerDTO.getFirstName());
            statement.setString(2, workerDTO.getSurName());
            statement.setString(3, workerDTO.getEmail());
            statement.setDate(4, Date.valueOf(workerDTO.getBirthday()));
            statement.setString(5, password);

            statement.execute();

            System.out.println("Worker have been added to: \t DB: myhours \tTable: " + WORKERS_TABLENAME);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateWorker(WorkerDTO worker, String password) {

        try (Connection c = connPool.getConn()) {

            PreparedStatement pStatement = c.prepareStatement(
                    "UPDATE " + WORKERS_TABLENAME +
                            " SET firstname = ?, surname = ?, email = ?, birthday = ?, pass = ?" +
                            "WHERE workerid = '" + worker.getWorkerID() + "'");

            pStatement.setString(1, worker.getFirstName());
            pStatement.setString(2, worker.getSurName());
            pStatement.setString(3, worker.getEmail());
            pStatement.setDate(4, Date.valueOf(worker.getBirthday()));
            pStatement.setString(5, password);

            pStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteWorker(String email) {

        try (Connection c = connPool.getConn()) {

            PreparedStatement pStatement = c.prepareStatement("DELETE FROM " + WORKERS_TABLENAME + " WHERE email =?");
            pStatement.setString(1, email);

            pStatement.executeUpdate();

        } catch (SQLException e ) {
            e.printStackTrace();
        }
    }

    /*
    ---------------------- Support Methods ----------------------
     */
    
    
}
