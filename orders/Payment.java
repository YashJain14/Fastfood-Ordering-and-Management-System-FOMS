

package orders;

import interfaces.PaymentDetails;
import interfaces.PaymentMethod;

// import db.BranchDBController;
/**
Handles the processing of payments for orders.
This class interacts with PaymentMethod and PaymentDetails objects to process payments through different payment methods, such as credit card and PayNow.
*/
public class Payment {
	
	private Cart cart;
	
	public Payment(Cart c) {
		cart = c;
	}
		

	
	
    public boolean processPayment(PaymentMethod paymentMethod, PaymentDetails paymentDetails) throws UnsupportedOperationException{
            
    	double totalAmount = cart.getTotalCost();
        boolean paymentSuccessful;
       
    	System.out.println("Processing Paynow payment of $" + cart.getTotalCost());

        paymentSuccessful = paymentMethod.processPayment(totalAmount, paymentDetails);
        
        return paymentSuccessful;

    }
    
    
    
    
    
 
    
}