

package login_system;

import db.UserDBController;

/**
Retrieves staff information based on the provided staff ID.
This class interacts with the UserDBController to get the Staff object associated with the given staff ID.
*/

public class RetrieveStaff {
    private UserDBController userDBController;

    public RetrieveStaff(UserDBController userDBController){
        this.userDBController = userDBController;
    }

    public users.Staff retrieveStaff(String staffId){
        return userDBController.getStaffByLoginId(staffId);
    }

}
