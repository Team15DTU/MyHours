import DAO.DALException;
import db.DBController;
import db.IConnPool;
import db.IDBController;
import db.MySQL_DB;

public class Main {

    public static void main(String[] args) throws DALException {

        // This is the MySQL DB that the program is running on.
        IConnPool iConnPool = new MySQL_DB();

        // This is the MySQL DB Controller which accesses the DB an executes all actions.
        IDBController idbController = new DBController(iConnPool);

    }
}
