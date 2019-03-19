package DTOs.paymentRule.rules;

import DTOs.paymentRule.PaymentRule;
import DTOs.shift.ShiftDTO;

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

    // <editor-folder desc="Properties"


    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */
    
    public double calPayOut(ShiftDTO shiftDTO) {

        double payOut = 1;

        return payOut;
    }
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
