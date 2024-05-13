

package interfaces;
/**
Interface for payment method classes.
This interface defines a method processPayment() that is responsible for processing payments using the provided payment details and amount.
*/
public interface PaymentMethod {
    boolean processPayment(double amount, PaymentDetails paymentDetails);
}