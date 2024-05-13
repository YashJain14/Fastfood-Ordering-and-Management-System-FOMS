

package db;

import java.util.HashMap;


//hash map of payment method to boolean --> payment method and whether it is activated 
// return payment status then get payment method and return a boolean 
// 
/**
Represents the database of available payment methods.
This class stores information about which payment methods (e.g., CreditCard, PayNow, PayPal) are currently enabled for the application.
*/
public class PaymentMethodDB {
    HashMap <String, Integer> availablePayments = new HashMap<>();

    public HashMap <String, Integer> getAvailablePayment(){
        return availablePayments;
    }
    
    

}
