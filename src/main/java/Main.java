import DAO.worker.WorkerDAO;
import db.DBController;
import DTOs.worker.WorkerDTO;
import db.MySQL_DB;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        // TODO: Indsæt den rigtige kode til DB og slet den igen før du committer.
        MySQL_DB mySQL_db = new MySQL_DB("PASSWORD");

        DBController dbController = new DBController(mySQL_db);

        WorkerDTO testWorkerDTO = new WorkerDTO();
        testWorkerDTO.setFirstName("Test");
        testWorkerDTO.setSurName("Test");
        //TODO: Mailen skal ændres hver gang programmet køres, fordi mailen er primaryKey og derfor unik!
        testWorkerDTO.setEmail("test@testWorkerDTO.dk");
        testWorkerDTO.setBirthday(LocalDate.parse("1992-01-06"));
        System.out.println(testWorkerDTO);

        WorkerDAO workerDAO = new WorkerDAO(dbController);

        workerDAO.createWorker(testWorkerDTO, "password");


    }
}
