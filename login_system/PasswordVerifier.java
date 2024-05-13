
package login_system;

import users.Staff;
import db.UserDBController;
/**
Verifies the entered password against the stored password for a given staff ID.
This class interacts with the UserDBController to retrieve the stored password and then compares it with the provided password.
*/

public class PasswordVerifier {

    private UserDBController userDBController;
    public PasswordVerifier(UserDBController userDBController){
        this.userDBController = userDBController;

    }
    
   public boolean verifyPassword(String staffId, String userInput) {
        
        try {
            Staff staffMember = userDBController.getStaffByLoginId(staffId);
            if (staffMember != null) {
                return staffMember.checkPassword(userInput);
            }
        } catch (NullPointerException e) {;
            return false;
        }
        return false;
    }
    
}

