import DAO.DALException;
import DAO.worker.IWorkerDAO;
import DAO.worker.WorkerDAO;
import DTOs.worker.IWorkerDTO;
import db.DBController;
import DTOs.worker.WorkerDTO;
import db.IConnPool;
import db.IDBController;
import db.MySQL_DB;
import db.connectionPools.ConnPoolV1;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.TimeZone;

public class Main {

    public static void main(String[] args) throws DALException
    {
        // This is the MySQL DB Controller which accesses the DB an executes all actions.
        IDBController dbController = DBController.getInstance(ConnPoolV1.getInstance());
    }
}
