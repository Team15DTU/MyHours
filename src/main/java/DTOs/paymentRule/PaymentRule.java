package DTOs.paymentRule;

import DTOs.shift.ShiftDTO;

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

    // <editor-folder desc="Properties"


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */
    
    protected abstract double calPayOut(ShiftDTO shift);
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
