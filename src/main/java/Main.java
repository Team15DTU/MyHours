import dao.DALException;

import db.DBController;
import db.IDBController;
import db.connectionPools.ConnPoolV1;

public class Main {

    public static void main(String[] args) throws DALException
    {
        // This is the MySQL DB Controller which accesses the DB and executes all actions.
        IDBController dbController = DBController.getInstance();
    }
}
