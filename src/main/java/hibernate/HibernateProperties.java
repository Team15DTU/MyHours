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
    private Properties realDB;
    private Properties xamppRSL_DB;
    private Properties h2DB;

    // Overall settings
    private final int global_ConnectionPoolSize = 2;

    // region Settings for testDB (Alfred DiplomPortalDB)

    private String test_url = "ec2-52-30-211-3.eu-west-1.compute.amazonaws.com";
    private String test_dbName = "s160107";
    private String test_user = "s160107";
    private String test_password = "wOWElNh0bVravE9uGDNzw";
    private final String test_driver_class = "com.mysql.jdbc.Driver";
    private String test_dialect = "org.hibernate.dialect.MySQLDialect";
    private boolean test_show_sql = true;

    // endregion

    // region Settings for realDB (Rasmus DiplomPortalDB)

    private String real_url = "ec2-52-30-211-3.eu-west-1.compute.amazonaws.com";
    private String real_dbName = "s185097";
    private String real_user = "s185097";
    private String real_password = "qsNAphOJ13ySzlpn1kh6Y";
    private final String real_driver_class = "com.mysql.jdbc.Driver";
    private String real_dialect = "org.hibernate.dialect.MySQLDialect";
    private boolean real_show_sql = true;

    // endregion

    // region Settings for XamppRSL_DB (XAMPP Rasmus Local DB)

    private String xampp_full_url = "127.0.0.1";
    private String xampp_dbName = "Myhours";
    private String xampp_user = "root";
    private String xampp_password = "test";
    private final String xampp_driver_class = "com.mysql.jdbc.Driver";
    private String xampp_dialect = "org.hibernate.dialect.MySQLDialect";
    private boolean xampp_show_sql = true;

    // endregion

    // region Settings for H2DB (H2Database)

    private String h2_full_url = "jdbc:h2:mem:db1;DB_CLOSE_DELAY=-1;MVCC=TRUE";
    private String h2_user = "sa";
    private String h2_password = "test";
    private final String h2_driver_class = "org.h2.Driver";
    private String h2_dialect = "org.hibernate.dialect.H2Dialect";
    private String h2_cacheProvider = "org.hibernate.cache.internal.NoCacheProvider";
    private String h2_hbm2ddl_auto = "create";
    private boolean h2_show_sql = true;

    // endregion



    /*
    ----------------------- Constructor -------------------------
     */

    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"

    public Properties getTestDB() {

        testDB = createStdProperties(
                test_driver_class,
                test_url,
                test_dbName,
                test_user,
                test_password,
                test_dialect,
                global_ConnectionPoolSize,
                test_show_sql
        );

        return testDB;
    }

    public Properties getRealDB() {
        realDB = createStdProperties(
                real_driver_class,
                real_url,
                real_dbName,
                real_user,
                real_password,
                real_dialect,
                global_ConnectionPoolSize,
                real_show_sql
        );

        return realDB;
    }

    public Properties getXamppRSL_DB () {
        xamppRSL_DB = createStdProperties(
                xampp_driver_class,
                xampp_full_url,
                xampp_dbName,
                xampp_user,
                xampp_password,
                xampp_dialect,
                global_ConnectionPoolSize,
                xampp_show_sql
        );

        return xamppRSL_DB;
    }

    public Properties getH2DB () {
        h2DB = createH2Properties(h2_driver_class,
                h2_full_url,
                h2_user,
                h2_password,
                h2_dialect,
                h2_cacheProvider,
                global_ConnectionPoolSize,
                h2_hbm2ddl_auto,
                h2_show_sql);

        return h2DB;
    }

    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */
    

    
    /*
    ---------------------- Support Methods ----------------------
     */

    private Properties createStdProperties (String driver_class,String url, String dbName, String user, String password, String dialect,
                                      int connectionPoolSize, boolean show_sql){
        Properties properties = new Properties();
        // Database connection settings
        properties.put("hibernate.connection.driver_class", driver_class);
        properties.put("hibernate.connection.url", "jdbc:mysql://"+url+":3306/"+dbName);
        properties.put("hibernate.connection.username", user);
        properties.put("hibernate.connection.password", password);

        // SQL dialect
        properties.put("hibernate.dialect", dialect);

        // JDBC connection pool (use the built-in)
        properties.put("hibernate.connection.pool_size", connectionPoolSize);

        //Echo all executed SQL to stdout
        properties.put("show_sql", show_sql);

        return properties;
    }

    private Properties createH2Properties (String driver_class,String full_url, String user, String password, String dialect,
                                           String cacheProvider, int connectionPoolSize, String hbm2ddl_auto, boolean show_sql){
        Properties properties = new Properties();
        // Database connection settings
        properties.put("hibernate.connection.driver_class", driver_class);
        properties.put("hibernate.connection.url", full_url);
        properties.put("hibernate.connection.username", user);
        properties.put("hibernate.connection.password", password);

        // SQL dialect
        properties.put("hibernate.dialect", dialect);

        // Disable the second-level cache
        properties.put("cache.provider_class",cacheProvider);

        // JDBC connection pool (use the built-in)
        properties.put("hibernate.connection.pool_size", connectionPoolSize);

        // Drop and re-create the database schema on startup
        properties.put("hbm2ddl.auto",hbm2ddl_auto);

        //Echo all executed SQL to stdout
        properties.put("show_sql", show_sql);

        return properties;
    }

}
