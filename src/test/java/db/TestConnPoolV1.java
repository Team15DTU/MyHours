package db;

import dao.DALException;
import db.connectionPools.ConnPoolV1;

/**
 * @author Alfred Röttger Rydahl
 */
public class TestConnPoolV1 extends ConnPoolV1 implements IConnPool
{
    /**
     * Only purpose is to make it possible to use this constructor,
     * as it isn't possible to use superclass constructor otherwise.
     * @throws DALException Data Access Layer Exception
     */
    private TestConnPoolV1() throws DALException { super(); }
    
    /**
     * Gives the instance of the Test Connection Pool, which is using
     * another database for testing.
     * @return ConnPoolV1 object
     * @throws DALException Data Access Layer Exception
     */
    public synchronized static ConnPoolV1 getInstance() throws DALException
    {
        // DiplomPortal Database - Alfred (TEST DB)
        password = "wOWElNh0bVravE9uGDNzw";
        user = "s160107";
        url = "ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s160107?";
        
        try
        {
            if (instance == null)
                instance = new TestConnPoolV1();
            
            return instance;
        }
        catch (DALException e)
        {
            System.err.println("ERROR: Couldn't get ConnPoolV1 instance");
            throw e;
        }
    }
}