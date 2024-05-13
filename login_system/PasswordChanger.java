

package login_system;

import users.Staff;
import db.UserDBController; 
import java.util.Scanner;
/**
Facilitates password change functionality for staff members.
This class interacts with the user to input a new password and verifies its complexity. It then updates the password in the user database through the UserDBController.
*/
public class PasswordChanger {
    private UserDBController userDBController;
    private Scanner scanner;
    
    public PasswordChanger(Scanner scanner, UserDBController userDBController) {
        this.userDBController = userDBController;
        this.scanner = scanner;
    }

    public boolean changePassword(String staffId, boolean insecure) {
        Staff staffMember = userDBController.getStaffByLoginId(staffId);

        while (true){
            System.out.println("Input a new password \n");

            String newPassword = this.scanner.nextLine();

         
            boolean secure = checkComplexity(newPassword);

            if (secure){

                staffMember.setPassword(newPassword);
                return true;
            }

            
            else{
                System.out.println("Password not secure");
                System.out.println("Please ensure that your password fulfils the following requirements:\n 1. At least 8 characters \n 2. At least 1 special character \n 3. At least 1 lower case letter \n 4. At least 1 upper case letter \n 5. At least 1 number\n");
            }
            
            }
        }





    public boolean changePassword(String staffId) {
        Staff staffMember = userDBController.getStaffByLoginId(staffId);

        while (true){
            System.out.println("Input a new password or input -1 to exit\n");

            String newPassword = this.scanner.nextLine();

            if (newPassword.equals("-1")){
                return false;
            }

            boolean secure = checkComplexity(newPassword);

            if (secure){

                staffMember.setPassword(newPassword);
                return true;
            }

            

            else{
                System.out.println("Password not secure");
                System.out.println("Please ensure that your password fulfils the following requirements:\n 1. At least 8 characters \n 2. At least 1 special character \n 3. At least 1 lower case letter \n 4. At least 1 upper case letter \n 5. At least 1 number\n");
            }
            
            }
        }


    public boolean checkComplexity(String password){
        
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
           
    }

    
}

