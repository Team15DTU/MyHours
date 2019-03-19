import db.DBController;
import DTOs.worker.WorkerDTO;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {



        final String workerTableName = "workers";

        WorkerDTO test = new WorkerDTO();
        test.setFirstName("Rasmus");
        test.setSurName("Larsen");
        //TODO: Mailen skal ændres hver gang programmet køres, fordi mailen er primaryKey og derfor unik!
        test.setEmail("test3@test.dk");
        test.setBirthday(LocalDate.parse("1992-01-06"));

        DBController dbController = new DBController();
        dbController.insertWorkerDTOInto(workerTableName, test);

    }
}
