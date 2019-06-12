package dao.job;

/**
 * @author Alfred Röttger Rydahl
 *
 * This class functions as an enum. It contains all column names,
 * and table name.
 * When names of columns or table is needed, then this class should
 * always be used.
 */
public class JobConstants
{
	public static final String TABLENAME 	= "Jobs";
	public static final String id 			= "id";
	public static final String employerID 	= "employerID";
	public static final String jobName 		= "jobName";
	public static final String hireDate		= "hireDate";
	public static final String finishDate	= "finishDate";
	public static final String salary		= "salary";
}
