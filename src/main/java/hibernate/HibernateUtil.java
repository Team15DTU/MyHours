package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.TimeZone;

/**
 * @author Rasmus Sander Larsen
 */
public class HibernateUtil {

    /*
    -------------------------- Fields --------------------------
     */

    private SessionFactory sessionFactory;

    /*
    ----------------------- Constructor -------------------------
     */


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


    // </editor-folder>

    /*
    ---------------------- Public Methods -----------------------
     */

    public void setup() {

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from dao.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            StandardServiceRegistryBuilder.destroy(registry);
        }

        // Sets the program timezone to the same as the DB.
        TimeZone.setDefault(TimeZone.getTimeZone(getTimeZoneFromDB()));
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
    
    /*
    ---------------------- Support Methods ----------------------
     */

    // Gets the timezone of the DB.
    private String getTimeZoneFromDB () {
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
