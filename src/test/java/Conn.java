import db.IConnPool;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conn implements IConnPool {
	
	//region DB Constants
	
	private final String url = "jdbc:mysql://mysql26.unoeuro.com:3306/runningessentials_dk_db_myhours";
	private final String user = "runningesse_dk";
	
	//endregion
	
	public Connection getConn()
	{
		return null;
	}
}
