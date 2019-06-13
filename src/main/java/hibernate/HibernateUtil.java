package hibernate;

import dao.worker.WorkerConstants;
import dto.worker.WorkerHiberDTO;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.MySQLDialect;

import java.util.Properties;
import java.util.TimeZone;

/**
 * @author Rasmus Sander Larsen
 */
public class HibernateUtil {

    /*
    -------------------------- Fields --------------------------
     */

    private SessionFactory sessionFactory;
    private Properties properties;
    private String url = "ec2-52-30-211-3.eu-west-1.compute.amazonaws.com";
    private String user = "s160107";
    private String password = "wOWElNh0bVravE9uGDNzw";
    private final int connectionPoolSize = 2;
    private final String driver_class = "com.mysql.jdbc.Driver";
    private boolean show_sql = true;

    /*
    ----------------------- Constructor -------------------------
     */

    public HibernateUtil (Properties properties) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // </editor-folder>

    /*
    ---------------------- Public Methods -----------------------
     */

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

        // Sets the program timezone to the same as the DB.
        //TimeZone.setDefault(TimeZone.getTimeZone(getTimeZoneFromDB())); TODO, skal muligvis fjernes!
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
