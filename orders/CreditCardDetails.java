

package orders;

import interfaces.PaymentDetails;
/**
Stores the credit card details for payments.
This class holds information such as the cardholder's name, card number, expiry date, and CVV. It also provides methods to validate the format of the credit card details.
*/
public class CreditCardDetails implements PaymentDetails{
    private String name;
    private String cardNumber;
    private String expiryDate;
    private String cvv;

    public CreditCardDetails(String name, String cardNumber, String expiryDate, String cvv) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    public String getName() {
        return name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public static boolean validateDetails(CreditCardDetails cardDetails) {
        // Credit card number validation (assuming 16 digits)
        if (cardDetails.getCardNumber().length() != 16) {
            return false;
        }

        String expiryDate = cardDetails.getExpiryDate();
        if (expiryDate.length() != 5 || !expiryDate.matches("\\d{2}/\\d{2}")) {
            return false;
        }

        if (cardDetails.getCvv().length() != 3) {
            return false;
        }
        return true;
    }
}