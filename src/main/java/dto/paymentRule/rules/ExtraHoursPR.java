package dto.paymentRule.rules;

import dto.paymentRule.PaymentRule;
import dto.activity.ActivityDTO;

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