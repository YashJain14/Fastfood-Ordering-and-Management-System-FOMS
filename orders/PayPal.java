

package orders;

import interfaces.PaymentDetails;
import interfaces.PaymentMethod;
/**
Implements the PayPal payment method.
This class processes payments using PayPal details and returns a boolean indicating whether the payment was successful.
*/
public class PayPal implements PaymentMethod{


    public boolean processPayment(double amount, PaymentDetails paymentDetails) throws NullPointerException{
    	
    	if (paymentDetails == null) {
            throw new NullPointerException("Email details are required for Paypal payment.");
        }
    	
    	if (paymentDetails instanceof PayPalDetails) {
    		return true;

    	}
    	else {
    		throw new NullPointerException("Error: Programmer Skill Issue");
	}
    	}

   
}
