package dto.paymentRule.rules;

import dto.paymentRule.PaymentRule;
import dto.shift.ShiftDTO;

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
	
	public double calPayOut(ShiftDTO shiftDTO)
	{
		
		double payOut = 1;
		
		return payOut;
	}
    
    /*
    ---------------------- Support Methods ----------------------
     */
}