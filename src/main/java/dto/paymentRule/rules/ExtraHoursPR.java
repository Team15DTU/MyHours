package DTOs.paymentRule.rules;

import DTOs.paymentRule.PaymentRule;
import DTOs.activity.ActivityDTO;

/**
 * @author Rasmus Sander Larsen
 */
public class ExtraHoursPR extends PaymentRule {

    /*
    -------------------------- Fields --------------------------
     */
    
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    
    /*
    ------------------------ Properties -------------------------
     */
    
    
    /*
    ---------------------- Public Methods -----------------------
     */
	
	public double calPayOut(ActivityDTO shiftDTO)
	{
		
		double payOut = 1;
		
		return payOut;
	}
    
    /*
    ---------------------- Support Methods ----------------------
     */
}