import DAO.DALException;
import DAO.worker.WorkerHiberDAO;
import dto.worker.IWorkerDTO;
import dto.worker.WorkerHiberDTO;
import hibernate.HibernateUtil;

import java.util.List;

public class MainHiberWorker {

    public static void main(String[] args) throws DALException {

        // This is the MySQL DB that the program is running on.
        HibernateUtil hibernateUtil = new HibernateUtil();
        hibernateUtil.setup();

        WorkerHiberDAO workerHiberDAO = new WorkerHiberDAO(hibernateUtil);

        IWorkerDTO workerHiberDTO = workerHiberDAO.getWorker("test@test.dk");;

        System.out.println(workerHiberDTO);

        IWorkerDTO testInsertWorker = new WorkerHiberDTO("Firstname", "Surname", "Email_2", "pass");

        //workerHiberDAO.createWorker(testInsertWorker, "Ligemeget");

        List<IWorkerDTO> workerList = workerHiberDAO.getWorkerList();
        System.out.println("WorkerDTO LIST:");
        for (IWorkerDTO workerDTO : workerList) {
            System.out.println(workerDTO);
        }

        hibernateUtil.exit();

    }
}
