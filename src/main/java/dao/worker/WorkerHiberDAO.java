package dao.worker;

import dao.DALException;
import dto.worker.IWorkerDTO;
import hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

/**
 * @author Rasmus Sander Larsen
 */

//TODO: getWorker(int id) needs implementation!

public class WorkerHiberDAO implements IWorkerDAO {

    /*
    -------------------------- Fields --------------------------
     */
    
    private HibernateUtil hibernateUtil;
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public WorkerHiberDAO(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
    }
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */

    /**
     * This Method inserts the details from the inputted WorkerDTO into the MYSQL_DB
     *
     * @param workerDTO   This is the object containing the details to pass into the DB.
     * @throws DALException Will throw a DALException.
     */
    @Override
    public void createWorker(IWorkerDTO workerDTO) throws DALException {
        //TODO: Diverse interfaces skal gentænkes!

        Session session = null;

        try {
            session = hibernateUtil.getSession();

            session.beginTransaction();

            session.save(workerDTO);

            session.getTransaction().commit();

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            // Closes session.
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * This Methods should return a WorkerDTO object, containing all the details matching the inputted email
     *
     * @param email this is the email the methods creates a WorkerDTO from.
     * @return a WorkerDTO containing the correct details.
     * @throws DALException Will throw a DALException.
     */
    @Override
    public IWorkerDTO getWorker(String email) throws DALException {
        IWorkerDTO workerDTOToReturn = null;

        // now lets pull events from the database and list them
        Session session = hibernateUtil.getSession();

        // Query for getting WorkerHiberDTO by email
        Query getQuery = session.createQuery("from WorkerHiberDTO where email = :email");
        getQuery.setParameter("email", email);
        // IWorkerDTO retrieved.
        workerDTOToReturn = (IWorkerDTO) getQuery.uniqueResult();

        // Close Session TODO: Kan også bare gøres med "session.close".
        hibernateUtil.closeSession(session);

        return workerDTOToReturn;
    }

    /**
     * This Method returns a List of WorkerDTO objects.
     *
     * @return a List<WorkerDTO>
     * @throws DALException Will throw a DALException.
     */
    @Override
    public List<IWorkerDTO> getWorkerList() throws DALException {
        // now lets pull events from the database and list them
        Session session = hibernateUtil.getSession();
        session.beginTransaction();

        List<IWorkerDTO> result = session.createQuery( "from WorkerHiberDTO", IWorkerDTO.class).list();

        session.getTransaction().commit();
        session.close();

        return result;
    }

    /**
     * This Method finds the Worker matching the WorkerDTO object that is inputted
     * and updates the existing details in the DB with the information from the inputted WorkerDTO.
     *
     * @param workerDTO   This object contains the details that DB should be updated with.
     * @return Will return the number of rows affected.
     * @throws DALException Will throw a DALException.
     */
    @Override
    public int updateWorker(IWorkerDTO workerDTO) throws DALException {
        int changes = 0;
        Session session = hibernateUtil.getSession();
        session.beginTransaction();

        session.createQuery("update WorkerHiberDTO as w " +
                "set w.email = :email," +
                " w.password = :password," +
                " w.firstName = :firstname," +
                " w.surName = :surname where w.workerID = :workerID")
                .setParameter("email", workerDTO.getEmail())
                .setParameter("firstname", workerDTO.getFirstName())
                .setParameter("surname", workerDTO.getSurName())
                .setParameter("password",workerDTO.getPassword())
                .setParameter("workerID", workerDTO.getWorkerID())
                .executeUpdate();

        session.getTransaction().commit();
        session.close();

        return changes;
    }

    /**
     * This method should delete all information about a specific Worker matching the inputted email.
     *
     * @param email This is the email matching the Worker we wishes to delete.
     * @throws DALException Will throw a DALException.
     */
    @Override
    public void deleteWorker(String email) throws DALException {

        Session session = hibernateUtil.getSession();
        session.beginTransaction();
        session.createQuery("delete WorkerHiberDTO where email = :email")
                .setParameter("email",email).executeUpdate();
        session.getTransaction().commit();
        session.close();

    }
    
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
