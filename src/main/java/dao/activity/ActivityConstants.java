package dao.activity;

/**
 * @author Alfred Röttger Rydahl
 *
 * This class functions as an enum. It contains all column names,
 * and table name.
 * When names of columns or table is needed, then this class should
 * always be used.
 */

public class ActivityConstants
{
	public static final String TABLENAME 		= "Activities";
	public static final String id 				= "id";
	public static final String jobID 			= "jobID";
	public static final String startDateTime 	= "startDateTime";
	public static final String endDateTime 		= "endDateTime";
	public static final String pause 			= "break";
	public static final String activityValue 	= "activityValue";
}
