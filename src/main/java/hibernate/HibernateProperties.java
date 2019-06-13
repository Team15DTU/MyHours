package hibernate;

import java.util.Properties;

/**
 * @author Rasmus Sander Larsen
 */
public class HibernateProperties {

    /*
    -------------------------- Fields --------------------------
     */
    
    private Properties testDB;

    // Overall settings
    private final int connectionPoolSize = 2;

    // region Settings for testDB (Alfred DiplomPortalDB)

    private String test_url = "ec2-52-30-211-3.eu-west-1.compute.amazonaws.com";
    private String test_user = "s160107";
    private String test_password = "wOWElNh0bVravE9uGDNzw";
    private final String test_driver_class = "com.mysql.jdbc.Driver";
    private String dialect = "org.hibernate.dialect.MySQLDialect";
    private boolean test_show_sql = true;

    // endregion
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public HibernateProperties () {

    }

    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */
    
    public Properties getTestDB() {
        testDB = new Properties();
        // Database connection settings
        testDB.put("hibernate.connection.driver_class", test_driver_class);
        testDB.put("hibernate.connection.url", "jdbc:mysql://"+test_url+":3306/"+test_user);
        testDB.put("hibernate.connection.username", test_user);
        testDB.put("hibernate.connection.password", test_password);

        // SQL dialect
        testDB.put("hibernate.dialect", dialect);

        // JDBC connection pool (use the built-in)
        testDB.put("hibernate.connection.pool_size",connectionPoolSize);

        //Echo all executed SQL to stdout
        testDB.put("show_sql", test_show_sql);
        return testDB;
    }
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
