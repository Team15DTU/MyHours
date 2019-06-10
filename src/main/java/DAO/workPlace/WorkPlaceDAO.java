package DAO.workPlace;

import DAO.DALException;
import dto.workPlace.IWorkPlaceDTO;
import dto.workPlace.WorkPlaceDTO;

import db.IConnPool;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */

public class WorkPlaceDAO implements IWorkPlaceDAO {

    /*
    -------------------------- Fields --------------------------
     */

    private IConnPool iConnPool;
    private final String WORKPLACES_TABLENAME = "Workplaces";

    /*
    ----------------------- Constructor -------------------------
     */

    public WorkPlaceDAO (IConnPool iConnPool) {
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
    public IWorkPlaceDTO getIWorkPlace(int workplaceID)  throws DALException {
        Connection c = iConnPool.getConn();

        IWorkPlaceDTO workPlaceToReturn = new WorkPlaceDTO();

        try {

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM " + WORKPLACES_TABLENAME + " WHERE workplaceID = '" + workplaceID + "'");

            while (resultSet.next()) {
                workPlaceToReturn.setWorkplaceID(resultSet.getInt("workplaceID"));
                workPlaceToReturn.setWorkerID(resultSet.getInt("workerID"));
                workPlaceToReturn.setName(resultSet.getString("workplaceName"));
                workPlaceToReturn.setColor(Color.decode("#"+resultSet.getString("colorHEX")));
                workPlaceToReturn.setTelephone(resultSet.getInt("telephone"));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }

        return workPlaceToReturn;
    }

    @Override
    public List<IWorkPlaceDTO> getIWorkPlaceList() throws DALException {
        Connection c = iConnPool.getConn();

        List<IWorkPlaceDTO> listToReturn = new ArrayList<>();

        try {

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT workplaceID FROM " + WORKPLACES_TABLENAME );

            while (resultSet.next()) {
                listToReturn.add(getIWorkPlace(resultSet.getInt("workplaceID")));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }

        return listToReturn;
    }

    @Override
    public List<IWorkPlaceDTO> getIWorkPlaceList(int workerID) throws DALException {

        Connection c = iConnPool.getConn();

        List<IWorkPlaceDTO> listToReturn = new ArrayList<>();

        try {

            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT workplaceID FROM " + WORKPLACES_TABLENAME + " WHERE workerID = " + workerID);

            while (resultSet.next()) {
                listToReturn.add(getIWorkPlace(resultSet.getInt("workplaceID")));
            }

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }

        return listToReturn;
    }

    @Override
    public void createIWorkPlace(IWorkPlaceDTO workPlaceDTO) throws DALException {

        Connection c = iConnPool.getConn();

        try {

            PreparedStatement pStatement = c.prepareStatement(
                    "INSERT INTO " + WORKPLACES_TABLENAME +
                            "(workerID, workplaceName, colorHEX, telephone) VALUES (?,?,?,?)");

            pStatement.setInt(1, workPlaceDTO.getWorkerID());
            pStatement.setString(2, workPlaceDTO.getName());
            pStatement.setString(3, colorHEXToString(workPlaceDTO.getColor()));
            pStatement.setInt(4, workPlaceDTO.getTelephone());

            pStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DALException(e.getMessage());
        } finally {
            iConnPool.releaseConnection(c);
        }
    }

    @Override
    public void updateIWorkPlace(IWorkPlaceDTO workPlaceDTO) throws DALException {

        Connection c = iConnPool.getConn();

        try {

            PreparedStatement pStatement = c.prepareStatement(
                    "UPDATE " + WORKPLACES_TABLENAME +
                    " SET workplaceName = ?, colorHEX = ?, telephone = ?" +
                    " WHERE workplaceID = ?");

            pStatement.setString(1, workPlaceDTO.getName());
            pStatement.setString(2, colorHEXToString(workPlaceDTO.getColor()));
            pStatement.setInt(3, workPlaceDTO.getTelephone());
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

        try {

            PreparedStatement pStatement = c.prepareStatement("DELETE FROM " + WORKPLACES_TABLENAME + " WHERE workplaceID = ?");
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
