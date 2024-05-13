

package customer_ui;

import java.util.Scanner;
import orders.*;
import interfaces.PaymentMethod;
import interfaces.PaymentDetails;
import db.PaymentMethodDBController;
import java.util.List;
import java.util.ArrayList;

/**
Manages the payment process for customer orders.
This class presents available payment methods to the user, collects payment details, and processes the payment through the chosen payment method. It interacts with the PaymentMethodDBController to determine available payment options and with various payment classes to handle specific payment processes.
*/
public class PaymentHandler {

	private Scanner scanner;
    private PaymentMethodDBController paymentMethodDBController;
	
	private Cart cart;
	
	
	PaymentHandler(Scanner scanner, Cart cart, PaymentMethodDBController paymentMethodDBController){
		this.scanner = scanner;
		this.cart = cart;
        this.paymentMethodDBController = paymentMethodDBController;

	}
	
	public boolean processPayment() {

        if (cart.getItems().isEmpty()){
            System.out.println("Cart is Empty. Please add items to cart for purchase.");
            return false;
        }
		

        List<PaymentMethodEnum> paymentMethodEnums = new ArrayList<>();


		boolean paymentSuccessful = false;


		
		while (true) {
            int i = 1;
            System.out.println("Select payment method:");

            

            
            if (paymentMethodDBController.isAvailable(PaymentMethodEnum.CreditCardPayment)){
                System.out.printf("%d. Credit Card\n", i++);
                paymentMethodEnums.add(PaymentMethodEnum.CreditCardPayment);

            }

            if (paymentMethodDBController.isAvailable(PaymentMethodEnum.PaynowPayment)){
                System.out.printf("%d. Paynow \n", i++);
                paymentMethodEnums.add(PaymentMethodEnum.PaynowPayment);

            }

            if (paymentMethodDBController.isAvailable(PaymentMethodEnum.PayPal)) {
                System.out.printf("%d. PayPal \n", i++);
                paymentMethodEnums.add(PaymentMethodEnum.PayPal);
            }
    
            System.out.printf("%d. Quit\n", i);


            int index = scanner.nextInt();
            scanner.nextLine(); 

            if (index == i){
                System.out.println("Exiting payment menu.");
            	break;
            }

            PaymentMethodEnum choice = paymentMethodEnums.get(index-1);

            PaymentMethod paymentMethod = null;
            PaymentDetails paymentDetails = null;
            

            if (choice == PaymentMethodEnum.CreditCardPayment) {

                String cardNumber, expiryDate, cvv, name;
                boolean validDetails;

                do {
                    System.out.print("Enter your name: ");
                    name = scanner.nextLine();

                    System.out.print("Enter credit card number: ");
                    cardNumber = scanner.nextLine();

                    System.out.print("Enter expiry date (MM/YY): ");
                    expiryDate = scanner.nextLine();

                    System.out.print("Enter CVV: ");
                    cvv = scanner.nextLine();

                    paymentDetails = new CreditCardDetails(name, cardNumber, expiryDate, cvv);
                    validDetails = CreditCardDetails.validateDetails((CreditCardDetails) paymentDetails);

                    if (!validDetails) {
                        System.out.println("Invalid credit card details. Please try again.");
                    }
                } while (!validDetails);

                paymentMethod = new CreditCardPayment();
                         
                
            } else if (choice == PaymentMethodEnum.PaynowPayment) {
            	
            	paymentDetails = new PaynowDetails();
                paymentMethod = new PaynowPayment();
                
                
            } else if (choice == PaymentMethodEnum.PayPal) {
                String email;
                String password;
                boolean validDetails = false;

                do {
                    System.out.print("Enter your email: ");
                    email = scanner.nextLine();

                    System.out.print("Enter password: ");
                    password = scanner.nextLine();


                    paymentDetails = new PayPalDetails(email, password);
                    validDetails = ((PayPalDetails) paymentDetails).validatePayment();
                    if (!validDetails) {
                        System.out.println("Invalid PayPal details. Please try again.");
                    }
                } while (!validDetails);
                paymentMethod = new PayPal();
            	
            }
            
            else {
                System.out.println("Invalid choice. Please try again.");
                continue;
            }

            
            
            Payment payment = new Payment(cart);
            paymentSuccessful = payment.processPayment(paymentMethod, paymentDetails);

            if (paymentSuccessful) {
                System.out.println("Payment Successful");
            	break; 
            } else {
                System.out.println("Payment failed. Please try another payment method.");
            }
        }
		
		return paymentSuccessful;
	}
}
