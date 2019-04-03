import DAO.DALException;
import DAO.worker.WorkerDAO;
import db.DBController;
import DTOs.worker.WorkerDTO;
import db.MySQL_DB;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.TimeZone;

public class Main {

    public static void main(String[] args) throws DALException {


        /*
        MySQL_DB mySQL_db = new MySQL_DB();

        DBController dbController = new DBController(mySQL_db);

        WorkerDTO testWorkerDTO = new WorkerDTO();
        testWorkerDTO.setFirstName("Test1");
        testWorkerDTO.setSurName("Test1");
        //TODO: Mailen skal ændres hver gang programmet køres, fordi mailen er primaryKey og derfor unik!
        testWorkerDTO.setEmail("test1@testWorkerDTO.dk");
        testWorkerDTO.setBirthday(LocalDate.parse("1992-01-06"));
        System.out.println(testWorkerDTO);

        WorkerDAO workerDAO = new WorkerDAO(mySQL_db);
        // workerDAO.createWorker(testWorkerDTO,"testtest");

        WorkerDTO beforeUpdate = workerDAO.getWorker("test1@testWorkerDTO.dk");
        System.out.println("BEFORE:");
        System.out.println(beforeUpdate);
        beforeUpdate.setFirstName("Mikkel");
        workerDAO.updateWorker(beforeUpdate,"testtest");

        System.out.println("AFTER:");
        System.out.println(workerDAO.getWorker("test1@testWorkerDTO.dk"));

        System.out.println("List size = " + workerDAO.getWorkerList().size());

        workerDAO.deleteWorker("test1@testWorkerDTO.dk");

        System.out.println("List size = " + workerDAO.getWorkerList().size());

        */


    }
}
