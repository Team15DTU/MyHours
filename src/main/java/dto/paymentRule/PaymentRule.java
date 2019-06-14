package dto.paymentRule;

import dto.activity.ActivityDTO;

/**
 * @author Rasmus Sander Larsen
 */
public abstract class PaymentRule  {

    /*
    -------------------------- Fields --------------------------
     */

	protected String ruleName;

    /*
    ----------------------- Constructor -------------------------
     */


    /*
    ------------------------ Properties -------------------------
     */


    /*
    ---------------------- Public Methods -----------------------
     */

	protected abstract double calPayOut(ActivityDTO shift);
    
    /*
    ---------------------- Support Methods ----------------------
     */
}