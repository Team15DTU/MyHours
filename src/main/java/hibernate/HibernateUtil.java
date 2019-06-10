package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

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


}
