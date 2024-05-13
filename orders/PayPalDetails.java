
package orders;

import java.util.regex.Pattern;
import interfaces.PaymentDetails;
/**
Stores the email and password for PayPal payments.
This class provides methods to validate the email and password format for PayPal transactions.
*/
public class PayPalDetails implements PaymentDetails{

    private String email;
    private String password;

    
    public PayPalDetails(String email, String password){
        this.email = email;
        this.password = password;
    }

    private boolean isValidEmail() {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isValidPassword() {
        return this.password.length() >= 3; 
    }

    public String getEmail(){
        return email;
    }

    public boolean validatePayment(){
        if (isValidEmail() && isValidPassword()){
            return true;
        }
        return false;

    }
}
