package dao.employer;

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

    /*
    ----------------------- Constructor -------------------------
     */

    public EmployerDAO(IConnPool iConnPool) {
        this.iConnPool = iConnPool;
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
                EmployerConstants.TABLENAME, EmployerConstants.id);

        try {

            PreparedStatement preparedStatement = c.prepareStatement(getQuery);
            preparedStatement.setInt(1,employerID);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                iEmployerDTOToReturn.setWorkplaceID(resultSet.getInt(EmployerConstants.id));
                iEmployerDTOToReturn.setWorkerID(resultSet.getInt(EmployerConstants.workerID));
                iEmployerDTOToReturn.setName(resultSet.getString(EmployerConstants.employerName));
                iEmployerDTOToReturn.setColor(Color.decode("#"+resultSet.getString(EmployerConstants.color)));
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
    public List<IEmployerDTO> getIWorkPlaceList() throws DALException {
        Connection c = iConnPool.getConn();

        List<IEmployerDTO> listToReturn = new ArrayList<>();

        try {

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT workplaceID FROM " + EmployerConstants.TABLENAME );

            while (resultSet.next()) {
                listToReturn.add(getIEmployer(resultSet.getInt("workplaceID")));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }

        return listToReturn;
    }

    @Override
    public List<IEmployerDTO> getIWorkPlaceList(int workerID) throws DALException {

        Connection c = iConnPool.getConn();

        List<IEmployerDTO> listToReturn = new ArrayList<>();

        try {

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT workplaceID FROM " + EmployerConstants.TABLENAME + " WHERE workerID = " + workerID);

            while (resultSet.next()) {
                listToReturn.add(getIEmployer(resultSet.getInt("workplaceID")));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }

        return listToReturn;
    }

    @Override
    public void createIWorkPlace(IEmployerDTO workPlaceDTO) throws DALException {

        Connection c = iConnPool.getConn();

        try {

            PreparedStatement pStatement = c.prepareStatement(
                    "INSERT INTO " + EmployerConstants.TABLENAME +
                            "(workerID, employerName, colorHEX, telephone) VALUES (?,?,?,?)");

            pStatement.setInt(1, workPlaceDTO.getWorkerID());
            pStatement.setString(2, workPlaceDTO.getName());
            pStatement.setString(3, colorHEXToString(workPlaceDTO.getColor()));
            pStatement.setString(4, workPlaceDTO.getTelephone());

            pStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }
    }

    @Override
    public void updateIWorkPlace(IEmployerDTO workPlaceDTO) throws DALException {

        Connection c = iConnPool.getConn();

        try {

            PreparedStatement pStatement = c.prepareStatement(
                    "UPDATE " + EmployerConstants.TABLENAME +
                    " SET workplaceName = ?, colorHEX = ?, telephone = ?" +
                    " WHERE workplaceID = ?");

            pStatement.setString(1, workPlaceDTO.getName());
            pStatement.setString(2, colorHEXToString(workPlaceDTO.getColor()));
            pStatement.setString(3, workPlaceDTO.getTelephone());
            pStatement.setInt(4, workPlaceDTO.getWorkplaceID());

            pStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }
    }

    @Override
    public void deleteIWorkPlace(int workplaceID) throws DALException {

        Connection c = iConnPool.getConn();

        String deleteQuery = String.format("DELETE FROM %s WHERE %s = ?",
                EmployerConstants.TABLENAME, EmployerConstants.id);

        try {

            PreparedStatement pStatement = c.prepareStatement(deleteQuery);
            pStatement.setInt(1, workplaceID);

            pStatement.executeUpdate();

        } catch (SQLException e ) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
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
