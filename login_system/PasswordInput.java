

package login_system;

import java.util.Scanner;
import db.UserDBController;
/**
Handles password input and verification for staff login.
This class interacts with the user to input their password and then verifies the password against the stored password for the given staff ID. It also handles cases where the password might be empty or insecure.
*/
public class PasswordInput {
    private Scanner scanner;
    private PasswordVerifier passwordVerifier;

    public PasswordInput(Scanner scanner, UserDBController userDBController) {
        this.scanner = scanner;
        this.passwordVerifier = new PasswordVerifier(userDBController);
    }

    public int inputPassword(String staffId) {
        String userInput;
        
        while (true){
        System.out.println("\nEnter password for staff ID " + staffId + ":");
        userInput = scanner.nextLine();
        if (userInput == null || userInput.trim().isEmpty()) {
        System.out.println("\nPassword cannot be empty.");
        continue;}
        break;
        
    }
        if (passwordVerifier.verifyPassword(staffId, userInput)){
            if (userInput.equals("password")){
                return 2;
            }
            else return 1;
    }
    return 0;
    }
}

