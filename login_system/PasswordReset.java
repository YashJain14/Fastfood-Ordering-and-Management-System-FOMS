
package login_system;

import db.UserDBController;
import users.Staff;
/**
Provides functionality for staff to reset their passwords.
This class interacts with the UserDBController to update the password for the specified staff member.
*/

public class PasswordReset {

    private UserDBController userDBController;
    
    public PasswordReset(UserDBController userDBController) {
        this.userDBController = userDBController;
    }

    public boolean changePassword(String staffId, String newPassword) {
        Staff staffMember = userDBController.getStaffByLoginId(staffId);
        if (staffMember != null) {
            staffMember.setPassword(newPassword);
            return true;
        } else {
            return false;
        }
    }
}
