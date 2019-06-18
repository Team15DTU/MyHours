package hibernate;

import dto.worker.WorkerHiberDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

/**
 * @author Rasmus Sander Larsen
 */
public class HibernateUtil {

    /*
    -------------------------- Fields --------------------------
     */
    
    private HibernateUtil instance;
    private SessionFactory sessionFactory;
    private Properties properties;

    /*
    ----------------------- Constructor -------------------------
     */

    private HibernateUtil (Properties properties) {
        this.properties = properties;
    }
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    // </editor-folder>

    /*
    ---------------------- Public Methods -----------------------
     */
    
    /**
     * Gives the running instance of the HibernateUtil object.
     * This either creates an the instance if it isn't running, or
     * just returning the instance if it's already running. If the
     * object is already running, then it ignores the properties
     * parameter.
     * @param properties Properties object - Specifying which DB to use.
     * @return A HibernateUtil object.
     */
    public HibernateUtil getInstance (Properties properties)
    {
        try
        {
            if ( instance == null )
            {
				instance = new HibernateUtil(properties);
				instance.setup();
			}
        }
        catch ( Exception e )
        {
            System.err.println( String.format("ERROR: HibernateUtil getInstance() - %s", e.getMessage()) );
        }
        
        return instance;
    }

    public void setup() {
        Configuration configuration = new Configuration().setProperties(properties)
                .addAnnotatedClass(WorkerHiberDTO.class);

        //final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
        //        .configure() // configures settings from dao.cfg.xml
        //        .build();
        try {
            sessionFactory = configuration.buildSessionFactory();
            //sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            //StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public void exit() {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

    public Session getSession () {
        return sessionFactory.openSession();
    }

    public void closeSession (Session session) {
        session.close();
    }

    public void executeSQLQuery (String query) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createSQLQuery(query).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public void executeHQLQuery (String query) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery(query).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    /*
    ---------------------- Support Methods ----------------------
     */

    // Gets the timezone of the DB.
    public String getTimeZoneFromDB () {

        String hibernateDialect = (String) sessionFactory.getProperties().get("hibernate.dialect");
        Session session = getSession();

        // H2 database bruger hibernate.dialect = "org.hibernate.dialect.H2Dialect".

        if (hibernateDialect.equals("org.hibernate.dialect.H2Dialect")) {
            return (String) session.createSQLQuery("select '@@system_time_zone'").uniqueResult();
        } else {
            return (String) session.createSQLQuery("SELECT @@system_time_zone").uniqueResult();
        }
    }

}
