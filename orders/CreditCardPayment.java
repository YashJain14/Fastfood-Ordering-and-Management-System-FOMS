
package orders;

import interfaces.PaymentDetails;
import interfaces.PaymentMethod;
/**
Implements the credit card payment method.
This class processes payments using credit card details and returns a boolean indicating whether the payment was successful.
*/

public class CreditCardPayment implements PaymentMethod {
    
	@Override
    public boolean processPayment(double amount, PaymentDetails paymentDetails) throws NullPointerException{
    	
    	if (paymentDetails == null) {
            throw new NullPointerException("Credit card details are required for credit card payment.");
        }
    	
    	if (paymentDetails instanceof CreditCardDetails) {
    		return true;

    	}
    	else {
    		throw new NullPointerException("Error: Programmer Skill Issue");
	}
    	}
    	
    
  
}