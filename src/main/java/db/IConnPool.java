package db;

import java.sql.Connection;

public interface IConnPool {
	
	Connection getConn();
}
