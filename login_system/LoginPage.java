

package login_system;

import java.util.Scanner;
import interfaces.UserInterface;
import users.Customer;
import users.User;
import db.BranchDBController;
import db.UserDBController;
/**
Implements the login page of the application.
This class provides a user interface for staff login and customer order placement. It interacts with various classes to handle branch selection, password input and verification, user retrieval, and password changes.
*/
public class LoginPage implements UserInterface{
	
	private BranchSelector branchSelector;
	private Scanner scanner;
	private PasswordInput passwordInput;
    private User user;
    private RetrieveStaff retrieveStaff;

    private PasswordChanger passwordChanger;

	
	public LoginPage(Scanner scanner, UserDBController userDBController, BranchDBController branchDBController) {
        this.scanner = scanner;
        this.branchSelector = new BranchSelector(scanner, branchDBController);
        this.passwordInput = new PasswordInput(scanner, userDBController);
        this.passwordChanger = new PasswordChanger(scanner, userDBController);
        this.retrieveStaff = new RetrieveStaff(userDBController);
    }
	

	
	public boolean genUI() {
        while(true){
            System.out.println("Welcome to the Fastfood Ordering and Management System (FOMS)\n");
            System.out.println("Please select an option:\n");
            System.out.println("1. Staff Login");
            System.out.println("2. Order Food");
            System.out.println("Enter 'exit' to close the application.\n");

            String userInput = scanner.nextLine().trim();

            // Process the input
            switch (userInput) {
                case "1": // Staff Login
                    
                    while (true){
                    
                        System.out.println("\n\nPlease enter your staff ID:");
                        String staffId = scanner.nextLine();

                        if (staffId.equals("-1")){
                            break;}

                        int isValidPassword = this.passwordInput.inputPassword(staffId);
                        if (isValidPassword == 1) {

                            System.out.println("\nLogin successful!\n");
                            this.user = this.retrieveStaff.retrieveStaff(staffId);

                            while (true){
                            System.out.println("\nWould you like to change your password?\n");

                            System.out.println("1. Yes.");
                            System.out.println("2. No");

                            String choice = scanner.nextLine();

                            if (choice.equals("2")){
                                return true;
                            }

                            else if (choice.equals("1")){
                                System.out.println("\nAre you sure you want to change your password?\n");

                                System.out.println("1. Yes.");
                                System.out.println("2. No");

                                choice = scanner.nextLine();

                                if (choice.equals( "1")){
                                    this.passwordChanger.changePassword(staffId);
                                    return true;
                                }
                            }
                        }   

                    } 
                        else if(isValidPassword == 2){
                            System.out.println("Login successful!\n");
                            this.user = this.retrieveStaff.retrieveStaff(staffId);

                            System.out.println("\n\nYour password is insecure please change your password\n");
                            this.passwordChanger.changePassword(staffId, true);


                            return true;

                        }

                        
                        else {
                            System.out.println("\nInvalid password. Try again or input '-1' to exit.\n");
        
                        }
                    }
                    break;
                case "2": // Order Food
                    int branchId = this.branchSelector.selectBranch(true);

                    if (branchId == -1){
                        continue;
                    }

                    user = new Customer(branchId);
                    System.out.println("\nRedirecting to the food ordering system...");
                    return true;
                case "exit":
                    return false;

                    
                default:
                    System.out.println("\nInvalid option. Please try again.\n\n");
                    break;
            }
	}
}


    public User getUser(){
        return user;
    }
	
}
