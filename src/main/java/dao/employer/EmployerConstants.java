package dao.employer;

/**
 * @author Alfred Röttger Rydahl
 *
 * This class functions as an enum. It contains all column names,
 * and table name.
 * When names of columns or table is needed, then this class should
 * always be used.
 */
public class EmployerConstants
{
	public static final String TABLENAME 	= "Employers";
	public static final String id 			= "id";
	public static final String workerID 	= "WorkerID";
	public static final String employerName = "employerName";
	public static final String color 		= "colorHEX";
	public static final String tlf 			= "telephone";
}
