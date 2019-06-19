package dao;

import db.IConnPool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Rasmus Sander Larsen
 */
public class ConnectionHelper {

    /*
    -------------------------- Fields --------------------------
     */
    
    private IConnPool iConnPool;
    
    /*
    ----------------------- Constructor -------------------------
     */

    public ConnectionHelper(IConnPool iConnPool) {
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

    public void finallyActionsForConnection (Connection c, String methodName) {
        try {
        	c.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("SQLException in finally in "+ methodName + " :");
            e.printStackTrace();
        }
        iConnPool.releaseConnection(c);
    }

    public void catchSQLExceptionAndDoRollback (Connection c, SQLException e, String methodName) throws DALException {
        try {
            System.err.println("Transaction is being rolled back. ");
            c.rollback();
        } catch (SQLException rollbackSQLException) {
            System.out.println("Rollback SQLException in " + methodName + " :");
            e.printStackTrace();
        }

        System.out.println("Standard SQLException in " + methodName + ":");
        throw  new DALException(e.getMessage(), e.getCause());
    }
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
