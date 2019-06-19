package dao.employer;

import dao.ConnectionHelper;
import dao.DALException;
import dto.employer.EmployerDTO;
import dto.employer.IEmployerDTO;

import db.IConnPool;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */

public class EmployerDAO implements IEmployerDAO {

    /*
    -------------------------- Fields --------------------------
     */

    private IConnPool iConnPool;
    private ConnectionHelper connectionHelper;

    /*
    ----------------------- Constructor -------------------------
     */

    public EmployerDAO(IConnPool iConnPool, ConnectionHelper connectionHelper) {
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
    public IEmployerDTO getIEmployer(int employerID)  throws DALException {

        Connection c = iConnPool.getConn();

        IEmployerDTO iEmployerDTOToReturn = new EmployerDTO();

        String getQuery = String.format("SELECT * FROM %s WHERE %s = ?",
                EmployerConstants.TABLENAME,
                EmployerConstants.id); // ParameterIndex 1

        try {

            PreparedStatement preparedStatement = c.prepareStatement(getQuery);
            preparedStatement.setInt(1,employerID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                iEmployerDTOToReturn.setEmployerID(resultSet.getInt(EmployerConstants.id));
                iEmployerDTOToReturn.setWorkerID(resultSet.getInt(EmployerConstants.workerID));
                iEmployerDTOToReturn.setName(resultSet.getString(EmployerConstants.employerName));

                if (resultSet.getString(EmployerConstants.color) != null) {
                    iEmployerDTOToReturn.setColor(Color.decode("#"+resultSet.getString(EmployerConstants.color)));
                } else {
                    iEmployerDTOToReturn.setColor(null);
                }

                iEmployerDTOToReturn.setTelephone(resultSet.getString(EmployerConstants.tlf));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }

        return iEmployerDTOToReturn;
    }

    @Override
    public List<IEmployerDTO> getiEmployerList() throws DALException {
        List<IEmployerDTO> listToReturn = new ArrayList<>();

        Connection c = iConnPool.getConn();

        // Finds all id's in the Employer table.
        String getAllEmployerIDQuery = String.format("SELECT %s FROM %s",
                EmployerConstants.id,
                EmployerConstants.TABLENAME );

        try {

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery(getAllEmployerIDQuery);

            // Gets and adds Employer objects from the result of the query.
            while (resultSet.next()) {
                listToReturn.add(getIEmployer(resultSet.getInt(EmployerConstants.id)));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }

        return listToReturn;
    }

    @Override
    public List<IEmployerDTO> getiEmployerList(int workerID) throws DALException {
        List<IEmployerDTO> listToReturn = new ArrayList<>();

        Connection c = iConnPool.getConn();

        // Gets all employer id's that belongs to the inputted workerID.
        String employerIDsOfWorkerIDQeury = String.format("SELECT %s FROM %s WHERE %s = ?",
                EmployerConstants.id,
                EmployerConstants.TABLENAME,
                EmployerConstants.workerID); // ParameterIndex 1

        try {
            // Sets parameters.
            PreparedStatement employerID_preparedStatement = c.prepareStatement(employerIDsOfWorkerIDQeury);
            employerID_preparedStatement.setInt(1,workerID);

            ResultSet resultSet = employerID_preparedStatement.executeQuery();

            // Gets and add all Employer's that belongs to inputted workerID, to list.
            while (resultSet.next()) {
                listToReturn.add(getIEmployer(resultSet.getInt(EmployerConstants.id)));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }

        // Returns list of iEmployerDTO objects, that all belongs to the inputted workerID.
        return listToReturn;
    }

    @Override
    public void createiEmployer(IEmployerDTO employerDTO) throws DALException {

        Connection c = iConnPool.getConn();

        String createQuery = String.format(
                "INSERT INTO %s (%s, %s, %s, %s) VALUES (?,?,?,?)",
                EmployerConstants.TABLENAME,
                EmployerConstants.workerID, // ParameterIndex 1
                EmployerConstants.employerName, // ParameterIndex 2
                EmployerConstants.color, // ParameterIndex 3
                EmployerConstants.tlf); // ParameterIndex 4

        try {
            c.setAutoCommit(false);

            PreparedStatement pStatement = c.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);

            pStatement.setInt(1, employerDTO.getWorkerID());
            pStatement.setString(2, employerDTO.getName());
            if (employerDTO.getColor() != null) {
                pStatement.setString(3, colorHEXToString(employerDTO.getColor()));
            } else {
                pStatement.setString(3, null);
            }
            if (employerDTO.getTelephone() != null){
                pStatement.setString(4, employerDTO.getTelephone());
            } else {
                pStatement.setString(4, null);
            }

            pStatement.executeUpdate();

            ResultSet generatedKeys = pStatement.getGeneratedKeys();

            while (generatedKeys.next()) {
                employerDTO.setEmployerID(generatedKeys.getInt(1));
            }

            c.commit();

        } catch (SQLException e) {
            connectionHelper.catchSQLExceptionAndDoRollback(c,e,"EmployerDAO.createiEmployer");
        } finally {
            connectionHelper.finallyActionsForConnection(c, "EmployerDAO.createiEmployer");
        }
    }

    @Override
    public void updateiEmployer(IEmployerDTO employerDTO) throws DALException {

        Connection c = iConnPool.getConn();

        String updateQuery = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?",
                EmployerConstants.TABLENAME,
                EmployerConstants.employerName,
                EmployerConstants.color,
                EmployerConstants.tlf,
                EmployerConstants.id);

        try {
            c.setAutoCommit(false);

            PreparedStatement pStatement = c.prepareStatement(updateQuery);

            pStatement.setString(1, employerDTO.getName());

            if (employerDTO.getColor() != null){
                pStatement.setString(2, colorHEXToString(employerDTO.getColor()));
            } else {
                pStatement.setString(2, null);
            }
            if (employerDTO.getTelephone() != null){
                pStatement.setString(3, employerDTO.getTelephone());
            } else {
                pStatement.setString(3, null);
            }

            pStatement.setInt(4, employerDTO.getEmployerID());

            pStatement.executeUpdate();

            c.commit();

        } catch (SQLException e) {
            connectionHelper.catchSQLExceptionAndDoRollback(c,e,"EmployerDAO.updateiEmployer");
        } finally {
            connectionHelper.finallyActionsForConnection(c,"EmployerDAO.updateiEmployer");
        }
    }

    @Override
    public void deleteiEmployer(int employerID) throws DALException {

        Connection c = iConnPool.getConn();

        String deleteQuery = String.format("DELETE FROM %s WHERE %s = ?",
                EmployerConstants.TABLENAME, EmployerConstants.id);

        try {
            c.setAutoCommit(false);

            PreparedStatement pStatement = c.prepareStatement(deleteQuery);
            pStatement.setInt(1, employerID);

            pStatement.executeUpdate();
            c.commit();

        } catch (SQLException e ) {
            connectionHelper.catchSQLExceptionAndDoRollback(c,e,"EmployerDAO.deleteiEmployer");
        } finally {
            connectionHelper.finallyActionsForConnection(c,"EmployerDAO.deleteiEmployer");
        }

    }

    /*
    ---------------------- Support Methods ----------------------
     */

    private String colorHEXToString (Color colour) {

        String hexColour = Integer.toHexString(colour.getRGB() & 0xffffff);
            if (hexColour.length() < 6) {
                hexColour = "000000".substring(0, 6 - hexColour.length()) + hexColour;
            }

        return hexColour;

    }
}